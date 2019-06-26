package com.bridgelabz.fundoo.utility;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.Dto.LoginDTO;
import com.bridgelabz.fundoo.model.User;

@Component
public class EncodeUtil {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	public boolean isPassword(LoginDTO loginDTO, User user) {
		return passwordEncoder.matches(loginDTO.getPassword(),user.getPassword());
	}
	
	
	
	
}
