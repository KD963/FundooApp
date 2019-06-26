package com.bridgelabz.fundoo.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoo.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	Optional<Note> findByNoteId(String noteId);
}
