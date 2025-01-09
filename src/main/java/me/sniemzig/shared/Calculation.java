package me.sniemzig.shared;

import org.springframework.data.mongodb.core.mapping.MongoId;

public class Calculation {
    @MongoId
    private String id;
    private CalculationParams request;
    private double premium;

    public Calculation() {}

    public Calculation(CalculationParams request, double premium) {
        this.request = request;
        this.premium = premium;
    }

    public String getId() {
        return id;
    }

    public void setRequest(CalculationParams request) {
        this.request = request;
    }

    public CalculationParams getRequest() {
        return request;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public double getPremium() {
        return premium;
    }

}
