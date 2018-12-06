package com.spamalot.sudoku;

import java.util.Set;

/**
 * Methods to solve a Sudoku puzzle.
 * 
 * @author gej
 *
 */
public final class Solver {
  /** Do not instantiate. */
  private Solver() {
  }

  /**
   * Apply the single pattern to a Sudoku Grid.
   * 
   * @param g
   *          Sudoku Grid to solve in
   */
  static void solveSingles(final Grid g) {
    Set<Integer> p = g.getAllPossibleCandidates();
    for (Integer i : p) {
      System.out.print(i);
    }
  }

}
