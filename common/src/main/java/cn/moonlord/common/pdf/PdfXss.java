package cn.moonlord.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;

import java.io.IOException;

public class PdfXss {

    public static void createFile(String fileSavePath){
        try {
            PDDocument document = new PDDocument();
            String javaScript = "app.alert( {cMsg: 'this is an example', nIcon: 3, nType: 0,cTitle: 'PDFBox Javascript example' } );";
            PDActionJavaScript action = new PDActionJavaScript(javaScript);
            document.getDocumentCatalog().setOpenAction(action);
            document.save(fileSavePath);
            document.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void checkFile(String fileSavePath){
    }

}
