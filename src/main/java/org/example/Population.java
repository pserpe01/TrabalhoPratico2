package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Population {

    private List<PathAndCost> pathsList;
    private Matrix matrix;
    private int size;

    public Population(Matrix matrix) {
        this.matrix = matrix;
        this.size = matrix.getSize();
    }

    //Função que gera números random
    private int randomGenerator(){
        Random random = new Random();

        return random.nextInt(size + 1);
    }

    //Verifica se os valores gerados são iguais
    private void fillArrayWithoutRepetitions(int[] array){
        Set<Integer> uniqueValues = new HashSet<>();
        int size = array.length;

        for(int i = 0; i < size; i++){
            int randomValue;
            do {
                randomValue = randomGenerator();
            } while (!uniqueValues.add(randomValue) || randomValue >= size);

            array[i] = randomValue;
            uniqueValues.add(randomValue);
        }
    }

    public void populationPaths(int totalPopulation){
        pathsList = new ArrayList<>();

        for(int i = 0; i < totalPopulation; i++){
            int[] array = new int[size];
            fillArrayWithoutRepetitions(array);
            PathAndCost obj = new PathAndCost(array, calculatePathCost(array));
            pathsList.add(obj);
        }

        bestTwoPaths(pathsList);

        for (PathAndCost path : pathsList){
            System.out.println(path.toString());
        }

        System.out.println("\nOs dois caminhos com menor custo");

        removeWorstPaths(pathsList);
        for (PathAndCost path : pathsList){
            System.out.println(path.toString());
        }
    }

    private int calculatePathCost(int[] path){
        int pathCost = 0;

        for (int i = 0; i < path.length; i++) {
            int fromNode = path[i];
            int toNode = path[(i + 1) % path.length];

            // Adicione estas verificações
            if (fromNode < 0 || fromNode >= size || toNode < 0 || toNode >= size) {
                System.out.println("Índices inválidos: " + fromNode + ", " + toNode);
                break;  // Adiciona um break para interromper o loop quando ocorre um erro
            }

            pathCost += matrix.getCost(fromNode, toNode);
        }

        return pathCost;
    }

    //Esta função ordena os caminhos gerados
    private void bestTwoPaths(List<PathAndCost> pathList){
        pathList.sort(Comparator.comparingInt(PathAndCost::getCost));
    }

    private void removeWorstPaths(List<PathAndCost> pathList){
        pathList.subList(2, pathList.size()).clear();
    }
}
