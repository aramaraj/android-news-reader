package adalwin.com.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by aramar1 on 5/30/16.
 */
public class NewsExternalClient {
        private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        private static final String IMAGE_URL = "http://www.nytimes.com/";
        //private static final String API_KEY = "6cf601355e4249689117fa77dc282ed6";
        private static final String API_KEY = "4140e249d9a048b19b951a7aca2fc42a";

        private AsyncHttpClient client;
        public NewsExternalClient() {
            client = new AsyncHttpClient();
        }
        public void getNewsResults(RequestParams reqParams, JsonHttpResponseHandler handler) {
            try {

                    client.get(BASE_URL,reqParams,handler);
                    System.out.println(handler.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }
