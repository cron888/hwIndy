package org.example.recommendation.model;

public class Recommendation {
    private String id;
    private String productId;
    private String rules;

    public Recommendation() {}

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
