package com.bank_application_system.services;

import com.bank_application_system.dto.EmailDetails;
import com.bank_application_system.entity.Transaction;
import com.bank_application_system.entity.User;
import com.bank_application_system.repository.TransactionRepository;
import com.bank_application_system.repository.UserRepository;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BankStatement {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    private static final String FILE =  "c:\\Users\\Sai Vinay Goud\\Documents\\Mystatement.pdf";

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        List<Transaction> transactionList = transactionRepository
        .findAll()
        .stream()
        .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
        .filter(transaction -> transaction.getCreatedAt().isEqual(start))
        .filter(transaction -> transaction.getCreatedAt().isEqual(end))
        .toList();

        try {
            User user = userRepository.findByAccountNumber(accountNumber);
            String customerAccount = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
            OutputStream outputStream = new FileOutputStream(FILE);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            log.info("setting size of document");
            Document document = new Document(pdfDocument);
            DeviceRgb blueColor = new DeviceRgb(0, 0, 255);

            Table bankInfoTable = new Table(1);
            bankInfoTable.addCell(new Cell().add(new Paragraph("The Java Coding Bank App")).setBorder(null).setBackgroundColor(blueColor).setPadding(20f));
            bankInfoTable.addCell(new Cell().add(new Paragraph("4-444, Hyderabad, India")).setBorder(null));

            Table statementInfo = new Table(2);
            statementInfo.addCell(new Cell().add(new Paragraph("Start Date: " + startDate)).setBorder(null));
            statementInfo.addCell(new Cell().add(new Paragraph("STATEMENT OF ACCOUNT")).setBorder(null));
            statementInfo.addCell(new Cell().add(new Paragraph("End Date: " + endDate)).setBorder(null));
            statementInfo.addCell(new Cell().add(new Paragraph("Customer Name: " + customerAccount)).setBorder(null));
            statementInfo.addCell(new Cell().setBorder(null));
            statementInfo.addCell(new Cell().add(new Paragraph("Customer Address " + user.getAddress())).setBorder(null));

            Table transactionsTable = new Table(4);
            transactionsTable.addCell(new Cell().add(new Paragraph("DATE")).setBorder(null).setBackgroundColor(blueColor));
            transactionsTable.addCell(new Cell().add(new Paragraph("TRANSACTION TYPE")).setBorder(null).setBackgroundColor(blueColor).setBorder(Border.NO_BORDER));
            transactionsTable.addCell(new Cell().add(new Paragraph("TRANSACTION AMOUNT")).setBorder(null).setBackgroundColor(blueColor).setBorder(Border.NO_BORDER));
            transactionsTable.addCell(new Cell().add(new Paragraph("STATUS")).setBorder(null).setBackgroundColor(blueColor).setBorder(Border.NO_BORDER));
            
            transactionList.forEach(transaction -> {
                transactionsTable.addCell(new Cell().add(new Paragraph(transaction.getCreatedAt().toString())));
                transactionsTable.addCell(new Cell().add(new Paragraph(transaction.getTransactionType())));
                transactionsTable.addCell(new Cell().add(new Paragraph(transaction.getAmount().toString())));
                transactionsTable.addCell(new Cell().add(new Paragraph(transaction.getStatus())));
                
            });


            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionsTable);

            document.close();
            pdfDocument.close();
            outputStream.close(); 

            EmailDetails emailDetails = EmailDetails.builder()
            .recipient(user.getEmail())
            .subject("STATEMENT OF ACCOUNT")
            .messageBody("Kindly find your requested account statement attached")
            .attachment(FILE)
            .build();

            emailService.sendEmailWithAttachment(emailDetails);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactionList;
    }

}
