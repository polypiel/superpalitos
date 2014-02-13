/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelcalvo.palitos;

import org.junit.Assert;
import org.junit.Test;

import com.angelcalvo.palitos.Sticks;

/**
 *
 * @author angel
 */
public class PalitoTest {
      
  @Test
  public void testTacha() {
  	Sticks p = new Sticks();
  	p.cross(2);
  	Assert.assertTrue(!p.getEstado(2));
  }
  
  @Test
  public void testVivos() {
  	Sticks p = new Sticks();
  	p.cross(2);
  	p.cross(5);
  	Assert.assertTrue(p.vivos() == Sticks.NPALS - 2);
  }
}
