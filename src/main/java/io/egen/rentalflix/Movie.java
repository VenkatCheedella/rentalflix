package io.egen.rentalflix;

import java.util.UUID;

/**
 * Entity representing a movie.
 * Fields: id, title, year, language
 */
public class Movie {
	
	private UUID id;
	private String title;
	private int year;
	private String language;
	private boolean isRented;
	private String rentedUser;
			
	public String getRentedUser() {
		return rentedUser;
	}

	public void setRentedUser(String rentedUser) {
		this.isRented = true;
		this.rentedUser = rentedUser;
	}

	public void resetRentedStatus(){
		this.isRented = false;
		this.rentedUser = "";
	}
	
	public Movie(String title, int year, boolean isRented, UUID id, String language) {
		super();
		this.title = title;
		this.year = year;
		this.isRented = isRented;
		this.id = id;
		this.language = language;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isRented() {
		return isRented;
	}
	public void setRented(boolean isRented) {
		this.isRented = isRented;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
