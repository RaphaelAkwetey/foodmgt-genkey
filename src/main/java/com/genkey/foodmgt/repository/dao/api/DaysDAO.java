package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.WeekDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysDAO extends JpaRepository<WeekDays, String> {

    WeekDays findWeekDaysById(String id);

}
