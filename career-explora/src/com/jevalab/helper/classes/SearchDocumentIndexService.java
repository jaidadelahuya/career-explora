package com.jevalab.helper.classes;

import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

public class SearchDocumentIndexService {

	public static void indexDocuments (String indexName, List<Document> documents) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
	    Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	    
	    try {
	    	//Put the Document in the Index. If the document is already existing, it will be overwritten
	        index.put(documents);
	    } catch (PutException e) {
	        if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
	            // retry putting the document
	        }
	    }
	}
	
	public static void indexDocument (String indexName, Document document) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
	    Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	    
	    try {
	    	//Put the Document in the Index. If the document is already existing, it will be overwritten
	        index.put(document);
	    } catch (PutException e) {
	        if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
	            // retry putting the document
	        }
	    }
	}
	
	/**
	 * This method is used to retrieve a particular Document from the Index
	 * @param documentId This is the key field that uniquely identifies a document in the collection i.e. the Index. In our case
	 * it is the user id
	 * @return An instance of the Document object from the Index.
	 */
	public static Document retrieveDocument(String indexName, String documentId) {
		//Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		//Retrieve the Record from the Index
		return index.get(documentId);
	}
	
	public static Results<ScoredDocument> retrieveDocuments(String indexName, Query  q) {
		//Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		//Retrieve the Record from the Index
		return index.search(q);
	}
	
	/**
	 * This method is used to retrieve a list of documents from the Index that match the Search Term.
	 * @param searchText The search term to find matching documents. By default, if you do not use the Search Language Syntax,
	 * it will retrieve all the records that contain a partial or full text match for all attributes of a document
	 * @return A collection of Documents that were found
	 */
	public static Results<ScoredDocument> retrieveDocuments(String indexName, String searchText) {
		//Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		
		//Retrieve the Records from the Index
		return index.search(searchText);
	}
	
	/**
	 * This method is used to delete a document from the Index
	 * @param documentId This is the key field that uniquely identifies a document in the collection i.e. the Index. In our case
	 * it is the user id
	 */
	
	public static void deleteDocumentFromIndex(String indexName, String documentId) {
		//Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		
		//Delete the Records from the Index
		index.delete(documentId);
	}

	
}
