import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Window {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        Frame frame = new Frame("Chess");
        Game game = new Game();
        ChessBoard board = new ChessBoard();
//        frame.add(board);
//
//        frame.pack();
//        frame.setVisible(true);
        game.startGame();
    }
}
