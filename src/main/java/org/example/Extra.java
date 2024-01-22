package org.example;

import java.io.*;
import java.util.Random;

public class Extra {
    private int[] excludeNumbers = { 22, 24, 26, 42, 48 };
    private int numberOfCities;
    private int seed;

    public Extra(int seed, int numberOfCities) {
        this.numberOfCities = numberOfCities;
        this.seed = seed;
    }

    private Random initializeRandom() {
        return new Random(this.seed);
    }
    
    public void generateProblem() {
        for(int num : this.excludeNumbers){
            if(this.numberOfCities == num){
                System.out.println("Numero de cidades já existe.");
                return;
            }
        }

        try {
            Random random = initializeRandom();
            PrintWriter writer = new PrintWriter(new FileWriter("..\\TrabalhoPratico2\\testes\\ex_gau" + this.numberOfCities + ".txt"));

            writer.println(this.numberOfCities);

            //Geração de coordernadas
            int[][] coordenadas = new int[this.numberOfCities][2];
            for(int i = 0; i < this.numberOfCities; i++) {
                coordenadas[i][0] = (int) random.nextGaussian(60, 30);
                coordenadas[i][1] = (int) random.nextGaussian(60, 30);
            }

            //Cálculo da distância e preenchimento do ficheiro
            for(int i = 0; i < this.numberOfCities; i++) {
                for(int j = 0; j < this.numberOfCities; j++) {
                    double distance = calculateDistance(coordenadas[i][0], coordenadas[i][1], coordenadas[j][0], coordenadas[j][1]);
                    writer.print((int)distance + " ");

                    // Adiciona uma quebra de linha ao final de cada linha
                    if (j == this.numberOfCities - 1) {
                        writer.println();
                    }
                }
            }

            writer.close();
            System.out.println("Problema gerado com sucesso para n = " + this.numberOfCities + "\n\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static void main(String[] args) {
        Extra extra = new Extra(18, 18);
        extra.generateProblem();
    }
}


