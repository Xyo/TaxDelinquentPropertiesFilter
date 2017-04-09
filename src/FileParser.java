import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;

/**
 * Created by Jeremy on 4/9/2017.
 */
public class FileParser {
    public static void main(String args[]) {
        File f = new File("C:\\Users\\Jeremy\\Desktop\\Files");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".pdf");
            }
        });
        if(matchingFiles != null){
            System.out.println("printing file: ");
            for(File file : matchingFiles){
                String result = parsePDF(file);
                System.out.println("PDF FILE HERE: " + result);
            }

        }

    }

    public static String parsePDF(File pdfFile){
        PDDocument pdDoc = null;
        PDFTextStripper stripper = null;

        COSDocument cosDoc = null;
        String parsedText = "";

        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
            parser.parse();

            cosDoc = parser.getDocument();
            stripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            int endPage = pdDoc.getNumberOfPages();
            stripper.setStartPage(1);
            stripper.setEndPage(endPage);
            parsedText = stripper.getText(pdDoc);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (pdDoc != null) {
                    pdDoc.close();
                }
                if (cosDoc != null){
                    cosDoc.close();
                }
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
        return parsedText;
    }


}
