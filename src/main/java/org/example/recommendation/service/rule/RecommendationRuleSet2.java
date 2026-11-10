package org.example.recommendation.service.rule;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Правило для рекомендации 2:
 * "(Сумма пополнений по всем продуктам типа DEBIT > 50000) ИЛИ (Сумма пополнений по всем продуктам типа DEPOSIT > 100000)"
 * 
 * Согласно ТЗ:
 * - Если в правиле есть "ИЛИ", это логическое "ИЛИ" между двумя записанными правилами
 * - Все правила объединяются логическим "И"
 * 
 * Для рекомендации 2:
 * - Продукт: prod-002 (Вклад Сохраняй, тип DEPOSIT)
 * - Правило: (DEBIT > 50000) ИЛИ (DEPOSIT > 100000)
 * - Тестовый пользователь: d4a4d619-9a0c-4fc5-b0cb-76c49409546b
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

    /**
     * Проверяет, подходит ли пользователь под правила рекомендации 2.
     * 
     * Алгоритм:
     * 1. Вычисляем сумму DEPOSIT-транзакций по продуктам типа DEBIT
     * 2. Вычисляем сумму DEPOSIT-транзакций по продуктам типа DEPOSIT
     * 3. Проверяем условие: (DEBIT > 50000) ИЛИ (DEPOSIT > 100000)
     * 4. Если хотя бы одно условие выполнено — возвращаем продукт
     */
    @Override
    public Optional<RecommendationItem> check(String userId) {
        // Проверяем первое условие: сумма DEPOSIT по продуктам типа DEBIT > 50000
        Double sumDebit = repository.sumDepositsByProductType(userId, DEBIT_TYPE);
        boolean condition1 = sumDebit != null && sumDebit > DEBIT_THRESHOLD;

        // Проверяем второе условие: сумма DEPOSIT по продуктам типа DEPOSIT > 100000
        Double sumDeposit = repository.sumDepositsByProductType(userId, DEPOSIT_TYPE);
        boolean condition2 = sumDeposit != null && sumDeposit > DEPOSIT_THRESHOLD;

        // Логическое ИЛИ между условиями (согласно ТЗ)
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
