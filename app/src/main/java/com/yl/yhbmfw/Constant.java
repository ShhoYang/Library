package com.yl.yhbmfw;

/**
 * @author HaoShi
 */
public class Constant {

    private static final String BASE_URL_DEBUG = "http://192.168.1.223:8444";
    private static final String BASE_URL_RELEASE = "http://grid.501sh.cn";


    public static String getBaseUrl() {
        if (BuildConfig.API_DEBUG) {
            return BASE_URL_RELEASE;
        }
        return BASE_URL_RELEASE;
    }

    public static final String YU_HANG_CODE = "330110000000";

    //Activity传参用
    public static final String KEY_BEAN = "bean";
    public static final String KEY_BEAN_2 = "bean2";
    public static final String KEY_STRING_1 = "String1";
    public static final String KEY_STRING_2 = "String2";
    public static final String KEY_INT_1 = "int1";
    public static final String KEY_INT_2 = "int2";
    public static final String KEY_BOOLEAN_1 = "boolean1";
    public static final String KEY_RESULT = "result";

    //设置WebView图片的宽度
    public static final String HTML_START = "<html><head_default><style type=\"text/css\"> img{width:100%;height:auto}</style></head_default><body>";
    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
//    public static final String HTML_END = "</body></html>";
}
