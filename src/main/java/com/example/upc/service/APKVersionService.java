package com.example.upc.service;

import com.example.upc.dataobject.APKVersion;

public interface APKVersionService {
    APKVersion selectTopOne();
    void insert(APKVersion apkVersion);
}
