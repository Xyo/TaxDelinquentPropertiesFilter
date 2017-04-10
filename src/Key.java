/**
 * Created by Jeremy on 4/9/2017.
 */
public class Key {

    int low;
    int high;
    String key;

    Key( String key, String low, String high){
        this.key = key;
        try{
            this.low = Integer.parseInt(low);
            this.high = Integer.parseInt(high);
        }catch(Exception e){

        }
    }
}
