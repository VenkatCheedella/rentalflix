package io.egen.rentalflix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service implementing IFlix interface
 * You can use any Java collection type to store movies
 */
public class MovieService implements IFlix {
	
	private static Map<UUID, Movie> movies;
	private static Map<String, List<UUID>> nameToMoviesMap;
	
	static{
		movies = new HashMap<UUID, Movie>();
		nameToMoviesMap = new HashMap<String, List<UUID>>();
	}
		
	@Override
	public List<Movie> findAll() {	
		List<Movie> existingMovies = (List<Movie>)movies.values();
		return existingMovies;
	}

	@Override
	public List<Movie> findByName(String name) {
		List<UUID> movieIds = nameToMoviesMap.get(name);
		List<Movie> moviesOfGivenName = new ArrayList<>();
		for(UUID id : movieIds){
			Movie movie = movies.get(id);
			moviesOfGivenName.add(movie);
		}
		return moviesOfGivenName;
	}

	@Override
	public Movie create(Movie movie) {	
		movies.put(movie.getId(), movie);
		if(nameToMoviesMap.containsKey(movie.getTitle())){
			 List<UUID> currentIds =  nameToMoviesMap.get(movie.getTitle());
			 currentIds.add(movie.getId());
		}
		else{
			List<UUID> newId = new ArrayList<>();
			newId.add(movie.getId());
			nameToMoviesMap.put(movie.getTitle(), newId);
		}
		return movie;
	}

	@Override
	public Movie update(Movie movie) {		
		if(movies.containsKey(movie.getId())){
			return movies.put(movie.getId(), movie);
		}
		return null;
	}

	@Override
	public Movie delete(int id) {
		
		return movies.get(id);
	}

	@Override
	public boolean rentMovie(int movieId, String user) {
		Movie movie = movies.get(movieId);
		if(!movie.isRented()){
			movie.setRentedUser(user);			 //isRented is set inside the setRentedUser function
			return true;
		}
		return false;
	}

}
