package com.example.photoview;

import in.pragati.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PreviewActivity extends Activity {
	
	private PhotoView ivPreview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.preview_photoview);
		ivPreview = (PhotoView) findViewById(R.id.ivPreview);
		
		ivPreview.setImageResource(R.drawable.demo_croper);
	}

}
