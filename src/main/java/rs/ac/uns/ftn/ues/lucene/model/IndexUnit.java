package rs.ac.uns.ftn.ues.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;

public class IndexUnit {
	
	private String text;
	private String title;
	private List<String> keywords = new ArrayList<String>();
	private String filename;
	private String filedate;
	//!!!!!!!!!!!!!!!!!!! mozda i lang treba
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	
	public Document getLuceneDocument() {
		Document d = new Document();
		d.add(new TextField("title", title, Store.YES));
		d.add(new TextField("text", text, Store.YES));
		d.add(new TextField("filename", filename, Store.YES));
		d.add(new TextField("filedate", filedate, Store.YES));
		for (String keyword : keywords) {
			d.add(new TextField("keyword", keyword, Store.YES));
		}
		
		return d;
	}
	

}
