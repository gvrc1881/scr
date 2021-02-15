package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scr.model.TpcBoard;

public interface TPCBoardRepository extends JpaRepository<TpcBoard, Long>{
	List<TpcBoard> findAll();
	@Query(value = "SELECT case when count(tpb)> 0 then true else false  end  FROM TpcBoard tpb WHERE tpb.tpcBoard = :tpcBoard and tpb.dataDiv  = :dataDiv")
	Boolean existsByTpcBoardAndDataDiv(@Param("tpcBoard") String tpcBoard,@Param("dataDiv") String dataDiv);
	
    Optional<TpcBoard>findByTpcBoardAndDataDiv(String tpcBoard,String dataDiv);
    List<TpcBoard>findByDataDiv(String dataDiv);
	List<TpcBoard> getAllByDataDivIn(List<String> fac);

}
