package com.example.upc.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Billdao extends BillReport{
    private String recordList;

    public String getRecordList() {
        return recordList;
    }

    public void setRecordList(String recordList) {
        this.recordList = recordList;
    }
}
