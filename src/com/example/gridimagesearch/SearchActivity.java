package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	Button btnSearch;
	GridView gvResults;

	ImageResultsArrayAdapter imageAdapter;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	private String imgcolor = null;
	private String imgsz = null;
	private String type = null;
	private String site = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter(this, imageResults);

		gvResults.setAdapter(imageAdapter);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onImageSearch(v, 0);
				gvResults.setOnScrollListener(new EndlessScrollListener() {
					@Override
					public void loadMore(int page, int totalItemsCount) {
						View v = getCurrentFocus();
						onImageSearch(v, totalItemsCount);

					}
				});

			}
		});

		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long arg3) {
				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);

			}
		});

	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);

	}

	public void onFilterSearch(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), ImageSearchFilter.class);

		startActivityForResult(i, 5);
		// Toast.makeText(this, "FilterPage " , Toast.LENGTH_LONG).show();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.imgsearch:
			Intent i = new Intent(getApplicationContext(),
					ImageSearchFilter.class);
			startActivityForResult(i, 5);
			Toast.makeText(this, "FilterPage ", Toast.LENGTH_LONG).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 5 && resultCode == RESULT_OK) {
			imgcolor = data.getExtras().getString("color");
			imgsz = data.getExtras().getString("size");
			type = data.getExtras().getString("type");
			site = data.getExtras().getString("site");

		}
	}

	public void onImageSearch(View v, int time) {
		String query = etQuery.getText().toString();
		// Toast.makeText(this, "Search for " + query,
		// Toast.LENGTH_LONG).show();
		AsyncHttpClient async = new AsyncHttpClient();
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
				+ "start=" + time + "&v=1.0&q=" + Uri.encode(query);
		if (!imgcolor.isEmpty()) {
			url = url + "&imgcolor=" + imgcolor;
		}
		if (!imgsz.isEmpty()) {
			url = url + "&imgsz=" + imgsz;
		}
		if (!type.isEmpty()) {
			url = url + "&imgtype=" + type;
		}
		if (!site.isEmpty()) {
			url = url + "&as_sitesearch=" + site;
		}
		Toast.makeText(this, "Final url is " + url, Toast.LENGTH_LONG).show();
		async.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult
							.fromJsonArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
