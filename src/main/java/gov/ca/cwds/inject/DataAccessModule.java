package gov.ca.cwds.inject;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CmsDocReferralClientDao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.OtherClientNameDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.data.persistence.cms.ISystemCodeDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.Person;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.data.validation.SmartyStreetsDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.SmartyStreetsConfiguration;
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
          CmsDocumentBlobSegment.class, CmsDocReferralClient.class) {
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

  /**
   * Constructor takes the API config.
   * 
   * @param bootstrap the ApiConfiguration
   */
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

    bind(AddressDao.class);
    bind(PersonDao.class);
    bind(ScreeningDao.class);

    bind(ElasticsearchDao.class);
    bind(SmartyStreetsDao.class);
    bind(ISystemCodeDao.class).to(SystemCodeDaoFileImpl.class);
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

  @Provides
  public SmartyStreetsConfiguration smartystreetsConfig(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getSmartyStreetsConfiguration();
  }

}
