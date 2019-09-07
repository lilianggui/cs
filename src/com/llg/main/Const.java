package com.llg.main;

import com.llg.entity.HistoricalData;
import com.llg.entity.TradeManagerData;
import com.llg.entity.Preferences;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Const {
    public final static String APP_TITLE = "CS - [ NQ ]";
    public final static SimpleDateFormat SF1 = new SimpleDateFormat("yyyyMM");
    public final static SimpleDateFormat SF4 = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat SF2 = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat SF3 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    public final static SimpleDateFormat SF5 = new SimpleDateFormat("MM-dd");
    public final static SimpleDateFormat SF6 = new SimpleDateFormat("HH:mm:ss");
    public final static SimpleDateFormat SF7 = new SimpleDateFormat("HHmmss");
    public final static String[] SYMBOLS = {"F","G","H","J","K","M","N","Q","U","V","X","Z"};
    public static List<HistoricalData> HISTORICAL_DATA = new ArrayList<>();
    public static TradeManagerData TRADEMANAGERDATA = null;
    public static Preferences PREFERENCES_DATA = null;
    public static Double ABR = null;
    public static Double VOLATILITY_DOLLAR = null;
    public static String contract;
    public static DecimalFormat DECIMAL_FORMAT1 = new DecimalFormat("#0.00");
    public static DecimalFormat DECIMAL_FORMAT2 = new DecimalFormat("#");
    public static DecimalFormat DECIMAL_FORMAT3 = new DecimalFormat("#0.000");
    public static Double PREVIOUS_CLOSE = 0d;
    public static Double HIGHEST_HIGH = 0d;
    public static Double LOWEST_LOW = 0d;
    public static Double CURRENT = 0d;
//    public static Double CURRENT_EQUITY = 0d;
    public static Integer UPT_ROUNDED = 0;



    public static double LongEntry = -1;
    public static double LongStoploss = 0;


    public static Double ShortEntry = -1d;
    public static Double ShortStoploss = 0d;


    public static int TI_MODE = 1;


    public static int IS_LONG_ENTRIED = 0;
    public static int IS_SHORT_ENTRIED = 0;

    /**
     * 服务器当前时间
     */
    public static long SERVICE_CURRENT_TIME;

    public static int LONG_AUTO_EXIT = 0;

    public static int SHORT_AUTO_EXIT = 0;

    public static int IS_SENT_AUTO_EXIT_ORDER = 0;




}
