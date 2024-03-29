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
        this.pathsList = new ArrayList<>();
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
        for(int i = 0; i < totalPopulation; i++){
            int[] array = new int[size];
            fillArrayWithoutRepetitions(array);
            PathAndCost obj = new PathAndCost(array, calculatePathCost(array));
            pathsList.add(obj);
        }

        bestTwoPaths(pathsList);
    }

    public void exchangeMutation(double mutationProbability) {
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            PathAndCost originalPath = pathsList.get(i);

            // Cria uma cópia do caminho original para não modificar o original
            int[] originalArray = originalPath.getPath().clone();

            // Cria um novo caminho mutado com base no original
            int[] mutatedPath = createMutatedPath(originalArray, random, mutationProbability);

            // Adiciona o novo caminho à lista
            pathsList.add(new PathAndCost(mutatedPath, calculatePathCost(mutatedPath)));
        }

        removeTwoHighestCostPaths();
        bestTwoPaths(pathsList);
    }

    private int[] createMutatedPath(int[] path, Random random, double mutationProbability) {
        // Verifica a probabilidade de mutação
        if (random.nextDouble() <= mutationProbability) {
            int[] newPath = path.clone(); // Clona o caminho para não modificar o original

            // Escolhe duas posições aleatórias no caminho
            int position1 = random.nextInt(size);
            int position2 = random.nextInt(size);

            // Garante que as posições escolhidas são diferentes
            while (position1 == position2) {
                position2 = random.nextInt(size);
            }

            // Troca os elementos nas posições escolhidas
            int temp = newPath[position1];
            newPath[position1] = newPath[position2];
            newPath[position2] = temp;

            return newPath;
        }
        else {
            // Se não houver mutação, retorna o caminho original
            return path.clone();
        }
    }

    private int calculatePathCost(int[] path) {
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

    private void removeTwoHighestCostPaths() {
        PathAndCost highestCostPath1 = null;
        PathAndCost highestCostPath2 = null;

        for (PathAndCost path : pathsList) {
            if (highestCostPath1 == null || path.getCost() > highestCostPath1.getCost()) {
                highestCostPath2 = highestCostPath1;
                highestCostPath1 = path;
            } else if (highestCostPath2 == null || path.getCost() > highestCostPath2.getCost()) {
                highestCostPath2 = path;
            }
        }

        pathsList.remove(highestCostPath1);
        pathsList.remove(highestCostPath2);
    }

    public List<PathAndCost> getPathsList() {
        return pathsList;
    }
}