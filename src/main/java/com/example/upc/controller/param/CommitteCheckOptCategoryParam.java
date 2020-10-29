package com.example.upc.controller.param;

import com.example.upc.dataobject.CommitteCheckOptCategory;
import com.example.upc.dataobject.CommitteeCheckOpt;
import com.example.upc.dataobject.CommitteeCheckOptAnswer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author 董志涵
 */
@Data
@Getter
@Setter
public class CommitteCheckOptCategoryParam extends CommitteCheckOptCategory {
    private List<CommitteeCheckOptAnswerWithTopic> committeeCheckOptAnswerList;

    public List<CommitteeCheckOptAnswerWithTopic> getCommitteeCheckOptAnswerList(){
        return committeeCheckOptAnswerList;
    }

    public void setCommitteeCheckOptAnswerList(List<CommitteeCheckOptAnswerWithTopic> committeeCheckOptAnswerList){
        this.committeeCheckOptAnswerList = committeeCheckOptAnswerList;
    }
}
