package test.model;

import main.model.MoveSet;
import main.model.PuzzleSolver;
import main.model.SlidingList;
import main.model.SolutionBFS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionBFSTest {
    SolutionBFS solutionBFS;

    SlidingList answerList;
    SlidingList possibleQuestionListA;
    SlidingList possibleQuestionListB;
    SlidingList impossibleQuestionListA;
    SlidingList impossibleQuestionListB;
    SlidingList impossibleQuestionListC;
    PuzzleSolver puzzleSolver;

    @BeforeEach
    public void beforeRun() {
        // create a solution -> (base 1 - 15)
        answerList = new SlidingList(4, 4);

        // create a question -> swap(15, 16)
        possibleQuestionListA = new SlidingList(4, 4);
        int[] possibleNumberA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        possibleQuestionListA.createAQuestion(possibleNumberA, 4, 4);

        // create a question -> spiral
        possibleQuestionListB = new SlidingList(4, 4);
        int[] reverseNumberB = {1, 2, 3, 4, 12, 13, 14, 5, 11, 16, 15, 6, 10, 9, 8, 7};
        possibleQuestionListB.createAQuestion(reverseNumberB, 4, 4);

        // create a question -> (swap 14, 15)
        impossibleQuestionListA = new SlidingList(4, 4);
        int[] impossibleNumberA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 16};
        impossibleQuestionListA.createAQuestion(impossibleNumberA, 4, 4);

        // create a question -> (swap 12, 15)
        impossibleQuestionListB = new SlidingList(4, 4);
        int[] impossibleNumberB = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 13, 14, 12, 16};
        impossibleQuestionListB.createAQuestion(impossibleNumberB, 4, 4);

        // create a question -> (base 15 - 1)
        impossibleQuestionListC = new SlidingList(4, 4);
        int[] reverseNumberC = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 16};
        impossibleQuestionListC.createAQuestion(reverseNumberC, 4, 4);

        //create a puzzleSolver
        puzzleSolver = new PuzzleSolver(answerList);

        solutionBFS = new SolutionBFS(impossibleQuestionListC, answerList);
    }

    @Test
    public void isPossibleConfigurationTest() {
        assertEquals(solutionBFS.solvePuzzle(), new ArrayList<MoveSet>());
    }
}
