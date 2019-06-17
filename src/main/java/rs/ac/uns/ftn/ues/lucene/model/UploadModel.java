package rs.ac.uns.ftn.ues.lucene.model;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.entity.Language;

public class UploadModel {
	
	private String title;
	private String text;
	private String author;
	private String keywords;
//	private File file;
	private MultipartFile[] files;
	private Language language;
	private Category category;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
//	public File getFile() {
//		return file;
//	}
//	public void setFile(File file) {
//		this.file = file;
//	}
		public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	
	
	

}
