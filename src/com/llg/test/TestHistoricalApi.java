package com.llg.test;

import com.ib.client.Contract;
import com.ib.client.Types;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiConnection;
import com.ib.controller.ApiController;
import com.ib.controller.Bar;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TestHistoricalApi implements ApiController.IConnectionHandler {
    private final Contract m_contract = new Contract();
    private ApiController m_controller;
    private final Logger m_inLogger = new Logger();
    private final Logger m_outLogger = new Logger();

    public ApiConnection.ILogger getInLogger()            { return m_inLogger; }
    public ApiConnection.ILogger getOutLogger()           { return m_outLogger; }

    protected void onRealTime() {
        //m_contractPanel.onOK();
        ResultData data = new ResultData();
        m_contract.symbol( "HSI" );
        m_contract.secType( "FUT" );
        m_contract.lastTradeDateOrContractMonth( "" );
        m_contract.strike(0 );
        m_contract.right( "None" );
        m_contract.multiplier( "" );
        m_contract.exchange( "HKFE");
        m_contract.primaryExch( "");
        m_contract.currency( "" );
        m_contract.localSymbol( "HSIK8" );
        m_contract.tradingClass( "" );

        m_controller.reqHistoricalData(m_contract, "", 20, Types.DurationUnit.DAY, Types.BarSize._1_day,
                WhatToShow.TRADES, true, data);

        //System.out.println(data.m_rows);
    }

    public static void main(String argvs[]){
        run();
    }
    public ApiController controller() {
        if ( m_controller == null ) {
            m_controller = new ApiController( this, getInLogger(), getOutLogger());
        }
        return m_controller;
    }



    private static void run(){
        TestHistoricalApi tapi = new TestHistoricalApi();
        ApiController ac = tapi.controller();
        ac.connect("127.0.0.1", 7496, 0,"");
        tapi.onRealTime();
//        ac.disconnect();
    }


    @Override
    public void connected() {
        //连接之后调用该方法
        System.out.println("connect success!!!");
    }

    @Override
    public void disconnected() {
        System.out.println("disConnect!!!");
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


    class ResultData implements ApiController.IHistoricalDataHandler, ApiController.IRealTimeBarHandler {
        public ArrayList<Bar> m_rows = new ArrayList<Bar>();


        @Override
        public void historicalData(Bar bar, boolean b) {
            //System.out.println("add bar");
            System.out.println("HIGH:"+bar.high()+",LOW:"+bar.low());
            m_rows.add(bar);
        }

        @Override
        public void historicalDataEnd() {

        }

        @Override
        public void realtimeBar(Bar bar) {
            m_rows.add(bar);
        }
    }


    private static class Logger implements ApiConnection.ILogger{

        @Override
        public void log(String s) {
            //System.out.println(s);
        }
    }
}
