package rs.ac.uns.ftn.ues.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import rs.ac.uns.ftn.ues.dto.EBookDTO;
import rs.ac.uns.ftn.ues.dto.UserDTO;
import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.entity.EBook;
import rs.ac.uns.ftn.ues.entity.Language;
import rs.ac.uns.ftn.ues.service.EBookServiceInterface;

@RestController
@RequestMapping(value = "api/ebooks")
public class EBookController {
	
	@Autowired
	private EBookServiceInterface ebookService;
	
	@Autowired
    private HttpServletRequest request;
	
	
	private static String DATA_DIR_PATH = "D:/Fakultet/3 godina/1 semestar/TiPzU elektronskim sadrzajima i dokumentima/workspace/ProjectUES/src/main/resources/files/";
	
	@GetMapping
	public ResponseEntity<List<EBookDTO>> getBooks(){
		List<EBook> books = ebookService.findAll();
		List<EBookDTO> booksDTO = new ArrayList<EBookDTO>();
		for(EBook p : books) {
			booksDTO.add(new EBookDTO(p));
		}
		return new ResponseEntity<List<EBookDTO>>(booksDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EBookDTO> getOne(@PathVariable("id") Integer id){
		EBook ebook = ebookService.findOne(id);

//		try {
//			EBook ebook = ebookService.findOne(id);
//			return new ResponseEntity<EBookDTO>(new EBookDTO(ebook), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
//		}
		
		if(ebook == null) {
			return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
		}else {
//			EBook ebook = ebookService.findOne(id);
			return new ResponseEntity<EBookDTO>(new EBookDTO(ebook), HttpStatus.OK);
		}
		
	}
	
	
	
	
	
	@PostMapping(value = "/save")
	private ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
		System.out.println("ovo je file sto saljem " + file);
		
        try {
//        	if(Files.exists(DATA_DIR_PATH, file.getOriginalFilename()));
        	Files.copy(file.getInputStream(), Paths.get(DATA_DIR_PATH, file.getOriginalFilename()));
//			saveFilee(file);
		} catch (Exception e) {
			System.out.println("ovo nije uspelo");
			e.printStackTrace();
		}
        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
        
		
	}
	
//	private void saveFilee(MultipartFile multipartFile) throws Exception {
//		Files.copy(multipartFile.getInputStream(), Paths.get(DATA_DIR_PATH, multipartFile.getOriginalFilename()));
////	    String destination = "/files/"  + multipartFile.getOriginalFilename();
////	    File file = new File(destination);
////	    multipartFile.transferTo(file);
//	}
	
	
	
//	private void indexUploadedFile(EBook model) throws IOException{
////		String fileName = saveUploadedFile(file);
////    	for (MultipartFile file : model.getFiles()) {
////
////            if (file.isEmpty()) {
////                continue; //next please
////            }
////            String fileName = saveUploadedFile(file);
////            if(fileName != null){
////            	IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
////            	indexUnit.setTitle(model.getTitle());
////            	indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
////            	Indexer.getInstance().add(indexUnit.getLuceneDocument());
////            }
////    	}
//    }
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<EBookDTO> saveBook(@RequestBody EBookDTO ebookDTO){
		EBook ebook =  new EBook();
		ebook.setTitle(ebookDTO.getTitle());
		ebook.setYear(ebookDTO.getYear());
		ebook.setAuthor(ebookDTO.getAuthor());
		
		Category c = new Category();
		c.setId(2);
		c.setName("probna kategorija");
		ebook.setCategory(c);
		Language l = new Language();
		l.setId(2);
		l.setName("probni jezik");
		ebook.setLanguage(l);
		
		ebook = ebookService.save(ebook);
		return new ResponseEntity<EBookDTO>(new EBookDTO(ebook), HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	public ResponseEntity<EBookDTO> updateBook(@RequestBody EBookDTO eBookDTO, @PathVariable("id") Integer id){
//		try {
//			EBook ebook = ebookService.findOne(id);
//		} catch (Exception e) {
//			return new ResponseEntity<EBookDTO>(HttpStatus.BAD_REQUEST);
//		}
		EBook ebook = ebookService.findOne(id);
		if(ebook == null) {
			return new ResponseEntity<EBookDTO>(HttpStatus.NOT_FOUND);
		}
		ebook.setTitle(eBookDTO.getTitle());
		ebook.setYear(eBookDTO.getYear());
		ebook.setAuthor(eBookDTO.getAuthor());
		
		ebook = ebookService.save(ebook);
		return new ResponseEntity<EBookDTO>(new EBookDTO(ebook), HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id){
		EBook book = ebookService.findOne(id);
		if (book != null) {
			ebookService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

}
