package com.llg.main;

import com.ib.client.*;
import com.ib.controller.AccountSummaryTag;
import com.ib.controller.ApiConnection;
import com.ib.controller.ApiController;
import com.llg.utils.CreateTab;
import com.llg.utils.FileUtils;
import com.llg.utils.OvalPanel;
import com.llg.utils.PublicMethod;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main implements ApiController.IConnectionHandler{
    private static Main main ;
    public static String appPath = "";

    public final Contract m_contract = new Contract();
    public final Contract high_low_contract = new Contract();
    public ApiController m_controller;
    private final Logger m_inLogger = new Logger();
    private final Logger m_outLogger = new Logger();

    private JTable adjustmentTable;
    private JTable marketTable;
    private JTable dataTable;
    private JTable nominalTable;
    private JTable modelTable;
    private JTable dayMaxTable;
    private JTable currentPriceTable;
    private JTable entryTable;
    private JTable symbolTable;
    private OvalPanel quoteOval;
    private JLabel timeLabel;
    private CreateTab ct = new CreateTab();

    private Main() {

    }
    private Main(String appPath) {
        JFrame f = new JFrame();
        //设置选项卡在坐标
        JTabbedPane  jp = new JTabbedPane(JTabbedPane.TOP) ;

        setPreferencesData();

        jp.add("Trade Manager", ct.createTradeManager())  ;
        jp.add("Preferences", ct.createPreference())  ;
        jp.add("Data", ct.createDataTab());

        this.marketTable = ct.getCtf().getMarketTable();
        this.dataTable = ct.getCtf().getDataTable();
        this.nominalTable = ct.getCtf().getNominalTable();
        this.dayMaxTable = ct.getCtf().getDayMaxTable();
        this.currentPriceTable = ct.getCtf().getCurrentPriceTable();
        this.quoteOval = ct.getCm().getQuoteOval();
        this.entryTable = ct.getCtf().getEntryTable();
        this.symbolTable = ct.getCtf().getSymbolTable();
        this.adjustmentTable = ct.getCtf().getAdjustmentTable();
        this.timeLabel = ct.getCm().getTimeLabel();
        this.modelTable = ct.getCtf().getModeTable();

        this.controller();

        //将选项卡窗体添加到 主窗体上去
        f.add(jp, BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();      //得到屏幕的尺寸
        int width = screenSize.width; //宽度
        int height = screenSize.height; //高度
        int csWidth = 1392;
        int csHeight = 475;
        int csLocationX =  0;
        int csLocationY = height - csHeight - 40;

        f.setLocation(csLocationX,csLocationY);
        f.setSize(csWidth,csHeight);
        f.setTitle(Const.APP_TITLE);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });



    }

    public static void main(String[] args) {
        appPath = args[0];
        Main.main = new Main(args[0]);
        Main mainCS = getInstance();
        ApiConnection client = mainCS.controller().client();

        mainCS.setMarketDefaultData();//获取主页默认数据
        mainCS.setHistoricalData();//读取历史数据
        mainCS.setTradeDetailsData();
        PublicMethod.countAccountData();

        //获取昨天收盘价
        new Thread(new GetYesterdayClosePrice(mainCS)).start();
        //获取服务器时间
        new Thread(new GetCurrentTime()).start();
        //获取现价
        new Thread(new GetCurrentPrice(mainCS)).start();
        //获取今天最高最低
        new Thread(new GetHighLowPrice(mainCS)).start();
        //检查连接，连接成功时信号灯变绿，失去连接时信号灯变红
        new Thread(new CheckConnect(mainCS)).start();


    }


    private void setTradeDetailsData(){
        String filePath = appPath + "\\TradeDetails.txt";
        String firstLine = FileUtils.readFirstLine(filePath).replaceAll("\\s+","");
        this.getSymbolTable().setValueAt(Const.TI_MODE,0,11);
        if(firstLine != null){
            String nowDate = Const.SF2.format(new Date());
            if(firstLine.contains(nowDate)){
                //如果日期是当天，读取
                List<String> lines = FileUtils.getFileLine(filePath,null);
                PublicMethod.setTradeDetailsData(lines);
            }else{
                //否则重写该文件
                PublicMethod.resetTradeDetailsData();
            }
        }
        symbolTable.setValueAt(Const.TI_MODE,0,11);
    }

    private void setHistoricalData(){
        String filePath = appPath + "\\Data.txt";
        List<String> lines = FileUtils.getFileLine(filePath,Const.PREFERENCES_DATA.getAbrDay());
        PublicMethod.setHistoricalData(lines);
        PublicMethod.setHistoricalTableData(Const.HISTORICAL_DATA);
    }

    private void setMarketDefaultData(){
        String filePath = appPath + "\\TradeManager.txt";
        List<String> lines = FileUtils.getFileLine(filePath,null);
        PublicMethod.setMarDefaultMarketData(lines);
        PublicMethod.setMarketDefaultTableData(Const.TRADEMANAGERDATA);
    }

    private void setPreferencesData(){
        String filePath = appPath + "\\Preferences.txt";
        List<String> lines = FileUtils.getFileLine(filePath,null);
        PublicMethod.setPreferencesData(lines);
        //PublicMethod.setPreferencesTableData(Const.PREFERENCE_DATA);
    }

    private String getAppPath(){
        String appPath = System.getProperty("appPath");
        return appPath;
    }


    public void getAccountSummaryData(){
        ApiController ac = this.controller();
        //获取现在总资金
        AccountSummaryTag[] ast = {AccountSummaryTag.NetLiquidation};
        ac.reqAccountSummary( "All", ast, new AccountData(this.getNominalTable()));
    }

    public void getYesterdayClosePrice(){
        ApiController ac = this.controller();
        if(Const.PREVIOUS_CLOSE == 0){
            HistoryData historicalData = new HistoryData();
            Main m = Main.getInstance();
            m.historicalDataInit(m.m_contract);
            String closeTime = Const.PREFERENCES_DATA.getMarketCloseTime();
            String[] hourMinuteSecond = closeTime.split(":");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR,-1);//昨天数据
            c.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hourMinuteSecond[0]));
            c.set(Calendar.MINUTE,Integer.parseInt(hourMinuteSecond[1]));
            c.set(Calendar.SECOND,Integer.parseInt(hourMinuteSecond[2]) + 5);
            String endDate = Const.SF3.format(c.getTime());
            ac.reqHistoricalData(m.m_contract, endDate, 1, Types.DurationUnit.DAY, Types.BarSize._1_day,
                    Types.WhatToShow.TRADES, true, historicalData);
        }
    }

    public void getHighLowPrice(){
        ApiController ac = this.controller();
        HighLowData highLowData = new HighLowData();
        Main m = Main.getInstance();
        m.historicalDataInit(m.high_low_contract);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR,+1);//截止时间为当前系统日期+1天凌晨00:00:00
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        String endDate = Const.SF3.format(c.getTime());
        ac.reqHistoricalData(m.m_contract, endDate, 1, Types.DurationUnit.DAY, Types.BarSize._1_day,
                Types.WhatToShow.TRADES, true, highLowData);
    }


    public void getCurrentData(){
        ApiController ac = this.controller();
        //获取现价
        this.currentDataInit(this.m_contract);
        ac.reqTopMktData(this.m_contract, "", false, new MarketData(this.getEntryTable()));
    }


    public synchronized ApiController controller() {
        if ( m_controller == null ) {
            m_controller = new ApiController( this, getM_inLogger(), getM_outLogger());
        }
        return m_controller;
    }

    private void currentDataInit(Contract m_contract){
        m_contract.symbol( "NQ" );
        m_contract.secType( "FUT" );
        m_contract.lastTradeDateOrContractMonth(Main.getInstance().getSymbolTable().getValueAt(0,0).toString());
        m_contract.strike(0 );
        m_contract.right( "None" );
        m_contract.multiplier( "" );
        m_contract.exchange( "GLOBEX");
        m_contract.primaryExch( "");
        m_contract.currency( "" );
        m_contract.localSymbol( "" );
        m_contract.tradingClass( "" );
    }


    public void todayDataInit(Contract m_contract){
        this.historicalDataInit(m_contract);
    }

    public void historicalDataInit(Contract m_contract){
        m_contract.symbol( "NQ" );
        m_contract.secType( "FUT" );
        m_contract.lastTradeDateOrContractMonth(Main.getInstance().getSymbolTable().getValueAt(0,0).toString());
        m_contract.strike(0 );
        m_contract.right( "None" );
        m_contract.multiplier( "" );
        m_contract.exchange( "GLOBEX");
        m_contract.primaryExch( "");
        m_contract.currency( "" );
        m_contract.localSymbol( "" );
        m_contract.tradingClass( "" );
    }

    @Override
    public void connected() {

    }

    @Override
    public void disconnected() {

    }

    @Override
    public void accountList(ArrayList<String> arrayList) {

    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public void message(int i, int i1, String s) {

    }

    @Override
    public void show(String s) {

    }

    public Logger getM_inLogger() {
        return m_inLogger;
    }

    public Logger getM_outLogger() {
        return m_outLogger;
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

    public OvalPanel getQuoteOval() {
        return quoteOval;
    }

    public void setQuoteOval(OvalPanel quoteOval) {
        this.quoteOval = quoteOval;
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

    public CreateTab getCt() {
        return ct;
    }

    public void setCt(CreateTab ct) {
        this.ct = ct;
    }

    public JTable getAdjustmentTable() {
        return adjustmentTable;
    }

    public void setAdjustmentTable(JTable adjustmentTable) {
        this.adjustmentTable = adjustmentTable;
    }

    public static Main getInstance(){
        return Main.main;
    }


    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public JTable getModelTable() {
        return modelTable;
    }

    public void setModelTable(JTable modelTable) {
        this.modelTable = modelTable;
    }

    public JTable getCurrentPriceTable() {
        return currentPriceTable;
    }

    public void setCurrentPriceTable(JTable currentPriceTable) {
        this.currentPriceTable = currentPriceTable;
    }
}
