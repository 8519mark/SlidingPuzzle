package main.ui;

import main.model.SlidingList;

public class SlidingPuzzleText {
    private SlidingList slidingList;

    public SlidingPuzzleText(SlidingList slidingList) {
        this.slidingList = slidingList;
    }

    public void printPuzzle() {
        int width  = slidingList.getWidth();
        int height = slidingList.getHeight();


        /**
         * 3 x 2
            * *** * *** * *** *
            *  1  *  2  *  3  *
            * *** * *** * *** *
            *  4  *  5  *     *
            * *** * *** * *** *
        */


        String line = "*";
        for (int i = 0; i < width; i++) {
            line = line.concat("****");
        }

        System.out.println(line);
        for (int i = 0; i < width * height; i++) {
            Integer number = slidingList.getSlidingList()[i];
            String content = number.toString();
            if (i + 1 == slidingList.getEmptyBlock()) {
                content = " ";
            }

            if ((i + 1) % width == 0) {
                System.out.println("* " + content +  " *");
                System.out.println(line);
            } else {
                System.out.print("* " + content + " ");
            }
        }
    }
}
