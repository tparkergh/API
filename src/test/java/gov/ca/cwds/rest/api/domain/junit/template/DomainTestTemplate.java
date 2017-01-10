package gov.ca.cwds.rest.api.domain.junit.template;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * 
 * Domain JUnit test template
 * 
 * @author CWDS API Team
 *
 */
public interface DomainTestTemplate {

  @SuppressWarnings("javadoc")
  @ClassRule


  @Before
  public abstract void setup() throws Exception;

  @SuppressWarnings("javadoc")
  @After
  public abstract void teardown() throws Exception;

  /*
   * class level JUnits
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testEqualsHashCodeWorks() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testSerializesToJSON() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testDeserializesFromJSON() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testPersistentConstructor() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testJSONCreatorConstructor() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testSuccessWithValid() throws Exception;


  @SuppressWarnings("javadoc")
  @Test
  public abstract void testSuccessWithOptionalsNotIncluded() throws Exception;

  /*
   * Property JUnit Test
   * 
   * for each property of the bean, create appropriate tests depending upon the annotations on the
   * property
   * 
   */

  /*
   * Case: Boolean NotNull NotRequired
   * 
   * @NotNull
   * 
   * @ApiModelProperty(required = false, readOnly = false) private Boolean property;
   */

  /*
   * Case: Boolean NotNull Required
   * 
   * @NotNull
   * 
   * @ApiModelProperty(required = true) private Boolean property
   * 
   */

}
