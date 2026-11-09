package org.example.recommendation.model;

public class RecommendationResponse {
    private String user_id;
    private java.util.List<RecommendationItem> recommendations;

    public RecommendationResponse() {
        this.recommendations = new java.util.ArrayList<>();
    }

    public RecommendationResponse(String user_id, java.util.List<RecommendationItem> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public java.util.List<RecommendationItem> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(java.util.List<RecommendationItem> recommendations) {
        this.recommendations = recommendations;
    }

    public static class RecommendationItem {
        private String name;
        private String id;
        private String text;

        public RecommendationItem() {}

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
