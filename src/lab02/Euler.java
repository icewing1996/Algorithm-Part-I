/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2017
 *
 * Name: Jimmy Wei
 * Date: 08/28/2017
 *
 * Lab: Lab02, Exercise 5
 *
 * Description:
 * A simple exercise to to compute e, and to illustrate BigDecimal and
 * its computational cost
 * *****************************************
 */
package lab02;

import java.math.BigDecimal;

/**
 * Euler - a class of static methods to illustrate different ways to compute
 * Euler's number
 */
public class Euler {

    /**
     * computeEulerTerm - compute the nth term of the power series of e^x
     *
     * @param n - The nth term of the power series to compute
     * @param x - the power of e used
     * @return - The computed term in the expansion
     */
    public static double computeEulerTerm(int n, double x) {
        double numerator = 1.0;
        double denominator = 1.0;
        for (int i = 1; i <= n; i++) {
            numerator *= x;         // Compute x to the n
            denominator *= i;       // Compute n!
        }

        // Compute the term, and return it
        return numerator / denominator;
    }

    /**
     * compute Euler's number to the x power using the Taylor series expansion.
     * This is a bit inefficient. It calls a separate method to compute each
     * term in the expansion.
     *
     * @param x - compute e^x
     * @param numTerms - Number of terms to compute in the expansion
     * @return Euler's number
     */
    public static double computeEulerSlow(double x, int numTerms) {
        // TODO - Complete this method!
        double result = 0.0;
        for (int i = 0; i <= numTerms; i++) {
            result += computeEulerTerm(i, x);
        }
        return result;
    }

    /**
     * compute Euler's number to the x power using the Taylor series expansion.
     * This is a faster implementation, using only one loop to compute the
     * result.
     *
     * @param x - compute e^x
     * @param numTerms - Number of terms to compute in the expansion
     * @return Euler's number
     */
    public static double computeEulerFast(double x, int numTerms) {
        // TODO - Complete this method!
        double result = 1.0;
        double factorial = 1.0;
        for (int i = 1; i <= numTerms; i++) {
            factorial *= i;
            result += (double) Math.pow(x,i) / factorial;
        }
        // Compute the term, and return it
        return result;
    }

    /**
     * computeEulerBig - compute e using BigDecimal so that we do not lose
     * accuracy
     *
     * @param x - the exponent of e to use
     * @param numTerms - the number of terms of the series to compute
     * @return - e as a BigDecimal
     */
    public static BigDecimal computeEulerBig(double x, int numTerms) {
        // TODO - Complete this method!
        BigDecimal result = new BigDecimal(1.0);
        BigDecimal big_x = new BigDecimal(x);
        BigDecimal factorial = new BigDecimal(1.0);

        for (int i = 1; i <= numTerms; i++) {
            BigDecimal big_i = new BigDecimal(i);
            factorial = factorial.multiply(big_i);
            BigDecimal power = big_x.pow(i);
            BigDecimal divided = power.divide(factorial, 23, BigDecimal.ROUND_HALF_UP);
            result = result.add(divided);
        }
        // Compute the term, and return it
        return result;
    }

    /**
     * Main program to test out Euler computations
     */
    public static void main(String args[]) {

        final int X = 5;
        final int NUM_TERMS = 20;

        System.out.printf("Evaluating e^%d using %d terms:\n", X, NUM_TERMS);

        long startTime = System.nanoTime();
        double dAnswer = Math.exp(X);
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Math.exp answer:      %.20f : Elapsed time (ns): %d\n", dAnswer, elapsedTime);

        startTime = System.nanoTime();
        double num = computeEulerSlow(X, NUM_TERMS);
        elapsedTime = System.nanoTime() - startTime;
        System.out.printf("My slow answer:       %.20f : Elapsed time (ns): %d\n", num, elapsedTime);

        startTime = System.nanoTime();
        num = computeEulerFast(X, NUM_TERMS);
        elapsedTime = System.nanoTime() - startTime;
        System.out.printf("My fast answer:       %.20f : Elapsed time (ns): %d\n", num, elapsedTime);

        startTime = System.nanoTime();
        BigDecimal bigAnswer = computeEulerBig(X, NUM_TERMS);
        elapsedTime = System.nanoTime() - startTime;
        System.out.printf("My BigDecimal answer: %.20f : Elapsed time (ns): %d\n", bigAnswer, elapsedTime);
    }
}
