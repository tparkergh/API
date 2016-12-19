package gov.ca.cwds.rest.api.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ESFieldSearchEntry;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.QueryType;

public class ESRequestTest {

  private ESSearchRequest cut; // "class under test"

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    cut = new ESSearchRequest();
  }

  @Test
  public void testMixQueryType() throws Exception {
    cut.setDocumentType("person");
    cut.getRoot().addElem(new ESFieldSearchEntry("first_name", "bart", QueryType.MATCH));
    cut.getRoot().addElem(new ESFieldSearchEntry("last_name", "simps*", QueryType.WILDCARD));

    // For pretty JSON, instead of a single line.
    // new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(System.out, cut);

    StringWriter w = new StringWriter();
    new ObjectMapper().writeValue(w, cut);
    w.flush();
    w.close();

    final String compare =
        "{\"cwds_search\":{\"logic\":\"OR\",\"elems\":[{\"field\":\"first_name\",\"value\":\"bart\",\"elementType\":\"FIELD_TERM\",\"query_type\":\"MATCH\"},{\"field\":\"last_name\",\"value\":\"simps*\",\"elementType\":\"FIELD_TERM\",\"query_type\":\"WILDCARD\"}],\"elementType\":\"GROUP\"},\"document_type\":\"person\"}";
    assertThat("ES JSON incorrect", compare.equals(w.toString()));
  }


}
