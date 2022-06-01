package com.example.TestingServer.repository;


import com.example.TestingServer.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long>, JpaSpecificationExecutor<Variant> {
    List<Variant> findAllByName(String name);

    Optional<Variant> findVariantById(Long variantId);
}
