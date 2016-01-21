package com.decimatech.bilim.model;

public class PieChart {
    private String label;
    private Integer value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PieChart{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}
