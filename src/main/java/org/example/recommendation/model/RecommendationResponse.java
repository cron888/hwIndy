package org.example.recommendation.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO (Data Transfer Object) — объект ответа REST API.
 * 
 * Согласно ТЗ, REST API возвращает JSON следующего формата:
 * {
 *   "user_id": "<uuid пользователя>",
 *   "recommendations": [
 *     {"name": "<имя продукта>", "id": "<id продукта>", "text": "<текстовое описание>"},
 *     ...
 *   ]
 * }
 * 
 * Если рекомендаций нет — массив recommendations пустой [].
 * 
 * Используется автоматически сериализуется в JSON через Jackson.
 */
public class RecommendationResponse {
    
    /** ID пользователя, для которого сформированы рекомендации. */
    private String user_id;
    
    /** Список подходящих рекомендаций. Пустой массив если нет подходящих продуктов. */
    private List<RecommendationItem> recommendations;

    /**
     * Конструктор по умолчанию для JSON-сериализации.
     * Инициализирует пустой список рекомендаций.
     */
    public RecommendationResponse() {
        this.recommendations = new ArrayList<>();
    }

    /**
     * Конструктор с параметрами.
     * 
     * @param user_id ID пользователя
     * @param recommendations список рекомендаций
     */
    public RecommendationResponse(String user_id, List<RecommendationItem> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<RecommendationItem> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationItem> recommendations) {
        this.recommendations = recommendations;
    }

    /**
     * Вложенный класс — элемент списка рекомендаций.
     * Описывает один рекомендуемый продукт с именем, ID и текстовым описанием.
     */
    public static class RecommendationItem {
        
        /** Название продукта (например, "Дебетовая карта"). */
        private String name;
        
        /** ID продукта из таблицы products (например, "prod-001"). */
        private String id;
        
        /** Текстовое описание продукта для отображения пользователю. */
        private String text;

        /** Конструктор по умолчанию для JSON-сериализации. */
        public RecommendationItem() {}

        /**
         * Конструктор с параметрами.
         * 
         * @param name название продукта
         * @param id ID продукта
         * @param text текстовое описание
         */
        public RecommendationItem(String name, String id, String text) {
            this.name = name;
            this.id = id;
            this.text = text;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
