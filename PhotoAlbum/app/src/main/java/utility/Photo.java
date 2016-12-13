package utility;

//import javafx.scene.image.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
/**
 * @author Matthew Ya
 * @author Taehee Lee
 */
public class Photo implements Serializable{

	/**
	 * Image object where the image is created stored.
	 */
	//private transient Image image;
	/**
	 * File that contains image file.
	 */
	private String imgBitMap;
	private transient Bitmap image;
	/**
	 * An ArrayList of tags.
	 */
	private ArrayList<Tag> tags;
	/**
	 * String that contains the caption of the image.
	 */
	private String caption;
	
	public Photo(String image) throws IllegalArgumentException{
		// TODO Auto-generated constructor stub
		this.imgBitMap = image;
		this.tags = new ArrayList<Tag>();
		this.caption = "";
	}
	/**
	 * 
	 * @return	returns the ArrayList of tags in this photo
	 */
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	/**
	 * adds a new tag to the photo
	 * 
	 * @param newtag	the new tag to be added to the photo
	 */
	public void addTag(Tag newtag){
		this.tags.add(newtag);
	}
	/**
	 * Removes a tag from the photo
	 * 
	 * @param index		index of tag to be removed
	 */
	public void removeTag(int index){
		this.tags.remove(index);
	}
	/**
	 * captions/recaptions the photo
	 * 
	 * @param text	the new text to be captioned onto the photo
	 */
	public void setCaption(String text){
		this.caption = text;
	}
	/**
	 * 
	 * @return	returns the caption of this photo
	 */
	public String getCaption(){
		return this.caption;
	}

	public void setImage(){
		this.image = BitmapFactory.decodeFile(this.imgBitMap);
	}

	public Bitmap getImage(){
		return this.image;
	}

	public String getImgBitMap(){return this.imgBitMap;}

	/**
	 * 
	 * @return	returns the tags of this photo
	 */
	public String getInfo(){
		StringBuilder mes = new StringBuilder();
		mes.append("Tags: ");
		for(int i = 0; i < this.tags.size(); i++){
			if(i == this.tags.size() - 1){
				mes.append(tags.get(i).getValue());
			}else {
				mes.append(tags.get(i).getValue() + ", ");
			}
		}
		return mes.toString();
	}
	/**
	 * 
	 * @param tag	the tag that is being queried
	 * @return	true if the photo contains the tag, false if not.
	 */
	public boolean containsTag(Tag tag){
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).equals(tag)){
				return true;
			}
		}
		return false;
	}

	public CharSequence[] tagsToStringArray(){

		CharSequence[] tags = new CharSequence[this.getTags().size()];

		for(int i = 0; i < this.tags.size(); i++){
			tags[i] = this.tags.get(i).toString();
		}

		return tags;
	}
}
