package rs.ac.uns.ftn.ues.lucene.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {
	
	private String title;
	private String keywords;
	private MultipartFile[] files;
	
	
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
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	

}
