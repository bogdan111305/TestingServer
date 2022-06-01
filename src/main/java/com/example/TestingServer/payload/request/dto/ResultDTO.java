package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResultDTO {
    @Null(groups = {New.class, Exist.class})
    private Long id;
    @Null(groups = {New.class})
    @NotNull(message = "Date report not can empty", groups = {Exist.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @Null(groups = {New.class})
    @NotNull(message = "Execution date time not can empty", groups = {Exist.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @NotEmpty(groups = {New.class, Exist.class})
    private Long userId;
    @NotEmpty(groups = {New.class, Exist.class})
    private Long variantId;
    @NotNull(groups = {New.class, Exist.class})
    private List<AnswerDTO> answers;

    private Byte percentageCorrectAnswers;
}
