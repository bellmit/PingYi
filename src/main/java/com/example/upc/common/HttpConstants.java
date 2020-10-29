package com.example.upc.common;

public abstract class HttpConstants {

    public static final String KEY_HEADER_REQUEST_RANGE = "Range";//$NON-NLS-1$
    public static final String KEY_HEADER_RESPONSE_RANGE = "Content-Range";//$NON-NLS-1$
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";//$NON-NLS-1$
    public static final String KEY_HEADER_COMP_KEYS = "X-HEADER-COMP-KEYS"; // 联合主键

    public static final String HEADER_BANG_PROFILE = "Bang-Profile";//$NON-NLS-1$
    public static final String PROFILE_TEST = "0";//$NON-NLS-1$
    public static final String PROFILE_PRODUCTION = "1";//$NON-NLS-1$

    public static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";//$NON-NLS-1$
    public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";//$NON-NLS-1$
    public static final String CONTENT_TYPE_WORD = "application/vnd.ms-word";//$NON-NLS-1$
    public static final String CONTENT_TYPE_APK = "application/vnd.android.package-archive";//$NON-NLS-1$

    public static final String SESSION_USER = "USER";

    // 为了兼容处理联合主键，根据联合主键加载记录，bizId对应的值是会改变的，但是如果出现更新联合主键的
    // 情况，bizId的值就会发生变化。而在dgrid中要求id必须是唯一且不变的，否则在更新时无法正确定位到记录，
    // 因此引入uuid作为代理主键，解决更新定位的问题。
    public static final String KEY_ID = "dbid";
    public static final String KEY_BIZ_ID = "bizid";
}
