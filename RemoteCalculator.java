// RemoteCalculator class contains implementation of CalculatorInterface
public class RemoteCalculator implements CalculatorInterface{
    public double calculate(String expression, String x) throws Exception {
        String ExpressionToBeEvaluated;
        String xVal = x;
        // check 'expression' for errors
        xSubstitution subX = new xSubstitution(expression, xVal);
        ExpressionToBeEvaluated = subX.getUpdatedExpression();
        ErrorChecking checkErrors = new ErrorChecking(ExpressionToBeEvaluated);
        EvaluateExpression evaluateInput = new EvaluateExpression(ExpressionToBeEvaluated);
        return Double.parseDouble(evaluateInput.solveExpression());
    }
}
