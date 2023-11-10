//package ICS;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Random;
//import java.util.Scanner;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class App {
//    private static final int NBRUN = 1;
//    private static final int GENERATION = 2000;
//    private static final int SIZE = 20;
//    private static final int CUCKOOSIZE = 10;
//    private static final double alphaMAX = 0.5;
//    private static final double alphaMIN = 0.01;
//    private static final double paMIN = 0.05;
//    private static final double paMAX = 0.5;
//
//    private static int nbSensor = 101;
//    private static int nbTypeofSensor=3;
//    private static int nbObstacle=1;
//    private static int nbT1 = 22, nbT2 = 32, nbT3 = 47;
//    private static double r1 =  6.00, r2 = 4.8, r3 = 3.84;
//
//    private static Random random;
//    private static ArrayList<Individual> population;
//    private static ArrayList<Individual> cuckoo;
//    private static final int nb2 = 10;
//    private static final int nbCuckoo = 5;
//
//    private static void initialize(ArrayList<Obstacle> listObstacle, int nbSensor, int nbT1, int nbT2, int nbT3, double r1, double r2,
//                                   double r3) {
//        population = new ArrayList<>();
//        for (int i = 0; i < SIZE - nb2; ++i) {
//            Individual tmp = new Individual(1, listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//            mutation2(listObstacle, tmp);
//            tmp.normal(listObstacle);
//            tmp.fitness(listObstacle, r1);
//            population.add(tmp);
//        }
//        for (int i = 0; i < nb2; i++) {
//            Individual tmp = new Individual(0, listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//            mutation2(listObstacle, tmp);
//            tmp.normal(listObstacle);
//            tmp.fitness(listObstacle, r1);
//            population.add(tmp);
//        }
//    }
//
//    private static void initialize3(ArrayList<Obstacle> listObstacle, int nbSensor, int nbT1, int nbT2, int nbT3, double r1, double r2,
//                                    double r3) {
//        cuckoo = new ArrayList<>();
//        for (int i = 0; i < CUCKOOSIZE - nbCuckoo; ++i) {
//            Individual tmp = new Individual(0, listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//            mutation2(listObstacle, tmp);
//            tmp.normal(listObstacle);
//            tmp.fitness(listObstacle, r1);
//            cuckoo.add(tmp);
//        }
//        for (int i = 0; i < nbCuckoo; i++) {
//            Individual tmp = new Individual(1, listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//            mutation2(listObstacle, tmp);
//            tmp.normal(listObstacle);
//            tmp.fitness(listObstacle, r1);
//            cuckoo.add(tmp);
//        }
//    }
//
//    private static Individual mutation2(ArrayList<Obstacle> listObstacle, Individual indi) {
//
//        for (int i = 0; i < nbSensor; ++i) {
//            double frx = 0, fry = 0;
//            int nr = 0;
//            for (int j = 0; j < i; ++j) {
//                double d = Math.sqrt(Math.pow(indi.listGene.get(i).x - indi.listGene.get(j).x, 2)
//                        + Math.pow(indi.listGene.get(i).y - indi.listGene.get(j).y, 2));
//                if (d < indi.listGene.get(i).r + indi.listGene.get(j).r && d != 0) {
//                    double tmp = 1 - (indi.listGene.get(i).r + indi.listGene.get(j).r) / d;
//                    frx += tmp * (indi.listGene.get(j).x - indi.listGene.get(i).x);
//                    fry += tmp * (indi.listGene.get(j).y - indi.listGene.get(i).y);
//                    ++nr;
//                }
//            }
//            for (int j = i + 1; j < nbSensor; ++j) {
//                double d = Math.sqrt(Math.pow(indi.listGene.get(i).x - indi.listGene.get(j).x, 2)
//                        + Math.pow(indi.listGene.get(i).y - indi.listGene.get(j).y, 2));
//                if (d < indi.listGene.get(i).r + indi.listGene.get(j).r && d != 0) {
//                    double tmp = 1 - (indi.listGene.get(i).r + indi.listGene.get(j).r) / d;
//                    frx += tmp * (indi.listGene.get(j).x - indi.listGene.get(i).x);
//                    fry += tmp * (indi.listGene.get(j).y - indi.listGene.get(i).y);
//                    ++nr;
//                }
//            }
//            double db = 100 - indi.listGene.get(i).y;
//            if (db < indi.listGene.get(i).r) {
//                fry += db - indi.listGene.get(i).r;
//                ++nr;
//            }
//
//            db = indi.listGene.get(i).y;
//            if (db < indi.listGene.get(i).r) {
//                fry += indi.listGene.get(i).r - db;
//                ++nr;
//            }
//
//            db = 100 - indi.listGene.get(i).x;
//            if (db < indi.listGene.get(i).r) {
//                frx += db - indi.listGene.get(i).r;
//                ++nr;
//            }
//
//            db = indi.listGene.get(i).x;
//            if (db < indi.listGene.get(i).r) {
//                frx += indi.listGene.get(i).r - db;
//                ++nr;
//            }
//            if (nr != 0) {
//                indi.listGene.get(i).x += frx / nr;
//                indi.listGene.get(i).y += fry / nr;
//            }
//            if (indi.listGene.get(i).x < indi.listGene.get(i).r) {
//                indi.listGene.get(i).x = indi.listGene.get(i).r;
//            }
//            if (100 - indi.listGene.get(i).x < indi.listGene.get(i).r) {
//                indi.listGene.get(i).x = 100 - indi.listGene.get(i).r;
//            }
//            if (indi.listGene.get(i).y < indi.listGene.get(i).r) {
//                indi.listGene.get(i).y = indi.listGene.get(i).r;
//            }
//            if (100 - indi.listGene.get(i).y < indi.listGene.get(i).r) {
//                indi.listGene.get(i).y = 100 - indi.listGene.get(i).r;
//            }
//        }
//        indi.normal(listObstacle);
//        return indi;
//    }
//
//
//    private static String write(Individual indi) {
//        String newString = new String();
//        for (int i = 0; i < nbSensor; i++) {
//            newString = newString + String.format("(%.2f - %.2f - %.2f) ", indi.listGene.get(i).x,
//                    indi.listGene.get(i).y, indi.listGene.get(i).r);
//        }
//        newString += "\r\n";
//        newString += "fitness: " + indi.fitness + "\r\n";
//        newString += "Area: " + indi.areas + "\r\n";
//        return newString;
//    }
//
//    public static void main(String[] args) {
//        for (int i = 1; i < 6; i++) {
//            for (int j = 1; j < 6; j++) {
//                if (i == 1 && (j == 4 || j == 5)) {
//                    continue;
//                }
//                // khoi tao bien moi truong
//                ArrayList<Obstacle> listObstacle = new ArrayList<>();
//                try {
//                    // doc du lieu tu file
//                    String strRead = "BoDuLieu\\" + i + "-" + j + ".txt";
//                    Scanner fr = new Scanner(new File(strRead));
//                    nbSensor = fr.nextInt();
//                    nbTypeofSensor = fr.nextInt();
//                    nbT1 = fr.nextInt();
//                    nbT2 = fr.nextInt();
//                    nbT3 = fr.nextInt();
//                    r1 = fr.nextDouble();
//                    r2 = fr.nextDouble();
//                    r3 = fr.nextDouble();
//                    nbObstacle = fr.nextInt();
//                    for (int idObstacle = 0; idObstacle < nbObstacle; idObstacle++) {
//                        double xmin, xmax, ymin, ymax;
//                        xmin = fr.nextDouble();
//                        ymin = fr.nextDouble();
//                        xmax = fr.nextDouble();
//                        ymax = fr.nextDouble();
//                        Obstacle obstacle = new Obstacle(xmin, xmax, ymin, ymax);
//                        listObstacle.add(obstacle);
//                    }
//                    double upperBound = (nbT1 * r1 * r1 + nbT2 * r2 * r2 + nbT3 * r3 * r3) * Math.PI;
//                    fr.close();
//                    // khoi chay thuat toan
//                    XSSFWorkbook workbook = new XSSFWorkbook();
//                    XSSFSheet sheet = workbook.createSheet("Output_ICS");
//                    String strWrite = "Output_ICS\\" + i + "-" + j + ".txt";
//                    FileWriter fw = new FileWriter(new File(strWrite));
//                    Row row = sheet.createRow(0);
//                    Cell cell = row.createCell(0);
//                    cell.setCellValue("Lần Chạy");
//                    cell = row.createCell(1);
//                    cell.setCellValue("Fitness");
//                    cell = row.createCell(2);
//                    cell.setCellValue("Dien Tich");
//                    cell = row.createCell(3);
//                    cell.setCellValue("Thoi Gian (s)");
//                    cell = row.createCell(4);
//                    cell.setCellValue("Upper Bound");
//                    int rownum = 1;
//                    System.out.println(strRead);
//                    double timeTB = 0;
//                    double areaTB = 0;
//                    double fitnessTB = 0;
//                    for (int idRun = 0; idRun < NBRUN; idRun++) {
//                        long t1 = System.currentTimeMillis();
//                        MyRandom.setR(idRun);
//                        initialize(listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//                        System.out.println(population.size());
//                        initialize3(listObstacle, nbSensor, nbT1, nbT2, nbT3, r1, r2, r3);
//                        System.out.println(cuckoo.size());
//                        int count=0;
//                        double fitnessNow = population.get(0).fitness;
//                        for (int idGeneration = 1; idGeneration <= GENERATION; ++idGeneration) {
//                            evol(listObstacle, idGeneration);
//                            Collections.sort(population, Individual.ASC_COMPARATOR);
//                            if (population.get(0).fitness < fitnessNow) {
//                                fitnessNow = population.get(0).fitness;
//                                count = 0;
//                            } else {
//                                count++;
//                            }
//                            if (count > 400) {
//                                break;
//                            }
//                        }
//                        population.get(0).area(listObstacle);
//                        long t2 = System.currentTimeMillis();
//                        timeTB = timeTB + (t2 - t1) / 1000.0;
//                        areaTB = areaTB + population.get(0).areas;
//                        fitnessTB = fitnessTB + population.get(0).fitness;
//                        // in ra file exel
//                        row = sheet.createRow(rownum++);
//                        int cellnum = 0;
//                        cell = row.createCell(cellnum++);
//                        cell.setCellValue(rownum - 1);
//                        cell = row.createCell(cellnum++);
//                        cell.setCellValue((double) population.get(0).fitness);
//                        cell = row.createCell(cellnum++);
//                        cell.setCellValue(population.get(0).areas);
//                        cell = row.createCell(cellnum++);
//                        cell.setCellValue((t2 - t1) / 1000.0);
//                        cell = row.createCell(cellnum++);
//                        cell.setCellValue(upperBound);
//                        // in du lieu ra file text
//                        population.get(0).write(fw);
//                    }
//                    // in ra ket qua trung binh
//                    timeTB = timeTB / NBRUN;
//                    areaTB = areaTB / NBRUN;
//                    fitnessTB = fitnessTB / NBRUN;
//                    row = sheet.createRow(rownum++);
//                    int cellnum = 0;
//                    cell = row.createCell(cellnum++);
//                    cell.setCellValue("Trung Binh");
//                    cell = row.createCell(cellnum++);
//                    cell.setCellValue((double) fitnessTB);
//                    cell = row.createCell(cellnum++);
//                    cell.setCellValue(areaTB);
//                    cell = row.createCell(cellnum++);
//                    cell.setCellValue(timeTB);
//                    cell = row.createCell(cellnum++);
//                    cell.setCellValue(upperBound);
//                    // dong file
//                    fw.close();
//                    try {
//                        String strWrite1 = "Output_ICS\\" + i + "-" + j + ".xlsx";
//                        FileOutputStream out = new FileOutputStream(new File(strWrite1));
//                        workbook.write(out);
//                        out.close();
//                        System.out.println("Done " + i + "-" + j + ".txt");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    System.out.println("anh quan dep trai");
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    System.out.println("anh quan khong dep trai");
//                }
//            }
//        }
//        System.out.println("Done All");
//    }
//
//    public static void evol(ArrayList<Obstacle> listObstacle, int generation) {
//        Individual cockoo = new Individual();
////		int indexCockoo = random.nextInt(CUCKOOSIZE);
//        double x = 0;
//        double y = 0;
//        for (int j = 0; j < CUCKOOSIZE; j++) {
//            for (int i = 0; i < nbSensor; i++) {
//                double p = MyRandom.R.nextDouble();
//                double q = MyRandom.R.nextDouble();
//                if (p > 0.5) {
//                    p = 1;
//                } else {
//                    p = -1;
//                }
//                if (q > 0.5) {
//                    q = 1;
//                } else {
//                    q = -1;
//                }
//                Gene trunggian = cuckoo.get(j).listGene.get(i);
//                x = trunggian.x + p * alpha(generation) * levy(generation);
//                if (x < trunggian.r) {
//                    x = trunggian.r;
//                }
//                if (x > 100 - trunggian.r) {
//                    x = 100 - trunggian.r;
//                }
//                y = trunggian.y + q * alpha(generation) * levy(generation);
//                if (y < trunggian.r) {
//                    y = trunggian.r;
//                }
//                if (y > 100 - trunggian.r) {
//                    y = 100 - trunggian.r;
//                }
//                cockoo.listGene.add(new Gene(x, y, trunggian.r));
//            }
//            mutation2(listObstacle, cockoo);
//            cockoo.fitness(listObstacle, r1);
//            if (cuckoo.get(j).fitness > cockoo.fitness) {
//                change(cuckoo.get(j), cockoo);
//                cuckoo.get(j).fitness = cockoo.fitness;
//            }
//            int indexNest = random.nextInt(SIZE);
//            if (cuckoo.get(j).fitness < population.get(indexNest).fitness) {
//                change(population.get(indexNest), cuckoo.get(j));
//                population.get(indexNest).fitness(listObstacle, r1);
//            }
//            double phathien = MyRandom.R.nextDouble();
//            if (phathien < pa(generation)) {
//                Individual tmp;
//                double pb = MyRandom.R.nextDouble();
//                if (pb > 0.8) {
//                    tmp = khoitaoHeuristic(listObstacle);
//                } else {
//                    tmp = khoitaoRandom(listObstacle);
//                }
//                change(population.get(indexNest), tmp);
//                population.get(indexNest).fitness(listObstacle, r1);
//            }
//        }
//    }
//
//    public static double levy(int index) {
//        double trunggian = Math.pow(index, -1.5);
//        return trunggian;
//    }
//
//    private static void change(Individual indi1, Individual indi2) {
//        for (int i = 0; i < nbSensor; ++i) {
//            indi1.listGene.get(i).x = indi2.listGene.get(i).x;
//            indi1.listGene.get(i).y = indi2.listGene.get(i).y;
//            indi1.listGene.get(i).r = indi2.listGene.get(i).r;
//        }
//    }
//
//    private static double alpha(int generation) {
//        double c = Math.log(alphaMIN / alphaMAX) / NBRUN;
//        return (alphaMAX * Math.exp(c * generation));
//    }
//
//    private static double pa(int generation) {
//        return (paMAX - generation * (paMAX - paMIN) / NBRUN);
//    }
//    private static Individual khoitaoRandom(ArrayList<Obstacle> listObstacle) {
//        Individual tmp = new Individual();
//        int k;
//        for (k = 0; k < nbT1; ++k) {
//            double x1 = 100 * MyRandom.R.nextDouble();
//            double y1 = 100 * MyRandom.R.nextDouble();
//            if (x1 + r1 > 100) {
//                x1 = 100 - r1;
//            } else {
//                if (x1 - r1 < 0) {
//                    x1 = r1;
//                }
//            }
//            if (y1 + r1 > 100) {
//                y1 = 100 - r1;
//            } else {
//                if (y1 - r1 < 0) {
//                    y1 = r1;
//                }
//            }
//            tmp.listGene.add(new Gene(x1, y1, r1));
//        }
//        for (; k < nbT1 + nbT2; ++k) {
//            double x1 = 100 * MyRandom.R.nextDouble();
//            double y1 = 100 * MyRandom.R.nextDouble();
//            if (x1 + r2 > 100) {
//                x1 = 100 - r2;
//            } else {
//                if (x1 - r2 < 0) {
//                    x1 = r2;
//                }
//            }
//            if (y1 + r2 > 100) {
//                y1 = 100 - r2;
//            } else {
//                if (y1 - r2 < 0) {
//                    y1 = r2;
//                }
//            }
//            tmp.listGene.add(new Gene(x1, y1, r2));
//        }
//        for (; k < nbSensor; ++k) {
//            double x1 = 100 * MyRandom.R.nextDouble();
//            double y1 = 100 * MyRandom.R.nextDouble();
//            if (x1 + r3 > 100) {
//                x1 = 100 - r3;
//            } else {
//                if (x1 - r3 < 0) {
//                    x1 = r3;
//                }
//            }
//            if (y1 + r3 > 100) {
//                y1 = 100 - r3;
//            } else {
//                if (y1 - r3 < 0) {
//                    y1 = r3;
//                }
//            }
//            tmp.listGene.add(new Gene(x1, y1, r3));
//        }
//        mutation2(listObstacle, tmp);
//        tmp.normal(listObstacle);
//        tmp.fitness(listObstacle, r1);
//        return tmp;
//    }
//    private static Individual khoitaoHeuristic(ArrayList<Obstacle> listObstacle) {
//        Individual tmp = new Individual();
//
//        for (int j = 0; j < nbT1; ++j) {
//            tmp.listGene.add(new Gene(r1));
//        }
//        for (int j = 0; j < nbT2; ++j) {
//            tmp.listGene.add(new Gene(r2));
//        }
//        for (int j = 0; j < nbT3; ++j) {
//            tmp.listGene.add(new Gene(r3));
//        }
//        mutation2(listObstacle, tmp);
//        tmp.normal(listObstacle);
//        tmp.fitness(listObstacle, r1);
//        return tmp;
//    }
//}
