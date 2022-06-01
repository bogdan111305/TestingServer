package com.example.TestingServer.web;

import com.example.TestingServer.payload.request.dto.VariantDTO;
import com.example.TestingServer.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/variant")
public class VariantController {

    private VariantService variantService;

    @Autowired
    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping()
    public List<VariantDTO> getAllVariants(@RequestParam(name = "name", required = false) String name) throws Exception {
        return variantService.getAllVariants(name);
    }

    @PostMapping()
    public VariantDTO createVariant(@Valid @RequestBody VariantDTO variantDTO) throws Exception {
        return variantService.create(variantDTO);
    }

    @PutMapping(value="/{variantId}")
    public VariantDTO updateVariant(@Valid @RequestBody VariantDTO variantDTO, @PathVariable("variantId") Long variantId) throws Exception {
        return variantService.update(variantDTO, variantId);
    }

    @DeleteMapping(value="/{variantId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("variantId") Long variantId) throws Exception {
        variantService.delete(variantId);
    }

    @GetMapping(value = "/{variantId}")
    public VariantDTO getVariant(@PathVariable("variantId") Long variantId) throws Exception {
        return variantService.getVariantById(variantId);
    }

    @PostMapping("/check")
    public VariantDTO check(@RequestParam(name = "id") String variantId,@RequestParam(name = "userId") String name) throws Exception {
        return null;
    }
}
