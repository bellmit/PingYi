package com.example.upc.util;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public  class DatabaseUtil {
    private Statement st;
    private ResultSet rs;
    private Connection conn = null;
    private String dbURL = "";

    public Connection getConnection() {
        try {
            String driverName = "";
            if ((this.conn == null) || (this.conn.isClosed()))
                try {
                    driverName = "com.mysql.jdbc.Driver";
                    dbURL ="jdbc:mysql://" + "106.15.181.173" +  ":" + "3306"+ "/" + "splatform";
                    Driver driver = (Driver)Class.forName(driverName).newInstance();
                    DriverManager.registerDriver(driver);
                    this.conn = DriverManager.getConnection(this.dbURL, "root", "Zcc..934947091");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return this.conn;
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }return null;
    }

    public ResultSet executeQuery(String sql) {
        try {
            this.st = getConnection().createStatement();
            this.rs = this.st.executeQuery(sql);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.rs;
    }

    public void executeUpdate(String sql)
    {
        try {
            this.st = getConnection().createStatement();
            this.st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            sqlclose();
        }
    }

    public void sqlclose() {
        try { if (this.rs != null) this.rs.close();
            if (this.st != null) this.st.close();
            if (this.conn != null) this.conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String[] bgColor() {
        String[] s = { "#eeeeff", "#ddeedd" };
        return s;
    }

    public static String ISOtoGBK(String s) {
        try {
            if (s != null)
                s = new String(s.getBytes("ISO8859_1"), "GB2312");
            else s = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    public static String GBKtoISO(String s) {
        try {
            s = new String(s.getBytes("GB2312"), "ISO8859_1"); } catch (Exception e) {
            e.printStackTrace();
        }return s;
    }

    public static String firstUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
    }

    public String typeConvert(int i)
    {
        String s;
        switch (i) { case 12:
            s = "String"; break;
            case -1:
                s = "String"; break;
            case 4:
                s = "float"; break;
            case 93:
                s = "String"; break;
            default:
                s = "String";
        }
        return s;
    }

    public Class typeConvertObject(int i) {
        Class c = null;
        try {
            switch (i) {
                case 12:
                    c = Class.forName("java.lang.String");
                    break;
                case -1:
                    c = Class.forName("java.lang.String");
                    break;
                case 4:
                    c = Float.TYPE;
                    break;
                case 93:
                    c = Class.forName("java.lang.String");
                    break;
                default:
                    c = Class.forName("java.lang.String");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public Object myCase(String type, String value) {
        Object o = value;
        if (type.equals("int")) o = Integer.valueOf(value);
        if (type.equals("Date")) o = Date.valueOf(value);
        if (type.equals("float")) o = Float.valueOf(value);
        return o;
    }
}
