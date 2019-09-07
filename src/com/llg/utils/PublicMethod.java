package com.llg.utils;

import com.ib.controller.ApiController;
import com.llg.entity.HistoricalData;
import com.llg.entity.TradeManagerData;
import com.llg.entity.Preferences;
import com.llg.main.Const;
import com.llg.main.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PublicMethod {

    public static double ceilQuart(double d){
        double result = Math.ceil(d * 4)/4.0;
        return Double.parseDouble(Const.DECIMAL_FORMAT1.format(result));
    }

    public static double floorQuart(double d){
        double result = Math.floor(d * 4)/4.0;
        return Double.parseDouble(Const.DECIMAL_FORMAT1.format(result));
    }

    public static void countAccountData(){
        Main.getInstance().getNominalTable().getModel().setValueAt("$"+Const.TRADEMANAGERDATA.getCurrentEquity(),2,0);
        Double nominalEquity = Const.TRADEMANAGERDATA.getAdjustment() + Const.TRADEMANAGERDATA.getCurrentEquity();
        Double rPTDollar = nominalEquity * Const.TRADEMANAGERDATA.getRiskPerTrade()/100;
        Double unitPerTradeUPT = rPTDollar / Const.VOLATILITY_DOLLAR;
        int uPTRounded = (int)Math.floor(unitPerTradeUPT);
        Double marginRequirementPerUnit = Const.PREVIOUS_CLOSE * Const.TRADEMANAGERDATA.getDollarPerPoint()
                * (Const.TRADEMANAGERDATA.getLeverageRatio()/100) * uPTRounded;
        Double currentLevel = marginRequirementPerUnit / nominalEquity;

        TableModel tmM = Main.getInstance().getMarketTable().getModel();
        Main.getInstance().getNominalTable().getModel().setValueAt("$"+Const.DECIMAL_FORMAT1.format(nominalEquity),0,0);
        tmM.setValueAt("$"+Const.DECIMAL_FORMAT1.format(rPTDollar),4,1);
        tmM.setValueAt(Const.DECIMAL_FORMAT1.format(unitPerTradeUPT),6,1);
        tmM.setValueAt(uPTRounded,7,1);
        tmM.setValueAt("$"+Const.DECIMAL_FORMAT1.format(marginRequirementPerUnit),3,1);

        Main.getInstance().getDayMaxTable().getModel().setValueAt(Const.DECIMAL_FORMAT1.format(currentLevel*100)+"%",0,2);

        Const.UPT_ROUNDED = uPTRounded;
        Const.TRADEMANAGERDATA.setuPTRounded((double)uPTRounded);
    }

    public static void resetMode(){
        Const.TI_MODE = 1;
//        TableModel tmE = Main.getInstance().getEntryTable().getModel();
//        tmE.setValueAt(null,0,7);
//        tmE.setValueAt(null,0,8);
//        tmE.setValueAt(null,1,7);
//        tmE.setValueAt(null,1,8);

//        Double entry3 = Const.HIGHEST_HIGH - Const.ABR* Const.PREFERENCES_DATA.getTrendIdentifier();
//        Double entry4 = entry3 + Const.ABR * Const.PREFERENCES_DATA.getStoploss();
//        Const.ShortStoploss = (int)Math.ceil(entry4);
//        tmE.setValueAt(Const.ShortStoploss,1,2);
//
//        Double entry1 = Const.ABR * Const.PREFERENCES_DATA.getTrendIdentifier() + Const.LOWEST_LOW;
//        Double entry2 = entry1 - Const.ABR * Const.PREFERENCES_DATA.getStoploss();
//        Const.LongStoploss = (int)Math.floor(entry2);
//        tmE.setValueAt(Const.LongStoploss,0,2);


        PublicMethod.resetTradeDetailsData();
        Main.getInstance().getSymbolTable().setValueAt(Const.TI_MODE,0,11);
    }


    public static void waite(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waiteConnect(ApiController ac){
        while(!ac.client().isConnected()){
            //等待连接
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 动态设置表格某个单元格的颜色
     * @param entryTable
     * @param isDefault null默认，1， 2，
     */
    public static void setEntryTableStyle(JTable entryTable, Integer isDefault){

        entryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                dtcr.setHorizontalAlignment(JLabel.CENTER);
                Component cell = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if( column == 0){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.BOLD,15));
                }else if( column == 1 && row == 0 ){
                    if(new Integer(1).equals(isDefault)){
                        cell.setBackground(Color.YELLOW);
                        cell.setForeground(new Color(28, 100, 255));
                        cell.setFont(new Font("Arial",Font.BOLD,18));
                    }else{
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(new Color(28, 100, 255));
                        cell.setFont(new Font("Arial",Font.BOLD,18));
                    }
                }else if(column == 2 && row == 0 ){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(new Color(28, 100, 255));
                    cell.setFont(new Font("Arial",Font.BOLD,18));
                }else if( column == 1  && row == 1 ){
                    if(new Integer(2).equals(isDefault)){
                        cell.setBackground(Color.YELLOW);
                        cell.setForeground(Color.RED);
                        cell.setFont(new Font("Arial",Font.BOLD,18));
                    }else{
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(Color.RED);
                        cell.setFont(new Font("Arial",Font.BOLD,18));
                    }
                }else if(column == 2 && row == 1 ){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.RED);
                    cell.setFont(new Font("Arial",Font.BOLD,18));
                }else if(column == 3){
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                    cell.setFont(new Font("Arial",Font.PLAIN,16));
                }
                return cell;
            }
        });


    }

    public static void resetTradeDetailsData(){
        List<String> lines = new ArrayList<>();
        lines.add("DATE:"+Const.SF2.format(new Date()));
        lines.add("TI_MODE:" + Const.TI_MODE);
        FileUtils.insertLines(lines,Main.appPath + "\\TradeDetails.txt",false);
    }

    public static void setTradeDetailsData(List<String> lines){
        for(String s : lines){
            String strs[] = s.replaceAll("\\s+","").split(":");
            String str1 = strs[1].replaceAll("\\s+","");
            if(strs[0].equals("TI_MODE")){
                Const.TI_MODE = Integer.parseInt(str1);
            }
        }
    }

    public static int getCloseTime(){
        return getTimeInt(Const.PREFERENCES_DATA.getMarketCloseTime());
    }

    public static int getTimeInt(String time){
        return Integer.parseInt(time.replaceAll(":",""));
    }

    public static int getTimeInt(long time){
        return Integer.parseInt(Const.SF7.format(new Date(time*1000)));
    }

    public static void setHistoricalData(List<String> lines){
        for(String s : lines){
            String strs[] = s.trim().split("\\s+");
            HistoricalData hd = new HistoricalData();
            hd.setContract(strs[0]);
            hd.setDate(strs[1]);
            hd.setOpen(Double.parseDouble(strs[2]));
            hd.setHigh(Double.parseDouble(strs[3]));
            hd.setLow(Double.parseDouble(strs[4]));
            hd.setClose(Double.parseDouble(strs[5]));
            hd.setVolume(Long.parseLong(strs[6]));
            Const.HISTORICAL_DATA.add(hd);

        }
    }

    public static void resetMarDefaultMarketData(){
        TradeManagerData tm = Const.TRADEMANAGERDATA;
        List<String> lines = new ArrayList<>();
        lines.add("Dollar Per Point:$" + tm.getDollarPerPoint());
        lines.add("Risk-Per-Trade (RPT):" + tm.getRiskPerTrade() + "%");
        lines.add("Leverage Ratio:" + tm.getLeverageRatio() + "%");
        lines.add("Current Equity:$" + tm.getCurrentEquity());
        lines.add("Adjustment:$" + tm.getAdjustment());
        lines.add("Start-up Equity:$" + tm.getStartUpEquity());
        lines.add("Day Max Loss RPT:" + tm.getDayMaxLoss());
        lines.add("Max Margin Level:" + tm.getMaxMarginLevel()+"%");
        FileUtils.insertLines(lines,Main.appPath+"\\TradeManager.txt",false);

    }

    public static void setMarDefaultMarketData(List<String> lines){
        DecimalFormat df = new DecimalFormat("######0.00");
        Const.TRADEMANAGERDATA = new TradeManagerData();
        for(String line : lines){
            String strs[] = line.replaceAll("\\s+","").split(":");
            String str1 = strs[1].replaceAll("\\s+","");
            if(strs[0].equals("DollarPerPoint")){
                Const.TRADEMANAGERDATA.setDollarPerPoint(PublicMethod.dollarToDouble(str1));
            }else if(strs[0].equals("Risk-Per-Trade(RPT)")){
                Const.TRADEMANAGERDATA.setRiskPerTrade(PublicMethod.parcentToDouble(str1));
            }else if(strs[0].equals("LeverageRatio")){
                Const.TRADEMANAGERDATA.setLeverageRatio(PublicMethod.parcentToDouble(str1));
            }else if(strs[0].equals("Start-upEquity")){
                Const.TRADEMANAGERDATA.setStartUpEquity(PublicMethod.dollarToDouble(str1));
            }else if(strs[0].equals("Adjustment")){
                Const.TRADEMANAGERDATA.setAdjustment(PublicMethod.dollarToDouble(str1));
            }else if(strs[0].equals("DayMaxLossRPT")){
                Const.TRADEMANAGERDATA.setDayMaxLoss(Double.parseDouble(str1));
            }else if(strs[0].equals("MaxMarginLevel")){
                Const.TRADEMANAGERDATA.setMaxMarginLevel(PublicMethod.parcentToDouble(str1));
            }else if(strs[0].equals("CurrentEquity")){
                Const.TRADEMANAGERDATA.setCurrentEquity(PublicMethod.dollarToDouble(str1));
            }
        }
    }

    public static void setPreferencesData(List<String> lines){
        DecimalFormat df = new DecimalFormat("######0.00");
        Const.PREFERENCES_DATA = new Preferences();
        for(String line : lines){
            String strs[] = line.replaceAll("\\s+","").split(":");
            String str1 = strs[1].replaceAll("\\s+","");
            if(strs[0].equals("Account")){
                Const.PREFERENCES_DATA.setAccount(str1);
            }else if(strs[0].equals("Contract")){
                Const.PREFERENCES_DATA.setContract(str1);
            }else if(strs[0].equals("Currency")){
                Const.PREFERENCES_DATA.setCurrency(str1);
            }else if(strs[0].equals("TrendIdentifier")){
                Const.PREFERENCES_DATA.setTrendIdentifier(Double.parseDouble(str1));
            }else if(strs[0].equals("ABRDay")){
                Const.PREFERENCES_DATA.setAbrDay(Integer.parseInt(str1));
            }else if(strs[0].equals("Trail-Stop")){
                Const.PREFERENCES_DATA.setTrailStop(Double.parseDouble(str1));
            }else if(strs[0].equals("Slippage(Tick)")){
                Const.PREFERENCES_DATA.setSlippage(Double.parseDouble(str1));
            }else if(strs[0].equals("ProductTickSize")){
                Const.PREFERENCES_DATA.setProductTickSize(Double.parseDouble(str1));
            }else if(strs[0].equals("Stoploss")){
                Const.PREFERENCES_DATA.setStoploss(Double.parseDouble(str1));
            }else if(strs[0].equals("MarketOpenTime")){
                String marketOpenTime = line.replaceAll("\\s+","").replaceAll("MarketOpenTime:","");
                Const.PREFERENCES_DATA.setMarketOpenTime(marketOpenTime);
            }else if(strs[0].equals("MarketCloseTime")){
                String marketCloseTime = line.replaceAll("\\s+","").replaceAll("MarketCloseTime:","");
                Const.PREFERENCES_DATA.setMarketCloseTime(marketCloseTime);
            }else if(strs[0].equals("1stPre-EntryPlacementTime")){
                String oneStPreEntryPlacementTime = line.replaceAll("\\s+","").replaceAll("1stPre-EntryPlacementTime:","");
                Const.PREFERENCES_DATA.setOneStPreEntryPlacementTime(oneStPreEntryPlacementTime);
            }else if(strs[0].equals("EntryCut-offTime")){
                String entryCutOffTime = line.replaceAll("\\s+","").replaceAll("EntryCut-offTime:","");
                Const.PREFERENCES_DATA.setEntryCutOffTime(entryCutOffTime);
            }else if(strs[0].equals("PositionExitAverage(Minute)")){
                String positionExitAverage = line.replaceAll("\\s+","").replaceAll("PositionExitAverage\\(Minute\\):","");
                Const.PREFERENCES_DATA.setPositionExitAverage(Integer.parseInt(positionExitAverage));
            }else if(strs[0].equals("LastPositionExit(Second)")){
                String LastPositionExit = line.replaceAll("\\s+","").replaceAll("LastPositionExit\\(Second\\):","");
                Const.PREFERENCES_DATA.setLastPositionExit(LastPositionExit);
            }
        }
    }

    public static Double parcentToDouble(String percent){
        return Double.parseDouble(percent.replaceAll("%",""));

    }

    public static Double dollarToDouble(String dollar){
        return Double.parseDouble(dollar.replaceAll("\\$",""));

    }

    public static void setHistoricalTableData(List<HistoricalData> hds){
        Main main = Main.getInstance();
        TableModel tm = main.getDataTable().getModel();
        double sumVol = 0;
        DecimalFormat df = new DecimalFormat("######0");
        DecimalFormat df1 = new DecimalFormat("######0.00");
        for(int i = 0;i<hds.size();i++){
            HistoricalData hd = hds.get(i);
            tm.setValueAt(hd.getContract(),i,0);
            tm.setValueAt(hd.getDate(),i,1);
            tm.setValueAt(df1.format(hd.getOpen()),i,2);
            tm.setValueAt(df1.format(hd.getHigh()),i,3);
            tm.setValueAt(df1.format(hd.getLow()),i,4);
            tm.setValueAt(df1.format(hd.getClose()),i,5);
            tm.setValueAt(df.format(hd.getVolume()),i,6);
            sumVol += hd.getHigh() - hd.getLow();
        }
        BigDecimal sumD = new BigDecimal(sumVol);
        double abrVol = sumD.divide(new BigDecimal(hds.size()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
        //20平均波动
        Const.ABR = abrVol;
        TableModel tmS = main.getSymbolTable().getModel();
        tmS.setValueAt(df1.format(abrVol),0,6);
        tmS.setValueAt(df1.format(abrVol*Const.PREFERENCES_DATA.getTrendIdentifier()),0,8);
        tmS.setValueAt(df1.format(abrVol*Const.PREFERENCES_DATA.getStoploss()),0,10);
        tm.setValueAt(df1.format(abrVol),hds.size()-1,7);

        double volatilityDollar = Const.TRADEMANAGERDATA.getDollarPerPoint() * Const.PREFERENCES_DATA.getStoploss() * abrVol;
        TableModel tmMar = main.getMarketTable().getModel();
        tmMar.setValueAt("$"+df1.format(volatilityDollar),5,1);
        Const.VOLATILITY_DOLLAR = volatilityDollar;

    }

    public static void setMarketDefaultTableData(TradeManagerData md){
        Main main = Main.getInstance();
        TableModel tm = main.getMarketTable().getModel();
        tm.setValueAt("$"+Const.DECIMAL_FORMAT1.format(md.getDollarPerPoint()),0,1);
        tm.setValueAt(md.getRiskPerTrade().toString()+"%",1,1);
        tm.setValueAt(md.getLeverageRatio().toString()+"%",2,1);

        TableModel tmN = main.getNominalTable().getModel();
        tmN.setValueAt("$"+Const.DECIMAL_FORMAT1.format(md.getStartUpEquity()),4,0);

        TableModel tmAd = main.getAdjustmentTable().getModel();
        tmAd.setValueAt("$"+Const.DECIMAL_FORMAT1.format(md.getAdjustment()),0,0);

        TableModel tmDM = main.getDayMaxTable().getModel();
        tmDM.setValueAt(md.getDayMaxLoss(),0,0);
        tmDM.setValueAt(md.getMaxMarginLevel().toString()+"%",0,1);

        TableModel tmSy = main.getSymbolTable().getModel();
        tmSy.setValueAt(Const.DECIMAL_FORMAT3.format(Const.PREFERENCES_DATA.getStoploss()),0,9);


    }


    public static List<String> HistoricalDataToLine(List<HistoricalData> hds){
        List<String> lines = new ArrayList<>();
        for(HistoricalData hd : hds){
            String line = hd.getContract()+"\t"+hd.getDate()+"\t"+hd.getOpen()
                    +"\t\t"+hd.getHigh()+"\t\t"+hd.getLow()+"\t\t"+hd.getClose()+"\t\t"+hd.getVolume();
            lines.add(line);
        }
        return lines;
    }
}
