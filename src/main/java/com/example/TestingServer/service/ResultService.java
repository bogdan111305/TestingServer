package com.example.TestingServer.service;

import com.example.TestingServer.payload.request.dto.ResultDTO;
import com.example.TestingServer.specifications.PageSetting;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface ResultService {
    ResultDTO createResult(ResultDTO resultDTO) throws Exception;

    ResultDTO getResultById(Long reportId, Principal principal) throws Exception;

    List<ResultDTO> getResultsByCurrentUser(Principal principal) throws Exception;
}
