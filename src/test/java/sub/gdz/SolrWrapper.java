package sub.gdz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrWrapper {

	private SolrClient solrServerClient;
	private String core;
	private String user;
	private String password;
	private SolrDocumentList docList;
	private Map<String, Map<String, List<String>>> highlightings;
	private GroupResponse groupList;

	public SolrWrapper(SolrClient newSolrThing, String solrCore) {
		this(newSolrThing, solrCore, null, null);
	}

	public SolrWrapper(SolrClient newSolrThing, String solrCore, String solrUser, String solrPassword) {
		solrServerClient = newSolrThing;
		core = solrCore;
		user = solrUser;
		password = solrPassword;
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
			if (user != null && password != null) {
				SolrRequest<QueryResponse> req = new QueryRequest(solrQuery);
				req.setBasicAuthCredentials(user, password);
				response = req.process(solrServerClient, core);
			} else {
				response = solrServerClient.query(core, solrQuery);
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not execute '" + query + "'", e);
		}

		docList = response.getResults();
		highlightings = response.getHighlighting();
		groupList = response.getGroupResponse();
	}

	public long results() {
		return docList.getNumFound();
	}

	public long groupResults() {
		return groupList.getValues().get(0).getMatches();
	}

	public List<String> ids(int number) {
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			String id = (String) docList.get(i).get("id");
			idList.add(id);
		}
		return idList;
	}

	public void clean() throws Exception {
		solrServerClient.deleteByQuery(core, "*:*");
		solrServerClient.commit(core);
	}

	public void close() throws IOException {
		solrServerClient.close();
	}

}
