package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.entity.Question;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class VariantDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private List<QuestionDTO> questions;

    private List<ResultDTO> results;
}
