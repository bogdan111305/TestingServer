package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.entity.Question;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class AnswerDTO {
    private Long id;

    @NotEmpty(groups = {New.class})
    @Null(groups = {Exist.class})
    private String name;

    @NotNull(groups = {New.class})
    @Null(groups = {Exist.class})
    private Boolean isTrue;

    public void clearIsTrue(){
        this.isTrue = null;
    }
}
