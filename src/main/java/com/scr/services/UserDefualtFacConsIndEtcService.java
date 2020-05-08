package com.scr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.repository.UserDefualtFacConsIndEtcRepository;

@Service
public class UserDefualtFacConsIndEtcService {
	
	@Autowired
	private UserDefualtFacConsIndEtcRepository userDefualtFacConsIndEtcRepository;

	public Optional<UserDefualtFacConsIndEtc> findByUserLoginId(String userLogin) {
		// TODO Auto-generated method stub
		return userDefualtFacConsIndEtcRepository.findByUserLoginId(userLogin);
	}
	
	

}
