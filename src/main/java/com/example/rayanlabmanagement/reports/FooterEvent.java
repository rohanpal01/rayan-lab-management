package com.example.rayanlabmanagement.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import java.awt.*;

public class FooterEvent extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfPTable footer = new PdfPTable(1);
        footer.setTotalWidth(document.getPageSize().getWidth() - 72); // margins

        Font titleFont = new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0, 102, 204));
        Font noteFont = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.DARK_GRAY);

        PdfPCell labName = new PdfPCell(new Phrase("SWATI DIAGNOSTIC CENTER", titleFont));
        labName.setBorder(Rectangle.NO_BORDER);
        labName.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell note = new PdfPCell(new Phrase(
                "Note: This report is to help clinic for better patient management. This is not valid for medico-legal purpose.",
                noteFont
        ));
        note.setBorder(Rectangle.NO_BORDER);
        note.setHorizontalAlignment(Element.ALIGN_CENTER);

        footer.addCell(labName);
        footer.addCell(note);

        footer.writeSelectedRows(
                0, -1,
                36,
                50,   // Y position (bottom spacing)
                writer.getDirectContent()
        );
    }
}