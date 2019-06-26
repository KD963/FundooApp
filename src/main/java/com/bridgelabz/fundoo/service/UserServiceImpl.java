package com.bridgelabz.fundoo.service;

import java.io.UnsupportedEncodingException;

import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.Dto.LoginDTO;
import com.bridgelabz.fundoo.Dto.UserDTO;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.model.Email;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.EncodeUtil;
import com.bridgelabz.fundoo.utility.JwtTokenInterface;
import com.bridgelabz.fundoo.utility.MailUtil;

/**
 * @author administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtTokenInterface jwtToken;

	@Autowired
	private EncodeUtil encodeUtil;

	/**
	 * Details userDTO userDTO {@link UserDTO}
	 * 
	 */
	@Override
	public String registerUser(UserDTO userDTO, StringBuffer requestUrl) {

		User user = modelMapper(userDTO, User.class);

		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

		logger.info("User id " + user.getUserId());

		logger.info("Optional user " + optionalUser);
		Email email = new Email();
		String token;
		if (!optionalUser.isPresent()) {

			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			User saveUser = userRepository.save(user);
			logger.info("Save user " + saveUser);
			// generating token

			try {
				token = jwtToken.generateToken(saveUser.getUserId());
				logger.info("Generated Token " + token);

				String activationUrl = validationUrl(requestUrl, "/verification/", token);
				logger.info("activation url" + activationUrl);
				email.setTo(saveUser.getEmail());
				email.setSub("Verification link");
				email.setBody("Please verify your mail \n " + activationUrl);
				mailUtil.send(email);
				return "mail send successfully";
			} catch (Exception e) {

				e.printStackTrace();
				throw new UserException("something  went wrong");
			}

		} else {

			throw new UserException("User is already present");

		}

	}

	/**
	 * Details to check whether user is valid or not
	 */
	@Override
	public String validateUser(String token) {

		String tokenId = jwtToken.verifyToken(token);

		logger.info("token id " + tokenId);

		Optional<User> optionalUser = userRepository.findById(tokenId);
		logger.info("optional user " + optionalUser);

		try {
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setVerify(true);
				userRepository.save(user);
				return "User is verify";
			} else {
				throw new UserException("User is not verify");
			}
		} catch (Exception exception) {
			throw new UserException("something went wrong");
		}

	}

	/**
	 * Details login user LoginDTO
	 */
	@Override
	public String loginUser(LoginDTO loginDTO) {

		Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			boolean ispassword = encodeUtil.isPassword(loginDTO, user);

			try {
				if (ispassword) {

					if (user.isVerify()) {
						String token = jwtToken.generateToken(user.getUserId());
						logger.info("Generated token in login " + token);
						return token;
					} else {
						throw new UserException("Verify your mail");
					}

				} else {
					throw new UserException("Password is wrong");
				}
			} catch (Exception exception) {
				exception.printStackTrace();
				throw new UserException("Unsuccessul");
			}
		} else {
			throw new UserException("User already exist");
		}

	}

	/**
	 * Details forgot password
	 */
	@Override
	public String forgotPassword(String emailId, StringBuffer requestUrl) {

		Optional<User> optionalUser = userRepository.findByEmail(emailId);

		logger.info("Optional user " + optionalUser);
		User user = optionalUser.get();
		logger.info("User is " + user);
		if (optionalUser.isPresent()) {

			try {
				String id = user.getUserId();

				logger.info("generated ID is " + id);
				String token = jwtToken.generateToken(id);
				logger.info("Generated token is " + token);
				String resetLink = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/resetPassword/" + token;
				Email email = new Email();
				email.setTo(user.getEmail());
				email.setSub("Forgot password link");
				email.setBody("Please,Reset your password using below link \n " + resetLink);
				mailUtil.send(email);
				return "mail send successful";

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new UserException("Something went wrong");
			}
		} else {

			throw new UserException("Invalid user");
		}

	}

	/**
	 * Details reset password
	 */
	@Override
	public String resetPassword(String password, String token) {

		String tokenId = jwtToken.verifyToken(token);
		logger.info("token id  " + tokenId);

		Optional<User> optionalUser = userRepository.findById(tokenId);

		try {

			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setPassword(passwordEncoder.encode(password));
				userRepository.save(user);
				return "password is changed";
			} else {
				throw new UserException("Password is not changed");
			}
		} catch (Exception exception) {
			throw new UserException("Something went wrong");
		}

	}

	// method for activationUrl

	private String validationUrl(StringBuffer requestUrl, String urlMapping, String token) {

		String link = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + urlMapping + token;
		return link;

		// String link1 = requestUrl.substring(0,
		// requestUrl.lastIndexOf("/")).concat(urlMapping).concat(token);

	}

	// modelMapper
	private <E, T> E modelMapper(T srcObject, Class<E> desObject) {

		return mapper.map(srcObject, desObject);
	}

}
