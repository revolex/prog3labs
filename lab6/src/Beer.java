import java.io.Serializable;

public class Beer implements Serializable {
    private String _name;
    private String _style;
    private double _strength;

    public Beer(String name, String style, double strength) {
        _name = name;
        _style = style;
        _strength = strength;
    }

    public String get_name() {
        return _name;
    }

    public String get_style() {
        return _style;
    }

    public double get_strength() {
        return _strength;
    }

    public String toString() {
        return "Name: "+_name+"; Style: "+_style+"; Strength: "+_strength;
    }
}
