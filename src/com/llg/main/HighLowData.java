package com.llg.main;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;
import com.llg.utils.PublicMethod;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 返回的数据
 */
public class HighLowData implements ApiController.IHistoricalDataHandler, ApiController.IRealTimeBarHandler{
    private TableModel tm = Main.getInstance().getSymbolTable().getModel();

    private JTable tmB = Main.getInstance().getEntryTable();
    private TableModel tmE = tmB.getModel();

    private TableModel modeTM = Main.getInstance().getModelTable().getModel();

    @Override
    public void historicalData(Bar bar, boolean b) {
        Const.HIGHEST_HIGH = bar.high();
        Const.LOWEST_LOW = bar.low();
        tm.setValueAt(Const.DECIMAL_FORMAT1.format(Const.HIGHEST_HIGH),0,1);
        countIV();//计算模式

        if(Const.IS_LONG_ENTRIED == 0){
            Double entry3 = Const.HIGHEST_HIGH - Const.ABR* Const.PREFERENCES_DATA.getTrendIdentifier();
            Const.ShortEntry = PublicMethod.floorQuart(entry3);
            tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.ShortEntry),1,1);
            double halfABRValue = (Const.PREFERENCES_DATA.getTrendIdentifier() - Const.PREFERENCES_DATA.getStoploss())/2 * Const.ABR;
            double shortMode1Stop = Const.ShortEntry + PublicMethod.ceilQuart(Const.PREFERENCES_DATA.getStoploss() * Const.ABR);
            double shortMode2Target = PublicMethod.floorQuart(entry3 - halfABRValue);
            double shortMode2Stop = shortMode2Target + PublicMethod.ceilQuart(Const.PREFERENCES_DATA.getStoploss() * Const.ABR);
            double temp = (Const.PREFERENCES_DATA.getTrendIdentifier() - Const.PREFERENCES_DATA.getStoploss())/2;
            double shortMode3Target = Const.HIGHEST_HIGH - (temp + Const.PREFERENCES_DATA.getTrailStop() + Const.PREFERENCES_DATA.getStoploss())*Const.ABR;
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(shortMode2Target),1,0);
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(shortMode2Stop),1,1);
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(PublicMethod.floorQuart(shortMode3Target)),1,2);
            if(Const.TI_MODE == 1){
                Const.ShortStoploss = shortMode1Stop;
                tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.ShortStoploss),1,2);
            }else if(Const.TI_MODE == 2){
                Const.ShortStoploss = shortMode2Stop;
                tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.ShortStoploss),1,2);
            }
        }


        tm.setValueAt(Const.DECIMAL_FORMAT1.format(Const.LOWEST_LOW),0,2);

        if(Const.IS_SHORT_ENTRIED == 0){
            Double entry1 = Const.ABR * Const.PREFERENCES_DATA.getTrendIdentifier() + Const.LOWEST_LOW;
            Const.LongEntry = PublicMethod.ceilQuart(entry1);
            tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.LongEntry),0,1);
            double halfABRValue = (Const.PREFERENCES_DATA.getTrendIdentifier() - Const.PREFERENCES_DATA.getStoploss())/2 * Const.ABR;
            double longMode1Stop = Const.LongEntry - PublicMethod.ceilQuart(Const.PREFERENCES_DATA.getStoploss() * Const.ABR);
            double longMode2Target = PublicMethod.ceilQuart(entry1 + halfABRValue);
            double longMode2Stop = longMode2Target - PublicMethod.ceilQuart(Const.PREFERENCES_DATA.getStoploss() * Const.ABR);
            double temp = (Const.PREFERENCES_DATA.getTrendIdentifier() - Const.PREFERENCES_DATA.getStoploss())/2;
            double longMode3Target = Const.LOWEST_LOW + (temp + Const.PREFERENCES_DATA.getTrailStop() + Const.PREFERENCES_DATA.getStoploss())*Const.ABR;
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(longMode2Target),0,0);
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(longMode2Stop),0,1);
            modeTM.setValueAt(Const.DECIMAL_FORMAT1.format(PublicMethod.ceilQuart(longMode3Target)),0,2);
            if(Const.TI_MODE == 1){
                Const.LongStoploss = longMode1Stop;
                tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.LongStoploss),0,2);
            }else if(Const.TI_MODE == 2){
                Const.LongStoploss = longMode2Stop;
                tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.LongStoploss),0,2);
            }
        }
    }

    private void countIV(){
        if(Const.HIGHEST_HIGH != 0 && Const.LOWEST_LOW != 0){
            //高低价格准备就绪
            double intradayVolatility = new BigDecimal(Const.HIGHEST_HIGH - Const.LOWEST_LOW)
                    .divide(new BigDecimal(Const.ABR),3,BigDecimal.ROUND_HALF_DOWN).doubleValue();
            //保留3位小数，上面的方法如果结果是小于两位小数的话不会保留3位小数，此时用以下放法补0
            tm.setValueAt(Const.DECIMAL_FORMAT3.format(intradayVolatility),0,3);
            double temp = (Const.PREFERENCES_DATA.getTrendIdentifier() - Const.PREFERENCES_DATA.getStoploss())/2;
            if(Const.TI_MODE != 0){
                if(Const.TI_MODE != 3){
                    if(temp + Const.PREFERENCES_DATA.getTrendIdentifier() <= intradayVolatility){
                        tm.setValueAt("ACTION",0,4);
                        if(Const.TI_MODE != 2){
                            Const.TI_MODE = 2;
                            PublicMethod.resetTradeDetailsData();
                            tm.setValueAt(Const.TI_MODE,0,11);
                        }
                    }else{
                        tm.setValueAt(null,0,4);
                    }
                }


                if(temp + Const.PREFERENCES_DATA.getTrailStop() + Const.PREFERENCES_DATA.getStoploss() <= intradayVolatility){
                    tm.setValueAt(null,0,4);
                    tm.setValueAt("ACTION",0,5);
                    if(Const.TI_MODE != 3){
                        Const.TI_MODE = 3;
                        PublicMethod.resetTradeDetailsData();
                        tm.setValueAt(Const.TI_MODE,0,11);
                    }
                    Const.LongStoploss = PublicMethod.floorQuart(Const.HIGHEST_HIGH - Const.PREFERENCES_DATA.getTrailStop() * Const.ABR);
                    Const.ShortStoploss = PublicMethod.ceilQuart(Const.LOWEST_LOW + Const.PREFERENCES_DATA.getTrailStop() * Const.ABR);
                    if(Const.IS_LONG_ENTRIED == 1){
                        tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.LongStoploss),0,2);
                        tmE.setValueAt(null,1,2);
                    }
                    if(Const.IS_SHORT_ENTRIED == 1){
                        tmE.setValueAt(Const.DECIMAL_FORMAT1.format(Const.ShortStoploss),1,2);
                        tmE.setValueAt(null,0,2);
                    }


                }
            }
        }
    }


    @Override
    public void historicalDataEnd() {

    }

    @Override
    public void realtimeBar(Bar bar) {

    }



}
