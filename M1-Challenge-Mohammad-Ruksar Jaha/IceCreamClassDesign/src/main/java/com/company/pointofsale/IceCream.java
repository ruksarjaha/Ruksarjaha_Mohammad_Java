package com.company.pointofsale;

public class IceCream {

    public String flavor;

    public int quantity;

    public double price;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "IceCream{" +
                "flavor='" + flavor + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';

    }

    public void totalAmount(){


        if(this.flavor == "vanilla"){

            this.price = 2.00;

        } else if (this.flavor == "Almond"){

            this.price = 4.00;

        } else if (this.flavor == "cashew"){

            this.price = 3.00;
        }
        else {
            this.price = 1.00;
        }

    }

}
