package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.Line;


@Repository
public interface LineRepository extends JpaRepository<Line, Long>{
	@Query("FROM Line ORDER BY lineCode ASC")
    List<Line> findAllOrderByLineCodeAsc();

}
