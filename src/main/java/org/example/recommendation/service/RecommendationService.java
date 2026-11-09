package org.example.recommendation.service;

import org.example.recommendation.model.RecommendationResponse;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.service.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис рекомендаций.
 * Использует список RuleSet-ов для проверки каждой рекомендации.
 */
@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    /**
     * Получает рекомендации для пользователя, проверяя каждую RuleSet.
     */
    public RecommendationResponse getRecommendations(String userId) {
        List<RecommendationItem> recommendations = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            ruleSet.check(userId).ifPresent(recommendations::add);
        }

        return new RecommendationResponse(userId, recommendations);
    }
}
