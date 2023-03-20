package com.example.rest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResult {
	private int result;
	
	/** Error map
	 * key: field name
	 * value: error message
	 */
	private Map<String, String> errors;
}
