import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// public class that calls the methods necessary to evaluate the user expression
public class EvaluateExpression {
    // class-variable that contains the expression input by the user
    String expression;
    String finalValue;

    // class constructor assigns input to String 'expression'
    public EvaluateExpression(String expressionToBeCalculated) {
        expression = expressionToBeCalculated;
        expression = expression.replace("pi", String.valueOf(Math.PI));
        expression = expression.replace("e", String.valueOf(Math.E));
    }

    // public method 'solveExpression' solves the user input and returns the value
    public String solveExpression() throws Exception{
        // while the current expression contains parenthesis, evaluate the innermost parenthesis
        while(expression.contains("(")) {
            calculateInnerExpression();
        }
        // evaluate the expression once all the parenthesis have been solved
        finalValue = solveInnerExpression(expression);
        checkForMultipleDecimals();
        return finalValue;
    }

	// method to ensure final value doesn't contain multiple decimals (Missing operand)
	public void checkForMultipleDecimals(){
		int counter = 0;
		for (int i=0; i<finalValue.length(); i++) {
			if (finalValue.charAt(i) == '.') {
				counter++;
			}
		}
		if (counter > 1){
			throw new IllegalArgumentException("Missing operator.");
		}
	}

    // method to isolate the innermost parenthetical expression and solve (pass to inner function)
    public void calculateInnerExpression() throws Exception{
        String innerMostExpression, expressionValue;
        int indexOfLeftP = 0, indexOfRightP = 0;
        boolean leftPEncountered = false;
        for(int i=0;i<expression.length();i++){
            if(expression.charAt(i) == '('){
                indexOfLeftP = i;
                leftPEncountered = true;
            }
            if(expression.charAt(i) == ')'){
                if (!leftPEncountered) {
                    throw new IllegalArgumentException("Check your parenthesis order please.");
                }
                indexOfRightP = i;
                innerMostExpression = expression.substring(indexOfLeftP+1,indexOfRightP);
                expressionValue = solveInnerExpression(innerMostExpression);
                expression = expression.replace("(" + innerMostExpression + ")",expressionValue);
                return;
            }
        }
    }

  //-----------------------------------------------------------------------------------------------------------------------------------
  	// Argument: expression with no parenthesis and an arbitrary number of operands & operators
  	// Return: the value of the expression as a string
  	//-----------------------------------------------------------------------------------------------------------------------------------
  	  private static String solveInnerExpression(String simpleExpression) throws Exception {
  	    
  	    boolean done;
  	    char current;
  	    int i;
  	    int leftOpStart,rightOpStart,leftOpEnd,rightOpEnd;
  	    double result;
  	    String blockResult = null;
  	    String leftOp;
  	    String rightOp;
  	    String complexity;
  	    String simple = "simple";
  	    String complex = "complex";
  	    List<Integer> operators = new ArrayList<Integer>();
  	    
  	    while(simpleExpression.contains("^") || simpleExpression.contains("r") || simpleExpression.contains("*") || simpleExpression.contains("/") || simpleExpression.contains("+") || simpleExpression.contains("-")){
  	      operators.clear();
  	      done = false;

  	      //-----------------------------------------------------------------------------
  	      //----------------------- Block 1 ---------------------------------------------
  	      //  A list of the indexes of all operators is generated
  	      //-----------------------------------------------------------------------------


  	      for(i = 0; i<simpleExpression.length();i++){            
  	        switch(simpleExpression.charAt(i)){
  	          case '^': operators.add(i); break;
  	          case 'r': operators.add(i); break;
  	          case '*': operators.add(i); break;
  	          case '/': operators.add(i); break; 
  	          case '+': operators.add(i); break;
  	          case '-': operators.add(i); break;
  	        }
  	      }
  	      
  	      
  	      switch(operators.size()) {
  	        case 1:{
  	          complexity = simple;
  	          break;
  	        } 
  	        default:{
  	          complexity = complex;
  	          break;
  	        }
  	      }

  	      if(complexity == simple){
  	        simpleExpression = solveSimpleExpression(simpleExpression);
  	 	    //System.out.println(" Simple ");
  	        return simpleExpression;
  	      }
  		    //System.out.println(" Complex ");

  	      //-----------------------------------------------------------------------------
  	      //----------------------- Block 2 ---------------------------------------------
  	      //  '^' and 'r' operators are handled
  	      //-----------------------------------------------------------------------------

  	      for(i = 0; i<operators.size();i++){
  	        if(done)break;
  	        current = simpleExpression.charAt(operators.get(i)); // retrieves the operand from the simpleExpression
  	        switch(current){
  	          case '^': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));   // operators.get(i) = location of first operator in simpleExpression
  	              leftOpStart = 0;                        // puts the first operand in a substring (leftOp)
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i)); // puts the operands between first and second operators in substring
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {                      // handles the last operand.  puts last operand in substring (rightOp)
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {                                  // puts the operand between second and third operators into substring (rightOp)
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }          
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }
  	            result = Math.pow(Double.parseDouble(leftOp), Double.parseDouble(rightOp));            
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;          
  	          }            
  	          case 'r': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }          
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }          
  	            result = Math.pow(Double.parseDouble(leftOp), (1/Double.parseDouble(rightOp)));
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");          
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;
  	          }
  	        }
  	      }

  	      //-----------------------------------------------------------------------------
  	      //----------------------- Block 3 ---------------------------------------------
  	      //  '*' and '/' operators are handled
  	      //-----------------------------------------------------------------------------
  	    
  	      for(i = 0; i<operators.size();i++){
  	        if(done)break;
  	        current = simpleExpression.charAt(operators.get(i));
  	        switch(current){
  	          case '*': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }          
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }
  	            result = Double.parseDouble(leftOp)*Double.parseDouble(rightOp);
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;
  	          }            
  	          case '/': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }          
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }
  	            if(Double.parseDouble(rightOp) == 0){
  	              throw new Exception("ERROR: Divide by Zero");
  	            }
  	            result = Double.parseDouble(leftOp)/Double.parseDouble(rightOp);
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;
  	          }
  	        }
  	      }
  	      
  	      //-----------------------------------------------------------------------------
  	      //----------------------- Block 4 ---------------------------------------------
  	      //  '+' and '-' operators are handled
  	      //-----------------------------------------------------------------------------


  	      if(done)continue;
  	      for(i = 0; i<operators.size(); i++){
  	        if(done)break;
  	        current = simpleExpression.charAt(operators.get(i));
  	        switch(current){
  	          case '+': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }
  	            result = Double.parseDouble(leftOp)+Double.parseDouble(rightOp);
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;
  	          }   
  	          case '-': {
  	            if(i == 0) {
  	              leftOp = simpleExpression.substring(0, operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = 0;
  	            }
  	            else{
  	              leftOp = simpleExpression.substring(operators.get(i-1)+1,operators.get(i));
  	              leftOpStart = 0;
  	              leftOpEnd = operators.get(i-1)+1;
  	            }
  	            if(i == (operators.size()-1)) {
  	              rightOp = simpleExpression.substring(operators.get(i)+1);
  	              rightOpStart = simpleExpression.length();
  	              rightOpEnd = simpleExpression.length();
  	            }
  	            else {
  	              rightOp = simpleExpression.substring(operators.get(i)+1,operators.get(i+1));  
  	              rightOpStart = operators.get(i+1);
  	              rightOpEnd = simpleExpression.length();
  	            }        
  	            if (leftOp.contains("u")) {
  	              leftOp = leftOp.replace("u", "-");
  	            }
  	            if (rightOp.contains("u")) {
  	              rightOp = rightOp.replace("u", "-");
  	            }
  	            result = Double.parseDouble(leftOp)-Double.parseDouble(rightOp);
  	            blockResult = Double.toString(result);
  	            if(blockResult.startsWith("-")) blockResult = blockResult.replace("-", "u");
  	            simpleExpression = simpleExpression.substring(leftOpStart, leftOpEnd) +blockResult + simpleExpression.substring(rightOpStart,rightOpEnd);
  	            done = true;
  	            break;
  	          }
  	        }
  	      }
  	      if(done)continue;
  	    }
  	    return simpleExpression;
  	  }  
  	  //-----------------------------------------------------------------------------------------------------------------------------------
  	  //-----------------------------------------------------------------------------------------------------------------------------------








  	private static String solveSimpleExpression(String expression) throws Exception {
  	      // find the operator!
  		//String simpleResult;
  	    double rightNumber = 0;
  	    double leftNumber = 0;

  	      char operator = ' ';
  	      int i;
  	      for (i = 1; i < expression.length(); i++) //(1st char shouldn't be an operator)
  	        {                                       // and starting at 1 allows a unary!
  	        if((expression.charAt(i) == '+')
  	         ||(expression.charAt(i) == '-')
  	         ||(expression.charAt(i) == '*')
  	         ||(expression.charAt(i) == '/')
  	         ||(expression.charAt(i) == '^')
  	         ||(expression.charAt(i) == 'r'))
  	          {
  	          operator = expression.charAt(i);
  	          break;
  	          }
  	        }
  	      if ((i == expression.length())   // operator is missing or is 1st char
  	       || (i == expression.length()-1))// or operator is last char
  	         {
  	         System.out.println("Expression is not an operator surrounded by operands.");
  	       throw new IllegalArgumentException("Expression is not an operator surrounded by operands: " + expression);
  	         //continue;
  	         }

  	      // find operands!
  	      String leftOperand  = expression.substring(0,i).trim();
  	      String rightOperand = expression.substring(i+1).trim();
        if (leftOperand.charAt(0) == 'u'){
            leftOperand = '-' + leftOperand.substring(1,leftOperand.length());
        }
        if (rightOperand.charAt(0) == 'u'){
            rightOperand = '-' + rightOperand.substring(1,rightOperand.length());
        }

        // convert operands from String to double
  	      // Note that parseDouble() will allow a unary operator!
  	      try {
  	          leftNumber = Double.parseDouble(leftOperand);
  	      }
  	      catch(NumberFormatException nfe)
  	          {
  	          System.out.println("Left operand is not numeric.");
  	        throw new IllegalArgumentException("Left operand is not numeric: " + leftOperand);
  	      //continue;
  	      }
  	      try {
  	          rightNumber = Double.parseDouble(rightOperand);
  	      }
  	      catch(NumberFormatException nfe)
  	          {
  	          System.out.println("Right operand is not numeric.");
  	          throw new IllegalArgumentException("Right operand is not numeric: " + rightOperand);
  	          //continue;
  	          }

  	     // If we are still executing at this point, we have a valid operator and two operands!
  	     // System.out.println("Left operand is "   + leftNumber
  	     //                  + " operator is "      + operator 
  	     //                  + " right operand is " + rightNumber);
  	    
  	     // calculate the value of the expression
  	     double result = 0;
  	     switch (operator)
  	       {
  	       case '+' : result = leftNumber + rightNumber; 
  	                  break;
  	       case '-' : result = leftNumber - rightNumber; 
  	                  break;
  	       case '*' : result = leftNumber * rightNumber; 
  	                  break;
  	       case '/' : result = leftNumber / rightNumber; 
  	                  break;
  	       case '^' : result = Math.pow(leftNumber,rightNumber); 
  	                  break;
  	       case 'r' : result = Math.pow(leftNumber, 1/rightNumber); 
  	                  break;
  	       }
  	     // Note the Math class offers square root and cube root methods,
  	     // but the form used above allows higher-order roots. 
  	     expression = Double.toString(result);
		if(expression.startsWith("-")) expression = expression.replace("-", "u");
		return expression;
  	     }

}
