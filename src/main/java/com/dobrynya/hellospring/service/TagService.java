package com.dobrynya.hellospring.service;

import com.dobrynya.hellospring.exception.TagNotFoundException;
import com.dobrynya.hellospring.model.Tag;
import com.dobrynya.hellospring.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new TagNotFoundException(id);
        }
        tagRepository.deleteById(id);
    }
}
