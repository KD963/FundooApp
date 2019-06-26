package com.bridgelabz.fundoo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
		String message = userService.registerUser(userDTO, requestUrl);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
		String token = userService.loginUser(loginDTO);
		response.setHeader("Authorization", token);
		Response response2 = new Response(200, "successfully login", token);
		return new ResponseEntity<Response>(response2, HttpStatus.OK);
	}

	@GetMapping("/verification/{verify}")
	public ResponseEntity<Response> validate(@PathVariable String verify) {
		String message = userService.validateUser(verify);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/forgot")
	public ResponseEntity<Response> forgot(@RequestParam String emailId, HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		String message = userService.forgotPassword(emailId, requestUrl);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/reset")
	public ResponseEntity<Response> resetPassword(@RequestBody String password, @RequestHeader String token) {
		String message = userService.resetPassword(password, token);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
