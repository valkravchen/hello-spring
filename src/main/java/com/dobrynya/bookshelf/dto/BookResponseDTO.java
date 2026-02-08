package com.dobrynya.bookshelf.dto;

import java.util.List;

public class BookResponseDTO {
    private Long id;
    private String title;
    private List<String> authorNames;
    private List<String> tagNames;
    private boolean hasPdf;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String title, List<String> authorNames,
                           List<String> tagNames, boolean hasPdf) {
        this.id = id;
        this.title = title;
        this.authorNames = authorNames;
        this.tagNames = tagNames;
        this.hasPdf = hasPdf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public boolean isHasPdf() {
        return hasPdf;
    }

    public void setHasPdf(boolean hasPdf) {
        this.hasPdf = hasPdf;
    }
}
