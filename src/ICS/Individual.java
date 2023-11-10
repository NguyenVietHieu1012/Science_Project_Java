package ICS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Individual {
    public int L = 1000000;
    ArrayList<Gene> listGene = new ArrayList<Gene>();
    double fitness;
    double areas;

    public Individual() {
    }

    public Individual(Individual individual) {
        this.listGene = (ArrayList<Gene>) individual.listGene.clone();
        this.areas = individual.areas;
        this.fitness = individual.fitness;
    }

    public Individual(int type, ArrayList<Obstacle> obstacle, int nbSensor, int nbT1, int nbT2, int nbT3, double r1,
                      double r2, double r3) {
        // neu type = 0 thi khoi tao ngau nhien
        if (type == 0) {
            int i = 0;
            while (i < nbSensor) {
                double x = 100 * MyRandom.R.nextDouble();
                double y = 100 * MyRandom.R.nextDouble();
                boolean test = true;
                for (int j = 0; j < obstacle.size(); j++) {
                    if ((x >= obstacle.get(j).xmin && x <= obstacle.get(j).xmax)
                            && (y >= obstacle.get(j).ymin && y <= obstacle.get(j).ymax)) {
                        test = false;
                        break;
                    }
                }
                if (test == true) {
                    if (i < nbT1) {
                        this.listGene.add(new Gene(x, y, r1));
                    } else if (i >= nbT1 && i < nbT1 + nbT2) {
                        this.listGene.add(new Gene(x, y, r2));
                    } else {
                        this.listGene.add(new Gene(x, y, r3));
                    }
                    i = i + 1;
                }
            }
            // hieu chinh
//			VFA(obstacle);
            normal(obstacle);
            fitness(obstacle, r1);

        } else if (type == 1) {
            // neu type = 1 thi khoi tao heuristic
            // xay dung bo ban kinh
            for (int j = 0; j < nbT1; ++j) {
                this.listGene.add(new Gene(r1));
            }
            for (int j = 0; j < nbT2; ++j) {
                this.listGene.add(new Gene(r2));
            }
            for (int j = 0; j < nbT3; ++j) {
                this.listGene.add(new Gene(r3));
            }
            // dao vi tri cac ban kinh
            for (int j = 0; j < nbSensor / 2; ++j) {
                int id1 = (int) (MyRandom.R.nextDouble() * nbSensor);
                int id2 = (int) (MyRandom.R.nextDouble() * nbSensor);

                while (this.listGene.get(id2).r == this.listGene.get(id1).r) {
                    id2 = (int) (MyRandom.R.nextDouble() * nbSensor);
                }
                double tmpr = this.listGene.get(id1).r;
                this.listGene.get(id1).r = this.listGene.get(id2).r;
                this.listGene.get(id2).r = tmpr;
            }
            findBest(nbSensor, r1, r2, r3, obstacle);
//			VFA(obstacle);
            normal(obstacle);
            fitness(obstacle, r1);
        }
    }

    public void findBest(int nbSensor, double r1, double r2, double r3, ArrayList<Obstacle> obstacle) {
        if (this.listGene.get(0).r == r1) {
            this.listGene.get(0).x = r1;
            this.listGene.get(0).y = r1;
        } else if (this.listGene.get(0).r == r2) {
            this.listGene.get(0).x = r2;
            this.listGene.get(0).y = r2;
        } else {
            this.listGene.get(0).x = r3;
            this.listGene.get(0).y = r3;
        }
        int count1 = 1, count2 = nbSensor;
        for (int j = 1; j < nbSensor; ++j) {
            this.listGene.get(j).x = this.listGene.get(j - 1).x + this.listGene.get(j - 1).r + this.listGene.get(j).r;
            if (this.listGene.get(j).x > 100) {
                count2 = count1;
                count1 = 1;
                this.listGene.get(j).x = this.listGene.get(j).r;
                this.listGene.get(j).y = this.listGene.get(j - count2).y + this.listGene.get(j - count2).r
                        + this.listGene.get(j).r;
                for (int i = 0; i < obstacle.size(); i++) {
                    if ((this.listGene.get(j).x >= obstacle.get(i).xmin
                            && this.listGene.get(j).x <= obstacle.get(i).xmax)
                            && (this.listGene.get(j).y >= obstacle.get(i).ymin
                            && this.listGene.get(j).y <= obstacle.get(i).ymax)) {
                        if (this.listGene.get(j).x - this.listGene.get(j).r >= obstacle.get(i).xmin) {
                            this.listGene.get(j).x = obstacle.get(i).xmax;
                        } else {
                            this.listGene.get(j).x = obstacle.get(i).xmin;
                        }
                    }
                }
            } else {
                ++count1;
                if (j - count2 <= 0) {
                    this.listGene.get(j).y = this.listGene.get(j).r;
                } else {
                    this.listGene.get(j).y = this.listGene.get(j - count2).y + this.listGene.get(j - count2).r
                            + this.listGene.get(j).r;
                }
                for (int i = 0; i < obstacle.size(); i++) {
                    if ((this.listGene.get(j).x >= obstacle.get(i).xmin
                            && this.listGene.get(j).x <= obstacle.get(i).xmax)
                            && (this.listGene.get(j).y >= obstacle.get(i).ymin
                            && this.listGene.get(j).y <= obstacle.get(i).ymax)) {
                        if (this.listGene.get(j).x - this.listGene.get(j).r >= obstacle.get(i).xmin) {
                            this.listGene.get(j).x = obstacle.get(i).xmax;
                        } else {
                            this.listGene.get(j).x = obstacle.get(i).xmin;
                        }
                    }
                }
            }

        }
        // sap xep lai cac gene theo ban kinh
        for (int i = 0; i < nbSensor - 1; ++i) {
            for (int j = i + 1; j < nbSensor; ++j) {
                if (this.listGene.get(i).r < this.listGene.get(j).r) {
                    double x = this.listGene.get(i).x;
                    double y = this.listGene.get(i).y;
                    double r = this.listGene.get(i).r;
                    this.listGene.get(i).x = this.listGene.get(j).x;
                    this.listGene.get(i).y = this.listGene.get(j).y;
                    this.listGene.get(i).r = this.listGene.get(j).r;
                    this.listGene.get(j).x = x;
                    this.listGene.get(j).y = y;
                    this.listGene.get(j).r = r;
                }
            }
        }
    }

    public void fitness(ArrayList<Obstacle> obstacle, double rmax) {
        this.fitness = 0;
        for (int i = 0; i < this.listGene.size(); ++i) {
            // tinh do chong giua cac sensor voi nhau
            for (int j = i + 1; j < this.listGene.size(); ++j) {
                double dx = this.listGene.get(j).x - this.listGene.get(i).x;
                double dy = this.listGene.get(j).y - this.listGene.get(i).y;
                double r1 = this.listGene.get(j).r;
                double r2 = this.listGene.get(i).r;

                double d = Math.sqrt(dx * dx + dy * dy);
                double tr = Math.sqrt((r1 + r2) * (r1 + r2));
                double hr = Math.sqrt((r1 - r2) * (r1 - r2));

                if (d < tr) {
                    if (d > hr) {
                        double cosAlpha = (r1 * r1 + d * d - r2 * r2) / (2 * r1 * d);
                        double cosBeta = (r2 * r2 + d * d - r1 * r1) / (2 * r2 * d);
                        double alpha = 2 * Math.acos(cosAlpha);
                        double beta = 2 * Math.acos(cosBeta);
                        double S = 0.5 * (alpha - Math.sin(alpha)) * r1 * r1 + 0.5 * (beta - Math.sin(beta)) * r2 * r2;
                        this.fitness += S;
                    } else {
                        this.fitness += Math.PI * Math.min(r1, r2) * Math.min(r1, r2);
                    }
                }
            }
            // tinh do chong giua sensor voi bien
            double x = this.listGene.get(i).x;
            double y = this.listGene.get(i).y;
            double r = this.listGene.get(i).r;
            double d = 100 - this.listGene.get(i).y;
            if (d < r) {
                double alpha = 2 * Math.acos(d / r);
                this.fitness += 0.5 * (alpha - Math.sin(alpha)) * r * r;
            } else {
                d = this.listGene.get(i).y;
                if (d < r) {
                    double alpha = 2 * Math.acos(d / r);
                    this.fitness += 0.5 * (alpha - Math.sin(alpha)) * r * r;
                }
            }
            d = this.listGene.get(i).x;
            if (d < r) {
                double alpha = 2 * Math.acos(d / r);
                this.fitness += 0.5 * (alpha - Math.sin(alpha)) * r * r;
            } else {
                d = 100 - this.listGene.get(i).x;
                if (d < r) {
                    double alpha = 2 * Math.acos(d / r);
                    this.fitness += 0.5 * (alpha - Math.sin(alpha)) * r * r;
                }
            }
            // tinh do chong cua sensor voi obstacle
            for (int j = 0; j < obstacle.size(); j++) {
                Obstacle newObstacle = obstacle.get(j);
                // truong hop 1
                if (x < newObstacle.xmin && y < newObstacle.ymin) {
                    d = Math.sqrt((newObstacle.xmin - x) * (newObstacle.xmin - x)
                            + (newObstacle.ymin - y) * (newObstacle.ymin - y));
                    if (d < r) {
                        this.fitness += 0.75
                                * (Math.sqrt(r * r - (newObstacle.xmin - x) * (newObstacle.xmin - x))
                                - (newObstacle.ymin - y))
                                * (Math.sqrt(r * r - (newObstacle.ymin - y) * (newObstacle.ymin - y))
                                - (newObstacle.xmin - x));
                    }
                }
                // truong hop 2
                else if (x >= newObstacle.xmin && x <= newObstacle.xmax && y < newObstacle.ymin) {
                    if (y + r > newObstacle.ymin) {
                        this.fitness += 1.5 * (y + r - newObstacle.ymin)
                                * Math.sqrt(r * r - (newObstacle.ymin - y) * (newObstacle.ymin - y));
                    }
                }
                // truong hop 3
                else if (x > newObstacle.xmax && y < newObstacle.ymin) {
                    d = Math.sqrt((x - newObstacle.xmax) * (x - newObstacle.xmax)
                            + (y - newObstacle.ymin) * (y - newObstacle.ymin));
                    if (d < r) {
                        this.fitness += 0.75
                                * (Math.sqrt(r * r - (y - newObstacle.ymin) * (y - newObstacle.ymin))
                                - (x - newObstacle.xmax))
                                * (Math.sqrt(r * r - (x - newObstacle.xmax) * (x - newObstacle.xmax))
                                - (newObstacle.ymin - y));
                    }
                }
                // truong hop 4
                else if (x > newObstacle.xmax && y >= newObstacle.ymin && y <= newObstacle.ymax) {
                    if (x - r < newObstacle.xmax) {
                        this.fitness += 1.5 * (newObstacle.xmax - x + r)
                                * Math.sqrt(r * r - (x - newObstacle.xmax) * (x - newObstacle.xmax));
                    }
                }
                // truong hop 5
                else if (x > newObstacle.xmax && y > newObstacle.ymax) {
                    d = Math.sqrt((x - newObstacle.xmax) * (x - newObstacle.xmax)
                            + (y - newObstacle.ymax) * (y - newObstacle.ymax));
                    if (d < r) {
                        this.fitness += 0.75
                                * (Math.sqrt(r * r - (y - newObstacle.ymax) * (y - newObstacle.ymax))
                                - (x - newObstacle.xmax))
                                * (Math.sqrt(r * r - (x - newObstacle.xmax) * (x - newObstacle.xmax))
                                - (y - newObstacle.ymax));
                    }
                }
                // truong hop 6
                else if (x >= newObstacle.xmin && x <= newObstacle.xmax && y > newObstacle.ymax) {
                    if (y - r < newObstacle.ymax) {
                        this.fitness += 1.5 * (newObstacle.ymax - y + r)
                                * Math.sqrt(r * r - (y - newObstacle.ymax) * (y - newObstacle.ymax));
                    }
                }
                // truong hop 7
                else if (x < newObstacle.xmin && y > newObstacle.ymax) {
                    d = Math.sqrt((x - newObstacle.xmin) * (x - newObstacle.xmin)
                            + (y - newObstacle.ymax) * (y - newObstacle.ymax));
                    if (d < r) {
                        this.fitness += 0.75
                                * (Math.sqrt(r * r - (x - newObstacle.xmin) * (x - newObstacle.xmin))
                                - (y - newObstacle.ymax))
                                * (Math.sqrt(r * r - (y - newObstacle.ymax) * (y - newObstacle.ymax))
                                - (newObstacle.xmin - x));
                    }
                }
                // truong hop 8
                else if (x < newObstacle.xmin && y >= newObstacle.ymin && y <= newObstacle.ymax) {
                    if (x + r > newObstacle.xmin) {
                        this.fitness += 1.5 * (x + r - newObstacle.xmin)
                                * Math.sqrt(r * r - (newObstacle.xmin - x) * (newObstacle.xmin - x));
                    }
                }
                // truong hop 9
                else {
                    d = Math.min(r, newObstacle.xmax - x) + Math.min(r, x - newObstacle.xmin);
                    this.fitness += Math
                            .abs(d * (Math.min(r, newObstacle.ymax - y) + Math.min(r, y - newObstacle.ymin)));
                }
            }
        }
    }

    public void area(ArrayList<Obstacle> obstacle) {
        int i = 0;
        double sum = 0;
        while (i < L) {
            double x = 100 * MyRandom.R.nextDouble();
            double y = 100 * MyRandom.R.nextDouble();
            for (int j = 0; j < this.listGene.size(); j++) {
                if (this.listGene.get(j).distnce(x, y) <= this.listGene.get(j).r) {
                    sum = sum + 1;
                    break;
                }
            }
            i = i + 1;
        }
        this.areas = sum / 10000.0;
    }

    public void normalize(Individual indi, int nbT1, int nbT2, int nbT3) {
        normalize(indi, 0, nbT1);
        normalize(indi, nbT1, nbT2);
        normalize(indi, nbT1 + nbT2, nbT3);
    }

    private void normalize(Individual indi, int nbSensor, int start) {
        double d[][] = new double[nbSensor][nbSensor];
        double fx[] = new double[nbSensor], fy[] = new double[nbSensor];
        double[] kcd = new double[nbSensor];
        int arg[] = new int[nbSensor];
        int matchX[] = new int[nbSensor], matchY[] = new int[nbSensor];
        int trace[] = new int[nbSensor], queue[] = new int[nbSensor];
        int front, rear;
        int s, finish;
        // tinh khoang cach giua tung cap sensor
        for (int i = start; i < start + nbSensor; ++i) {
            for (int j = start; j < start + nbSensor; ++j) {
                d[i - start][j - start] = Math.sqrt(Math.pow(this.listGene.get(j).x - indi.listGene.get(i).x, 2)
                        + Math.pow(this.listGene.get(j).y - indi.listGene.get(i).y, 2));
            }
        }

        for (int i = 0; i < nbSensor; ++i) {
            matchX[i] = -1;
            matchY[i] = -1;
        }

        // thu ghep lan luot cac s[1].. s[nbSensor]
        for (int si = 0; si < nbSensor; ++si) {
            s = si;
            // initBFS(nbSensor);
            front = 0;
            rear = 1;
            queue[0] = s;
            // reset(trace);
            for (int i = 0; i < nbSensor; ++i) {
                trace[i] = -1;
            }

            for (int j = 0; j < nbSensor; ++j) {
                kcd[j] = d[si][j] - fx[si] - fy[j];
                arg[j] = s;
            }
            finish = -1;

            do {
                // findAugmentingPath(nbSensor);
                int i, j;
                double w;

                do {
                    // i = pop();
                    i = queue[front];
                    ++front;
                    for (j = 0; j < nbSensor; ++j) {
                        if (trace[j] == -1) {
                            w = d[i][j] - fx[si] - fy[j];
                            if (w == 0.0) {
                                trace[j] = i;
                                if (matchY[j] == -1) {
                                    finish = j;
                                    return;
                                }
                                // push(matchY[j]);
                                queue[rear] = matchY[j];
                                ++rear;
                            }
                            if (kcd[j] > w) {
                                kcd[j] = w;
                                arg[j] = i;
                            }
                        }
                    }
                } while (front < rear);
                if (finish == -1) {
                    // subX_addY(nbSensor);
                    double delta;
                    int i1, j1;

                    // delta la gia tri nho nhat trong so cac kcd[j] ma y[j]
                    // chua tham
                    delta = 100000;
                    for (j1 = 0; j1 < nbSensor; ++j1) {
                        if (trace[j1] == -1 && kcd[j1] < delta) {
                            delta = kcd[j1];
                        }
                    }

                    fx[s] += delta;

                    for (j1 = 0; j1 < nbSensor; ++j1) {
                        if (trace[j1] != -1) {
                            i1 = matchY[j1];
                            fy[j1] -= delta;
                            fx[i1] += delta;
                        } else {
                            kcd[j1] -= delta;
                        }
                    }

                    for (j1 = 0; j1 < nbSensor; ++j1) {
                        if (trace[j1] == -1 && kcd[j1] == 0) {
                            trace[j1] = arg[j1];
                            if (matchY[j1] == -1) {
                                finish = j1;
                                return;
                            }
                            // push(matchY[j1]);
                            queue[rear] = matchY[j1];
                            ++rear;
                        }
                    }
                }
            } while (finish == -1);
            // enlarge();
            int i, next;
            do {
                i = trace[finish];
                next = matchX[i];
                matchX[i] = finish;
                matchY[finish] = i;
                finish = next;
            } while (finish != -1);
        }
        // write();
        Gene[] tmp = new Gene[nbSensor];
        for (int i = start; i < start + nbSensor; ++i) {
            tmp[i - start] = new Gene(this.listGene.get(start + matchX[i - start]).x,
                    this.listGene.get(start + matchX[i - start]).y);
        }

        for (int i = start; i < start + nbSensor; ++i) {
            this.listGene.get(i).x = tmp[i - start].x;
            this.listGene.get(i).y = tmp[i - start].y;
        }

    }

    public void write(FileWriter fw) throws IOException {
        fw.write("Aria trung binh: " + this.areas + "\r\n");
        String newString = new String();
        for (int i = 0; i < this.listGene.size(); i++) {
            newString = newString + String.format("(%.2f - %.2f - %.2f) ", this.listGene.get(i).x,
                    this.listGene.get(i).y, this.listGene.get(i).r);
        }
        fw.write(newString);
        fw.write("\r\n");
    }

    public void normal(ArrayList<Obstacle> obstacle) {
        for (int i = 0; i < this.listGene.size(); i++) {
            double x = this.listGene.get(i).x;
            double y = this.listGene.get(i).y;
            double r = this.listGene.get(i).r;
            for (int j = 0; j < obstacle.size(); j++) {
                Obstacle newObstacle = obstacle.get(j);
                if (x < newObstacle.xmin && y < newObstacle.ymin) {
                    if (x + r > newObstacle.xmin && y + r > newObstacle.ymin) {
                        x = newObstacle.xmin - r;
                        y = newObstacle.ymin - r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x >= newObstacle.xmin && x <= newObstacle.xmax && y < newObstacle.ymin) {
                    if (y + r > newObstacle.ymin) {
                        y = newObstacle.ymin - r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x > newObstacle.xmax && y < newObstacle.ymin) {
                    if (x - r < newObstacle.xmax && y + r > newObstacle.ymin) {
                        x = newObstacle.xmax + r;
                        y = newObstacle.ymin - r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x > newObstacle.xmax && y >= newObstacle.ymin && y <= newObstacle.ymax) {
                    if (x - r < newObstacle.xmax) {
                        x = newObstacle.xmax + r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x > newObstacle.xmax && y > newObstacle.ymax) {
                    if (x - r < newObstacle.xmax && y - r < newObstacle.ymax) {
                        x = newObstacle.xmax + r;
                        y = newObstacle.ymin + r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x >= newObstacle.xmin && x <= newObstacle.xmax && y > newObstacle.ymax) {
                    if (y - r < newObstacle.ymax) {
                        y = newObstacle.ymax + r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x < newObstacle.xmin && y > newObstacle.ymax) {
                    if (x + r > newObstacle.xmin && y - r < newObstacle.ymax) {
                        x = newObstacle.xmin - r;
                        y = newObstacle.ymax + r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else if (x < newObstacle.xmin && y >= newObstacle.ymin && y <= newObstacle.ymax) {
                    if (x + r > newObstacle.xmin) {
                        x = newObstacle.xmin - r;
                        this.listGene.set(i, new Gene(x, y, r));
                    }
                } else {
                    double p = MyRandom.R.nextDouble();
                    if (p > 0.5) {
                        double q = MyRandom.R.nextDouble();
                        if (q > 0.5) {
                            x = newObstacle.xmax + r;
                        } else {
                            x = newObstacle.xmin - r;
                        }
                    } else {
                        double q = MyRandom.R.nextDouble();
                        if (q > 0.5) {
                            y = newObstacle.ymax + r;
                        } else {
                            y = newObstacle.ymin - r;
                        }
                    }
                    this.listGene.set(i, new Gene(x, y, r));
                }
            }
            if (this.listGene.get(i).x + r > 100) {
                x = 100 - r;
            } else {
                if (this.listGene.get(i).x - r < 0) {
                    x = r;
                }
            }
            if (this.listGene.get(i).y + r > 100) {
                y = 100 - r;
            } else {
                if (this.listGene.get(i).y - r < 0) {
                    y = r;
                }
            }
            this.listGene.set(i, new Gene(x, y, r));
        }
    }



    public static final Comparator<Individual> ASC_COMPARATOR = new Comparator<Individual>() {

        @Override
        public int compare(Individual o1, Individual o2) {
            // TODO Auto-generated method stub
            if (o1.fitness == o2.fitness)
                return 0;
            else
                return o1.fitness > o2.fitness ? 1 : -1;
        }
    };

    public static void main(String[] args) {
        int nbSensor = 101;
        int nbT1 = 22, nbT2 = 32, nbT3 = 47;
        double r1 = 6.00, r2 = 4.8, r3 = 3.84;
        Obstacle obstacle = new Obstacle(35, 60, 40, 75);
        ArrayList<Obstacle> listObstacle = new ArrayList<>();
        String strRead = "BoDuLieu\\1-7.txt";
        Scanner fr;
        try {
            fr = new Scanner(new File(strRead));
            nbSensor = fr.nextInt();
            int nbTypeofSensor = fr.nextInt();
            nbT1 = fr.nextInt();
            nbT2 = fr.nextInt();
            nbT3 = fr.nextInt();
            r1 = fr.nextDouble();
            r2 = fr.nextDouble();
            r3 = fr.nextDouble();
            int nbObstacle = fr.nextInt();
            for (int idObstacle = 0; idObstacle < nbObstacle; idObstacle++) {
                double xmin, xmax, ymin, ymax;
                xmin = fr.nextDouble();
                ymin = fr.nextDouble();
                xmax = fr.nextDouble();
                ymax = fr.nextDouble();
                obstacle = new Obstacle(xmin, xmax, ymin, ymax);
                listObstacle.add(obstacle);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Individual newIndividual = new Individual(1, listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
        newIndividual.area(listObstacle);
        String strWrite = "output.txt";
        try {
            FileWriter fw = new FileWriter(new File(strWrite));
            newIndividual.write(fw);
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
