package rs.ac.uns.ftn.ues.lucene.indexing.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.lucene.document.DateTools;

import rs.ac.uns.ftn.ues.lucene.model.IndexUnit;

public class TextDocHandler extends DocumentHandler {

	@Override
	public IndexUnit getIndexUnit(File file) {
		IndexUnit iu = new IndexUnit();
		BufferedReader reader =  null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis, "UTF8"));
			
			String firstLine = reader.readLine(); //prva linija datoteke je naslov
			iu.setTitle(firstLine);
			
			String secondLine = reader.readLine();
			String[] keywords = secondLine.split(";"); //!!!!!!!!!! ili po zarezu mozda
			iu.setKeywords(new ArrayList<String>(Arrays.asList(keywords)));
			
			String fullText = "";
			while(true) {
				secondLine = reader.readLine();
				if(secondLine == null) {
					break;
				}
				fullText += " " + secondLine;
			}
			iu.setText(fullText);
			iu.setFilename(file.getCanonicalPath());
			
			String modificationDate = DateTools.dateToString(new Date(file.lastModified()), DateTools.Resolution.DAY);
			iu.setFiledate(modificationDate);
			
			return iu;
			
			
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("fajl ne postoji");
		} catch(IOException e) {
			throw new IllegalArgumentException("nesto nije u redu sa fajlom");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e2) {
					// TODO: handle exception
				}
			}
		}
	}

	@Override
	public String getText(File file) {
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis, "UTF8"));
			String secondLine;
			String fullText = "";
			while (true) {
				secondLine = reader.readLine();
				if (secondLine == null) {
					break;
				}
				fullText += " " + secondLine;
			}
			return fullText;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("datoteka ne postoji");
		} catch (IOException e) {
			throw new IllegalArgumentException("fajl nije u redu");
		}finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	
	
	

}
