package gov.ca.cwds.rest.services.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.rest.api.ApiException;

public class RIClientCollateral implements ApiReferentialCheck<ClientCollateral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientCollateral.class);

  private ClientDao clientDao;

  @Inject
  public RIClientCollateral(final ClientDao clientDao) {
    this.clientDao = clientDao;
    ApiHibernateInterceptor.addHandler(ClientCollateral.class, c -> {
      if (!apply((ClientCollateral) c)) {
        throw new ApiException("RI ERROR: ClientCollateral => Client");
      }
    });
  }

  @Override
  public Boolean apply(ClientCollateral t) {
    LOGGER.debug("RI: ClientCollateral");
    return clientDao.find(t.getClientId()) != null;
  }

}
