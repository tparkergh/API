package gov.ca.cwds.rest.services.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.auth.StaffAuthorityPrivilegeDao;
import gov.ca.cwds.data.auth.StaffUnitAuthorityDao;
import gov.ca.cwds.data.auth.UserIdDao;
import gov.ca.cwds.data.persistence.auth.CmsUserAuthPrivilege;
import gov.ca.cwds.data.persistence.auth.UserId;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.auth.StaffAuthorityPrivilege;
import gov.ca.cwds.rest.api.domain.auth.StaffUnitAuthority;
import gov.ca.cwds.rest.api.domain.auth.UserAuthorization;
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
  private StaffUnitAuthorityDao staffUnitAuthorityDao;

  /**
   * Constructor
   * 
   * @param userIdDao The User Id DAO
   * @param staffAuthorityPrivilegeDao The Staff Authority Privilege DAO
   * @param staffUnitAuthorityDao DAO for Staff Unit Authority
   */
  @Inject
  public UserAuthorizationService(UserIdDao userIdDao,
      StaffAuthorityPrivilegeDao staffAuthorityPrivilegeDao,
      StaffUnitAuthorityDao staffUnitAuthorityDao) {
    this.userIdDao = userIdDao;
    this.staffAuthorityPrivilegeDao = staffAuthorityPrivilegeDao;
    this.staffUnitAuthorityDao = staffUnitAuthorityDao;
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
    final List<UserId> userList = userIdDao.listUserFromLogonId(userId);
    gov.ca.cwds.data.persistence.auth.StaffAuthorityPrivilege socialWorker;

    // Found the user?
    if (userList != null && !userList.isEmpty()) {
      final UserId user = userList.get(0);
      socialWorker = staffAuthorityPrivilegeDao.isSocialWorker(user.getId());

      // Staff authorization privileges:
      final gov.ca.cwds.data.persistence.auth.StaffAuthorityPrivilege[] staffAuthPrivs =
          this.staffAuthorityPrivilegeDao.findByUser(user.getId());

      Set<StaffAuthorityPrivilege> userAuthPrivs = new HashSet<>();
      for (gov.ca.cwds.data.persistence.auth.StaffAuthorityPrivilege p : staffAuthPrivs) {
        LOGGER.info("priv sys id=" + p.getLevelOfAuthPrivilegeType() + ", lvl of auth="
            + p.getLevelOfAuthPrivilegeCode());
        userAuthPrivs.add(new StaffAuthorityPrivilege(p.getLevelOfAuthPrivilegeType().toString(),
            p.getLevelOfAuthPrivilegeCode(), p.getCountySpecificCode(), CmsUserAuthPrivilege
                .getInstance().findSysIdDescription(p.getLevelOfAuthPrivilegeType().longValue())));
      }

      // Staff unit authorizations:
      final gov.ca.cwds.data.persistence.auth.StaffUnitAuthority[] staffUnitAuths =
          this.staffUnitAuthorityDao.findByStaff(user.getStaffPersonId());

      Set<StaffUnitAuthority> setStaffUnitAuths = new HashSet<>();
      for (gov.ca.cwds.data.persistence.auth.StaffUnitAuthority p : staffUnitAuths) {
        setStaffUnitAuths.add(new StaffUnitAuthority(p.getAuthorityCode(), p.getFkasgUnit(),
            p.getCountySpecificCode(), CmsUserAuthPrivilege.getInstance()
                .findUnitAuthorityByCode(p.getAuthorityCode()).getDescription()));
      }

      return new UserAuthorization(user.getLogonId(), user.getStaffPersonId(), socialWorker != null,
          false, true, userAuthPrivs, setStaffUnitAuths);
    } else {
      LOGGER.warn("No user id found for " + primaryKey);
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
