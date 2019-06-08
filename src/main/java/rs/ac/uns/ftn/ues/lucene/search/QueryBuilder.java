package rs.ac.uns.ftn.ues.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

import rs.ac.uns.ftn.ues.lucene.indexing.analysers.SerbianAnalyzer;
import rs.ac.uns.ftn.ues.lucene.model.SearchType;

public class QueryBuilder {
	
	private static SerbianAnalyzer analyzer = new SerbianAnalyzer();
	private static int maxEdits = 1;
	
	public static int getMaxEdits() {
		return maxEdits;
	}
	public static void setMaxEdits(int maxEdits) {
		QueryBuilder.maxEdits = maxEdits;
	}
	
	
	public static Query buildQuery(SearchType queryType, String field, String value) throws IllegalArgumentException, ParseException{
		QueryParser parser = new QueryParser(field, analyzer);
		String errorMsg = "";
		if(field == null || field.equals("")) {
			errorMsg += "Field not specified";
		}
		if(value == null) {
			if (!errorMsg.equals("")) errorMsg+="\n";
			errorMsg += "Value not specified";
		}
		if (!errorMsg.equals("")) {
			throw new IllegalArgumentException(errorMsg);
		}
		
		Query query = null;
		if (queryType.equals(SearchType.regular)) {
			Term term = new Term(field, value);
			query = new TermQuery(term);
		}else if(queryType.equals(SearchType.fuzzy)){
			Term term = new Term(field, value);
			query = new FuzzyQuery(term, maxEdits);
		}else if(queryType.equals(SearchType.prefix)){
			Term term = new Term(field, value);
			query = new PrefixQuery(term);
		}else{
			String[] values = value.split(" ");
			PhraseQuery.Builder builder = new PhraseQuery.Builder();
			for(String word : values){
				Term term = new Term(field, word);
				builder.add(term);
			}
			query = builder.build();
		}
		
		return parser.parse(query.toString(field));
	}
	
	
	
	
	

}
