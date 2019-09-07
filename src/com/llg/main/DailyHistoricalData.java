package com.llg.main;

import com.ib.controller.ApiController;
import com.ib.controller.Bar;
import com.llg.entity.HistoricalData;
import com.llg.utils.FileUtils;
import com.llg.utils.PublicMethod;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyHistoricalData implements ApiController.IHistoricalDataHandler, ApiController.IRealTimeBarHandler {
    @Override
    public void historicalData(Bar bar, boolean b) {

        String filePath = Main.appPath+"\\Data.txt";
        String line = FileUtils.readLastLine(filePath);
        String date = "2018-"+Const.SF5.format(bar.time() * 1000L);
        String contract = Main.getInstance().getSymbolTable().getModel().getValueAt(0, 0).toString();
        String newLine = contract + "\t" + date + "\t" + Const.DECIMAL_FORMAT1.format(bar.open())
                + "\t" + Const.DECIMAL_FORMAT1.format(bar.high()) + "\t" + Const.DECIMAL_FORMAT1.format(bar.low()) + "\t"
                + Const.DECIMAL_FORMAT1.format(bar.close()) + "\t" + new Double(bar.volume()).intValue();
        //如果已经添加了当前返回的历史，每次请求会更新。否则插入新数据
        int size = Const.HISTORICAL_DATA.size();
        HistoricalData hd = barToHistoricalData(bar);
        hd.setDate(date);
        if(line != null && line.contains(date)){
            //替换
//            FileUtils.replaceLastLine(newLine,filePath);
//            Const.HISTORICAL_DATA.set(size-1,hd);
        }else if(line != null) {
            FileUtils.insertLines(newLine, filePath);//插入新的数据
            Const.HISTORICAL_DATA = Const.HISTORICAL_DATA.subList(1,size);
            Const.HISTORICAL_DATA.add(hd);
            PublicMethod.setHistoricalTableData(Const.HISTORICAL_DATA);
        }

    }

    private HistoricalData barToHistoricalData(Bar bar){
        HistoricalData hd = new HistoricalData();
        hd.setContract(Main.getInstance().getSymbolTable().getModel().getValueAt(0, 0).toString());
        hd.setOpen(bar.open());
        hd.setHigh(bar.high());
        hd.setLow(bar.low());
        hd.setClose(bar.close());
        hd.setVolume(bar.volume());
        return hd;
    }

    @Override
    public void historicalDataEnd() {

    }

    @Override
    public void realtimeBar(Bar bar) {

    }
}
