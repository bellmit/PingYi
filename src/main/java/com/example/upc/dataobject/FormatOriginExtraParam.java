package com.example.upc.dataobject;

import java.util.List;

public class FormatOriginExtraParam {
    private Integer listId;
    private List<FormatOriginExtra> formatOriginExtraList;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public List<FormatOriginExtra> getFormatOriginExtraList() {
        return formatOriginExtraList;
    }

    public void setFormatOriginExtraList(List<FormatOriginExtra> formatOriginExtraList) {
        this.formatOriginExtraList = formatOriginExtraList;
    }
}
