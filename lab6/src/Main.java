import java.io.*;
import java.util.*;

public class Main {
    private static ArrayList<Beer> beers = new ArrayList<Beer>();
    private static Map<String, Command> commands = new HashMap<>();
    private static Map<String, Comparator<Beer>> comparators = new HashMap<>();

    static {
        comparators.put("name",Comparator.comparing((a) -> a.get_name()));
        comparators.put("style",Comparator.comparing((a) -> a.get_style()));
        comparators.put("strength",Comparator.comparing((a) -> a.get_strength()));
    }
    public static void storeCommands() {
        commands.put("exit",Main::exit);
        commands.put("add",Main::add);
        commands.put("list",Main::list);
        commands.put("save",Main::save);
        commands.put("load",Main::load);
        commands.put("search",Main::search);
        commands.put("find",Main::find);
        commands.put("delete",Main::delete);
    }

    protected static void add(String[] cmd) {
        beers.add(new Beer(cmd[1],cmd[2],Double.parseDouble(cmd[3])));
    }

    protected static void list(String[] cmd) {
        if(cmd.length>1) {
            Comparator<Beer> cmp = comparators.get("name");
            if(comparators.containsKey(cmd[1])) {
                cmp = comparators.get(cmd[1]);
            }
            beers.sort(cmp);
        }
        for(Beer b : beers) {
            System.out.println(b.toString());
        }
    }

    protected static void save(String[] cmd) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cmd[1]));
            out.writeObject(beers);
            out.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected static void load(String[] cmd) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(cmd[1]));
            beers = (ArrayList<Beer>)in.readObject();
            in.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static void search(String[] cmd) {
        if(cmd.length>2) {
            if(cmd[1].equals("name")){
                for(Beer b : beers) {
                    if(b.get_name().equals(cmd[2])) System.out.println(b.toString());
                }
            } else if(cmd[1].equals("style")){
                for(Beer b : beers) {
                    if(b.get_style().equals(cmd[2])) System.out.println(b.toString());
                }
            } else if(cmd[1].equals("strength")){
                for(Beer b : beers) {
                    if(b.get_strength() == Double.parseDouble(cmd[2])) System.out.println(b.toString());
                }
            }
        } else {
            for(Beer b : beers) {
                if(b.get_name().equals(cmd[1])) System.out.println(b.toString());
            }
        }
    }

    protected static void find(String[] cmd) {
        if(cmd.length>2) {
            if(cmd[1].equals("name")){
                for(Beer b : beers) {
                    if(b.get_name().contains(cmd[2])) System.out.println(b.toString());
                }
            } else if(cmd[1].equals("style")){
                for(Beer b : beers) {
                    if(b.get_style().contains(cmd[2])) System.out.println(b.toString());
                }
            } else if(cmd[1].equals("strength")){
                for(Beer b : beers) {
                    if(b.get_strength() >= Double.parseDouble(cmd[2])) System.out.println(b.toString());
                }
            } else if (cmd[1].equals("weaker")) {
                for(Beer b : beers) {
                    if(b.get_strength() <= Double.parseDouble(cmd[2])) System.out.println(b.toString());
                }
            }
        } else {
            for(Beer b : beers) {
                if(b.get_name().contains(cmd[1])) System.out.println(b.toString());
            }
        }
    }

    protected static void delete(String[] cmd) {
        Iterator<Beer> i = beers.iterator();

        while(i.hasNext()) {
            Beer read = i.next();
            if(read.get_name().equals(cmd[1])) i.remove();
        }
    }

    protected static void exit(String[] cmd) {
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        storeCommands();

        while(true) {
            String line[] = br.readLine().split(" ");
            if(!commands.containsKey(line[0])) {
                System.out.println("Nincs ilyen parancs!");
            } else {
                commands.get(line[0]).execute(line);
            }
        }
    }
}
