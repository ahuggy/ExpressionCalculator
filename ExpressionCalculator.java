import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ExpressionCalculator implements ActionListener {

    private Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();

    private JFrame WindowMain = new JFrame();

    private JPanel inputPanel = new JPanel();
    private JLabel expressionLabel = new JLabel("Enter Simple Expression:");
    private JTextField expressionInput = new JTextField(10);

    private JPanel buttonPanel = new JPanel();
    private JButton evaluate = new JButton("Evaluate");
    private JButton clear = new JButton("Clear");

    private JPanel xEqualsPanel = new JPanel();
    private JLabel xEquals = new JLabel("X=");
    private JTextField xInput = new JTextField(5);

    private JPanel outputPanel = new JPanel();
    private JTextArea outputExpression = new JTextArea();
    private JScrollPane outputScrollPane = new JScrollPane(outputExpression);

    private JPanel errorPanel = new JPanel();
    private JTextField errorMsg = new JTextField();

    private String newLine = System.lineSeparator();

    public ExpressionCalculator() {
        // TODO Auto-generated constructor stub
        WindowMain.setTitle("Simple Expressions Calculator");

        //----------  Build GUI  -------------------------
        //------------------------------------------------
        //----------  Build Input Panel  -----------------

        //----------  Build XEquals SubPanel  ------------
        //xEqualsPanel.setLayout(new GridLayout(1,2));
        xEqualsPanel.add(xEquals);
        xEqualsPanel.add(xInput);

        //----------  Build buttonPanel subpanel  --------
        buttonPanel.setLayout(new GridLayout(1,2));
        buttonPanel.add(evaluate);
        buttonPanel.add(clear);
        evaluate.setBackground(Color.BLACK);
        evaluate.setForeground(Color.yellow);
        clear.setBackground(Color.CYAN);
        clear.setForeground(Color.BLACK);

        //----------  Build inputPanel  ------------------
        //  assemble subpanels
        inputPanel.setLayout(new GridLayout(1,3));
        inputPanel.add(expressionLabel);
        inputPanel.add(expressionInput);
        inputPanel.add(xEqualsPanel);
        inputPanel.add(buttonPanel);

        //----------  Build outputPanel  -----------------
        outputPanel.setLayout(new GridLayout(1,1));
        outputPanel.add(outputScrollPane);
        outputExpression.setEditable(false);

        //----------  Build errorPanel  ------------------
        errorPanel.setLayout(new GridLayout(1,1));
        errorPanel.add(errorMsg);
        errorMsg.setEditable(false);
        errorMsg.setBackground(Color.PINK);
        errorMsg.setForeground(Color.BLACK);

        //Set panel locations
        WindowMain.getContentPane().add(inputPanel, "North");
        WindowMain.getContentPane().add(outputPanel, "Center");
        WindowMain.getContentPane().add(errorPanel, "South");

        //add borders for output and error panels
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLACK);
        expressionInput.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        xInput.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        outputScrollPane.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        errorMsg.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 0, 0, 0)));



        //Set a reasonable windows size
        WindowMain.setSize(screenSize.width/2, screenSize.height/2);

        //Start GUI in the top left
        WindowMain.setLocation(screenSize.width/8,screenSize.height/8);

        //Show window and terminate on close
        WindowMain.setVisible(true);
        WindowMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		//Add action listeners
//		expressionInput.addActionListener(this);
//		xInput.addActionListener(this);
		evaluate.addActionListener(this);
		clear.addActionListener(this);
    }

    public static void main(String[] args)
    {
        System.out.println("Andrew Huggins & James Oden");
        new ExpressionCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // if the 'evaluate' button was pressed
        if (ae.getSource() == evaluate){
            // clear the error message box
            errorMsg.setText("");
            // assign user input to string 'expression'
            String expression = expressionInput.getText();
            // check 'expression' for errors
            try {
                ErrorChecking checkErrors = new ErrorChecking(expression);
				EvaluateExpression evaluateInput = new EvaluateExpression(expression);
				outputExpression.append(evaluateInput.solveExpression() + newLine);
			}
            catch (IllegalArgumentException iae){
                errorMsg.setText(iae.getMessage());
                return;
            }
        }
        if (ae.getSource() == clear){
            expressionInput.setText("");
        }
    }
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	// Argument: expression with no parenthesis and an arbitrary number of operands & operators
	// Return: the value of the expression as a string
	//-----------------------------------------------------------------------------------------------------------------------------------
	  private static String solveExpression(String simpleExpression) throws Exception {
	    
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
	         //continue;
	         }

	      // find operands!
	      String leftOperand  = expression.substring(0,i).trim();
	      String rightOperand = expression.substring(i+1).trim();

	      // convert operands from String to double 
	      // Note that parseDouble() will allow a unary operator!
	      try { 
	          leftNumber = Double.parseDouble(leftOperand);
	      }
	      catch(NumberFormatException nfe)
	          {
	          System.out.println("Left operand is not numeric.");
	      //continue;
	      }
	      try {
	          rightNumber = Double.parseDouble(rightOperand);
	      }
	      catch(NumberFormatException nfe)
	          {
	          System.out.println("Right operand is not numeric.");
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
	       return expression;
	     }
	
	

}
