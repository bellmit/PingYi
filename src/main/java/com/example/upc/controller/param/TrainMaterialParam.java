package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamTrainMaterial;
import com.example.upc.dataobject.ExamTrainMaterialWithBLOBs;

/**
 * @author zcc
 * @date 2019/5/13 21:53
 */
public class TrainMaterialParam extends ExamTrainMaterialWithBLOBs {
       private String industryName;
       private String workTypeName;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
}
