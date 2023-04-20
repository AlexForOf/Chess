import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);

    private final int numberRows = 8;

    private int columnFrom = -1;
    private int rowFrom = -1;

    private int columnTo = -1;
    private int rowTo = -1;

    private boolean reset = false;
    private boolean canMove = false;
    private boolean checkmate = false;
    private boolean turn = false;

    private final Board chessBoard;
    private Piece selectedPiece = null;
    private ArrayList<int[]> possibleMoves;
    private Color currentColor;

    public Game(){
        this.chessBoard = new Board();
        possibleMoves = new ArrayList<>();
        selectedPiece = null;
    }

    public void loadGame(){

    }


    public void saveGame(){

    }

    public void chooseFromRowColumn(){
        String prompt = null;
        while (chessBoard.notInBounds(rowFrom, columnFrom)){
            System.out.println("Print row and column to move from eg. a1/b3/h5, or print save to save game and exit");
            prompt = scanner.nextLine();
            if (prompt != null){
                if (checkRegex(prompt)){
                    columnFrom = (prompt.toLowerCase().charAt(0) - 96);
                    System.out.println(rowFrom);
                    rowFrom = Character.getNumericValue(prompt.toLowerCase().charAt(1));
                    System.out.println(rowFrom + "" + columnFrom);
                }else if(prompt.equalsIgnoreCase("save")){

                }
                else{
                    System.out.println("Invalid prompt");
                }
            }
        }
        selectedPiece = chessBoard.getBoardCell(rowFrom,columnFrom);
        System.out.println(selectedPiece);
        if (selectedPiece == null){
            System.out.println("Chosen cell is empty");
            rowFrom = -1;
            columnFrom =-1;
        }
    }
    public void chooseToRowColumn(){
        String prompt = null;
        while (chessBoard.notInBounds(rowTo, columnTo)){
            System.out.println("Print row and column to move to eg. a1/b3/h5, or print 'cancel' to change chosen piece");
            prompt = scanner.nextLine();
            if (prompt != null){
                if (checkRegex(prompt)){
                    columnTo = (prompt.toLowerCase().charAt(0)-96);
                    rowTo = Character.getNumericValue(prompt.toLowerCase().charAt(1));
                    System.out.println(rowTo + "" + columnTo);
                }else if(prompt.equalsIgnoreCase("cancel")){
                    System.out.println("Restarting chose");
                    reset = true;
                    break;
                }else{
                    System.out.println("Invalid prompt or command");
                }
            }
        }
    }
    public void checkPieceMoves(){
        if (selectedPiece != null){
            if (selectedPiece.color != currentColor){
                System.out.println("Chosen piece is from the opposing team");
            }else{
                possibleMoves = selectedPiece.move(rowFrom, columnFrom, chessBoard.getFullBoard());
            }

        }
    }
    public boolean checkRegex(String prompt){
        return prompt.matches("^[a-hA-h][1-8]$");
    }
    public void checkIsLegalMove(){
        boolean foundMove = false;
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (rowTo == possibleMoves.get(i)[0] && columnTo == possibleMoves.get(i)[1]){
                chessBoard.setBoardCell(rowFrom,columnFrom,rowTo,columnTo, selectedPiece);
                canMove = true;
                foundMove = true;
                break;
            }
        }
        if(!foundMove){
            System.out.println("Invalid move");
            rowTo = -1;
            columnTo = -1;
        }
    }
    public void displayPossibleMoves(){
        System.out.println("Possible moves for selected piece: ");
        List<int[]> moves = List.copyOf(possibleMoves);
        for (int[] move: moves) {
            if (chessBoard.isInCheckForNextMove(rowFrom, columnFrom, selectedPiece, move)){
                System.out.println("This move will put you under the check!");
                System.out.println("Column: " + (char)(move[1]+96) + ", Row: " + (move[0]) + "\n");
                possibleMoves.remove(move);
            }else{
                System.out.println("Column: " + (char)(move[1]+96) + ", Row: " + (move[0]));
            }
        }
        if (possibleMoves.size() == 0){
            System.out.println("No possible moves for this piece");
        }
    }


    public void resetValues(){
        columnFrom = -1;
        columnTo = -1;

        rowFrom = -1;
        rowTo = -1;
        selectedPiece = null;
        possibleMoves = new ArrayList<>();
        if (!reset){
            turn = !turn;
        }
        canMove = false;
        reset = false;
    }

    public void startGame(){
        while (!checkmate){
            System.out.println("The last captured piece on position {0,0}: " + chessBoard.getFullBoard()[0][0]);

            currentColor = turn ? Color.BLACK : Color.WHITE;
            if(currentColor == Color.WHITE){
                chessBoard.printBoardWhite();
            }else{
                chessBoard.printBoardBlack();
            }
            if (chessBoard.isInCheck(chessBoard.getFullBoard(),currentColor)){
                System.out.println("You are under check!");
                if (chessBoard.isInMate(chessBoard.getFullBoard(), currentColor)){
                    System.out.println("Game over, player " +
                            (currentColor == Color.WHITE ? "White" : "Black") + " is under the mate!");
                    checkmate = true;
                    break;
                }
            }

            System.out.println("Current move of team: " + (turn ? "Black" : "White"));
            while (selectedPiece == null && possibleMoves.size() == 0){
                chooseFromRowColumn();
                checkPieceMoves();
            }
            displayPossibleMoves();
            while (!canMove && !reset){
                chooseToRowColumn();
                checkIsLegalMove();
            }
            resetValues();
        }
    }
}
