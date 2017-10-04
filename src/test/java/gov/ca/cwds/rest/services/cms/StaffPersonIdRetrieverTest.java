package gov.ca.cwds.rest.services.cms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import io.dropwizard.jackson.Jackson;


/**
 * @author CWDS API Team
 *
 */
public class StaffPersonIdRetrieverTest extends AbstractShiroTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @After
  public void tearDownSubject() {
    clearSubject();
  }

  @SuppressWarnings({"javadoc", "unchecked", "rawtypes"})
  @Test
  public void getStaffPersonIdReturnsHardCodedValueWhenUserInfoIsNotPassedIn() {
    Subject mockSubject = mock(Subject.class);
    PrincipalCollection principalCollection = mock(PrincipalCollection.class);

    List list = new ArrayList();
    list.add("mg");
    when(principalCollection.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(principalCollection);
    setSubject(mockSubject);
    String actual = new StaffPersonIdRetriever().getStaffPersonId();
    String expected = "0X5";
    assertEquals(actual, expected);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getStaffPersonIdReturnsHardCodedValueWhenPerryIsNotEnabled() {
    Subject mockSubject = mock(Subject.class);
    setSubject(mockSubject);
    String actual = new StaffPersonIdRetriever().getStaffPersonId();
    String expected = "0X5";
    assertEquals(actual, expected);
  }

  @SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
  @Test
  public void getStaffPersonIdReturnsHardCodedValueWhenStaffIdIsNotProvided() throws Exception {
    Subject mockSubject = mock(Subject.class);
    PrincipalCollection pc = mock(PrincipalCollection.class);
    List list = new ArrayList();
    list.add("mg");
    list.add(MAPPER.readValue("{ \"user\" : \"guest\"}", PerryUserIdentity.class));
    when(pc.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(pc);
    setSubject(mockSubject);
    String actual = new StaffPersonIdRetriever().getStaffPersonId();
    String expected = "0X5";
    assertEquals(actual, expected);
  }

  @SuppressWarnings({"javadoc", "rawtypes", "unchecked"})
  @Test
  public void getStaffPersonIdReturnsStaffIdValue() throws Exception {
    Subject mockSubject = mock(Subject.class);
    PrincipalCollection pc = mock(PrincipalCollection.class);
    List list = new ArrayList();
    list.add("mg");
    list.add(MAPPER.readValue(
        "{ \"user\" : \"mg\", \"roles\": [\"role1\", \"role2\"], \"staffId\": \"q1p\"}",
        PerryUserIdentity.class));
    when(pc.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(pc);
    setSubject(mockSubject);
    String actual = new StaffPersonIdRetriever().getStaffPersonId();
    String expected = "q1p";
    assertEquals(actual, expected);
  }

}
