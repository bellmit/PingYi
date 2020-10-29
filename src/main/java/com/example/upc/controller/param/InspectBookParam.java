package com.example.upc.controller.param;

import com.example.upc.dataobject.InspectDailyBook;

/**
 * @author zcc
 * @date 2019/7/7 1:22
 */
public class InspectBookParam {
    private int id;
    private int isPublic;
    private String remark;
    private String bookName;
    private String bookUrl;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }
}
