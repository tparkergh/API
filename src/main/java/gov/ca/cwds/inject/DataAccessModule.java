package gov.ca.cwds.inject;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege;
import gov.ca.cwds.rest.api.persistence.auth.UserId;
import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.api.persistence.cms.CrossReport;
import gov.ca.cwds.rest.api.persistence.cms.Referral;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.persistence.ns.Address;
import gov.ca.cwds.rest.api.persistence.ns.Person;
import gov.ca.cwds.rest.api.persistence.ns.Screening;
import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;
import gov.ca.cwds.rest.jdbi.auth.StaffAuthorityPrivilegeDao;
import gov.ca.cwds.rest.jdbi.auth.StaffUnitAuthorityDao;
import gov.ca.cwds.rest.jdbi.auth.UserAuthorizationDao;
import gov.ca.cwds.rest.jdbi.auth.UserIdDao;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import gov.ca.cwds.rest.jdbi.cms.AttorneyDao;
import gov.ca.cwds.rest.jdbi.cms.ClientDao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocReferralClientDao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.jdbi.cms.CrossReportDao;
import gov.ca.cwds.rest.jdbi.cms.OtherClientNameDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralClientDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralDao;
import gov.ca.cwds.rest.jdbi.cms.ReporterDao;
import gov.ca.cwds.rest.jdbi.cms.StaffPersonDao;
import gov.ca.cwds.rest.jdbi.ns.AddressDao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;
import gov.ca.cwds.rest.jdbi.ns.ScreeningDao;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;

/**
 * DI (dependency injection) setup for data access objects (DAO).
 * 
 * @author CWDS API Team
 */
public class DataAccessModule extends AbstractModule {
  private final HibernateBundle<ApiConfiguration> cmsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(StaffPerson.class, Referral.class, Allegation.class,
          CrossReport.class, ReferralClient.class, Reporter.class, CmsDocument.class,
          CmsDocumentBlobSegment.class, CmsDocReferralClient.class, UserId.class,
          StaffAuthorityPrivilege.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getCmsDataSourceFactory();
        }

        @Override
        public String name() {
          return "cms";
        }
      };

  private final HibernateBundle<ApiConfiguration> nsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(Person.class, Address.class, Screening.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getNsDataSourceFactory();
        }

        @Override
        public String name() {
          return "ns";
        }
      };

  public DataAccessModule(Bootstrap<ApiConfiguration> bootstrap) {
    bootstrap.addBundle(cmsHibernateBundle);
    bootstrap.addBundle(nsHibernateBundle);
  }

  /**
   * {@inheritDoc}
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    bind(AllegationDao.class);
    bind(AttorneyDao.class);
    bind(ClientDao.class);
    bind(CmsDocReferralClientDao.class);
    bind(CmsDocumentDao.class);
    bind(CrossReportDao.class);
    bind(OtherClientNameDao.class);
    bind(ReferralClientDao.class);
    bind(ReferralDao.class);
    bind(ReporterDao.class);
    bind(StaffPersonDao.class);

    bind(UserAuthorizationDao.class);
    bind(UserIdDao.class);
    bind(StaffAuthorityPrivilegeDao.class);
    bind(StaffUnitAuthorityDao.class);

    bind(AddressDao.class);
    bind(PersonDao.class);
    bind(ScreeningDao.class);

    bind(ElasticsearchDao.class);
  }

  @Provides
  @CmsSessionFactory
  SessionFactory cmsSessionFactory() {
    return cmsHibernateBundle.getSessionFactory();
  }

  @Provides
  @NsSessionFactory
  SessionFactory nsSessionFactory() {
    return nsHibernateBundle.getSessionFactory();
  }

  @Provides
  public ElasticsearchConfiguration elasticSearchConfig(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getElasticsearchConfiguration();
  }

}
