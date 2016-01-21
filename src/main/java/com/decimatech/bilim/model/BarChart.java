package com.decimatech.bilim.model;

import java.util.List;

public class BarChart {

    private List<String> labels;
    private List<BarObject> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<BarObject> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<BarObject> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "BarChart{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
