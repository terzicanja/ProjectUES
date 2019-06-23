package rs.ac.uns.ftn.ues.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import rs.ac.uns.ftn.ues.entity.User;
import rs.ac.uns.ftn.ues.lucene.indexing.Indexer;
import rs.ac.uns.ftn.ues.lucene.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.ues.lucene.model.IndexUnit;
import rs.ac.uns.ftn.ues.lucene.model.UploadModel;
import rs.ac.uns.ftn.ues.service.EBookServiceInterface;
import rs.ac.uns.ftn.ues.service.LanguageServiceInterface;
import rs.ac.uns.ftn.ues.service.UserServiceInterface;

@RestController
@RequestMapping(value = "api/ebooks")
public class EBookController {
	
	@Autowired
	private EBookServiceInterface ebookService;
	
	@Autowired
	private LanguageServiceInterface langService;
	
	@Autowired
	private UserServiceInterface userService;
	
	@Autowired
    private HttpServletRequest request;
	
//	@Autowired
//	private filestorageser
	
	
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
	
	
	@GetMapping(value = "/category/{id}")
	public ResponseEntity<List<EBook>> getBooksByCategory(@PathVariable("id") Integer id){
		List<EBook> books = ebookService.findAllByCategory_Id(id);
		List<EBookDTO> booksDTO = new ArrayList<EBookDTO>();
		for(EBook p : books) {
			booksDTO.add(new EBookDTO(p));
		}
		return new ResponseEntity<List<EBook>>(books, HttpStatus.OK);
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
	
	
	
	
	
//	@PostMapping(value = "/save")
//	private ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
//		System.out.println("ovo je file sto saljem " + file);
//		
//        try {
////        	if(Files.exists(DATA_DIR_PATH, file.getOriginalFilename()));
////        	Files.copy(file.getInputStream(), Paths.get(DATA_DIR_PATH, file.getOriginalFilename()));
////			saveFilee(file);
////        	UploadModel um = file.
//        	indexUploadedFile(file);
//		} catch (Exception e) {
//			System.out.println("ovo nije uspelo");
//			e.printStackTrace();
//		}
//        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
//	}
	
	@PostMapping(value = "/save")
	private ResponseEntity<String> saveFile(@ModelAttribute UploadModel file) {
        try {
        	String indeksiraj = indexUploadedFile(file);
        	if(indeksiraj == "" || indeksiraj == "empty" || indeksiraj == "notPDF") {
        		System.out.println("ovo je u kontroleru: " + indeksiraj);
        	}else {
        		System.out.println("uspesno indeksirano nadam se");
        		System.out.println("isto u kont: " + indeksiraj);
        		
        		
//        		return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
        	}
//        	indexUploadedFile(file); //ova linija ovdeeeeeeeeeeeeee
		} catch (Exception e) {
			System.out.println("ovo nije uspelo");
			e.printStackTrace();
		}
        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
	}
	
	
	
	
	private String indexUploadedFile(UploadModel model) throws IOException{
		String rez = "";
//		System.out.println("znaci model");
		System.out.println("ovo je model sta god: " + model);
		System.out.println("title iz filea: " + model.getTitle());
		System.out.println("ovo su model.getfiles: " + model.getFiles());
		
    	for (MultipartFile file : model.getFiles()) {
            if (file.isEmpty()) {
                continue; //next please
            }else {
            	
	            String fileName = saveFilee(file);
	            if(!fileName.endsWith(".pdf")) {
	            	rez = "notPDF";
	            	System.out.println("nije pdf");
	            }else if(fileName != null){
	            	System.out.println("fajlname je pdf i nije prazan");
	            	IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
	            	System.out.println("u kontroleru model.gettitle jebe mater: " + model.getTitle());
	            	String nas = indexUnit.getTitle();
	            	System.out.println("ovo je naslov u kontroleru ali iz indexunita: " + nas);
//	            	indexUnit.setTitle(model.getTitle());
	            	System.out.println("aj opet ovo je autor modela: "+model.getAuthor());
	//            	indexUnit.setAuthor(model.getAuthor());
	//            	indexUnit.setText(model.getText());
//	            	indexUnit.setFiledate("idk");
//	            	List<String> keywords = new ArrayList<>();
//	            	keywords.add("bla blaaa");
//	            	indexUnit.setKeywords(keywords);
	//            	indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
	            	Indexer.getInstance().add(indexUnit.getLuceneDocument());
	            	rez = "indeksirano";
	            	
	            	EBook ebook = new EBook();
	            	String title = model.getTitle();
	            	System.out.println("e ovo je title izvucen linija 173: "+ title);
	            	if(title == null || title.equals(null) || title.equals("")) {
	            		ebook.setTitle(indexUnit.getTitle());
	            	}else {
	            		ebook.setTitle(title);
	            	}
//	        		ebook.setTitle(indexUnit.getTitle());
	        		ebook.setAuthor(indexUnit.getAuthor());
	        		Category c = new Category();
	        		c.setId(2);
	        		c.setName("proba");
//	        		ebook.setCategory(c);
	        		ebook.setCategory(model.getCategory());
	        		String keywords = model.getKeywords();
	        		if(keywords == null || keywords.equals(null) || keywords.equals("")) {
	        			ebook.setKeywords(indexUnit.getKeywords().toString());
	        		}else {
	        			ebook.setKeywords(keywords);
	        		}
//	        		ebook.setKeywords(indexUnit.getKeywords().toString());
	        		ebook.setYear(2078);
	        		System.out.println("ovo je lang iz modela: " + model.getLanguage());
	        		System.out.println("a ovo je cat iz modela: " + model.getCategory());
//	        		Language l = langService.findOne(model.getLanguage().getId());
//	        		Language l = new Language();
//	        		l.setId(3);
//	        		l.setName("aaaa");
//	        		ebook.setLanguage(l);
	        		String u = model.getUser();
	        		User user = userService.findByUsername(u);
	        		System.out.println("ovo je user u liniji 222: " + u);
	        		ebook.setUser(user);
	        		ebook.setLanguage(model.getLanguage());
	        		String lokacija = indexUnit.getFilename();
	        		String[] split = lokacija.split("\\\\");
	    			String l = split[split.length-1];
	        		ebook.setFilename(l);
	        		ebook = ebookService.save(ebook);
	            }
            }
    	}
    	return rez;
    }
	
	public ResponseEntity<User> whoAmI(Principal user) {
		User logged = userService.findByUsername(user.getName());
		
		return new ResponseEntity<User>(logged, HttpStatus.OK);
    }
	
	
	private String saveFilee(MultipartFile multipartFile) throws IOException {
		String retVal = null;
		if (!multipartFile.isEmpty()) {
			byte[] bytes = multipartFile.getBytes();
//			Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + multipartFile.getOriginalFilename());
			Path path = Paths.get(DATA_DIR_PATH + File.separator + multipartFile.getOriginalFilename());
			Files.write(path, bytes);
//			Files.copy(multipartFile.getInputStream(), Paths.get(DATA_DIR_PATH, multipartFile.getOriginalFilename()));
			retVal = path.toString();
		}
		return retVal;
	}
	
	
	
	
	private File getResourceFilePath(String path) {
		URL url = this.getClass().getClassLoader().getResource(path);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			file = new File(url.getPath());
		}
		return file;
	}
	
	
	
	
	
	
	
	
	
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
	
	
//	@GetMapping(value = "/download/{id}")
//	public ResponseEntity<Resource> downloadBook(@PathVariable("id") Integer id, HttpServletRequest request){
//		EBook ebook = ebookService.findOne(id);
//		Resource r = files
//	}
	
	
	
	
	
	
	
	
	
	
	
	

}
