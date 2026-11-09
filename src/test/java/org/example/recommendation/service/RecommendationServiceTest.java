package org.example.recommendation.service;

import org.example.recommendation.model.RecommendationResponse;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private org.example.recommendation.service.rule.RecommendationRuleSet ruleSet1;

    @Mock
    private org.example.recommendation.service.rule.RecommendationRuleSet ruleSet2;

    @Mock
    private org.example.recommendation.service.rule.RecommendationRuleSet ruleSet3;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void shouldReturnRecommendationsWhenAllRulesMatch() {
        // Given
        String userId = "test-user";
        RecommendationItem item1 = new RecommendationItem("Product 1", "prod-1", "Text 1");
        RecommendationItem item2 = new RecommendationItem("Product 2", "prod-2", "Text 2");

        when(ruleSet1.check(userId)).thenReturn(Optional.of(item1));
        when(ruleSet2.check(userId)).thenReturn(Optional.of(item2));
        when(ruleSet3.check(userId)).thenReturn(Optional.empty());

        // When
        RecommendationResponse response = recommendationService.getRecommendations(userId);

        // Then
        assertEquals(userId, response.getUser_id());
        assertEquals(2, response.getRecommendations().size());
        assertEquals(item1.getName(), response.getRecommendations().get(0).getName());
        assertEquals(item2.getName(), response.getRecommendations().get(1).getName());
    }

    @Test
    void shouldReturnEmptyRecommendationsWhenNoRulesMatch() {
        // Given
        String userId = "test-user";

        when(ruleSet1.check(userId)).thenReturn(Optional.empty());
        when(ruleSet2.check(userId)).thenReturn(Optional.empty());
        when(ruleSet3.check(userId)).thenReturn(Optional.empty());

        // When
        RecommendationResponse response = recommendationService.getRecommendations(userId);

        // Then
        assertEquals(userId, response.getUser_id());
        assertTrue(response.getRecommendations().isEmpty());
    }

    @Test
    void shouldReturnOnlyMatchingRecommendations() {
        // Given
        String userId = "test-user";
        RecommendationItem item2 = new RecommendationItem("Product 2", "prod-2", "Text 2");

        when(ruleSet1.check(userId)).thenReturn(Optional.empty());
        when(ruleSet2.check(userId)).thenReturn(Optional.of(item2));
        when(ruleSet3.check(userId)).thenReturn(Optional.empty());

        // When
        RecommendationResponse response = recommendationService.getRecommendations(userId);

        // Then
        assertEquals(userId, response.getUser_id());
        assertEquals(1, response.getRecommendations().size());
        assertEquals(item2.getName(), response.getRecommendations().get(0).getName());
    }
}
