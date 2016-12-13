package group26.photoalbum;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import utility.Photo;
import utility.PhotoAlbum;
import utility.SerializeData;
import utility.Tag;

/**
 * Created by matth on 12/12/2016.
 */

public class ShowPhoto extends AppCompatActivity{

    private Photo photo;
    private PhotoAlbum album;
    private TextView textview;
    private int photoindex;
    private static final int TAG_KEY_PERSON  = 0;
    private static final int TAG_KEY_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_display);


        Bundle bundle = getIntent().getExtras();
        photoindex = (int) bundle.get(ShowAlbum.PHOTO_INDEX_KEY);
        album = SerializeData.getData().get(SerializeData.getAlbum(bundle.getString(photoalbumhomescreen.ALBUM_NAME_KEY)));
        photo = album.getPhotos().get(photoindex);
        photo.setImage();

        ImageView view = (ImageView) findViewById(R.id.displayimage);
        view.setImageBitmap(photo.getImage());
        textview = (TextView) findViewById(R.id.tagtext);
        textview.setText(photo.getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tagphoto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.tag:
                final Context tagcontext = this;
                AlertDialog.Builder tagoptions = new AlertDialog.Builder(this);
                tagoptions.setTitle("Tag Options:");
                tagoptions.setItems(R.array.tag_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder taginput = new AlertDialog.Builder(tagcontext);
                        final EditText tagvalue = new EditText(tagcontext);
                        switch(which){
                            case TAG_KEY_LOCATION:
                                taginput.setTitle("Tag Location:");
                                tagvalue.setHint("Enter location");
                                taginput.setNegativeButton("Tag", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String value = tagvalue.getText().toString();
                                        photo.addTag(new Tag("Location",value));
                                        SerializeData.writeData();
                                        textview.setText(photo.getInfo());
                                    }
                                });
                                break;
                            case TAG_KEY_PERSON:
                                taginput.setTitle("Tag Person:");
                                tagvalue.setHint("Enter name");
                                taginput.setNegativeButton("Tag", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String value = tagvalue.getText().toString();
                                        photo.addTag(new Tag("Person",value));
                                        SerializeData.writeData();
                                        textview.setText(photo.getInfo());
                                    }
                                });
                                break;
                        }
                        taginput.setView(tagvalue);

                        taginput.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        taginput.show();
                    }
                });
                tagoptions.show();
                break;
            case R.id.removetag:
                AlertDialog.Builder remTagOptions = new AlertDialog.Builder(this);
                remTagOptions.setTitle("Select Tag to Remove: ");
                remTagOptions.setItems(photo.tagsToStringArray(), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        photo.removeTag(i);
                        SerializeData.writeData();
                        textview.setText(photo.getInfo());
                    }
                });
                remTagOptions.show();
                break;
        }
        return true;
    }
}
