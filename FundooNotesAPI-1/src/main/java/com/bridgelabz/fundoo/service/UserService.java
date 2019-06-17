package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.Dto.LoginDTO;
import com.bridgelabz.fundoo.Dto.UserDTO;
import com.bridgelabz.fundoo.response.Response;

public interface UserService {

	Response registerUser(UserDTO userDTO, StringBuffer requestUrl);

	Response loginUser(LoginDTO loginDTO);

	Response forgotPassword(String emailId, StringBuffer requestUrl);

	String resetPassword(String password, String token);

	String validateUser(String token);
}
