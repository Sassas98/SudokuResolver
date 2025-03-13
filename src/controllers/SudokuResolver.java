package controllers;

import java.util.ArrayList;
import java.util.List;

import models.SudokuMatrix;

public class SudokuResolver {

    public void resolve(SudokuMatrix matrix){
        int dimention = matrix.getDimention();
        boolean repeat = true;
        int count = 0;
        while (repeat) {
            System.out.println("Iterazione numero " + count++);
            repeat = false;
            for (int i = 0; i < dimention; i++) {
                for (int j = 0; j < dimention; j++) {
                    if(matrix.getFromPosition(i, j) != 0) continue;
                    repeat = true;
                    List<Integer> list = getAllPossibilities(matrix, i, j);
                    if(list.size() == 1) {
                        System.out.println("Inserito numero " + list.get(0) + " nella posizione " + i + " - " + j);
                        matrix.setIntoPosition(i, j, list.get(0));
                    }
                }
            }
        }
    }

    public List<Integer> getAllPossibilities(SudokuMatrix matrix, int x, int y){
        List<Integer> list = new ArrayList<>();
        for (int n = 1; n <= matrix.getDimention(); n++) {
            if(matrix.setIntoPosition(x, y, n)){
                matrix.setIntoPosition(x, y, 0);
                list.add(n);
            }
        }
        return list;
    }

}
