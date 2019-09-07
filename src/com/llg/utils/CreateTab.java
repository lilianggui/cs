package com.llg.utils;

import com.ib.client.Types;
import com.llg.entity.HistoricalData;
import com.llg.main.Const;
import com.llg.main.DailyHistoricalData;
import com.llg.main.HistoryData;
import com.llg.main.Main;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateTab {
    private Common cm;
    private CreateTableFac ctf;
    private CreatePreferenceP cpp;
    private CreateButtonFac cbf;


    public CreateTab(){
        this.ctf = new CreateTableFac();
        this.cm = new Common();
        this.cpp = new CreatePreferenceP();
        this.cbf = new CreateButtonFac();
    }


    public JPanel createTradeManager(){
        JScrollPane marketTable = ctf.createMarketTable();
        JScrollPane nominalTable = ctf.createNominalTable();
        JScrollPane adjustmentTable = ctf.createAdjustmentTable();
        HtmlButton equityButton = cbf.getDayEndEquityButton();
        JScrollPane dayMaxTable = ctf.createDayMaxTable();
        JScrollPane createCurrentPriceTable = ctf.createCurrentPriceTable();
        JScrollPane symbolTable = ctf.createSymbolTable();
        JScrollPane entryTable = ctf.createEntryTable();
        JScrollPane modeTable = ctf.createModeTable();
        JPanel tradeManager = new JPanel();
        tradeManager.setLayout(null);
        tradeManager.add(marketTable);
        tradeManager.add(nominalTable);
        tradeManager.add(adjustmentTable);
        tradeManager.add(equityButton);
        tradeManager.add(dayMaxTable);
        tradeManager.add(createCurrentPriceTable);
        tradeManager.add(symbolTable);
        tradeManager.add(entryTable);
        tradeManager.add(cm.getOrderPanelPanel());//发单
        tradeManager.add(cm.getSignalPanel());//信号灯
        tradeManager.add(cm.getConnectPanel());//连接按钮
        tradeManager.add(modeTable);

        return tradeManager;
    }

    public JPanel createPreference(){
        JPanel preferencePanel = new JPanel();
        preferencePanel.setLayout(null);

        preferencePanel.add(cpp.createLabelTextFieldPanel(10,"Account"
                ,Const.PREFERENCES_DATA.getAccount()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(35,"Contract"
                ,Const.PREFERENCES_DATA.getContract()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(60,"Currency"
                ,Const.PREFERENCES_DATA.getCurrency()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(90,"Trend Identifier"
                ,Const.PREFERENCES_DATA.getTrendIdentifier().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(115,"Stoploss"
                ,Const.PREFERENCES_DATA.getStoploss().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(140,"Trail-Stop"
                ,Const.PREFERENCES_DATA.getTrailStop().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(165,"ABR Day"
                ,Const.PREFERENCES_DATA.getAbrDay().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(195,"Product Tick Size"
                ,Const.PREFERENCES_DATA.getProductTickSize().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(220,"Slippage (Tick)"
                ,Const.PREFERENCES_DATA.getSlippage().toString()));

        preferencePanel.add(cpp.createLabelTextFieldPanel(250,"Market Open Time"
                ,Const.PREFERENCES_DATA.getMarketOpenTime()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(275,"Market Close Time"
                ,Const.PREFERENCES_DATA.getMarketCloseTime()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(300,"1st Pre-Entry Placement Time"
                ,Const.PREFERENCES_DATA.getOneStPreEntryPlacementTime()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(325,"Entry Cut-off Time"
                ,Const.PREFERENCES_DATA.getEntryCutOffTime()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(350,"Position Exit Average (Minute)"
                ,Const.PREFERENCES_DATA.getPositionExitAverage().toString()));
        preferencePanel.add(cpp.createLabelTextFieldPanel(375,"Last Position Exit (Second)"
                ,Const.PREFERENCES_DATA.getLastPositionExit()));
        return preferencePanel;
    }

    public JPanel createDataTab(){
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(null);
        dataPanel.add(this.ctf.createDataTable());

        HtmlButton requestBtn = new HtmlButton( "Request Daily Data") {
            @Override protected void actionPerformed() {
                Main main = Main.getInstance();
                Object symbol =  main.getSymbolTable().getModel().getValueAt(0,0);
                if(symbol == null || symbol.toString().equals(""))return;
                DailyHistoricalData dh = new DailyHistoricalData();
                main.todayDataInit(main.m_contract);
                String closeTime = Const.PREFERENCES_DATA.getMarketCloseTime();
                String[] hourMinuteSecond = closeTime.split(":");
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hourMinuteSecond[0]));
                c.set(Calendar.MINUTE,Integer.parseInt(hourMinuteSecond[1]));
                c.set(Calendar.SECOND,Integer.parseInt(hourMinuteSecond[2]) + 5);
                String endDate = Const.SF3.format(c.getTime());
                main.controller().reqHistoricalData(main.m_contract, endDate, 1, Types.DurationUnit.DAY, Types.BarSize._1_day,
                        Types.WhatToShow.TRADES, true, dh);
            }
        };

        requestBtn.setBounds(1090,200,200,22);
        requestBtn.setFont(new Font("Arial",Font.BOLD,20));

        dataPanel.add(requestBtn);
        return dataPanel;
    }



    public Common getCm() {
        return cm;
    }

    public void setCm(Common cm) {
        this.cm = cm;
    }

    public CreateTableFac getCtf() {
        return ctf;
    }

    public void setCtf(CreateTableFac ctf) {
        this.ctf = ctf;
    }

    public CreatePreferenceP getCpp() {
        return cpp;
    }

    public void setCpp(CreatePreferenceP cpp) {
        this.cpp = cpp;
    }
}
