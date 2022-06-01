package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;

@Data
public class QuestionDTO {
    @Null(groups = {New.class})
    private Long id;
    @NotEmpty(groups = {New.class})
    private String name;
    @NotNull(groups = {New.class})
    private TopicDTO topic;
    @NotNull(groups = {New.class})
    private List<AnswerDTO> answers;
}
