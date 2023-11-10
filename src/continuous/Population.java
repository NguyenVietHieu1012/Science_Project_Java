package continuous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Population {
    public ArrayList<Individual> population = new ArrayList<Individual>();
    public Random rd = new Random();
    public Population(){
        for(int i=0; i <Hangso.size; i++){
            Individual individual = new Individual();
            population.add(individual);
        }
    }

    public Individual crossover(Individual individual1, Individual individual2){
        Individual child = new Individual();
        int index = rd.nextInt(2);
        if(index == 1){
            child.chromosome.set(0,individual1.chromosome.get(0));
            child.chromosome.set(1,individual2.chromosome.get(1));
        }
        else {
            child.chromosome.set(0,individual2.chromosome.get(0));
            child.chromosome.set(1,individual1.chromosome.get(1));
        }
        return child;
    }

    public Individual mutation(Individual individual){
        int index = rd.nextInt(2);
        Individual child = new Individual();
        if (child.chromosome.get(index) >= 5) {
            child.chromosome.set(index, rd.nextDouble(10));
        }
        else {
            child.chromosome.set(index, rd.nextDouble(10));
        }

        child.fitness = child.cost();
        child.rp();
        return child ;
    }

    public void evolution(){
        for(int i = 0; i < Hangso.n; i++){
            int r = rd.nextInt(100);
            if(r <= 80){
                int j = rd.nextInt(Hangso.size);
                Individual child = crossover(population.get(i), population.get(j));
                population.add(child);
            }
        }

        for(int i = 0; i < Hangso.n; i++){
            int r = rd.nextInt(100);
            if (r <= 5){
                Individual child = mutation(population.get(i));
                population.add(child);
            }
        }

        Collections.sort(population);
        ArrayList<Individual> tmp = new ArrayList<>();
        for(int i=0; i <Hangso.size; i++){
            tmp.add(population.get(i));
        }

        population = tmp;
    }

    public static void main(String[] args) {
        Population pop = new Population();
        System.out.println(pop.population);
        Collections.sort(pop.population);
        for(int i=0; i<1000; i++){
            pop.evolution();
            if(i % 10 == 0) {
                System.out.println("index 0: ");
                pop.population.get(0).show();
                System.out.println("index 1: ");
                pop.population.get(1).show();
            }

        }

//        for(int i=0; i<5; i++){
//            pop.population.get(i).show();
//        }

//        pop.population.sort(Comparator.reverseOrder());
//        for(int i=0; i<5; i++){
//            pop.population.get(i).show();
//        }
    }
}
