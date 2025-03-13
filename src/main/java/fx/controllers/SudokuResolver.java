package fx.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fx.models.SudokuMatrix;
import fx.models.SudokuStructure;
import fx.models.SudokuStrutureOptions;

public class SudokuResolver {

    private final int MAX_ITERATION = 30;
    private SudokuStructure structure;
    private SudokuMatrix matrix;

    public void resolve(SudokuMatrix matrix){
        this.matrix = matrix;
        resolve( 0);
    }

    private void resolve(int count){
        System.out.println("Iterazione numero " + count++);
        structure = new SudokuStructure();
        boolean found = false;
        for (int i = 0; i < matrix.getDimention(); i++) {
            for (int j = 0; j < matrix.getDimention(); j++) {
                if(matrix.getFromPosition(i, j) != 0) continue;
                found |= handleEmptyPosition(i, j);
            }
        }
        if(!found){
            if(count >= MAX_ITERATION){
                System.out.println("Numero di iterazioni (" + MAX_ITERATION + ") superato!\nInizio ipotesi ricorsive.");
                Optional<SudokuStrutureOptions> oop = structure.getOptions().stream()
                                                               .filter(x -> x.Possibilities().size() == 2)
                                                               .findFirst();
                if(oop.isPresent()){
                    SudokuStrutureOptions op = oop.get();
                    int[][] m1 = matrix.getFullMatrix();
                    int[][] m2 = matrix.getFullMatrix();
                    m1[op.X()][op.Y()] = op.Possibilities().get(0);
                    m2[op.X()][op.Y()] = op.Possibilities().get(1);
                    int dim = (int)Math.sqrt(matrix.getDimention());
                    SudokuMatrix ma1 = new SudokuMatrix(dim, m1);
                    SudokuMatrix ma2 = new SudokuMatrix(dim, m2);
                    SudokuResolver re1 = new SudokuResolver();
                    SudokuResolver re2 = new SudokuResolver();
                    re1.resolve(ma1);
                    re2.resolve(ma2);
                    if(ma1.isSolved() || ma2.isSolved()){
                        int num = ma1.isSolved() ? op.Possibilities().get(0) : op.Possibilities().get(1);
                        matrix.setIntoPosition(op.X(), op.Y(), num);
                        resolve(0);
                    }
                     return;
                }else return;
            }
            System.out.println("Avvio analisi avanzata");
            runAdvancedAnalysis(structure.getOptions());
            System.out.println("Fine analisi avanzata");
        }
        if(!matrix.isSolved()) resolve(count);
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
        for (int i = 0; i < matrix.getDimention(); i++) {
            runAdvancedAnalysisIntoPartition((s) -> s.X(), i, options);
            runAdvancedAnalysisIntoPartition((s) -> s.Y(), i, options);
            runAdvancedAnalysisIntoPartition((s) -> s.A(), i, options);
        }
    }

    private void runAdvancedAnalysisIntoPartition(SudokuDataGetter filter, int target, List<SudokuStrutureOptions> options){
        runAdvancedAnalysisIntoPartition(new ArrayList<>(options.stream().filter(x -> filter.get(x) == target).toList()), options);
    }

    private void runAdvancedAnalysisIntoPartition(List<SudokuStrutureOptions> options, List<SudokuStrutureOptions> fullOptions){
        Set<Integer> nums = new HashSet<>();
        for(SudokuStrutureOptions op : options){
            nums.addAll(op.Possibilities());
        }
        for (Integer num : nums) {
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < options.size(); i++) {
                if(options.get(i).Possibilities().contains(num)){
                    list.add(i);
                }
            }
            if(list.size() == 1){
                SudokuStrutureOptions op = options.get(list.get(0));
                System.out.println("Inserito numero " + num + " nella posizione " + op.X() + " - " + op.Y());
                matrix.setIntoPosition(op.X(), op.Y(), num);
                options.remove(op);
                options.stream().forEach(x -> x.removePossibility(num));
                fullOptions.remove(op);
                fullOptions.stream().filter(x -> x.A() == op.A() || x.X() == op.X() || x.Y() == op.Y())
                                    .forEach(x -> x.removePossibility(num));
            }else if(list.size() == 2){
                SudokuStrutureOptions op1 = options.get(list.get(0));
                SudokuStrutureOptions op2 = options.get(list.get(1));
                if(op1.Possibilities().size() == 2 && op2.Possibilities().size() == 2 && op1.Possibilities().containsAll(op2.Possibilities())){
                    System.out.println("Esclusione numeri per coppia.");
                    options.stream().filter(x -> x != op1 && x != op2).forEach(x -> op1.Possibilities().forEach(y -> x.removePossibility(y)));
                    fullOptions.stream().filter(x -> x != op1 && x != op2)
                                    .filter(x -> (x.A() == op1.A() && x.A() == op2.A()) || 
                                                    (x.X() == op1.X() && x.X() == op2.X()) || 
                                                    (x.Y() == op1.Y() && x.Y() == op2.Y()))
                                    .forEach(x -> op1.Possibilities().forEach(y -> x.removePossibility(y)));
                }
            }
        }
    }

}
