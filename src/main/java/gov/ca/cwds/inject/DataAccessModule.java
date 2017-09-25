package gov.ca.cwds.inject;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.data.cms.AddressUcDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.CaseAssignmentDao;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientCollateralDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.cms.CmsDocReferralClientDao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.ExternalInterfaceDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.OtherClientNameDao;
import gov.ca.cwds.data.cms.ReferralAssignmentDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.EthnicityDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
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
import gov.ca.cwds.data.persistence.cms.AddressUc;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.BaseAssignment;
import gov.ca.cwds.data.persistence.cms.CaseAssignment;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.data.persistence.cms.ClientUc;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.data.persistence.cms.CountyTriggerEmbeddable;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import gov.ca.cwds.data.persistence.cms.ExternalInterface;
import gov.ca.cwds.data.persistence.cms.GovernmentOrganization;
import gov.ca.cwds.data.persistence.cms.LawEnforcement;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralAssignment;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.data.persistence.cms.Tickle;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.Ethnicity;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
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
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.data.validation.SmartyStreetsDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.SmartyStreetsConfiguration;
import gov.ca.cwds.rest.TriggerTablesConfiguration;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientCollateral;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;
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

  private Map<String, Client> clients;

  private final HibernateBundle<ApiConfiguration> cmsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(ImmutableList.<Class<?>>of(
          gov.ca.cwds.data.persistence.cms.Address.class, Allegation.class, ClientAddress.class,
          ClientCollateral.class, gov.ca.cwds.data.persistence.cms.Client.class,
          CmsDocReferralClient.class, CmsDocument.class, CmsDocumentBlobSegment.class,
          CollateralIndividual.class, CrossReport.class, EducationProviderContact.class,
          OtherAdultInPlacemtHome.class, OtherChildInPlacemtHome.class, OtherClientName.class,
          Referral.class, ReferralClient.class, Reporter.class, ServiceProvider.class,
          StaffPerson.class, SubstituteCareProvider.class, LongText.class,
          AllegationPerpetratorHistory.class, ClientUc.class, ChildClient.class,
          gov.ca.cwds.data.persistence.cms.Address.class, ClientAddress.class,
          CountyOwnership.class, CountyTrigger.class, CountyTriggerEmbeddable.class,
          SystemCode.class, SystemMeta.class, DrmsDocument.class, Assignment.class,
          BaseAssignment.class, ReferralAssignment.class, CaseAssignment.class, CmsCase.class,
          Tickle.class, ClientRelationship.class, ClientCollateral.class, AddressUc.class,
          ExternalInterface.class, DeliveredServiceEntity.class,
          ContactPartyDeliveredServiceEntity.class, ReferralClientDeliveredServiceEntity.class,
          IndividualDeliveredServiceEntity.class, LawEnforcement.class, CaseLoad.class,
          StaffPersonCaseLoad.class, ClientScpEthnicity.class, GovernmentOrganization.class),

          new ApiSessionFactoryFactory()) {

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
          PersonRaceId.class, PersonRace.class, Race.class, IntakeLov.class) {
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
   * Constructor takes the API configuration.
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

    // Fails here. Call later.
    // Method getSessionFactory() returns null at this point and fails method toInstance().
    // bind(SessionFactory.class).toInstance(cmsHibernateBundle.getSessionFactory());

    // CMS:
    // CmsReferral participants:
    bind(AllegationDao.class);
    bind(ClientDao.class);
    bind(ReferralClientDao.class);
    bind(ReferralDao.class);
    bind(ReporterDao.class);
    bind(CrossReportDao.class);
    bind(CaseDao.class);
    bind(ReferralAssignmentDao.class);
    bind(CaseAssignmentDao.class);
    bind(CaseDao.class);
    bind(ClientRelationshipDao.class);
    bind(ClientCollateralDao.class);

    bind(AttorneyDao.class);
    bind(CmsDocReferralClientDao.class);
    bind(CmsDocumentDao.class);
    bind(OtherClientNameDao.class);
    bind(StaffPersonDao.class);
    bind(LongTextDao.class);
    bind(AllegationPerpetratorHistoryDao.class);
    bind(ClientUcDao.class);
    bind(ChildClientDao.class);
    bind(SystemCodeDao.class);
    bind(SystemMetaDao.class);
    bind(DrmsDocumentDao.class);
    bind(AssignmentDao.class);
    bind(TickleDao.class);
    bind(AddressUcDao.class);
    bind(ExternalInterfaceDao.class);
    bind(DeliveredServiceDao.class);
    bind(ContactPartyDeliveredServiceDao.class);
    bind(ReferralClientDeliveredServiceDao.class);
    bind(IndividualDeliveredServiceDao.class);
    bind(LawEnforcementDao.class);
    bind(CaseLoadDao.class);
    bind(StaffPersonDao.class);
    bind(ClientScpEthnicityDao.class);
    bind(GovernmentOrganizationDao.class);

    // NS:
    bind(AddressDao.class);
    bind(PersonDao.class);
    bind(ScreeningDao.class);
    bind(ParticipantDao.class);
    bind(PhoneNumberDao.class);
    bind(LanguageDao.class);
    bind(PersonAddressDao.class);
    bind(PersonPhoneDao.class);
    bind(PersonLanguageDao.class);
    bind(PersonEthnicityDao.class);
    bind(EthnicityDao.class);
    bind(PersonRaceDao.class);
    bind(RaceDao.class);
    bind(IntakeLovDao.class);

    // Trigger Tables:
    bind(CountyOwnershipDao.class);
    bind(CountyTriggerDao.class);
    bind(NonLACountyTriggers.class);
    bind(LACountyTrigger.class);
    bind(TriggerTablesDao.class);
    bind(CountyTriggerEmbeddable.class);

    // Downstream Tables:
    bind(Reminders.class);

    // Miscellaneous:
    bind(SmartyStreetsDao.class);

    // System code loader DAO:
    bind(ApiSystemCodeDao.class).to(SystemCodeDaoFileImpl.class);

    // Referential integrity:
    bind(RIClientCollateral.class);
    bind(RIChildClient.class);
    bind(RIAllegationPerpetratorHistory.class);
    bind(RIAssignment.class);
    bind(RIClientAddress.class);
    bind(RIAllegation.class);
    bind(RICrossReport.class);
    bind(RIReporter.class);
    bind(RIReferral.class);
    bind(RIReferralClient.class);
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
  @CmsHibernateBundle
  HibernateBundle<ApiConfiguration> cmsHibernateBundle() {
    return cmsHibernateBundle;
  }

  @Provides
  @NsHibernateBundle
  HibernateBundle<ApiConfiguration> nsHibernateBundle() {
    return nsHibernateBundle;
  }

  @Provides
  public Map<String, ElasticsearchConfiguration> elasticSearchConfigs(
      ApiConfiguration apiConfiguration) {
    return apiConfiguration.getElasticsearchConfigurations();
  }

  @Provides
  public SmartyStreetsConfiguration smartystreetsConfig(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getSmartyStreetsConfiguration();
  }

  @Provides
  public TriggerTablesConfiguration triggerTablesConfiguration(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getTriggerTablesConfiguration();
  }

  @Provides
  @Named("elasticsearch.daos")
  public Map<String, ElasticsearchDao> provideElasticSearchDaos(ApiConfiguration apiConfiguration) {
    if (clients == null) {
      provideElasticsearchClients(apiConfiguration);
    }

    Map<String, ElasticsearchDao> esDaos = new HashMap<>();
    for (String esKey : clients.keySet()) {
      Client client = clients.get(esKey);
      ElasticsearchConfiguration config =
          apiConfiguration.getElasticsearchConfigurations().get(esKey);
      ElasticsearchDao dao = new ElasticsearchDao(client, config);
      esDaos.put(esKey, dao);
    }
    return esDaos;
  }

  @Provides
  @Named("people.index")
  public ElasticsearchDao provideElasticSearchDaoPeople(
      @Named("elasticsearch.daos") Map<String, ElasticsearchDao> esDaos) {
    return esDaos.get("peopleIndex");
  }

  @Provides
  @Named("screenings.index")
  public ElasticsearchDao provideEelasticSearchDaoScreenings(
      @Named("elasticsearch.daos") Map<String, ElasticsearchDao> esDaos) {
    return esDaos.get("screeningsIndex");
  }

  @Provides
  public synchronized Map<String, Client> provideElasticsearchClients(
      ApiConfiguration apiConfiguration) {

    if (clients == null) {
      clients = new HashMap<>();

      Map<String, ElasticsearchConfiguration> esConfigs =
          apiConfiguration.getElasticsearchConfigurations();

      for (String esConfigKey : esConfigs.keySet()) {
        ElasticsearchConfiguration config = esConfigs.get(esConfigKey);

        try (TransportClient transportClient = new PreBuiltTransportClient(
            Settings.builder().put("cluster.name", config.getElasticsearchCluster()).build());) {
          transportClient.addTransportAddress(
              new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
                  Integer.parseInt(config.getElasticsearchPort())));
          clients.put(esConfigKey, transportClient);
        } catch (Exception e) {
          LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
          throw new ApiException("Error initializing Elasticsearch client: " + e.getMessage(), e);
        }
      }
    }

    return clients;
  }
}
