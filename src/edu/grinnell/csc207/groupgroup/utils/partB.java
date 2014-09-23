package edu.grinnell.csc207.groupgroup.utils;

public class partB {

    public static int
    expt (int x, int p)
  { 
	    int result = 1;
    if(p == 0){
        return result;
    }
	    while (p > 0){
		
	      if (p%2==1){
	        result *= x;
	        p--;
	      }
	      else{
		  x *= x;
		  p /= 2;
	      }
	    }
	    result = x;
	    return result;
	  }//while
}//partB
//code based off of code from http://www.programminglogic.com/fast-exponentiation-algorithms/
	
  
