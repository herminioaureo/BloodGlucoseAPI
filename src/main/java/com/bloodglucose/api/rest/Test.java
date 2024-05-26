package com.bloodglucose.api.rest;

import java.net.HttpURLConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@GetMapping("/test")
	public ResponseEntity test() {
		return ResponseEntity.status(HttpStatus.OK).body("Service OK");
	}
}
