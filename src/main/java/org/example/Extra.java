package org.example;

import java.io.*;
import java.util.Random;

public class Extra {
    private int[] excludeNumbers = { 22, 24, 26, 42, 48 };
    private int numberOfCities;
    private int seed;

    public Extra(int seed) {
        this.seed = seed;
    }

    private Random initializeRandom() {
        return new Random(this.seed);
    }

    private int generateNumberOfCities() {
        Random random = initializeRandom();
        int number;
        boolean exists = false;

        do {
            number = random.nextInt(50 - 18) + 18;

            for(int exclude : this.excludeNumbers) {
                if(number == exclude) {
                    exists = true;
                    break;
                }
            }
        }
        while(!exists);

        return number;
    }
    
    public void generateProblem() {
        this.numberOfCities = generateNumberOfCities();

        try {
            Random random = initializeRandom();
            PrintWriter writer = new PrintWriter(new FileWriter("ex_gau" + this.numberOfCities + ".txt"));

            writer.println(this.numberOfCities);

            //Geração de coordernadas
            int[][] coordenadas = new int[this.numberOfCities][2];
            for(int i = 0; i < this.numberOfCities; i++) {
                coordenadas[i][0] = nextGaussianInt(random, 60, 30); //Coordenada x
                coordenadas[i][1] = nextGaussianInt(random, 60, 30); //Coordenada y
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
    
    private int nextGaussianInt(Random random, int media, int standardDeviation) {
        return (int) Math.round(random.nextGaussian() * standardDeviation + media);
    }
}
