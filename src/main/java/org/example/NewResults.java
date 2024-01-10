package org.example;

import java.util.Arrays;
import java.util.Random;

public class NewResults {
    private int[] forbiddenNumbers = {22, 24, 26, 42, 48};
    private int size;
    private int[][] array;

    public NewResults() {
        this.size = randomMatrixSize();
        this.array = new int[this.size][this.size];
    }

    // Cria um tamanho para a matriz
    private int randomMatrixSize() {
        Random random = new Random();
        int number;
        boolean exist = false;

        do {
            number = random.nextInt(50 - 18) + 18;

            for (int match : this.forbiddenNumbers) {
                if(number == match) {
                    exist = true;
                    break;
                }
            }
        }
        while(exist);

       return number;
    }

    // Insere números criados aleatóriamente na matriz
    public void populateMatrix() {
        Random value = new Random();
        System.out.println(" " + this.size);
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array.length; j++) {
                if(i == j ) {
                    array[i][j] = 0;
                }
                else {
                    array[i][j] = value.nextInt((this.size * 2) - 1) + 1;
                }
            }
        }

        for (int[] row : array) {
            System.out.print("| ");
            for (int dass : row) {
                System.out.printf("%4d ", dass);
            }
            System.out.println("|");
        }
    }
}
