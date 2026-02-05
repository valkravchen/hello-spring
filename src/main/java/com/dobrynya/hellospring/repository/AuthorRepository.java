package com.dobrynya.hellospring.repository;

import com.dobrynya.hellospring.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
