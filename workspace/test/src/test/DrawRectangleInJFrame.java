package test;

import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Color;

public class DrawRectangleInJFrame extends JFrame
{
public DrawRectangleInJFrame()
{
 //Set JFrame title
 super("Draw A Rectangle In JFrame");

 //Set default close operation for JFrame
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 //Set JFrame size
 setSize(400,400);

 //Make JFrame visible 
 setVisible(true);
}

public void paint(Graphics g)
{
 super.paint(g);

 //draw rectangle outline
 g.drawRect(50,50,300,100);
 
 
 
 g.drawLine(50, 50, 70, 70);

 //set color to RED
 //So after this, if you draw anything, all of it's result will be RED
 g.setColor(Color.RED);

 //fill rectangle with RED
 //g.fillRect(50,50,300,100);
}

public static void main(String[]args)
{
 DrawRectangleInJFrame dlijf=new DrawRectangleInJFrame();
}
}
