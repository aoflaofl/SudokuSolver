package com.spamalot.sudoku;

import com.spamalot.sudoku.Grid.Cell;

import java.util.Set;

/**
 * Methods to solve a Sudoku puzzle.
 * 
 * @author gej
 *
 */
final class Solver {
  /** Do not instantiate. */
  private Solver() {
  }

  static void scanForSingles(final Grid g) {
    for (int row = 0; row < g.getPuzzleSize(); row++) {
      for (int column = 0; column < g.getPuzzleSize(); column++) {
        Cell c = g.getCell(row, column);
        if (c.isEmpty()) {
          System.out.print(row + ", " + column + " ");
          Set<Integer> cands = c.getNonCandidates();
          System.out.println(cands);
        }
      }
    }
  }

  /**
   * Apply the single pattern to a Sudoku Grid.
   * 
   * @param g
   *            Sudoku Grid to solve in
   */
  static void solveSingles(final Grid g) {
    Set<Integer> p = g.getAllPossibleCandidates();
    for (Integer i : p) {
      System.out.print(i);
    }
  }

}
