package org.example.recommendation.controller;

import org.example.recommendation.model.RecommendationResponse;
import org.example.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API контроллер для сервиса рекомендаций.
 * 
 * Согласно ТЗ: сервис выставляет наружу один метод REST API:
 * Request: GET /recommendation/<user_id>
 * Response: 200 OK с JSON объектом, содержащим user_id и массив recommendations.
 * В случае если рекомендаций нет — возвращается пустой массив.
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Получает рекомендации для пользователя по его ID.
     * 
     * Согласно ТЗ:
     * - Request: GET /recommendation/{user_id}
     * - Response: {"user_id": "...", "recommendations": [{"name": "...", "id": "...", "text": "..."}]}
     * - Content-Type: application/json
     * 
     * @param user_id ID пользователя (UUID)
     * @return ResponseEntity с JSON-объектом рекомендаций или пустым массивом
     */
    @GetMapping("/{user_id}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable String user_id) {
        RecommendationResponse response = recommendationService.getRecommendations(user_id);
        return ResponseEntity.ok(response);
    }
}
