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
        p.add(formatReceipt(books));
        document.add(p);
        document.close();
    }

    private String formatReceipt(ArrayList<BorrowBook> books){
        String returnVal = "";

        int c = 1;
        for(BorrowBook book: books){

            returnVal += "["+c+"] Book title:  " + book.getTitle() + "\n" +
                    "Book author:  " + book.getAuthor() + "\n" +
                    "Borrow date:  " + book.getBorrowDate() + "\n" +
                    "Return date:  " + book.getReturnDate() + "\n\n";
            c++;
        }
        return returnVal;
    }
}