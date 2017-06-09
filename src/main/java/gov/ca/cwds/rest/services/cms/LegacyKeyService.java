package gov.ca.cwds.rest.services.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for Intake Person Auto-complete.
 * 
 * @author CWDS API Team
 */
public class LegacyKeyService
    extends SimpleResourceService<String, LegacyKeyRequest, LegacyKeyResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LegacyKeyService.class);

  /**
   * Constructor
   */
  @Inject
  public LegacyKeyService() {
    // Default, no-op.
  }

  @Override
  protected LegacyKeyResponse handleRequest(LegacyKeyRequest req) {
    return null;
  }

  @Override
  protected LegacyKeyResponse handleFind(String searchForThis) {
    return null;
  }

}
