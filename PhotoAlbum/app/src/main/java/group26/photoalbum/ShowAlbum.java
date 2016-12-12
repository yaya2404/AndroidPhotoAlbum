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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopage);
        Bundle bundle = getIntent().getExtras();
        album = SerializeData.getData().get(SerializeData.getAlbum(bundle.getString(photoalbumhomescreen.ALBUM_NAME_KEY)));
        setTitle(album.toString());
        gridView = (GridView) findViewById(R.id.gridView);
        photoAdapter = new PhotosAdapter(this, album.getPhotos());
        gridView.setAdapter(photoAdapter);
        registerForContextMenu(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    displayPhoto(i);
                }
        });
    }

    private void displayPhoto(int pos){

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.addphoto:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_photopage, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.Move:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Move Photo to a Different Album");
                final EditText newname = new EditText(this);
                newname.setHint("Album Name");
                newname.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(newname);

                builder.setNegativeButton("Move", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newn = newname.getText().toString();
                        int index = SerializeData.getAlbum(newn);

                        if(index == -1){
                            Toast.makeText(ShowAlbum.this, "Error: album does not exist", Toast.LENGTH_SHORT).show();
                        }else {
                            SerializeData.getData().get(index).getPhotos().add(album.getPhotos().remove(info.position));
                            photoAdapter.notifyDataSetChanged();
                            SerializeData.writeData();
                        }
                    }
                });

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case R.id.deletephoto:
                album.getPhotos().remove(info.position);
                photoAdapter.notifyDataSetChanged();
                SerializeData.writeData();
                break;

        }
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
            album.getPhotos().add(item);
            photoAdapter.notifyDataSetChanged();
            SerializeData.writeData();
        }
    }

}
