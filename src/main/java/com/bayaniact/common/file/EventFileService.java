package com.bayaniact.common.file;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EventFileService {

    @Autowired
    private EventFileRepository eventFileRepository;

    public EventFile saveFile(MultipartFile multipartFile, Event event) throws IOException {
        if (event == null || event.getEventId() == null) {
            throw new IllegalArgumentException("Event ID is required to save a file.");
        }

        EventFile file = new EventFile();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setFileType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());
        file.setEvent(event);

        return eventFileRepository.save(file);
    }

    public EventFile getFile(Long fileId) {
        return eventFileRepository.findById(fileId).orElse(null);
    }

    public List<EventFile> getAllFiles() {
        return eventFileRepository.findAll();
    }

    public void deleteFile(Long fileId) {
        eventFileRepository.deleteById(fileId);
    }
}
