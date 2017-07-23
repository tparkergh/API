package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import io.dropwizard.jackson.Jackson;

import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPersonService staffPersonService;
  private StaffPersonDao staffPersonDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    staffPersonDao = mock(StaffPersonDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    staffPersonService = new StaffPersonService(staffPersonDao, staffPersonIdRetriever);

  }

  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      staffPersonService.find(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectStaffPersonWhenFound() throws Exception {
    String id = "ABC";
    StaffPerson domainStaffPerson =
        new StaffPerson("2016-10-31", "John", "CEO", "Doe", "C", "Mr", new BigDecimal(9165551212L),
            22, "2016-10-31", "III", true, "MIZN02k11B", "abc", "def", "99", false, "3XPCP92b24",
            "john.doe@anyco.com");
    PostedStaffPerson expected = new PostedStaffPerson(domainStaffPerson, "ABC");
    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id,
            DomainChef.uncookDateString("2016-10-31"), "John", "CEO", "Doe", "C", "Mr",
            new BigDecimal(9165551212L), 22, DomainChef.uncookDateString("2016-10-31"), "III",
            DomainChef.cookBoolean(true), "MIZN02k11B", "abc", "def", "99",
            DomainChef.cookBoolean(false), "3XPCP92b24", "john.doe@anyco.com");

    when(staffPersonDao.find(id)).thenReturn(staffPerson);

    StaffPerson found = staffPersonService.find(id);

    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonService.find("0XA");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.delete("ABC");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.update("ABC", mock(StaffPerson.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    staffPersonService.create(mock(StaffPerson.class));
  }

}
