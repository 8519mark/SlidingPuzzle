package main.model;

import main.ui.SlidingPuzzleText;

import java.util.*;

public class SolutionBFS {
    private SlidingList question;
    private SlidingList answer;
    private int size;
    private int width;
    private int height;

    public SolutionBFS(SlidingList question, SlidingList answer) {
        this.question = question;
        this.answer = answer;
        this.width = answer.getWidth();
        this.height = answer.getHeight();
        this.size = answer.getWidth() * answer.getHeight();
    }

    /**
     BFS template :
        - add initial question to queue
            initial question marked visited
        - pseudocode :

     while (queueNotEmpty) {
        dequeue a vertex from queue
        for (each available moves<UP, DOWN, LEFT, RIGHT>) {
            * check if the new 4 configuration exits(marked) in the queue
            * do something with 4 directions that is new and not yet "visited"
        }
     }
     */

    public List<MoveSet> solvePuzzle() {
        // initialize the queue
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(question.getSlidingList());
        // marked the initial question configuration as "visited"
        List<int[]> visitedConfig = new ArrayList<>();
        visitedConfig.add(question.getSlidingList());
        // record and initialize moveSet
        Queue<List<MoveSet>> moveQueue = new LinkedList<List<MoveSet>>();
        List<MoveSet> initialStep = new ArrayList<>();
        moveQueue.add(initialStep);

        int[] currentPuzzle;
        List<MoveSet> currentMove;
        List<MoveSet> resultMove = new ArrayList<>();
        while (!queue.isEmpty()) {
            // dequeue the most recent puzzle state
            currentPuzzle = queue.remove();
            currentMove = moveQueue.remove();
            resultMove = currentMove;
            // check if the puzzle is solved or not
            boolean isAnswerConfig = true;
            for (int i = 0; i < size; i++) {
                if (currentPuzzle[i] != answer.getSlidingList()[i]) {
                    isAnswerConfig = false;
                }
            }
            SlidingList tempList = new SlidingList();
            tempList.createAQuestion(currentPuzzle, width, height);
//            SlidingPuzzleText tempText = new SlidingPuzzleText(tempList);
//            tempText.printPuzzle();
//            System.out.println();

            if (isAnswerConfig) {
                System.out.println("Answer found !!");
                break;
            } else {
                for (int i = 0; i < 4; i++) {
                    if (tempList.getAvailableMove()[i] == true) {
                        int[] newCurrentPuzzle = new int[size];
                        for (int j = 0; j < size; j++) {
                            newCurrentPuzzle[j] = currentPuzzle[j];
                        }
                        List<MoveSet> newCurrentMove = new ArrayList<>();
                        for (MoveSet move : currentMove) {
                            newCurrentMove.add(move);
                        }
                        movePuzzle(newCurrentPuzzle, i, visitedConfig, queue, moveQueue, newCurrentMove);
                    }
                }
            }
        }
        return resultMove;
    }

    private void movePuzzle(int[] currentPuzzle, int i, List<int[]> visitedConfig, Queue<int[]> queue,
                             Queue<List<MoveSet>> moveSetStack, List<MoveSet> currentMove) {
        SlidingList tempList = new SlidingList();
        tempList.createAQuestion(currentPuzzle, width, height);
        MoveSet nextMove;
        if (i == 0) {
            nextMove = MoveSet.UP;
        } else if (i == 1) {
            nextMove = MoveSet.DOWN;
        } else if (i == 2) {
            nextMove = MoveSet.LEFT;
        } else {
            nextMove = MoveSet.RIGHT;
        }
        tempList.move(nextMove);
        int[] tempConfig = new int[size];
        for (int j = 0; j < size; j++) {
            tempConfig[j] = tempList.getSlidingList()[j];
        }

        // check if the configuration already exits
        if (isConfigNew(visitedConfig, tempConfig)) {
            queue.add(tempConfig);
            currentMove.add(nextMove);
            moveSetStack.add(currentMove);
            visitedConfig.add(tempConfig);
        }
    }

    private boolean isConfigNew(List<int[]> visitedConfig, int[] currentConfig) {
        boolean areNew = true;
        // loop through all config
        for (int[] aConfig : visitedConfig) {
            boolean isSame = true;
            for (int i = 0; i < size; i++) {
                // check if the config is the same
                if (aConfig[i] != currentConfig[i]) {
                    isSame = false;
                }
            }
            if (isSame) {
                areNew = false;
            }
        }
        return areNew;
    }
}
