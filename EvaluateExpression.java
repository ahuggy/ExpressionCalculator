// public class that calls the methods necessary to evaluate the user expression
public class EvaluateExpression {
    // class-variable that contains the expression input by the user
    String expression;

    // class constructor assigns input to String 'expression'
    public EvaluateExpression(String expressionToBeCalculated) {
        expression = expressionToBeCalculated;
    }

    // public method 'solveExpression' solves the user input and returns the value
    public String solveExpression(){
        // while the current expression contains parenthesis, evaluate the innermost parenthesis
        while(expression.contains("(")) {
            calculateInnerExpression();
        }
        // evaluate the expression once all the parenthesis have been solved
        return evaluateExpression(expression);
    }

    // method to isolate the innermost parenthetical expression and solve (pass to inner function)
    public void calculateInnerExpression(){
        String innerMostExpression, expressionValue;
        int indexOfLeftP = 0, indexOfRightP = 0;
        for(int i=0;i<expression.length();i++){
            if(expression.charAt(i) == '('){
                indexOfLeftP = i;
            }
            if(expression.charAt(i) == ')'){
                indexOfRightP = i;
                innerMostExpression = expression.substring(indexOfLeftP+1,indexOfRightP);
                expressionValue = evaluateExpression(innerMostExpression);
                expression = expression.replace("(" + innerMostExpression + ")",expressionValue);
                return;
            }
        }
    }

    // TEST METHOD
    // this method will be replaced with the inner-function to solve an expression w/o parenthesis
    public String evaluateExpression(String expression){
        return "3";
    }

}
