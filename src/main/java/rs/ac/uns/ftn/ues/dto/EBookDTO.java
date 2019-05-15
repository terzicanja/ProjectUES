package rs.ac.uns.ftn.ues.dto;

import java.io.Serializable;

import rs.ac.uns.ftn.ues.entity.EBook;

public class EBookDTO implements Serializable {
	
	private Integer id;
	private String title;
	private String author;
	private String keywords;
	private Integer year;
	private String mime;
	
	public EBookDTO() {
		super();
	}

	public EBookDTO(Integer id, String title, String author, String keywords, Integer year, String mime) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.year = year;
		this.mime = mime;
	}
	
	public EBookDTO(EBook ebook) {
		this(ebook.getId(), ebook.getTitle(), ebook.getAuthor(), ebook.getKeywords(), ebook.getYear(), ebook.getMime());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	@Override
	public String toString() {
		return "EBookDTO [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords + ", year="
				+ year + ", mime=" + mime + "]";
	}
	
	
	
	

}
