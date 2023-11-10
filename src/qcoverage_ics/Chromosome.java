package qcoverage_ics;

import java.util.ArrayList;
import java.util.Random;

import static qcoverage_ics.Target.*;

public class Chromosome {
    public ArrayList<Integer> chro = new ArrayList<>();

    public Random rd = new Random();

    public int cost;

    public int sizeChro = 3;

    int x,y,r;

    public Chromosome(){
        for(int i = 0; i< sizeChro  ; i++){
            chro.add(rd.nextInt(5));
        }

        cost = cost();
    }

    public Chromosome(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void show(){

        System.out.println(chro + " cost: " + cost);
    }

    public int cost(){
        int c=0;
        int d1 = (int) (Math.pow(chro.get(0) - locationTarget1[0], 2) + Math.pow(chro.get(1) - locationTarget1[1], 2));
//        System.out.println(d1); // Khoảng cách từ chromosome đến mục tiêu 1
        if (d1 <= chro.get(2)) {
            c = c +1;
        }

        int d2 = (int) (Math.pow(chro.get(0) - locationTarget2[0], 2) + Math.pow(chro.get(1) - locationTarget2[1], 2));
//        System.out.println(d2); // Khoảng cách từ chromosome đến mục tiêu 2
        if (d2 <= chro.get(2)){
            c = c +1;
        }

        int d3 = (int) (Math.pow(chro.get(0) - locationTarget3[0], 2) + Math.pow(chro.get(1) - locationTarget3[1], 2));
//        System.out.println(d3); // Khoảng cách từ chromosome đến mục tiêu 3
        if (d3 <= chro.get(2)){
            c = c +1;
        }


//        System.out.println(c);

        return c;
    }

    public static void main(String[] args) {
        Chromosome chromosome1 = new Chromosome();
        chromosome1.cost();
        chromosome1.show();
    }


}
