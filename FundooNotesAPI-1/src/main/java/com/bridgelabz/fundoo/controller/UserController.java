package com.bridgelabz.fundoo.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.Dto.LoginDTO;
import com.bridgelabz.fundoo.Dto.UserDTO;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> registerUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) {

		StringBuffer requestUrl = request.getRequestURL();
		Response response = userService.registerUser(userDTO, requestUrl);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO) {
		Response response = userService.loginUser(loginDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping("/verification/{verify}")
	public String validate(@PathVariable String verify) {
		return userService.validateUser(verify);
	}

	@GetMapping("/forgot")
	public ResponseEntity<Response> forgot(@RequestParam String emailId, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		Response response = userService.forgotPassword(emailId, requestUrl);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/reset")
	public String resetPassword(@RequestParam String password,@RequestParam String token) {
		return userService.resetPassword(password, token);
	}

}
