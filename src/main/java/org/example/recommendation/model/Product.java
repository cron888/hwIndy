package org.example.recommendation.model;

/**
 * DTO — представление записи из таблицы products.
 * 
 * Используется для чтения данных из БД через JdbcTemplate.
 * Каждая запись содержит:
 * - id: уникальный ID продукта
 * - name: название продукта (например, "Дебетовая карта")
 * - type: тип продукта (DEBIT, DEPOSIT, CREDIT, MORTGAGE)
 * - text: текстовое описание продукта для отображения пользователю
 * 
 * Поле type используется в SQL-запросах для фильтрации продуктов по типу
 * при вычислении сумм пополнений (например, "Сумма пополнений по всем продуктам типа DEBIT").
 */
public class Product {
    
    /** Уникальный ID продукта (например, "prod-001"). */
    private String id;
    
    /** Название продукта, отображаемое пользователю. */
    private String name;
    
    /** Тип продукта: DEBIT (дебетовые), DEPOSIT (вклады), CREDIT (кредитные), MORTGAGE (ипотека). */
    private String type;
    
    /** Текстовое описание продукта для рекомендации. */
    private String text;

    /** Конструктор по умолчанию для JdbcTemplate. */
    public Product() {}

    /**
     * Конструктор с параметрами.
     * 
     * @param id ID продукта
     * @param name название продукта
     * @param type тип продукта (DEBIT, DEPOSIT, CREDIT, MORTGAGE)
     * @param text текстовое описание
     */
    public Product(String id, String name, String type, String text) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
