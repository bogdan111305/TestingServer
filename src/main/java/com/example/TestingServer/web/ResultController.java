package com.example.TestingServer.web;

import com.example.TestingServer.specifications.PageSetting;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.example.TestingServer.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.TestingServer.payload.request.dto.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }

    @GetMapping("/user/reports")
    public List<ResultDTO> getAllVariantByUser(Principal principal) throws Exception{
        return resultService.getReportsByCurrentUser(principal);
    }

    @GetMapping("/{reportId}")
    public ResultDTO getVariantById(@PathVariable("reportId") Long reportId, Principal principal) throws Exception{
        return resultService.getReportById(reportId,principal);
    }

    @PostMapping("/create")
    public ResultDTO createReport(@Validated(New.class) @RequestBody ResultDTO resultDTO) throws Exception{
        return resultService.createReport(resultDTO);
    }

    @PostMapping("/update/{reportId}")
    public ResultDTO updateReport(@PathVariable("reportId") Long reportId, @Validated(Exist.class) @RequestBody ResultDTO resultDTO) throws Exception{
        return resultService.updateAndCheckReport(reportId, resultDTO);
    }
}
