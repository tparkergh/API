package gov.ca.cwds.rest.services.junit.template;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public interface ServiceTestTemplate {
  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public abstract void setup() throws Exception;

  /*
   * JUnit test for find
   */
  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityFindThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityFindReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityFindReturnsNullWhenNotFound() throws Exception;

  /*
   * JUnit test for delete
   */
  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityDeleteThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityDeleteDelegatesToCrudsService() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityDeleteReturnsNullWhenNotFound() throws Exception;

  /*
   * JUnit test for update
   */
  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityUpdateThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityUpdateReturnsPersistent() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityUpdateReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityUpdateThrowsExceptionWhenNotFound() throws Exception;

  /*
   * JUnit test for create
   */
  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateReturnsPostedClass() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateBlankIDError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateNullIDError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateEmptyError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testEntityCreateExistsError() throws Exception;

}
