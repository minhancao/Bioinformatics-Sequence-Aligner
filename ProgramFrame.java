import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ProgramFrame extends JFrame {
	protected JTextField inputField1 = new JTextField("");
	protected JTextField inputField2 = new JTextField("");
	protected JLabel label1 = new JLabel("Sequence 1: ");
	protected JLabel label2 = new JLabel("Sequence 2: ");
	protected JLabel result1 = new JLabel("Result 1: ");
	protected JLabel result2 = new JLabel("Result 2: ");
	protected JLabel traceBack = new JLabel("Traceback: ");
	protected JLabel score = new JLabel("Optimal Score: ");
	protected JButton alignButton = new JButton("Align!");
	protected JButton clearButton = new JButton("Clear All!");
	protected JButton textButton = new JButton("Text File Output!");
	protected JTextField textField = new JTextField("");
	protected JLabel writeStatus = new JLabel("Write Status: ");
	
	public static void main (String [] args)
	{
		Font font = new Font("Monospaced", Font.PLAIN, 14);

		SequenceAligner sequenceAligner = new SequenceAligner();
		
		ProgramFrame frame = new ProgramFrame();
		frame.inputField1.setFont(font);
		frame.inputField2.setFont(font);
		frame.label1.setFont(font);
		frame.label2.setFont(font);
		frame.result1.setFont(font);
		frame.result2.setFont(font);
		frame.traceBack.setFont(font);
		frame.score.setFont(font);
		frame.alignButton.setFont(font);
		frame.clearButton.setFont(font);
		frame.textButton.setFont(font);
		frame.textField.setFont(font);
		frame.writeStatus.setFont(font);
		frame.setTitle("PWA Program");
		frame.setPreferredSize(new Dimension(580, 335));
		frame.setLayout(new BorderLayout());
		
		frame.inputField1.setPreferredSize(new Dimension(300, 14));		
		frame.inputField2.setPreferredSize(new Dimension(300, 14));
		
		
		Box vBox = Box.createVerticalBox();
		
		Box hBox1 = Box.createHorizontalBox();
		hBox1.add(frame.label1);
		hBox1.add(frame.inputField1);
		hBox1.setMaximumSize(new Dimension(500, 30));
		
		Box hBox2 = Box.createHorizontalBox();
		hBox2.add(frame.label2);
		hBox2.add(frame.inputField2);
		hBox2.setMaximumSize(new Dimension(500, 30));
		
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		vBox.add(hBox1);
		vBox.add(hBox2);
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		Box hBoxText2 = Box.createHorizontalBox();
		hBoxText2.add(frame.alignButton);
		hBoxText2.add(Box.createRigidArea(new Dimension(14, 15)));
		hBoxText2.add(frame.clearButton);
		vBox.add(hBoxText2);
		
		Box hBoxText = Box.createHorizontalBox();
		hBoxText.add(frame.textButton);
		hBoxText.add(frame.textField);
		hBoxText.setMaximumSize(new Dimension(500, 30));
		
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		vBox.add(hBoxText);
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		vBox.add(frame.writeStatus);
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		vBox.add(frame.result1);
		vBox.add(frame.result2);
		vBox.add(frame.traceBack);
		vBox.add(frame.score);
		vBox.add(Box.createRigidArea(new Dimension(5, 15)));
		
		frame.alignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequenceAligner.clearAllData();
				sequenceAligner.setSequences(frame.inputField1.getText(), frame.inputField2.getText());
				sequenceAligner.align();
				frame.result1.setText("Result 1: " + sequenceAligner.getResult1());
				frame.result2.setText("Result 2: " + sequenceAligner.getResult2());
				frame.traceBack.setText("Traceback: " + sequenceAligner.getTraceBack());
				frame.score.setText("Optimal score: " + sequenceAligner.getScore()); 
			}
		});
		
		frame.clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sequenceAligner.clearAllData();
				frame.inputField1.setText("");
				frame.inputField2.setText("");
				frame.result1.setText("Result 1: ");
				frame.result2.setText("Result 2: ");
				frame.traceBack.setText("Traceback: ");
				frame.score.setText("Optimal score: "); 
			}
		});
		
		frame.textButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!sequenceAligner.writeToFile(frame.textField.getText()))
					{
						frame.writeStatus.setText("Write Status: Failed, please align first!");
					}
					
					else
					{
						frame.writeStatus.setText("Write Status: Successful!");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Box hBoxAll = Box.createHorizontalBox();
		hBoxAll.add(Box.createRigidArea(new Dimension(20, 15)));
		hBoxAll.add(vBox);
		frame.add(hBoxAll, BorderLayout.NORTH);
		
		for (Component comp : vBox.getComponents()) {
			((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}