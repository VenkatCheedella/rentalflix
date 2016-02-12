package io.egen.rentalflix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for MovieService
 */
public class MovieServiceTest {
	
		
	@Test
	public void testCreateMovie(){	
		Map<String, Movie> movies = new HashMap<>();
		MovieService mvService = new MovieService();
		Movie mv1 = new Movie("Martian", 2014, UUID.randomUUID().toString(), "English");
		movies.put(mv1.getId(), mv1);
		Movie returnedMv = mvService.create(mv1);
		Assert.assertEquals(null, returnedMv);
	}
	
	@Test
	public void testFindAll(){
		
		Set<String> moviesIds = new HashSet<>();
		MovieService mvService = new MovieService();
		mvService.deleteAllMovies();
		Movie mv1 = new Movie("Matrix", 2003, UUID.randomUUID().toString(), "English");
		Movie mv2 =new Movie("Don", 2007, UUID.randomUUID().toString(), "Hindi");
		Movie mv3 = new Movie("Bahubali", 2014, UUID.randomUUID().toString(), "Telugu");
		Movie mv4 = new Movie("Premam", 2014, UUID.randomUUID().toString(), "Malayalam");
		
		moviesIds.add(mv1.getId());
		moviesIds.add(mv2.getId());
		moviesIds.add(mv3.getId());
		moviesIds.add(mv4.getId());
					
		mvService.create(mv1);
		mvService.create(mv2);
		mvService.create(mv3);
		mvService.create(mv4);
		
		List<Movie> allMovies = mvService.findAll();				
		boolean expected = true;
		boolean actual = true;
		if((allMovies.size() != moviesIds.size()) ? true : false){
			actual =false;
		}
		for(Movie mv : allMovies){
			if(!moviesIds.contains(mv.getId()))
			{
				actual = false;
				break;
			}
		}
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testFindByName(){
		MovieService mvService = new MovieService();
		Movie new_mv1 = new Movie("Matrix", 2005, UUID.randomUUID().toString(), "telugu");
		Movie new_mv2 = new Movie("Matrix", 2005, UUID.randomUUID().toString(), "hindi");
		mvService.create(new_mv1);
		mvService.create(new_mv2);
		List<Movie> movies = mvService.findByName("Matrix");
		boolean expected = true;
		boolean actual = true;
		if(movies.size() == 0)
			actual = false;		
		for(Movie mv : movies){
			String returnedTitle = mv.getTitle();
			if(!returnedTitle.equals("Matrix"))
				actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdate(){
		MovieService mvService = new MovieService();
		List<Movie> movies = mvService.findAll();
		int index = -1;
		if(!movies.isEmpty())
			index = movies.size()/2;
		Movie mv ;		
		Movie mvBeforeUpdate = null;
		Movie receviedOldMovie = null;
		if(index != -1)
		{
			mv = movies.get(index);
			mvBeforeUpdate = mv;
			String title = mv.getTitle();
			mv.setTitle(title + "modified");
			receviedOldMovie = mvService.update(mv);
		}			
		Assert.assertEquals(mvBeforeUpdate, receviedOldMovie);					
	}
	
	@Test
	public void testDelete(){
		MovieService mvService = new MovieService();
		List<Movie> movies = mvService.findAll();
		int index = -1;
		if(!movies.isEmpty())
			index = movies.size()/2;
		Movie mv = null;	
		Movie returnedMv = null;
		if(index != -1){
			mv = movies.get(index);
			returnedMv = mvService.delete(mv.getId());
		}
		Assert.assertEquals(returnedMv, mv);
	}
	
	@Test
	public void rentMovie(){
		Movie mv1 = new Movie("Matrix Reloaded", 2005, UUID.randomUUID().toString(), "English");
		MovieService mvService = new MovieService();
		mvService.create(mv1);
		List<Movie> movies = mvService.findAll();
		int index = -1;
		if(!movies.isEmpty())
			index = movies.size()/2;
		boolean expected  = true;	
		boolean actual = false;		
		Movie mv;
		if(index != -1){
			mv = movies.get(index);
			actual = mvService.rentMovie(mv.getId(), "venkat");
		}
		Assert.assertEquals(expected, actual);;
	}
	
	
}
