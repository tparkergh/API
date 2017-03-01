package gov.ca.cwds.rest.services.cms;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link Client}.
 * 
 * @author CWDS API Team
 */
public class ClientService implements TypedCrudsService<String, Client, Client> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientDao dao;

  /**
   * Constructor.
   * 
   * @param dao The {@link Dao} handling {@link ClientDao} objects.
   */
  @Inject
  public ClientService(ClientDao dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Client find(String primaryKey) {
    LOGGER.info("primaryKey=" + primaryKey);

    Client retval = null;
    gov.ca.cwds.data.persistence.cms.Client persisted = dao.find(primaryKey);
    if (persisted != null) {
      retval = new Client(persisted);
    }
    return retval;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Client delete(String primaryKey) {
    throw new NotImplementedException("DELETE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Client create(Client request) {
    throw new NotImplementedException("CREATE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Client update(String primaryKey, Client request) {
    throw new NotImplementedException("UPDATE NOT IMPLEMENTED!");
  }

}
