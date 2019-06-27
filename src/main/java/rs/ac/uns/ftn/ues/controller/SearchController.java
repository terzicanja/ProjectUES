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

import rs.ac.uns.ftn.ues.dto.CategoryDTO;
import rs.ac.uns.ftn.ues.dto.EBookDTO;
import rs.ac.uns.ftn.ues.dto.LanguageDTO;
import rs.ac.uns.ftn.ues.dto.UserDTO;
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
	public ResponseEntity<List<EBookDTO>> searchTerm(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		String field = simpleQuery.getField();
		if (field.equalsIgnoreCase("language")) {
//			ebookService.findAllByLanguage_Id(id)
		}
		System.out.println("trazim term fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		
		List<EBookDTO> bookdto = new ArrayList<>();
		for(ResultData r : results) {
			String location = r.getLocation();
			String[] split = location.split("\\\\");
			String l = split[split.length-1];
			EBook ebook = ebookService.findByFilename(l);
			if(ebook==null) {
				return new ResponseEntity<List<EBookDTO>>(HttpStatus.NOT_FOUND);
			}
			bookdto.add(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), new UserDTO(ebook.getUser())));
			
		}
		return new ResponseEntity<List<EBookDTO>>(bookdto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/fuzzy", consumes = "application/json")
	public ResponseEntity<List<EBookDTO>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		System.out.println("trazim fuzzy fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		
//		List<EBook> books = new ArrayList<>();
		List<EBookDTO> bookdto = new ArrayList<>();
		for(ResultData r : results) {
			String location = r.getLocation();
			String[] split = location.split("\\\\");
			String l = split[split.length-1];
			EBook ebook = ebookService.findByFilename(l);
			System.out.println("ebook u pretrazi je: " + ebook);
			if(ebook==null) {
				return new ResponseEntity<List<EBookDTO>>(HttpStatus.NOT_FOUND);
			}
			bookdto.add(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), new UserDTO(ebook.getUser())));
//			books.add(ebook);
			
		}
		return new ResponseEntity<List<EBookDTO>>(bookdto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/phrase", consumes = "application/json")
	public ResponseEntity<List<EBookDTO>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception{
		Query query = QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		System.out.println("trazim phrase fieldname: "+simpleQuery.getField() + "a ovo je vrednost valjda: " + simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		
		List<EBookDTO> bookdto = new ArrayList<>();
		for(ResultData r : results) {
			String location = r.getLocation();
			String[] split = location.split("\\\\");
			String l = split[split.length-1];
			System.out.println("ovo je srch kontroleru l: "+l);
			EBook ebook = ebookService.findByFilename(l);
			if(ebook==null) {
				return new ResponseEntity<List<EBookDTO>>(HttpStatus.NOT_FOUND);
			}
			bookdto.add(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), new UserDTO(ebook.getUser())));
			
		}
		
		return new ResponseEntity<List<EBookDTO>>(bookdto, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/boolean", consumes = "application/json")
	public ResponseEntity<List<EBookDTO>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception{
		System.out.println("linija 80 aq.getfield: " + advancedQuery.getField1());
		System.out.println("linija 80 aq.getValue1: " + advancedQuery.getValue1());
		System.out.println("linija 80 aq.getField2: " + advancedQuery.getField2());
		System.out.println("linija 80 aq.getValue2: " + advancedQuery.getValue2());
		System.out.println("linija 80 aq.getOperation: " + advancedQuery.getOperation1());
		Query query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
		Query query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());
		Query query3 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField3(), advancedQuery.getValue3());
		
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		if(advancedQuery.getOperation1().equalsIgnoreCase("AND")){
			if(advancedQuery.getOperation2().equalsIgnoreCase("AND")) {
				builder.add(query3,BooleanClause.Occur.MUST);
			}else if(advancedQuery.getOperation1().equalsIgnoreCase("OR")) {
				builder.add(query3,BooleanClause.Occur.SHOULD);
			}else if(advancedQuery.getOperation1().equalsIgnoreCase("NOT")){
				builder.add(query3,BooleanClause.Occur.MUST_NOT);
			}
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST);
		}else if(advancedQuery.getOperation1().equalsIgnoreCase("OR")){
			
			builder.add(query1,BooleanClause.Occur.SHOULD);
			builder.add(query2,BooleanClause.Occur.SHOULD);
		}else if(advancedQuery.getOperation1().equalsIgnoreCase("NOT")){
			builder.add(query1,BooleanClause.Occur.MUST);
			builder.add(query2,BooleanClause.Occur.MUST_NOT);
		}
		
		Query query = builder.build();
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
		rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
		List<ResultData> results = ResultRetriever.getResults(query, rh);
		
		
		List<EBookDTO> bookdto = new ArrayList<>();
		for(ResultData r : results) {
			String location = r.getLocation();
			String[] split = location.split("\\\\");
			String l = split[split.length-1];
			EBook ebook = ebookService.findByFilename(l);
			if(ebook==null) {
				return new ResponseEntity<List<EBookDTO>>(HttpStatus.NOT_FOUND);
			}
			bookdto.add(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), new UserDTO(ebook.getUser())));
			
		}
		return new ResponseEntity<List<EBookDTO>>(bookdto, HttpStatus.OK);
		
	}
	
	
	

}
