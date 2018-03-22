import java.awt.*;

import javax.swing.*;

public class ExpressionCalculator {

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
		evaluate.setForeground(Color.BLACK);
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
		outputExpression.setText("test");

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
//		evaluate.addActionListener(this);
//		clear.addActionListener(this);
	}
	
	public static void main(String[] args)                                      
	{
	   System.out.println("Andrew Huggins");
		new ExpressionCalculator();
	}

}
