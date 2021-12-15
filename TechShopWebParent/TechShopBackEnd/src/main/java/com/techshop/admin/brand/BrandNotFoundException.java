package com.techshop.admin.brand;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class BrandNotFoundException extends Exception {

	public BrandNotFoundException(String string) {
		// TODO Auto-generated constructor stub
	}

	public BrandNotFoundException() {
		// TODO Auto-generated constructor stub
	}

}
