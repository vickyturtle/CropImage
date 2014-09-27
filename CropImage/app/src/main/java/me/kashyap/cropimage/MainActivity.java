package me.kashyap.cropimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import me.kashyap.croplib.crop.AspectRatio;
import me.kashyap.croplib.crop.CropImageView;


public class MainActivity extends Activity {

    private static final int REQUEST_CAMERA = 0x1;
    private static final int REQUEST_GALLERY = 0x2;
    private CropImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (CropImageView) findViewById(R.id.crop_image);
        imageView.setCropDrawable(R.drawable.crop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_camera) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
            return true;
        } else if ( id == R.id.action_new_pic) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, REQUEST_GALLERY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CAMERA) {
                Log.d("MainActvity", "camera result");
            } else if (requestCode == REQUEST_GALLERY) {
                imageView.setImageURI(data.getData());
                imageView.setAspectRatio(new AspectRatio(16, 9));
            }
        }
    }
}
