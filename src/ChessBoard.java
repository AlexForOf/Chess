import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard extends Canvas {
//    Constants that define paint method behavior
    private final int cellSize = 64;
    private final int circleSize = 48;
    private final int numberRows = 8;
    private final char numberColumns = 'h';
    private final int pieceSize = 64;

//    For selecting cells with mouse and moving pieces
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private Piece selectedPiece = null;
    private ArrayList<int[]> possibleMoves = new ArrayList<>();

//    Constants for colors(LMAO)
    private final Color brightCellColor = new Color(255, 206,158);
    private final Color darkCellColor = new Color(209,139,71);

//    board Array that holds all Pieces on it
    private final Piece[][] board = new Piece[numberRows][numberRows];

    public ChessBoard(){
        setPreferredSize(new Dimension(cellSize*numberRows + 10, cellSize*numberRows + 10));

//        Setting up white team
        board[0][0] = new Rook("\u2656", Color.WHITE, "Rook", "R");
        board[0][1] = new Knight("\u2658", Color.WHITE, "Knight", "K");
        board[0][2] = new Bishop("\u2657", Color.WHITE, "Bishop", "B");
        board[0][3] = new Queen("\u2655", Color.WHITE, "Queen", "Q");
        board[0][4] = new King("\u2654", Color.WHITE, "King", "M");
        board[0][5] = new Bishop("\u2657", Color.WHITE, "Bishop", "B");
        board[0][6] = new Knight("\u2658", Color.WHITE, "Knight", "K");
        board[0][7] = new Rook("\u2656", Color.WHITE, "Rook", "R");

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("\u2659", Color.WHITE, "Pawn", "P");
        }

//        Setting up black team
        board[7][0] = new Rook("\u265C", Color.BLACK, "Rook", "R");
        board[7][1] = new Knight("\u265E", Color.BLACK, "Knight", "K");
        board[7][2] = new Bishop("\u265D", Color.BLACK, "Bishop", "B");
        board[7][3] = new Queen("\u265B", Color.BLACK, "Queen", "Q");
        board[7][4] = new King("\u265A", Color.BLACK, "King", "M");
        board[7][5] = new Bishop("\u265D", Color.BLACK, "Bishop", "B");
        board[7][6] = new Knight("\u265E", Color.BLACK, "Knight", "K");
        board[7][7] = new Rook("\u265C", Color.BLACK, "Rook", "R");

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn("\u265F", Color.BLACK, "Pawn", "P");
        }

//          Mouse adapter and listener to perform point-and-click events on board
        addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                System.out.println("Clicked on cell: (" + row + ", " + col + ")");

                if (selectedRow == -1 && selectedColumn == -1){
                    selectedRow = row;
                    selectedColumn = col;
                    selectedPiece = board[row][col];
                    System.out.println("Selected cell: (" + selectedRow + ", " + selectedColumn + ")");
                    System.out.println(selectedPiece);

                    if (selectedPiece != null){
                        possibleMoves = selectedPiece.move(selectedRow, selectedColumn, board);
                    }

                    for (int i = 0; i < possibleMoves.size(); i++) {
                        System.out.println(Arrays.toString(possibleMoves.get(i)));
                    }
                    repaint();
                }
                else if(selectedColumn == col && selectedRow == row){
                    selectedRow = -1;
                    selectedColumn = -1;
                    System.out.println("Selected cell: (" + selectedRow + ", " + selectedColumn + ")");
                    possibleMoves = new ArrayList<>();
                    repaint();
                }else{
                    System.out.println("Selected cell: (" + selectedRow + ", " + selectedColumn + ")");
                    for (int i = 0; i < possibleMoves.size(); i++) {
                        if (row == possibleMoves.get(i)[0] && col == possibleMoves.get(i)[1]){
                            board[selectedRow][selectedColumn] = null;
                            board[row][col] = selectedPiece;
                            selectedPiece.movedBefore = true;
                        }
                    }
                    possibleMoves = new ArrayList<>();
                    System.out.println(selectedPiece);
                    selectedColumn = -1;
                    selectedRow = -1;
                    repaint();
                }
            }
        });
    }

    public Piece[][] getBoard(){
        return board;
    }

    @Override
    public void paint(Graphics g){
        int rowCounter = 0;
        for (int row = 1; row <= numberRows; row++){
            int columnCounter = 0;
            for (char col = 'a'; col <= numberColumns; col++) {
                int x = columnCounter * cellSize;
                int y = rowCounter * cellSize;
                if ((selectedRow == rowCounter) && (selectedColumn == columnCounter)){
                    g.setColor(Color.magenta);
                    g.fillRect(x, y, cellSize, cellSize);
                }else {
                    if ((rowCounter + columnCounter) % 2 == 0) {
                        g.setColor(brightCellColor);
                    } else {
                        g.setColor(darkCellColor);
                    }
                    g.fillRect(x, y, cellSize, cellSize);
                }


                for (int i = 0; i < possibleMoves.size(); i++) {
                    if (possibleMoves.get(i)[0] == rowCounter && possibleMoves.get(i)[1] == columnCounter){
                        g.setColor(Color.GREEN);
                        int putX = x + circleSize/5;
                        int putY = y + circleSize/5;
                        g.fillOval(putX, putY, circleSize, circleSize);
                    }
                }

                Piece piece = board[rowCounter][columnCounter];

                if (piece != null){
                    int piecePlacementY = y + cellSize;
                    g.setColor(piece.color);
                    g.setFont(new Font("Arial Unicode MS", Font.PLAIN, pieceSize));
                    g.drawString(piece.code, x, piecePlacementY);
                }
//                System.out.println(col+""+row);

                columnCounter++;
            }
            rowCounter++;
        }
    }
}
