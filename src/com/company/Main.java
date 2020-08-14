package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Duota N daiktų, kurių svoriai s 1 , s 2 , ... s N , o kainos k 1 , k 2 , ... k N.
 * Programa turi sudaryti daiktų rinkinį, kurio kaina būtų maksimali,
 * o svoris neviršytų nurodyto svorio S.
 * Vartotojas nurodo failą, iš kurio programa įveda daiktų svorius ir kainas, bei svorį S.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String fileName = Ask("Input name of file: ");
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();

        if(n > 0) {
            int[] weight = new int[n];
            int[] price = new int[n];
            readData(weight, price, n, scanner);
            int S = scanner.nextInt();
            //getHighestPricePossible(S, weight, price, n);
            System.out.println("----------------------------");
            System.out.println(doItAgain(S, weight, price, n));
        }
    }
    static int doItAgain(int S, int[] weight, int[] price, int n)
    {

        if (n == 0 || S == 0)
            return 0;

        if (weight[n-1] > S) {
            return doItAgain(S, weight, price, n-1);
        } else {
            return Math.max(price[n - 1] + doItAgain(S - weight[n - 1], weight, price, n - 1),
                    doItAgain(S, weight, price, n - 1));
        }
    }

    static void getHighestPricePossible(int S, int[] weight, int[] price, int n) {

        int[][] K = new int[n+1][S+1];

        for(int i = 0; i <= n; i++) {
            for(int k = 0; k <= S; k++) {
                if(i == 0 || k == 0) {
                    K[i][k] = 0;
                } else if(weight[i-1] <= k) {
                    K[i][k] = Math.max(price[i-1] + K[i-1][k-weight[i-1]], K[i-1][k]);
                } else {
                    K[i][k] = K[i-1][k];
                }
            }
        }

        int res = K[n][S];
        System.out.println("Highest price possible: " + res);

        for(int i = n; i > 0 && res > 0; i--) {
            if(res != K[i-1][S]) {
                System.out.println(weight[i-1] + " " + price[i-1]);

                res = res - price[i-1];
                S = S - weight[i-1];
            }
        }
    }

    static void readData(int[] weight, int[] price, int n, Scanner scanner) {
        for(int i=0; i < n; i++) {
            weight[i] = scanner.nextInt();
            price[i] = scanner.nextInt();
        }
    }

    static String Ask(String question)
    {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextLine();
    }
}
