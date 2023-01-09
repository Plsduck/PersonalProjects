package movieList;
import java.util.ArrayList; 
public class Movie {
	
	private String title; 
	private ArrayList<String> genres;
	private String id; 
	
	public Movie(String id, String title, ArrayList<String> genre) {
		genres = new ArrayList<String>(); 
		this.title = title;
		this.id = id; 
		for(int i = 0; i < genre.size(); i++){
			genres.add(genre.get(i)); 
		}
		
	}
	public String getTitle() {
		return title; 
	}
	public String toString() {
		String toReturn = "\"" + title + "\"" + ", ";
		for (int i = 0; i < genres.size(); i++) {
			toReturn = toReturn + genres.get(i) + ", ";
		}

		toReturn = toReturn + id;		
		return toReturn; 
	}
		

}
