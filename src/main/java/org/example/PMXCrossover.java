package org.example;

import java.util.List;
import java.util.Random;

public class PMXCrossover {
    private Population population;
    private Matrix matrix;

    public PMXCrossover(Population population, Matrix matrix) {
        this.population = population;
        this.matrix = matrix;
    }

    private int randomGenerator(){
        Random random = new Random();

        return random.nextInt(matrix.getSize());
    }

    public void pmxCrossover(){
        List<PathAndCost> pathList = population.getPathsList();
        int[] cpy1, cpy2;
        int[] aux1 = new int[matrix.getSize()];
        int[] aux2 = new int[matrix.getSize()];
        int[] replacement1 = new int[matrix.getSize()];
        int[] replacement2 = new int[matrix.getSize()];
        int swap, cuttingPoint1, cuttingPoint2, n1, m1, n2, m2;

        cuttingPoint1 = randomGenerator();
        cuttingPoint2 = randomGenerator();

        while(cuttingPoint1 == cuttingPoint2){
            cuttingPoint2 = randomGenerator();
        }

        if(cuttingPoint1 > cuttingPoint2) {
            swap = cuttingPoint1;
            cuttingPoint1 = cuttingPoint2;
            cuttingPoint2 = swap;
        }

        System.out.printf("CP1-> %d CP2-> %d\n", cuttingPoint1, cuttingPoint2);

        for(int i = 0; i < matrix.getSize(); i++){
            replacement1[i] = -1;
            replacement2[i] = -1;
        }

        if(!pathList.isEmpty()){
            PathAndCost firstPath = pathList.get(0);
            PathAndCost secondPath = pathList.get(1);
            cpy1 = firstPath.getPath();
            cpy2 = secondPath.getPath();

            System.out.println();

            for(int i = cuttingPoint1; i <= cuttingPoint2; i++){
                aux1[i] = cpy1[i];
                aux2[i] = cpy2[i];
                replacement1[cpy2[i]] = cpy1[i];
                replacement2[cpy1[i]] = cpy2[i];
            }

            System.out.print("Replacement 1 -> ");
            for(int i = 0; i < matrix.getSize(); i++){
                System.out.printf("%2d", replacement1[i]);
            }
            System.out.println();

            System.out.print("Replacement 2 -> ");
            for(int i = 0; i < matrix.getSize(); i++){
                System.out.printf("%2d", replacement2[i]);
            }
            System.out.println();

            //Preenchimento do resto dos slots dos arrays
            for(int i = 0; i < matrix.getSize(); i++){
                if((i < cuttingPoint1) || (i > cuttingPoint2)) {
                    n1 = cpy1[i];
                    m1 = replacement1[n1];
                    n2 = cpy2[i];
                    m2 = replacement1[n2];
                    while (m1 != -1) {
                        n1 = m1;
                        m1 = replacement1[m1];
                    }
                    while (m2 != -1) {
                        n2 = m2;
                        m2 = replacement2[m2];
                    }
                    aux1[i] = n1;
                    aux2[i] = n2;
                }
            }
        } else {
            System.out.println("A lista está vazia");
        }

        for (int i = 0; i < matrix.getSize(); i++){
            System.out.printf("%2d ", aux1[i]);
        }
        System.out.println();
        for (int i = 0; i < matrix.getSize(); i++){
            System.out.printf("%2d ", aux2[i]);
        }
        System.out.println();
    }
}
