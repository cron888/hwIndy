package org.example.recommendation.service.rule;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.RecommendationResponse.RecommendationItem;
import org.example.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Правило для рекомендации 1: "Сумма пополнений по всем продуктам типа DEBIT > 100000"
 * 
 * Согласно ТЗ:
 * - Все правила объединяются логическим "И"
 * - Если в правиле есть "ИЛИ", это логическое "ИЛИ" между двумя записанными правилами
 * - Клиент является пользователем продукта тогда и только тогда, когда в таблице транзакций
 *   по этому продукту есть любая транзакция
 * 
 * Для рекомендации 1:
 * - Продукт: prod-001 (Дебетовая карта, тип DEBIT)
 * - Правило: сумма всех DEPOSIT-транзакций пользователя по продуктам типа DEBIT должна быть > 100000
 * - Тестовый пользователь: cd515076-5d8a-44be-930e-8d4fcb79f42d
 */
@Component
public class RecommendationRuleSet1 implements RecommendationRuleSet {

    private static final double THRESHOLD = 100000.0;
    private static final String PRODUCT_TYPE = "DEBIT";
    private static final String PRODUCT_ID = "prod-001";

    private final RecommendationRepository repository;

    public RecommendationRuleSet1(RecommendationRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверяет, подходит ли пользователь под правила рекомендации 1.
     * 
     * Алгоритм:
     * 1. Вычисляем сумму всех DEPOSIT-транзакций пользователя по продуктам типа DEBIT
     *    (через JOIN transactions → products, так как тип продукта хранится в таблице products)
     * 2. Сравниваем сумму с порогом 100000
     * 3. Если сумма > порога — возвращаем продукт, иначе пустой Optional
     */
    @Override
    public Optional<RecommendationItem> check(String userId) {
        // Получаем сумму пополнений по продуктам типа DEBIT
        Double sumDeposits = repository.sumDepositsByProductType(userId, PRODUCT_TYPE);
        
        // Проверяем условие: сумма > порога
        if (sumDeposits != null && sumDeposits > THRESHOLD) {
            // Получаем продукт для возврата в ответе
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
