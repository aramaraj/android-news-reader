package adalwin.com.models;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aramar1 on 5/30/16.
 * ref:https://github.com/johncarl81/parceler
 *
 */
@Parcel
public class Settings implements Serializable{

    public String sortOrder;
    public String startDate;
    public String newsDesk;

    public List<String> newsOptions;

    public List<String> getNewsOptions() {
        return newsOptions;
    }

    public void setNewsOptions(List<String> newsOptions) {
        this.newsOptions = newsOptions;
    }


    public String getSort() {
        return sortOrder;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDeskValue() {
        return newsDesk;
    }

    public Settings(String sortOrder, String startDate, String newsDesk) {
        this.sortOrder = sortOrder;
        this.startDate = startDate;
        this.newsDesk = newsDesk;
    }

    public Settings() {

    }
    public String toString(){
        return "Date "+ startDate.toString()+ "  sort :" +sortOrder +"newsOptions"+newsOptions;
    }
}
