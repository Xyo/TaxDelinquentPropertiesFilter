import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 4/9/2017.
 */
public class FileParser {



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

    public static List<Key> parseKeyFile(File keyFile){

        String line;
        List<Key> keys = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(keyFile));
            while((line=br.readLine()) != null
                    && !line.isEmpty()){
                String[] parts = line.split(" ");
                keys.add( new Key(parts[0], parts[1], parts[2]));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return keys;
    }

    public static File[] getPDFFiles(){
        File f = new File(getFilePath());
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".pdf");
            }
        });
        return matchingFiles;
    }

    public static File getKeyFile(){
        File f = new File(getFilePath());
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.startsWith("key")) && name.endsWith(".txt");
            }
        });
        return matchingFiles[0];
    }


    public static String getFilePath(){
        String executionPath = "";
        try{
            executionPath = System.getProperty("user.dir");
            System.out.print(executionPath.replace("\\", "/"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return executionPath;
    }

}
