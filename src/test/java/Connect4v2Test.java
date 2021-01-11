import org.hamcrest.BaseMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Connect4v2Test {
    private Connect4v2 connect4v2;
    private OutputStream output=new ByteArrayOutputStream();
    @Rule
    public ExpectedException exception=ExpectedException.none();



    @Before
    public void before(){
        connect4v2=new Connect4v2(new PrintStream(output));
    }

    //    1. The board is composed of seven columns and six rows; all positions are empty.
//    @Test
//    public void whenIntroducedDiscExceedMaxColumnsThenException(){
//        exception.expect(RuntimeException.class);
//        connect4v2.putDisc(9,1);
//    }
//    @Test(expected = RuntimeException.class)
//    public void whenIntroducedDiscExceedMaxRowsThenException(){
//        connect4v2.putDisc(1,8);
//    }
    @Test(expected = RuntimeException.class)
    public void whenIntroducedDiscExceedMaxCloumnsThenException(){
        connect4v2.play(9);
    }

    @Test
    public void whenInitiatedThenBoardIsEmpty(){
        assertTrue(connect4v2.isBoardEmpty());
    }
//    2. Players introduce discs on the top of the columns. The introduced disc drops
//    down the board if the column is empty. Future discs introduced in the same
//    column will stack over the previous ones.
    @Test
    public void whenPlayFindChooseColumnEmptyThenPlaceBottom(){

        assertThat(connect4v2.getEmptyPosition(3),is(0));
    }
    @Test
    public void whenPlayFindChooseColumnNotEmptyThenPlaceOverIt(){
        connect4v2.play(2);
        assertThat(connect4v2.getEmptyPosition(2),is(1));
    }
    @Test(expected = RuntimeException.class)
    public void whenPlayFindChooseColumnIsFullThenException(){
        connect4v2.play(1);
        connect4v2.play(1);
        connect4v2.play(1);
        connect4v2.play(1);
        connect4v2.play(1);
        connect4v2.play(1);
        connect4v2.play(1);
    }
//    3. It is a two-person game, so there is one color for each player. One player uses red
//    (R) and the other one uses green (G). Players alternate turns, inserting one disc every time.
    @Test
    public void whenRPlayerDoneThenSwitchToGPlayer(){
        connect4v2.play(2);//R player
        connect4v2.play(2);//G player
        assertThat(connect4v2.getCurrentPlayer(),is("R"));
    }
//    4. We want feedback when either an event or an error occurs within the game. The output shows
//     the status of the board after every move.
    @Test
    public void whenPlayFinishThenGetTheEventFeedback(){

        connect4v2.getCurrentPlayer();
        assertThat(output.toString(),containsString("R turn"));
    }

    @Test
    public void whenPlayFinishThenPrintBoardStatus(){
        connect4v2.play(2);
        assertThat(output.toString(),containsString("| | |R| | | | |"));
    }
//    5. When no more discs can be inserted, the game finishes, and it is considered a draw.
    @Test
    public void whenBoardIsFullThenReturnFinish(){
        for(int row=0;row<6;row++)
            for(int col=0;col<7;col++)
                connect4v2.play(col);
        assertThat(connect4v2.isFinish(),is("game finish"));
    }
    @Test
    public void whenBoardIsNotFullThenReturnNotFinish(){
        connect4v2.play(2);
        assertThat(connect4v2.isFinish(),is("game not finish"));
    }



//    6. If a player inserts a disc and connects more than three discs of his color in a
//    straight vertical line, then that player wins.

//    @Test
//    public void whenColorHave4discsInStraightLineThenItIsWinner(){
//        connect4v2.play(2);//R
//        connect4v2.play(3);//G
//        connect4v2.play(2);//R
//        connect4v2.play(3);//G
//        connect4v2.play(2);//R
//        connect4v2.play(3);//G
//
//        assertThat(connect4v2.play(2),is("R is winner vertical"));//R winner
//    }
//    7. The same happens in a horizontal line direction.
    @Test
    public void whenDiscsHave4SameColorInHorizontalLineThenWinner(){
        connect4v2.play(0);//R
        connect4v2.play(0);//G
        connect4v2.play(1);//R
        connect4v2.play(1);//G
        connect4v2.play(2);//R
        connect4v2.play(2);//G
        assertThat(connect4v2.play(3),is("R is winner hor"));
    }
//    8. The same happens in a diagonal line direction.
    @Test
    public void whenDiscsHave4SameColorInDiagonalLineThenWinner(){
        int[] testData=new int[]{0,1,1,2,3,2,2,3,4,3};
        for(int col:testData){
            connect4v2.play(col);
        }
        assertThat(connect4v2.play(3),is("R is winner diagonal"));
    }
    @Test
    public void whenDiscsHave4SameColorInInverseDiagonalLineThenWinner(){
        int[] testData=new int[]{4,3,3,2,0,1,1,2,2,1};
        for(int col:testData){
            connect4v2.play(col);
        }
        assertThat(connect4v2.play(1),is("R is winner Inverse"));
    }
}
