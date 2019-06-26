package com.bridgelabz.fundoo.Dto;

public class LabelDTO {

	private String labelName;

	public LabelDTO() {

	}

	public LabelDTO(String labelName) {
		super();
		this.labelName = labelName;
	}
	

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public String toString() {
		return "LabelDTO [labelName=" + labelName + "]";
	}

}
