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
}
