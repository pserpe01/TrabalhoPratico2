package org.example;

import javax.naming.InterruptedNamingException;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class StarterVersion {
    private String nomeFicheiro;
    private String caminhoFicheiro;
    private int[][] matrix;
    private List<PathAndCost> pathsList;

    public StarterVersion(String nomeFicheiro) {
        this.caminhoFicheiro = "..\\TrabalhoPratico2\\testes";
        this.nomeFicheiro = nomeFicheiro;
    }

    //Função que vai construir a matriz
    public void populateMatrix(){
        matrix = new int[0][0];

        try {
            File file = new File(caminhoFicheiro + "\\" + nomeFicheiro);
            Scanner scanner = new Scanner(file);

            scanner.nextLine();

            int rows = firstValue();
            int columns = firstValue();
            matrix = new int[rows][columns];

            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix.length; j++){
                    matrix[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado");
        }
    }

    //Função para ir buscar o número referente ao tamanho da matriz
    private int firstValue(){
        int first = -1;

        try {
            File file = new File(caminhoFicheiro + "\\" + nomeFicheiro);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String firstRow = br.readLine();
            if(firstRow != null){
                String[] values = firstRow.split(" ");
                if (values.length > 0){
                    first = Integer.parseInt(values[0]);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return first;
    }

    //Função para dar print da matriz
    public void printMatrix(){
        underScore();
        System.out.println();
        for (int[] ints : matrix) {
            System.out.print("|");
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println("|");
        }
        ifen();
    }

    //Função para complementar o print da matriz
    private void underScore(){
        for (int i = 0; i < (firstValue() * 3 - 1); i++){
            System.out.print("_");
        }
    }

    //Função para complementar o print da matriz
    private void ifen(){
        for (int i = 0; i < (firstValue() * 3 - 1); i++){
            System.out.print("-");
        }
    }

    //Função que gera números random
    private int randomGenerator(){
        Random random = new Random();
        int size = firstValue();

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
            int[] array = new int[totalPopulation];
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
            if (fromNode < 0 || fromNode >= matrix.length || toNode < 0 || toNode >= matrix.length) {
                System.out.println("Índices inválidos: " + fromNode + ", " + toNode);
                break;  // Adiciona um break para interromper o loop quando ocorre um erro
            }

            pathCost += matrix[fromNode][toNode];
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
