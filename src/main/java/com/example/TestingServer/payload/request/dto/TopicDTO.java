package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class TopicDTO {
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Long id;
    @NotEmpty(groups = {New.class})
    @Null(groups = {Exist.class})
    private String name;
}
