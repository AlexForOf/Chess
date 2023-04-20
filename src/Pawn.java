import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece{
    protected Pawn(String code, Color color, String name, String letter) {
        super(code, color, name, letter);
    }



    @Override
    protected ArrayList<int[]> move(int row, int column, Piece[][] board) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int team = color == Color.WHITE ? 1 : -1;

        int[] oneForward = {row + team, column};
        if ((team == 1 && row == 2) || (team == -1 && row == 7)){
            int[] twoForward = {row + team*2, column};

            if (board[oneForward[0]][oneForward[1]] == null){
                possibleMoves.add(oneForward);
                if (board[twoForward[0]][twoForward[1]] == null){
                    possibleMoves.add(twoForward);
                }
            }
        }else{
            if (board[oneForward[0]][oneForward[1]] == null){
                possibleMoves.add(oneForward);
            }
        }
        int[] leftEliminate = {-1, -1};
        int[] rightEliminate = {-1, -1};

        if (row != 7 && row != 2) {
            if (column != 0) {
                leftEliminate[0] = row + team;
                leftEliminate[1] = column - 1;
            }
            if (column != 7) {
                rightEliminate[0] = row + team;
                rightEliminate[1] = column + 1;
            }
        }

        if (leftEliminate[0] != -1 && board[leftEliminate[0]][leftEliminate[1]] != null
                && board[leftEliminate[0]][leftEliminate[1]].color != color){
            possibleMoves.add(leftEliminate);
        }
        if (rightEliminate[0] != -1 && board[rightEliminate[0]][rightEliminate[1]] != null
                && board[rightEliminate[0]][rightEliminate[1]].color != color){
            possibleMoves.add(rightEliminate);
        }

        if ((team == 1 && row == 7) || (team == -1 && row == 2)){
            System.out.println("Move to promotion!");
            int[] promotionMove = {row+team, column};
            possibleMoves.add(promotionMove);
        }
        return possibleMoves;
    }
}
