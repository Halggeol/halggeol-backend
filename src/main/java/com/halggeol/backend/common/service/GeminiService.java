package com.halggeol.backend.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.user.mapper.UserMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateAdvantageDisadvantage(String productData, String userData) {
        try {
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
                return parseGeminiResponse(response.getBody());
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
            
            
            위 정보를 바탕으로 이 사용자가 이 금융상품을 사용할 때 느낄 수 있는 개인화된 장점과 단점을 분석해주세요.
            
            응답 형식:
            {
                "advantage": "이 사용자에게 특화된 구체적인 장점 (50자 이내)",
                "disadvantage": "이 사용자에게 특화된 구체적인 단점 (50자 이내)"
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

    public void setAdvantageDisadvantageUsingGemini(Object result, User user) {
        if (result == null) return;

        String defaultAdvantage = "이 상품의 장점을 분석 중입니다.";
        String defaultDisadvantage = "이 상품의 단점을 분석 중입니다.";

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
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof SavingsDetailResponseDTO) {
                SavingsDetailResponseDTO dto = (SavingsDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof FundDetailResponseDTO) {
                FundDetailResponseDTO dto = (FundDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof ForexDetailResponseDTO) {
                ForexDetailResponseDTO dto = (ForexDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            } else if (result instanceof PensionDetailResponseDTO) {
                PensionDetailResponseDTO dto = (PensionDetailResponseDTO) result;
                if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty() ||
                    dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                    needsGeminiCall = true;
                }
            }

            if (needsGeminiCall) {
                String geminiResponse = generateAdvantageDisadvantage(productData, userData);
                
                if (geminiResponse != null) {
                    JsonNode jsonNode = objectMapper.readTree(geminiResponse);
                    String generatedAdvantage = jsonNode.path("advantage").asText(defaultAdvantage);
                    String generatedDisadvantage = jsonNode.path("disadvantage").asText(defaultDisadvantage);
                    
                    setGeneratedAdvantageDisadvantage(result, generatedAdvantage, generatedDisadvantage);
                } else {
                    setGeneratedAdvantageDisadvantage(result, defaultAdvantage, defaultDisadvantage);
                }
            }

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            setGeneratedAdvantageDisadvantage(result, defaultAdvantage, defaultDisadvantage);
        }
    }

    private String createProductDataString(Object result) {
        try {
            if (result instanceof DepositDetailResponseDTO) {
                DepositDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), DepositDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof SavingsDetailResponseDTO) {
                SavingsDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), SavingsDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof FundDetailResponseDTO) {
                FundDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), FundDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof ForexDetailResponseDTO) {
                ForexDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), ForexDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
                return objectMapper.writeValueAsString(clone);
            } else if (result instanceof PensionDetailResponseDTO) {
                PensionDetailResponseDTO clone = objectMapper.readValue(
                    objectMapper.writeValueAsString(result), PensionDetailResponseDTO.class);
                clone.setAdvantage(null);
                clone.setDisadvantage(null);
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

    private void setGeneratedAdvantageDisadvantage(Object result, String advantage, String disadvantage) {
        if (result instanceof DepositDetailResponseDTO) {
            DepositDetailResponseDTO dto = (DepositDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
        } else if (result instanceof SavingsDetailResponseDTO) {
            SavingsDetailResponseDTO dto = (SavingsDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
        } else if (result instanceof FundDetailResponseDTO) {
            FundDetailResponseDTO dto = (FundDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
        } else if (result instanceof ForexDetailResponseDTO) {
            ForexDetailResponseDTO dto = (ForexDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
        } else if (result instanceof PensionDetailResponseDTO) {
            PensionDetailResponseDTO dto = (PensionDetailResponseDTO) result;
            if (dto.getAdvantage() == null || dto.getAdvantage().trim().isEmpty()) {
                dto.setAdvantage(advantage);
            }
            if (dto.getDisadvantage() == null || dto.getDisadvantage().trim().isEmpty()) {
                dto.setDisadvantage(disadvantage);
            }
        }
    }
}