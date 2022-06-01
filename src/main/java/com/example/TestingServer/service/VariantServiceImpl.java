package com.example.TestingServer.service;

import com.example.TestingServer.entity.Question;
import com.example.TestingServer.entity.Variant;
import com.example.TestingServer.exceptions.VariantNotFoundException;
import com.example.TestingServer.payload.request.dto.QuestionDTO;
import com.example.TestingServer.payload.request.dto.VariantDTO;
import com.example.TestingServer.repository.VariantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantServiceImpl implements VariantService{

    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final VariantRepository variantRepository;


    @Autowired
    public VariantServiceImpl(ModelMapper modelMapper, QuestionService questionService, VariantRepository variantRepository) {
        this.modelMapper = modelMapper;
        this.questionService = questionService;
        this.variantRepository = variantRepository;
    }

    @Override
    public VariantDTO create(VariantDTO variantDTO) throws Exception {
        Variant variant = new Variant();
        variant.setName(variantDTO.getName());

        List<Question> questions = getOrCreateQuestions(variantDTO.getQuestions());

        variant.setQuestions(questions.stream().collect(Collectors.toSet()));

        Variant variantUpdate = variantRepository.save(variant);

        return modelMapper.map(variantUpdate, VariantDTO.class);
    }

    @Override
    public VariantDTO update(VariantDTO variantDTO, Long variantId) throws Exception{
        Variant variant = variantRepository.findVariantById(variantId).orElseThrow(() -> new VariantNotFoundException("Variant not found"));
        variant.setName(variantDTO.getName());

        List<Question> questions = getOrCreateQuestions(variantDTO.getQuestions());

        variant.setQuestions(questions.stream().collect(Collectors.toSet()));

        variantRepository.save(variant);

        return modelMapper.map(variant, VariantDTO.class);
    }

    @Override
    public void delete(Long variantId) {
        variantRepository.deleteById(variantId);
    }

    @Override
    public VariantDTO getVariantById(Long variantId) {
        Variant variant = variantRepository.getById(variantId);
        return modelMapper.map(variant, VariantDTO.class);
    }

    @Override
    public List<VariantDTO> getAllVariants(String nameVariant) {
        if (nameVariant == null){
            return variantRepository.findAll().stream()
                    .map(variant -> modelMapper.map(variant, VariantDTO.class)).collect(Collectors.toList());
        } else {
            return variantRepository.findAllByName(nameVariant).stream().map(variant -> modelMapper.map(variant, VariantDTO.class)).collect(Collectors.toList());
        }
    }

    private List<Question> getOrCreateQuestions(List<QuestionDTO> questionDTOS) throws Exception{
        List<Question> questions = new ArrayList<>();

        List<Long> questionsID = questionDTOS.stream()
                .filter(questionDTO -> questionDTO.getId() != null)
                .map(questionDTO -> questionDTO.getId())
                .collect(Collectors.toList());

        if (!questionsID.isEmpty())
            questions = questionService.getQuestions(questionsID);

        List<QuestionDTO> newQuestionsDTO = questionDTOS.stream()
                .filter(questionDTO -> questionDTO.getId() == null)
                .collect(Collectors.toList());

        if (!newQuestionsDTO.isEmpty())
            questions.addAll(questionService.createAll(newQuestionsDTO));

        return questions;
    }
}
