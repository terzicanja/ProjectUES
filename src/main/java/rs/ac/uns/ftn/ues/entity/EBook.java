package rs.ac.uns.ftn.ues.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class EBook implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "title", unique = false, nullable = true)
	private String title;
	
	@Column(name = "author", unique = false, nullable = true)
	private String author;
	
	//ovo stvarno nzm sta ce mi
	@Column(name = "keywords", unique = false, nullable = true)
	private String keywords;
	
	@Column(name = "year", unique = false, nullable = true)
	private Integer year;
	
	@Column(name = "filename", nullable = true)
	private String filename;
	
	//nije obavezno valjda
	@Column(name = "mime", unique = false, nullable = true)
	private String mime;
	
//	@Column(name = "language", unique = false, nullable = true)
	@ManyToOne
	@JoinColumn(name="language_id", referencedColumnName="language_id", nullable=false)
	private Language language;
	
//	@Column(name = "category", unique = false, nullable = true)
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="category_id", nullable=false)
	private Category category;
	
	public EBook() {
		
	}
	
	

	public EBook(Integer id, String title, String author, String keywords, Integer year, String filename, String mime,
			Language language, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.year = year;
		this.filename = filename;
		this.mime = mime;
		this.language = language;
		this.category = category;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}



	@Override
	public String toString() {
		return "EBook [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords + ", year="
				+ year + ", filename=" + filename + ", mime=" + mime + ", language=" + language + ", category="
				+ category + "]";
	}



	
	
	
	
	
	

}
