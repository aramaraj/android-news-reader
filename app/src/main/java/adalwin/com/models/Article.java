package adalwin.com.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aramar1 on 5/26/16.
 */
public class Article {

    String newsUrl;
    String newsThumbImage;
    String newsHeadline;

    public String getNewsThumbImage() {
        return newsThumbImage;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsHeadline() {
        return newsHeadline;
    }


    public Article(JSONObject jsonObject)throws JSONException {
        this.newsUrl = jsonObject.getString("web_url");
        this.newsHeadline = jsonObject.getJSONObject("headline").getString("main");
        JSONArray multimedia = jsonObject.getJSONArray("multimedia");
        if(multimedia.length()>0){
            JSONObject imageJsonObject=multimedia.getJSONObject(0);
            this.newsThumbImage = "http://www.nytimes.com/"+imageJsonObject.getString("url");
        }else{
            this.newsThumbImage = "";
        }

    }

    public static ArrayList<Article> fromJsonArray(JSONArray jsonArray){

        ArrayList<Article> articlesArray=new ArrayList<Article>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                articlesArray.add(new Article(jsonArray.getJSONObject(i)));
            }
            catch(JSONException jse){
                jse.printStackTrace();
            }
        }
        return articlesArray;
    }

    @Override
    public String toString() {
        return "News URL "+newsUrl+"thumbnail"+newsThumbImage+"newsHeadline"+newsHeadline;

    }
}
