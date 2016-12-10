package utility;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//import javafx.scene.image.Image;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class PhotoAlbum implements Serializable{
	
	/**
	 * Name of the PhotoAlbum object.
	 */
	private String name;
	/**
	 * ArrayList of Photo objects
	 */
	private ArrayList<Photo> photos;
	
	public PhotoAlbum(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	/**
	 * @return	returns the name of the PhotoAlbum object.
	 */
	public String toString(){
		return this.name;
	}
	/**
	 * 
	 * @return returns the number of photos inside the PhotoAlbum object.
	 */
	public int getSize(){
		return this.photos.size();
	}
	/**
	 * Renames the PhotoAlbum object.
	 * 
	 * @param name	the new name of the PhotoAlbum.
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 
	 * @return	returns the ArrayList of Photo objects in the PhotoAlbum object.
	 */
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	/**
	 * Searches the PhotoAlbum object for the Photo object that contains the Image object.
	 * 
	 * 
	 * @param image		the image that is being queried.
	 * @return		returns the index of the Photo object that contains the image. -1 if the Image object is not found.
	 */
	/*
	public int getIndexOfPhoto(Image image) {
		for (int i = 0; i < this.photos.size(); i++) {
			if (this.photos.get(i).getImage().equals(image)) {
				return i;
			}
		}
		return -1;
	}
	*/
}
