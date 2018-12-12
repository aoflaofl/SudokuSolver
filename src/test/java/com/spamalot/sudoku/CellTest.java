package com.spamalot.sudoku;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class CellTest {
  Grid g;

  @BeforeClass
  public static void setup() {

  }

  @Before
  public void setupThis() {
    g = new Grid(9);
    g.getCell(1, 1).setValue(1);
  }

  /**
   * Test that a cell is Empty in a newly created Grid.
   */
  @Test
  public void cellEmpty() {
    Cell c = g.getCell(0, 0);
    assertNotNull(c);
    assertTrue(c.isEmpty());
  }

  @Test
  public void cellOccupied() {
    Cell c = g.getCell(1, 1);
    assertNotNull(c);
    assertTrue(!c.isEmpty());
    assertTrue(c.getValue() instanceof Integer);
    assertEquals(c.getValue(), Integer.valueOf(1));
  }

  @Test
  public void cellNonCandidates() {
    Set<Integer> n = g.getCell(1, 1).getNonCandidates();
    assertNotNull(n);
    assertEquals(1, n.size());
    assertTrue(n.contains(Integer.valueOf(1)));

  }

  @Test
  public void cellSetMoreThanOnce() {
    Cell c = g.getCell(1, 1);
    c.setValue(2);
    assertNotNull(c);
  
  }

  @After
  public void tearThis() {
  }

  @AfterClass
  public static void tear() {
  }
}
