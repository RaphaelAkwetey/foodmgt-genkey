package com.genkey.foodmgt.dto;

public class MetaCapDB {

    private double cap;

    public MetaCapDB(double cap) {
        this.cap = cap;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

    public MetaCapDB() {
    }

    @Override
    public String toString() {
        return "MetaCapDB{" +
                "cap=" + cap +
                '}';
    }
}
