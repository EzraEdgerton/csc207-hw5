package edu.grinnell.csc207.groupgroup.utils;
import java.math.BigDecimal;
import java.math.MathContext;

/*
 * This class contains the square root function for BigDecimals. 
 * It calculates the square root of a method to the precision specified by epsilon.  
 */

public class Utils {
	public static BigDecimal sqrt(BigDecimal input, BigDecimal epsilon)
	{
			BigDecimal currentApprox = BigDecimal.valueOf(1);
			while(epsilon.compareTo((input.subtract(currentApprox.multiply(currentApprox))).abs()) < 0)
			{
				System.out.println("currentApprox before: " + currentApprox.toPlainString());
				currentApprox = (currentApprox.add(input.divide(currentApprox,MathContext.DECIMAL128))).divide(BigDecimal.valueOf(2),MathContext.DECIMAL128);
				System.out.println("currentApprox after: " + currentApprox.toPlainString());
				System.out.println("currentApprox sq: " + currentApprox.multiply(currentApprox));
				System.out.println("Input: " + input);
				System.out.println("compareTo: " + (epsilon.compareTo((input.subtract(currentApprox.multiply(currentApprox))).abs()) < 0));
			}//while
			return currentApprox;
	}//returns an estimation of the square root of input
}
