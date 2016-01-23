package com.decimatech.bilim.model;

import java.util.Arrays;
import java.util.List;

public class Color {

    private List<String> colors = Arrays.asList("#815e77", "#e4a972", "#f1b6c5", "#b0d8de", "#9adbc7", "#a5a696");

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
