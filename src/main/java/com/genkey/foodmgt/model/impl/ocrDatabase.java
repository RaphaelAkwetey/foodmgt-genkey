package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ocr", schema="public")
@Getter
@Setter
//@ToString
@RequiredArgsConstructor
public class ocrDatabase extends UniqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(columnDefinition="text", length=10485760)
    private String text;
    @Column
    private LocalDate uploadDate;

    @Column
    private boolean display = false;

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}
