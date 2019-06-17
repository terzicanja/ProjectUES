package rs.ac.uns.ftn.ues.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.entity.EBook;
import rs.ac.uns.ftn.ues.lucene.model.RequiredHighlight;
import rs.ac.uns.ftn.ues.lucene.model.ResultData;
import rs.ac.uns.ftn.ues.lucene.model.SearchType;
import rs.ac.uns.ftn.ues.lucene.model.SimpleQuery;
import rs.ac.uns.ftn.ues.lucene.search.QueryBuilder;
import rs.ac.uns.ftn.ues.lucene.search.ResultRetriever;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
	
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
	public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception{
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
			
		}
		
		
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	
	
	

}
