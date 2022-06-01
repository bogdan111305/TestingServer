package com.example.TestingServer.service;

import com.example.TestingServer.exceptions.ReportNotFoundException;
import com.example.TestingServer.payload.request.dto.ResultDTO;
import com.example.TestingServer.entity.Result;
import com.example.TestingServer.entity.User;
import com.example.TestingServer.specifications.PageSetting;
import com.example.TestingServer.repository.ResultRepository;
import com.example.TestingServer.repository.UserRepository;
import com.example.TestingServer.specifications.SpecificationImpl;
import com.example.TestingServer.specifications.SearchCriteria;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ResultRepository resultRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository,
                             UserRepository userRepository,
                             ModelMapper modelMapper){
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResultDTO createReport(ResultDTO resultDTO) throws Exception{
        User user = userRepository.findUserById(resultDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found with username"));

        Result result = new Result();
        result.setUser(user);

        LOG.info("Report id(" + result.getId() + ") successful save.");
        resultRepository.save(result);


        /*result.setReportAnswers(resultAnswers);*/

        return getReportDTO(result);
    }

    @Override
    public ResultDTO updateAndCheckReport(Long reportId, ResultDTO resultDTO) throws Exception {
        Result result = resultRepository.findReportById(reportId).orElseThrow(() -> new ReportNotFoundException("Report not found"));
/*

        result.getStartDate(reportDTO.getDateReport());
        result.setExecutionDateTime(reportDTO.getExecutionDateTime());
*/

        LOG.info("Report id(" + result.getId() + ") successful update.");
        resultRepository.save(result);


        /*result.setReportAnswers(resultAnswers);
*/
        return getReportDTO(result);
    }

    public ResultDTO getReportById(Long reportId, Principal principal) throws Exception{
        Result result = getReportByIdAndUser(reportId, principal);

        return getReportDTO(result);
    }

    public List<ResultDTO> getReportsByCurrentUser(Principal principal) throws Exception{
        List<Result> results;

        if(hasRole("ROLE_ADMIN")){
            results = resultRepository.findAll();
        } else {
            User user = getUserByPrincipal(principal);

            results = resultRepository.findAllByUser(user);
        }

        return results.stream().map(result -> modelMapper.map(result, ResultDTO.class)).collect(Collectors.toList());
    }

    public Result getReportByIdAndUser(Long reportId, Principal principal) throws Exception{
        if(hasRole("ROLE_ADMIN")){
            return resultRepository.findReportById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException("Report not found"));
        } else {
            User user = getUserByPrincipal(principal);

            return resultRepository.findReportByIdAndUser(reportId, user)
                    .orElseThrow(() -> new ReportNotFoundException("Report not found"));
        }
    }

    private User getUserByPrincipal(Principal principal) throws Exception{
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

    private ResultDTO getReportDTO(Result result){
        ResultDTO resultDTO = null;

        if(hasRole("ROLE_ADMIN")){
            resultDTO = modelMapper.map(result, ResultDTO.class);
        } else {
            resultDTO = modelMapper.map(result, ResultDTO.class);
        }

        return resultDTO;
    }

    private boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
