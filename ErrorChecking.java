// public class that contains error checking methods for input expression
public class ErrorChecking {
    // class-variable that contains the expression input by the user
    String expression;

    // constructor calls the necessary error-checking methods
    public ErrorChecking(String expressionToBeChecked) {
        expression = expressionToBeChecked;
        checkForParenthesisMatching();
        checkForMissingOperator();
        checkForMissingOperand();
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


    // ensure that there is an operator in the expression
    private void checkForMissingOperator() {
        /*
        for (int i = 0; i < expression.length(); i++) {
            switch(expression.charAt(i)){
                case '(': checkForDuplicateParentheticalExpressions(i);
            }
        }
        */
            for (int i = 0; i < expression.length(); i++) {
            switch(expression.charAt(i)){
                case '^': return;
                case 'r': return;
                case '*': return;
                case '/': return;
                case '+': return;
                case '-': return;
            }
        }
        throw new IllegalArgumentException("Missing operator.");
    }


    private void checkForMissingOperand() {
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) == '+')
                    || (expression.charAt(i) == '-')
                    || (expression.charAt(i) == '*')
                    || (expression.charAt(i) == '/')
                    || (expression.charAt(i) == '^')
                    || (expression.charAt(i) == 'r')) {
                checkForOperandFollowingIndex(i);
            }
        }
    }

    public void checkForOperandFollowingIndex(int currentChar){
        for(int i = currentChar+1; i < expression.length(); i++){
            if((expression.charAt(i) == '+')
                    ||(expression.charAt(i) == '-')
                    ||(expression.charAt(i) == '*')
                    ||(expression.charAt(i) == '/')
                    ||(expression.charAt(i) == '^')
                    ||(expression.charAt(i) == 'r')){
                throw new IllegalArgumentException("Missing operand");
            }
            else if((expression.charAt(i) != ' ')
                    ||(expression.charAt(i) == ')')
                    ||(expression.charAt(i) == '(')){
                return;
            }
        }
    }

}
