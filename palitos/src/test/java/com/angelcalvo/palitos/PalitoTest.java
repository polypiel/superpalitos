/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvs.palitos;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author angel
 */
public class PalitoTest {
      
  @Test
  public void testTacha() {
  	Palito p = new Palito();
  	p.tacha(2);
  	Assert.assertTrue(!p.getEstado(2));
  }
  
  @Test
  public void testVivos() {
  	Palito p = new Palito();
  	p.tacha(2);
  	p.tacha(5);
  	Assert.assertTrue(p.vivos() == Palito.NPALS - 2);
  }
}
