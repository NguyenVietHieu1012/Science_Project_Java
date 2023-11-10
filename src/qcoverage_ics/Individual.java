package qcoverage_ics;

import java.util.ArrayList;

public class Individual implements  Comparable {
    public ArrayList<Chromosome> indi = new ArrayList<>();
    public int fitness = 0 ;

    public int sizeIndi = 5;

    public Individual(){
        for(int i=0; i < sizeIndi; i++){
            Chromosome chromosome = new Chromosome();
            indi.add(chromosome);
            chromosome.cost();
//            fitness = fitness + chromosome.cost();
        }
        fitness = fitness();
    }

    public int fitness(){
        int result = 0;
        for(int i=0; i <sizeIndi; i++){
            result = result + indi.get(i).cost();
        }
        return result;
    }

    public void show() {
        for(int i=0; i < sizeIndi ; i++){
            indi.get(i).show();
        }
        System.out.println("fitness: " + fitness);
    }

    public static void main(String[] args) {
        Individual individual = new Individual();
        individual.show();

    }

    @Override
    public int compareTo(Object o) {
        int compare = ((Individual)o).fitness ;
        return compare-this.fitness;
    }

}
