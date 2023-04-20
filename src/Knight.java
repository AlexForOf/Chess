import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece{
    protected Knight(String code, Color color, String name,String letter) {
        super(code, color, name, letter);
    }

    private final int[][] allMoves = {{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1}, {1,2}, {-1,2}, {-2,1}};
    @Override
    protected ArrayList<int[]> move(int row, int column, Piece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        for (int[] newMove : allMoves) {
            int newRow = row + newMove[0];
            int newColumn = column + newMove[1];

            if (newRow >= 1 && newRow < board.length && newColumn >= 1 && newColumn < board.length){
                    Piece piece = board[newRow][newColumn];
                    if (piece != null) {
                        if (piece.color != this.color) {
                            possibleMoves.add(new int[]{newRow, newColumn});
                        }
                    } else {
                        possibleMoves.add(new int[]{newRow, newColumn});
                    }
            }
        }

        return possibleMoves;
    }
}
