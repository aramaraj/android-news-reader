package adalwin.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adalwin.com.adapters.ArticleArrayAdapter;
import adalwin.com.models.Article;
import adalwin.com.models.Settings;
import adalwin.com.net.NewsExternalClient;
import adalwin.com.tools.EndlessRecylcerScrollerListener;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String IMAGE_URL = "http://www.nytimes.com/";
    private static final String API_KEY = "6cf601355e4249689117fa77dc282ed6";

    ArticleArrayAdapter articleArrayAdapter;
    ArrayList<Article> articles;
    private Settings settings;
    private final int SETTING_REQUEST_CODE=1000;
    NewsExternalClient client;
    ListView lvView;
    RequestParams reqParams = new RequestParams();
    String search;
    GridView gvResults;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //lvView = (ListView)findViewById(R.id.lvView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        gvResults = (GridView)findViewById(R.id.gvResults);

        setSupportActionBar(toolbar);
        ActionBar menu = getSupportActionBar();
        menu.setLogo(R.mipmap.ic_launcher_news);
        menu.setDisplayUseLogoEnabled(true);
        settings = new Settings();
        articles = new ArrayList<Article>();
        articleArrayAdapter = new ArticleArrayAdapter(this,articles);
        gvResults.setAdapter(articleArrayAdapter);

        /*reqParams.put("q", "hollywood");
        retrievePage(0);*/

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                Article article = articles.get(position);
                intent.putExtra("url",article.getNewsUrl());
                startActivity(intent);
            }
        });
       /* lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                Article article = articles.get(position);
                intent.putExtra("url",article.getNewsUrl());
                startActivity(intent);
            }
        });
*/
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        gvResults.setOnScrollListener(new EndlessRecylcerScrollerListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                advancedSearch(page);
                return true;
            }
            @Override
            public int getFooterViewType() {
                return 0;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                articles.clear();
                articleArrayAdapter.notifyDataSetChanged();
                reqParams.put("q",query);
                search=query;
                retrievePage(0);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivityForResult(intent, SETTING_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                settings = (Settings)data.getSerializableExtra("settings");
                Log.d("DEBUG --> ONACTIVITY", settings.toString());

            }
        }
    }

    private String setNewsOptionsParams(List<String> newsOptionsList) {
        Log.d("SearchActivity", newsOptionsList.toString());
        StringBuilder sb = new StringBuilder("news_desk:(");
        String newsDesk="";
        if (!newsOptionsList.isEmpty()) {

            for (String n : newsOptionsList) {
                sb.append(" \"").append(n).append("\"");
            }
            sb.append(" )");
            newsDesk= sb.toString();
            Log.d("SearchActivity", newsDesk);
        }
        return newsDesk;
    }


    private String formatDate(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date);
    }

    public void retrievePage(int page) {

        reqParams.put("api-key", API_KEY);
        reqParams.put("page", 0);
        client=new NewsExternalClient();

        client.getNewsResults(reqParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("DEBUG",response.toString());
                JSONArray searchResults = null;
                try {
                    searchResults=response.getJSONObject("response").getJSONArray("docs");
                    articleArrayAdapter.clear();
                    articleArrayAdapter.addAll(Article.fromJsonArray(searchResults));
                    articleArrayAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", articles.toString());
                }catch(JSONException je){
                    je.printStackTrace();

                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void advancedSearch(int offset) {
        reqParams.put("api-key", API_KEY);
        reqParams.put("page", offset);
        if(search!=null && !search.isEmpty()) {
            reqParams.put("q", search);
        }
        else {
            reqParams.put("q", "news");
        }

        if(settings != null){

            reqParams.put("begin_date", settings.getStartDate());
            reqParams.put("sort", settings.getSort());
            reqParams.put("fq",settings.getDeskValue());
        }
        client.getNewsResults(reqParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("DEBUG",response.toString());
                JSONArray searchResults = null;
                try {
                    searchResults=response.getJSONObject("response").getJSONArray("docs");
                    articleArrayAdapter.addAll(Article.fromJsonArray(searchResults));
                    articleArrayAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", articles.toString());
                }catch(JSONException je){
                    je.printStackTrace();

                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }



}

