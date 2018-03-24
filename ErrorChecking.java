// public class that contains error checking methods for input expression
public class ErrorChecking {
    // class-variable that contains the expression input by the user
    String expression;

    // constructor calls the necessary error-checking methods
    public ErrorChecking(String expressionToBeChecked) {
        expression = expressionToBeChecked;
        checkForParenthesisMatching();
    }

    // ensure that the number of "(" matches ")"
    private void checkForParenthesisMatching() {
        int numOfLeftP = 0, numOfRightP = 0;
        for(int i=0;i<expression.length();i++){
            if(expression.charAt(i) == '('){
                numOfLeftP++;
            }
            if(expression.charAt(i) == ')'){
                numOfRightP++;
            }
        }
        if(numOfLeftP != numOfRightP){
            System.out.println("The number of left parenthesis must equal the number of right parenthesis.");
            throw new IllegalArgumentException("The number of left parenthesis must equal the number of right parenthesis.");
        }
    }
}
