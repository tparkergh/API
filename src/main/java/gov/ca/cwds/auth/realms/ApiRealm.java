package gov.ca.cwds.auth.realms;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Implementation of JWT processing realm. This realm validates JWT token and extract identity claim
 * from JWT payload. Identity claim example: {"user" : "username", "roles" : ["role1", "role2"] ,
 * "staffId" : "id"}
 *
 * @author CWDS API Team
 *
 */
public class ApiRealm extends JwtRealm {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiRealm.class);

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Maps JWT payload to user info. User info will be accessible as secondary principal:
   * <p>
   * Subject subject = SecurityUtils.getSubject(); List principals =
   * subject.getPrincipals().asList(); PerryAccount account = (PerryAccount) principals.get(1);
   *
   * @param json jwt payload
   * @return mapped jwt payload
   */
  @Override
  protected PerryUserIdentity map(final String json) {
    PerryUserIdentity userIdentity = null;

    try {
      userIdentity = objectMapper.readValue(json, PerryUserIdentity.class);
    } catch (IOException e) {
      LOGGER.warn("Could not map user identity: " + e.getMessage());
      userIdentity = new PerryUserIdentity();
      userIdentity.setUser(json);
    }

    return userIdentity;
  }
}
