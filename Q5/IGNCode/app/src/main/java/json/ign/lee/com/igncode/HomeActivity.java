package json.ign.lee.com.igncode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class HomeActivity extends Activity {
    ImageButton imageButton;
    boolean current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        current=true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        addListenerOnButton();
        TextView title = (TextView) findViewById(R.id.title);
        title.setTextColor(Color.WHITE);
        title.setText("VIDEOS");
        Thread thread = new Thread()
        {
            @Override
            public void run() {

                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet httpGet = new HttpGet("http://ign-apis.herokuapp.com/videos?startIndex=0&count=10");
                httpGet.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                HashMap<Integer, String[]> data = new HashMap<Integer, String[]>();
                String description = "";
                try {
                    HttpResponse response = httpclient.execute(httpGet);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    JSONObject json = new JSONObject(result);
                    JSONArray query = json.getJSONArray("data");
                    String[] entry = {"",""};

                    for(int r=0; r<query.length(); ++r) {
                        description=query.getJSONObject(r).getJSONObject("metadata").getString("title");
                        entry[1]=Integer.toString(query.getJSONObject(r).getJSONObject("metadata").getInt("duration"));
                        entry[0]=description;
                        data.put(r,entry.clone());
                    }
                    final HashMap<Integer, String[]> updateData = data;
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SpecialAdapter iAdapter= new SpecialAdapter(HomeActivity.this, R.layout.listview, R.id.list_content, updateData);
                            //ArrayAdapter<String> ignAdapter = new ArrayAdapter<String>(HomeActivity.this,R.layout.listview,R.id.list_content, update);
                            ListView node = (ListView) findViewById(R.id.videoList);
                            node.setBackgroundColor(Color.WHITE);
                            node.setAdapter(iAdapter);
                        }
                    });

                } catch (Exception e) {
                    System.out.println(e);
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception exception){}
                }



            }
        };
        thread.start();


    }
    public void addListenerOnButton() {

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                 if(!current){
                     TextView title = (TextView) findViewById(R.id.title);
                     title.setTextColor(Color.WHITE);
                     title.setText("VIDEOS");
                     Thread thread = new Thread()
                     {
                         @Override
                         public void run() {

                             DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                             HttpGet httpGet = new HttpGet("http://ign-apis.herokuapp.com/videos?startIndex=0&count=10");
                             httpGet.setHeader("Content-type", "application/json");

                             InputStream inputStream = null;
                             String result = null;
                             HashMap<Integer, String[]> data = new HashMap<Integer, String[]>();
                             String description = "";
                             try {
                                 HttpResponse response = httpclient.execute(httpGet);
                                 HttpEntity entity = response.getEntity();

                                 inputStream = entity.getContent();
                                 // json is UTF-8 by default
                                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                                 StringBuilder sb = new StringBuilder();

                                 String line = null;
                                 while ((line = reader.readLine()) != null)
                                 {
                                     sb.append(line + "\n");
                                 }
                                 result = sb.toString();
                                 JSONObject json = new JSONObject(result);
                                 JSONArray query = json.getJSONArray("data");
                                 String[] entry = {"",""};

                                 for(int r=0; r<query.length(); ++r) {
                                     description=query.getJSONObject(r).getJSONObject("metadata").getString("title");
                                     entry[1]=Integer.toString(query.getJSONObject(r).getJSONObject("metadata").getInt("duration"));
                                     entry[0]=description;
                                     data.put(r,entry.clone());
                                 }
                                 final HashMap<Integer, String[]> updateData = data;
                                 HomeActivity.this.runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         SpecialAdapter iAdapter= new SpecialAdapter(HomeActivity.this, R.layout.listview, R.id.list_content, updateData);
                                         //ArrayAdapter<String> ignAdapter = new ArrayAdapter<String>(HomeActivity.this,R.layout.listview,R.id.list_content, update);
                                         ListView node = (ListView) findViewById(R.id.videoList);
                                         node.setBackgroundColor(Color.WHITE);
                                         node.setAdapter(iAdapter);
                                     }
                                 });

                             } catch (Exception e) {
                                 System.out.println(e);
                             }
                             finally {
                                 try{if(inputStream != null)inputStream.close();}catch(Exception exception){}
                             }
                         }
                     };
                     thread.start();
                     current=true;
                 }
                else{
                     current=false;
                     TextView title = (TextView) findViewById(R.id.title);
                     title.setTextColor(Color.WHITE);
                     title.setText("ARTICLES");
                     Thread thread = new Thread()
                     {
                         @Override
                         public void run() {

                             DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                             HttpGet httpGet = new HttpGet("http://ign-apis.herokuapp.com/articles?startIndex=0&count=10");
                             httpGet.setHeader("Content-type", "application/json");

                             InputStream inputStream = null;
                             String result = null;
                             HashMap<Integer, String[]> data = new HashMap<Integer, String[]>();
                             String description = "";
                             try {
                                 HttpResponse response = httpclient.execute(httpGet);
                                 HttpEntity entity = response.getEntity();

                                 inputStream = entity.getContent();
                                 // json is UTF-8 by default
                                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                                 StringBuilder sb = new StringBuilder();

                                 String line = null;
                                 while ((line = reader.readLine()) != null)
                                 {
                                     sb.append(line + "\n");
                                 }
                                 result = sb.toString();
                                 JSONObject json = new JSONObject(result);
                                 JSONArray query = json.getJSONArray("data");
                                 String[] entry = {"",""};

                                 for(int r=0; r<query.length(); ++r) {
                                     description=query.getJSONObject(r).getJSONObject("metadata").getString("headline");
                                     try{
                                         description+="\n"+ query.getJSONObject(r).getJSONObject("metadata").getString("subHeadline");
                                     } catch (JSONException e) {

                                     }
                                     entry[0]=description;
                                     data.put(r,entry.clone());
                                 }
                                 final HashMap<Integer, String[]> updateData = data;
                                 HomeActivity.this.runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         SpecialAdapter iAdapter= new SpecialAdapter(HomeActivity.this, R.layout.listview, R.id.list_content, updateData);
                                         //ArrayAdapter<String> ignAdapter = new ArrayAdapter<String>(HomeActivity.this,R.layout.listview,R.id.list_content, update);
                                         ListView node = (ListView) findViewById(R.id.videoList);
                                         node.setBackgroundColor(Color.WHITE);
                                         node.setAdapter(iAdapter);
                                     }
                                 });

                             } catch (Exception e) {
                                 System.out.println(e);
                             }
                             finally {
                                 try{if(inputStream != null)inputStream.close();}catch(Exception exception){}
                             }



                         }
                     };
                     thread.start();                 }
            }
        });
    }
    public void modifyList(final String[] items){
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> ignAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, items);
                ListView node = (ListView) findViewById(R.id.videoList);
                node.setAdapter(ignAdapter);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
