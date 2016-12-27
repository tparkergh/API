package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;

/**
 * 
 * @author Tabpcenc1
 */
public class AllegationDaoIT {

  private SessionFactory sessionFactory;
  private AllegationDao allegationDao;
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String id = "Aaeae9r0F4";
  private String abuseEndDateString = "2016-11-22";
  private String abuseStartDateString = "2016-09-12";
  private Short abuseFrequency = 1;
  private String abuseFrequencyPeriodCode = "M";
  private String abuseLocationDescription = "Home";
  private Short allegationDispositionType = 2;
  private Short allegationType = 3;
  private String dispositionDescription = "disposition description";
  private String dispositionDateString = "2016-11-22";
  private String injuryHarmDetailIndicator = "Y";
  private String nonProtectingParentCode = "N";
  private String staffPersonAddedIndicator = "N";
  private String victimClientId = "1234567890";
  private String perpetratorClientId = "0987654321";
  private String referralId = "2345678901";
  private String countySpecificCode = "099";
  private String zippyCreatedIndicator = "Y";
  private Short placementFacilityType = 4;

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    allegationDao = new AllegationDao(sessionFactory);
  }

  /**
   * 
   */
  @After
  public void teardown() {
    sessionFactory.close();
  }

  /**
   * 
   */
  @Test
  public void testFind() {
    String id = "Aaeae9r0F4";
    Allegation found = allegationDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testCreate() throws Exception {
    Date abuseEndDate = df.parse(abuseEndDateString);
    Date abuseStartDate = df.parse(abuseStartDateString);
    Date dispositionDate = df.parse(dispositionDateString);
    Allegation allegation =
        new Allegation("1234567890", abuseEndDate, abuseStartDate, abuseFrequency,
            abuseFrequencyPeriodCode, abuseLocationDescription, allegationDispositionType,
            allegationType, dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
            nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId,
            referralId, countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    Allegation create = allegationDao.create(allegation);
    assertThat(allegation, is(create));
  }

  /**
   * @throws Exception
   */
  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date abuseEndDate = df.parse(abuseEndDateString);
    Date abuseStartDate = df.parse(abuseStartDateString);
    Date dispositionDate = df.parse(dispositionDateString);
    Allegation allegation = new Allegation(id, abuseEndDate, abuseStartDate, abuseFrequency,
        abuseFrequencyPeriodCode, abuseLocationDescription, allegationDispositionType,
        allegationType, dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
        nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId,
        referralId, countySpecificCode, zippyCreatedIndicator, placementFacilityType);
    allegationDao.create(allegation);
  }

  /**
   * 
   */
  @Test
  public void testDelete() {
    String id = "Aaeae9r0F4";
    Allegation deleted = allegationDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() throws Exception {
    Date abuseEndDate = df.parse(abuseEndDateString);
    Date abuseStartDate = df.parse(abuseStartDateString);
    Date dispositionDate = df.parse(dispositionDateString);
    Allegation allegation = new Allegation(id, abuseEndDate, abuseStartDate, abuseFrequency,
        abuseFrequencyPeriodCode, abuseLocationDescription, allegationDispositionType,
        allegationType, dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
        nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId,
        referralId, countySpecificCode, zippyCreatedIndicator, placementFacilityType);
    Allegation update = allegationDao.update(allegation);
    assertThat(allegation, is(update));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);

    Date abuseEndDate = df.parse(abuseEndDateString);
    Date abuseStartDate = df.parse(abuseStartDateString);
    Date dispositionDate = df.parse(dispositionDateString);
    Allegation allegation = new Allegation("abc", abuseEndDate, abuseStartDate, abuseFrequency,
        abuseFrequencyPeriodCode, abuseLocationDescription, allegationDispositionType,
        allegationType, dispositionDescription, dispositionDate, injuryHarmDetailIndicator,
        nonProtectingParentCode, staffPersonAddedIndicator, victimClientId, perpetratorClientId,
        referralId, countySpecificCode, zippyCreatedIndicator, placementFacilityType);
    allegationDao.update(allegation);
  }

}
