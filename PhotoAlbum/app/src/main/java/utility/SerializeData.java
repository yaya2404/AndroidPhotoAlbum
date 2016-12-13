package utility;


import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;

/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class SerializeData {
	
	/**
	 * The file where the ArrayList users is stored and read.
	 */
	private static final String file = "album";
	private static File test;
	/**
	 * The ArrayList of users.
	 */
	private static ArrayList<PhotoAlbum> albums;


	/**
	 * initData is called when the application starts. The serialized items are read from the file and stored
	 * into the users ArrayList. Because the Image object is not serializable, the Image objects are created
	 * with their serialized File object.
	 */
	public static void initData(){

		test = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"/" + file);
		try {
			if (!test.exists()) {
				albums = new ArrayList<PhotoAlbum>();
				FileOutputStream fos = new FileOutputStream(test);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(albums);
				os.close();
				fos.close();
			}else{
				FileInputStream fis = new FileInputStream(test);
				ObjectInputStream ois = new ObjectInputStream(fis);
				albums = (ArrayList<PhotoAlbum>) ois.readObject();
				ois.close();
				fis.close();

				//needs to load images/data
				for(int i = 0; i < albums.size(); i++){
					for(int j = 0; j < albums.get(i).getPhotos().size(); j++){
						albums.get(i).getPhotos().get(j).setImage();
					}
				}


			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return	The ArrayList of users that was read from the file.
	 */
	public static ArrayList<PhotoAlbum> getData(){
		return albums;
	}
	/**
	 * Writes the ArrayList of users into the file.
	 */
	public static void writeData(){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(test));
			oos.writeObject(albums);
			oos.close();
		}catch(Exception e){

			System.out.println("OBJECT      WRITE       DID     NOT         WORK");
			e.printStackTrace();		//remove later



		}
	}
	public static int getAlbum(String name){
		for(int i = 0; i < albums.size(); i++){
			if(albums.get(i).toString().compareToIgnoreCase(name) == 0){
				return i;
			}
		}
		return -1;
	}
	public static PhotoAlbum searchPhoto(String query){
		PhotoAlbum photoQuery = new PhotoAlbum("Search Query");

		for(int i = 0; i < albums.size(); i++){
			for(int j = 0 ; j < albums.get(i).getPhotos().size(); j++){
				for(int k = 0; k < albums.get(i).getPhotos().get(j).getTags().size(); k++){
					if(albums.get(i).getPhotos().get(j).getTags().get(k).getValue().contains(query)){
						photoQuery.getPhotos().add(albums.get(i).getPhotos().get(j));
					}
				}
			}
		}


		return photoQuery;
	}
}
