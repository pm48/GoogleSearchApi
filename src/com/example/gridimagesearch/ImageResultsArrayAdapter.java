package com.example.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultsArrayAdapter extends ArrayAdapter<ImageResult>{

	public ImageResultsArrayAdapter(Context context, List <ImageResult>images) {
		super(context, R.layout.item_image_result,images);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
		}else
		{
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
			
		}
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
		
	}

}