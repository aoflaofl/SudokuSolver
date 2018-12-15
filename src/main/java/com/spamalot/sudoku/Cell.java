package com.spamalot.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Hold the value and logic for a Cell in a game of Sudoku.
 * 
 * @author gej
 *
 */
class Cell {

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
   *          the Set
   */
  public final void setRowSet(final Set<Integer> s) {
    this.rowSet = s;
  }

  /**
   * Set the Column Set for this Cell.
   * 
   * @param s
   *          the columnSet to set
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
   *          the value to set
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
   *          int of value to set.
   */
  public void setValue(final int v) {
    this.setValue(Integer.valueOf(v));
  }

  /**
   * Check if any of the Sets already contains the value.
   * 
   * @param v
   *          the value to see if is in any of the sets
   * @return true if the value is in one of the Sets.
   */
  private boolean setsContain(final Integer v) {
    return this.rowSet.contains(v) || this.columnSet.contains(v) || this.squareSet.contains(v);
  }

  /**
   * Add a value to all three Sets this cell is a member of.
   * 
   * @param v
   *          the value to add to the sets.
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
   *          the squareSet to set
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
