package rs.ac.uns.ftn.ues.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.entity.EBook;
import rs.ac.uns.ftn.ues.lucene.model.AdvancedQuery;
import rs.ac.uns.ftn.ues.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.ues.lucene.model.ResultData;
import rs.ac.uns.ftn.ues.lucene.model.SearchType;
import rs.ac.uns.ftn.ues.lucene.model.SimpleQuery;
import rs.ac.uns.ftn.ues.lucene.search.QueryBuilder;
import rs.ac.uns.ftn.ues.lucene.search.ResultRetriever;
import rs.ac.uns.ftn.ues.service.EBookServiceInterface;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
	
	@Autowired
	private EBookServiceInterface ebookService;
	
	@PostMapping(value = "/term", consumes = "application/json")
	public ResponseEntity<List<ResultData>> searchTerm(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		System.out.println("trazim fuzzy fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value = "/fuzzy", consumes = "application/json")
	public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		System.out.println("trazim fuzzy fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value = "/phrase", consumes = "application/json")
	public ResponseEntity<List<EBook>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		System.out.println("trazim phrase fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		
		List<EBook> books = new ArrayList<>();
		for(ResultData r : results) {
			String location = r.getLocation();
			String[] split = location.split("\\\\");
			String l = split[split.length-1];
			System.out.println("ovo je srch kontroleru l: "+l);
			EBook ebook = ebookService.findByFilename(l);
			books.add(ebook);
			
		}
		
		return new ResponseEntity<List<EBook>>(books, HttpStatus.OK);
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/boolean", consumes = "application/json")
	public ResponseEntity<List<ResultData>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception{
		Query query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
		Query query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
			builder.add(query1,BooleanClause.Occur.SHOULD);
			builder.add(query2,BooleanClause.Occur.SHOULD);
		}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST_NOT);
		}
		
		Query query = builder.build();
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
		rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
