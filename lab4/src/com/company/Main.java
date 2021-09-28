package com.company;

import Comparator.*;
import  Beer.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Main {

    public static BufferedReader be = new BufferedReader(new InputStreamReader(System.in));
    public static ArrayList<Beer> rekesz = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        while(true) {
            String olvasott[] = be.readLine().split(" ");

            if (olvasott[0].equals("exit"))
                System.exit(0);

            if (olvasott[0].equals("add")) add(new Beer(olvasott[1], olvasott[2], Double.parseDouble(olvasott[3])));
            else if (olvasott[0].equals("list")) list(olvasott);
            else if (olvasott[0].equals("save")) save(olvasott[1]);
            else if (olvasott[0].equals("load")) load(olvasott[1]);
            else if (olvasott[0].equals("search")) search(olvasott[1]);
            else if(olvasott[0].equals("find")) find(olvasott[1]);
            else if(olvasott[0].equals("delete")) delete(olvasott[1]);
        }



    }

    public static void add(Beer b){
        rekesz.add(b);
    }

    public static void list(String[] olvasott){
        if(olvasott.length>1) {
            Comparator<Beer> cmp = null;
            if (olvasott[1].equals("name"))
                cmp = new NameComparator();

            else if (olvasott[1].equals("style"))
                cmp = new StyleComparator();

            else if (olvasott[1].equals("strength"))
                cmp = new StrengthComparator();

            Collections.sort(rekesz, cmp);
        }

        for (int i = 0; i < rekesz.size(); i++)
            System.out.println(rekesz.get(i));
    }

    public static void delete(String s){
        Iterator<Beer> it = rekesz.iterator();
        while(it.hasNext()){
            Beer n = it.next();
            if(n.getName().equals(s))
                it.remove();
        }
    }

    public static void search(String s){
        for (Beer b : rekesz)
            if (s.equals(b.getName()))
                System.out.println(b.getName());
    }

    public static void find(String s){

    }

    public static void load(String s) throws ClassNotFoundException, IOException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(s));
        rekesz = (ArrayList<Beer>) is.readObject();
        is.close();
    }

    public static void save(String s) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(s));
        os.writeObject(rekesz);
        os.close();
    }
}
