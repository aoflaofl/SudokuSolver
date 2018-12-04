package com.spamalot.sudoku;

/**
 * Driver class for Sudoku.
 * 
 * @author gej
 *
 */
public final class Sudoku {
  /** Instantiate Nothing (tm). */
  private Sudoku() {
  }

  /**
   * Start here.
   * 
   * @param args
   *          Currently nothing.
   */
  public static void main(final String[] args) {
    Grid g = new Grid();

    int[][] table = { //
        { 0, 0, 0, 0, 0, 8, 0, 0, 3 }, //
        { 0, 5, 0, 0, 4, 0, 0, 0, 0 }, //
        { 4, 0, 0, 9, 0, 3, 0, 7, 0 }, //
        //
        { 0, 1, 0, 0, 6, 5, 0, 0, 0 }, //
        { 0, 0, 2, 0, 0, 0, 7, 0, 0 }, //
        { 0, 0, 0, 4, 3, 0, 0, 6, 0 }, //
        //
        { 0, 7, 0, 3, 0, 6, 0, 0, 8 }, //
        { 0, 0, 0, 0, 5, 0, 0, 3, 0 }, //
        { 2, 0, 0, 1, 0, 0, 0, 0, 0 }//
        //
    };

    g.initGrid(table);
    System.out.println(g);

    g.scanForSingles();
  }

}
