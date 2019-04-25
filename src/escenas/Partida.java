package escenas;

import config.ConfigurationGame;

import java.io.*;
import java.util.ArrayList;

public class Partida {

    private static File partidasTop10 = new File("src/data/partidastop10.csv");
    private FileReader fr = new FileReader(partidasTop10);
    private BufferedReader br = new BufferedReader(fr);
    private String linea;
    private static ArrayList<String> top10 = new ArrayList<>();
    private int[] bolitasCapturadas = new int[6];
    private double[] time = new double[6];
    private static double bestTime;
    private static double worstTime;
    private static int bestCatch;
    private static int worstCatch;
    private static boolean bestadded;
    private static boolean worstadded;

    public Partida() throws IOException {
        bestadded=false;
        worstadded=false;
        linea = br.readLine();
        int i=0;
        while((linea = br.readLine()) != null)  {
            top10.add(linea);
            i++;
        }
        br.close();
        fr.close();
    }


    public static ArrayList<String> getPartidasTop10() {
        return top10;
    }

    public static void setPartidaToTop10() throws IOException {
        ArrayList<String> newTop10 = new ArrayList<>();
        for(int i=0;i<=9;i++) {
            String[] values = top10.get(i).split(",");
            int bolitasTopTen = Integer.valueOf(values[1]);
            double timeTopTen = Double.valueOf(values[2]);
            /*Si has capturado mas bolitas, y el tiempo es menor, entras en esa posición.
            Si no, se mantiene el top =.
             */
            if(bolitasTopTen < bestCatch && !bestadded) {
                newTop10.add(newTop10.size(),ConfigurationGame.getPlayerName()+","+bestCatch+","+bestTime);
                bestadded = true;
                if(bolitasTopTen < worstCatch && !worstadded) {
                    newTop10.add(newTop10.size(),ConfigurationGame.getPlayerName()+","+worstCatch+","+worstTime);
                    worstadded = true;
                    newTop10.add(newTop10.size(), top10.get(i));
                }else if(bolitasTopTen == bestCatch && !worstadded) {
                    if (timeTopTen > worstTime) {
                        newTop10.add(newTop10.size(), ConfigurationGame.getPlayerName() + "," + worstCatch + "," + worstTime);
                        worstadded = true;
                        newTop10.add(newTop10.size(), top10.get(i));
                    } else if(i<9) {
                        newTop10.add(newTop10.size(), top10.get(i));
                    }
                }else {
                    newTop10.add(newTop10.size(), top10.get(i));
                }
            }else if(bolitasTopTen == bestCatch && !bestadded) {
                if(timeTopTen > bestTime) {
                    newTop10.add(newTop10.size(),ConfigurationGame.getPlayerName()+","+bestCatch+","+bestTime);
                    bestadded = true;
                }else {
                    newTop10.add(newTop10.size(),top10.get(i));
                }
            }else if(bolitasTopTen < worstCatch && !worstadded) {
                newTop10.add(newTop10.size(),ConfigurationGame.getPlayerName()+","+worstCatch+","+worstTime);
                worstadded = true;
            }else if(bolitasTopTen == worstCatch && !worstadded) {
                if(timeTopTen > worstTime) {
                    newTop10.add(newTop10.size(),ConfigurationGame.getPlayerName()+","+worstCatch+","+worstTime);
                    worstadded = true;
                }else {
                    newTop10.add(newTop10.size(),top10.get(i));
                }
            }else {
                newTop10.add(newTop10.size(),top10.get(i));
            }
            System.out.println(newTop10.get(i));
        }
        top10 = newTop10;
        File partidasTop10Temporal = new File("src/data/partidastop10_2.csv");
        FileWriter fw = new FileWriter(partidasTop10Temporal);
        fw.write("Jugador,Bolitas capturadas,Tiempo de partida\n");
        for(int i=0;i<=9;i++) {
            fw.write(top10.get(i)+"\n");
        }
        fw.close();
        partidasTop10.delete();
        partidasTop10Temporal.renameTo(partidasTop10);
    }

    public void setBolitasCapturadas(int partida, int bolitasCapturadas) {
        this.bolitasCapturadas[partida] = bolitasCapturadas;
    }

    public int[] getBolitasCapturadas() {
        return this.bolitasCapturadas;
    }

    public void setTime(int partida, double tiempo) {
        this.time[partida] = tiempo;
    }

    public double[] getTime() {
        return this.time;
    }

    public static double getBestTime() {
        return bestTime;
    }

    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    public static double getWorstTime() {
        return worstTime;
    }

    public void setWorstTime(double worstTime) {
        this.worstTime = worstTime;
    }

    public static int getBestCatch() {
        return bestCatch;
    }

    public void setBestCatch(int bestCatch) {
        this.bestCatch = bestCatch;
    }

    public static int getWorstCatch() {
        return worstCatch;
    }

    public void setWorstCatch(int worstCatch) {
        this.worstCatch = worstCatch;
    }

}
