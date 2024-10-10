package com.genkey.foodmgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    private String filename;
    private String url;

    private Long id;

    public Long getId() {
        return id;
    }

    public FileInfo(String filename, String url,Long id) {
        this.filename = filename;
        this.url = url;
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}