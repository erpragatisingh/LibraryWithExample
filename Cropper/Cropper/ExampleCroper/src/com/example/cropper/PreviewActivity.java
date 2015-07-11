package com.example.cropper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pragati.cropper.CropImageView;

public class PreviewActivity extends Activity {
	
	private ImageView ivPreview;
	private CropImageView ivPreviewCrop;
	protected Bitmap croppedImage;
	Bitmap bm = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.preview);
		ivPreview = (ImageView) findViewById(R.id.ivPreview);
		
		ivPreviewCrop = (CropImageView) findViewById(R.id.ivPreviewCrop);
//		ivPreviewCrop.setImageBitmap(bm);
		ivPreview.setImageResource(R.drawable.demo_croper);
		ivPreviewCrop.setImageResource(R.drawable.demo_croper);
		
		ImageView ivCrop = (ImageView) findViewById(R.id.ivCrop);
		ivCrop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (ivPreviewCrop.getVisibility() == View.GONE) {
					ivPreview.setVisibility(View.GONE);
					ivPreviewCrop.setVisibility(View.VISIBLE);
				} else {
					croppedImage = ivPreviewCrop.getCroppedImage();
					ivPreview.setImageBitmap(croppedImage);
					ivPreview.setVisibility(View.VISIBLE);
					ivPreviewCrop.setVisibility(View.GONE);
				}

			}
		});
		ivPreview.setVisibility(View.GONE);
		ivPreviewCrop.setVisibility(View.VISIBLE);
	}

}
