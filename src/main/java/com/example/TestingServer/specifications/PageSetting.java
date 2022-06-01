package com.example.TestingServer.specifications;

import com.example.TestingServer.specifications.SearchCriteria;
import com.example.TestingServer.specifications.SortCriteria;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
public class PageSetting {
    @NotEmpty
    private int page;
    @NotEmpty
    private int size;
    @NotEmpty
    private List<SortCriteria> sortCriteria;
}
