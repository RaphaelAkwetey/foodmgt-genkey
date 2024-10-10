package com.genkey.foodmgt;

import com.genkey.foodmgt.dto.*;
import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@SpringBootTest
class FoodOrderManagerApplicationTests {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	MenuDAO menuDAO;

	@Autowired
	ReportDAO reportDAO;

	@Autowired
	DaysDAO daysDAO;

	@Test
	void contextLoads() {

		LocalDate date = LocalDate.now();
		LocalDate today = date.minusDays(6);
		LocalDate next = date.plusDays(1);
		LocalDate monday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

		List<OrderResponse> orders = transactionRepository.getAllPendingOrdersAndUsers(monday);
		System.out.println(orders);
		/*function getMondayDate() {
  const d = new Date();
  const daysToMonday = (7 - d.getDay()) % 7 + 2;
  const monday = d.getDate() + daysToMonday;

			return new Date(d.getFullYear(), d.getMonth(), monday);
		}

		console.log(getMondayDate().toISOString().substring(0,10));*/


	}

}
