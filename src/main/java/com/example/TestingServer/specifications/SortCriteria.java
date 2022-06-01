package com.example.TestingServer.specifications;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter @Getter
public class SortCriteria {
    @NotEmpty
    private String sortProperty;
    @NotEmpty
    private String direction;
}
