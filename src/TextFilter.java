import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jeremy on 4/9/2017.
 */
public class TextFilter {


    public static void main(String args[]) {
        File keyFile = FileParser.getKeyFile();
        File[] pdfs = FileParser.getPDFFiles();

        // get entries out of key file
        List<Key> keys = FileParser.parseKeyFile(keyFile);

        List<Entry> matches = new ArrayList<>();
        for(File file : pdfs) {
            List<Entry> entries = processPDF(file);
            for(Entry e : entries){
                for(Key k : keys){
                    if(e.code.substring(0,4).equals(k.key)
                            && e.dollars < (double)k.high
                            && e.dollars > (double)k.low){
                        matches.add(e);
                    }
                }
            }
            createMatchesFile(file.getName(), matches);
        }
    }


    private static void createMatchesFile(String pdfFileName, List<Entry> matches){
        String fileName = "MatchesFor-" + pdfFileName.substring(0, pdfFileName.length()-4) + ".csv";
        try{
            CSVWriter writer = new CSVWriter(new FileWriter(fileName), '\t');
            writer.writeNext("Name,Key,Amount".split(","));
            for(Entry entry : matches){
                String[] toWrite = {entry.name, "C"+entry.code, String.valueOf(entry.dollars)};
                writer.writeNext(toWrite);
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static List<Entry> processPDF(File file){

        List<Entry> entries = new ArrayList<>();
        try {
            String fullText = FileParser.parsePDF(file);
            String[] lines = fullText.split("\\r?\\n");

            //int start = getStartOfData(lines);
            int start = 0;
            try {
                for (int i = start; i < lines.length; i++) {
                    // EOF
                    if(lines[i].contains("THIS IS TO NOTIFY EACH OF YOU")){
                        break;
                    }else {
                        Entry newEntry = parseEntry(lines[i]);
                        if(newEntry != null){
                            entries.add(newEntry);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return entries;
    }

    private static Entry parseEntry(String line){
        String[] nameParts = line.split("\\$");
        String name = nameParts[0];

        String code = "";
        String dollars = "";

        Pattern codeP = Pattern.compile("\\d{8}");
        Pattern dollarsP = Pattern.compile("\\$\\d*\\.\\d*");

        Matcher m = codeP.matcher(line);
        if(m.find()){
            code = m.group();
        }else{
            return null;
        }

        Matcher d = dollarsP.matcher(line);
        if(d.find()){
            dollars = d.group();
        }
        if(name != null && !name.isEmpty() && !code.isEmpty() && !dollars.isEmpty() ){
            return new Entry(name, dollars, code);
        }
        return null;
    }

    private static int getStartOfData(String[] lines){
        for(int i=0; i<lines.length; i++){
            if(lines[i].contains("Amt:") && lines[i].contains("Entry:)")){
                return i+1;
            }
        }
        return -1;
    }

}
