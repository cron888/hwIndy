package org.example.recommendation.model;

/**
 * DTO — представление записи из таблицы recommendations.
 * 
 * Используется для чтения данных из БД через JdbcTemplate.
 * Каждая запись содержит:
 * - id: уникальный ID рекомендации
 * - product_id: ID продукта, который рекомендуется (ссылка на таблицу products)
 * - rules: набор правил в текстовом виде (например, "Сумма пополнений по всем продуктам типа DEBIT > 100000")
 * 
 * Не используется для записи в БД — данные в таблице recommendations уже наполнены.
 */
public class Recommendation {
    
    /** Уникальный ID рекомендации (например, "rec-001"). */
    private String id;
    
    /** ID продукта, который рекомендуется (ссылка на products.id). */
    private String productId;
    
    /** Текстовое описание правил рекомендации. */
    private String rules;

    /** Конструктор по умолчанию для JdbcTemplate. */
    public Recommendation() {}

    /**
     * Конструктор с параметрами.
     * 
     * @param id ID рекомендации
     * @param productId ID рекомендуемого продукта
     * @param rules текстовое описание правил
     */
    public Recommendation(String id, String productId, String rules) {
        this.id = id;
        this.productId = productId;
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
