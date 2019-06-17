package rs.ac.uns.ftn.ues.lucene.indexing.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import rs.ac.uns.ftn.ues.lucene.model.IndexUnit;

public class PDFHandler extends DocumentHandler {

	@Override
	public IndexUnit getIndexUnit(File file) {
		IndexUnit iu = new IndexUnit();
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getText(parser);
			iu.setText(text);
			
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();
			
			String title = "" + info.getTitle();
			iu.setTitle(title);
			System.out.println("pdf handler ajde title: "+ title);
			
			String author = ""+info.getAuthor();
			iu.setAuthor(author);
			System.out.println("autor u pdf handleru: " + author);
			
			String keywords = "" + info.getKeywords();
			if (keywords != null) {
				String[] splittedKeywords = keywords.split(" ");
				System.out.println("ovo su keyw u handleru: " + splittedKeywords);
				iu.setKeywords(new ArrayList<String>(Arrays.asList(splittedKeywords)));
			}
			
			iu.setFilename(file.getCanonicalPath());
			System.out.println("ovo je filename u pdf handleru: " + file.getCanonicalPath());
			
			String modDate = DateTools.dateToString(new Date(file.lastModified()), Resolution.DAY);
			iu.setFiledate(modDate);
			
			pdf.close();
		} catch (Exception e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return iu;
	}

	
	@Override
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("greska pri konvertovanju");
		}
		return null;
	}
	
	
	
	public String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("greska pri konvertovanju u pdf");
		}
		return null;
	}
	
	
	

}
