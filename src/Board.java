import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int numberRows = 9;

    private final Piece[][] board = new Piece[numberRows][numberRows];

    public Board(){
        //        Setting up white team
        this.board[1][1] = new Rook("\u2656", Color.WHITE, "Rook", "R");
        this.board[1][2] = new Knight("\u2658", Color.WHITE, "Knight", "K");
        this.board[1][3] = new Bishop("\u2657", Color.WHITE, "Bishop", "B");
        this.board[1][4] = new Queen("\u2655", Color.WHITE, "Queen", "Q");
        this.board[1][5] = new King("\u2654", Color.WHITE, "King", "M");
        this.board[1][6] = new Bishop("\u2657", Color.WHITE, "Bishop", "B");
        this.board[1][7] = new Knight("\u2658", Color.WHITE, "Knight", "K");
        this.board[1][8] = new Rook("\u2656", Color.WHITE, "Rook", "R");

        for (int i = 1; i <= 8; i++) {
            this.board[2][i] = new Pawn("\u2659", Color.WHITE, "Pawn", "P");
        }

//        Setting up black team
        this.board[8][1] = new Rook("\u265C", Color.BLACK, "Rook", "R");
        this.board[8][2] = new Knight("\u265E", Color.BLACK, "Knight", "K");
        this.board[8][3] = new Bishop("\u265D", Color.BLACK, "Bishop", "B");
        this.board[8][4] = new Queen("\u265B", Color.BLACK, "Queen", "Q");
        this.board[8][5] = new King("\u265A", Color.BLACK, "King", "M");
        this.board[8][6] = new Bishop("\u265D", Color.BLACK, "Bishop", "B");
        this.board[8][7] = new Knight("\u265E", Color.BLACK, "Knight", "K");
        this.board[8][8] = new Rook("\u265C", Color.BLACK, "Rook", "R");

        for (int i = 1; i <= 8; i++) {
            this.board[7][i] = new Pawn("\u265F", Color.BLACK, "Pawn", "P");
        }
    }

    public void printBoardWhite(){
        System.out.println("x-----x-----x-----x-----x-----x-----x-----x-----x");
        for (int i = board.length - 1; i >= 1; i--) {
            for (int j = 1; j < board[i].length; j++) {
                if (j == 1) {
                    System.out.print("|");
                }
                if (board[i][j] != null) {
                    System.out.print("  " + board[i][j].letter + "  ");
                } else {
                    if ((i + j) % 2 != 0) {
                        System.out.print("  1  ");
                    } else {
                        System.out.print("  2  ");
                    }
                }
                System.out.print("|");
                if (j == 8) {
                    System.out.print(" " + (i));
                }
            }

            System.out.println("\nx-----x-----x-----x-----x-----x-----x-----x-----x");
            if (i == 1) {
                for (int k = 0; k < 8; k++) {
                    System.out.print("   " + (char)(k + 97) + "  ");
                }
            }
        }
        System.out.println();
    }

    public void printBoardBlack(){
        System.out.println("x-----x-----x-----x-----x-----x-----x-----x-----x");
        for (int i = 1; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 1; j--) {
                if (j == board[i].length - 1) {
                    System.out.print("|");
                }
                if (board[i][j] != null) {
                    System.out.print("  " + board[i][j].letter + "  ");
                } else {
                    if ((i + j) % 2 == 0) {
                        System.out.print("  1  ");
                    } else {
                        System.out.print("  2  ");
                    }
                }
                System.out.print("|");
                if (j == 1) {
                    System.out.print(" " + (i));
                }
            }

            System.out.println("\nx-----x-----x-----x-----x-----x-----x-----x-----x");
            if (i == 8) {
                for (int k = 8; k > 0; k--) {
                    System.out.print("   " + (char)(k + 96) + "  ");
                }
            }
        }
        System.out.println();
    }


    public boolean notInBounds(int row, int column){
        return ((row < 1 || row >= board.length) || (column < 1 || column >= board.length));
    }

    public int[] findKing(Piece[][] board, Color color){
        int[] kingPosition = new int[2];
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color && board[i][j].letter.equals("M")) {
                        kingPosition[0] = i;
                        kingPosition[1] = j;
                    }
                }
            }
        }
        return kingPosition;
    }

    public boolean isInCheckForNextMove(int rowFrom, int columnFrom, Piece selectedPiece, int[] move){
            Piece[][] tempBoard = copyFullBoard();
            int moveRow = move[0];
            int moveColumn = move[1];
            tempBoard[moveRow][moveColumn] = tempBoard[rowFrom][columnFrom];
            tempBoard[rowFrom][columnFrom] = null;
            return isInCheck(tempBoard, selectedPiece.color);
    }

    public boolean isInCheck(Piece[][] board, Color color){
        int[] kingPosition = findKing(board,color);
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[i].length; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.color != color){
                    ArrayList<int[]> possibleMoves = piece.move(i,j,board);
                    for (int[] move : possibleMoves) {
                        if (move[0] == kingPosition[0] && move[1] == kingPosition[1]){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInMate(Piece[][] board, Color color){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = getBoardCell(i,j);
                if (piece != null && piece.color == color){
                    ArrayList<int[]> possibleMoves = piece.move(i,j, board);
                    for (int[] move: possibleMoves) {
                        if (!isInCheckForNextMove(i,j,piece, move)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public Piece[][] copyFullBoard(){
        Piece[][] boardCopy = new Piece[board.length][];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return boardCopy;
    }

    public Piece[][] getFullBoard(){
        return board;
    }

    public Piece getBoardCell(int row, int column){
        return board[row][column];
    }

    public void setBoardCell(int rowFrom, int columnFrom, int rowTo, int columnTo, Piece piece){
        board[0][0] = board[rowTo][columnTo];
        System.out.println("New captured piece: " + board[0][0]);
        board[rowTo][columnTo] = piece;
        board[rowFrom][columnFrom] = null;
    }

}
