package test;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;

/**
 * This program demonstrates how to rectangles using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class mainexample extends JFrame {

	public mainexample() {
super("Rectangles Drawing Demo");

getContentPane().setBackground(Color.WHITE);
setSize(480, 200);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
}
void drawRectangles(Graphics gr) {
	Graphics2D g2d = (Graphics2D) gr;	
	
}

public void paint(Graphics g) {
super.paint(g);
drawRectangles(g);}

public static void main(String[] args) throws Exception {
SwingUtilities.invokeLater(new Runnable() {
@Override
public void run() {
new mainexample().setVisible(true);
}
});
}
}