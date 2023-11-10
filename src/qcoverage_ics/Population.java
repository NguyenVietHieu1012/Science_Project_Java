package qcoverage_ics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    public ArrayList<Individual> pop = new ArrayList<>();
    public int sizePop = 5;

    Random rd = new Random();
    public Population() {
        for (int i = 0; i < sizePop; i++) {
            Individual individual = new Individual();
            pop.add(individual);
        }
    }

    public Individual crossover(Individual indi1, Individual indi2){
        int index = rd.nextInt(sizePop);
        Individual child = new Individual();

        for(int i=0; i < index; i++){
            child.indi.set(i,indi1.indi.get(i));
        }

        for(int i=0; i < sizePop; i++) {
            child.indi.set(i,indi2.indi.get(i));
        }

        return child;

    }

    public Individual mutation(Individual indi){
        int index = rd.nextInt(indi.sizeIndi);
        Individual child = new Individual();

        child.indi.set(index,new Chromosome(10,10,10));

        return child;

    }

    public void evolution(){
        for(int i=0; i < sizePop; i++){
            int r = rd.nextInt(100);
            if (r <= 80){
                int j = rd.nextInt(sizePop);
                Individual child = crossover(pop.get(i),pop.get(j));
                pop.add(child);
            }
        }

        for(int i=0; i < sizePop; i++){
            int r = rd.nextInt(100);
            if(r <= 5){
                Individual child = mutation(pop.get(i));
                pop.add(child);
            }
        }

        Collections.sort(pop);
        ArrayList<Individual> tmp = new ArrayList<Individual>();
        for(int i=0; i <sizePop; i++){
            tmp.add(pop.get(i));
        }

        pop = tmp;

    }

    public static void main(String[] args) {
        Population population = new Population();
        System.out.println("Population\n");
        for(int i=0; i < 3; i++){
            System.out.println("Individual " + (i+1));
            population.pop.get(i).show();
            System.out.println("\n");
        }

        for(int i=0; i<100; i++){
            population.evolution();
        }

        System.out.println("Population evolution\n");
        for(int i=0; i < 3; i++){
            System.out.println("Individual evolution " + (i+1));
            population.pop.get(i).show();
            System.out.println("\n");
        }
    }
}