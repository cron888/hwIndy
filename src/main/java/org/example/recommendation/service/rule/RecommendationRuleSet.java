package org.example.recommendation.service.rule;

import org.example.recommendation.model.RecommendationResponse.RecommendationItem;

import java.util.Optional;

/**
 * Интерфейс для проверки правил конкретной рекомендации.
 * Каждая реализация проверяет, подходит ли пользователь под правила одной рекомендации.
 */
public interface RecommendationRuleSet {
    /**
     * Проверяет, подходит ли пользователь под правила рекомендации.
     *
     * @param userId ID пользователя
     * @return RecommendationItem если пользователь подходит, пустой Optional иначе
     */
    Optional<RecommendationItem> check(String userId);

    /**
     * Возвращает ID продукта для этой рекомендации.
     */
    String getProductId();
}
