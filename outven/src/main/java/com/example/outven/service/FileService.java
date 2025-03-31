package com.example.outven.service;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    
    private final String FILE_PATH = "static/storage";

    public String storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ""; // 파일이 없으면 빈 문자열 반환
        }

        try {
            // 파일명 생성 (타임스탬프 적용)
            String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = formattedDate + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

            // 저장 경로 설정
            File file = new File(FILE_PATH, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 파일 저장
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
