package org.example;

import java.io.*;
import java.util.Scanner;

public class Matrix {
    private String nomeFicheiro;
    private String caminhoFicheiro;
    private int[][] matrix;
    private int size;

    public Matrix(String nomeFicheiro) {
        this.caminhoFicheiro = "..\\TrabalhoPratico2\\testes";
        this.nomeFicheiro = nomeFicheiro;
        this.size = matrixSize();
        populateMatrix();
    }

    public int getSize() {
        return size;
    }

    //Função que vai construir a matriz
    private void populateMatrix(){
        matrix = new int[0][0];

        try {
            File file = new File(caminhoFicheiro + "\\" + nomeFicheiro);
            Scanner scanner = new Scanner(file);

            scanner.nextLine();

            int rows = matrixSize();
            int columns = matrixSize();
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
    public int matrixSize(){
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

    public void initializeMatrix(int rows, int colums) {
        matrix = new int[rows][colums];
    }

    public int getCost(int fromNode, int toNode){
        if(matrix == null || fromNode < 0 || fromNode >= matrix.length || toNode < 0 || toNode >= matrix[0].length){
            System.out.println("Matriz não inicializada ou índices inválidos");
            return 0;
        }

        return matrix[fromNode][toNode];
    }

    //Função para dar print da matriz
    public void printMatrix(){
        if(matrix == null){
            System.out.println("Matriz não inicializada");
            return;
        }

        System.out.println("Matriz");
        System.out.println("+---------------------------------------+");

        System.out.println();
        for (int[] row : matrix) {
            System.out.print("| ");
            for (int value : row) {
                System.out.printf("%4d ", value);
            }
            System.out.println("|");
        }

        System.out.println("+---------------------------------------+");
    }


}
