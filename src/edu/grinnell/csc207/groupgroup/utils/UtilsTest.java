package edu.grinnell.csc207.groupgroup.utils;


import static org.junit.Assert.*;



import org.junit.Test;
public class UtilsTest {

	@Test
	public void partBtest() {
		assertEquals(81, partB.expt(3, 4));
		assertEquals(-10, partB.expt(-10, 1));
	}
}
