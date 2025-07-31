package com.halggeol.backend.survey.service;

import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.survey.dto.KnowledgeSurveyRequestDTO;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import com.halggeol.backend.survey.mapper.SurveyMapper;
import com.halggeol.backend.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyMapper surveyMapper;
    private final UserService userService;

    @Override
    public Map<String, String> initKnowledge(KnowledgeSurveyRequestDTO surveyResult) {
        String email = surveyResult.getEmail();
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 입력되지 않았습니다.");
        }
        if (!userService.findByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }

        int userKlg = 0; // surveyResult로 점수 내기

        surveyMapper.updateKnowledgeByEmail(email, userKlg);
        return Map.of("Message", "금융 이해도 설정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> updateKnowledge(
        CustomUser user,
        KnowledgeSurveyRequestDTO surveyResult
    ) {
        String email = user.getUser().getEmail();

        int userKlg = 0; // surveyResult로 점수 내기

        surveyMapper.updateKnowledgeByEmail(email, userKlg);
        return Map.of("Message", "금융 이해도 갱신이 완료되었습니다.");
    }

    @Override
    public Map<String, String> initTendency(TendencySurveyRequestDTO surveyResult) {
        String email = surveyResult.getEmail();
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 입력되지 않았습니다.");
        }
        if (!userService.findByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }

        log.error(surveyResult.toString());

        int risk = 0; // surveyResult로 점수 내기
        UserVectorResponseDTO scores = null; // 5개 user score 점수 내기

//        surveyMapper.updateTendencyByEmail(
//            email,
//            risk,
//            scores.getYieldScore(),
//            scores.getRiskScore(),
//            scores.getCostScore(),
//            scores.getLiquidityScore(),
//            scores.getComplexityScore()
//        );
        return Map.of("Message", "투자 성향 설정이 완료되었습니다.");
    }

    @Override
    public Map<String, String> updateTendency(
        CustomUser user,
        TendencySurveyRequestDTO surveyResult
    ) {
        String email = user.getUser().getEmail();

        int risk = 0; // surveyResult로 점수 내기
        UserVectorResponseDTO scores = null; // 5개 user score 점수 내기

//        surveyMapper.updateTendencyByEmail(
//            email,
//            risk,
//            scores.getYieldScore(),
//            scores.getRiskScore(),
//            scores.getCostScore(),
//            scores.getLiquidityScore(),
//            scores.getComplexityScore()
//        );
        return Map.of("Message", "투자 성향 갱신이 완료되었습니다.");
    }
}
