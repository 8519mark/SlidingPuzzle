package test.model;

import main.model.MoveSet;
import main.model.SlidingList;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SlidingListTest {
    SlidingList slidingList;

    @BeforeEach
    public void beforeRun() {
        slidingList = new SlidingList();
    }

    @Test
    public void constructorTest() {
        // check initial format (3 x 3) :
        // length
        assertEquals(9, slidingList.getSlidingList().length);

        // check items
        for (int i = 1; i <= 9; i++) {
            assertEquals(i, slidingList.getSlidingList()[i - 1]);
        }

        // check emptyIndex
        assertEquals(9, slidingList.getEmptyBlock());

        // check available move
        //  UP    DOWN   LEFT  RIGHT
        // {true, false, true, false}
        boolean[] cornerBotRight= {true, false, true, false};
        assertEquals(cornerBotRight[0], slidingList.availableMoves()[0]);
        assertEquals(cornerBotRight[1], slidingList.availableMoves()[1]);
        assertEquals(cornerBotRight[2], slidingList.availableMoves()[2]);
        assertEquals(cornerBotRight[3], slidingList.availableMoves()[3]);

        // test move UP valid
        slidingList.move(MoveSet.UP);
        assertEquals(1, slidingList.getSlidingList()[0]);
        assertEquals(2, slidingList.getSlidingList()[1]);
        assertEquals(3, slidingList.getSlidingList()[2]);
        assertEquals(4, slidingList.getSlidingList()[3]);
        assertEquals(5, slidingList.getSlidingList()[4]);
        assertEquals(9, slidingList.getSlidingList()[5]);
        assertEquals(7, slidingList.getSlidingList()[6]);
        assertEquals(8, slidingList.getSlidingList()[7]);
        assertEquals(6, slidingList.getSlidingList()[8]);
        assertEquals(6, slidingList.getEmptyBlock());

        // test move RIGHT invalid
        slidingList.move(MoveSet.RIGHT);
        assertEquals(1, slidingList.getSlidingList()[0]);
        assertEquals(2, slidingList.getSlidingList()[1]);
        assertEquals(3, slidingList.getSlidingList()[2]);
        assertEquals(4, slidingList.getSlidingList()[3]);
        assertEquals(5, slidingList.getSlidingList()[4]);
        assertEquals(9, slidingList.getSlidingList()[5]);
        assertEquals(7, slidingList.getSlidingList()[6]);
        assertEquals(8, slidingList.getSlidingList()[7]);
        assertEquals(6, slidingList.getSlidingList()[8]);
        assertEquals(6, slidingList.getEmptyBlock());

        // test move LEFT valid
        slidingList.move(MoveSet.LEFT);
        assertEquals(1, slidingList.getSlidingList()[0]);
        assertEquals(2, slidingList.getSlidingList()[1]);
        assertEquals(3, slidingList.getSlidingList()[2]);
        assertEquals(4, slidingList.getSlidingList()[3]);
        assertEquals(9, slidingList.getSlidingList()[4]);
        assertEquals(5, slidingList.getSlidingList()[5]);
        assertEquals(7, slidingList.getSlidingList()[6]);
        assertEquals(8, slidingList.getSlidingList()[7]);
        assertEquals(6, slidingList.getSlidingList()[8]);
        assertEquals(5, slidingList.getEmptyBlock());

        // test move DOWN valid
        slidingList.move(MoveSet.DOWN);
        assertEquals(1, slidingList.getSlidingList()[0]);
        assertEquals(2, slidingList.getSlidingList()[1]);
        assertEquals(3, slidingList.getSlidingList()[2]);
        assertEquals(4, slidingList.getSlidingList()[3]);
        assertEquals(8, slidingList.getSlidingList()[4]);
        assertEquals(5, slidingList.getSlidingList()[5]);
        assertEquals(7, slidingList.getSlidingList()[6]);
        assertEquals(9, slidingList.getSlidingList()[7]);
        assertEquals(6, slidingList.getSlidingList()[8]);
        assertEquals(8, slidingList.getEmptyBlock());
    }

}
