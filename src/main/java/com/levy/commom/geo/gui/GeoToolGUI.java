package com.levy.commom.geo.gui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class GeoToolGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public GeoToolGUI() {
        super("Geo Tool");
        this.getContentPane().add(new MainPage());

        this.setSize(800, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
//        GeoTool.getInstance();
    }

    public static void main(String[] args) {
        new GeoToolGUI();
    }
}
