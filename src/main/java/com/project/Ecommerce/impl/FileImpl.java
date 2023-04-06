package com.project.Ecommerce.impl;

import com.project.Ecommerce.exception.BadApiRequestException;
import com.project.Ecommerce.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        log.info("File Name : {}",originalFilename);

        String  fileName= UUID.randomUUID().toString();

        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileNames = fileName + substring;

        String fullPathWithFileName= path+ fileNames;

        log.info("Full image Path : {} " +fullPathWithFileName);

        if(substring.equalsIgnoreCase(".png")|| substring.equalsIgnoreCase(".jpg") || substring.equalsIgnoreCase(".jpeg"))
        {
            log.info("file extension is {} ",substring);

            File folder = new File(path);

            if( !folder.exists())
            {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream() , Paths.get(fullPathWithFileName));

            return fileNames;

        }else{
            throw new BadApiRequestException("file with this " + substring+ " not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath= path+ File.separator +name;

        InputStream input= new FileInputStream(fullPath);

        return input;
    }
}
