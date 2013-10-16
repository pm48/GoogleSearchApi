package com.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ImageSearchFilter extends Activity {
	EditText etColor;
	EditText etImgSize;
	EditText etImgSite;
	EditText etImgType;
	Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search_filter);
		setupViews();
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("color", etColor.getText().toString());
				data.putExtra("size", etImgSize.getText().toString());
				data.putExtra("type", etImgType.getText().toString());
				data.putExtra("site", etImgSite.getText().toString());
				setResult(RESULT_OK, data);
				finish();

			}
		});
	}

	private void setupViews() {
		etColor = (EditText) findViewById(R.id.etColor);
		etImgSite = (EditText) findViewById(R.id.etFilter);
		etImgSize = (EditText) findViewById(R.id.etImageSize);
		etImgType = (EditText) findViewById(R.id.etImgType);
		btnSave = (Button) findViewById(R.id.btnSave);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search_filter, menu);
		return true;
	}

}
