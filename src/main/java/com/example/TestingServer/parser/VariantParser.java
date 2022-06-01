package com.example.TestingServer.parser;

import com.example.TestingServer.entity.Variant;
import org.springframework.stereotype.Component;

@Component
public class VariantParser {
    public Variant getVariantByText(String dataVariant){
        Variant variant = new Variant();
        return variant;
    }
}
