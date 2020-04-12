package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{

	Menu findMenuById(Integer id);

}
