import java.awt.*;
import java.util.ArrayList;

public class King extends Piece{
    protected King(String code, Color color, String name,String letter) {
        super(code, color, name, letter);

    }



    @Override
    protected ArrayList<int[]> move(int row, int column, Piece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        for (int dirRow = -1; dirRow <= 1; dirRow++){
            for (int dirCol = -1; dirCol <= 1 ; dirCol++) {
                if (dirRow == row && dirCol == column){
                    continue;
                }

                int newRow = row + dirRow;
                int newColumn = column + dirCol;

                if (newRow < 1 || newRow >= board.length || newColumn < 1 || newColumn >= board.length){
                    continue;
                }
//                if (checkValidMove(row,column,board,new int[]{newRow,newColumn})) {
                    Piece piece = board[newRow][newColumn];
                    if (piece != null) {
                        if (piece.color != this.color) {
                            possibleMoves.add(new int[]{newRow, newColumn});
                        }
                        continue;
                    }

                    possibleMoves.add(new int[]{newRow, newColumn});
//                }
            }
        }
        return possibleMoves;
    }

}
