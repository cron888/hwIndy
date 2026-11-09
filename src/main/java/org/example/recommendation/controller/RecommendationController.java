package org.example.recommendation.controller;

import org.example.recommendation.model.RecommendationResponse;
import org.example.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable String user_id) {
        RecommendationResponse response = recommendationService.getRecommendations(user_id);
        return ResponseEntity.ok(response);
    }
}
