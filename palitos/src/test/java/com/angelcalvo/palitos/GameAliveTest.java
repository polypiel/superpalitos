/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelcalvo.palitos;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author angel
 */
public class GameAliveTest {
      
  @Test
  public void testTacha() {
  	GameState state = new GameState();
  	state.crossStick(2);
  	Assert.assertTrue(!state.getStick(2));
  }
  
  @Test
  public void testVivos() {
  	GameState state = new GameState();
  	state.crossStick(2);
  	state.crossStick(5);
  	Assert.assertTrue(state.alive() == GameState.NSTICKS - 2);
  }
}
