package com.example.upc.controller.param;

import com.example.upc.dataobject.MiniFoodSamples;
import com.example.upc.dataobject.MiniFoodSamplesItem;

import java.util.List;

public class FoodSapmlesResult extends MiniFoodSamples {
    private List<MiniFoodSamplesItem> items;

    public List<MiniFoodSamplesItem> getItems() {
        return items;
    }

    public void setItems(List<MiniFoodSamplesItem> items) {
        this.items = items;
    }
}
