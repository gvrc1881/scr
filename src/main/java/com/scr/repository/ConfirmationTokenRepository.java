package com.scr.repository;

import org.springframework.data.repository.CrudRepository;

import com.scr.model.ConfirmationToken;



public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
