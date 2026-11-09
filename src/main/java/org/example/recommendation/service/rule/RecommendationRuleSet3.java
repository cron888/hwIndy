package org.example.recommendation.service.rule;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Правило для рекомендации 3:
 * "Сумма пополнений по всем продуктам типа DEBIT > 200000"
 */
@Component
public class RecommendationRuleSet3 implements RecommendationRuleSet {

    private static final double THRESHOLD = 200000.0;
    private static final String PRODUCT_TYPE = "DEBIT";
    private static final String PRODUCT_ID = "prod-003";

    private final RecommendationRepository repository;

    public RecommendationRuleSet3(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationItem> check(String userId) {
        Double sumDeposits = repository.sumDepositsByProductType(userId, PRODUCT_TYPE);
        if (sumDeposits != null && sumDeposits > THRESHOLD) {
            Product product = repository.findProductById(PRODUCT_ID);
            if (product != null) {
                return Optional.of(new RecommendationItem(
                    product.getName(),
                    product.getId(),
                    product.getText()
                ));
            }
        }
        return Optional.empty();
    }

    @Override
    public String getProductId() {
        return PRODUCT_ID;
    }
}
