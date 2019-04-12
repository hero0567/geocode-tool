package com.levy.commom.geo.gui.drawer;


import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class exercise15_11 extends JFrame{
    public exercise15_11 (){
        setLayout(new BorderLayout());
        add(new eleven(), BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        exercise15_11 frame = new exercise15_11();
        frame.setSize(500, 500);
        frame.setTitle("Exercise15_7");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
@SuppressWarnings("serial")
class eleven extends JPanel{
    protected void  paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(".", 10000,10);
    }
}