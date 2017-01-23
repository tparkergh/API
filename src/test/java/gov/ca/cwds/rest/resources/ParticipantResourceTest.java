package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.resource.junit.template.ResourceTestTemplate;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ParticipantResourceTest implements ResourceTestTemplate {

  private static final String ROOT_RESOURCE = "/participants/";
  private static final String FOUND_RESOURCE = "/participants/1";

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @SuppressWarnings("javadoc")
  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ParticipantResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {}


  @Override

  public void testGetDelegatesToResourceDelegate() throws Exception {

  }

  @Override
  public void testGet201ResourceSuccess() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  @Test
  public void testGet404NotFoundError() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  @Override
  public void testGet501NotImplemented() throws Exception {}

  @Override
  @Test
  public void testPostDelegatesToResourceDelegate() throws Exception {
    Participant participant =
        new Participant(1, 1, "Marge", "Simpson", "Female", "2017-01-11", "111223333");
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    verify(resourceDelegate).create(eq(participant));

  }

  @Override
  @Test
  public void testPostValidatesEntity() throws Exception {
    Participant participant =
        new Participant(1, 1, "Marge", "Simpson", "Female", "11-01-2017", "111223333");

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @Override
  public void testPost400JSONError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost406NotSupportedError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost409AlreadyExistsError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost422ValidationError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost200ResourceSuccess() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost501NotImplemented() throws Exception {}

  @Override
  @Test
  public void testDelete200ResourceSuccess() throws Exception {
    Participant participant =
        new Participant(1, 1, "Marge", "Simpson", "Female", "2017-01-23", "111223333");

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));

  }

  @Override
  public void testDelete404NotFoundError() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));

  }

  @Override
  public void testDelete501NotImplemented() throws Exception {

  }

  @Override
  public void testDeleteDelegatesToResource() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdateDelegatesToResourceDelegate() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate200ResourceSuccess() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate400JSONError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  @Test
  public void testUpdate404NotFoundError() throws Exception {
    Participant participant =
        new Participant(1, 1, "Marge", "Simpson", "Female", "2017-01-11", "111223333");
    int receivedStatus =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    int expectedStatus = 405;
    assertThat(receivedStatus, is(expectedStatus));
  }

  @Override
  public void testUpdate406NotSupportedError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate422ValidationError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate501NotImplemented() throws Exception {}



}
