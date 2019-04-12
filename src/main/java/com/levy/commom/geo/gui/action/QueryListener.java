package com.levy.commom.geo.gui.action;

import com.levy.commom.geo.model.Point;
import com.levy.commom.geo.util.GeoMapHelper;
import com.levy.commom.geo.GeoTool;
import com.levy.commom.geo.model.Division;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class QueryListener implements ActionListener {

    private JTextField inputText;
    private JLabel resultLable;

    public QueryListener() {
    }

    public QueryListener(JTextField inputText, JLabel resultLable) {
        this.inputText = inputText;
        this.resultLable = resultLable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GeoTool tool = GeoTool.getInstance();

        String input = inputText.getText();
        log.warn("Query with :" + input);
        if (StringUtils.isBlank(input)) {
            resultLable.setText("Found district: Not Found.");
            return;
        }

        String[] geo = input.split(",");
        if (geo.length != 2) {
            resultLable.setText("Found district: Parameter not correct.");
            return;
        }

        Division division = tool.findDivision(Double.valueOf(geo[0]), Double.valueOf(geo[1]));
        Point[] points = GeoMapHelper.extractPoints(division);
        resultLable.setText("Found district: " + division.getName());
    }
}