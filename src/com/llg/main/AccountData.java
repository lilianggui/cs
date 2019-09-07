package com.llg.main;

import com.ib.controller.AccountSummaryTag;
import com.ib.controller.ApiController;
import com.llg.utils.PublicMethod;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Date;

public class AccountData implements ApiController.IAccountSummaryHandler {
    private int accountIndex;
    private JTable nominalTable;


    public AccountData(JTable nominalTable){
        this.accountIndex = 0;
        this.nominalTable = nominalTable;
    }

    @Override
    public void accountSummary(String s, AccountSummaryTag tag, String value, String currency) {
        if(s.equals(Const.PREFERENCES_DATA.getAccount()) && tag.name().equals("NetLiquidation")){
            Integer currentTime = Integer.parseInt(Const.SF7.format(new Date(Const.SERVICE_CURRENT_TIME*1000)));
            Integer openTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketOpenTime().replaceAll(":",""));
            Integer closeTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketCloseTime().replaceAll(":",""));

            if(currentTime < openTime || currentTime > closeTime){
                Const.TRADEMANAGERDATA.setCurrentEquity(Double.parseDouble(value.replaceAll(",","")));
                PublicMethod.countAccountData();
                PublicMethod.resetMarDefaultMarketData();//重写配置文件
            }
        }
    }

    @Override
    public void accountSummaryEnd() {

    }
}
