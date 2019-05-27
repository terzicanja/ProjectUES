package rs.ac.uns.ftn.ues.lucene.indexing.handlers;

import java.io.File;

import rs.ac.uns.ftn.ues.lucene.model.IndexUnit;

public abstract class DocumentHandler {
	
	public abstract IndexUnit getIndexUnit(File file);
	public abstract String getText(File file);

}
