package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PMXCrossover {
    private int[] parent1;
    private int[] parent2;
    private int[] offSpring1;
    private int[] offSpring2;
    private int n;
    private Population population;

    public PMXCrossover(Population population, int n) {
        this.population = population;
        this.offSpring1 = new int[n];
        this.offSpring2 = new int[n];
        this.n = n;
    }

    private void separatePopulation() {
        List<PathAndCost> pathList = population.getPathsList();

        PathAndCost firstPath = pathList.get(0);
        PathAndCost secondPath = pathList.get(1);

        this.parent1 = Arrays.copyOf(firstPath.getPath(), firstPath.getPath().length);
        this.parent2 = Arrays.copyOf(secondPath.getPath(), secondPath.getPath().length);
    }

    public void pmxCrossover() {
        Random rand = new Random();

        int[] replacement1 = new int[this.n+1];
        int[] replacement2 = new int[this.n+1];
        int i, n1, m1, n2, m2;
        int swap;

        separatePopulation();

        for (i = 0; i < this.n; i++) {
            System.out.printf("%2d ",parent1[i]);
        }

        System.out.println();

        for (i = 0; i < this.n; i++) {
            System.out.printf("%2d ",parent2[i]);
        }

        System.out.println();

        int cuttingPoint1 = rand.nextInt(this.n);
        int cuttingPoint2 = rand.nextInt(this.n);

        while (cuttingPoint1 == cuttingPoint2) {
            cuttingPoint2 = rand.nextInt(this.n);
        }

        if (cuttingPoint1 > cuttingPoint2) {
            swap = cuttingPoint1;
            cuttingPoint1 = cuttingPoint2;
            cuttingPoint2 = swap;
        }

        System.out.printf("cp1 = %d cp2 = %d\n",cuttingPoint1,cuttingPoint2);

        for (i = 0; i < this.n + 1; i++) {
            replacement1[i] = -1;
            replacement2[i] = -1;
        }

        for (i=cuttingPoint1; i <= cuttingPoint2; i++) {
            this.offSpring1[i] = this.parent2[i];
            this.offSpring2[i] = this.parent1[i];
            replacement1[this.parent2[i]] = this.parent1[i];
            replacement2[this.parent1[i]] = this.parent2[i];
        }

        for (i = 0; i < this.n + 1; i++) {
            System.out.printf("%2d ",replacement1[i]);
        }

        System.out.println();

        for (i = 0; i < this.n + 1; i++) {
            System.out.printf("%2d ",replacement2[i]);
        }

        System.out.println();

        // fill in remaining slots with replacements
        for (i = 0; i < this.n; i++) {
            if ((i < cuttingPoint1) || (i > cuttingPoint2)) {
                n1 = this.parent1[i];
                m1 = replacement1[n1];
                n2 = this.parent2[i];
                m2 = replacement2[n2];

                while (m1 != -1) {
                    n1 = m1;
                    m1 = replacement1[m1];
                }

                while (m2 != -1) {
                    n2 = m2;
                    m2 = replacement2[m2];
                }

                this.offSpring1[i] = n1;
                this.offSpring2[i] = n2;
            }
        }

        printStuff(this.offSpring1, this.offSpring2);
    }

    private void printStuff(int[] offSpring1, int[] offSpring2) {
        for (int i=0; i< n; i++) {
            System.out.printf("%2d ",offSpring1[i]);
        }

        System.out.println();

        for (int i=0; i< n; i++) {
            System.out.printf("%2d ",offSpring2[i]);
        }
    }
}
