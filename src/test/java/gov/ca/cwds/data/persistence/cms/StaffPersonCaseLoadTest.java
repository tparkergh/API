package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.fixture.StaffPersonCaseLoadResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 */
public class StaffPersonCaseLoadTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testEmptyConstructorSuccess() throws Exception {
    assertThat(StaffPersonCaseLoad.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingDomainSuccess() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().build();

    StaffPersonCaseLoad persistent = new StaffPersonCaseLoad(builder.getThirdId(), builder, "0X5");

    assertThat(builder.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(builder.getEndDate(),
        is(equalTo(persistent.getEndDate() == null ? "" : df.format(persistent.getEndDate()))));
    assertThat(builder.getFkCaseLoad(), is(equalTo(persistent.getFkCaseLoad())));
    assertThat(builder.getFkStaffPerson(), is(equalTo(persistent.getFkStaffPerson())));
    assertThat(builder.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(builder.getThirdId(), is(equalTo(persistent.getThirdId())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().build();

    StaffPersonCaseLoad persistent = new StaffPersonCaseLoad(builder.getCountyCode(),
        DomainChef.uncookDateString(builder.getEndDate()), builder.getFkCaseLoad(),
        builder.getFkStaffPerson(), DomainChef.uncookDateString(builder.getStartDate()),
        builder.getThirdId());

    assertThat(builder.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(builder.getEndDate(),
        is(equalTo(persistent.getEndDate() == null ? "" : df.format(persistent.getEndDate()))));
    assertThat(builder.getFkCaseLoad(), is(equalTo(persistent.getFkCaseLoad())));
    assertThat(builder.getFkStaffPerson(), is(equalTo(persistent.getFkStaffPerson())));
    assertThat(builder.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(builder.getThirdId(), is(equalTo(persistent.getThirdId())));

  }
}

