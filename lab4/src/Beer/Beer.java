package Beer;

public class Beer {
    String name,style;
    double strength;

    public Beer(String n, String s, double f){
        name=n;
        style=s;
        strength=f;
    }
    public String getName(){ return name;}
    public String getStyle(){ return style;}
    public double getStrength(){ return strength;}
    public String toString(){return name + " " + style + " "+ strength;}
}
