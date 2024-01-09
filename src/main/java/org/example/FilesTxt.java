package org.example;

import javax.naming.InterruptedNamingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class FilesTxt implements Comparable<FilesTxt>{
    private String nomeFicheiro;
    private String caminhoFicheiro;
    private int[][] matrix;
    private List<int[]> listOfPaths;
    private List<int[]> bestPaths;

    public FilesTxt(String nomeFicheiro) {
        this.caminhoFicheiro = "..\\TrabalhoPratico2\\testes";
        this.nomeFicheiro = nomeFicheiro;
    }

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

    private void underScore(){
        for (int i = 0; i < (firstValue() * 3 - 1); i++){
            System.out.print("_");
        }
    }

    private void ifen(){
        for (int i = 0; i < (firstValue() * 3 - 1); i++){
            System.out.print("-");
        }
    }

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
        listOfPaths = new ArrayList<>();

        for(int i = 0; i < totalPopulation; i++){
            int[] array = new int[totalPopulation];
            fillArrayWithoutRepetitions(array);
            listOfPaths.add(array);
        }
    }

    /*
    private void sortListOfPaths(List<int[]> listOfPaths ){
        List<int[]> aux = new ArrayList<>();
        int costAux = 0;

        for (int[] path : listOfPaths) {
            int cost = calculatePathCost(path);
        }
    }*/

    public void choosePaths(){
        int bestTotal1 = 0, bestTotal2 = 0, pathCost;

        if(listOfPaths == null){
            System.out.println("A lista dos caminhos está vazia");
            return;
        }

        for(int[] path : listOfPaths){
            pathCost = calculatePathCost(path);

            System.out.println(Arrays.toString(path) + " Custo Total -> " + pathCost);
        }

        Collections.sort(listOfPaths, (path1, path2) -> Integer.compare(calculatePathCost(path1), calculatePathCost(path2)));
        
        listOfPaths.subList(2, listOfPaths.size()).clear();

        for (int[] array : listOfPaths){
            System.out.println(Arrays.toString(array) + ", Custo: " + calculatePathCost(array));
        }


    }

    private int calculatePathCost(int[] path){
        int pathCost = 0;

        for (int i = 0; i < path.length; i++) {
            int fromNode = path[i];
            int toNode = path[(i + 1) % path.length];

            //System.out.println("fromNode: " + fromNode + ", toNode: " + toNode + ", Valor: " + matrix[fromNode][toNode]);

            // Adicione estas verificações
            if (fromNode < 0 || fromNode >= matrix.length || toNode < 0 || toNode >= matrix.length) {
                System.out.println("Índices inválidos: " + fromNode + ", " + toNode);
                break;  // Adiciona um break para interromper o loop quando ocorre um erro
            }

            pathCost += matrix[fromNode][toNode];
        }

        return pathCost;
    }

    @Override
    public int compareTo(FilesTxt other){
        return Integer.compare(this.calculatePathCost(this.listOfPaths.get(0)), other.calculatePathCost(other.listOfPaths.get(0)));
    }
}
