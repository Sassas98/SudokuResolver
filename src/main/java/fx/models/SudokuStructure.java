package fx.models;

import java.util.ArrayList;
import java.util.List;

public class SudokuStructure {

    private List<SudokuStrutureOptions> options;

    public SudokuStructure(){
        options = new ArrayList<>();
    }

    public boolean addToOptions(int x, int y, int a, List<Integer> possibilities){
        if(options.stream().anyMatch(o -> o.A() == a && o.X() == x && o.Y() == y))
            return false;
        options.add(new SudokuStrutureOptions(x, y, a, possibilities));
        return true;
    }

    public List<SudokuStrutureOptions> getOptions() {
        return new ArrayList<>(options);
    }

    

}
