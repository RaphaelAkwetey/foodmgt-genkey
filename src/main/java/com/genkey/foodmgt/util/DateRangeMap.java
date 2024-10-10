/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.util;

import java.util.Calendar;
import static java.util.Calendar.getInstance;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author david
 */
@Data
public class DateRangeMap {
    public Map<Integer,Calendar> dateRanges;
    public static final Calendar JAN = getInstance();
    public static final Calendar FEB= getInstance();
    public static final Calendar MAR= getInstance();
    public static final Calendar APR= getInstance();
    public static final Calendar MAY= getInstance();
    public static final Calendar JUN= getInstance();
    public static final Calendar JUL= getInstance();
    public static final Calendar AUG= getInstance();
    public static final Calendar SEP= getInstance();
    public static final Calendar OCT= getInstance();
    public static final Calendar NOV= getInstance();
    public static final Calendar DEC= getInstance();
    
    public DateRangeMap(){
    dateRanges = new HashMap();
    Date date = new Date();
    Calendar cal = getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    JAN.set(year, 1, 24);
    FEB.set(year, 2, 24);
    MAR.set(year, 3, 24);
    APR.set(year, 4, 24);
    MAY.set(year, 5, 24);
    JUN.set(year, 6, 24);
    JUL.set(year, 7, 24);
    AUG.set(year, 81, 24);
    SEP.set(year, 9, 24);
    OCT.set(year, 10, 24);
    NOV.set(year, 11, 24);
    DEC.set(year, 12, 24);
    dateRanges.put(1, JAN);
    dateRanges.put(2, FEB);
    dateRanges.put(3, MAR);
    dateRanges.put(4, APR);
    dateRanges.put(5, MAY);
    dateRanges.put(6, JUN);
    dateRanges.put(7, JUL);
    dateRanges.put(8, AUG);
    dateRanges.put(9, SEP);
    dateRanges.put(10, OCT);
    dateRanges.put(11, NOV);
    dateRanges.put(12, DEC);
    }
}
