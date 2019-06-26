package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.Dto.NoteDTO;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface NoteService {

	String createNote(NoteDTO noteDto, String token);

	String updateNote(NoteDTO noteDto, String noteId, String token);

	String deleteNote(String noteId, String token);

	String trashUntrash(String noteId, String token);

	String pinUnpin(String noteId, String token);

	String archiveUnarchive(String noteId, String token);

	List<Note> getAll(String token);
}
