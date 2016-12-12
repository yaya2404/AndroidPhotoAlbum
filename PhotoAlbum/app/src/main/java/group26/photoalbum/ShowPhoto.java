package group26.photoalbum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import utility.Photo;

/**
 * Created by matth on 12/12/2016.
 */

public class ShowPhoto extends AppCompatActivity{

    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_display);
        Bundle bundle = getIntent().getExtras();
        photo = (Photo) bundle.getSerializable(ShowAlbum.PHOTO_INDEX_KEY);
        photo.setImage();
        ImageView view = (ImageView) findViewById(R.id.displayimage);
        TextView textview = (TextView) findViewById(R.id.tagtext);
        view.setImageBitmap(photo.getImage());
        textview.setText(photo.getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tagphoto, menu);
        return true;
    }
}
