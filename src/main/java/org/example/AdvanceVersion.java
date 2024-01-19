package org.example;

import java.util.List;

public class AdvanceVersion extends Thread {
    private String arquivo;
    private int segundos;
    private int populacaoTotal;
    private double mutacao;
    private int bestPath;

    public AdvanceVersion(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        this.arquivo = arquivo;
        this.segundos = segundos;
        this.populacaoTotal = populacaoTotal;
        this.mutacao = mutacao;
        this.bestPath = 99999;
    }

    @Override
    public void run() {
        int threadBestPath = doJob(this.arquivo, this.segundos, this.populacaoTotal, this.mutacao);

        // Synchronize the update of bestPath to avoid race conditions
        synchronized (this) {
            bestPath = Math.min(bestPath, threadBestPath);
        }
    }

    static int doJob(String arquivo, int segundos, int populacaoTotal, double mutacao) {
        Matrix matrix = new Matrix(arquivo);
        matrix.printMatrix();

        Population population = new Population(matrix);
        System.out.println("\n\nCaminhos Gerados");
        population.populationPaths(populacaoTotal);

        System.out.println("\nPMX");
        PMXCrossover pmx = new PMXCrossover(population, matrix.getSize());

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < segundos * 1000L) { // Convert seconds to milliseconds
            pmx.pmxCrossover();
            System.out.println();
            population.exchangeMutation(mutacao);
            elapsedTime = System.currentTimeMillis() - startTime;
        }

        List<PathAndCost> pathsList = population.getPathsList();
        return pathsList.get(0).getCost();
    }

    public int getBestPath() {
        return bestPath;
    }

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

        // Create an array to store Main instances
        AdvanceVersion[] threads = new AdvanceVersion[Integer.parseInt(args[1])];

        // Create and start threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new AdvanceVersion(arquivo, segundos, populacaoTotal, mutacao);
            threads[i].start();
        }

        // Wait for all threads to complete
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the best path after all threads have completed
        int overallBestPath = Integer.MAX_VALUE;
        for (int i = 0; i < threads.length; i++) {
            overallBestPath = Math.min(overallBestPath, threads[i].getBestPath());
        }

        System.out.println("\nMelhor caminho: " + overallBestPath);



        //Extra extra = new Extra(18);
        //extra.generateProblem();
    }
}
