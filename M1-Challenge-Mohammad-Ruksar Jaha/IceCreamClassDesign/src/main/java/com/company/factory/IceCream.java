package com.company.factory;

public class IceCream {

    // **public String flavor;

    public double salePrice;

    public double productionCost;

    public String ingredients;

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "IceCream{" +
                "salePrice=" + salePrice +
                ", productionCost=" + productionCost +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }


}
