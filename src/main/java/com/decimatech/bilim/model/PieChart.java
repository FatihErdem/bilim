package com.decimatech.bilim.model;

public class PieChart {
    private String label;
    private Double value;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PieChart{" +
                "label='" + label + '\'' +
                ", value=" + value +
                ", color='" + color + '\'' +
                '}';
    }
}
