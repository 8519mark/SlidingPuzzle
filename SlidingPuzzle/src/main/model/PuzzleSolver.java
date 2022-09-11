package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
    Practice on DFS, BFS generating and searching

    reference material : Why is this 15-Puzzle Impossible? - Numberphile
                         https://www.youtube.com/watch?v=YI1WqYKHi78&t=1086s
 */



public class PuzzleSolver {
    SlidingList initialSlidingList;
    SlidingList currentSlidingList;
    SlidingList answerSlidingList;

    public PuzzleSolver(SlidingList answerSlidingList) {
        this.answerSlidingList = answerSlidingList;
    }
    public PuzzleSolver(SlidingList questionList, SlidingList answerSlidingList) {
        this.answerSlidingList = answerSlidingList;

        this.currentSlidingList = questionList;

        int[] initialSlidingArray = new int[ currentSlidingList.getWidth() * currentSlidingList.getHeight()];
        for (int i = 0; i < currentSlidingList.getWidth() * currentSlidingList.getHeight(); i++) {
            initialSlidingArray[i] = currentSlidingList.getSlidingList()[i];
        }
        initialSlidingList = new SlidingList();
        initialSlidingList.createAQuestion(initialSlidingArray,
                currentSlidingList.getWidth(), currentSlidingList.getHeight());
    }

    public void createRandomPuzzle() {
        int width = answerSlidingList.getWidth();
        int height = answerSlidingList.getHeight();
        initialSlidingList = new SlidingList(width, height);
        currentSlidingList = new SlidingList(width, height);

        // randomize sliding list
        boolean isSameAsAnswer = true;
        int[] initialList;
        ArrayList<Integer> tempList;

        initialList = new int[width * height];
        tempList = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            tempList.add(i + 1);
        }
        while (isSameAsAnswer) {
            initialList = new int[width * height];
            tempList = new ArrayList<>();
            for (int i = 0; i < width * height; i++) {
                tempList.add(i + 1);
            }

            for (int i = 0; i < width * height; i++) {
                if (initialList[i] != answerSlidingList.getSlidingList()[i]) {
                    isSameAsAnswer = false;
                }
            }
        }

        boolean isValid = false;
        while (!isValid) {
            Collections.shuffle(tempList);
            for (int i = 0; i < width * height; i++) {
                initialList[i] = tempList.get(i);
            }
            currentSlidingList.createAQuestion(initialList, width, height);
            isValid = isPossibleConfiguration(currentSlidingList);
        }

        int[] initialSlidingArray = new int[ currentSlidingList.getWidth() * currentSlidingList.getHeight()];
        for (int i = 0; i < currentSlidingList.getWidth() * currentSlidingList.getHeight(); i++) {
            initialSlidingArray[i] = currentSlidingList.getSlidingList()[i];
        }
        initialSlidingList = new SlidingList();
        initialSlidingList.createAQuestion(initialSlidingArray,
                currentSlidingList.getWidth(), currentSlidingList.getHeight());
    }

    /**
     Transposition :
        Position of Question Blank to answer Blank distance (only straight line)
            - if even : require EVEN number of moves
            - if odd  : require ODD number of moves
     */
    public boolean isPossibleConfiguration(SlidingList questionSlidingList) {
        boolean isEmptyBlockEven = false;
        boolean isSolutionEven = false;

        // Determine the steps required to an answer is Even or Odd
        int questionEmptyIndex = questionSlidingList.getEmptyBlock() - 1;
        int answerEmptyIndex = answerSlidingList.getEmptyBlock() - 1;

        int[] questionSlidingCord = {answerEmptyIndex / answerSlidingList.getWidth(),
                answerSlidingList.getEmptyBlock() % answerSlidingList.getWidth()};
        int[] answerSlidingCord = {questionEmptyIndex / questionSlidingList.getWidth(),
                questionSlidingList.getEmptyBlock() % questionSlidingList.getWidth()};
        int distance = questionSlidingCord[0] - answerSlidingCord[0] + questionSlidingCord[1] - answerSlidingCord[1];
        if ((distance) % 2 == 0) {
            isEmptyBlockEven = true;
        }


        // Determine the minimum Permutation needed to reach answer, and ask if Even or Odd
        int totalDifference = 0;
        // copying current list
        int[] tempSlidingList = new int[questionSlidingList.getSlidingList().length];
        int[] tempIndexedList = new int[questionSlidingList.getIndexedList().length];
        for (int i = 0; i < questionSlidingList.getSlidingList().length; i++) {
            tempSlidingList[i] = questionSlidingList.getSlidingList()[i];
            tempIndexedList[i] = questionSlidingList.getIndexedList()[i];
        }
        // checking
        for (int i = 0; i < questionSlidingList.getIndexedList().length; i++) {
            // suppose item in index i
            int curDifference = 0;
            int answerItem = answerSlidingList.getSlidingList()[i];

            if (tempSlidingList[i] != answerItem) {
                curDifference = Math.abs(tempIndexedList[i] - answerSlidingList.getIndexedList()[i]);
                totalDifference += curDifference;

                // update temp lists
                // sliding list :   1 2 3       1 2 3
                //                  4 5 8   ->  4 5 6
                //                  7 6 9       8 7 9
                // indexed list :
                //                  1 2 3 4 5 6 7 8 9       1 2 3 4 5 6 7 8 9
                //                  0 1 2 3 4 7 6 5 8   ->  0 1 2 3 4 5 7 6 8
                //                                                    + 1 1
                for (int j = tempIndexedList[answerItem - 1]; j > i; j--) {
                    int tempItem = tempSlidingList[j - 1] - 1;
                    tempSlidingList[j] =  tempSlidingList[j - 1];
                    tempIndexedList[tempItem] += 1;
                }
                tempSlidingList[i] = answerItem;
                tempIndexedList[answerItem - 1] = i;
            }
        }

        if ((totalDifference) % 2 == 0) {
            isSolutionEven = true;
        }

        return (isEmptyBlockEven == isSolutionEven);
    }

    public void reset() {

        int[] currentSlidingArray = new int[ initialSlidingList.getWidth() * initialSlidingList.getHeight()];
        for (int i = 0; i < initialSlidingList.getWidth() * initialSlidingList.getHeight(); i++) {
            currentSlidingArray[i] = initialSlidingList.getSlidingList()[i];
        }
        currentSlidingList = new SlidingList();
        currentSlidingList.createAQuestion(currentSlidingArray,
                initialSlidingList.getWidth(), initialSlidingList.getHeight());
    }

    public List<MoveSet> solvePuzzleDFS() {
        SlidingList tempList = new SlidingList();
        int[] tempArray = new int[currentSlidingList.getWidth() * currentSlidingList.getHeight()];
        for (int i = 0; i < currentSlidingList.getWidth() * currentSlidingList.getHeight(); i++) {
            tempArray[i] = currentSlidingList.getSlidingList()[i];
        }
        tempList.createAQuestion(tempArray, currentSlidingList.getWidth(), currentSlidingList.getHeight());
        SolutionBFS solutionBFS = new SolutionBFS(tempList, answerSlidingList);
        return solutionBFS.solvePuzzle();
    }

    public boolean isSolvable() {
        SlidingList tempList = new SlidingList();
        int[] tempArray = new int[currentSlidingList.getWidth() * currentSlidingList.getHeight()];
        for (int i = 0; i < currentSlidingList.getWidth() * currentSlidingList.getHeight(); i++) {
            tempArray[i] = currentSlidingList.getSlidingList()[i];
        }
        tempList.createAQuestion(tempArray, currentSlidingList.getWidth(), currentSlidingList.getHeight());

        for (MoveSet move : solvePuzzleDFS()) {
            tempList.move(move);
        }
        boolean isAnswerConfig = true;
        for (int i = 0; i < currentSlidingList.getWidth() * currentSlidingList.getHeight(); i++) {
            if (tempList.getSlidingList()[i] != answerSlidingList.getSlidingList()[i]) {
                isAnswerConfig = false;
            }
        }

        return isAnswerConfig;
    }

    public SlidingList getCurrentSlidingList() {
        return currentSlidingList;
    }

    public SlidingList getAnswerSlidingList() {
        return answerSlidingList;
    }

    public SlidingList getInitialSlidingList() {
        return initialSlidingList;
    }
}
