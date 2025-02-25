package com.bayaniact.common.file;

import com.bayaniact.common.entity.BrgyOfficial;
import com.bayaniact.common.entity.Resident;
import jakarta.persistence.*;

@Entity
@Table(name = "brgy_officials_files")
public class BrgyOfficialFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "brgy_official_id")
    private BrgyOfficial brgyOfficial;

    public BrgyOfficial getBrgyOfficial() {
        return brgyOfficial;
    }

    public void setBrgyOfficial(BrgyOfficial brgyOfficial) {
        this.brgyOfficial = brgyOfficial;
    }

    /**
     * @return the fileId
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }


}
