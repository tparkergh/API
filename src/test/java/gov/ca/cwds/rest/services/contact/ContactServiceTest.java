package gov.ca.cwds.rest.services.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.ibm.db2.jcc.am.DatabaseMetaData;

import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Contact;
import gov.ca.cwds.rest.api.domain.ContactRequestList;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.DeliveredToIndividualCode;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  ElasticsearchConfiguration esConfig;
  ElasticsearchDao esDao;
  Client client;
  ElasticSearchPerson esp = new ElasticSearchPerson();

  File tempFile;
  File esConfileFile;

  SessionFactory sessionFactory;
  Session session;
  EntityManager em;
  SessionFactoryOptions sfo;
  Transaction transaction;
  StandardServiceRegistry reg;
  ConnectionProvider cp;
  Connection con;
  Statement stmt;
  ResultSet rs;
  DatabaseMetaData meta;

  DeliveredServiceDao deliveredServiceDao;
  StaffPersonDao staffPersonDao;
  LongTextDao longTextDao;
  IndividualDeliveredServiceDao individualDeliveredServiceDao;
  ClientDao clientDao;
  AttorneyDao attorneyDao;
  CollateralIndividualDao collateralIndividualDao;
  ServiceProviderDao serviceProviderDao;
  SubstituteCareProviderDao substituteCareProviderDao;
  ReporterDao reporterDao;

  ContactService target;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    System.setProperty("DB_CMS_SCHEMA", "CWSRS1");

    // JDBC:
    sessionFactory = mock(SessionFactory.class);
    session = mock(Session.class);
    transaction = mock(Transaction.class);
    sfo = mock(SessionFactoryOptions.class);
    reg = mock(StandardServiceRegistry.class);
    cp = mock(ConnectionProvider.class);
    con = mock(Connection.class);
    rs = mock(ResultSet.class);
    meta = mock(DatabaseMetaData.class);
    stmt = mock(Statement.class);
    em = mock(EntityManager.class);
    client = mock(Client.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(sessionFactory.createEntityManager()).thenReturn(em);
    when(session.beginTransaction()).thenReturn(transaction);
    when(sessionFactory.getSessionFactoryOptions()).thenReturn(sfo);
    when(sfo.getServiceRegistry()).thenReturn(reg);
    when(reg.getService(ConnectionProvider.class)).thenReturn(cp);
    when(cp.getConnection()).thenReturn(con);
    when(con.getMetaData()).thenReturn(meta);
    when(con.createStatement()).thenReturn(stmt);
    when(stmt.executeQuery(any())).thenReturn(rs);

    // Result set:
    when(rs.next()).thenReturn(true).thenReturn(false);
    when(rs.getString(any())).thenReturn("abc123456789");
    when(rs.getString(contains("IBMSNAP_OPERATION"))).thenReturn("I");
    when(rs.getString("LIMITED_ACCESS_CODE")).thenReturn("N");
    when(rs.getInt(any())).thenReturn(0);

    final java.util.Date date = new java.util.Date();
    final Timestamp ts = new Timestamp(date.getTime());
    when(rs.getDate(any())).thenReturn(new Date(date.getTime()));
    when(rs.getTimestamp("LIMITED_ACCESS_CODE")).thenReturn(ts);
    when(rs.getTimestamp(any())).thenReturn(ts);

    // DB2 platform and version:
    when(meta.getDatabaseMajorVersion()).thenReturn(11);
    when(meta.getDatabaseMinorVersion()).thenReturn(2);
    when(meta.getDatabaseProductName()).thenReturn("DB2");
    when(meta.getDatabaseProductVersion()).thenReturn("DSN11010");

    // Elasticsearch:
    esDao = mock(ElasticsearchDao.class);
    esConfig = mock(ElasticsearchConfiguration.class);

    when(esDao.getConfig()).thenReturn(esConfig);
    when(esDao.getClient()).thenReturn(client);

    final Map<String, String> mapSettings = new HashMap<>();
    final Settings settings = Settings.builder().put(mapSettings).build();;
    when(client.settings()).thenReturn(settings);

    when(esConfig.getElasticsearchAlias()).thenReturn("people");
    when(esConfig.getElasticsearchDocType()).thenReturn("person");

    // Target:
    attorneyDao = mock(AttorneyDao.class);
    clientDao = mock(ClientDao.class);
    collateralIndividualDao = mock(CollateralIndividualDao.class);
    deliveredServiceDao = mock(DeliveredServiceDao.class);
    individualDeliveredServiceDao = mock(IndividualDeliveredServiceDao.class);
    longTextDao = mock(LongTextDao.class);
    reporterDao = mock(ReporterDao.class);
    serviceProviderDao = mock(ServiceProviderDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    substituteCareProviderDao = mock(SubstituteCareProviderDao.class);

    target = new ContactService(deliveredServiceDao, staffPersonDao, longTextDao,
        individualDeliveredServiceDao, clientDao, attorneyDao, collateralIndividualDao,
        serviceProviderDao, substituteCareProviderDao, reporterDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_Args__String() throws Exception {
    final String primaryKey = DEFAULT_KEY;
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test(expected = Exception.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    Contact actual = target.delete(primaryKey);
  }

  @Test
  public void create_Args__ContactRequestList() throws Exception {
    final ContactRequestList request = mock(ContactRequestList.class);
    Response actual = target.create(request);
    assertThat(actual, notNullValue());
  }

  @Test
  public void update_Args__String__ContactRequestList() throws Exception {
    String primaryKey = null;
    final ContactRequestList request = mock(ContactRequestList.class);
    Response actual = target.update(primaryKey, request);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findPerson_Args__BaseDaoImpl__DeliveredToIndividualCode__Collateral()
      throws Exception {
    final CollateralIndividual indiv =
        PersistentTestTemplate.<CollateralIndividual>valid(new CollateralIndividual());
    when(collateralIndividualDao.find(any())).thenReturn(indiv);
    final String id = indiv.getId();
    final DeliveredToIndividualCode code = DeliveredToIndividualCode.COLLATERAL_INDIVIDUAL;
    PostedIndividualDeliveredService actual = target.findPerson(code, id);
    assertThat(actual, notNullValue());
  }

}
