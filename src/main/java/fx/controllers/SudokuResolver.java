package fx.controllers;

import java.util.ArrayList;
import java.util.List;
import fx.models.*;

public class SudokuResolver {

    private final int MAX_ITERATION = 50;
    private SudokuStructure structure;
    private SudokuMatrix matrix;

    public void resolve(SudokuMatrix matrix){
        this.matrix = matrix;
        resolve(false, 0);
    }

    private void resolve(boolean repeat, int count){
        if(count >= MAX_ITERATION){
            System.out.println("Numero di iterazioni (" + MAX_ITERATION + ") superato!");
            return;
        }
        System.out.println("Iterazione numero " + count++);
        structure = new SudokuStructure();
        boolean found = false;
        for (int i = 0; i < matrix.getDimention(); i++) {
            for (int j = 0; j < matrix.getDimention(); j++) {
                if(matrix.getFromPosition(i, j) != 0) continue;
                repeat = true;
                found |= handleEmptyPosition(i, j);
            }
        }
        if(repeat && !found){
            System.out.println("Avvio analisi avanzata");
            runAdvancedAnalysis(structure.getOptions());
        }
        if(repeat) resolve(false, count);
    }

    private boolean handleEmptyPosition(int i, int j){
        List<Integer> list = getAllPossibilities(i, j);
        if(list.size() == 1) {
            System.out.println("Inserito numero " + list.get(0) + " nella posizione " + i + " - " + j);
            matrix.setIntoPosition(i, j, list.get(0));
            return true;
        }else{
            structure.addToOptions(i, j, matrix.getBoxFromPosition(i, j), list);
            return false;
        }
    }

    public List<Integer> getAllPossibilities(int x, int y){
        List<Integer> list = new ArrayList<>();
        for (int n = 1; n <= matrix.getDimention(); n++) {
            if(matrix.setIntoPosition(x, y, n)){
                matrix.setIntoPosition(x, y, 0);
                list.add(n);
            }
        }
        return list;
    }

    private void runAdvancedAnalysis(List<SudokuStrutureOptions> options){
        //TODO
    }

}
