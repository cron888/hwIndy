package org.example.recommendation.service.rule;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Правило для рекомендации 2:
 * "(Сумма пополнений по всем продуктам типа DEBIT > 50000) ИЛИ (Сумма пополнений по всем продуктам типа DEPOSIT > 100000)"
 */
@Component
public class RecommendationRuleSet2 implements RecommendationRuleSet {

    private static final double DEBIT_THRESHOLD = 50000.0;
    private static final double DEPOSIT_THRESHOLD = 100000.0;
    private static final String DEBIT_TYPE = "DEBIT";
    private static final String DEPOSIT_TYPE = "DEPOSIT";
    private static final String PRODUCT_ID = "prod-002";

    private final RecommendationRepository repository;

    public RecommendationRuleSet2(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationItem> check(String userId) {
        // Проверяем первое условие: DEBIT > 50000
        Double sumDebit = repository.sumDepositsByProductType(userId, DEBIT_TYPE);
        boolean condition1 = sumDebit != null && sumDebit > DEBIT_THRESHOLD;

        // Проверяем второе условие: DEPOSIT > 100000
        Double sumDeposit = repository.sumDepositsByProductType(userId, DEPOSIT_TYPE);
        boolean condition2 = sumDeposit != null && sumDeposit > DEPOSIT_THRESHOLD;

        // Логическое ИЛИ между условиями
        if (condition1 || condition2) {
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
