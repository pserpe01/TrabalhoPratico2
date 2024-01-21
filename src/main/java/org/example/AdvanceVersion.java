package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AdvanceVersion extends Thread {
    private String arquivo;
    private int segundos;
    private int populacaoTotal;
    private double mutacao;
    private int bestCost;
    private int[] bestPath;

    public AdvanceVersion(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        this.arquivo = arquivo;
        this.segundos = segundos;
        this.populacaoTotal = populacaoTotal;
        this.mutacao = mutacao;
        this.bestCost = 99999;
    }

    @Override
    public void run() {
        PathAndCost bestOne = doJobCost(this.arquivo, this.segundos, this.populacaoTotal, this.mutacao);

        synchronized (this) {
            this.bestCost = Math.min(bestCost, bestOne.getCost());
            this.bestPath = bestOne.getPath();
        }

        latch.countDown();
    }

    static PathAndCost doJobCost(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        Matrix matrix = new Matrix(arquivo);

        Population population = new Population(matrix);
        population.populationPaths(populacaoTotal);

        PMXCrossover pmx = new PMXCrossover(population, matrix.getSize());

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < segundos * 1000L) {
            System.out.println("Tempo: " + ((elapsedTime / 1000) + 1) + " seconds");
            pmx.pmxCrossover();
            population.exchangeMutation(mutacao);
            elapsedTime = System.currentTimeMillis() - startTime;
        }

        List<PathAndCost> pathsList = population.getPathsList();

        return new PathAndCost(pathsList.get(0).getPath(), pathsList.get(0).getCost()) ;
    }

    private static CountDownLatch latch;

    public int getBestCost() {
        return bestCost;
    }
    public int[] getBestPath() {
        return bestPath;
    }


    public static void main(String[] args) {
        if(args.length < 5) {
            System.out.println("Uso: java -jar tps2.jar arquivo.txt 10 60 80 0.01");
            System.exit(1);
        }

        String arquivo = args[0];
        int Numerothreads = Integer.parseInt(args[1]);
        int segundos = Integer.parseInt(args[2]);
        int populacaoTotal = Integer.parseInt(args[3]);
        double mutacao = Double.parseDouble(args[4]);

        AdvanceVersion threads = null;
        latch = new CountDownLatch(Numerothreads);

        for (int i = 0; i < Numerothreads; i++) {
            threads = new AdvanceVersion(arquivo, segundos, populacaoTotal, mutacao);
            threads.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFicheiro: " + arquivo);
        System.out.println("Nº de Threads: " + Numerothreads);
        System.out.println("Tempo de execução: " + segundos);
        System.out.println("Melhor caminho: " + threads.getBestCost());
        System.out.println("Percentagem da chance de acontecer a mutação: " + mutacao);
        System.out.println("Caminho: " + Arrays.toString(threads.getBestPath()));

    }
}