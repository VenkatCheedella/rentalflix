package io.egen.rentalflix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Service implementing IFlix interface
 * You can use any Java collection type to store movies
 */
public class MovieService implements IFlix {

	private static Map<String, Movie> movies;
	private static Map<String, List<String>> nameToMoviesMap;

	static{
		movies = new HashMap<String, Movie>();
		nameToMoviesMap = new HashMap<String, List<String>>();
	}

	@Override
	public List<Movie> findAll() {	
		List<Movie> existingMovies = new ArrayList<Movie>(movies.values());
		return existingMovies;
	}

	@Override
	public List<Movie> findByName(String name) {
		List<String> movieIds = nameToMoviesMap.get(name);
		List<Movie> moviesOfGivenName = new ArrayList<>();
		if(movieIds == null)
			return null;
		for(String id : movieIds){
			Movie movie = movies.get(id);
			moviesOfGivenName.add(movie);
		}
		return moviesOfGivenName;
	}

	@Override
	public Movie create(Movie movie) {	
		Movie insertedMv;
		synchronized(this){
			insertedMv = movies.put(movie.getId(), movie);
			if(nameToMoviesMap.containsKey(movie.getTitle())){
				List<String> currentIds =  nameToMoviesMap.get(movie.getTitle());
				currentIds.add(movie.getId());
			}
			else{
				List<String> newId = new ArrayList<>();
				newId.add(movie.getId());
				nameToMoviesMap.put(movie.getTitle(), newId);
			}			
		}
		return insertedMv;
	}

	@Override
	public Movie update(Movie movie) {	
		synchronized(this){
			if(movies.containsKey(movie.getId())){
				return movies.put(movie.getId(), movie);
			}
		}		
		return null;
	}

	@Override
	public Movie delete(String id) {
		synchronized(this){
			if(!movies.containsKey(id))
				return null;
			Movie mv = movies.get(id);			
			if(!nameToMoviesMap.containsKey(mv.getTitle()))
				return null;
			List<String> moviesWithGivenName = nameToMoviesMap.get(mv.getTitle());
			if(moviesWithGivenName.contains(id))
				moviesWithGivenName.remove(id);					
			return movies.remove(id);
		}	
	}

	@Override
	public boolean rentMovie(String movieId, String user) {
		synchronized(this){
			Movie movie = movies.get(movieId);
			if(!movie.isRented()){
				movie.setRentedUser(user);			 //isRented is set inside the setRentedUser function
				return true;
			}
			return false;
		}		
	}

	public void deleteAllMovies(){
		movies.clear();
	}
	
	public static void main(String[] args) {
		MovieService mvService = new MovieService();
		Movie mv1 = new Movie("Matrix", 2003, UUID.randomUUID().toString(), "English");
		Movie mv2 =new Movie("Don", 2007, UUID.randomUUID().toString(), "Hindi");
		Movie mv3 = new Movie("Bahubali", 2014, UUID.randomUUID().toString(), "Telugu");
		Movie mv4 = new Movie("Premam", 2014, UUID.randomUUID().toString(), "Malayalam");
		mvService.create(mv1);
		mvService.create(mv2);
		mvService.create(mv3);
		mvService.create(mv4);
		List<Movie> getAllMvs = mvService.findAll();
		for(Movie mv : getAllMvs){
			System.out.print(mv.getTitle() + " ");
		}
		System.out.println(mvService.findByName(mv3.getId()));
		mv3.setTitle("Bahubali Conclusion");
		mv3.setYear(2016);
		System.out.println(mvService.update(mv3));
		System.out.println(mvService.delete(mv2.getId()));
		System.out.println(mvService.rentMovie(mv4.getId(),	"venkat"));
		System.out.println(mvService.rentMovie(mv4.getId(),	"venkat"));
	}
}
