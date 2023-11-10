package GA.binary;

import java.util.ArrayList;
import java.util.Random;

public class Individual implements  Comparable {
    public ArrayList<Integer> x = new ArrayList<Integer>();
    public int fitness;

    public Random rd = new Random();
    public Individual(){
        for (int i = 0; i < Hangso.n ; i++){
            x.add(rd.nextInt(2));
        }
        fitness = fitness();
        rp();
    }

    public void show(){
        System.out.println(x);
        System.out.println(fitness);
    }

    public int fitness() {
        int sum = 0;
        for(int i =0; i<Hangso.n; i++){
            sum += Hangso.v[i] * x.get(i);
        }
        return sum;
    }

    public void rp (){
        while (fitness  > Hangso.c){
            int index = rd.nextInt(Hangso.n);
            x.set(index,0);
            fitness = fitness();
        }
    }


    public int compareTo(Individual individual){
        return this.fitness- individual.fitness;
    }

    public static void main(String[] args){
        Individual individual = new Individual();
        individual.show();

    }

    @Override
    public int compareTo(Object o) {
        int compare = ((Individual)o).fitness ;
        return compare-this.fitness;
    }

}

