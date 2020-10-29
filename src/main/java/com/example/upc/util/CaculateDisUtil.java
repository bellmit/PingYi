package com.example.upc.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

public class CaculateDisUtil {
    public double Distance(Double gpsA, Double gpsB, Double gpsC, Double gpsD)
    {
        GlobalCoordinates source = new GlobalCoordinates(gpsA, gpsB);
        GlobalCoordinates target = new GlobalCoordinates(gpsC, gpsD);

        return getDistanceMeter(source, target, Ellipsoid.Sphere);
        //System.out.println("Sphere坐标系计算结果："+meter1 + "米");
    }

    public double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
