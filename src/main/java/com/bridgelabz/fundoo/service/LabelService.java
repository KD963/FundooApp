package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.Dto.LabelDTO;
import com.bridgelabz.fundoo.model.Label;

public interface LabelService {

	String createLabel(LabelDTO labelDto, String token);

	String updateLabel(LabelDTO labelDto, String labelId, String token);

	String deleteLabel(String labelId, String token);
	
	List<Label> getAll(String token);

}
