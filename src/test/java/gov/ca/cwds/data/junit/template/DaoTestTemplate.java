package gov.ca.cwds.data.junit.template;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public interface DaoTestTemplate {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Before
  public abstract void setup() throws Exception;

  @SuppressWarnings("javadoc")
  @After
  public abstract void teardown() throws Exception;

  /*
   * Named Query JUnit Test
   * 
   * Test all named queries in the Dao class with the standard name of the persistent class plus the
   * suffix. Named queries are defined in the BaseDaoImpl class and the entity DAO class that
   * extends BaseDaoImpl.
   * 
   */
  @SuppressWarnings("javadoc")
  @Test
  public abstract void testFindAllNamedQueryExist() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public abstract void testFindAllReturnsCorrectList() throws Exception;

  /*
   * Find JUnit test
   * 
   */
  @SuppressWarnings("javadoc")
  public abstract void testFind() throws Exception;

  @SuppressWarnings("javadoc")
  public abstract void testFindEntityNotFoundException() throws Exception;

  /*
   * Create JUnit test
   */
  @SuppressWarnings("javadoc")
  public abstract void testCreate() throws Exception;

  @SuppressWarnings("javadoc")
  public abstract void testCreateExistingEntityException() throws Exception;

  /*
   * Delete JUnit test
   * 
   */
  @SuppressWarnings("javadoc")
  public abstract void testDelete() throws Exception;

  @SuppressWarnings("javadoc")
  public abstract void testDeleteEntityNotFoundException() throws Exception;

  /*
   * Update JUnit test
   */
  @SuppressWarnings("javadoc")
  public abstract void testUpdate() throws Exception;

  @SuppressWarnings("javadoc")
  public abstract void testUpdateEntityNotFoundException() throws Exception;

}
