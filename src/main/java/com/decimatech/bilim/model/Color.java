package com.decimatech.bilim.model;

import java.util.Arrays;
import java.util.List;

public class Color {

    private List<String> colors = Arrays.asList("#815e77", "#CACAAA", "#e4a972", "#f1b6c5", "#b0d8de", "#7D8CC4", "#4B3B40", "#FFC09F", "#191D32", "#9adbc7", "#a5a696");

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
