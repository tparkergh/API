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
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryDaoIT implements DaoTestTemplate {

  private SessionFactory sessionFactory;
  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AagoG8c0ND";

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
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    allegationPerpetratorHistoryDao = new AllegationPerpetratorHistoryDao(sessionFactory);
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
    AllegationPerpetratorHistory found = allegationPerpetratorHistoryDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    AllegationPerpetratorHistory found = allegationPerpetratorHistoryDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        validDomainAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(
        "1234567ABC", vdaph.getCountySpecificCode(), vdaph.getVictimClientId(),
        vdaph.getAllegationId(), DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    AllegationPerpetratorHistory create =
        allegationPerpetratorHistoryDao.create(allegationPerpetratorHistory);
    assertThat(allegationPerpetratorHistory, is(create));
  }

  @Override
  @Test
  public void testCreateExistingEntityException() throws Exception {

    thrown.expect(EntityExistsException.class);

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        validDomainAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(id,
        vdaph.getCountySpecificCode(), vdaph.getVictimClientId(), vdaph.getAllegationId(),
        DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    allegationPerpetratorHistoryDao.create(allegationPerpetratorHistory);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    AllegationPerpetratorHistory deleted = allegationPerpetratorHistoryDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    AllegationPerpetratorHistory deleted = allegationPerpetratorHistoryDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        validDomainAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(id,
        vdaph.getCountySpecificCode(), vdaph.getVictimClientId(), vdaph.getAllegationId(),
        DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    AllegationPerpetratorHistory updated =
        allegationPerpetratorHistoryDao.update(allegationPerpetratorHistory);

    assertThat(allegationPerpetratorHistory, is(updated));

  }

  @Override
  @Test
  public void testUpdateEntityNotFoundException() throws Exception {

    thrown.expect(EntityNotFoundException.class);

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory vdaph =
        validDomainAllegationPerpetratorHistory();

    AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistory(
        "1234567ABC", vdaph.getCountySpecificCode(), vdaph.getVictimClientId(),
        vdaph.getAllegationId(), DomainChef.uncookDateString(vdaph.getPerpetratorUpdateDate()));

    allegationPerpetratorHistoryDao.update(allegationPerpetratorHistory);
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

  private gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory validDomainAllegationPerpetratorHistory()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory validDomainAllegationPerpetratorHistory =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/AllegationPerpetratorHistory/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory.class);
    return validDomainAllegationPerpetratorHistory;
  }


}
