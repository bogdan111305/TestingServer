package com.example.TestingServer.specifications;

import com.example.TestingServer.specifications.enums.SearchOperation;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
}
