package continuous;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.sin;

public class Individual implements  Comparable {
    public ArrayList<Double> chromosome = new ArrayList<Double>();
    public Random rd = new Random();
    public double fitness;
    public Individual(){
        for(int i=0; i<Hangso.n; i++){
            chromosome.add(rd.nextDouble(Hangso.max));
        }
        fitness = cost();
    }

    public void show(){
        System.out.println(chromosome);
        System.out.println(cost());
    }
    public double cost(){
        double result;
        result = chromosome.get(0) * sin(4*chromosome.get(0)) + 1.1 * chromosome.get(1)* sin(2*chromosome.get(1));
        return result;
    }
    public void rp (){
        while (chromosome.get(0)  > Hangso.max && chromosome.get(1) > Hangso.max){
            int index = rd.nextInt(Hangso.n);
            chromosome.set(index, rd.nextDouble(10));
            fitness = cost();
        }
    }

    public double compareTo(Individual individual){
        return this.fitness- individual.fitness;
    }

    public static void main(String[] args) {
        Individual individual = new Individual();
        individual.show();
    }

    @Override
    public int compareTo(Object o) {
        int compare = (int) ((Individual)o).fitness;
        return (int) (this.fitness-compare);
    }
}
