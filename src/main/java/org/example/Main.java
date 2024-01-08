package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if(args.length < 5){
            System.out.println("Uso: java -jar tps2.jar arquivo.txt 10 60 80 0.01");
            System.exit(1);
        }

        String arquivo = args[0];
        int threads = Integer.parseInt(args[1]);
        int segundos = Integer.parseInt(args[2]);
        int populacaoCaminhos = Integer.parseInt(args[3]);
        double mutacao = Double.parseDouble(args[4]);

        //Args inseridos
        System.out.println("Ficheiro: " + arquivo);
        System.out.println("Nº de Threads: " + threads);
        System.out.println("Nº de Segundos: " + segundos);
        System.out.println("Nº da População: " + populacaoCaminhos);
        System.out.println("Percentagem da chance de acontecer a mutação: " + mutacao);
        System.out.println("\nMatrix do " + arquivo);

        FilesTxt f1 = new FilesTxt(arquivo);

        f1.populateMatrix();
        f1.printMatrix();

        System.out.println("\n\nCaminhos Gerados");
        f1.populationPaths();
        f1.choosePaths();
    }
}