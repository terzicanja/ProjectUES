package rs.ac.uns.ftn.ues.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;

public class IndexUnit {
	
	private String title;
	private String text;
	private String author;
	private List<String> keywords = new ArrayList<String>();
	private String filename;
	private String filedate;
	private String language;
	//!!!!!!!!!!!!!!!!!!! mozda i lang treba
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiledate() {
		return filedate;
	}
	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	public Document getLuceneDocument() {
		Document d = new Document();
		d.add(new TextField("title", title, Store.YES));
		d.add(new TextField("text", text, Store.YES));
		d.add(new TextField("author", author, Store.YES));
		d.add(new TextField("filename", filename, Store.YES));
		d.add(new TextField("filedate", filedate, Store.YES));
		for (String keyword : keywords) {
			d.add(new TextField("keyword", keyword, Store.YES));
		}
		
		return d;
	}
	

}
