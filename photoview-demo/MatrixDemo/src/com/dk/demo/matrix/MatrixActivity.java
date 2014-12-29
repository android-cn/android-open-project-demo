package com.dk.demo.matrix;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MatrixActivity extends Activity {
	public static final String KEY_ACTION = "KEY_ACTION";
	public static final int ACTION_ROTATE = 101;
	public static final int ACTION_TRANSLATE = 102;
	public static final int ACTION_SCALE = 103;
	public static final int ACTION_SKEW = 104;

	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrix);
		mImageView = (ImageView) findViewById(R.id.image);
		mImageView.setScaleType(ScaleType.MATRIX);
		updateMatrix(getIntent().getIntExtra(KEY_ACTION, -1));
	}

	private void updateMatrix(int action) {
		Matrix matrix = new Matrix();

		switch (action) {
		case ACTION_ROTATE:
			matrix.postRotate(45, 450, 450);
			break;
		case ACTION_TRANSLATE:
			matrix.postTranslate(250, 250);
			break;
		case ACTION_SCALE:
			matrix.postScale(1 / 2f, 2, 100, 100);
			break;
		case ACTION_SKEW:
			matrix.postSkew(0.2f, 0.2f, 100, 100);
			break;
		default:
			break;
		}

		mImageView.setImageMatrix(matrix);
	}
}
