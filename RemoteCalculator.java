// RemoteCalculator class contains implementation of CalculatorInterface
public class RemoteCalculator implements CalculatorInterface{
    public double calculate(String expression, double x) throws Exception {
        String ExpressionToBeEvaluated;
        String xVal = Double.toString(x);
        // check 'expression' for errors
        xSubstitution subX = new xSubstitution(expression, xVal);
        ExpressionToBeEvaluated = subX.getUpdatedExpression();
        ErrorChecking checkErrors = new ErrorChecking(ExpressionToBeEvaluated);
        EvaluateExpression evaluateInput = new EvaluateExpression(ExpressionToBeEvaluated);
        return Double.parseDouble(evaluateInput.solveExpression());
    }
}
