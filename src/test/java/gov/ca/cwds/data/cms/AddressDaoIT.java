package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AddressDaoIT {

  private static SessionFactory sessionFactory;
  private static AddressDao addressDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AbB9WNB0Nj";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    addressDao = new AddressDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  /**
   * Find JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testFind() throws Exception {
    Address found = addressDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Address found = addressDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();

    Address PersistedAddress = new Address("ABC1234567", validDomainAddress.getCity(),
        validDomainAddress.getEmergencyNumber(), validDomainAddress.getEmergencyExtension(),
        DomainChef.cookBoolean(validDomainAddress.getFrgAdrtB()),
        validDomainAddress.getGovernmentEntityCd(), validDomainAddress.getMessageNumber(),
        validDomainAddress.getMessageExtension(), validDomainAddress.getHeaderAddress(),
        validDomainAddress.getPrimaryNumber(), validDomainAddress.getPrimaryExtension(),
        validDomainAddress.getState(), validDomainAddress.getStreetName(),
        validDomainAddress.getStreetNumber(), Integer.toString(validDomainAddress.getZip()),
        validDomainAddress.getAddressDescription(), validDomainAddress.getZip4(),
        validDomainAddress.getPostDirCd(), validDomainAddress.getPreDirCd(),
        validDomainAddress.getStreetSuffixCd(), validDomainAddress.getUnitDesignationCd(),
        validDomainAddress.getUnitNumber());

    Address create = addressDao.create(PersistedAddress);
    assertThat(PersistedAddress, is(create));

  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();

    Address PersistedAddress = new Address(id, validDomainAddress.getCity(),
        validDomainAddress.getEmergencyNumber(), validDomainAddress.getEmergencyExtension(),
        DomainChef.cookBoolean(validDomainAddress.getFrgAdrtB()),
        validDomainAddress.getGovernmentEntityCd(), validDomainAddress.getMessageNumber(),
        validDomainAddress.getMessageExtension(), validDomainAddress.getHeaderAddress(),
        validDomainAddress.getPrimaryNumber(), validDomainAddress.getPrimaryExtension(),
        validDomainAddress.getState(), validDomainAddress.getStreetName(),
        validDomainAddress.getStreetNumber(), Integer.toString(validDomainAddress.getZip()),
        validDomainAddress.getAddressDescription(), validDomainAddress.getZip4(),
        validDomainAddress.getPostDirCd(), validDomainAddress.getPreDirCd(),
        validDomainAddress.getStreetSuffixCd(), validDomainAddress.getUnitDesignationCd(),
        validDomainAddress.getUnitNumber());

    addressDao.create(PersistedAddress);

  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    Address deleted = addressDao.delete(id);
    assertThat(deleted.getId(), is(id));

  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    Address deleted = addressDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));

  }

  @Test
  public void testUpdate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();

    Address PersistedAddress = new Address(id, validDomainAddress.getCity(),
        validDomainAddress.getEmergencyNumber(), validDomainAddress.getEmergencyExtension(),
        DomainChef.cookBoolean(validDomainAddress.getFrgAdrtB()),
        validDomainAddress.getGovernmentEntityCd(), validDomainAddress.getMessageNumber(),
        validDomainAddress.getMessageExtension(), validDomainAddress.getHeaderAddress(),
        validDomainAddress.getPrimaryNumber(), validDomainAddress.getPrimaryExtension(),
        validDomainAddress.getState(), validDomainAddress.getStreetName(),
        validDomainAddress.getStreetNumber(), Integer.toString(validDomainAddress.getZip()),
        validDomainAddress.getAddressDescription(), validDomainAddress.getZip4(),
        validDomainAddress.getPostDirCd(), validDomainAddress.getPreDirCd(),
        validDomainAddress.getStreetSuffixCd(), validDomainAddress.getUnitDesignationCd(),
        validDomainAddress.getUnitNumber());

    Address updated = addressDao.update(PersistedAddress);
    assertThat(PersistedAddress, is(updated));

  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress =
        new CmsAddressResourceBuilder().buildCmsAddress();

    Address PersistedAddress = new Address("ABC1234567", validDomainAddress.getCity(),
        validDomainAddress.getEmergencyNumber(), validDomainAddress.getEmergencyExtension(),
        DomainChef.cookBoolean(validDomainAddress.getFrgAdrtB()),
        validDomainAddress.getGovernmentEntityCd(), validDomainAddress.getMessageNumber(),
        validDomainAddress.getMessageExtension(), validDomainAddress.getHeaderAddress(),
        validDomainAddress.getPrimaryNumber(), validDomainAddress.getPrimaryExtension(),
        validDomainAddress.getState(), validDomainAddress.getStreetName(),
        validDomainAddress.getStreetNumber(), Integer.toString(validDomainAddress.getZip()),
        validDomainAddress.getAddressDescription(), validDomainAddress.getZip4(),
        validDomainAddress.getPostDirCd(), validDomainAddress.getPreDirCd(),
        validDomainAddress.getStreetSuffixCd(), validDomainAddress.getUnitDesignationCd(),
        validDomainAddress.getUnitNumber());

    addressDao.update(PersistedAddress);
  }

}
