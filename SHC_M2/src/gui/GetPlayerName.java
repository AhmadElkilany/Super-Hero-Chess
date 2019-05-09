package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GetPlayerName extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextArea t1;
	private String name;
	
	public static void main(String[] args) {
		GetPlayerName w1 = new GetPlayerName();
		w1.setVisible(true);
	}
	public GetPlayerName() {
		super();
		setLayout(new BorderLayout());
		setSize(300,100);
		t1 = new JTextArea();
		add(t1, BorderLayout.CENTER);
		JButton o = new JButton("OK");
		add(o, BorderLayout.SOUTH);
		o.addActionListener(this);
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JTextArea getT1() {
		return t1;
	}
	public void actionPerformed(ActionEvent e) {
		setName(getT1().getText());
		dispose();
	}
	
}
