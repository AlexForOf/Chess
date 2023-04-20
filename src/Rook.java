import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece{

    protected Rook(String code, Color color, String name, String letter) {
        super(code, color, name, letter);
    }


    private final int[][] allDirections = {{-1,0},{0,-1},{1,0},{0,1}};

    protected int[][] getAllDirections(){
        return allDirections;
    }

    @Override
    protected ArrayList<int[]> move(int row, int column, Piece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        for (int[] dir : allDirections) {
            int dirRow = dir[0];
            int dirColumn = dir[1];
            for (int i = 1; i < board.length; i++) {
                int newRow = row + i*dirRow;
                int newColumn = column + i*dirColumn;

                if (newRow < 0 || newRow >= board.length || newColumn < 0 || newColumn >= board.length){
                    break;
                }
//                if (checkValidMove(row,column,board,new int[]{newRow,newColumn})) {
                    Piece piece = board[newRow][newColumn];
                    if (piece != null) {
                        if (piece.color != this.color) {
                            possibleMoves.add(new int[]{newRow, newColumn});
                        }
                        break;
                    }
                    possibleMoves.add(new int[]{newRow, newColumn});
//                }
            }
        }
        return possibleMoves;
    }
}
