package com.llg.utils;

import com.llg.main.Const;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CreateTableFac {

    private JTable dataTable;
    private JTable marketTable;
    private JTable nominalTable;
    private JTable dayMaxTable;
    private JTable entryTable;
    private JTable symbolTable;
    private JTable adjustmentTable;
    private JTable modeTable;
    private JTable currentPriceTable;


    public JScrollPane createMarketTable(){
        Object[][] rowData = {
                { "Dollar Per Point", ""},
                { "Risk-Per-Trade (RPT)", "" },
                { "Leverage Ratio", "" },
                { "Margin Requirement (Per Unit)", "" },
                { "RPT Dollar", "" },
                { "Volatility Dollar", "" },
                { "Unit-Per-Trade (UPT)", "" },
                { "UPT Rounded", "" },
        };

        String[] colName = { "Market", "NQ"};
        marketTable = new JTable(rowData, colName);
//        table.setPreferredScrollableViewportSize(new Dimension(700, 192));

        //table1样式
        TableUtil.setColWidth(marketTable,0,200);
        //设置行高
        marketTable.setRowHeight(21);
        //文字居中
        TableUtil.setTableTextCenter(marketTable);



        //设置表头高度
        TableUtil.setTableHeadHeight(marketTable,25);
        TableUtil.setTableHeadCF(marketTable,new Color(0, 153, 102),new Color(255, 255, 255),
                new Font("Arial", Font.BOLD, 15));

        marketTable.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 12));
        marketTable.setBorder(BorderFactory.createLineBorder(Color.black, 0));

        marketTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                dtcr.setHorizontalAlignment(JLabel.CENTER);
                Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if( column == 1){
                    cell.setFont(new Font("Arial", Font.BOLD, 12));
                }else{
                    cell.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 12));
                }
                return cell;
            }
        });

        JScrollPane jScrollPane = new JScrollPane(marketTable);
        jScrollPane.setLocation(4,2);
        jScrollPane.setSize(700, 196);
        return jScrollPane;
    }

    public JScrollPane createNominalTable(){
        Object[][] rowData = {
                { ""},
                { "Current Equity"},
                { ""},
                { "Start-up Equity"},
                { ""},
        };

        String[] colName = {"Nominal Equity"};
        nominalTable = new JTable(rowData, colName);
        //设置某列的宽度
//        com.llg.utils.TableUtil.setColWidth(table,0,350);
        //设置行高
        nominalTable.setRowHeight(24);


        //设置表头高度
        TableUtil.setTableHeadHeight(nominalTable,25);

        TableUtil.setTableHeadCF(nominalTable,new Color(255,153,0),new Color(255, 255, 255),
                new Font("Arial Black", Font.PLAIN, 14));

        nominalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                dtcr.setHorizontalAlignment(JLabel.CENTER);
                Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if( column == 0 && (row == 1 || row == 3)){
                    cell.setBackground(Color.BLACK);
                    cell.setForeground(Color.WHITE);
                    cell.setFont(new Font("Arial Black",Font.BOLD,14));
                }else{
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,14));
                }
                return cell;
            }
        });

        JScrollPane sp = new JScrollPane(nominalTable);

        sp.setLocation(844,2);
        sp.setSize(420, 148);
        return sp;
    }

    public JScrollPane createAdjustmentTable(){
        Object[][] rowData = {
                { ""},
        };

        String[] colName = {"Adjustment"};
        adjustmentTable = new JTable(rowData, colName);

        //设置行高
        adjustmentTable.setRowHeight(24);
        //文字居中
        TableUtil.setTableTextCenter(adjustmentTable);

        //设置表头高度
        TableUtil.setTableHeadHeight(adjustmentTable,25);

        TableUtil.setTableHeadCF(adjustmentTable,Color.WHITE,Color.BLACK,
                new Font("Arial", Font.BOLD, 12));
        adjustmentTable.setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(adjustmentTable);

        scrollPane.setLocation(1264,2);
        scrollPane.setSize(105, 52);
        return scrollPane;
    }

    public JScrollPane createCurrentPriceTable(){
        Object[][] rowData = {
                { ""},
        };

        String[] colName = {"Current Price"};
        currentPriceTable = new JTable(rowData, colName);

        //设置行高
        currentPriceTable.setRowHeight(22);
        //文字居中
        TableUtil.setTableTextCenter(currentPriceTable);

        //设置表头高度
        TableUtil.setTableHeadHeight(currentPriceTable,24);

        TableUtil.setTableHeadCF(currentPriceTable,Color.RED,Color.WHITE,
                new Font("Arial", Font.BOLD, 11));

        currentPriceTable.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(currentPriceTable);

        scrollPane.setLocation(1264,149);
        scrollPane.setSize(105, 49);
        return scrollPane;
    }

    public JScrollPane createDayMaxTable(){
        Object[][] rowData = {
                { "","","",""},
        };

        String[] colName = {"Day Max Loss RPT","Max Margin Level","Current Level","Previous Close"};
        dayMaxTable = new JTable(rowData, colName);
        //设置行高
        dayMaxTable.setRowHeight(22);
        //文字居中
        TableUtil.setTableTextCenter(dayMaxTable);

        //设置表头高度
        TableUtil.setTableHeadHeight(dayMaxTable,24);

        TableUtil.setTableHeadCF(dayMaxTable,new Color(150, 150, 150),new Color(255, 255, 255),
                new Font("Arial", Font.BOLD, 11));

        dayMaxTable.setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(dayMaxTable);

        scrollPane.setLocation(704,149);
        scrollPane.setSize(560, 49);
        return scrollPane;
    }


    public JScrollPane createSymbolTable(){
        Object[][] rowData = {
                { Const.PREFERENCES_DATA.getContract(),"","","", "","",""
                        ,Const.DECIMAL_FORMAT3.format(Const.PREFERENCES_DATA.getTrendIdentifier()), "","","","", "","","",""},
        };

        String[] colName = {"Symbol ","Highest High","Lowest Low","Intraday Volatility","- Protective-Stop -","- Trail-Stop -",
                "ABR Volatility","Trend Identifier","Identifier Point","Stoploss","Stoploss Point","T.I. MODE"};
        symbolTable = new JTable(rowData, colName);

        //设置单元格颜色
        symbolTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                dtcr.setHorizontalAlignment(JLabel.CENTER);
                Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if( column == 0 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,14));
                }else if( column == 1 && row == 0){
                    cell.setBackground(Color.RED);
                    cell.setForeground(Color.WHITE);
                    cell.setFont(new Font("Arial",Font.BOLD,20));
                }else if(column == 2 && row == 0){
                    cell.setBackground(new Color(28, 100, 255));
                    cell.setForeground(Color.WHITE);
                    cell.setFont(new Font("Arial",Font.BOLD,20));
                }else if(column == 3 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(new Color(255,153,0));
                    cell.setFont(new Font("Arial",Font.BOLD,20));
                }else if((column == 4 ||column == 5) && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.GREEN);
                    cell.setFont(new Font("Arial",Font.BOLD,18));
                }else if( column == 6 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,16));
                }else if( column == 7 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,16));
                }else if( column == 8 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,14));
                }else if( column == 9 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,16));
                }else if( column == 10 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,14));
                }else if( column == 11 && row == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,20));
                }else{
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,14));
                }
                return cell;
            }
        });

        //设置行高
        symbolTable.setRowHeight(40);
        //设置表头高度
        TableUtil.setTableHeadHeight(symbolTable,25);

        TableUtil.setTableHeadCF(symbolTable,Color.BLACK,new Color(255, 255, 255),
                new Font("Arial", Font.BOLD+Font.ITALIC, 12));

        TableUtil.setTableHeaderColor(symbolTable,11,Color.WHITE,Color.BLACK,new Font("Arial", Font.BOLD, 16));

        TableUtil.setColWidth(symbolTable,0,70);
        TableUtil.setColWidth(symbolTable,11,104);

        JScrollPane scrollPane = new JScrollPane(symbolTable);
//        symbolTable.setValueAt(Const.TI_MODE,0,11);

        scrollPane.setLocation(4,199);
        scrollPane.setSize(1365, 68);
        return scrollPane;
    }

    public JScrollPane createDataTable(){
        Object[][] rowData = new Object[Const.PREFERENCES_DATA.getAbrDay()][8];

        String[] colName = {"Contract","Date","Open","High","Low","Close","Volume","ABR"};
        dataTable = new JTable(rowData, colName);

        //设置行高
        dataTable.setRowHeight(22);
        //设置表头高度
        TableUtil.setTableHeadHeight(dataTable,30);
        TableUtil.setColWidth(dataTable,0,90);
        TableUtil.setColWidth(dataTable,1,180);
        TableUtil.setColWidth(dataTable,6,180);

        //设置表头颜色，字体
        TableUtil.setTableHeadCF(dataTable,Color.BLACK,Color.WHITE,
                new Font("Arial", Font.BOLD, 14));

        TableUtil.setTableHeaderColor(dataTable,7,Color.WHITE,Color.BLACK,new Font("Arial", Font.BOLD, 16));

        TableUtil.setTableTextCenter(dataTable);
        dataTable.setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(dataTable);

        scrollPane.setLocation(4,10);
        Dimension dimension = new Dimension(1000,100);
        scrollPane.setSize(1000,380);
        JScrollBar bar = scrollPane.getVerticalScrollBar();
//        int aa = bar.getMaximum();
//        bar.setValue(50000);
        int rowcount = dataTable.getModel().getRowCount();
        dataTable.getSelectionModel().setSelectionInterval(rowcount - 1, rowcount - 1);
        Rectangle rect=dataTable.getCellRect(rowcount - 1, 0, true);
        dataTable.updateUI();
        dataTable.scrollRectToVisible(rect);
        return scrollPane;
    }


    public JScrollPane createEntryTable(){
        String[] cName = {"","Entry","Stoploss","Trade Side"};

        CMap m = new CMap1();
        DefaultTableModel tm = new DefaultTableModel( cName, 2 ){
            @Override
            public boolean isCellEditable( int indexRow, int indexColumn )
            {
                return true;
            }
        };
        tm.setValueAt( "ACTION", 0, 0);
//        tm.setValueAt( "1.00", 0, 8);
//        tm.setValueAt( "1.00", 1, 8);
        entryTable =  new CTable(m, tm);

        entryTable.setFont(new Font("Arial Black", Font.BOLD, 12));
        //设置行高
        entryTable.setRowHeight(35);


        //设置表头高度
        TableUtil.setTableHeadHeight(entryTable,25);

        TableUtil.setTableHeadCF(entryTable,Color.BLACK,new Color(255, 255, 255),
                new Font("Arial", Font.BOLD+Font.ITALIC, 12));

        TableUtil.setColWidth(entryTable,0,70);
        TableUtil.setColWidth(entryTable,1,238);
        TableUtil.setColWidth(entryTable,2,238);
        TableUtil.setTableTextCenter(entryTable);

        //设置单元格颜色
        PublicMethod.setEntryTableStyle(entryTable,null);
        TableUtil.setTableHeaderColor(entryTable,3,Color.WHITE,Color.BLACK,new Font("Arial", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(entryTable);

        scrollPane.setLocation(4,266);
        scrollPane.setSize(668, 98);
        return scrollPane;
    }

    public JScrollPane createModeTable(){
        Object[][] rowData = {
                {"","",""},
                {"","",""}
        };

        String[] colName = {"Mode 2 Target","Mode 2 Stop","Mode 3 Target"};
        modeTable = new JTable(rowData, colName);

        //设置行高
        modeTable.setRowHeight(35);
        //设置表头高度
        TableUtil.setTableHeadHeight(modeTable,25);

        TableUtil.setTableTextCenter(modeTable);

        TableUtil.setTableHeadCF(modeTable,new Color(150, 150, 150),new Color(255, 255, 255),
                new Font("Arial", Font.BOLD+Font.ITALIC, 12));

        TableUtil.setTableTextCenter(modeTable);
        modeTable.setFont(new Font("Arial", Font.BOLD, 13));

        modeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                dtcr.setHorizontalAlignment(JLabel.CENTER);
                Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.GRAY);
                cell.setFont(new Font("Arial", Font.PLAIN, 16));
                return cell;
            }
        });


        TableUtil.setColWidth(modeTable,0,118);
        TableUtil.setColWidth(modeTable,1,118);
        JScrollPane scrollPane = new JScrollPane(modeTable);

        scrollPane.setLocation(1025,266);
        scrollPane.setSize(344,98);
        return scrollPane;

    }

    public JTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(JTable dataTable) {
        this.dataTable = dataTable;
    }

    public JTable getNominalTable() {
        return nominalTable;
    }

    public void setNominalTable(JTable nominalTable) {
        this.nominalTable = nominalTable;
    }

    public JTable getDayMaxTable() {
        return dayMaxTable;
    }

    public void setDayMaxTable(JTable dayMaxTable) {
        this.dayMaxTable = dayMaxTable;
    }

    public JTable getEntryTable() {
        return entryTable;
    }

    public void setEntryTable(JTable entryTable) {
        this.entryTable = entryTable;
    }

    public JTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(JTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public JTable getMarketTable() {
        return marketTable;
    }

    public void setMarketTable(JTable marketTable) {
        this.marketTable = marketTable;
    }

    public JTable getAdjustmentTable() {
        return adjustmentTable;
    }

    public void setAdjustmentTable(JTable adjustmentTable) {
        this.adjustmentTable = adjustmentTable;
    }

    public JTable getModeTable() {
        return modeTable;
    }

    public void setModeTable(JTable modeTable) {
        this.modeTable = modeTable;
    }

    public JTable getCurrentPriceTable() {
        return currentPriceTable;
    }

    public void setCurrentPriceTable(JTable currentPriceTable) {
        this.currentPriceTable = currentPriceTable;
    }
}
