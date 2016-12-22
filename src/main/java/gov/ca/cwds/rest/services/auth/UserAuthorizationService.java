package gov.ca.cwds.rest.services.auth;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.auth.StaffAuthorityPrivilege;
import gov.ca.cwds.rest.api.domain.auth.StaffUnitAuthority;
import gov.ca.cwds.rest.api.domain.auth.UserAuthorization;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.auth.UserAuthorizationDao;
import gov.ca.cwds.rest.services.CrudsService;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Business layer object to work on {@link UserAuthorization}
 * 
 * @author CWDS API Team
 */
public class UserAuthorizationService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthorizationService.class);

  private UserAuthorizationDao userAuthorizationDao;

  /**
   * Constructor
   * 
   * @param userAuthorizationDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.auth.UserAuthorization} objects.
   */
  @Inject
  public UserAuthorizationService(UserAuthorizationDao userAuthorizationDao) {
    this.userAuthorizationDao = userAuthorizationDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public UserAuthorization find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    StaffUnitAuthority staffUnitAuthority =
        new StaffUnitAuthority("Unitwide Read", "ABC123", "Sacramento");
    StaffAuthorityPrivilege authorityPrivilege =
        new StaffAuthorityPrivilege("Countywide Read", "P", "Placer");
    StaffAuthorityPrivilege anotherAuthorityPrivilege =
        new StaffAuthorityPrivilege("Sealed", "P", "San Joaquin");
    Set<StaffUnitAuthority> testuserUnitAuthority = new HashSet<StaffUnitAuthority>();
    testuserUnitAuthority.add(staffUnitAuthority);
    Set<StaffAuthorityPrivilege> testuserAuthorityPrivilege =
        new HashSet<StaffAuthorityPrivilege>();
    testuserAuthorityPrivilege.add(authorityPrivilege);
    testuserAuthorityPrivilege.add(anotherAuthorityPrivilege);
    if (primaryKey != null && primaryKey.equals("testuser")) {
      return new gov.ca.cwds.rest.api.domain.auth.UserAuthorization("testuser", "q1p", true, false,
          true, testuserAuthorityPrivilege, testuserUnitAuthority);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization delete(Serializable primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization create(Request request) {
    throw new NotImplementedException("create not implemented");
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.auth.UserAuthorization update(Serializable primaryKey,
      Request request) {
    throw new NotImplementedException("update not implemented");
  }

}
