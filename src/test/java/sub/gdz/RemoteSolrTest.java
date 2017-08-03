package sub.gdz;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoteSolrTest {
	private static SolrWrapper solr;

	@BeforeClass
	public static void beforeAllTests() throws Exception {
		String solrUrl = "http://localhost:8983/solr";
		String core = "gdz";
		String user = "user";
		String password = "blub";
		SolrClient solrServerClient = new HttpSolrClient(solrUrl);
		solr = new SolrWrapper(solrServerClient, core, user, password);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/* {f.year_publish.facet.limit=1000&
	 * fl=page_key,doctype,id,product,title,creator,year_publish,log_part_key,date_indexed&
	 * f.facet_creator_personal.facet.mincount=1&
	 * f.facet_creator_personal.facet.sort=count&
	 * f.facet_publisher.facet.sort=count&
	 * f.year_publish.facet.sort=count&
	 * f.year_publish.facet.mincount=1&
	 * wt=json&
	 * f.facet_place_publish.facet.limit=100&
	 * facet.field={!key%3Dfacet_creator_personal}facet_creator_personal&
	 * facet.field={!key%3Dfacet_publisher}facet_publisher&
	 * facet.field={!key%3Dfacet_place_publish}facet_place_publish&
	 * facet.field={!key%3Dyear_publish}year_publish&
	 * facet.field={!key%3Ddc}dc&
	 * f.dc.facet.mincount=1&
	 * json.nl=flat&
	 * f.facet_publisher.facet.limit=100&
	 * f.facet_place_publish.facet.sort=count&
	 * f.dc.facet.limit=100&
	 * start=0&
	 * f.facet_creator_personal.facet.limit=100&
	 * sort=score+desc&
	 * rows=10&
	 * q=*:*+AND+fulltext:gauß+AND+!dc:archaeo18+AND+!doctype:fulltext&
	 * omitHeader=true&
	 * f.dc.facet.sort=count&
	 * f.facet_publisher.facet.mincount=1&
	 * f.facet_place_publish.facet.mincount=1&
	 * facet=true}
	 
	*/ 
	@Test
	public void shouldCurrent() throws Exception {
		String[][] extraParams = { 
				{"f.year_publish.facet.limit", "1000"},
				{"fl", "page_key,doctype,id,product,title,creator,year_publish,log_part_key,date_indexed"},
				{"f.facet_creator_personal.facet.mincount", "1"},
				{"f.facet_creator_personal.facet.sort", "count"},
				{"f.facet_publisher.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.mincount", "1"},
				{"f.facet_place_publish.facet.limit", "100"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_place_publish"},
				{"facet.field", "year_publish"},
				{"facet.field", "dc"},
				{"f.dc.facet.mincount", "1"},
				{"f.facet_publisher.facet.limit", "100"},
				{"f.facet_place_publish.facet.sort", "count"},
				{"f.dc.facet.limit", "100"},
				{"start", "0"},
				{"f.facet_creator_personal.facet.limit", "100"},
				{"sort", "score desc"},
				{"rows", "10"},
				{"omitHeader", "true"},
				{"f.dc.facet.sort", "count"},
				{"f.facet_publisher.facet.mincount", "1"},
				{"f.facet_place_publish.facet.mincount", "1"},
				{"facet", "true"}
		};
		solr.select(extraParams, "*:* AND fulltext:gauß AND !dc:archaeo18 AND !doctype:fulltext");
		
		
		String[][] extraParams2 = { 
				{"f.year_publish.facet.limit", "1000"},
				{"fl", "page_key,doctype,id,product,title,creator,year_publish,log_part_key,date_indexed"},
				{"f.facet_creator_personal.facet.mincount", "1"},
				{"f.facet_creator_personal.facet.sort", "count"},
				{"f.facet_publisher.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.mincount", "1"},
				{"f.facet_place_publish.facet.limit", "100"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_place_publish"},
				{"facet.field", "year_publish"},
				{"facet.field", "dc"},
				{"f.dc.facet.mincount", "1"},
				{"f.facet_publisher.facet.limit", "100"},
				{"f.facet_place_publish.facet.sort", "count"},
				{"f.dc.facet.limit", "100"},
				{"start", "0"},
				{"f.facet_creator_personal.facet.limit", "100"},
				{"sort", "score desc"},
				{"rows", "15"},
				{"omitHeader", "true"},
				{"f.dc.facet.sort", "count"},
				{"f.facet_publisher.facet.mincount", "1"},
				{"f.facet_place_publish.facet.mincount", "1"},
				{"facet", "true"}
		};
		solr.select(extraParams2, "*:* AND fulltext:gauß AND !dc:archaeo18 AND !doctype:fulltext");
		assertEquals(760, solr.results());
		
		List<String> idList = solr.ids(15);
		
		for (String workId : idList) {
			
			/*
			 * json.nl=flat&
			 * hl=true&
			 * fl=*,score&
			 * start=0&
			 * hl.fragsize=75&
			 * sort=ft_page_number+ASC&
			 * rows=500&
			 * hl.simple.pre=<mark+class%3D"result_mark">&
			 * hl.snippets=100&
			 * q=ft_of_work:PPN335994989+AND+ft:gauß&
			 * hl.simple.post=</mark>&
			 * omitHeader=true&
			 * hl.fl=ft&
			 * wt=json
			 */
			String[][] extraParams3 = { 
					{"hl", "true"},
					{"fl", "*,score"},
					{"start", "0"},
					{"hl.fragsize", "75"},
					{"sort", "ft_page_number ASC"},
					{"rows", "500"},
					{"hl.simple.pre", "<mark class=\"result_mark\">"},
					{"hl.snippets", "100"},
					{"hl.simple.post", "</mark>"},
					{"omitHeader", "true"},
					{"hl.fl", "ft"}
			};
			
			solr.select(extraParams3, "ft_of_work:" + workId + " AND ft:gauß");

		}
	}

	@Test
	public void shouldNew() throws Exception {
		String[][] extraParams = { 
				{"f.year_publish.facet.limit", "1000"},
				{"fl", "page_key,doctype,id,product,title,creator,year_publish,log_part_key,date_indexed"},
				{"f.facet_creator_personal.facet.mincount", "1"},
				{"f.facet_creator_personal.facet.sort", "count"},
				{"f.facet_publisher.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.mincount", "1"},
				{"f.facet_place_publish.facet.limit", "100"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_place_publish"},
				{"facet.field", "year_publish"},
				{"facet.field", "dc"},
				{"f.dc.facet.mincount", "1"},
				{"f.facet_publisher.facet.limit", "100"},
				{"f.facet_place_publish.facet.sort", "count"},
				{"f.dc.facet.limit", "100"},
				{"start", "0"},
				{"f.facet_creator_personal.facet.limit", "100"},
				{"sort", "score desc"},
				{"rows", "10"},
				{"omitHeader", "true"},
				{"f.dc.facet.sort", "count"},
				{"f.facet_publisher.facet.mincount", "1"},
				{"f.facet_place_publish.facet.mincount", "1"},
				{"facet", "true"}
		};
		solr.select(extraParams, "*:* AND fulltext:gauß AND !dc:archaeo18 AND !doctype:fulltext");
		
		
		String[][] extraParams2 = { 
				{"f.year_publish.facet.limit", "1000"},
				{"fl", "page_key,doctype,id,product,title,creator,year_publish,log_part_key,date_indexed"},
				{"f.facet_creator_personal.facet.mincount", "1"},
				{"f.facet_creator_personal.facet.sort", "count"},
				{"f.facet_publisher.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.sort", "count"},
				{"f.year_publish.facet.mincount", "1"},
				{"f.facet_place_publish.facet.limit", "100"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_publisher"},
				{"facet.field", "facet_place_publish"},
				{"facet.field", "year_publish"},
				{"facet.field", "dc"},
				{"f.dc.facet.mincount", "1"},
				{"f.facet_publisher.facet.limit", "100"},
				{"f.facet_place_publish.facet.sort", "count"},
				{"f.dc.facet.limit", "100"},
				{"start", "0"},
				{"f.facet_creator_personal.facet.limit", "100"},
				{"sort", "score desc"},
				{"rows", "15"},
				{"omitHeader", "true"},
				{"f.dc.facet.sort", "count"},
				{"f.facet_publisher.facet.mincount", "1"},
				{"f.facet_place_publish.facet.mincount", "1"},
				{"facet", "true"}
		};
		solr.select(extraParams2, "*:* AND fulltext:gauß AND !dc:archaeo18 AND !doctype:fulltext");
		assertEquals(760, solr.results());
		
			/*
			 * json.nl=flat&
			 * hl=true&
			 * fl=*,score&
			 * start=0&
			 * hl.fragsize=75&
			 * sort=ft_page_number+ASC&
			 * rows=500&
			 * hl.simple.pre=<mark+class%3D"result_mark">&
			 * hl.snippets=100&
			 * q=ft_of_work:PPN335994989+AND+ft:gauß&
			 * hl.simple.post=</mark>&
			 * omitHeader=true&
			 * hl.fl=ft&
			 * wt=json
			 */
		
			/* hl.snippets=100&
			 * group=true&
			 * group.field=ft_of_work&
			 * group.limit=10000&
			 * group.sort=ft_page_number asc
			 * 
			 */
			String[][] extraParams3 = { 
					{"hl", "true"},
					{"fl", "id"},
					{"start", "0"},
					{"hl.fragsize", "75"},
					{"sort", "ft_page_number ASC"},
					{"rows", "15"},
					{"hl.simple.pre", "<mark class=\"result_mark\">"},
					{"hl.snippets", "100"},
					{"hl.simple.post", "</mark>"},
					{"omitHeader", "true"},
					{"group", "true"},
					{"group.field", "ft_of_work"},
					{"group.limit", "10000"},
					{"group.sort", "ft_page_number asc"},
					{"hl.fl", "ft"}
			};
			
			solr.select(extraParams3, "ft:gauß");
			assertEquals(4822, solr.groupResults());

	}


}
