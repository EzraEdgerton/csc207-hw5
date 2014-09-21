package edu.grinnell.csc207.groupgroup.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import java.math.BigDecimal;
public class UtilsTest {

	@Test
	public void SqrtTest() {
		assertEquals("10", Utils.sqrt(new BigDecimal(100), new BigDecimal(.00001)));
	}

}
