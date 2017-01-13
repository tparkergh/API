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
  /*
   * if Service find() method throws Assertion Error
   */
  public abstract void testFindThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testFindReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testFindReturnsNullWhenNotFound() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testFindThrowsNotImplementedException() throws Exception;

  /*
   * JUnit test for delete
   */
  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service delete() method throws Assertion Error
   */
  public abstract void testDeleteThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testDeleteDelegatesToCrudsService() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testDeleteReturnsNullWhenNotFound() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service delete() method not implemented
   */
  public abstract void testDeleteThrowsNotImplementedException() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testDeleteReturnsClass() throws Exception;


  /*
   * JUnit test for update
   */
  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service update() throws Assertion Error
   */
  public abstract void testUpdateThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testUpdateReturnsDomain() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testUpdateReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testUpdateThrowsServiceException() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service update() method throws Exception
   */
  public abstract void testUpdateThrowsNotImplementedException() throws Exception;

  /*
   * JUnit test for create
   */

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service create() throws Assertion Error
   */
  public abstract void testCreateThrowsAssertionError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testCreateReturnsPostedClass() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testCreateReturnsCorrectEntity() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service create() throws error for blank ID
   */
  public abstract void testCreateBlankIDError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service create() throws error for null ID
   */
  public abstract void testCreateNullIDError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  /*
   * if Service create() throws error for empty ID
   */
  public abstract void testCreateEmptyIDError() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testCreateThrowsNotImplementedException() throws Exception;


}
