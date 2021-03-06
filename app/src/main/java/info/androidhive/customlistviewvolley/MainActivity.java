package info.androidhive.customlistviewvolley;

import info.androidhive.customlistviewvolley.adater.CustomListAdapter;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;


public class MainActivity extends Activity {
	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();

	// Movies json url
	private static final String url = "http://apanews.net/json/apa.json";
	private ProgressDialog pDialog;
	private List<Movie> movieList = new ArrayList<Movie>();
	private ListView listView;
    Button btnHelp;

	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        btnHelp = (Button) findViewById(R.id.action_ai);


		listView = (ListView) findViewById(R.id.list);

		adapter = new CustomListAdapter(this, movieList);
		listView.setAdapter(adapter);
        listView.setClickable(true);






                //recuperation ID ARTICLE
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                        TextView tv = (TextView) arg1.findViewById(R.id.releaseYear);
                        String textvalue = tv.getText().toString();
                        //Object o = listView.getItemAtPosition(position);
                        //Toast.makeText(getApplicationContext(),"Article Selectionnee : "+textvalue, Toast.LENGTH_LONG).show();


                        // Intent i = new Intent(this, ArticleRead.class);
                        //startActivity(i);
                        Intent intent = new Intent(MainActivity.this, ArticleRead.class);
                        startActivity(intent);

                        //Animation Activity
                        overridePendingTransition(R.animator.slidefromrightin, R.animator.slidetoleftout);
                        //Animation Activity
                        intent.putExtra("numero", textvalue);
                        startActivityForResult(intent, 1);

                    }
                });

        //FIN recuperation ID ARTICLE

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Chargement...");
		pDialog.show();

		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json elsoft
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								Movie movie = new Movie();
								movie.setTitle(obj.getString("title"));
								movie.setThumbnailUrl(obj.getString("image"));
								movie.setRating(((Number) obj.get("rating"))
										.doubleValue());
								movie.setYear(obj.getInt("releaseYear"));

								// Genre is json array
								JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}
								movie.setGenre(genre);

								// adding movie to movies array
								movieList.add(movie);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.action_ai){

        Log.d("CLick Sur Button Aide", "elsoft " + url);

        Intent intent = new Intent(MainActivity.this, AideActivity.class);
        startActivity(intent);

        //Animation Activity
        overridePendingTransition(R.animator.slidefromrightin, R.animator.slidetoleftout);

            }
      else if(item.getItemId() == R.id.action_ar){

            Log.d("CLick Sur Button Arabe", "elsoft " + url);

            //Intent intent = new Intent(MainActivity.this, AideActivity.class);
            //startActivity(intent);

            //Animation Activity
            //overridePendingTransition(R.animator.slidefromrightin, R.animator.slidetoleftout);

        }




            else{

            }

            return super.onOptionsItemSelected(item);
            }


}
