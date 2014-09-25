package edu.grinnell.csc207.groupgroup.utils;

//import Calculator;

import java.io.PrintWriter;
import edu.grinnell.csc207.groupgroup.utils.UnknownRegisterException;
import edu.grinnell.csc207.groupgroup.utils.InvalidInputStringException;


public class Calculator {
//fields    
Fraction r0, r1, r2, r3, r4, r5, r6, r7;

	public Calculator() {
		this.r0 = new Fraction("0/1");
		this.r1 = new Fraction("0/1");
		this.r2 = new Fraction("0/1");
		this.r3 = new Fraction("0/1");
		this.r4 = new Fraction("0/1");
		this.r5 = new Fraction("0/1");
		this.r6 = new Fraction("0/1");
		this.r7 = new Fraction("0/1");
	}

	private Fraction stringToFraction(String input) throws UnknownRegisterException {
		if (input.indexOf("/") != -1) {
			return new Fraction(input);
		} else if (input.charAt(0) == 'r') {
			String regNumString = input.substring(1,input.length());
			int regNum = Integer.parseInt(regNumString);
			switch (regNum) {
			case 0: {
				return this.r0;
			}
			case 1: {
				return this.r1;
			}
			case 2: {
				return this.r2;
			}
			case 3: {
				return this.r3;
			}
			case 4: {
				return this.r4;
			}
			case 5: {
				return this.r5;
			}
			case 6: {
				return this.r6;
			}
			case 7: {
				return this.r7;
			}
			default: throw new UnknownRegisterException();
			}
		}
		return new Fraction(Integer.valueOf(input), 1);

	}
	
	public void checkStringFormat(String input) throws InvalidInputStringException
	{
		
		//some quick and dirty equality tests for registers
		
		
		boolean currentBlockShouldBeOperand = true;
		String currentBlock = "";
		boolean done = false;
		int sepIndex = input.indexOf(" ");
		char[] operators = {'=', '+', '-', '*', '/', '^'};
		
		while (!done)
		{
			sepIndex = input.indexOf(" ");
			if (sepIndex < 0){// if no space can be found from start of given string
				System.out.println("Last Block: " + input);
				currentBlock = input;
				done = true;
			} else {
				currentBlock = input.substring(0,sepIndex);
				input = input.substring(sepIndex + 1,input.length());
			}
			
			if (currentBlockShouldBeOperand){// Check syntax of the operand
				try{
					stringToFraction(currentBlock);
				}catch (NumberFormatException e){
					throw new InvalidInputStringException("Format of Operand in Input String is incorrect.");
				}catch (UnknownRegisterException e){
					throw new InvalidInputStringException("Register in Input String is invalid");
				}catch (StringIndexOutOfBoundsException e){
					throw new InvalidInputStringException("Format of Operand in Input String is incorrect.");
				}
			} else {// Check syntax of the operator
				if (currentBlock.length() == 1){
					boolean operatorTest = false;
					for (int i = 0; i < operators.length; i++){
						if (currentBlock.charAt(0) == operators[i])
							operatorTest = true;
					}
					
					if (operatorTest == false)
						throw new InvalidInputStringException(currentBlock + " is an invalid operator.");
				} else {
					throw new InvalidInputStringException(currentBlock + " is not an operator.");
				}	
			}

			currentBlockShouldBeOperand = !currentBlockShouldBeOperand;
		}
		
		if(!currentBlockShouldBeOperand == false)
		{
			throw new InvalidInputStringException("Input string ends with an operator");
		}
	}
    
	public Fraction eval(String input) throws UnknownRegisterException,NumberFormatException
	{ 	//check if string is properly formatted
		try{
			checkStringFormat(input);
		}catch (InvalidInputStringException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		
		Fraction result;
		char oper;
		int storageChecker = 0;
		
		int sepIndex = input.indexOf(" ");
		
		if(sepIndex > 0)
		{
		    	 if (input.indexOf("=") == 3) {
		    	     sepIndex = input.indexOf(" ", 5);
		    	     result = stringToFraction(input.substring(5, sepIndex));
		    	     storageChecker++;
		    	     input = input.substring((sepIndex + 1), input.length());
		    	 }
			 else{
			result = stringToFraction(input.substring(0, sepIndex)); 
			input = input.substring((sepIndex + 1), input.length()); 
			 }
		
		}
		else {
		    
			result = stringToFraction((input.substring(0,input.length())));
			return result;
		}

		stringIterator: while (true) {
			oper = input.charAt(0); // set oper="+"
			switch (oper) {
			case '+': {
				input = input.substring(2, input.length());
				
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) {
					Fraction modifier = stringToFraction(input.substring(0,
							input.length()));
					result = result.add(modifier);
					break stringIterator;
				} else {
					result = result.add(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}
				break;
			}
			case '-': {
				input = input.substring(2, input.length());
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) {
					result = result.subtract(stringToFraction(input.substring(0,
							input.length())));
					break stringIterator;
				} else {
					result = result.subtract(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}
				break;
			}
			case '/': {
				input = input.substring(2, input.length());
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) {
					result = result.divide(stringToFraction(input.substring(0,
							input.length())));
					break stringIterator;
				} else {
					result = result.divide(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}
				break;
			}
			case '*': {
				input = input.substring(2, input.length());
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) {
					result = result.multiply(stringToFraction(input.substring(0,
							input.length())));
					break stringIterator;
				} else {
					result = result.multiply(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}
				break;
			}
			case '^': {
				input = input.substring(2, input.length());
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) { //casting doubles as ints because I can
					result = result.pow((int) stringToFraction( input.substring(0,
							input.length())).doubleValue());
					break stringIterator;
				} else {
					result = result.pow((int) stringToFraction(input.substring(0,
							sepIndex)).doubleValue());
					input = input.substring((sepIndex + 1), input.length());
				}
				break;
			}
			default:
				break;
			}
		}
		if (storageChecker == 1){
		    char regNum = input.charAt(1);
			   switch(regNum)
			   {
			   case '0':{
			       this.r0 = result;
			   }
			   case '1':{
			        this.r1 = result;
			   }
			   case '2':{
			       this.r2 = result;
			   }
			   case '3':{
			       this.r3 = result;
			   }
			   case '4':{
			       this.r4 = result;
			   }
			   case '5':{
			       this.r5 = result;
			   }
			   case '6':{
			       this.r6 = result;
			   }
			   case '7':{
			       this.r7 = result;
			   }
			   }
		}
		
		return result;
	} //calculatorEval0(String input)
	public static void main(String[] args) throws Exception {
	}//main(String)
}
