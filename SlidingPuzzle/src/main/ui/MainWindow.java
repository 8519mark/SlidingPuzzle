package main.ui;

import main.model.MoveSet;
import main.model.PuzzleSolver;
import main.model.SlidingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame{
    protected static final int BLOCK_WIDTH = 160;
    protected static final Color emptyColor = new Color(0xF5F5DC);

    private PuzzleSolver puzzleSolver;
    private int blockWidth;
    private int blockHeight;

    private JButton[] numberButtons;

    private FunctionClickHandler functionClickHandler;
    private NumberClickHandler numberClickHandler;
    private CustomClickHandler customClickHandler;

    private JTextField widthText;
    private JTextField heightText;
    private JFrame sizeWindow;
    private JFrame numberWindow;
    private JButton[] tempButtons;
    private JButton[] tempAnswerButtons;
    private int tempWidth;
    private int tempHeight;
    protected static int t = 0;
    private JButton checkButton;
    private JButton newGameButton;
    private SlidingList questionList;
    private SlidingList answerList;

    private boolean isLightMode;
    private ArrayList<JFrame> frames;

    private static int moveCounter = -1;
    private List<MoveSet> listOfMoves;
    private JButton[] numberShowButtons;
    private SlidingList showAnswerList;

    public MainWindow() {
        super(); // create a new window

        // stub : 3 * 3
        SlidingList slidingList = new SlidingList();
        // default answer : {1 2 .... 9}

        // default question : {1 2 .... 7 , 9, 8}
        int[] defaultQuestion = new int[]{1, 2, 3, 4, 5, 6, 7, 9, 8};
        SlidingList questionSlidingList = new SlidingList();
        questionSlidingList.createAQuestion(defaultQuestion, 3, 3);
        puzzleSolver = new PuzzleSolver(questionSlidingList, slidingList);
        // default question : {1 2 .... 7 , 9, 8}

        buildLayout();

        lightMode(this);
        isLightMode = true;
        this.setVisible(true);

        frames = new ArrayList<JFrame>();
        frames.add(this);
    }

    public MainWindow(SlidingList questionList, SlidingList answerList) {
        super(); // create a new window

        SlidingList slidingList = questionList;
        // default answer : {1 2 .... 9}
        puzzleSolver = new PuzzleSolver(slidingList, answerList);

        buildLayout();

        lightMode(this);
        isLightMode = true;
        this.setVisible(true);

        frames = new ArrayList<JFrame>();
        frames.add(this);
    }

    private void buildLayout() {
        blockWidth = puzzleSolver.getCurrentSlidingList().getWidth();
        blockHeight = puzzleSolver.getCurrentSlidingList().getHeight();

        functionClickHandler = new FunctionClickHandler();
        numberClickHandler = new NumberClickHandler();
        customClickHandler = new CustomClickHandler();

        this.setSize((blockWidth + 2) * BLOCK_WIDTH ,(blockHeight + 1) * BLOCK_WIDTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // prevent panel being resized
        this.setTitle("Let's Slide");

        this.setLayout(null);

        buildPuzzle();

        buildFunction();
    }


    /**
     * make JButton invisible
     * https://stackoverflow.com/questions/5654208/making-a-jbutton-invisible-but-clickable
     */
    private void buildPuzzle() {
        int k = 0;
        numberButtons = new JButton[blockHeight * blockWidth];
        for (int i = 0; i < blockHeight; i++) {
            for (int j = 0; j < blockWidth; j++) {
                JButton button = new JButton(String.valueOf(puzzleSolver.getCurrentSlidingList().getSlidingList()[k]));
                button.setBounds(j * BLOCK_WIDTH, i * BLOCK_WIDTH,
                        BLOCK_WIDTH, BLOCK_WIDTH);
                button.setRolloverEnabled(true);
                button.setBackground(null);
                button.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
                if (k == puzzleSolver.getCurrentSlidingList().getEmptyBlock() - 1) {
                    // set the empty number "invisible"
                    button.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
                    button.setBackground(emptyColor);
                }

                numberButtons[k] = button;
                button.addActionListener(numberClickHandler);
                this.add(button);

                k++;
            }
        }
    }

    private void buildFunction() {
        int defaultStart = BLOCK_WIDTH * blockWidth;
        int sectionWidth = BLOCK_WIDTH;
        int sectionHeight = BLOCK_WIDTH / 4;
        // create random puzzle
        createFunctionButtons("answer", defaultStart, 0, 1,
                sectionWidth, sectionHeight);
        // reset
        createFunctionButtons("reset", defaultStart + sectionWidth,0,1,
                sectionWidth, sectionHeight);
        // show answer
        createFunctionButtons("show answer", defaultStart,blockHeight * sectionHeight,2,
                sectionWidth, sectionHeight);
        // custom puzzle
        createFunctionButtons("custom", defaultStart,3 * blockHeight * sectionHeight,1,
                sectionWidth, sectionHeight);
        // check answer
        createFunctionButtons("mode", defaultStart + sectionWidth,3 * blockHeight * sectionHeight,
                1, sectionWidth, sectionHeight);


        // if ever "show answer"
        // forward
//        createFunctionButtons("forward", defaultStart,
//                2 * blockHeight * sectionHeight,1,
//                sectionWidth, sectionHeight);
//        // backward
//        createFunctionButtons("go back", defaultStart + sectionWidth,
//                2 * blockHeight * sectionHeight,1,
//                sectionWidth, sectionHeight);

        // if ever
    }

    private void createFunctionButtons(String name,int leftStart, int topStart, int widthMultiplier,
                                  int sectionWidth, int sectionHeight) {
        JButton button = new JButton();
        button.setBounds(leftStart, topStart,
                widthMultiplier * sectionWidth,  blockHeight * sectionHeight);
        button.setText(name);
        button.setBackground(null);
        button.setFont(new Font("Arial", Font.BOLD, BLOCK_WIDTH / 8));
        button.addActionListener(functionClickHandler);
        this.add(button);
    }

    public void darkMode(JFrame frame) {
        Color darkModeColour = new Color(0x121212);
        frame.getContentPane().setBackground(darkModeColour);
    }

    public void lightMode(JFrame frame) {
        Color lightModeColour = new Color(0xe4e5f1);
        frame.getContentPane().setBackground(lightModeColour);
    }


    // REQUIRES:
    // MODIFIES:
    // EFFECTS :
    private class FunctionClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();

            if (src.getText().equals("answer")) {
                answerPuzzle();
            } else if (src.getText().equals("reset")) {
                resetPuzzle();
            } else if (src.getText().equals("show answer")) {
                showPuzzle();
            } else if (src.getText().equals("custom")) {
                customPuzzle();
            } else if (src.getText().equals("mode")) {
                colorPuzzle();
            } else if (src.getText().equals("forward")) {
                movePuzzle(true);
            } else if (src.getText().equals("go back")) {
                movePuzzle(false);
            }
        }
    }

    private void movePuzzle(boolean isForward) {
        if (isForward) {
            if (moveCounter < listOfMoves.size() - 1) {
                moveCounter++;
                showAnswerList.move(listOfMoves.get(moveCounter));
            }
        } else {
            if (moveCounter >= 0) {
                MoveSet backWardMove;
                if (listOfMoves.get(moveCounter) == MoveSet.UP) {
                    backWardMove = MoveSet.DOWN;
                } else if (listOfMoves.get(moveCounter) == MoveSet.DOWN) {
                    backWardMove = MoveSet.UP;
                } else if (listOfMoves.get(moveCounter) == MoveSet.LEFT) {
                    backWardMove = MoveSet.RIGHT;
                } else {
                    backWardMove = MoveSet.LEFT;
                }
                showAnswerList.move(backWardMove);
                moveCounter--;
            }
        }

        int k = 0;
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                numberShowButtons[k].setText(String.valueOf(showAnswerList.getSlidingList()[k]));
                numberShowButtons[k].setFont(
                        new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
                numberShowButtons[k].setBackground(null);
                if (k == showAnswerList.getEmptyBlock() - 1) {
                    // set the empty number "invisible"
                    numberShowButtons[k].setFont(
                            new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
                    numberShowButtons[k].setBackground(emptyColor);
                }
                k++;
            }
        }
    }


    private void showPuzzle() {
        moveCounter = -1;
        JFrame numberWindow = new JFrame();
        if (isLightMode) {
            lightMode(numberWindow);
        } else {
            darkMode(numberWindow);
        }
        frames.add(numberWindow);
        numberWindow.setSize((blockWidth + 1) * BLOCK_WIDTH ,(blockHeight + 1) * BLOCK_WIDTH);
        numberWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        numberWindow.setLocationRelativeTo(null);
        numberWindow.setLayout(null);

        // build template for new puzzle
        int k = 0;
        numberShowButtons = new JButton[blockWidth * blockHeight];
        showAnswerList = new SlidingList();
        int[] showAnswerArray = new int[blockWidth * blockHeight];
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                JButton button = new JButton(String.valueOf(puzzleSolver.getCurrentSlidingList().getSlidingList()[k]));
                showAnswerArray[k] = puzzleSolver.getCurrentSlidingList().getSlidingList()[k];
                button.setBounds(j * BLOCK_WIDTH, i * BLOCK_WIDTH,
                        BLOCK_WIDTH, BLOCK_WIDTH);
                button.setRolloverEnabled(true);
                button.setBackground(null);
                button.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
                if (k == puzzleSolver.getCurrentSlidingList().getEmptyBlock() - 1) {
                    // set the empty number "invisible"
                    button.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
                    button.setBackground(emptyColor);
                }

                numberShowButtons[k] = button;
                numberWindow.add(button);
                k++;
            }
        }
        showAnswerList.createAQuestion(showAnswerArray, blockWidth, blockHeight);

        JButton forwardButton = new JButton("forward");
        JButton goBackButton = new JButton("go back");
        forwardButton.setBackground(null);
        goBackButton.setBackground(null);


        forwardButton.setBounds((blockWidth + 1 / 2) * BLOCK_WIDTH, BLOCK_WIDTH / 2,
                BLOCK_WIDTH * 2 / 3, BLOCK_WIDTH / 2);
        goBackButton.setBounds((blockWidth + 1 / 2) * BLOCK_WIDTH, BLOCK_WIDTH * 3 / 2,
                BLOCK_WIDTH * 2 / 3, BLOCK_WIDTH / 2);

        forwardButton.addActionListener(functionClickHandler);
        goBackButton.addActionListener(functionClickHandler);

        numberWindow.add(forwardButton);
        numberWindow.add(goBackButton);

        numberWindow.setVisible(true);

        listOfMoves = puzzleSolver.solvePuzzleDFS();
    }

    private void colorPuzzle() {
        if (isLightMode) {
            for (JFrame frame : frames) {
                darkMode(frame);
            }
            isLightMode = false;
        } else {
            for (JFrame frame : frames) {
                lightMode(frame);
            }
            isLightMode = true;
        }
        this.setVisible(true);
    }

    private void customPuzzle() {
        sizeWindow = new JFrame();
        if (isLightMode) {
            lightMode(sizeWindow);
        } else {
            darkMode(sizeWindow);
        }
        frames.add(sizeWindow);
        sizeWindow.setSize(BLOCK_WIDTH * 2 ,(int)(BLOCK_WIDTH * 5 / 4));
        sizeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        sizeWindow.setLocationRelativeTo(null);
        sizeWindow.setLayout(new FlowLayout());

        JButton confirmButton = new JButton("confirm");
        confirmButton.setBackground(null);
        widthText = new JTextField();
        widthText.setPreferredSize(new Dimension((int)(BLOCK_WIDTH * 3 / 2), (int)(BLOCK_WIDTH / 4)));

        heightText = new JTextField();
        heightText.setPreferredSize(new Dimension((int)(BLOCK_WIDTH * 3 / 2), (int)(BLOCK_WIDTH / 4)));

        JLabel widthLabel = new JLabel("width");
        JLabel heightLabel = new JLabel("height");

        sizeWindow.add(widthLabel);
        sizeWindow.add(widthText);
        sizeWindow.add(heightLabel);
        sizeWindow.add(heightText);
        sizeWindow.add(confirmButton);
        confirmButton.addActionListener(customClickHandler);

        // after enter size, create an empty puzzle

        // empty puzzle will light up (EmptyColor)

        // confirm puzzle

        sizeWindow.setVisible(true);
    }

    private void orderNumberPuzzle(int width, int height) {
        t = 0;
        numberWindow = new JFrame();
        if (isLightMode) {
            lightMode(numberWindow);
        } else {
            darkMode(numberWindow);
        }
        frames.add(numberWindow);
        numberWindow.setSize((width + 1) * BLOCK_WIDTH ,(height + 1) * BLOCK_WIDTH);
        numberWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        numberWindow.setLocationRelativeTo(null);
        numberWindow.setLayout(null);

        // build template for new puzzle
        int k = 0;
        tempButtons = new JButton[width * height];
        tempAnswerButtons = new JButton[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JButton tempButton = new JButton("temp " + (k + 1));
                tempButton.setBounds(j * BLOCK_WIDTH, i * BLOCK_WIDTH,
                        BLOCK_WIDTH, BLOCK_WIDTH);
                tempButton.setRolloverEnabled(true);
                tempButton.setBackground(null);
                numberWindow.add(tempButton);
                tempButtons[k] = tempButton;
                tempButton.addActionListener(customClickHandler);
                tempButton.setBackground(null);

                JButton tempAnswerButton = new JButton("answ " + (k + 1));
                tempAnswerButton.setBounds(j * BLOCK_WIDTH, i * BLOCK_WIDTH,
                        BLOCK_WIDTH, BLOCK_WIDTH);
                tempAnswerButton.setRolloverEnabled(true);
                tempAnswerButton.setBackground(null);
                numberWindow.add(tempAnswerButton);
                tempAnswerButtons[k] = tempAnswerButton;
                tempAnswerButton.addActionListener(customClickHandler);
                tempAnswerButton.setBackground(null);

                tempAnswerButton.setVisible(false);
                k++;
            }
        }
        tempWidth = height;
        tempHeight = height;

        checkButton = new JButton("check");
        newGameButton = new JButton("new game");
        checkButton.setBackground(null);
        newGameButton.setBackground(null);


        checkButton.setBounds((width + 1 / 2) * BLOCK_WIDTH, BLOCK_WIDTH / 2,
                BLOCK_WIDTH * 2 / 3, BLOCK_WIDTH / 2);
        newGameButton.setBounds(width * BLOCK_WIDTH, BLOCK_WIDTH * 3 / 2,
                BLOCK_WIDTH * 2 / 3, BLOCK_WIDTH / 2);

        checkButton.addActionListener(customClickHandler);
        newGameButton.addActionListener(customClickHandler);

        numberWindow.add(checkButton);
        numberWindow.add(newGameButton);

        checkButton.setEnabled(false);
        newGameButton.setEnabled(false);

        numberWindow.setVisible(true);
    }

    private class CustomClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();

            if (src.getText().equals("confirm")) {
                String widthString = widthText.getText();
                String heightString = heightText.getText();

                try {
                    int width = Integer.parseInt(widthString);
                    int height = Integer.parseInt(heightString);
                    orderNumberPuzzle(width, height);
                    sizeWindow.dispose();
                }
                catch (NumberFormatException numberFormatException) {
                    JFrame invalidWindow = new JFrame();
                    if (isLightMode) {
                        lightMode(invalidWindow);
                    } else {
                        darkMode(invalidWindow);
                    }
                    frames.add(invalidWindow);
                    invalidWindow.setSize(BLOCK_WIDTH * 3 / 2 ,(BLOCK_WIDTH));
                    invalidWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    invalidWindow.setLocationRelativeTo(null);

                    JLabel invalidNumber = new JLabel();
                    invalidWindow.add(invalidNumber);
                    invalidNumber.setText("invalid number.");
                    invalidNumber.setHorizontalTextPosition(JLabel.CENTER);
                    invalidNumber.setVerticalTextPosition(JLabel.CENTER);
                    invalidNumber.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 5));

                    invalidWindow.setVisible(true);
                    sizeWindow.dispose();
                }
            } else if (src.getText().substring(0, 4).equals("temp")) {
                changeButtonText(Integer.parseInt(src.getText().substring(5)), tempWidth * tempHeight);
            }  else if (src.getText().substring(0, 4).equals("answ")) {
                changeAnswerButtonText(Integer.parseInt(src.getText().substring(5)), tempWidth * tempHeight);
            } else if (src.getText().equals("check")) {
                if (checkValidPuzzle()) {
                    System.out.println("valid config");
                    newGameButton.setEnabled(true);
                } else {
                    JFrame invalidWindow = new JFrame();
                    if (isLightMode) {
                        lightMode(invalidWindow);
                    } else {
                        darkMode(invalidWindow);
                    }
                    frames.add(invalidWindow);
                    invalidWindow.setSize(BLOCK_WIDTH * 3 / 2 ,(BLOCK_WIDTH));
                    invalidWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    invalidWindow.setLocationRelativeTo(null);

                    JLabel invalidNumber = new JLabel();
                    invalidWindow.add(invalidNumber);
                    invalidNumber.setText("invalid config.");
                    invalidNumber.setHorizontalTextPosition(JLabel.CENTER);
                    invalidNumber.setVerticalTextPosition(JLabel.CENTER);
                    invalidNumber.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 5));

                    invalidWindow.setVisible(true);
                    numberWindow.dispose();
                }
            } else if (src.getText().equals("new game")) {
                MainWindow newMainWindow = new MainWindow(questionList, answerList);
                closeCurrentWindow();
            }
        }
    }

    private boolean checkValidPuzzle() {
        int[] tempArray = new int[tempWidth * tempHeight];
        int[] tempAnswerArray = new int[tempWidth * tempHeight];
        for (int i = 0; i < tempWidth * tempHeight; i++) {
            tempArray[i] = Integer.parseInt(tempButtons[i].getText());
            tempAnswerArray[i] = Integer.parseInt(tempAnswerButtons[i].getText());
        }
        SlidingList tempList = new SlidingList();
        SlidingList tempAnswerList = new SlidingList();
        tempList.createAQuestion(tempArray, tempWidth, tempHeight);
        tempAnswerList.createAQuestion(tempAnswerArray, tempWidth, tempHeight);
        PuzzleSolver tempSolver = new PuzzleSolver(tempList, tempAnswerList);

        questionList = tempList;
        answerList = tempAnswerList;

        return tempSolver.isSolvable();
    }

    private void changeButtonText(int i, int size) {
        t++;
        if (t == size) {
            tempButtons[i - 1].setText(String.valueOf(t));
            // set the empty number "invisible"
            tempButtons[i - 1].setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
            tempButtons[i - 1].setBackground(emptyColor);
            t = 0;
            for (JButton tempButton : tempButtons) {
                tempButton.setVisible(false);
            }
            for (JButton tempAnswerButton : tempAnswerButtons) {
                tempAnswerButton.setVisible(true);
            }
        } else {
            tempButtons[i - 1].setText(String.valueOf(t));
            tempButtons[i - 1].setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
        }

        tempButtons[i - 1].setEnabled(false);
    }

    private void changeAnswerButtonText(int i, int size) {
        t++;
        if (t == size) {
            tempAnswerButtons[i - 1].setText(String.valueOf(t));
            // set the empty number "invisible"
            tempAnswerButtons[i - 1].setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
            tempAnswerButtons[i - 1].setBackground(emptyColor);
            t = 0;
            checkButton.setEnabled(true);
        } else {
            tempAnswerButtons[i - 1].setText(String.valueOf(t));
            tempAnswerButtons[i - 1].setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
        }

        tempAnswerButtons[i - 1].setEnabled(false);
    }


    private void answerPuzzle() {
        JFrame answerWindow = new JFrame();
        if (isLightMode) {
            lightMode(answerWindow);
        } else {
            darkMode(answerWindow);
        }
        frames.add(answerWindow);
        answerWindow.setSize(BLOCK_WIDTH * (blockWidth + 1) ,BLOCK_WIDTH * (blockHeight + 1));
        answerWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        answerWindow.setLocationRelativeTo(null);

        answerWindow.setLayout(null);

        int k = 0;
        for (int i = 0; i < blockHeight; i++) {
            for (int j = 0; j < blockWidth; j++) {
                JButton button = new JButton(String.valueOf(puzzleSolver.getAnswerSlidingList().getSlidingList()[k]));
                button.setBounds(j * BLOCK_WIDTH, i * BLOCK_WIDTH,
                        BLOCK_WIDTH, BLOCK_WIDTH);
                button.setRolloverEnabled(true);
                button.setBackground(null);
                button.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
                if (k == puzzleSolver.getAnswerSlidingList().getEmptyBlock() - 1) {
                    // set the empty number "invisible"
                    button.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
                    button.setBackground(emptyColor);
                }
                answerWindow.add(button);
                k++;
            }
        }
        answerWindow.setVisible(true);
    }

    private void resetPuzzle() {
        puzzleSolver.reset();

        int k = 0;
        for (int i = 0; i < blockHeight; i++) {
            for (int j = 0; j < blockWidth; j++) {
                numberButtons[k].setText(String.valueOf(puzzleSolver.getCurrentSlidingList().getSlidingList()[k]));
                numberButtons[k].setBackground(null);
                numberButtons[k].setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
                if (k == puzzleSolver.getCurrentSlidingList().getEmptyBlock() - 1) {
                    // set the empty number "invisible"
                    numberButtons[k].setFont(new Font("Arial", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 4));
                    numberButtons[k].setBackground(emptyColor);
                }
                k++;
            }
        }

        refreshVisibility();

    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS :
    private class NumberClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();

            boolean preventLoop = true;

            for (int i = 0; i < blockHeight * blockWidth; i++) {
                if (src.getText().equals(numberButtons[i].getText()) && preventLoop) {

                    int emptyBlock = puzzleSolver.getCurrentSlidingList().getEmptyBlock();

                    if ((i + 1) == emptyBlock - 1) {
                        puzzleSolver.getCurrentSlidingList().move(MoveSet.LEFT);
                        swapButton(i + 1, emptyBlock);
                        preventLoop = false;
                    } else if ((i + 1) == emptyBlock + 1) {
                        puzzleSolver.getCurrentSlidingList().move(MoveSet.RIGHT);
                        swapButton(i + 1, emptyBlock);
                        preventLoop = false;
                    } else if ((i + 1) == emptyBlock - blockWidth) {
                        puzzleSolver.getCurrentSlidingList().move(MoveSet.UP);
                        swapButton(i + 1, emptyBlock);
                        preventLoop = false;
                    } else if ((i + 1) == emptyBlock + blockWidth) {
                        puzzleSolver.getCurrentSlidingList().move(MoveSet.DOWN);
                        swapButton(i + 1, emptyBlock);
                        preventLoop = false;
                    } else {
                        System.out.println("not valid move");
                    }
                }
            }

            int[] current = puzzleSolver.getCurrentSlidingList().getSlidingList();
            int[] answer = puzzleSolver.getAnswerSlidingList().getSlidingList();

            boolean isSame = true;
            for (int i = 0; i < blockWidth * blockHeight; i++) {
                if (current[i] != answer[i]) {
                    isSame = false;
                }
            }
            if (isSame) {
                JFrame victoryWindow = new JFrame();
                if (isLightMode) {
                    lightMode(victoryWindow);
                } else {
                    darkMode(victoryWindow);
                }
                frames.add(victoryWindow);
                victoryWindow.setSize(BLOCK_WIDTH * 3 ,(BLOCK_WIDTH));
                victoryWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                victoryWindow.setLocationRelativeTo(null);

                JLabel victorySpeech = new JLabel();
                victoryWindow.add(victorySpeech);
                victorySpeech.setText("You Win !!");
                victorySpeech.setHorizontalTextPosition(JLabel.CENTER);
                victorySpeech.setVerticalTextPosition(JLabel.CENTER);
                victorySpeech.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));

                victoryWindow.setVisible(true);
            }
        }
    }

    private void swapButton(int swapNumber, int emptyNumber) {
        JButton swapButton = numberButtons[emptyNumber - 1];
        JButton emptyButton = numberButtons[swapNumber - 1];

        String tempText = swapButton.getText();

        swapButton.setText(emptyButton.getText());
        swapButton.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, BLOCK_WIDTH / 2));
        swapButton.setBackground(null);

        emptyButton.setText(tempText);
        emptyButton.setFont(new Font("Arial", Font.BOLD, BLOCK_WIDTH / 8));
        emptyButton.setBackground(emptyColor);


    }



    // REQUIRES:
    // MODIFIES:
    // EFFECTS :
    public void refreshVisibility() {
        this.setVisible(false);
        this.setVisible(true);
    }

    private void closeCurrentWindow() {
        this.dispose();
    }

    private JFrame thisFrame() {
        return this;
    }
}
