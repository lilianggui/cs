package com.llg.utils;

import com.ib.client.Types;
import com.llg.main.Const;
import com.llg.main.DailyHistoricalData;
import com.llg.main.Main;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class CreateButtonFac {


    public HtmlButton getDayEndEquityButton(){
        HtmlButton dayEndEquityButton = new HtmlButton( "Equity Update") {
            @Override protected void actionPerformed() {
                Integer currentTime = Integer.parseInt(Const.SF7.format(new Date(Const.SERVICE_CURRENT_TIME*1000)));
                Integer openTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketOpenTime().replaceAll(":",""));
                Integer closeTime = Integer.parseInt(Const.PREFERENCES_DATA.getMarketCloseTime().replaceAll(":",""));

                if(currentTime < openTime || currentTime > closeTime){
                    Main main = Main.getInstance();
                    main.getAccountSummaryData();
                }

            }
        };

        dayEndEquityButton.setBounds(1278,70,80,24);
        dayEndEquityButton.setFont(new Font("Arial",Font.BOLD,12));

        return dayEndEquityButton;
    }


}
