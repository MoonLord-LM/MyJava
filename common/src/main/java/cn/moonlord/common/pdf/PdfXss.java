package cn.moonlord.common.pdf;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;

import java.io.File;
import java.io.IOException;

public class PdfXss {

    public static void createNormalFile(String fileSavePath) {
        try {
            new File((new File(fileSavePath)).getParent()).mkdirs();
            PDDocument document = new PDDocument();
            document.save(fileSavePath);
            PDPage page = new PDPage();
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 16);
            content.showText("COSName{JavaScript}");
            content.endText();
            document.addPage(page);
            document.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void createJavaScriptFile(String fileSavePath) {
        try {
            new File((new File(fileSavePath)).getParent()).mkdirs();
            PDDocument document = new PDDocument();
            String javaScript = "app.alert( {cMsg: 'this is an example', nIcon: 3, nType: 0, cTitle: 'PDFBox Javascript example' } );";
            PDActionJavaScript action = new PDActionJavaScript(javaScript);
            document.getDocumentCatalog().setOpenAction(action);
            document.save(fileSavePath);
            document.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean checkContainsJavaScript(String fileSavePath) {
        try {
            PDDocument document = PDDocument.load(new File(fileSavePath));
            PDDocumentCatalog pdDocumentCatalog = document.getDocumentCatalog();
            COSDictionary root = pdDocumentCatalog.getCOSObject();
            if (root == null) {
                return false;
            }
            if(root.toString().contains("COSName{JavaScript}")){
                // return true;
            }
            for (COSBase value: root.getValues()) {
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return false;
    }

}
