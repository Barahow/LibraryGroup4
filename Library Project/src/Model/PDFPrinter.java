package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PDFPrinter {




    public void createBorrowedBookPDF(String path, ArrayList<BorrowBook> books) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Paragraph p = new Paragraph();
        String test = "Borrowed Harry Potter until 25th of may";
        p.add(test);
        document.add(p);
        document.close();
    }

}