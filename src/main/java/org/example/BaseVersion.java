package org.example;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseVersion extends Thread {
    private String arquivo;
    private int segundos;
    private int populacaoTotal;
    private double mutacao;
    private int bestCost;
    private int[] bestPath;

    public BaseVersion(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        this.arquivo = arquivo;
        this.segundos = segundos;
        this.populacaoTotal = populacaoTotal;
        this.mutacao = mutacao;
        this.bestCost = 99999;
    }

    static int doJobCost(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        Matrix matrix = new Matrix(arquivo);

        Population population = new Population(matrix);
        population.populationPaths(populacaoTotal);

        PMXCrossover pmx = new PMXCrossover(population, matrix.getSize());

        long startTime = System.currentTimeMillis();

        long elapsedTime = 0;

        while (elapsedTime < segundos * 1000L) { // Convert seconds to milliseconds
            long elapsedSeconds = elapsedTime;
            System.out.println("Elapsed Time: " + elapsedSeconds + " seconds");
            pmx.pmxCrossover();
            population.exchangeMutation(mutacao);
            elapsedTime = System.currentTimeMillis() - startTime;
        }

        List<PathAndCost> pathsList = population.getPathsList();
        return pathsList.get(0).getCost();
    }

    static int[] doJobPath(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        Matrix matrix = new Matrix(arquivo);

        Population population = new Population(matrix);
        population.populationPaths(populacaoTotal);

        PMXCrossover pmx = new PMXCrossover(population, matrix.getSize());

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < segundos * 1000L) { // Convert seconds to milliseconds
            long elapsedSeconds = elapsedTime;
            System.out.println("Elapsed Time: " + elapsedSeconds + " seconds");
            pmx.pmxCrossover();
            population.exchangeMutation(mutacao);
            elapsedTime = System.currentTimeMillis() - startTime;
        }

        List<PathAndCost> pathsList = population.getPathsList();
        return pathsList.get(0).getPath();
    }

    public int getBestCost() {
        return bestCost;
    }
    public int[] getBestPath() {
        return bestPath;
    }

    private static CountDownLatch latch;

    public static void main(String[] args) {
        if(args.length < 5){
            System.out.println("Uso: java -jar tps2.jar arquivo.txt 10 60 80 0.01");
            System.exit(1);
        }

        String arquivo = args[0];
        int Numerothreads = Integer.parseInt(args[1]);
        int segundos = Integer.parseInt(args[2]);
        int populacaoTotal = Integer.parseInt(args[3]);
        double mutacao = Double.parseDouble(args[4]);

        //Args inseridos
        System.out.println("Ficheiro: " + arquivo);
        System.out.println("Nº de Threads: " + Numerothreads);
        System.out.println("Nº de Segundos: " + segundos);
        System.out.println("Nº da População: " + populacaoTotal);
        System.out.println("Percentagem da chance de acontecer a mutação: " + mutacao);
        System.out.println("\nMatrix do " + arquivo);

        // Crie um CountDownLatch com o número de threads
        latch = new CountDownLatch(Numerothreads);

        // Crie um array para armazenar as instâncias de threads
        BaseVersion[] threads = new BaseVersion[Numerothreads];

        // Create and start threads
        for (int i = 0; i < Numerothreads; i++) {
            threads[i] = new BaseVersion(arquivo, segundos, populacaoTotal, mutacao);
            threads[i].start();
        }

        long startTime = System.currentTimeMillis();

        // Aguarde até que todas as threads tenham concluído
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int overallBestTotal = Integer.MAX_VALUE;
        int[] overallBestPath = null;

        for (int i = 0; i < threads.length; i++) {
            overallBestTotal = Math.min(overallBestTotal, threads[i].getBestCost());
            overallBestPath = threads[i].getBestPath();
        }

        long overallExecutionTime = System.currentTimeMillis() - startTime; // Calcula o tempo de execução total

        System.out.println("\n\nFicheiro: " + arquivo);
        System.out.println("Nº de Threads: " + Numerothreads);
        System.out.println("Tempo de execução: " + segundos);
        System.out.println("Melhor caminho: " + overallBestTotal);
        System.out.println("Percentagem da chance de acontecer a mutação: " + mutacao);
        System.out.println("Caminho: " + java.util.Arrays.toString(overallBestPath));

    }

    @Override
    public void run() {

        int threadBestCost = doJobCost(this.arquivo, this.segundos, this.populacaoTotal, this.mutacao);
        int[] threadBestPath = doJobPath(this.arquivo, this.segundos, this.populacaoTotal, this.mutacao);

        // Synchronize the update of bestPath to avoid race conditions
        synchronized (this) {
            bestCost = Math.min(bestCost, threadBestCost);
            bestPath = threadBestPath;
        }

        // Sinalize que esta thread concluiu
        latch.countDown();
    }
}