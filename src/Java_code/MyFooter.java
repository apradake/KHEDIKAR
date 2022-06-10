/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Java_code;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 *
 * @author radake_a
 */

public class MyFooter extends PdfPageEventHelper
{

    Font ffont = new Font(Font.ITALIC);

    public void onEndPage(PdfWriter writer, Document document) 
    {
        PdfContentByte cb = writer.getDirectContent();
      
        Phrase header = new Phrase("TAX INVOICE", ffont);    //header part
        Phrase footer = new Phrase("-------------------------------------------------------------------------", ffont);          //footer part
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
    }
}