package com.elyntsev.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
	@Column(name = "publisher")
	private String publisher;

	@Column(name = "year")
	private Integer year;

	@Column(name = "city")
	private String city;

	public Book() {
	}

	public Book(String name, Author author, Genre genre, String publisher, Integer year, String city) {
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.publisher = publisher;
		this.year = year;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", authorId=" + author + ", genreId=" + genre + ", publisher="
				+ publisher + ", year=" + year + ", city=" + city + "]";
	}

}
