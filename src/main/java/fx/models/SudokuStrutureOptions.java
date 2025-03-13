package fx.models;

import java.util.ArrayList;
import java.util.List;

public class SudokuStrutureOptions{

    private int x, y, a;
    private List<Integer> possibilities;

    public SudokuStrutureOptions(int x, int y, int a, List<Integer> possibilities){
        this.x = x;
        this.y = y;
        this.a = a;
        this.possibilities = possibilities;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public int A() {
        return a;
    }

    public List<Integer> Possibilities() {
        return new ArrayList<>(this.possibilities);
    }

    public boolean  removePossibility(Integer possibility) {
        return this.possibilities.remove(possibility);
    }
    
}
