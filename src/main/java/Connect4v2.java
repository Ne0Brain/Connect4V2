import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class Connect4v2 {
    private int columns=7;
    private int rows=6;
    private String[][] board=new String[columns][rows];
    private String player="R";
    private PrintStream output ;
    public Connect4v2(PrintStream output){
        this.output=output;
    }
    public boolean isBoardEmpty(){
        return true;
    }
    public String play(int column){
        checkColumn( column);
        int height=0;
        int row=getEmptyPosition(column);
        checkIsRowsHaveExceed(row);
        board[column][row]=player;
        printBoard(row);
        getDiscInBoard();
        if(checkIf4SameColorStraightLine())return player+" is winner vertical";
        if(checkIf4SameColorHorizontalLine())return player+" is winner hor";
        if(checkIf4SameColorDiagonalLine())return  player+" is winner diagonal";
        if(checkIf4SameColorInverseDiagonalLine())return player+" is winner Inverse";
        switchPlayer();

        return "";

    }

    public boolean checkIf4SameColorInverseDiagonalLine(){
        int[][] countContinueSameColor=new int[columns][rows];
        for(int row=0;row<(rows-1);row++){
            for(int col=(columns-1);col>0;col--){
                if(player.equals(board[col][row])){
                    if(board[col][row].equals(board[col-1][row+1])){
                        countContinueSameColor[col-1][row+1]=1+countContinueSameColor[col][row];
                    }
                }
            }
        }
        return Arrays.stream(countContinueSameColor)
                .flatMapToInt(col-> Arrays.stream(col))
                .anyMatch(e->e==3);
    }
    public boolean checkIf4SameColorDiagonalLine(){
        int[][] countContinueSameColor=new int[columns][rows];
        for(int row=0;row<(rows-1);row++){
            for(int col=0;col<(columns-1);col++){
                if(player.equals(board[col][row])){
                    if(board[col][row].equals(board[col+1][row+1])){
                        countContinueSameColor[col+1][row+1]=1+countContinueSameColor[col][row];
                    }
                }
            }
        }
        return Arrays.stream(countContinueSameColor)
                .flatMapToInt(col-> Arrays.stream(col))
                .anyMatch(e->e==3);
    }
    public boolean checkIf4SameColorHorizontalLine(){
        int count=0;
        for(int row=0;row<rows;row++){
            for (int col=0;col<columns;col++){
                if(player.equals(board[col][row])){
                    count++;
                }else{
                    count=0;
                }
                if(count==4){
                    return true;
                }
            }

        }
        return false;
    }

    public boolean checkIf4SameColorStraightLine(){
        int count=0;
        for(int col=0;col<columns;col++){
            for(int row=0;row<rows;row++){
                if(player.equals(board[col][row])){
                    count++;
                }else{
                    count=0;
                }
                if(count==4){
                    return true;
                }
            }
        }

        return false;
    }


    public String isFinish(){
        if(getDiscInBoard()==rows*columns){
            return "game finish";
        }else{
            return "game not finish";
        }

    }

    public int getDiscInBoard(){
        int total=(int) Arrays.stream(board.clone())
                        .flatMap(row-> Arrays.stream(row.clone()))
                        .filter(e->"R".equals(e)||"G".equals(e))
                        .count();
        return total;
    }
    public void printBoard(int row){
        StringJoiner stringJoiner=new StringJoiner("|","|","|");
        for(int i=0;i<columns;i++){
            if(board[i][row]==null){
                stringJoiner.add(" ");
            }else{
                stringJoiner.add(board[i][row]);
            }
        }
        output.println(stringJoiner.toString());

    }
    public void switchPlayer(){
        if("G".equals(player)){
            player="R";
        }else{
            player="G";
        }

    }
    public String getCurrentPlayer(){
        output.println(player+" turn");

        return player;
    }

    public int getEmptyPosition(int column){
        int emptyPosition=0;
        while(board[column][emptyPosition]!=null){
            emptyPosition++;
        }
        return emptyPosition;
    }
    public void checkIsRowsHaveExceed(int row){
        if(row>=rows)throw new RuntimeException("Row is full");
    }

    public void checkColumn(int column){
        if(column>=columns) throw new RuntimeException();
    }

}
