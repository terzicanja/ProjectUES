package rs.ac.uns.ftn.ues.lucene.indexing;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import rs.ac.uns.ftn.ues.lucene.indexing.analysers.SerbianAnalyzer;
import rs.ac.uns.ftn.ues.lucene.indexing.handlers.DocumentHandler;
import rs.ac.uns.ftn.ues.lucene.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.ues.lucene.indexing.handlers.TextDocHandler;
import rs.ac.uns.ftn.ues.lucene.model.IndexUnit;


public class Indexer {
	
	private File indexDirPath;
	private IndexWriter indexWriter;
	private Directory indexDir;
	
	private static Indexer indexer = new Indexer(true);
	
	public static Indexer getInstance() {
		return indexer;
	}
	
	private Indexer(String path, boolean restart) {
		IndexWriterConfig iwc = new IndexWriterConfig(new SerbianAnalyzer()); 
		if(restart) {
			iwc.setOpenMode(OpenMode.CREATE);
		}else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}
		
		try {
			this.indexDir = new SimpleFSDirectory(FileSystems.getDefault().getPath(path));
			this.indexWriter = new IndexWriter(this.indexDir, iwc);
		}catch (IOException e) {
			throw new IllegalArgumentException("Path not correct");
		}
	}
	
	private Indexer(boolean restart) {
		this(ResourceBundle.getBundle("application").getString("index"), restart);
	}
	
	private Indexer() {
		this(true);
	}

	public File getIndexDirPath() {
		return indexDirPath;
	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public Directory getIndexDir() {
		return indexDir;
	}
	
	public boolean delete(String filename) {
		Term delTerm = new Term("filename", filename);
		try {
			synchronized (this) {
				this.indexWriter.deleteDocuments(delTerm);
				this.indexWriter.commit();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean add(Document doc) {
		try {
			synchronized (this) {
				this.indexWriter.addDocument(doc);
				this.indexWriter.commit();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean updateDocument(String filename, List<IndexableField> fields) {
		try {
			DirectoryReader reader = DirectoryReader.open(this.indexDir);
			IndexSearcher is = new IndexSearcher(reader);
			Query query = new TermQuery(new Term("filename", filename));
			TopScoreDocCollector collector = TopScoreDocCollector.create(10);
			is.search(query, collector);
			
			ScoreDoc[] scoreDocs = collector.topDocs().scoreDocs;
			if(scoreDocs.length > 0) {
				int docID = scoreDocs[0].doc;
				Document doc = is.doc(docID);
				if(doc != null) {
					for(IndexableField field : fields) {
						doc.removeFields(field.name());
					}
					for(IndexableField field : fields) {
						doc.add(field);
					}
					
					try {
						synchronized (this) {
							this.indexWriter.updateDocument(new Term("filename", filename), doc);
							this.indexWriter.commit();
							return true;
						}
					} catch (IOException e) {
						// TODO: handle exception
					}
				}
			}
			return false;
		} catch (IOException e) {
			throw new IllegalArgumentException("Index directory is wrong");
		}
	}
	
	public int index(File file) {
		DocumentHandler handler = null;
		String filename = null;
		try {
			File[] files;
			if (file.isDirectory()) {
				files = file.listFiles();
			}else {
				files = new File[1];
				files[0] = file;
			}
			
			for (File newFile : files) {
				if (newFile.isFile()) {
					filename = newFile.getName();
					handler = getHandler(filename);
					if (handler == null) {
						System.out.println("Nije moguce indeksirati ovaj dokument");
						continue;
					}
					IndexUnit iu = handler.getIndexUnit(newFile);
					Document d = iu.getLuceneDocument();
					this.indexWriter.addDocument(d);
				}else if (newFile.isDirectory()){
					index(newFile);
				}
			}
			this.indexWriter.commit();
			System.out.println("gotovo indeksiranjee");
		} catch (IOException e) {
			System.out.println("indexiranje nije izvrseno");
		}
		return this.indexWriter.numDocs();
	}
	
	public DocumentHandler getHandler(String filename) {
		if(filename.endsWith(".txt")) {
			return new TextDocHandler();
		}else if(filename.endsWith(".pdf")) {
			return new PDFHandler();
		}else {
			return null;
		}
	}
	
	protected void finalize() throws Throwable{
		this.indexWriter.close();
	}
	

}
