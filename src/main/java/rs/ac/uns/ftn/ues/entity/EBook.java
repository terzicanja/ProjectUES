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
	
	@Column(name = "keywords", unique = false, nullable = true)
	private String keywords;
	
	@Column(name = "filename", nullable = true)
	private String filename;
	
	@ManyToOne
	@JoinColumn(name="language_id", referencedColumnName="language_id", nullable=false)
	private Language language;
	
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="category_id", nullable=false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public EBook() {
		
	}
	
	public EBook(Integer id, String title, String author, String keywords, String filename,
			Language language, Category category, User user) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.filename = filename;
		this.language = language;
		this.category = category;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EBook [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords + ", filename=" + filename + ", language=" + language + ", category="
				+ category + "]";
	}


}
