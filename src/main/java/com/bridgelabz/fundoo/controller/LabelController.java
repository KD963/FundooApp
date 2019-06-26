package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.Dto.LabelDTO;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelController {

	@Autowired
	LabelService labelService;

	@PostMapping("/create")
	ResponseEntity<Response> createLabel(@RequestBody LabelDTO labelDto, @RequestParam String token) {
		String message = labelService.createLabel(labelDto, token);
		Response response = new Response(200, message, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<Response> updateLabel(@RequestBody LabelDTO labelDto, @RequestParam String labelId,
			@RequestParam String token) {
		String message = labelService.updateLabel(labelDto, labelId, token);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	ResponseEntity<Response> deleteLabel(@RequestParam String labelId, @RequestParam String token) {
		String message = labelService.deleteLabel(labelId, token);
		Response response = new Response(200, message, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/getall")
	public List<Label> getAll(@RequestParam String token) {
		List<Label> labels = labelService.getAll(token);
		return labels;
	}

}
