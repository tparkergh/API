package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.LawEnforcementEntity;
import gov.ca.cwds.fixture.CmsCrossReportResourceBuilder;
import gov.ca.cwds.fixture.LawEnforcementEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.StaffPerson;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RICrossReportTest {

  private ReferralDao referralDao;
  private StaffPersonDao staffPersonDao;
  private LawEnforcementDao lawEnforcementDao;

  @Before
  public void setup() {
    referralDao = mock(ReferralDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    lawEnforcementDao = mock(LawEnforcementDao.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(RICrossReport.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RICrossReport target = new RICrossReport(referralDao, staffPersonDao, lawEnforcementDao);
    assertThat(target, notNullValue());
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenReferralIsNotFound() throws Exception {

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234plo", crossReportDomain, "0X5");

    RICrossReport target = new RICrossReport(referralDao, staffPersonDao, lawEnforcementDao);
    when(referralDao.find(any(String.class))).thenReturn(null);
    target.apply(crossReport);
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenStaffPersonIsNotFound() throws Exception {

    CrossReport crossReportDomain =
        new CmsCrossReportResourceBuilder().setReferralId("H5CMVTm00k").build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234plo", crossReportDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00k", referralDomain, "0X5");

    RICrossReport target = new RICrossReport(referralDao, staffPersonDao, lawEnforcementDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(staffPersonDao.find(any(String.class))).thenReturn(null);
    target.apply(crossReport);
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenLawEnforcmentIsNotFound() throws Exception {

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().setReferralId("H5CMVTm00k")
        .setStaffPersonId("q1p").setLawEnforcementId("FDIp2i90kk").build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234plo", crossReportDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00k", referralDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.StaffPerson staffPersonDomain = new StaffPerson("2016-10-31",
        "John", "CEO", "Doe", "C", "Mr", new BigDecimal(9165551212L), 22, "2016-10-31", "III", true,
        "MIZN02k11B", "abc", "def", "99", false, "3XPCP92b24", "john.doe@anyco.com");
    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson("q1p", staffPersonDomain, "0X5");

    RICrossReport target = new RICrossReport(referralDao, staffPersonDao, lawEnforcementDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    target.apply(crossReport);
  }

  @Test
  public void riCheckPass() throws Exception {

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().setReferralId("H5CMVTm00k")
        .setStaffPersonId("q1p").setLawEnforcementId("FDIp2i90kk").build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234plo", crossReportDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00k", referralDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.StaffPerson staffPersonDomain = new StaffPerson("2016-10-31",
        "John", "CEO", "Doe", "C", "Mr", new BigDecimal(9165551212L), 22, "2016-10-31", "III", true,
        "MIZN02k11B", "abc", "def", "99", false, "3XPCP92b24", "john.doe@anyco.com");
    gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson =
        new gov.ca.cwds.data.persistence.cms.StaffPerson("q1p", staffPersonDomain, "0X5");

    LawEnforcementEntity lawEnforcement = new LawEnforcementEntityBuilder().build();

    RICrossReport target = new RICrossReport(referralDao, staffPersonDao, lawEnforcementDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(lawEnforcementDao.find(any(String.class))).thenReturn(lawEnforcement);
    Boolean result = target.apply(crossReport);
    assertThat(result, is(equalTo(true)));
  }

}


