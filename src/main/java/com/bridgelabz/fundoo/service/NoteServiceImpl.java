package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.Dto.NoteDTO;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.NoteRepository;
import com.bridgelabz.fundoo.repository.UserRepository;

import com.bridgelabz.fundoo.utility.JwtTokenInterface;

/**
 * @author administrator
 *
 */
@Service
public class NoteServiceImpl implements NoteService {

	Logger logger = Logger.getLogger(NoteServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtTokenInterface jwtToken;

	/**
	 * Details create a note using two parameters noteDto and a token
	 */
	@Override
	public String createNote(NoteDTO noteDto, String token) {

		String userId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findByUserId(userId);
		Note note = modelMapper(noteDto, Note.class);

		String noteId = note.getUserId();
		logger.info("note id " + noteId);
		/*
		 * if(noteDto.getTitle().isEmpty() && noteDto.getDescription().isEmpty()) {
		 * throw new NoteException("Title and description should not be empty"); } else
		 * {
		 */
		if (optionalUser.isPresent()) {

			note.setCreateTime(LocalDateTime.now());
			note.setUpdateTime(LocalDateTime.now());
			note.setUserId(userId);

			noteRepository.save(note);

			return "create note ";

		}

		return "fails";

	}

	/**
	 * Details UpdateNote it takes three parameter noteDto, noteId and the token
	 */
	@Override
	public String updateNote(NoteDTO noteDto, String noteId, String token) {
		String userId = jwtToken.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {
			Optional<Note> optionalNote = noteRepository.findById(noteId);
			Note note = optionalNote.get();

			if (optionalNote.isPresent()) {
				note.setTitle(noteDto.getTitle());
				note.setDescription(noteDto.getDescription());
				note.setUpdateTime(LocalDateTime.now());
				noteRepository.save(note);
				return "This Note is modified";
			} else {
				return "This note is not modified";
			}

		} else {
			throw new UserException("Something went wrong");
		}

	}

	/**
	 * Details trash and untrash a note. This is a string method using two
	 * parameters noteId and a token
	 */
	@Override
	public String trashUntrash(String noteId, String token) {
		String userId = jwtToken.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {

			Optional<Note> optionalNote = noteRepository.findByNoteId(noteId);
			if (optionalNote.isPresent()) {
				Note note = optionalNote.get();
				if (!note.isTrash()) {
					note.setTrash(true);
					noteRepository.save(note);
					return "Note is trashed";
				} else {
					note.setTrash(false);
					noteRepository.save(note);
					return "note is untrashed";
				}
			} else {
				throw new UserException("Note is not found");
			}

		} else {
			throw new UserException("Something went wrong");
		}

	}

	/**
	 * Details pin and unpin is a string method having two parameters noteId and a
	 * token
	 */
	@Override
	public String pinUnpin(String noteId, String token) {

		String userId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {

			Optional<Note> optionalNote = noteRepository.findByNoteId(noteId);
			Note note = optionalNote.get();
			if (optionalNote.isPresent()) {
				if (!note.isPin()) {
					note.setPin(true);
					noteRepository.save(note);
					return "This is pinned note";
				} else {
					note.setPin(false);
					noteRepository.save(note);
					return "This is unpinned note";
				}

			} else {
				throw new UserException("Note is not present");
			}
		} else {
			throw new UserException("Something went wrong");
		}

	}

	/**
	 * Details archive and unarchive a note is a string method having two parameters
	 * noteId and a token
	 */
	@Override
	public String archiveUnarchive(String noteId, String token) {
		String userId = jwtToken.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (optionalUser.isPresent()) {
			Optional<Note> optionalNote = noteRepository.findByNoteId(noteId);
			if (optionalNote.isPresent()) {
				Note note = optionalNote.get();
				if (!note.isArchive()) {
					note.setArchive(true);
					noteRepository.save(note);
					return "This is Archive note";
				} else {
					note.setArchive(false);
					noteRepository.save(note);
					return "This is unarchive note";
				}
			} else {
				throw new UserException("Note is not present");
			}
		} else {
			throw new UserException("Something went wrong");
		}

	}

	/**
	 * Details delete a note is String method having two parameters noteId and a
	 * token
	 */
	@Override
	public String deleteNote(String noteId, String token) {

		String userId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findByUserId(userId);
		return optionalUser.filter(user -> user != null).map(user -> {
			Optional<Note> optionalNote = noteRepository.findById(noteId);
			optionalNote.filter(note -> {
				return note.isTrash();
			}).map(note -> {
				noteRepository.delete(note);
				return "Deleted note";
			}).orElseThrow(() -> new UserException("note not found"));
			return "deleted note";
		}).orElseThrow(() -> new UserException("Something went wrong"));

	}

	/**
	 * Details getAll notes is collection-type method having a noteId
	 */

	@Override
	public List<Note> getAll(String token) {
		String userId = jwtToken.verifyToken(token);

		/*
		 * List<Note> filterNote = notes.stream().filter(note -> { return
		 * note.getUserId().equals(userId); }).collect(Collectors.toList());
		 * 
		 * logger.info("Filter notes " + filterNote); return filterNote;
		 */

		/*
		 * 
		 * Optional<User> optionalUser = userRepository.findByUserId(userId);
		 * 
		 * if (optionalUser.isPresent()) { List<Note> notes = noteRepository.findAll();
		 * return notes;
		 * 
		 * } else { throw new UserException("Invalid user");}
		 */

		logger.info("User id " + userId);

		List<Note> notes = noteRepository.findAll();
		List<Note> filterNote = notes.stream().filter(note -> {
			return note.getUserId().equals(userId);

		}).collect(Collectors.toList());

		return filterNote;
	}

	// modelMapper
	private <E, T> E modelMapper(T srcObject, Class<E> desObject) {

		return mapper.map(srcObject, desObject);
	}

}
