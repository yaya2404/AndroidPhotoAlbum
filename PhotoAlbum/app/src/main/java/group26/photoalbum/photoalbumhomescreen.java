package group26.photoalbum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


import java.io.Serializable;

import utility.AlbumsAdapter;
import utility.SerializeData;
import utility.PhotoAlbum;


public class photoalbumhomescreen extends AppCompatActivity {

    public static final String ALBUM_NAME_KEY = "album_name";


    private ListView listView;
    protected ArrayList<PhotoAlbum> albums;
    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SerializeData.initData();
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        albums = SerializeData.getData();

        adapter = new AlbumsAdapter(this, albums);
        System.out.println(adapter.getCount());
        listView = (ListView) findViewById(R.id.album_view);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                openAlbum(position);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.rename:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("Rename Album");
                final EditText newname = new EditText(this);
                newname.setHint("Album Name");
                newname.setInputType(InputType.TYPE_CLASS_TEXT);
                builder3.setView(newname);

                builder3.setNegativeButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newn = newname.getText().toString();

                        if(SerializeData.getAlbum(newn) != -1){
                            Toast.makeText(photoalbumhomescreen.this, "Error: album name already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            albums.get(info.position).setName(newn);
                            adapter.notifyDataSetChanged();
                            SerializeData.writeData();
                        }
                    }
                });

                builder3.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder3.show();
                break;
            case R.id.delete:
                albums.remove(info.position);
                adapter.notifyDataSetChanged();
                SerializeData.writeData();
                break;

        }
        return true;
    }

    private void openAlbum(int pos){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ALBUM_NAME_KEY, albums.get(pos).toString());
        Intent intent = new Intent(this, ShowAlbum.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        switch(id){
            case R.id.create:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Create Album");

                final EditText input = new EditText(this);
                input.setHint("Album Name");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setNegativeButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        if(SerializeData.getAlbum(m_Text) != -1){
                            Toast.makeText(photoalbumhomescreen.this, "Error: album name already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            albums.add(new PhotoAlbum(m_Text));
                            adapter.notifyDataSetChanged();
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

            case R.id.search:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Search");

                final EditText input2 = new EditText(this);
                input2.setHint("Tag Value");
                input2.setInputType(InputType.TYPE_CLASS_TEXT);
                builder2.setView(input2);

                builder2.setNegativeButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input2.getText().toString();

                    }
                });

                builder2.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder2.show();
                break;
        }
        return true;
    }

}
