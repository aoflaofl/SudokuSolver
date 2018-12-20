package com.spamalot.sudoku;

import java.util.ArrayList;
import java.util.Collections;
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

  /** The size of this puzzle. */
  private int puzzleSize;

  /** Hold the individual cells of the Grid. */
  private Cell[][] cells;

  /** List to hold the sets of the contents of the rows. */
  private ArrayList<Set<Integer>> rowSets;

  /** List to hold the sets of the contents of the columns. */
  private ArrayList<Set<Integer>> columnSets;

  /** List to hold the sets of the contents of the squares. */
  private ArrayList<Set<Integer>> squareSets;

  /**
   * Hold the Set of possible values for a cell in this Grid so it doesn't have to
   * be recomputed all the time.
   */
  private static Set<Integer> possibleCandidates = null;

  /** Construct a Sudoku Grid with standard 9x9 size. */
  Grid() {
    this(DEFAULT_SUDOKU_SIZE);
  }

  /**
   * Construct a Sudoku Grid of the give size. Only 9, 16 or 25 are supported.
   * 
   * @param size
   *               size of the puzzle.
   */
  private Grid(final int size) {
    if (size != 9 && size != 16 && size != 25) {
      throw new IllegalArgumentException("Illegal Puzzle Size (Use 9, 16, or 25 only).");
    }

    this.setPuzzleSize(size);
    initSets();
    initCells();
    initPossibleCandidates();
  }

  /** Create an immutable Set containing possible values in a Sudoku cell. */
  final private void initPossibleCandidates() {
    if (possibleCandidates == null) {
      Set<Integer> x = new HashSet<>(getPuzzleSize());
      for (int i = 0; i < this.getPuzzleSize(); i++) {
        x.add(Integer.valueOf(i));
      }
      possibleCandidates = Collections.unmodifiableSet(x);
    }
  }

  final private void initSets() {
    this.rowSets = new ArrayList<>(this.getPuzzleSize());
    this.columnSets = new ArrayList<>(this.getPuzzleSize());
    this.squareSets = new ArrayList<>(this.getPuzzleSize());
    for (int i = 0; i < this.getPuzzleSize(); i++) {
      this.rowSets.add(new HashSet<Integer>(this.getPuzzleSize()));
      this.columnSets.add(new HashSet<Integer>(this.getPuzzleSize()));
      this.squareSets.add(new HashSet<Integer>(this.getPuzzleSize()));
    }
  }

  final private void initCells() {
    // TODO: Check that sets are already built.
    this.cells = new Cell[this.getPuzzleSize()][this.getPuzzleSize()];

    for (int row = 0; row < this.getPuzzleSize(); row++) {
      for (int column = 0; column < this.getPuzzleSize(); column++) {
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
    for (int row = 0; row < this.getPuzzleSize(); row++) {
      for (int column = 0; column < this.getPuzzleSize(); column++) {

        int v = table[row][column];
        if (v < 1 || v > this.getPuzzleSize()) {
          continue;
        }
        Cell c = getCell(row, column);
        c.set(v);
      }

    }

  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (int row = 0; row < this.getPuzzleSize(); row++) {
      for (int column = 0; column < this.getPuzzleSize(); column++) {
        Integer v = getCell(row, column).getValue();
        if (v == null) {
          b.append(' ');
        } else {
          b.append(v);
        }
      }
      b.append('\n');
    }
    return b.toString();
  }

  public Set<Integer> getAllPossibleCandidates() {
    initPossibleCandidates();
    return Grid.possibleCandidates;
  }

  /**
   * @return the puzzleSize
   */
  public int getPuzzleSize() {
    return this.puzzleSize;
  }

  /**
   * @param size
   *               the puzzleSize to set
   * @return
   */
  private final void setPuzzleSize(final int size) {
    this.puzzleSize = size;
  }

  /**
   * Hold the value and logic for a Cell in a game of Sudoku.
   * 
   * @author gej
   *
   */
  public class Cell {

    /** Hold the Integer value of this cell. Null means empty. */
    private Integer value = null;

    /**
     * The Integers that have been solved on the row this cell is in. This Set is
     * shared by all Cells in the same row.
     */
    private Set<Integer> rowSet;

    /**
     * The Integers that have been solved on the column this cell is in. This Set is
     * shared by all Cells in the same column.
     */
    private Set<Integer> columnSet;

    /**
     * The Integers that have been solved on the Square this cell is in. This Set is
     * shared by all Cells in the same square.
     */
    private Set<Integer> squareSet;

    /**
     * Set the Row Set for this Cell.
     * 
     * @param s
     *            the Set
     */
    public final void setRowSet(final Set<Integer> s) {
      this.rowSet = s;
    }

    /**
     * Set the Column Set for this Cell.
     * 
     * @param s
     *            the columnSet to set
     */
    public void setColumnSet(final Set<Integer> s) {
      this.columnSet = s;
    }

    /**
     * Get the Integer value of the Cell.
     * 
     * @return the value
     */
    public Integer getValue() {
      return this.value;
    }

    /**
     * Set the Integer value of the Cell. If the value can't be put in the Cell,
     * don't store it.
     * 
     * @param v
     *            the value to set
     */
    public void setValue(final Integer v) {
      if (!setsContain(v)) {
        this.value = v;
        addValueToSets(v);
      } else {
        throw new java.lang.IllegalStateException("This violates the rules of Sudoku.");
      }
    }

    /**
     * Set the value as an int. Does boxing.
     * 
     * @param v
     *            int of value to set.
     */
    private void set(final int v) {
      this.setValue(Integer.valueOf(v));
    }

    /**
     * Check if any of the Sets already contains the value.
     * 
     * @param v
     *            the value to see if is in any of the sets
     * @return true if the value is in one of the Sets.
     */
    private boolean setsContain(final Integer v) {
      return this.rowSet.contains(v) || this.columnSet.contains(v) || this.squareSet.contains(v);
    }

    /**
     * Add a value to all three Sets this cell is a member of.
     * 
     * @param v
     *            the value to add to the sets.
     */
    private void addValueToSets(final Integer v) {
      this.rowSet.add(v);
      this.columnSet.add(v);
      this.squareSet.add(v);
    }

    /**
     * Set the Square Set for this Cell.
     * 
     * @param s
     *            the squareSet to set
     */
    public void setSquareSet(final Set<Integer> s) {
      this.squareSet = s;
    }

    /**
     * Get the values that can't be in this Cell.
     * 
     * @return the Set of all values that cannot be in this Cell.
     */
    public Set<Integer> getNonCandidates() {
      Set<Integer> ret = new HashSet<>(this.rowSet);
      ret.addAll(this.columnSet);
      ret.addAll(this.squareSet);
      return ret;
    }

    /**
     * Check if the Cell is empty.
     * 
     * @return true if the Cell has no assigned value.
     */
    public boolean isEmpty() {
      return this.value == null;
    }
  }
}
