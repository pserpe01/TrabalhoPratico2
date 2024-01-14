package org.example;

import java.util.Arrays;

public class PathAndCost {
    private int[] path;
    private int cost;

    public PathAndCost(int[] path, int cost){
        this.path = path;
        this.cost = cost;
    }

    public int[] getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PathAndCost{ " +
                "path = " + Arrays.toString(path) +
                ", cost = " + cost +
                " }";
    }
}
