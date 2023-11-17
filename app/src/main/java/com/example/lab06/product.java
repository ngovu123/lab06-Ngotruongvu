package com.example.lab06;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
public class product {
    private String name;
    private String ID;
    private double price;

    public product() {}

    public product(String ID, String name, double Price) {
        this.name = name;
        this.ID = ID;
        this.price = Price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = Price;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

