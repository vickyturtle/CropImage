package me.kashyap.cropimage;

import android.app.Activity;
import android.os.Bundle;

import me.kashyap.croplib.crop.CropImageView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CropImageView imageView = (CropImageView) findViewById(R.id.crop_image);
        imageView.setCropDrawable(R.drawable.crop);
    }

}
