package sub.gdz;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class EmbeddedSolrTest {

	private static SolrWrapper solr;

	@BeforeClass
	public static void beforeAllTests() throws Exception {
		CoreContainer container = new CoreContainer("solr");
		container.load();
		EmbeddedSolrServer solrEmbedded = new EmbeddedSolrServer(container, "gdz");
		solr = new SolrWrapper(solrEmbedded, "gdz");
	}

	@AfterClass
	public static void afterAllTests() throws Exception {
		solr.close();
	}

	@After
	public void afterEach() throws Exception {
		solr.clean();
	}

	@Test
	public void should() throws Exception {
		String[][] doc = { { "id", "1" }, {"fulltext", "tesst"} };
		solr.addDocument(doc);

		solr.select("tesst");
		assertEquals(1, solr.results());
	}

}
