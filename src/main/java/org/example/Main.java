package org.example;

public class Main {
    public static void main(String[] args) {

        FilesTxt f1 = new FilesTxt("ex5");
        FilesTxt f2 = new FilesTxt("ex6");
        FilesTxt f3 = new FilesTxt("ex7");
        FilesTxt f4 = new FilesTxt("ex8");
        FilesTxt f5 = new FilesTxt("ex9");
        FilesTxt f6 = new FilesTxt("ex10");
        FilesTxt f7 = new FilesTxt("sp11");
        FilesTxt f8 = new FilesTxt("uk12");
        FilesTxt f9 = new FilesTxt("ex13");
        FilesTxt f10 = new FilesTxt("burma14");
        FilesTxt f11 = new FilesTxt("lau15");
        FilesTxt f12 = new FilesTxt("ulysses16");
        FilesTxt f13 = new FilesTxt("gr17");
        FilesTxt f14 = new FilesTxt("ulysses22");
        FilesTxt f15 = new FilesTxt("gr24");
        FilesTxt f16 = new FilesTxt("fri26");
        FilesTxt f17 = new FilesTxt("dantzig42");
        FilesTxt f18 = new FilesTxt("att48");

        int[][] matrix = f1.populateMatrix();
        f1.printMatrix(matrix);

        f1.populationPaths(matrix);
    }
}