package com.levy.commom.geo.gui;

import com.levy.commom.geo.gui.action.QueryListener;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton queryBut;
    private JTextField inputText;
    private JLabel resultLable;
    private JTextArea logTextArea;
    private LogHelper log = LogHelper.getLog();

    public MainPage() {
        this.setLayout(null);
        buildLayout();
        completeLayout();
    }

    private void buildLayout() {
        Font font20 = new Font("宋体", Font.PLAIN, 20);
        Font font18 = new Font("宋体", Font.PLAIN, 18);

        JLabel title = new JLabel("Input latitude,longitude to query district.");
        title.setBounds(170, 10, 600, 40);
        title.setFont(font20);

        JLabel geoLable = new JLabel("latitude,longitude :");
        geoLable.setBounds(30, 80, 200, 40);
        geoLable.setFont(font18);

        inputText = new JTextField();
        inputText.setBounds(220, 80, 280, 40);
        inputText.setEditable(true);
        inputText.setFont(font18);

        queryBut = new JButton("Query");
        queryBut.setBounds(520, 80, 120, 40);
        queryBut.setFont(font18);

        resultLable = new JLabel("Found district: 成都市 锦江区");
        resultLable.setBounds(30, 150, 500, 40);
        resultLable.setFont(font18);

        logTextArea = new JTextArea();
        log.setLogTextArea(logTextArea);
        JScrollPane jsp = new JScrollPane(logTextArea);
        jsp.setBounds(50, 250, 520, 340);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(jsp);
        this.add(title);
        this.add(geoLable);
        this.add(resultLable);
        this.add(inputText);
        this.add(queryBut);
    }

    private void completeLayout() {
        queryBut.addActionListener(new QueryListener(inputText, resultLable));
    }
}
