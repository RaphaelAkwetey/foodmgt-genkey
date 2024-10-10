package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.dto.BillDto;
import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.model.impl.MonthlyReport;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.repository.dao.api.ReportDAO;
import com.genkey.foodmgt.repository.dao.api.TransactionRepository;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;



import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class ReportService {



    List<MonthlyReport> AllUsers;

    public ReportService(List<MonthlyReport> allUsers) {
        AllUsers = allUsers;
    }

    // reports

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.GREEN);

        Paragraph p = new Paragraph("Monthly Bill", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("User Spending", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Company Bill", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        double sum = 0.0;
        for (MonthlyReport user : AllUsers) {
            table.addCell(user.getFirstname());
            table.addCell(String.valueOf(user.getSpending()));
            table.addCell(String.valueOf(user.getCompany()));

            sum += user.getCompany();
        }
        table.addCell("");
        table.addCell("");
        table.addCell("Total=" + sum);

    }
}
