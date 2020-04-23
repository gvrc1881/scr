package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.TpcBoard;
import com.scr.repository.TPCBoardRepository;

@Service
public class TPCBoardService {
	
	@Autowired
	private TPCBoardRepository tpcBoardRepository;
	
	public List<TpcBoard> findAll() {
		// TODO Auto-generated method stub
		return tpcBoardRepository.findAll();

	}

	public void save(TpcBoard tpcBoard) {
		tpcBoardRepository.save(tpcBoard);
	}

	public Optional<TpcBoard> findTPCBoardById(Long id) {
		// TODO Auto-generated method stub
		return tpcBoardRepository.findById(id);

	}

	public void deleteTPCBoardById(Long id) {
		// TODO Auto-generated method stub
		tpcBoardRepository.deleteById(id);
	}

}
