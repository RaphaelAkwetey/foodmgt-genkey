package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.dto.BillDto;
import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.model.impl.MonthlyReport;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.repository.dao.api.ReportDAO;
import com.genkey.foodmgt.repository.dao.api.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonthlyReportService {

    @Autowired
    TransactionRepository transactionRepository;


@Autowired
    ReportDAO reportDAO;


@Autowired
    MetaDAO metaDAO;

// service
  public void CalcSpending(LocalDate sd, LocalDate ed)

    {
        LocalDate yes = LocalDate.now();
        /*LocalDate ahead = yes.plusDays(1);
        LocalDate no = yes.minusDays(25);*/

        // LocalDate yh = yes.plusDays(3);

        Meta meta = metaDAO.findById("345").orElse(null);

        double price = meta.getCurrentFoodPrice();
         List<BillDto> calc = transactionRepository.getOrderSummary(sd, ed);
        for (BillDto i : calc) {

            String username = null;
            double Spending = 0;
            double CompanyBill = 0;
            switch (i.getUserRoles().toString()) {
                case "ADMIN":
                case "USER":
                    Spending = i.getCost();
                    username = i.getUsername();
                    CompanyBill = i.getCost() / 2;
                    break;
                case "INTERN":
                case "SECURITY":
                    Spending = i.getCost();
                    username = i.getUsername();
                    CompanyBill = i.getCost();
                    break;
                case "NSS":
                    Spending = i.getCost();
                    username = i.getUsername();
                    if (i.getCost() > 18 * price) {
                        CompanyBill = i.getCost() - (18 * price);
                    } else {
                        CompanyBill = i.getCost();
                    }
                    break;
                default:
                    System.out.println("not working");

            }

            MonthlyReport newReport = new MonthlyReport(username, Spending, CompanyBill, yes);
            reportDAO.save(newReport);

        }
    }
}
