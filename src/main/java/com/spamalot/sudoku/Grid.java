package com.spamalot.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Hold the Cells and the logic for handling a game of Sudoku.
 * 
 * @author gej
 *
 */
class Grid {
  /** Default size of a Sudoku puzzle. */
  private static final int DEFAULT_SUDOKU_SIZE = 9;

  /** Hold the sets of the contents of the rows. */
  private ArrayList<Set<Integer>> rowSets;

  /** Hold the sets of the contents of the columns. */
  private ArrayList<Set<Integer>> columnSets;

  /** Hold the sets of the contents of the squares. */
  private ArrayList<Set<Integer>> squareSets;

  /** Hold the individual cells of the Grid. */
  private Cell[][] cells;

  /** The size of this puzzle. */
  private int puzzleSize;

  /** Construct a Sudoku Grid with standard 9x9 size. */
  Grid() {
    this(DEFAULT_SUDOKU_SIZE);
  }

  Grid(final int size) {
    // TODO: Check that size is a square number.
    this.puzzleSize = size;
    initSets();

    initCells();
  }

  private void initSets() {
    this.rowSets = new ArrayList<>(this.puzzleSize);
    this.columnSets = new ArrayList<>(this.puzzleSize);
    this.squareSets = new ArrayList<>(this.puzzleSize);
    for (int i = 0; i < this.puzzleSize; i++) {
      this.rowSets.add(new HashSet<Integer>());
      this.columnSets.add(new HashSet<Integer>());
      this.squareSets.add(new HashSet<Integer>());
    }
  }

  private void initCells() {
    this.cells = new Cell[this.puzzleSize][this.puzzleSize];

    for (int row = 0; row < this.puzzleSize; row++) {
      for (int column = 0; column < this.puzzleSize; column++) {
        Cell c = new Cell();
        c.setRowSet(this.rowSets.get(row));
        c.setColumnSet(this.columnSets.get(column));
        c.setSquareSet(this.squareSets.get(3 * (row / 3) + (column / 3)));
        this.cells[row][column] = c;
      }
    }
  }

  public Cell getCell(final int row, final int column) {
    return this.cells[row][column];
  }

  public void initGrid(final int[][] table) {
    for (int row = 0; row < this.puzzleSize; row++) {
      for (int column = 0; column < this.puzzleSize; column++) {

        int v = table[row][column];
        if (v < 1 || v > this.puzzleSize) {
          continue;
        }
        Cell c = getCell(row, column);
        c.setValue(v);

      }

    }

  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (int row = 0; row < this.puzzleSize; row++) {
      for (int column = 0; column < this.puzzleSize; column++) {
        Integer v = getCell(row, column).getValue();
        if (v == null) {
          b.append(" ");
        } else {
          b.append(v);
        }
      }
      b.append("\n");
    }
    return b.toString();
  }

  public void scanForSingles() {
    for (int row = 0; row < this.puzzleSize; row++) {
      for (int column = 0; column < this.puzzleSize; column++) {

        Cell c = getCell(row, column);
        if (c.isEmpty()) {
          System.out.print(row + ", " + column + " ");
          Set<Integer> cands = c.getNonCandidates();
          System.out.println(cands);
        }
      }
    }

  }

}
