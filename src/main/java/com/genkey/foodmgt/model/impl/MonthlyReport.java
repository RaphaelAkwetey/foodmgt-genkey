package com.genkey.foodmgt.model.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report", schema="public")
@Getter
@Setter
//@ToString
@RequiredArgsConstructor

public class MonthlyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String firstname;

    private double spending;

    private double company;

    private LocalDate localDate;

    public MonthlyReport(String firstname, double spending, double company, LocalDate localDate) {
        this.firstname = firstname;
        this.spending = spending;
        this.company = company;
        this.localDate = localDate;
    }
}
