package org.ieselcaminas.pmdm.minesweeper;


public class Singleton {
    private static Singleton singleton;
    private int numRows, numCols;
    private int numBombs;

    private Singleton() {
        numCols = 20;
        numRows = 20;
        numBombs = 90;
    }

    public static Singleton sharedInstance() {
        if (singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

}