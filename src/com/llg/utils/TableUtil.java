package com.llg.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TableUtil {
    /**
     * 设置表头颜色，字体，字体颜色
     * @param table 要设置的表格
     * @param columnIndex 第几列
     * @param bg 背景颜色
     * @param fg 字体颜色
     * @param f 字体
     */
    public static void  setTableHeaderColor(JTable table, int columnIndex, final Color bg, final Color fg, final Font f) {
        TableColumn column = table.getTableHeader().getColumnModel().getColumn(columnIndex);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setBackground(bg);
                comp.setForeground(fg);
                comp.setFont(f);
                comp.setBorder(BorderFactory.createRaisedBevelBorder());
                return comp;
            }
        };
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        column.setHeaderRenderer(cellRenderer);
    }


    public static void setTableHeaderColors(JTable table, int columnStart,int columnEnd, final Color bg, final Color fg, final Font f) {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setBackground(bg);
                comp.setForeground(fg);
                comp.setFont(f);
                comp.setBorder(BorderFactory.createRaisedBevelBorder());
                return comp;
            }
        };
        for(int i = columnStart;i<=columnEnd;i++){
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            column.setHeaderRenderer(cellRenderer);
        }
    }

    /**
     * 设置某列的宽度
     * @param table
     * @param colIndex
     * @param width
     */
    public static void setColWidth(JTable table,int colIndex,int width){
        TableColumn firstColumn = table.getColumnModel().getColumn(colIndex);
        firstColumn.setPreferredWidth(width);
        firstColumn.setMaxWidth(width);
        firstColumn.setMinWidth(width);
    }

    /**
     * 设置表格文字居中
     * @param table
     */
    public static void setTableTextCenter(JTable table){
        DefaultTableCellRenderer r   = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
    }

    /**
     * 设置表头高度
     * @param table
     * @param height
     */
    public static void setTableHeadHeight(JTable table,int height){
        JTableHeader tableH = table.getTableHeader();
        Dimension size = tableH.getPreferredSize();
        size.height = height;
        tableH.setPreferredSize(size);
    }

    /**
     * 设置表头背景色，前景色，字体
     * @param table
     * @param bgc
     * @param fgc
     * @param f
     */
    public static void setTableHeadCF(JTable table,Color bgc,Color fgc,Font f){
        JTableHeader th = table.getTableHeader();
        th.setBorder(BorderFactory.createLineBorder(Color.black, 0));
        th.setBackground(bgc);
        //设置表头的文字颜色
        th.setForeground(fgc);
        th.setFont(f);
    }
}
