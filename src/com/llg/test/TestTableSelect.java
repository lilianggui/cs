package com.llg.test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TestTableSelect extends JFrame{

    public TestTableSelect(){
        this.setTitle("下拉列表框使用");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,1000,500);
        this.setLayout(null);

        JPanel tradeTime = new JPanel();
        tradeTime.setBounds(10,10,602,24);
        tradeTime.setBorder(BorderFactory.createEtchedBorder());
        tradeTime.setLayout(null);
        JLabel label = new JLabel("Trading Hours:",JLabel.RIGHT);
        label.setFont(new Font("宋体",Font.PLAIN,12));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(128,128,128));
        label.setSize(200,24);
        tradeTime.add(label);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Normal : 09:15 - 12:00 / 13:00 - 16:30 (Monday - Friday)");
        comboBox.addItem("Half Day : 09:15 - 12:30 (Monday - Friday)");
        comboBox.addItem("Day Closed");
        comboBox.setBounds(200,0,400,24);
        comboBox.setBackground(Color.WHITE);
        tradeTime.add(comboBox);

        this.add(tradeTime);
        this.setVisible(true);
    }
    public static void main(String[]args){
        TestTableSelect example=new TestTableSelect();
    }
}
