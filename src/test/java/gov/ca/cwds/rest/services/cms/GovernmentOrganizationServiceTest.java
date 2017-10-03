package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.junit.Test;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity;
import gov.ca.cwds.fixture.GovernmentOrganizationEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationServiceTest {

  @Test
  public void type() throws Exception {
    assertThat(GovernmentOrganizationService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = null;
    LawEnforcementDao lawEnforcementDao = null;
    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    assertThat(target, notNullValue());
  }

  @Test
  public void handleFind_Args__String() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    when(governmentOrganizationDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity>());
    when(lawEnforcementDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.LawEnforcementEntity>());

    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    String searchForThis = null;
    GovernmentOrganizationResponse actual = target.handleFind(searchForThis);
    GovernmentOrganizationResponse expected =
        new GovernmentOrganizationResponse(new ArrayList<GovernmentOrganization>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = ServiceException.class)
  public void handleFind_Args__ServiceException() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    when(governmentOrganizationDao.findAll()).thenThrow(Exception.class);
    when(lawEnforcementDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.LawEnforcementEntity>());

    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    String searchForThis = null;
    GovernmentOrganizationResponse actual = target.handleFind(searchForThis);

  }

  @Test
  public void handleFind_Args__ByCountyId() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    List<GovernmentOrganizationEntity> govOrgs = new ArrayList<GovernmentOrganizationEntity>();
    GovernmentOrganizationEntity governmentOrganizationEntity =
        new GovernmentOrganizationEntityBuilder().setGovernmentEntityType((short) 1094)
            .setGovernmentOrganizationType((short) 1128).build();
    govOrgs.add(governmentOrganizationEntity);
    when(governmentOrganizationDao.findAll()).thenReturn(govOrgs);
    when(lawEnforcementDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.LawEnforcementEntity>());

    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    String searchForThis = "1094";
    GovernmentOrganizationResponse actual = target.handleFind(searchForThis);
    assertThat(actual.getGovernmentOrganizations().size(), is(equalTo(1)));
  }

  @Test
  public void handleFind_Args__OnlyStateLicensing() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    List<GovernmentOrganizationEntity> govOrgs = new ArrayList<GovernmentOrganizationEntity>();
    GovernmentOrganizationEntity governmentOrganizationEntity =
        new GovernmentOrganizationEntityBuilder().setGovernmentEntityType((short) 1094)
            .setGovernmentOrganizationType((short) 1128).build();
    govOrgs.add(governmentOrganizationEntity);
    when(governmentOrganizationDao.findAll()).thenReturn(govOrgs);
    when(lawEnforcementDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.LawEnforcementEntity>());

    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    String searchForThis = "0";
    GovernmentOrganizationResponse actual = target.handleFind(searchForThis);
    assertThat(actual.getGovernmentOrganizations().size(), is(equalTo(1)));
  }

  @Test
  public void handleFind_Args__All() throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    List<GovernmentOrganizationEntity> govOrgs = new ArrayList<GovernmentOrganizationEntity>();
    GovernmentOrganizationEntity governmentOrganizationEntity =
        new GovernmentOrganizationEntityBuilder().setGovernmentEntityType((short) 1094)
            .setGovernmentOrganizationType((short) 1128).build();
    govOrgs.add(governmentOrganizationEntity);
    when(governmentOrganizationDao.findAll()).thenReturn(govOrgs);
    when(lawEnforcementDao.findAll())
        .thenReturn(new ArrayList<gov.ca.cwds.data.persistence.cms.LawEnforcementEntity>());

    GovernmentOrganizationService target =
        new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    String searchForThis = null;
    GovernmentOrganizationResponse actual = target.handleFind(searchForThis);
    assertThat(actual.getGovernmentOrganizations().size(), is(equalTo(1)));
  }

}
