package org.example.recommendation.repository;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.Recommendation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с базой данных через JdbcTemplate.
 * 
 * Согласно ТЗ: не используем JPA/Entity, только JDBC и JdbcTemplate.
 * Данные в БД уже наполнены, сервис только читает их.
 */
@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получает все рекомендации из таблицы recommendations.
     * Каждая рекомендация содержит: ID, ID продукта и набор правил.
     */
    public List<Recommendation> findAllRecommendations() {
        String sql = "SELECT id, product_id, rules FROM recommendations";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Recommendation(
                rs.getString("id"),
                rs.getString("product_id"),
                rs.getString("rules")
            )
        );
    }

    /**
     * Получает продукт по ID для возврата в ответе API.
     * 
     * @param productId ID продукта из таблицы recommendations
     * @return объект продукта с именем, типом и текстовым описанием
     */
    public Product findProductById(String productId) {
        String sql = "SELECT id, name, type, text FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Product(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("text")
            ), productId);
    }

    /**
     * Вычисляет сумму пополнений (DEPOSIT) для конкретного пользователя
     * по всем продуктам указанного типа.
     * 
     * Согласно ТЗ: выражение "Сумма пополнений по всем продуктам типа DEBIT"
     * означает, что нужно сложить величину всех транзакций пополнения (DEPOSIT),
     * которые были сделаны конкретным пользователем по продуктам типа DEBIT.
     * Для этого соединяем таблицы транзакций и продуктов через JOIN,
     * так как в таблице транзакций нет указания на тип продукта — только id продукта.
     * 
     * @param userId ID пользователя
     * @param productType Тип продукта (DEBIT, DEPOSIT, CREDIT и т.д.)
     * @return сумма всех пополнений (DEPOSIT) за указанный тип продукта
     */
    public Double sumDepositsByProductType(String userId, String productType) {
        String sql = """
            SELECT COALESCE(SUM(t.amount), 0)
            FROM transactions t
            INNER JOIN products p ON t.product_id = p.id
            WHERE t.user_id = ?
              AND t.type = 'DEPOSIT'
              AND p.type = ?
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, userId, productType);
    }

    /**
     * Проверяет, есть ли у пользователя какие-либо транзакции по продукту.
     * 
     * Согласно ТЗ: клиент является пользователем какого-то продукта тогда и только тогда,
     * когда в таблице транзакций по этому продукту есть любая транзакция.
     * 
     * @param userId ID пользователя
     * @param productId ID продукта
     * @return true если у пользователя есть хотя бы одна транзакция по продукту
     */
    public boolean hasTransactionsForProduct(String userId, String productId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productId);
        return count != null && count > 0;
    }
}
