package gov.ca.cwds.inject;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import gov.ca.cwds.data.ns.EthnicityDao;
import gov.ca.cwds.data.ns.LanguageDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.PersonAddressDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.data.ns.PersonEthnicityDao;
import gov.ca.cwds.data.ns.PersonLanguageDao;
import gov.ca.cwds.data.ns.PersonPhoneDao;
import gov.ca.cwds.data.ns.PersonRaceDao;
import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.data.ns.RaceDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import gov.ca.cwds.data.persistence.cms.ISystemCodeDao;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.Ethnicity;
import gov.ca.cwds.data.persistence.ns.Language;
import gov.ca.cwds.data.persistence.ns.Participant;
import gov.ca.cwds.data.persistence.ns.Person;
import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.data.persistence.ns.PersonAddressId;
import gov.ca.cwds.data.persistence.ns.PersonEthnicity;
import gov.ca.cwds.data.persistence.ns.PersonEthnicityId;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.data.persistence.ns.PersonLanguageId;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.data.persistence.ns.PersonPhoneId;
import gov.ca.cwds.data.persistence.ns.PersonRace;
import gov.ca.cwds.data.persistence.ns.PersonRaceId;
import gov.ca.cwds.data.persistence.ns.PhoneNumber;
import gov.ca.cwds.data.persistence.ns.Race;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.data.validation.SmartyStreetsDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.SmartyStreetsConfiguration;
import gov.ca.cwds.rest.api.ApiException;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;

/**
 * DI (dependency injection) setup for data access objects (DAO).
 * 
 * @author CWDS API Team
 */
public class DataAccessModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessModule.class);


  private Client client;

  private final HibernateBundle<ApiConfiguration> cmsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(Allegation.class, ClientCollateral.class,
          gov.ca.cwds.data.persistence.cms.Client.class, CmsDocReferralClient.class,
          CmsDocument.class, CmsDocumentBlobSegment.class, CollateralIndividual.class,
          CrossReport.class, EducationProviderContact.class, OtherAdultInPlacemtHome.class,
          OtherChildInPlacemtHome.class, OtherClientName.class, Referral.class,
          ReferralClient.class, Reporter.class, ServiceProvider.class, StaffPerson.class,
          SubstituteCareProvider.class) {
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
      new HibernateBundle<ApiConfiguration>(Person.class, Address.class, Screening.class,
          Participant.class, PersonAddressId.class, PersonAddress.class, PersonPhoneId.class,
          PhoneNumber.class, PersonPhone.class, PersonLanguageId.class, Language.class,
          PersonLanguage.class, PersonEthnicityId.class, PersonEthnicity.class, Ethnicity.class,
          PersonRaceId.class, PersonRace.class, Race.class) {
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
    bind(OtherClientNameDao.class);
    bind(ReferralClientDao.class);
    bind(ReferralDao.class);
    bind(ReporterDao.class);
    bind(StaffPersonDao.class);

    bind(AddressDao.class);
    bind(PersonDao.class);
    bind(ScreeningDao.class);
    bind(ParticipantDao.class);
    bind(PhoneNumberDao.class);
    bind(LanguageDao.class);
    bind(CrossReportDao.class);
    bind(PersonAddressDao.class);
    bind(PersonPhoneDao.class);
    bind(PersonLanguageDao.class);
    bind(PersonEthnicityDao.class);
    bind(EthnicityDao.class);
    bind(PersonRaceDao.class);
    bind(RaceDao.class);

    bind(ElasticsearchDao.class);
    bind(SmartyStreetsDao.class);

    // System code loader DAO.
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

  @Provides
  public Client elasticsearchClient(ApiConfiguration apiConfiguration) {
    if (client == null) {
      ElasticsearchConfiguration config = apiConfiguration.getElasticsearchConfiguration();
      try {
        Settings settings = Settings.settingsBuilder()
            .put("cluster.name", config.getElasticsearchCluster()).build();
        client = TransportClient.builder().settings(settings).build().addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
                Integer.parseInt(config.getElasticsearchPort())));
      } catch (Exception e) {
        LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
        throw new ApiException("Error initializing Elasticsearch client: " + e.getMessage(), e);
      }
    }

    return client;

  }

}
