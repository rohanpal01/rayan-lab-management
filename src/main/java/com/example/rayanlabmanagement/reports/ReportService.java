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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepo;

    public byte[] generatePdf(Long id, boolean prePrinted) throws Exception {

        Report report = reportRepo.findById(id).orElseThrow();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // ================= DOCUMENT SETUP =================
        Document doc;
        if (prePrinted) {
            // Adjust these margins based on your printed sheet
            doc = new Document(PageSize.A4, 50, 36, 160, 120);
        } else {
            doc = new Document(PageSize.A4, 36, 36, 36, 36);
        }

        PdfWriter writer = PdfWriter.getInstance(doc, out);

        // Footer only for normal mode
        if (!prePrinted) {
            writer.setPageEvent(new FooterEvent());
        }

        doc.open();

        // ================= HEADER (ONLY NORMAL MODE) =================
        if (!prePrinted) {

            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);

            Image logo = Image.getInstance(getClass().getResource("/static/Swati-Lab-Logo.png"));
            logo.scaleToFit(doc.getPageSize().getWidth(), 120);
            logo.setWidthPercentage(100);

            PdfPCell logoCell = new PdfPCell();
            logoCell.addElement(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            headerTable.addCell(logoCell);
            doc.add(headerTable);

            doc.add(new Paragraph(" "));
        }

        // ================= PATIENT INFO BLOCK =================
        Font normal = new Font(Font.HELVETICA, 9);

        PdfPTable infoTable = new PdfPTable(3);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{4, 3, 2});

        // -------- LEFT --------
        PdfPCell left = new PdfPCell();
        left.setBorder(Rectangle.NO_BORDER);

        left.addElement(new Paragraph("Reg. No      : " +
                (report.getPatient().getUniquePatientId() != null
                        ? report.getPatient().getUniquePatientId() : "N/A"), normal));

        left.addElement(new Paragraph("Name         : " + report.getPatient().getName(), normal));

        left.addElement(new Paragraph("Age/Gender   : " +
                report.getPatient().getAge() + "/" + report.getPatient().getGender(), normal));

        left.addElement(new Paragraph("Mob No.      : " +
                report.getPatient().getContact(), normal));

        left.addElement(new Paragraph("Ref. By      : " +
                report.getPatient().getExaminedBy(), normal));

        left.addElement(new Paragraph("Investigation(s): " +
                report.getResults().stream()
                        .map(r -> r.getTest().getTestName())
                        .collect(Collectors.joining(", ")), normal));

        infoTable.addCell(left);

        // -------- RIGHT --------
        PdfPCell right = new PdfPCell();
        right.setBorder(Rectangle.NO_BORDER);

        right.addElement(new Paragraph("Collected at : " + LocalDate.now(), normal));
        right.addElement(new Paragraph("Reported at  : " + report.getCreatedAt(), normal));

        infoTable.addCell(right);

        // -------- QR CODE (ALWAYS) --------
        String qrText = "Patient: " + report.getPatient().getName() + "--------SWATI DIAGNOSTIC CENTER";

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(qrText, BarcodeFormat.QR_CODE, 120, 120);

        ByteArrayOutputStream png = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", png);

        Image qrImg = Image.getInstance(png.toByteArray());
        qrImg.scaleToFit(100, 100);

        PdfPCell qrCell = new PdfPCell(qrImg);
        qrCell.setBorder(Rectangle.NO_BORDER);
        qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        qrCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        infoTable.addCell(qrCell);

        // -------- WRAPPER (BORDER BOX) --------
        PdfPCell wrapper = new PdfPCell(infoTable);
        wrapper.setPadding(10);
        wrapper.setBorder(Rectangle.BOX);
        wrapper.setBorderWidth(1.2f);

        PdfPTable outer = new PdfPTable(1);
        outer.setWidthPercentage(100);
        outer.addCell(wrapper);

        doc.add(outer);
        doc.add(new Paragraph(" "));

        // ================= TESTS =================
        for (Result result : report.getResults()) {

            // CATEGORY TITLE
            Paragraph category = new Paragraph(
                    result.getTest().getCategory().toUpperCase() + " EXAMINATION"
            );
            category.setAlignment(Element.ALIGN_CENTER);
            category.setSpacingAfter(5);
            doc.add(category);

            // TEST NAME BOX
            Font testFont = new Font(Font.HELVETICA, 11, Font.BOLD);

            PdfPCell testCell = new PdfPCell(
                    new Phrase("Test: " + result.getTest().getTestName(), testFont)
            );
            testCell.setPadding(6);
            testCell.setBackgroundColor(new Color(230, 230, 230));

            PdfPTable testTable = new PdfPTable(1);
            testTable.setWidthPercentage(25);
            testTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            testTable.setSpacingAfter(5);
            testTable.addCell(testCell);

            doc.add(testTable);

            // RESULT TABLE
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font valueFont = new Font(Font.HELVETICA, 9);

            String[] headers = {"PARAMETER", "VALUE", "UNIT", "REFERENCE"};

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(new Color(200, 200, 200));
                cell.setPadding(7);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            for (ResultParameter rp : result.getParameters()) {

                PdfPCell c1 = new PdfPCell(new Phrase(rp.getParameter().getParamName(), valueFont));
                PdfPCell c2 = new PdfPCell(new Phrase(rp.getValue(), valueFont));
                PdfPCell c3 = new PdfPCell(new Phrase(rp.getParameter().getUnit(), valueFont));
                PdfPCell c4 = new PdfPCell(new Phrase(
                        rp.getParameter().getRefMin() + " - " + rp.getParameter().getRefMax(), valueFont
                ));

                for (PdfPCell c : Arrays.asList(c1, c2, c3, c4)) {
                    c.setPadding(6);
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                }

                table.addCell(c1);
                table.addCell(c2);
                table.addCell(c3);
                table.addCell(c4);
            }

            doc.add(table);
            doc.add(new Paragraph(" "));
        }

        // ================= REMARK =================
        doc.add(new Paragraph("Remark: " + report.getRemark()));

        doc.close();

        return out.toByteArray();
    }
}