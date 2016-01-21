package com.decimatech.bilim.model;

import java.util.List;

public class BarObject {
    private String label;
    public String fillColor;
    public String strokeColor;
    public String highlightFill;
    public String highlightStroke;
    private List<String> data;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public BarObject() {
        this.fillColor = "rgba(151,187,205,0.5)";
        this.strokeColor = "rgba(151,187,205,0.8)";
        this.highlightFill = "rgba(151,187,205,0.75)";
        this.highlightStroke = "rgba(151,187,205,1)";
    }

    @Override
    public String toString() {
        return "BarObject{" +
                "label='" + label + '\'' +
                ", fillColor='" + fillColor + '\'' +
                ", strokeColor='" + strokeColor + '\'' +
                ", highlightFill='" + highlightFill + '\'' +
                ", highlightStroke='" + highlightStroke + '\'' +
                ", data=" + data +
                '}';
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getHighlightFill() {
        return highlightFill;
    }

    public void setHighlightFill(String highlightFill) {
        this.highlightFill = highlightFill;
    }

    public String getHighlightStroke() {
        return highlightStroke;
    }

    public void setHighlightStroke(String highlightStroke) {
        this.highlightStroke = highlightStroke;
    }
}
