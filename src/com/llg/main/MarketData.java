package com.llg.main;

import com.ib.client.TickType;
import com.ib.client.Types;
import com.ib.client.Types.MktDataType;
import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.util.Date;

public class MarketData extends ApiController.TopMktDataAdapter {

    private JTable currentTable;
    private TableModel tmC;
    private TableModel tm = Main.getInstance().getSymbolTable().getModel();
    private JTable tmB = Main.getInstance().getEntryTable();
    private TableModel tmE = tmB.getModel();
    private TableModel modeTM = Main.getInstance().getModelTable().getModel();
    private TableModel currentTM = Main.getInstance().getCurrentPriceTable().getModel();

    public MarketData(JTable currentTable){
        this.currentTable = currentTable;
        this.tmC = currentTable.getModel();
    }

    @Override public void tickPrice(TickType tickType, double price, int canAutoExecute) {

        switch( tickType) {
            case LAST:
                Const.CURRENT = price;
                currentTM.setValueAt(Const.DECIMAL_FORMAT1.format(price),0,0);

                int currentTime = PublicMethod.getTimeInt(Const.SERVICE_CURRENT_TIME);
                int openTime = PublicMethod.getTimeInt(Const.PREFERENCES_DATA.getMarketOpenTime());
                if(currentTime >= openTime +5 && Const.LOWEST_LOW != 0 && Const.HIGHEST_HIGH != 0){
                    if(Const.IS_LONG_ENTRIED == 0 && Const.IS_SHORT_ENTRIED == 0){
                        if(Const.CURRENT >= Const.LongEntry){
                            Const.IS_LONG_ENTRIED = 1;
                            PublicMethod.setEntryTableStyle(tmB,1);
                            tmE.setValueAt("YES",0,3);
                            tmE.setValueAt(null,1,1);
                            tmE.setValueAt(null,1,2);
                            modeTM.setValueAt(null,1,0);
                            modeTM.setValueAt(null,1,1);
                            modeTM.setValueAt(null,1,2);
                        }
                    }

                    if(Const.IS_SHORT_ENTRIED == 0 && Const.IS_LONG_ENTRIED == 0){
                        if(Const.CURRENT <= Const.ShortEntry){
                            Const.IS_SHORT_ENTRIED = 1;
                            PublicMethod.setEntryTableStyle(tmB,2);
                            tmE.setValueAt("YES",1,3);
                            tmE.setValueAt(null,0,1);
                            tmE.setValueAt(null,0,2);
                            modeTM.setValueAt(null,0,0);
                            modeTM.setValueAt(null,0,1);
                            modeTM.setValueAt(null,0,2);
                        }
                    }
                }
                break;
            default: break;
        }
    }




    private void modelToZero(){
        if(Const.IS_LONG_ENTRIED == 1){
            PublicMethod.setEntryTableStyle(tmB,null);
            Const.IS_LONG_ENTRIED = 0;
        }
        if(Const.IS_SHORT_ENTRIED == 1){
            PublicMethod.setEntryTableStyle(tmB,null);
            Const.IS_SHORT_ENTRIED = 0;
        }
        tm.setValueAt(Const.TI_MODE,0,11);
        tm.setValueAt(null,0,4);
        tm.setValueAt(null,0,5);
        tmE.setValueAt(null,0,6);
        tmE.setValueAt(null,1,6);

    }

    @Override public void tickSize( TickType tickType, int size) {

    }

    @Override public void tickString(TickType tickType, String value) {

    }

    @Override public void marketDataType(MktDataType marketDataType) {
    }

}
