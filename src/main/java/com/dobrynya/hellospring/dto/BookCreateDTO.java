package com.dobrynya.hellospring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class BookCreateDTO {

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 200, message = "Название: от 2 до 200 символов")
    private String title;

    @NotEmpty(message = "Укажите хотя бы одного автора")
    private Set<Long> authorIds;

    private Set<Long> tagIds;

    public BookCreateDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
