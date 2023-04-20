import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public abstract class Piece {

    protected String code;
    protected String letter;
    protected Color color;
    protected String name;
    protected boolean movedBefore = false;


    protected Piece(String code, Color color, String name, String letter){
        this.code = code;
        this.color = color;
        this.name = name;
        this.letter = letter;
    }

    abstract protected ArrayList<int[]> move(int row, int column, Piece[][] board);

    @Override
    public String toString(){
        return "This is piece: [" + name + ", in the team: "
                + (color == Color.WHITE ? "White" : "Black") + "]";
    }
}
