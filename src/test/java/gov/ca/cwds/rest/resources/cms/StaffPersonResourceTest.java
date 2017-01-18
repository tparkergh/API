package gov.ca.cwds.rest.resources.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.StaffPerson;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class StaffPersonResourceTest {

  private static final String ROOT_RESOURCE = "/_staffpersons/";
  private static final String FOUND_RESOURCE = "/_staffpersons/abc";

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @SuppressWarnings("javadoc")
  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new StaffPersonResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {}

  /**
   * Get Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("abc");
  }

  /**
   * Create Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    StaffPerson staffperson = new StaffPerson("1969-01-01", "Bart", "Student", "Simpson", "Q", "",
        new BigDecimal(9165551212L), 124, "1969-01-01", "", true, "abcdefghij", "description", "id",
        "03", false, "1234567890", "emailaddy");
    final Response resp =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(staffperson, MediaType.APPLICATION_JSON));

    ValidationErrorMessage msg = resp.readEntity(ValidationErrorMessage.class);
    if (msg != null) {
      final List<String> errors = msg.getErrors();
      if (errors != null && errors.size() > 0) {
        for (String err : errors) {
          System.out.println("ERROR: " + err);
        }
      }
    }

    verify(resourceDelegate).create(eq(staffperson));
  }

  @Test
  public void createValidatesEntity() throws Exception {
    StaffPerson staffperson = new StaffPerson("1969-01-01", "Bart", "Student", "Simpson", "Q", "",
        new BigDecimal(9165551212L), 124, "1969-01-01", "", true, "abcdefghij", "description", "id",
        "WILL_NOT_VALIDATE", false, "1234567890", "emailaddy");
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(staffperson, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  /**
   * Delete Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void deleteDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate, atLeastOnce()).delete("abc");

  }

  @Test
  public void deleteEntity() throws Exception {
    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    assertThat(status, is(204));

  }

  /**
   * Update Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    StaffPerson staffperson = new StaffPerson("1969-01-01", "Bart", "Student", "Simpson", "Q", "",
        new BigDecimal(9165551212L), 124, "1969-01-01", "", true, "abcdefghij", "description", "id",
        "03", false, "1234567890", "emailaddy");
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(staffperson, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq("abc"), eq(staffperson));
  }

  @Test
  public void udpateValidatesEntity() throws Exception {
    StaffPerson staffperson = new StaffPerson("1969-01-01", "Bart", "Student", "Simpson", "Q", "",
        new BigDecimal(9165551212L), 124, "1969-01-01", "", true, "abcdefghij", "description", "id",
        "WILL_NOT_VALIDATE", false, "1234567890", "emailaddy");
    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(staffperson, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }
}
