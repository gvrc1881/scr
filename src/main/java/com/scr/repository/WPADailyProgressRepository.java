package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.WPADailyProgress;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;

public interface WPADailyProgressRepository extends JpaRepository<WPADailyProgress, Integer>{

	List<WPADailyProgress> findByWorkGroupIdAndWorkPhaseActivityIdAndDateLessThan(WorkGroup workGroup,
			WorkPhaseActivity workPhaseActivity, Date date);

	Optional<WPADailyProgress> findByWorkGroupIdAndWorkPhaseActivityIdAndDate(WorkGroup workGroupId,
			WorkPhaseActivity workPhaseActivityId, Date date);

	List<WPADailyProgress> getByWorkPhaseActivityId(WorkPhaseActivity workPhaseActivity);

}
