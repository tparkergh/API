package gov.ca.cwds.data.cms;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Hibernate DAO for DB2
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ClientAddressDao extends CrudsDaoImpl<ClientAddress> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientAddressDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientAddressDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param addressId - addressId
   * @param clientId - clientid
   * @return the existing address and client
   */
  public List<ClientAddress> findByAddressAndClient(String addressId, String clientId) {
    List<ClientAddress> clientAddresses = new ArrayList<>();
    if (addressId != null && clientId != null) {
      try {
        Query query = this.getSessionFactory().getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.ClientAddress.findByAddressAndClient")
            .setParameter("addressId", addressId).setParameter("clientId", clientId);
        List<ClientAddress> items = query.list();
        clientAddresses.addAll(query.list());
      } catch (Exception e) {
        StringBuilder message = new StringBuilder();
        message.append("Unable to find ClientAddress for address id ");
        message.append(addressId);
        message.append(" and client id ");
        message.append(clientId);
        LOGGER.warn(message.toString());
        throw new ServiceException(e);
      }
    }
    return clientAddresses;
  }
}
