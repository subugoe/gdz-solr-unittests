package sub.gdz;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrWrapper {

	private SolrClient solrServerClient;
	private String core;
	private SolrDocumentList docList;
	private Map<String, Map<String, List<String>>> highlightings;

	public SolrWrapper(SolrClient newSolrThing, String solrCore) {
		solrServerClient = newSolrThing;
		core = solrCore;
	}

	public void addDocument(String[][] documentFields) throws Exception {
		SolrInputDocument newDoc = new SolrInputDocument();
		for (String[] docField : documentFields) {
			newDoc.addField(docField[0], docField[1]);
		}
		solrServerClient.add(core, newDoc);
		solrServerClient.commit(core);
	}

	public void select(String query) {
		ask(new String[][] {}, query, "/select");
	}

	public void select(String[][] extraParams, String query) {
		ask(extraParams, query, "/select");
	}

	private void ask(String[][] extraParams, String query, String requestHandler) {
		SolrQuery solrQuery = new SolrQuery(query);
		solrQuery.setRequestHandler(requestHandler);
		for (String[] parameter : extraParams) {
			solrQuery.set(parameter[0], parameter[1]);
		}
		QueryResponse response;
		try {
			response = solrServerClient.query(core, solrQuery);
		} catch (Exception e) {
			throw new RuntimeException("Could not execute '" + query + "'", e);
		}

		docList = response.getResults();
		highlightings = response.getHighlighting();
	}

	public long results() {
		return docList.getNumFound();
	}

	public void clean() throws Exception {
		solrServerClient.deleteByQuery(core, "*:*");
		solrServerClient.commit(core);
	}

	public void close() throws IOException {
		solrServerClient.close();
	}

}
