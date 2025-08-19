package com.halggeol.backend.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.domain.User;
import com.halggeol.backend.user.mapper.UserMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class GeminiService {

    private static final long CACHE_TTL_HOURS = 12;
    private static final String CACHE_KEY_SEPARATOR = "_";

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, CacheEntry> analysisCache = new ConcurrentHashMap<>();
    
    public GeminiService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // JSR310 모듈 자동 등록
    }

    private static class CacheEntry {
        final String value;
        final LocalDateTime createdAt;
        
        CacheEntry(String value) {
            this.value = value;
            this.createdAt = LocalDateTime.now();
        }
        
        boolean isExpired() {
            return LocalDateTime.now().isAfter(createdAt.plusHours(CACHE_TTL_HOURS));
        }
    }

    public String generateAdvantageDisadvantage(String productData, String userData) {
        String cacheKey = createCacheKey(productData, userData);
        
        CacheEntry cachedEntry = analysisCache.get(cacheKey);
        if (cachedEntry != null && !cachedEntry.isExpired()) {
            log.info("캐시에서 Gemini 분석 결과 반환: {}", cacheKey.substring(0, Math.min(cacheKey.length(), 20)) + "...");
            return cachedEntry.value;
        } else if (cachedEntry != null) {
            analysisCache.remove(cacheKey);
            log.info("만료된 캐시 제거: {}", cacheKey.substring(0, Math.min(cacheKey.length(), 20)) + "...");
        }

        try {
            log.info("Gemini API 호출 시작 - 새로운 분석 요청");
            String prompt = createPrompt(productData, userData);
            
            String requestBody = String.format("""
                {
                    "contents": [{
                        "parts": [{
                            "text": "%s"
                        }]
                    }]
                }
                """, prompt.replace("\"", "\\\"").replace("\n", "\\n"));

            HttpResponse<String> response = Unirest.post(apiUrl)
                    .header("Content-Type", "application/json")
                    .queryString("key", apiKey)
                    .body(requestBody)
                    .asString();

            if (response.getStatus() == 200) {
                String result = parseGeminiResponse(response.getBody());
                if (result != null) {
                    analysisCache.put(cacheKey, new CacheEntry(result));
                    log.info("Gemini 분석 결과를 캐시에 저장: {}", cacheKey.substring(0, Math.min(cacheKey.length(), 20)) + "...");
                }
                return result;
            } else {
                log.error("Gemini API 호출 실패: Status {}, Body: {}", response.getStatus(), response.getBody());
                return null;
            }

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            return null;
        }
    }

    private String createPrompt(String productData, String userData) {
        return String.format("""
            다음은 금융상품 정보와 사용자 정보입니다.
            
            [금융상품 정보]
            %s
            
            [사용자 정보]
            %s
            
            [인자 설명]
            모두 1에 가까울 수록 강한 성향을 뜻합니다.
                        
            yield_score (수익 추구 성향)
            risk_score (위험 감수 성향)
            cost_score (비용 민감도)
            liquidity_score (유동성 선호)
            complexity_score (복잡성 수용/이해도)
            
            [요청 사항]
            
            위 정보를 바탕으로 다음을 분석해주세요:
            1. 이 사용자가 이 금융상품을 사용할 때 느낄 수 있는 개인화된 장점과 단점
            2. 상품의 핵심 특징을 한 줄로 요약한 설명
            
            응답 형식:
            {
                "advantage": "이 사용자에게 특화된 구체적인 장점 (50자 이내)",
                "disadvantage": "이 사용자에게 특화된 구체적인 단점 (50자 이내)",
                "description": "상품의 핵심 특징을 한 줄로 설명 (30자 이내)"
            }
            
            JSON 형식으로만 응답해주세요.
            """, productData, userData);
    }

    private String parseGeminiResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");
                
                if (parts.isArray() && parts.size() > 0) {
                    String text = parts.get(0).path("text").asText();
                    
                    // JSON 부분만 추출
                    int startIndex = text.indexOf("{");
                    int endIndex = text.lastIndexOf("}") + 1;
                    
                    if (startIndex >= 0 && endIndex > startIndex) {
                        return text.substring(startIndex, endIndex);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Gemini 응답 파싱 중 오류 발생", e);
        }
        
        return null;
    }

    public Object analyzeProductWithGemini(Object inputData, User user) {
        if (inputData == null) return null;

        String defaultAdvantage = "이 상품의 장점을 분석 중입니다.";
        String defaultDisadvantage = "이 상품의 단점을 분석 중입니다.";
        String defaultDescription = "상품 설명을 생성 중입니다.";

        try {
            // 입력 데이터를 JSON 문자열로 변환
            String productData = objectMapper.writeValueAsString(inputData);
            
            // 사용자 정보를 JSON 문자열로 생성
            User fullUser = userMapper.findById(user.getId());
            String userData = createUserDataString(fullUser);

            // 항상 Gemini 호출 (새로운 분석 요청)
            String geminiResponse = generateAdvantageDisadvantage(productData, userData);
            
            if (geminiResponse != null) {
                JsonNode jsonNode = objectMapper.readTree(geminiResponse);
                String generatedAdvantage = jsonNode.path("advantage").asText(defaultAdvantage);
                String generatedDisadvantage = jsonNode.path("disadvantage").asText(defaultDisadvantage);
                String generatedDescription = jsonNode.path("description").asText(defaultDescription);
                
                // 분석 결과만 담은 객체 생성
                return createAnalysisResult(generatedAdvantage, generatedDisadvantage, generatedDescription);
            } else {
                return createAnalysisResult(defaultAdvantage, defaultDisadvantage, defaultDescription);
            }

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            return createAnalysisResult(defaultAdvantage, defaultDisadvantage, defaultDescription);
        }
    }

    public Object setAdvantageDisadvantageUsingGemini(Object result, User user) {
        if (result == null) return null;

        String defaultAdvantage = "이 상품의 장점을 분석 중입니다.";
        String defaultDisadvantage = "이 상품의 단점을 분석 중입니다.";
        String defaultDescription = "상품 설명을 생성 중입니다.";

        try {
            // 상품 정보를 JSON 문자열로 생성 (advantage, disadvantage 제외)
            String productData = createProductDataString(result);
            
            // 사용자 정보를 JSON 문자열로 생성
            User fullUser = userMapper.findById(user.getId());
            String userData = createUserDataString(fullUser);

            boolean needsGeminiCall = false;

            if (result instanceof DepositDetailResponseDTO) {
                DepositDetailResponseDTO dto = (DepositDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty() ||
                    dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof SavingsDetailResponseDTO) {
                SavingsDetailResponseDTO dto = (SavingsDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty() ||
                    dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof FundDetailResponseDTO) {
                FundDetailResponseDTO dto = (FundDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty() ||
                    dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof ForexDetailResponseDTO) {
                ForexDetailResponseDTO dto = (ForexDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty() ||
                    dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof PensionDetailResponseDTO) {
                PensionDetailResponseDTO dto = (PensionDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty() ||
                    dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            }

            if (needsGeminiCall) {
                String geminiResponse = generateAdvantageDisadvantage(productData, userData);
                
                if (geminiResponse != null) {
                    JsonNode jsonNode = objectMapper.readTree(geminiResponse);
                    String generatedAdvantage = jsonNode.path("advantage").asText(defaultAdvantage);
                    String generatedDisadvantage = jsonNode.path("disadvantage").asText(defaultDisadvantage);
                    String generatedDescription = jsonNode.path("description").asText(defaultDescription);
                    
                    setGeneratedAdvantageDisadvantage(result, generatedAdvantage, generatedDisadvantage, generatedDescription);
                } else {
                    setGeneratedAdvantageDisadvantage(result, defaultAdvantage, defaultDisadvantage, defaultDescription);
                }
            }

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            setGeneratedAdvantageDisadvantage(result, defaultAdvantage, defaultDisadvantage, defaultDescription);
        }
        
        return result;
    }

    private String createProductDataString(Object result) {
        try {
            if (result instanceof DepositDetailResponseDTO) {
                DepositDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), DepositDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                clone.setDescription(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof SavingsDetailResponseDTO) {
                SavingsDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), SavingsDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                clone.setDescription(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof FundDetailResponseDTO) {
                FundDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), FundDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                clone.setDescription(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof ForexDetailResponseDTO) {
                ForexDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), ForexDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                clone.setDescription(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof PensionDetailResponseDTO) {
                PensionDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), PensionDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                clone.setDescription(null);
                return objectMapper.writeValueAsString(clone);
            }
        } catch (Exception e) {
            log.error("상품 데이터 JSON 생성 중 오류", e);
        }
        return "{}";
    }

    private String createUserDataString(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            log.error("사용자 데이터 JSON 생성 중 오류", e);
            return "{}";
        }
    }

    private void setGeneratedAdvantageDisadvantage(Object result, String advantage, String disadvantage, String description) {
        if (result instanceof DepositDetailResponseDTO) {
            DepositDetailResponseDTO dto = (DepositDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
            if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                dto.setDescription(description);
            }
        } else if (result instanceof SavingsDetailResponseDTO) {
            SavingsDetailResponseDTO dto = (SavingsDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
            if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                dto.setDescription(description);
            }
        } else if (result instanceof FundDetailResponseDTO) {
            FundDetailResponseDTO dto = (FundDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
            if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                dto.setDescription(description);
            }
        } else if (result instanceof ForexDetailResponseDTO) {
            ForexDetailResponseDTO dto = (ForexDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
            if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                dto.setDescription(description);
            }
        } else if (result instanceof PensionDetailResponseDTO) {
            PensionDetailResponseDTO dto = (PensionDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
            if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
                dto.setDescription(description);
            }
        }
    }

    private String createCacheKey(String productData, String userData) {
        try {
            int productHash = productData.hashCode();
            int userHash = userData.hashCode();
            return productHash + CACHE_KEY_SEPARATOR + userHash;
        } catch (Exception e) {
            log.error("캐시 키 생성 중 오류", e);
            return "default_key";
        }
    }

    private Object createAnalysisResult(String advantage, String disadvantage, String description) {
        return new AnalysisResult(advantage, disadvantage, description);
    }

    public static class AnalysisResult {
        private String advantage;
        private String disadvantage;
        private String description;

        public AnalysisResult(String advantage, String disadvantage, String description) {
            this.advantage = advantage;
            this.disadvantage = disadvantage;
            this.description = description;
        }

        public String getAdvantage() { return advantage; }
        public String getDisadvantage() { return disadvantage; }
        public String getDescription() { return description; }

        public void setAdvantage(String advantage) { this.advantage = advantage; }
        public void setDisadvantage(String disadvantage) { this.disadvantage = disadvantage; }
        public void setDescription(String description) { this.description = description; }
    }

}