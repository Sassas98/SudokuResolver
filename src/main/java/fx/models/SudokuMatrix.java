package fx.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuMatrix {

    private int[][] matrix;
    private final int DIM;

    public SudokuMatrix(int dim){
        this.DIM = dim;
        matrix = new int[dim*dim][dim*dim];
    }
    public SudokuMatrix(int dim, int[][] matrix){
        if(dim*dim != matrix.length && dim*dim != matrix[0].length)
            throw new IllegalArgumentException("Matrice inserita di dimensione sbagliata");
        this.DIM = dim;
        this.matrix = new int[dim*dim][dim*dim];
        for (int i = 0; i < dim*dim; i++) {
            for (int j = 0; j < dim*dim; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            if(!checkPartialSequence(getRow(i))) throw new IllegalArgumentException("Matrice inserita con riga sbagliata (" + i + ")");
        }
        for (int i = 0; i < matrix.length; i++) {
            if(!checkPartialSequence(getColumn(i))) throw new IllegalArgumentException("Matrice inserita con colonna sbagliata (" + i + ")");
        }
        for (int i = 0; i < matrix.length; i++) {
            if(!checkPartialSequence(getBox(i))) throw new IllegalArgumentException("Matrice inserita con area sbagliata (" + i + ")");
        }
    }

    public int getDimention(){
        return DIM*DIM;
    }

    public boolean setIntoPosition(int x, int y, int n){
        if(!(isValidIndex(x) || isValidIndex(y)))
            throw new IllegalArgumentException("coordinate fuori dalla griglia");
        matrix[x][y] = n;
        if(checkPartialSequence(getRow(x)) 
        && checkPartialSequence(getColumn(y)) 
        && checkPartialSequence(getBox(getBoxFromPosition(x, y))))
            return true;
        matrix[x][y] = 0;
        return false;
    }

    private boolean isValidIndex(int i){
        return i >= 0 && i < DIM*DIM;
    }

    private boolean isValidNumber(int i){
        return i > 0 && i <= DIM*DIM;
    }

    public int getFromPosition(int x, int y){
        if(!(isValidIndex(x) || isValidIndex(y)))
            throw new IllegalArgumentException("coordinate fuori dalla griglia");
        return matrix[x][y];
    }

    public int[][] getFullMatrix(){
        int d = DIM*DIM;
        int[][] copy = new int[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }

    public List<Integer> getRow(int i){
        if(!isValidIndex(i))
            throw new IllegalArgumentException("coordinate fuori dalla griglia");
        List<Integer> list = new ArrayList<>();
        for(int n : matrix[i]){
            list.add(n);
        }
        return list;
    }

    public List<Integer> getColumn(int i){
        if(!isValidIndex(i))
            throw new IllegalArgumentException("coordinate fuori dalla griglia");
        List<Integer> list = new ArrayList<>();
        for(int[] n : matrix){
            list.add(n[i]);
        }
        return list;
    }

    public List<Integer> getBox(int i){
        if(!isValidIndex(i))
            throw new IllegalArgumentException("coordinate fuori dalla griglia");
        int x = i%DIM;
        int y = i/DIM;
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < DIM; j++) {
            for (int j2 = 0; j2 < DIM; j2++) {
                list.add(matrix[j + (DIM*x)][j2 + (DIM*y)]);
            }
        }
        return list;
    }

    public boolean checkPartialSequence(List<Integer> seq){
        Set<Integer> set = new HashSet<>();
        for(int n : seq){
            if(n == 0) continue;
            if(!(isValidNumber(n) && set.add(n))) 
                return false;
        }
        return true;
    }

    public boolean checkCompleteSequence(List<Integer> seq){
        Set<Integer> set = new HashSet<>();
        for(int n : seq){
            if(!(isValidNumber(n) && set.add(n))) 
                return false;
        }
        return true;
    }

    public int getBoxFromPosition(int x, int y){
        return (x/DIM) + (3 * (y/DIM));
    }

}
