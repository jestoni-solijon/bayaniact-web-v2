package com.bayaniact.common.file;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.bayaniact.common.entity.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File saveFile(MultipartFile multipartFile, Resident resident) throws IOException {
        if (resident == null || resident.getResidentId() == null) {
            throw new IllegalArgumentException("Resident ID is required to save a file.");
        }

        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());
        file.setResident(resident);

        return fileRepository.save(file);
    }

    public File getFile(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }
}

