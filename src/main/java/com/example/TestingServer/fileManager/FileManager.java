package com.example.TestingServer.fileManager;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class FileManager {
    public String getTextByFile(MultipartFile multipartFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        String dataTest = "";
        String lineTxt;
        while ((lineTxt=bufferedReader.readLine())!=null){
            dataTest += lineTxt;
        }
        return dataTest;
    }
    public String getFileByText(String dataVariant) throws IOException {
        String dataTest = "";
        return dataTest;
    }
}
