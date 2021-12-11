package com.springframework.springrecipeapp.repository;

import com.springframework.springrecipeapp.model.Notes;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Notes, Long> {
}
