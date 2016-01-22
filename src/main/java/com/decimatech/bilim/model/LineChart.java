package com.decimatech.bilim.model;

import java.util.List;

public class LineChart {

    private List<String> labels;
    private List<LineObject> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<LineObject> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<LineObject> datasets) {
        this.datasets = datasets;
    }

    @Override
    public String toString() {
        return "LineChart{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
