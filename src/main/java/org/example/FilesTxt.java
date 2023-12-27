package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class FilesTxt {
    private String nomeFicheiro;
    private String caminhoFicheiro;

    public FilesTxt(String nomeFicheiro) {
        this.caminhoFicheiro = "..\\TrabalhoPratico2\\testes";
        this.nomeFicheiro = nomeFicheiro;
    }

    public int[][] populateMatrix(){
        int[][] matrix = new int[0][0];

        try {
            File file = new File(caminhoFicheiro + "\\" + nomeFicheiro + ".txt");
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

            /*
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }*/
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado");
        }
        return matrix;
    }

    private int firstValue(){
        int first = -1;

        try {
            File file = new File(caminhoFicheiro + "\\" + nomeFicheiro + ".txt");
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

    public void printMatrix(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int randomGenerator(){
        Random random = new Random();
        int size = firstValue();

        return random.nextInt(0, size);
    }

    public void populationPaths(int [][] matrix){
        List<int[]> listOfPaths = new ArrayList<>();

        for(int i = 0; i < firstValue(); i++){
            int[] array = new int[firstValue()];
            Arrays.fill(array, randomGenerator());
            listOfPaths.add(array);
        }

        for(int[] array : listOfPaths) {
            System.out.println(Arrays.toString(array));
        }
    }


}
