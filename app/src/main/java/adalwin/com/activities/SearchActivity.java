package adalwin.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adalwin.com.adapters.ArticleArrayAdapter;
import adalwin.com.models.Article;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etSearch;
    GridView gvResults;
    Button btnSearch;
    ArticleArrayAdapter articleArrayAdapter;
    ArrayList<Article> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupviews();


    }

    public void setupviews(){
        etSearch= (EditText)findViewById(R.id.etSearch);
        gvResults = (GridView)findViewById(R.id.gvResults);
        btnSearch = (Button)findViewById(R.id.btSearch);
        articles = new ArrayList<>();
        articleArrayAdapter = new ArticleArrayAdapter(this,articles);
        gvResults.setAdapter(articleArrayAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //create an intent
                //start  the activity
                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                Article article = articles.get(position);
                intent.putExtra("url",article.getNewsUrl());
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = etSearch.getText().toString();
                //GEt the movies we want to Display

                String url="https://api.nytimes.com/svc/search/v2/articlesearch.json";
                AsyncHttpClient client=new AsyncHttpClient();
                RequestParams reqParams=new RequestParams();

                reqParams.put("api-key","6cf601355e4249689117fa77dc282ed6");
                reqParams.put("q",searchQuery);
                reqParams.put("sort","newest");
                reqParams.put("page",0);


                client.get(url,reqParams,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        JSONObject searchObject = null;
                        //ArrayList<Article> articleList = new ArrayList<Article>();
                        Log.d("DEBUG",response.toString());
                        JSONArray searchResults = null;
                        try {
                            searchResults=response.getJSONObject("response").getJSONArray("docs");
                            articleArrayAdapter.addAll(Article.fromJsonArray(searchResults));
                            Log.d("DEBUG",articleArrayAdapter.toString());
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
        }));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

