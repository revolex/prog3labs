import java.util.Comparator;

public class StrengthComparator implements Comparator<Beer> {
    public int compare(Beer b1, Beer b2) {
        if(b1.get_strength() == b2.get_strength()) return 0;
        else if(b1.get_strength() < b2.get_strength()) return -1;
        else return 1;
    }
}
