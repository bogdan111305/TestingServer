package com.example.TestingServer.specifications;

import com.example.TestingServer.repository.BaseSpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SpecificationSearchJPA {

    public <E, D> Page<E> getAll(int page, int size, List<SearchCriteria> searchCriteriaList, BaseSpecificationRepository<E, D> repository) throws Exception{
        Pageable pageable = PageRequest.of(page, size);;

        SpecificationImpl specificationsImpl = new SpecificationImpl();
        specificationsImpl.setList(searchCriteriaList);

        Page<E> entities = repository.findAll(specificationsImpl, pageable);

        return entities;
    }
}
