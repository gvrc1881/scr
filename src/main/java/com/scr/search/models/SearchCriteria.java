package com.scr.search.models;

import lombok.Data;

public @Data class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
	public SearchCriteria(String key, String operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
    
    
}