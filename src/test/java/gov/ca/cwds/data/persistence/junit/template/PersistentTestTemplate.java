package gov.ca.cwds.data.persistence.junit.template;

import org.junit.Test;

/**
 * 
 * Persistent Entity JUnits abstract class
 * 
 * these JUnit test must be included with any persistent classes.
 * 
 * @author CWDS API Team
 *
 */
public interface PersistentTestTemplate {

  @SuppressWarnings("javadoc")
  @Test
  public void testEmptyConstructor() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentContructor() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingDomain() throws Exception;

  @SuppressWarnings("javadoc")
  @Test
  public void testEqualsHashCodeWorks() throws Exception;


}
