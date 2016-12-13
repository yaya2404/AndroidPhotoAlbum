package group26.photoalbum;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utility.Photo;
import utility.PhotoAlbum;
import utility.PhotosAdapter;
import utility.SerializeData;

/**
 * Created by matth on 12/10/2016.
 */

public class ShowAlbum extends AppCompatActivity{

    private PhotoAlbum album;
    private GridView gridView;
    private PhotosAdapter photoAdapter;
    private static final int RESULT_LOAD_IMG = 1;
    protected static final String PHOTO_INDEX_KEY = "photo_index";
    private static final int ALBUM_PAGE_KEY = 3;
    private int index;
    protected ContextMenu imgMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopage);
        Bundle bundle = getIntent().getExtras();
        index = SerializeData.getAlbum(bundle.getString(photoalbumhomescreen.ALBUM_NAME_KEY));
        album = SerializeData.getData().get(SerializeData.getAlbum(bundle.getString(photoalbumhomescreen.ALBUM_NAME_KEY)));
        setTitle(album.toString());
        gridView = (GridView) findViewById(R.id.gridView);
        photoAdapter = new PhotosAdapter(this, album, ALBUM_PAGE_KEY);
        gridView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.addphoto:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photopage, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            Photo item = new Photo(imgDecodableString);
            item.setImage();
            //album.getPhotos().add(item);
            SerializeData.getData().get(index).getPhotos().add(item);
            SerializeData.writeData();
            photoAdapter.notifyDataSetChanged();

        }
    }

}
