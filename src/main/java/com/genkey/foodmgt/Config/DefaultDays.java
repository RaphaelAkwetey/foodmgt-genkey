package com.genkey.foodmgt.Config;

import com.genkey.foodmgt.model.impl.WeekDays;
import com.genkey.foodmgt.repository.dao.api.DaysDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Order(value = 2)
@Component
public class DefaultDays implements CommandLineRunner {

    @Autowired
    DaysDAO daysDAO;

    @Override
    public void run(String... args) throws Exception {



        try {
            WeekDays day1 = new WeekDays("0", "Monday", true);
            WeekDays day2 = new WeekDays("1", "Tuesday", true);
            WeekDays day3 = new WeekDays("2", "Wednesday", true);
            WeekDays day4 = new WeekDays("3", "Thursday", true);
            WeekDays day5 = new WeekDays("4", "Friday", true);
            List<WeekDays> days = Arrays.asList(day1,day2, day3,day4, day5);
            daysDAO.saveAll(days);
            //daysDAO.save(day1);
            //daysDAO.save(day2);
            //daysDAO.save(day3);
            //daysDAO.save(day4);
            //daysDAO.save(day5);

        }catch (Exception e){
            System.out.println("already persisted");

        }
    }
}
