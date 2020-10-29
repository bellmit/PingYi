package com.example.upc.service;

import com.example.upc.controller.searchParam.EnterpriseVoteSearchParam;
import com.example.upc.dataobject.UserEnterpriseVote;

public interface UserEnterpriseVoteService {
    Object insert(EnterpriseVoteSearchParam enterpriseVoteSearchParam) throws Exception;

    Object getProblem();
}
