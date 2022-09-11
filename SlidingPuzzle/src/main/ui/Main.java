package main.ui;

import main.model.PuzzleSolver;
import main.model.SlidingList;
import main.model.MoveSet;
import main.model.SolutionBFS;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SlidingList slidingList = new SlidingList();
//        SlidingPuzzleText slidingPuzzleText = new SlidingPuzzleText(slidingList);
//        slidingPuzzleText.printPuzzle();

//        System.out.println();
//        System.out.println();
//
//        System.out.println("Move Up");
//        slidingList.move(MoveSet.UP);
//        slidingPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//        System.out.println("Move Right - no change");
//        slidingList.move(MoveSet.RIGHT);
//        slidingPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//        System.out.println("Move Left");
//        slidingList.move(MoveSet.LEFT);
//        slidingPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//        System.out.println("Move Down");
//        slidingList.move(MoveSet.DOWN);
//        slidingPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//        System.out.println("Move RIGHT");
//        slidingList.move(MoveSet.RIGHT);
//        slidingPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//
//        SlidingList answerList = new SlidingList(4, 4);
//        PuzzleSolver puzzleSolver = new PuzzleSolver(answerList);
//        // impossible
//        SlidingList impossibleList = new SlidingList(4, 4);
//        int[] impossibleConfig = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 16};
//        impossibleList.createAQuestion(impossibleConfig, 4, 4);
//        System.out.println(puzzleSolver.isPossibleConfiguration(impossibleList));
//
//        SlidingPuzzleText impossiblePuzzleText = new SlidingPuzzleText(impossibleList);
//        impossiblePuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//        // possible
//        SlidingList possibleList = new SlidingList(4, 4);
//        int[] possibleConfig = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 16};
//        possibleList.createAQuestion(possibleConfig, 4, 4);
//        System.out.println(puzzleSolver.isPossibleConfiguration(possibleList));
//
//        SlidingPuzzleText possiblePuzzleText = new SlidingPuzzleText(possibleList);
//        possiblePuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();

//        // create a random puzzle
//        puzzleSolver.createRandomPuzzle();
//        SlidingPuzzleText randomPuzzleText = new SlidingPuzzleText(puzzleSolver.getCurrentSlidingList());
//        randomPuzzleText.printPuzzle();
//
//        System.out.println();
//        System.out.println();
//
//
//        // find shortest puzzle
//        SolutionBFS bfsSolver = new SolutionBFS(answerList, answerList);
//        for (MoveSet move : bfsSolver.solvePuzzle()) {
//            System.out.println(move);
//        }
//
//        System.out.println();
//        System.out.println();


//        SlidingList possibleList = new SlidingList(4, 4);
//        int[] possibleConfig = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 14, 15};
//        possibleList.createAQuestion(possibleConfig, 4, 4);
//        SlidingList answerList = new SlidingList(4, 4);
//        int[] answerConfig = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
//        answerList.createAQuestion(answerConfig, 4,4);
//
//        PuzzleSolver puzzleSolver = new PuzzleSolver(possibleList, answerList);
//
//        List<MoveSet> bfsAnswer = puzzleSolver.solvePuzzleDFS();
//        for (MoveSet move : bfsAnswer) {
//            System.out.println(move);
//        }



        MainWindow mainWindow = new MainWindow();
    }
}
