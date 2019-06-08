package rs.ac.uns.ftn.ues.lucene.indexing.filters;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class CyrilicToLatinFilter extends TokenFilter {
	
	private CharTermAttribute termAttr;
	
	public CyrilicToLatinFilter(TokenStream input) {
		super(input);
		termAttr = (CharTermAttribute)input.addAttribute(CharTermAttribute.class);
	}
	
	public boolean incrementToken() throws IOException{
		if (input.incrementToken()) {
			String text = termAttr.toString();
			termAttr.setEmpty();
			termAttr.append(CyrilicLatinConverter.cirToLat(text));
			return true;
		}
		return false;
	}

}
