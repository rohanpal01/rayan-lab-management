package com.example.rayanlabmanagement.reports;

import com.example.rayanlabmanagement.entity.Report;
import com.example.rayanlabmanagement.entity.Result;
import com.example.rayanlabmanagement.entity.ResultParameter;
import com.example.rayanlabmanagement.repository.ReportRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import com.lowagie.text.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;

import java.awt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepo;

    public byte[] generatePdf(Long id) throws Exception {

        Report report = reportRepo.findById(id).orElseThrow();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        writer.setPageEvent(new FooterEvent()); // ✅ ADD THIS
        doc.open();

        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);

// Column width: logo small, text big
        headerTable.setWidths(new float[]{5});

        Image logo = Image.getInstance(getClass().getResource("/static/Swati-Lab-Logo.png"));
        logo.scaleToFit(doc.getPageSize().getWidth(), 120);

        logo.setWidthPercentage(100); // 🔥 important

        PdfPCell logoCell = new PdfPCell();
        logoCell.addElement(logo);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setVerticalAlignment(Element.ALIGN_CENTER);

        headerTable.addCell(logoCell);


        doc.add(headerTable);
        doc.add(new Paragraph(" "));


        // ================= PATIENT INFO BOX =================
        Font normal = new Font(Font.HELVETICA, 9);
        Font bold = new Font(Font.HELVETICA, 9, Font.BOLD);

        PdfPTable infoTable = new PdfPTable(3);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{3, 3, 1});

        // -------- LEFT SIDE --------
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);

        leftCell.addElement(new Paragraph("Reg. No      : " +
                (report.getPatient().getUniquePatientId() != null
                        ? report.getPatient().getUniquePatientId() : "N/A"), normal));

        leftCell.addElement(new Paragraph("Name         : " + report.getPatient().getName(), normal));

        leftCell.addElement(new Paragraph("Age/Gender   : " +
                report.getPatient().getAge() + "/" + report.getPatient().getGender(), normal));

        leftCell.addElement(new Paragraph("Mob No.   : " +
                report.getPatient().getContact(), normal));

        leftCell.addElement(new Paragraph("Ref. By      : " + report.getPatient().getExaminedBy(), normal));

        leftCell.addElement(new Paragraph("Investigation(s): " +
                report.getResults().stream()
                        .map(r -> r.getTest().getTestName())
                        .collect(Collectors.joining(", ")), normal));

        infoTable.addCell(leftCell);

        // -------- RIGHT SIDE --------
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);

        rightCell.addElement(new Paragraph("Collected at : " + LocalDateTime.now(), normal));
        rightCell.addElement(new Paragraph("Reported at  : " + report.getCreatedAt(), normal));

        infoTable.addCell(rightCell);

        // -------- QR CODE --------
        String qrText = "Patient :" +  report.getPatient().getName() + "-------- SWATI DIAGNOSTIC CENTER";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 100, 100);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        Image qrImage = Image.getInstance(pngOutputStream.toByteArray());

        PdfPCell qrCell = new PdfPCell(qrImage);
        qrCell.setBorder(Rectangle.NO_BORDER);
        qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        infoTable.addCell(qrCell);

        // -------- WRAP IN BORDER BOX --------
        PdfPCell wrapper = new PdfPCell(infoTable);
        wrapper.setPadding(10);
        wrapper.setBorder(Rectangle.BOX);

        PdfPTable outerTable = new PdfPTable(1);
        outerTable.setWidthPercentage(100);
        outerTable.addCell(wrapper);

        doc.add(outerTable);
        doc.add(new Paragraph(" "));

// LOOP TESTS
        for (Result result : report.getResults()) {

            Paragraph testCategory = new Paragraph(result.getTest().getCategory() + " EXAMINATION\n");
            testCategory.setAlignment(Element.ALIGN_CENTER);
            doc.add(testCategory);

            doc.add(new Paragraph("Test: " + result.getTest().getTestName()));



            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.addCell("Parameter");
            table.addCell("Value");
            table.addCell("Unit");
            table.addCell("Reference");

            for (ResultParameter rp : result.getParameters()) {
                table.addCell(rp.getParameter().getParamName());
                table.addCell(rp.getValue());
                table.addCell(rp.getParameter().getUnit());
                table.addCell(
                        rp.getParameter().getRefMin() + " - " + rp.getParameter().getRefMax()
                );
            }

            doc.add(table);
            doc.add(new Paragraph(" "));
        }

// REMARK
        doc.add(new Paragraph("Remark: " + report.getRemark()));


        doc.close();


        return out.toByteArray(); // ✅ THIS is your PDF
    }
}