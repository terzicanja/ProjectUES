package rs.ac.uns.ftn.ues.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import rs.ac.uns.ftn.ues.dto.CategoryDTO;
import rs.ac.uns.ftn.ues.dto.EBookDTO;
import rs.ac.uns.ftn.ues.dto.LanguageDTO;
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
			UserDTO udto = new UserDTO(p.getUser());
			booksDTO.add(new EBookDTO(p, new LanguageDTO(p.getLanguage()), new CategoryDTO(p.getCategory()),udto));
		}
		return new ResponseEntity<List<EBookDTO>>(booksDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/category/{id}")
	public ResponseEntity<List<EBookDTO>> getBooksByCategory(@PathVariable("id") Integer id){
		List<EBook> books = ebookService.findAllByCategory_Id(id);
		List<EBookDTO> booksDTO = new ArrayList<EBookDTO>();
		for(EBook p : books) {
			UserDTO udto = new UserDTO(p.getUser());
			booksDTO.add(new EBookDTO(p, new LanguageDTO(p.getLanguage()), new CategoryDTO(p.getCategory()), udto));
		}
		return new ResponseEntity<List<EBookDTO>>(booksDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<List<EBookDTO>> getBooksByUser(@PathVariable("id") Integer id){
		List<EBook> books = ebookService.findAllByUser_Id(id);
		List<EBookDTO> booksDTO = new ArrayList<EBookDTO>();
		for(EBook p : books) {
			UserDTO udto = new UserDTO(p.getUser());
			booksDTO.add(new EBookDTO(p, new LanguageDTO(p.getLanguage()), new CategoryDTO(p.getCategory()), udto));
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
			UserDTO udto = new UserDTO(ebook.getUser());
			return new ResponseEntity<EBookDTO>(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), udto), HttpStatus.OK);
		}
		
	}
	
	
	@GetMapping(value = "/index/{id}")
	public ResponseEntity<Boolean> index(@PathVariable("id") Integer id){
		EBook ebook = ebookService.findOne(id);
		if(ebook == null) {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}else {
//			Path path = 
			Path path = Paths.get(DATA_DIR_PATH + ebook.getFilename());
			File file = new File(DATA_DIR_PATH + ebook.getFilename());
			Indexer.getInstance().index(file);
//			EBook ebook = ebookService.findOne(id);
//			UserDTO udto = new UserDTO(ebook.getUser());
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
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
		System.out.println("ovo je model sta god: " + model);
		
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
	            	String nas = indexUnit.getTitle();
//	            	indexUnit.setTitle(model.getTitle());
//	            	List<String> keywords = new ArrayList<>();
//	            	keywords.add("bla blaaa");
//	            	indexUnit.setKeywords(keywords);
//	            	indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
	            	
	            	
	            	
	            	EBook ebook = new EBook();
	            	String title = model.getTitle();
	            	if(title == null || title.equals(null) || title.equals("")) {
	            		ebook.setTitle(indexUnit.getTitle());
	            	}else {
	            		indexUnit.setTitle(title);
	            		ebook.setTitle(title);
	            	}
	        		ebook.setAuthor(indexUnit.getAuthor());
	        		ebook.setCategory(model.getCategory());
	        		String keywords = model.getKeywords();
	        		if(keywords == null || keywords.equals(null) || keywords.equals("")) {
	        			String key = "";
	        			for(String k : indexUnit.getKeywords()) {
	        				key = key + " " + k;
//	        				ebook.setKeywords(k);
	        			}
	        			ebook.setKeywords(key);
//	        			ebook.setKeywords(indexUnit.getKeywords().toString());
	        		}else {
	        			indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
	        			ebook.setKeywords(keywords);
	        		}
	        		indexUnit.setLanguage(model.getLanguage().getName());
	        		Indexer.getInstance().add(indexUnit.getLuceneDocument());
	            	rez = "indeksirano";
	        		
//	        		ebook.setKeywords(indexUnit.getKeywords().toString());
	        		System.out.println("ovo je lang iz modela: " + model.getLanguage());
	        		System.out.println("a ovo je cat iz modela: " + model.getCategory());
	        		String u = model.getUser();
	        		User user = userService.findByUsername(u);
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
		UserDTO udto = new UserDTO(ebook.getUser());
		return new ResponseEntity<EBookDTO>(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), udto), HttpStatus.CREATED);
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
		ebook.setAuthor(eBookDTO.getAuthor());
		
		ebook = ebookService.save(ebook);
		UserDTO udto = new UserDTO(ebook.getUser());
		return new ResponseEntity<EBookDTO>(new EBookDTO(ebook, new LanguageDTO(ebook.getLanguage()), new CategoryDTO(ebook.getCategory()), udto), HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id){
		EBook book = ebookService.findOne(id);
		if (book != null) {
			boolean iu = Indexer.getInstance().delete(DATA_DIR_PATH+book.getFilename());
			if(iu==true) {
				System.out.println("indeks je obrisan");
				ebookService.remove(id);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}else {
				System.out.println("indeks nije obrisan");
				return new ResponseEntity<Void>(HttpStatus.I_AM_A_TEAPOT);
			}
			
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "/download/{filename}", consumes = "application/pdf")
	public ResponseEntity<Resource> downloadBook(@PathVariable("filename") String filename, HttpServletResponse response){
		try {
			Path path = Paths.get(DATA_DIR_PATH + filename);
			String p = DATA_DIR_PATH + filename;
			System.out.println("ovo je u dwnl path: " + path);
			Resource r = new UrlResource(path.toUri());
			System.out.println("aj da vidimo sta je r: " + r);
			if(r.exists()) {
				System.out.println("znaci ako r postoji");
				InputStream is = r.getInputStream();
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
//				return new ResponseEntity<Resource>(HttpStatus.OK);
				
				return ResponseEntity.ok()
		                .contentType(MediaType.APPLICATION_PDF)
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getFilename() + "\"")
		                .header(HttpHeaders.CONTENT_TYPE, "application/pdf;charset=utf-8")
		                .body(r);
			}else {
				System.out.println("r ne postoji");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			System.out.println("neuspela akcija skidanja knjige");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
//		EBook ebook = ebookService.findByFilename(filename);
//		Resource r = files
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
