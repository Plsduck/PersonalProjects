package movieList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.*;



public class User {
	private ArrayList<Movie> movList;
	private File saveList;
	private String savePath = "C:\\Users\\Isaia\\eclipse-workspace\\1.0\\src\\main\\java\\movieList\\saveList.txt";
	public User(){
		movList = new ArrayList<Movie>(); 
		try {
			saveList = new File(savePath);
			if (saveList.createNewFile()) {
				System.out.println("File Created"); 
			}
			else {
				System.out.println("File Already Exists");
				BufferedReader br = new BufferedReader(new FileReader(saveList));
				
				String temp, id, title;
				ArrayList<String> genres = new ArrayList<String>(); 
				while ((temp = br.readLine()) != null) {
					String titlearray[] = temp.split("\""); 
					title = titlearray[1]; 
					
					String genrearray[] = titlearray[2].split(",");
					for(int i = 1; i < genrearray.length - 1; i++) {
						genres.add(genrearray[i].replaceFirst("\\s", "")); 
					}
					id = genrearray[genrearray.length - 1].replaceFirst("\\s", "");
					
					Movie movie = new Movie(id, title, genres);
					movList.add(movie); 
					
					genres.clear();
				}
			}
				
		}
		catch (Exception e) {
			System.out.println(e); 
		}
		 
		
		

	}
	public void Add(String title, ArrayList<String> genres, String id) {
		Movie movieToAdd = new Movie(id, title, genres); 
		movList.add(movieToAdd); 
		Save(); 
	}
	
	public boolean Delete(String title) {
		for (int i = 0; i < movList.size(); i++) {
			if (movList.get(i).getTitle().equals(title)) {
				movList.remove(i); 
				Save();
				return true; 
			}
		}
		return false; 
	}
	
	public boolean Modify(int movieOne, int movieTwo) {
		try {
			Collections.swap(movList, movieOne - 1, movieTwo - 1);
			return true; 
		}
		catch(Exception e) {
			return false; 
		}
	}
	
	private void Save() {
		File myObj = new File(savePath);
		myObj.delete();  
		try {
			myObj.createNewFile();
			FileWriter writer = new FileWriter(savePath);
			for (int i = 0; i < movList.size(); i++) {
				writer.write(movList.get(i).toString());
				writer.write("\n");
			}
			writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printList() {
		for (int i = 0; i < movList.size(); i++) {
			System.out.print(i + 1);
			System.out.println(" " + movList.get(i).toString()); 
		}
	}

}
