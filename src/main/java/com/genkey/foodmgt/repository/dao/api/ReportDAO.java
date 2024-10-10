package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportDAO extends JpaRepository<MonthlyReport, Integer> {

    List<MonthlyReport> findMonthlyReportByLocalDate(LocalDate localDate);


    List<MonthlyReport> findDistinctFirstnameAndCompanyAndSpendingByLocalDate(LocalDate date);

    List<MonthlyReport> findDistinctByLocalDate(LocalDate date);

    @Query(value = "select r.firstname,r.id,r.company,r.spending,r.local_date from report r where local_date = cast(:date as date)" +
            "group by r.firstname,r.id,r.company,r.spending,r.local_date",nativeQuery = true)
    List<MonthlyReport> silence(LocalDate date);



}
