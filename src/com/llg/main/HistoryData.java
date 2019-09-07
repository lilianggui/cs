package com.llg.main;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;
import com.ib.controller.Formats;
import com.llg.utils.PublicMethod;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 返回的数据
 */
public class HistoryData implements ApiController.IHistoricalDataHandler, ApiController.IRealTimeBarHandler{

    @Override
    public void historicalData(Bar bar, boolean b) {
        Const.PREVIOUS_CLOSE = bar.close();
        Main.getInstance().getDayMaxTable().getModel().setValueAt(Const.DECIMAL_FORMAT1.format(Const.PREVIOUS_CLOSE), 0, 3);
        PublicMethod.countAccountData();
    }

    @Override
    public void historicalDataEnd() {

    }

    @Override
    public void realtimeBar(Bar bar) {

    }
}
