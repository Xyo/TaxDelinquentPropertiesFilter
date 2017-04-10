/**
 * Created by Jeremy on 4/9/2017.
 */
public class Entry {
    String name;
    double dollars;
    String code;

    Entry(String name, String dollars, String code){
        this.name = name;
        this.code = code;
        String dollarsCut = dollars.substring(1);
        try{
            this.dollars = Double.parseDouble(dollarsCut);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
