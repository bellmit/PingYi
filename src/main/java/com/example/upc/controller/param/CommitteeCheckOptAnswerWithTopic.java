package com.example.upc.controller.param;

import com.example.upc.dataobject.CommitteeCheckOptAnswer;

/**
 * @author 董志涵
 */
public class CommitteeCheckOptAnswerWithTopic extends CommitteeCheckOptAnswer {
    private String optTopic;

    public String getOptTopic() {
        return optTopic;
    }

    public void setOptTopic(String optTopic) {
        this.optTopic = optTopic;
    }
}
