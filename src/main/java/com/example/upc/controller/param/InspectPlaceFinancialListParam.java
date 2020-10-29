package com.example.upc.controller.param;

import com.example.upc.dataobject.InspectPlaceFinancialList;
import com.example.upc.dataobject.InspectThingList;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/31 22:40
 */
public class InspectPlaceFinancialListParam extends InspectPlaceFinancialList {
   private List<InspectThingList> inspectThingListList;

    public List<InspectThingList> getInspectThingListList() {
        return inspectThingListList;
    }

    public void setInspectThingListList(List<InspectThingList> inspectThingListList) {
        this.inspectThingListList = inspectThingListList;
    }
}
