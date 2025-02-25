package com.bayaniact.common.file;

import com.bayaniact.common.entity.BrgyOfficial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BrgyOfficialFileService {

    @Autowired
    private BrgyOfficialFileRepository brgyOfficialFileRepository;

    public BrgyOfficialFile saveFile(MultipartFile multipartFile, BrgyOfficial brgyOfficial) throws IOException {
        if (brgyOfficial == null || brgyOfficial.getBrgyOfficialId() == null) {
            throw new IllegalArgumentException("Barangay Official ID is required to save a file.");
        }

        BrgyOfficialFile file = new BrgyOfficialFile();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());
        file.setBrgyOfficial(brgyOfficial);

        return brgyOfficialFileRepository.save(file);
    }

    public BrgyOfficialFile getFile(Long fileId) {
        return brgyOfficialFileRepository.findById(fileId).orElse(null);
    }

    public List<BrgyOfficialFile> getAllFiles() {
        return brgyOfficialFileRepository.findAll();
    }

    public void deleteFile(Long fileId) {
        brgyOfficialFileRepository.deleteById(fileId);
    }
}
