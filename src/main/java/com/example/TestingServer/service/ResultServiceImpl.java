package com.example.TestingServer.service;

import com.example.TestingServer.entity.*;
import com.example.TestingServer.exceptions.ReportNotFoundException;
import com.example.TestingServer.exceptions.VariantNotFoundException;
import com.example.TestingServer.payload.request.dto.ResultDTO;
import com.example.TestingServer.repository.AnswerRepository;
import com.example.TestingServer.repository.VariantRepository;
import com.example.TestingServer.repository.ResultRepository;
import com.example.TestingServer.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ResultRepository resultRepository;

    private final VariantRepository variantRepository;

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository,
                             VariantRepository variantRepository, AnswerRepository answerRepository, UserRepository userRepository,
                             ModelMapper modelMapper){
        this.resultRepository = resultRepository;
        this.variantRepository = variantRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResultDTO createResult(ResultDTO resultDTO) throws Exception{
        User user = userRepository.findUserById(resultDTO.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Variant variant = variantRepository.findVariantById(resultDTO.getVariantId())
                .orElseThrow(() -> new VariantNotFoundException("Variant not found"));

        Set<Answer> answers = answerRepository.findAllById(
                resultDTO.getAnswers().stream()
                        .map(answerDTO -> answerDTO.getId())
                        .collect(Collectors.toSet())
        ).stream().collect(Collectors.toSet());

        Result result = new Result();
        result.setUser(user);
        result.setVariant(variant);
        result.setStartDate(resultDTO.getStartDate());
        result.setEndDate(resultDTO.getEndDate());
        result.setAnswers(answers);

        byte pca = check(answers, variant.getQuestions());
        result.setPercentageCorrectAnswers(pca);

        LOG.info("Report id(" + result.getId() + ") successful save.");
        resultRepository.save(result);

        return getResultDTO(result);
    }

    public ResultDTO getResultById(Long reportId, Principal principal) throws Exception{
        Result result = getResultByIdAndUser(reportId, principal);

        return getResultDTO(result);
    }

    public List<ResultDTO> getResultsByCurrentUser(Principal principal) throws Exception{
        List<Result> results;

        if(hasRole("ROLE_ADMIN")){
            results = resultRepository.findAll();
        } else {
            User user = getUserByPrincipal(principal);

            results = resultRepository.findAllByUser(user);
        }

        return results.stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .collect(Collectors.toList());
    }

    private Byte check(Set<Answer> answers, Set<Question> questions){

        return 0;
    }

    public Result getResultByIdAndUser(Long reportId, Principal principal) throws Exception{
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

    private ResultDTO getResultDTO(Result result){
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
