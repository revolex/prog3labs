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

            if (olvasott[0].equals(new String("exit")))
                System.exit(0);

            if (olvasott[0].equals("add"))
                add(new Beer(olvasott[1], olvasott[2], Double.parseDouble(olvasott[3])));

            else if (olvasott[0].equals("list")) {
                Comparator<Beer> cmp = null;
                if (olvasott[1].equals("name"))
                    cmp = new NameComparator();

                else if (olvasott[1].equals("style"))
                    cmp = new StyleComparator();

                else if (olvasott[1].equals("strength"))
                    cmp = new StrengthComparator();

                Collections.sort(rekesz, cmp);

                for (int i = 0; i < rekesz.size(); i++)
                    System.out.println(rekesz.get(i));
            } else if (olvasott[0].equals("save")) {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(olvasott[1]));
                os.writeObject(rekesz);
                os.close();
            } else if (olvasott[0].equals("load")) {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(olvasott[1]));
                rekesz = (ArrayList<Beer>) is.readObject();
                is.close();
            } else if (olvasott[0].equals("search")){
                for (Beer b : rekesz)
                    if (olvasott[1].equals(b.getName()))
                        System.out.println(b.getName());
            }
            else if(olvasott[0].equals("find")) {
                for (Beer b : rekesz)
                    if (b.getName().contains(olvasott[1]))
                        System.out.println(b.getName());
            }
            else if(olvasott[0].equals("delete")){
                Iterator<Beer> it = rekesz.iterator();
                while(it.hasNext()){
                    Beer n = it.next();
                    if(n.getName().equals(olvasott[1]))
                        it.remove();
                }
            }
        }



    }

    public static void add(Beer b){
        rekesz.add(b);
    }
}
