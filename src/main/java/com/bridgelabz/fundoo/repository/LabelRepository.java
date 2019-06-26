package com.bridgelabz.fundoo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Label;
import java.lang.String;
import java.util.List;


@Repository
public interface LabelRepository extends MongoRepository<Label, String> {

	List<Label> findByLabelId(String labelid);
	
}
