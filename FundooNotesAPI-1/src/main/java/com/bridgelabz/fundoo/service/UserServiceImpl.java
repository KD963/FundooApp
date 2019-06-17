package com.bridgelabz.fundoo.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.Dto.ForgotDTO;
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
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author administrator
 *
 */
/**
 * @author administrator
 *
 */
/**
 * @author administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired(required = true)
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
	public Response registerUser(UserDTO userDTO, StringBuffer requestUrl) {

		User user = modelMapper(userDTO, User.class);

		Optional<User> optionalUser = userRepository.findByEmailId(userDTO.getEmailId());

		Email email = new Email();
		String token;
		if (!optionalUser.isPresent()) {

			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			User saveUser = userRepository.save(user);
			System.out.println("Save user " + saveUser);
			// generating token

			try {
				token = jwtToken.generateToken(saveUser.getUserId());
				System.out.println("Generated Token " + token);
				String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/verification/" + token;
				email.setTo(saveUser.getEmailId());
				email.setSub("Verification link");
				email.setBody("Please verify your mail \n " + activationUrl);
				mailUtil.send(email);
				return new Response(200, "mail send successfully", null);
			} catch (Exception e) {

				e.printStackTrace();
				throw new UserException("something  went wrong");
			}

		} else {

			throw new UserException("User is already present");

		}

	}

//------------------------------------Validation--------------------------------
	@Override
	public String validateUser(String token) {

		String tokenId = jwtToken.verifyToken(token);

		Optional<User> optionalUser = userRepository.findById(tokenId);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setVerify(true);
			userRepository.save(user);
			return "User is verify";
		} else {
			return "User is not verify";
		}

	}

//------------------------------------Login--------------------------------------
	@Override
	public Response loginUser(LoginDTO loginDTO) {

		Optional<User> optUser = userRepository.findByEmailId(loginDTO.getEmailId());
		if (optUser.isPresent()) {
			User user = optUser.get();
			boolean ispassword = encodeUtil.isPassword(loginDTO, user);
			if (ispassword) {
				return new Response(200, "successful", null);
			} else {
				return new Response(-3, "unsuccessful", null);
			}
		} else {
			return new Response(-3, "user not found", null);
		}

	}

//------------------------------------forgot password--------------------------------

	@Override
	public Response forgotPassword(String emailId, StringBuffer requestUrl) {

		Optional<User> optionalUser = userRepository.findByEmailId(emailId);
		System.out.println("Optional user " + optionalUser);
		User user = optionalUser.get();
		System.out.println("User is " + user);
		if (optionalUser.isPresent()) {

			try {
				String id = user.getUserId();

				System.out.println("generated ID is " + id);
				String token = jwtToken.generateToken(id);
				System.out.println("Generated token is " + token);
				String resetLink = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/resetPassword/" + token;
				Email email = new Email();
				email.setTo(user.getEmailId());
				email.setSub("Forgot password link");
				email.setBody("Please,Reset your password using below link \n " + resetLink);
				mailUtil.send(email);
				return new Response(200, "mail send successful", null);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new UserException("Something went wrong");
			}
		} else {

			throw new UserException("Invalid user");
		}

	}

	@Override
	public String resetPassword(String password, String token) {

		String tokenId = jwtToken.verifyToken(token);
		System.out.println("token id  " + tokenId);

		Optional<User> optionalUser = userRepository.findById(tokenId);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			return "Password is changed";
		} else {
			return "Password is not changed";
		}

	}

	private <E, T> E modelMapper(T srcObject, Class<E> desObject) {

		return mapper.map(srcObject, desObject);
	}

}
