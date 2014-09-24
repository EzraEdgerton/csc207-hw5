package edu.grinnell.csc207.groupgroup.utils;

//import Calculator;

import java.io.PrintWriter;


public class Calculator {
//fields    
Fraction r0, r1, r2, r3, r4, r5, r6, r7;

    public Calculator()
    {
	this.r0 = new Fraction("0/1");
	this.r1 = new Fraction("0/1");
	this.r2 = new Fraction("0/1");
	this.r3 = new Fraction("0/1");
	this.r4 = new Fraction("0/1");
	this.r5 = new Fraction("0/1");
	this.r6 = new Fraction("0/1");
	this.r7 = new Fraction("0/1");
    }
    private Fraction stringToFraction(String input)
    {
	if(input.indexOf("/") != -1)
	{
	    return new Fraction(input);
	}
	else if(input.charAt(0) == 'r')
	{
	   char regNum = input.charAt(1);
	   switch(regNum)
	   {
	   case '0':{
	       return this.r0;
	   }
	   case '1':{
	       return this.r1;
	   }
	   case '2':{
	       return this.r2;
	   }
	   case '3':{
	       return this.r3;
	   }
	   case '4':{
	       return this.r4;
	   }
	   case '5':{
	       return this.r5;
	   }
	   case '6':{
	       return this.r6;
	   }
	   case '7':{
	       return this.r7;
	   }
	   }
	}
	       return new Fraction(Integer.valueOf(input), 1);

	}
	
    
	public Fraction eval(String input)
	{ 
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
	   Calculator calc = new Calculator();
		
		String thing = "r2 = 12 + -13";
		PrintWriter pen = new PrintWriter(System.out, true);
		Fraction answer = calc.eval(thing);
		pen.println(answer);
		pen.println(calc.eval("r2 + 2"));
		// pen.println(test.pow(5));
		pen.close();
	}//main(String)
}
