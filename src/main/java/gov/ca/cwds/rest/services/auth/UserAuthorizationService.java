package gov.ca.cwds.rest.services.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.auth.StaffAuthorityPrivilege;
import gov.ca.cwds.rest.api.domain.auth.UserAuthorization;
import gov.ca.cwds.rest.api.persistence.auth.UserId;
import gov.ca.cwds.rest.jdbi.auth.StaffAuthorityPrivilegeDao;
import gov.ca.cwds.rest.jdbi.auth.UserIdDao;
import gov.ca.cwds.rest.services.CrudsService;


/**
 * Business layer object to work on {@link UserAuthorization}
 * 
 * @author CWDS API Team
 */
public class UserAuthorizationService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthorizationService.class);

  private UserIdDao userIdDao;
  private StaffAuthorityPrivilegeDao staffAuthorityPrivilegeDao;

  /**
   * Constructor
   * 
   * @param userIdDao The User Id DAO
   * @param staffAuthorityPrivilegeDao The Staff Authority Privilege DAO
   */
  @Inject
  public UserAuthorizationService(UserIdDao userIdDao,
      StaffAuthorityPrivilegeDao staffAuthorityPrivilegeDao) {
    this.userIdDao = userIdDao;
    this.staffAuthorityPrivilegeDao = staffAuthorityPrivilegeDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public UserAuthorization find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    LOGGER.info(primaryKey.toString());

    final String userId = ((String) primaryKey).trim();
    List<UserId> userList = userIdDao.listUserFromLogonId(userId);
    Set<gov.ca.cwds.rest.api.domain.auth.StaffUnitAuthority> testuserUnitAuthority =
        new HashSet<>();
    UserId user;
    gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege socialWorker;

    if (userList != null && !userList.isEmpty()) {
      user = userList.get(0);
      socialWorker = staffAuthorityPrivilegeDao.isSocialWorker(user.getId());

      final gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege[] staffAuthPrivs =
          this.staffAuthorityPrivilegeDao.findByUser(user.getId());

      Set<StaffAuthorityPrivilege> userAuthorityPrivileges = new HashSet<>();
      for (gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege priv : staffAuthPrivs) {
        userAuthorityPrivileges
            .add(new StaffAuthorityPrivilege(priv.getLevelOfAuthPrivilegeType().toString(),
                priv.getLevelOfAuthPrivilegeCode(), priv.getCountySpecificCode()));
      }

      return new gov.ca.cwds.rest.api.domain.auth.UserAuthorization(user.getLogonId(),
          user.getStaffPersonId(), socialWorker != null, false, true, userAuthorityPrivileges,
          testuserUnitAuthority);
    }

    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization delete(Serializable primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization create(Request request) {
    throw new NotImplementedException("create not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization update(Serializable primaryKey,
      Request request) {
    throw new NotImplementedException("update not implemented");
  }

}
