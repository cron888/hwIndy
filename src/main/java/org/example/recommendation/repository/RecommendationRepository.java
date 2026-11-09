package org.example.recommendation.repository;

import org.example.recommendation.model.Product;
import org.example.recommendation.model.Recommendation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
     * Calculates the sum of deposits for a specific user across all products of a given type.
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
     * Checks if a user has any transactions for a specific product.
     */
    public boolean hasTransactionsForProduct(String userId, String productId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productId);
        return count != null && count > 0;
    }
}
