package qcoverage;

import java.util.ArrayList;
import java.util.Random;

public class Individual implements  Comparable {
    public ArrayList<Integer> locationSensor = new ArrayList<Integer>();

    public Random rd = new Random();

    public int fitness;

    public Individual(){
        for (int i=0; i<Hangso.n; i++){
            locationSensor.add(rd.nextInt(Hangso.max));
        }
        fitness = cost();
    }

    public void show(){
        System.out.println("locationSensor: " + locationSensor);
        System.out.println("fitness: " + cost() + "\n");
    }

    public int cost(){
        int result = 0;
        int x1 = (Hangso.locationTarget1[0] - locationSensor.get(0));
        int x2 = (Hangso.locationTarget1[1] - locationSensor.get(1));

        int x3 = (Hangso.locationTarget1[0] - locationSensor.get(0));
        int x4 = (Hangso.locationTarget1[1] - locationSensor.get(1));

        int x5 = (Hangso.locationTarget1[0] - locationSensor.get(0));
        int x6 = (Hangso.locationTarget1[1] - locationSensor.get(1));

        int x7 = (Hangso.locationTarget1[0] - locationSensor.get(0));
        int x8 = (Hangso.locationTarget1[1] - locationSensor.get(1));

        int x9 = (Hangso.locationTarget1[0] - locationSensor.get(0));
        int x10 = (Hangso.locationTarget1[1] - locationSensor.get(1));

        for (int i=1; i<=5; i++){
            if(x1 * x1 + x2 * x2 <= Hangso.r * Hangso.r) {
                result++;
            } else if (x3 * x3 + x4 * x4 <= Hangso.r * Hangso.r) {
                result++;
            } else if (x5 * x5 + x6 * x6 <= Hangso.r * Hangso.r) {
                result++;
            } else if (x7 * x7 + x8 * x8 <= Hangso.r * Hangso.r) {
                result++;
            } else if (x9 * x9 + x10 * x10 <= Hangso.r * Hangso.r) {
                result++;
            }
        }
        return result;
    }

    public double compareTo(Individual individual){
        return individual.fitness - this.fitness;
    }

    public static void main(String[] args) {
        Individual individual = new Individual();
        individual.show();
//        System.out.println("Target1: " + Hangso.locationTarget1[0] + " " + Hangso.locationTarget1[1] + "\n" );
//        System.out.println("Target2: " + Hangso.locationTarget2[0] + " " + Hangso.locationTarget2[1] + "\n" );
//        System.out.println("Target3: " + Hangso.locationTarget3[0] + " " + Hangso.locationTarget3[1] + "\n" );
//        System.out.println("Target4: " + Hangso.locationTarget4[0] + " " + Hangso.locationTarget4[1] + "\n" );
//        System.out.println("Target5: " + Hangso.locationTarget5[0] + " " + Hangso.locationTarget5[1] + "\n" );
    }

    @Override
    public int compareTo(Object o) {
        int compare = (int) ((Individual)o).fitness;
        return (int) (compare - this.fitness);
    }
}
