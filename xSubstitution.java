// class that substitutes x into expression if x-value is specified.
public class xSubstitution {
    private String expression, xVal;
    public xSubstitution(String expressionToBeSub, String xValToBeSub){
        expression = expressionToBeSub;
        xVal = xValToBeSub;
        // If expression has 'x' but user doesn't specify x-value throw exception
        if (expression.contains("x")){
            if (xVal.isEmpty()){
                throw new IllegalArgumentException("x value must be specified if expression contains 'x'");
            }
            checkExpressionWithX();
        }
        // if expression doesn't contain 'x' but user specifies an x-value throw exception
        else if (!expression.contains("x")){
            if (!xVal.isEmpty()){
                throw new IllegalArgumentException("x value shouldn't be specified unless expression contains 'x'");
            }
        }
    }

    public String getUpdatedExpression(){
        // if expression contains 'x', substitute xVal for 'x' in expression
        if (expression.contains("x")){
            expression = expression.replaceAll("x",xVal);
        }
        return expression;
    }

    // Method to check if any x's in the expression are syntactically correct
    public void checkExpressionWithX(){
        int currentCharEval;
        for (currentCharEval = 0; currentCharEval < expression.length();  currentCharEval++){
            if (expression.charAt(currentCharEval) == 'x'){
                checkForDuplicateNumbers(currentCharEval);
            }
        }
    }

    // Method to check expression for number/x encountered before an operand on either side of 'x'
    public void checkForDuplicateNumbers(int currentChar){
        for(int i = currentChar+1; i < expression.length(); i++){
            if((expression.charAt(i) == '+')
                    ||(expression.charAt(i) == '-')
                    ||(expression.charAt(i) == '*')
                    ||(expression.charAt(i) == '/')
                    ||(expression.charAt(i) == '^')
                    ||(expression.charAt(i) == 'r')){
                break;
            }
            else if((expression.charAt(i) == 'x')
                    ||(Character.isDigit(expression.charAt(i)))){
                throw new IllegalArgumentException("Check expression: x-value shouldn't be adjacent to a numerical value (or another 'x')");
            }
        }
        for(int i = currentChar-1; i >= 0; i--){
            if((expression.charAt(i) == '+')
                    ||(expression.charAt(i) == '-')
                    ||(expression.charAt(i) == '*')
                    ||(expression.charAt(i) == '/')
                    ||(expression.charAt(i) == '^')
                    ||(expression.charAt(i) == 'r')){
                break;
            }
            else if((expression.charAt(i) == 'x')
                    ||(Character.isDigit(expression.charAt(i)))){
                throw new IllegalArgumentException("Check expression: x-value shouldn't be adjacent to a numerical value (or another 'x')");
            }
        }
    }

}
