package main.model;


public class SlidingList {

    // REQUIRES :
    // MODIFIES :
    // EFFECTS  :

    // description : a list of "numbers"
    // length      : depends on the size of input ( n x m )
    private int[] slidingList;
    private int[] indexedList; // ex: indexedList[0] = 2 --> "number 1" is at "position 3"

    private int width;
    private int height;
    // description : the id of the block that is "empty"
    private int emptyBlock;
    private boolean[] availableMove;


    // description : create a ( 3 x 3 ) sliding puzzle

    // n = 3, m = 3
    /**
    1   2   3
    4   5   6
    7   8   9
    */
    // 9 is emptyID
    public SlidingList() {
        listConstructor(3, 3);

        this.emptyBlock = 3 * 3;
        this.availableMove = this.availableMoves();
    }



    // description : create a custom ( n x m ) sliding puzzle
    // REQUIRES : 1 <= emptyID <= ( n x m )
    // MODIFIES :
    // EFFECTS  :

    // n = 4, m = 2
    /**
    1   2   3   4
    5   6   7   8
    */
    public SlidingList(int width, int height) {
        listConstructor(width, height);

        this.emptyBlock = width * height;
        this.availableMove = this.availableMoves();
    }


    private void listConstructor(int width, int height) {
        this.slidingList = new int[width * height];

        for (int i = 0; i < width * height; i++) {
            this.slidingList[i] = i + 1;
        }

        this.width = width;
        this.height = height;

        indexedListConstructor();
    }

    private void indexedListConstructor() {
        this.indexedList = new int[width * height];

        for (int i = 0; i < width * height; i++) {

            int curNumber = this.slidingList[i];
            this.indexedList[curNumber - 1] = i;
        }
    }

    // REQUIRES : potentialList has the same length as slidingList (n x m)
    // MODIFIES :
    // EFFECTS  :
    public void createAQuestion(int[] potentialList, int width, int height) {
        this.width = width;
        this.height = height;

        this.slidingList = potentialList;

        for (int i = 0; i < width * height; i++) {
            if (slidingList[i] == width * height) {
                this.emptyBlock = i + 1;
            }
        }
        indexedListConstructor();
        this.availableMove = this.availableMoves();
    }

    // REQUIRES : 1 <= emptyID <= ( n x m )
    // MODIFIES :
    // EFFECTS  :
    public boolean[] availableMoves() {
        //                 UP    DOWN  LEFT  RIGHT
        boolean[] moves = {true, true, true, true};

        boolean upWall = ((emptyBlock - 1) / width == 0);
        boolean bottomWall = ((emptyBlock - 1) / width == height - 1);
        boolean leftWall = (emptyBlock % width == 1);
        boolean rightWall = (emptyBlock % width == 0);

        if (upWall && leftWall) {
            // TOP - LEFT corner
            moves[0] = false;
            moves[2] = false;
        } else if (bottomWall && rightWall) {
            // BOTTOM - RIGHT corner
            moves[1] = false;
            moves[3] = false;
        } else if (upWall && rightWall) {
            // TOP - RIGHT corner
            moves[0] = false;
            moves[3] = false;
        } else if (bottomWall && leftWall) {
            //BOTTOM - LEFT corner
            moves[1] = false;
            moves[2] = false;
        } else if (upWall){
            moves[0] = false;
        } else if (bottomWall){
            moves[1] = false;
        } else if (leftWall){
            moves[2] = false;
        } else if (rightWall){
            moves[3] = false;
        }

        return moves;
    }

    public boolean move(MoveSet move) {
        switch (move) {
            case UP:
                 return(moveCheck(0));
            case DOWN:
                return(moveCheck(1));
            case LEFT:
                return(moveCheck(2));
            case RIGHT:
                return(moveCheck(3));
            default:
                return false;
        }
    }

    private boolean moveCheck(int i) {
        if (availableMove[i] == false) {
            return false;
        }
        moveSwap(i);
        return true;
    }

    private void moveSwap(int i) {
        int emptyIndex = emptyBlock - 1;
        if (i == 0) {
            swap(emptyIndex - width);
        } else if (i == 1) {
            swap(emptyIndex + width);
        } else if (i == 2) {
            swap(emptyIndex - 1);
        } else {
            swap(emptyIndex + 1);
        }
    }

    private void swap(int toBeSwappedIndex) {
        int emptyIndex = emptyBlock - 1;
        // sliding list
        int emptyTemp = slidingList[emptyIndex];
        int toBeSwappedTemp = slidingList[toBeSwappedIndex];

        // swap
        // sliding list
        slidingList[emptyIndex] = toBeSwappedTemp;
        slidingList[toBeSwappedIndex] = emptyTemp;
        // indexed list
        indexedList[emptyTemp - 1] = toBeSwappedIndex;
        indexedList[toBeSwappedTemp - 1] = emptyIndex;


        // new emptyBlock
        emptyBlock = toBeSwappedIndex + 1;

        // update move status of emptyBlock
        this.availableMove = this.availableMoves();
    }



    public int[] getSlidingList() {
        return slidingList;
    }

    public int getEmptyBlock() {
        return emptyBlock;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean[] getAvailableMove() {
        return availableMove;
    }

    public int[] getIndexedList() {
        return indexedList;
    }
}
