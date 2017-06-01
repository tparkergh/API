package gov.ca.cwds.data.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationDaoIT implements DaoTestTemplate {

  private SessionFactory sessionFactory;
  private AllegationDao allegationDao;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "Aaeae9r0F4";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @Override
  @Before
  public void setup() {
    // TODO: Don't open a pool of connections for each test case!
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    allegationDao = new AllegationDao(sessionFactory);
  }

  /**
   * 
   */
  @Override
  @After
  public void teardown() {
    sessionFactory.close();
  }

  /**
   * Find JUnit test
   */
  @Override
  @Test
  public void testFind() throws Exception {
    Allegation found = allegationDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Allegation found = allegationDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda = validDomainAllegation();

    Allegation allegation =
        new Allegation("1234567ABC", DomainChef.uncookDateString(vda.getAbuseEndDate()),
            DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
            vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
            vda.getAllegationDispositionType(), vda.getAllegationType(),
            vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
            DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
            vda.getNonProtectingParentCode(),
            DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
            vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
            DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
            null, null);

    Allegation create = allegationDao.create(allegation);
    assertThat(allegation, is(create));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);
    gov.ca.cwds.rest.api.domain.cms.Allegation vda = validDomainAllegation();

    Allegation allegation = new Allegation(id, DomainChef.uncookDateString(vda.getAbuseEndDate()),
        DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
        vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
        vda.getAllegationDispositionType(), vda.getAllegationType(),
        vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
        DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
        vda.getNonProtectingParentCode(),
        DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
        vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
        DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
        null, null);

    allegationDao.create(allegation);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    Allegation deleted = allegationDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Allegation deleted = allegationDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Allegation vda = validDomainAllegation();

    Allegation allegation = new Allegation(id, DomainChef.uncookDateString(vda.getAbuseEndDate()),
        DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
        vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
        vda.getAllegationDispositionType(), vda.getAllegationType(),
        vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
        DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
        vda.getNonProtectingParentCode(),
        DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
        vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
        DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
        null, null);

    Allegation updated = allegationDao.update(allegation);
    assertThat(allegation, is(updated));
  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {

    thrown.expect(EntityNotFoundException.class);
    gov.ca.cwds.rest.api.domain.cms.Allegation vda = validDomainAllegation();

    Allegation allegation =
        new Allegation("1234567ABC", DomainChef.uncookDateString(vda.getAbuseEndDate()),
            DomainChef.uncookDateString(vda.getAbuseStartDate()), vda.getAbuseFrequency(),
            vda.getAbuseFrequencyPeriodCode(), vda.getAbuseLocationDescription(),
            vda.getAllegationDispositionType(), vda.getAllegationType(),
            vda.getDispositionDescription(), DomainChef.uncookDateString(vda.getDispositionDate()),
            DomainChef.cookBoolean(vda.getInjuryHarmDetailIndicator()),
            vda.getNonProtectingParentCode(),
            DomainChef.cookBoolean(vda.getStaffPersonAddedIndicator()), vda.getVictimClientId(),
            vda.getPerpetratorClientId(), vda.getReferralId(), vda.getCountySpecificCode(),
            DomainChef.cookBoolean(vda.getZippyCreatedIndicator()), vda.getPlacementFacilityType(),
            null, null);

    allegationDao.update(allegation);
  }

  /*
   * Named Query JUnit test
   */
  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

  private gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Allegation validDomainAllegation =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Allegation.class);
    return validDomainAllegation;
  }

}
