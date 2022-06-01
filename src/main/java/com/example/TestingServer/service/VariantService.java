package com.example.TestingServer.service;

import com.example.TestingServer.payload.request.dto.VariantDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface VariantService {

    VariantDTO create(VariantDTO variantDTO) throws Exception;

    VariantDTO update(VariantDTO variantDTO, Long variantId) throws  Exception;

    void delete(Long variantId);

    VariantDTO getVariantById(Long variantId);

    List<VariantDTO> getAllVariants(String nameVariant);
}
