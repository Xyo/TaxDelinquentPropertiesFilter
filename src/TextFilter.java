import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Created by Jeremy on 4/9/2017.
 */
public class TextFilter {
    private String[] keyNumbers;
    File f = new File("C:\\Users\\Jeremy\\Desktop\\Files");
    File[] matchingFiles = f.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".pdf");
        }
    });


    TextFilter(String text){
        try{
            if(text == null || text.isEmpty()) return;
            String[] lines = text.split("\\r?\\n");

            doThing(lines);
        }catch(Exception e){
            System.out.println("ERROR: CANNOT SPLIT TEXT");
        }
    }

    private void doThing(String[] lines){
        String currLine = "";
        for(int i=0; i<lines.length; i++){
            if(lines[i].equals("Amt: Key:)"){

            }
        }

        for(String line : lines){

        }
    }

    private File getKeyFile(){
        File f = new File("C:\\Users\\Jeremy\\Desktop\\Files");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return (name.beginsWith("key")) && name.endsWith(".txt");
            }
        });

    }


}
