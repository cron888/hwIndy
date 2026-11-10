package org.example.recommendation.service;

import org.example.recommendation.model.RecommendationResponse;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.service.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис рекомендаций.
 * 
 * Согласно ТЗ: сервис получает id пользователя и по каждой рекомендации из таблицы
 * проверяет, подходит ли этот пользователь под правила этой рекомендации.
 * Если да — добавляет продукт из таблицы с текстом и именем в ответ REST API.
 * 
 * Архитектура: используется паттерн Strategy для каждой рекомендации.
 * Каждая RecommendationRuleSet — это отдельная стратегия проверки одной рекомендации.
 * Это позволяет легко добавлять новые рекомендации без изменения существующего кода.
 */
@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    /**
     * Получает рекомендации для пользователя.
     * 
     * Согласно ТЗ: по каждой рекомендации из таблицы проверяет, подходит ли
     * пользователь под правила этой рекомендации. Если да — добавляет продукт
     * в ответ REST API.
     * 
     * @param userId ID пользователя, для которого формируются рекомендации
     * @return объект с ID пользователя и списком подходящих рекомендаций
     */
    public RecommendationResponse getRecommendations(String userId) {
        List<RecommendationItem> recommendations = new ArrayList<>();

        // Проходим по всем правилам (рекомендациям) из БД
        for (RecommendationRuleSet ruleSet : ruleSets) {
            // Проверяем, подходит ли пользователь под правила рекомендации
            ruleSet.check(userId).ifPresent(recommendations::add);
        }

        return new RecommendationResponse(userId, recommendations);
    }
}
