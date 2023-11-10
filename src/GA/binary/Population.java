package GA.binary;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Population {
    public int size = 10;
    public Random rd = new Random();
    public ArrayList<Individual> population = new ArrayList<Individual>();
    public Population() {
        for(int i=0; i <size; i++){
            Individual individual = new Individual();
            population.add(individual);
        }
    }
    public Individual crossover(Individual individual1, Individual individual2){
        int index = rd.nextInt(Hangso.n);
        Individual child = new Individual();
        for (int i=0; i < index; i++ ){
            child.x.set(i, individual1.x.get(i));
        }
        for (int i= index; i < Hangso.n; i++ ){
            child.x.set(i,individual2.x.get(i));
        }

        child.fitness = child.fitness();
        child.rp();
        return child;
    }

    public Individual mutation(Individual individual){
        int index = rd.nextInt(Hangso.n);
        Individual child = new Individual();
        if (child.x.get(index) == 0) {
            child.x.set(index, 1);
        }
        else {
            child.x.set(index, 0);
        }

        child.fitness = child.fitness();
        child.rp();
        return child ;
    }

    public void evolution(){
        for(int i=0; i < Hangso.n; i++){
            int r = rd.nextInt(100);
            if(r <= 80){
                int j = rd.nextInt(size);
                Individual child = crossover(population.get(i), population.get(j));
                population.add(child);
            }
        }

        for(int i=0; i < Hangso.n; i++){
            int r = rd.nextInt(100);
            if (r <= 5){
                Individual child = mutation(population.get(i));
                population.add(child);
            }
        }

        Collections.sort(population);
        ArrayList<Individual> tmp = new ArrayList<Individual>();
        for(int i=0; i <size; i++){
            tmp.add(population.get(i));
        }

        population = tmp;
    }


    public static void main(String[] args) {
        Population pop = new Population();
        System.out.println(pop.population);
        Collections.sort(pop.population);
//        for(int i=0; i < pop.size; i++){
//            pop.population.get(i).show();
//        }
        for(int i=0; i<100; i++){
            pop.evolution();
        }
        pop.population.get(0).show();
    }
}
