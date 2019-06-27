package rs.ac.uns.ftn.ues.dto;

import java.io.Serializable;

import rs.ac.uns.ftn.ues.entity.EBook;

public class EBookDTO implements Serializable {
	
	private Integer id;
	private String title;
	private String author;
	private String keywords;
	private String filename;
	private LanguageDTO language;
	private CategoryDTO category;
	private UserDTO user;
	
	public EBookDTO() {
		super();
	}
	
	
	public EBookDTO(Integer id, String title, String author, String keywords, String filename, LanguageDTO language,
			CategoryDTO category, UserDTO user) {
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


	
	public EBookDTO(EBook ebook, LanguageDTO languageDTO, CategoryDTO categoryDTO, UserDTO userDTO) {
		this(ebook.getId(), ebook.getTitle(), ebook.getAuthor(), ebook.getKeywords(), ebook.getFilename(), languageDTO, categoryDTO, userDTO);
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
	

	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public LanguageDTO getLanguage() {
		return language;
	}


	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}


	public CategoryDTO getCategory() {
		return category;
	}


	public void setCategory(CategoryDTO category) {
		this.category = category;
	}


	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "EBookDTO [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords + "]";
	}
	
	
	
	

}
