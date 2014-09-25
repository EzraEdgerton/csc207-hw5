package edu.grinnell.csc207.groupgroup.utils;

//import Calculator;
//got the base code for eval from charlie's homework#4
//got the fraction class from ezra's homework #4

import java.io.PrintWriter;
import edu.grinnell.csc207.groupgroup.utils.UnknownRegisterException;
import edu.grinnell.csc207.groupgroup.utils.InvalidInputStringException;
import java.io.IOException;



public class Calculator {
//fields 
    //the 8 registers, as fraction objects
Fraction r0, r1, r2, r3, r4, r5, r6, r7;
//constructor, initializes register fractions to 0
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
	
//private method to convert a given string to the appropriate fraction, whether it is an int
//or fraction or register. Throws exception when not any of those
	private Fraction stringToFraction(String input) throws UnknownRegisterException {
		if (input.indexOf("/") != -1) {
			return new Fraction(input);
		} //if
		else if (input.charAt(0) == 'r') {
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
			}//case
		}//else
		return new Fraction(Integer.valueOf(input), 1);

	}//StringToFraction
	
	//private function to, when given a character and a fraction, set the appropriate register
	//to be equal to the given fraction.
	//pre: regValue must be a char that is equivilant to an integer between 0 and 7
	//post: appropriate register is set equal to result
	private void Storer (char regValue, Fraction result)
	{
	    switch(regValue)
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
		   }//switch
	}//Storer

	//this function checks if the string is properly formatted and throws exceptions otherwise
	private void checkStringFormat(String input) throws InvalidInputStringException
	{
		
		//some quick and dirty equality tests for registers
	    if(input.indexOf('=') != -1)
	    {
		if(input.indexOf('=') != 3)
		{
			throw new InvalidInputStringException("Equality operator in incorrect location");
			
		}//if
		else
		{
			if(input.charAt(0) != 'r')
			{
				throw new InvalidInputStringException("Equality operator can only be used with registers");
			}//if
		}//else
	    }//if
		
		
		boolean currentBlockShouldBeOperand = true;
		String currentBlock = "";
		boolean done = false;
		int sepIndex = input.indexOf(" ");
		char[] operators = {'=', '+', '-', '*', '/', '^'};
		
		while (!done)
		{
			sepIndex = input.indexOf(" ");
			if (sepIndex < 0){// if no space can be found from start of given string
				currentBlock = input;
				done = true;
			}//if
			else {
				currentBlock = input.substring(0,sepIndex);
				input = input.substring(sepIndex + 1,input.length());
			}//else
			
			if (currentBlockShouldBeOperand){// Check syntax of the operand
				try{
					stringToFraction(currentBlock);
				}catch (NumberFormatException e){
					throw new InvalidInputStringException("Format of Operand in Input String is incorrect.");
				}catch (UnknownRegisterException e){
					throw new InvalidInputStringException("Register in Input String is invalid");
				}catch (StringIndexOutOfBoundsException e){
					throw new InvalidInputStringException("Format of Operand in Input String is incorrect.");
				}//try
			} //if
			else {// Check syntax of the operator
				if (currentBlock.length() == 1){
					boolean operatorTest = false;
					for (int i = 0; i < operators.length; i++){
						if (currentBlock.charAt(0) == operators[i])
							operatorTest = true;
					}//if
					
					if (operatorTest == false)
						throw new InvalidInputStringException(currentBlock + " is an invalid operator.");
				} //if
				else {
					throw new InvalidInputStringException(currentBlock + " is not an operator.");
				}//else	
			}//else

			currentBlockShouldBeOperand = !currentBlockShouldBeOperand;
		}//while
		
		if(!currentBlockShouldBeOperand == false)
		{
			throw new InvalidInputStringException("Input string ends with an operator");
		}//if
	}
    /*This function evaluates a given string in a basic calculator way, operating from left to right.
	it is capable of parsing the string into fractions, integers, registers and operators, and
	can set one of the registers equal to an expression as well.
	pre: input has a format of "r0 = 2 + 4/5", spaces in between every operator and operand.
	post: returns a fraction whose value is equivilant to solving the expression from left to right
		the modified registers are appropriately valued
	*/
	public Fraction eval(String input) throws UnknownRegisterException,NumberFormatException
	{ 	//check if string is properly formatted
		try{
			checkStringFormat(input);
		}catch (InvalidInputStringException e){
			System.out.println("ERROR: " + e.getMessage());
		}//try
		Fraction result;
		char oper;
		int storageChecker = 0;
		String original = input;
		
		int sepIndex = input.indexOf(" ");
		
		if(sepIndex > 0)
		{
		    //if there is an = 
		    	 if (input.indexOf("=") == 3) {
		    	     sepIndex = input.indexOf(" ", 5);
		    	     if (sepIndex < 0)
		    		 //if there is only one value attached to that register
		    	     {
		    		 result = stringToFraction(input.substring(5, input.length()));
		    		Storer (input.charAt(1), result);
		    		return result;
		    	     }//if
		    	     else
		    	     {
		    	     result = stringToFraction(input.substring(5, sepIndex));
		    	     storageChecker++;
		    	     input = input.substring((sepIndex + 1), input.length());
		    	     }//else
		    	 }//if
			 else{
			result = stringToFraction(input.substring(0, sepIndex)); 
			input = input.substring((sepIndex + 1), input.length()); 
			 }//else
		
		}//if
		else {
		    
			result = stringToFraction((input.substring(0,input.length())));
			return result;
		}//else
//moves through the rest of the string
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
				} //if
				else {
					result = result.add(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}//else
				break;
			}//case
			case '-': {
				input = input.substring(2, input.length());
				sepIndex = input.indexOf(" ");
				if (sepIndex == -1) {
					result = result.subtract(stringToFraction(input.substring(0,
							input.length())));
					break stringIterator;
				}//if 
				else {
					result = result.subtract(stringToFraction(input.substring(0,
							sepIndex)));
					input = input.substring((sepIndex + 1), input.length());
				}//else
				break;
			}//case
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
			}//case
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
			}//case
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
			}//case
			default:
				break;
			}
		}//switch
		//stores the value into register if there was an = at the third character
		if (storageChecker == 1){
		    Storer (original.charAt(1), result);
		}//if
		
		return result;
	} //eval(String input)
	
/*calcInterface creates a read/eval/write loop that can be exited by typing "exit"
 * prints errors if input is wrong or if calculator is prompted to do something mathematically
 * impossible.
*/	
    public void calcInterface()
	    throws IOException,UnknownRegisterException {
	//eyes and istream ideas taken from reading on Input and Output
	String input = new String();
	java.io.PrintWriter pen;
	pen = new java.io.PrintWriter(System.out, true);
	java.io.BufferedReader eyes;
	java.io.InputStreamReader istream;
	istream = new java.io.InputStreamReader(System.in);
	Calculator calc = new Calculator();
	pen = new java.io.PrintWriter(System.out, true);
	eyes = new java.io.BufferedReader(istream);
	input = eyes.readLine();
	while (input.compareTo("exit") != 0) {
	    pen.print(">");
	    if (calc.eval(input).denom == calc.eval("0/0").denom)
	    {
		pen.println("not able to divide by 0");
		input = eyes.readLine();
	    }//if
	    else{
	    pen.println(calc.eval(input));
	    input = eyes.readLine();
	    }//else
	}//while
	pen.close();
	eyes.close();
    }//calcInterface()
	//calls calcInterface
	public static void main(String[] args) throws Exception {
	   Calculator calc = new Calculator();
		calc.calcInterface();
		PrintWriter pen = new PrintWriter(System.out, true);
		pen.close();
	}//main(String)
}

		
		