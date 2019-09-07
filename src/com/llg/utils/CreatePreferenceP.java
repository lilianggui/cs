package com.llg.utils;

import com.llg.main.Const;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CreatePreferenceP {

    private JTable trendTable;
    private JTable preAlertTable;
    private JTextField accountField;
    private JTextField contractField;
    private JTextField currencyField;
    private JTextField trendIdentifierField;
    private JTextField cutOffField;

    public JPanel createLabelTextFieldPanel(int y,String labelText,String textFieldValue){
        JPanel jp = new JPanel();
        jp.setBounds(10,y,600,25);
        jp.setBorder(BorderFactory.createEtchedBorder());
        jp.setLayout(null);

        JLabel label = new JLabel(labelText+" : ",JLabel.RIGHT);
        label.setFont(new Font("Arial",Font.BOLD,12));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(128,128,128));
        label.setSize(200,24);
        jp.add(label);

        trendIdentifierField = new JTextField(textFieldValue);
        trendIdentifierField.setFont(new Font("Arial",Font.BOLD,12));
        trendIdentifierField.setBounds(200,0,400,25);
        trendIdentifierField.setBackground(Color.WHITE);
        trendIdentifierField.setForeground(Color.BLACK);
        jp.add(trendIdentifierField);

        return jp;
    }

    public JTable getTrendTable() {
        return trendTable;
    }

    public void setTrendTable(JTable trendTable) {
        this.trendTable = trendTable;
    }

    public JTextField getAccountField() {
        return accountField;
    }

    public void setAccountField(JTextField accountField) {
        this.accountField = accountField;
    }

    public JTextField getContractField() {
        return contractField;
    }

    public void setContractField(JTextField contractField) {
        this.contractField = contractField;
    }

    public JTable getPreAlertTable() {
        return preAlertTable;
    }

    public void setPreAlertTable(JTable preAlertTable) {
        this.preAlertTable = preAlertTable;
    }

    public JTextField getCurrencyField() {
        return currencyField;
    }

    public void setCurrencyField(JTextField currencyField) {
        this.currencyField = currencyField;
    }

    public JTextField getCutOffField() {
        return cutOffField;
    }

    public void setCutOffField(JTextField cutOffField) {
        this.cutOffField = cutOffField;
    }
}
