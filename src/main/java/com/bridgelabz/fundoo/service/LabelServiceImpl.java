package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.Dto.LabelDTO;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.LabelRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.utility.JwtTokenInterface;

@Service
public class LabelServiceImpl implements LabelService {

	Logger logger = Logger.getLogger(LabelServiceImpl.class.getName());

	@Autowired
	UserService userService;

	@Autowired
	NoteService noteService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	JwtTokenInterface jwtToken;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Details createLabel is String method having two parameters labelDto and token
	 */
	@Override
	public String createLabel(LabelDTO labelDto, String token) {
		String userId = jwtToken.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(userId);

		Label label = modelMapper(labelDto, Label.class);
		String labelId = label.getUserId();
		logger.info("Label id" + labelId);

		if (optionalUser.isPresent()) {

			label.setLabelName(labelDto.getLabelName());
			label.setCreateTime(LocalDateTime.now());
			label.setUpdateTime(LocalDateTime.now());
			label.setUserId(userId);
			labelRepository.save(label);
			return "Label is created";
		} else {
			throw new LabelException("Label is not created");
		}

	}

	/**
	 * Details updateLabel is String method having three parameters labelDto,
	 * labelId and a token
	 */
	@Override
	public String updateLabel(LabelDTO labelDto, String labelId, String token) {
		String userId = jwtToken.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {

			Optional<Label> optionalLabel = labelRepository.findById(labelId);
			Label label = optionalLabel.get();

			if (optionalLabel.isPresent()) {
				label.setLabelName(labelDto.getLabelName());
				label.setCreateTime(LocalDateTime.now());
				label.setUpdateTime(LocalDateTime.now());
				labelRepository.save(label);
				return "This is updated label";
			} else {
				throw new LabelException("Label is not updated");
			}

		} else {
			throw new LabelException("Label is not found");
		}

	}

	/**
	 * Details deleteLabel is String method having two parameters labelId and a
	 * token
	 */
	@Override
	public String deleteLabel(String labelId, String token) {
		String userId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {
			Optional<Label> optionalLabel = labelRepository.findById(labelId);
			if (optionalLabel.isPresent()) {
				Label label = optionalLabel.get();
				labelRepository.delete(label);
				return "Label is deleted";
			} else {
				throw new LabelException("Label is not found");
			}
		} else {
			throw new LabelException("Label is not deleted");
		}

	}

	/**
	 * Details getAll labels is collection-type method having one parameter a token
	 */
	@Override
	public List<Label> getAll(String token) {
		String userId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findByUserId(userId);

		if (optionalUser.isPresent()) {
			List<Label> labels = labelRepository.findAll();
			return labels;
		} else {
			throw new LabelException("Labels not found");
		}

	}

	// modelMapper
	private <E, T> E modelMapper(T srcObject, Class<E> desObject) {

		return mapper.map(srcObject, desObject);
	}

}
