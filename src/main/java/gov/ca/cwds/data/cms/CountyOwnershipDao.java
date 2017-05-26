package gov.ca.cwds.data.cms;

import java.text.MessageFormat;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CountyOwnership}.
 * 
 * @author CWDS API Team
 */
public class CountyOwnershipDao extends CrudsDaoImpl<CountyOwnership> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public CountyOwnershipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * CountyOwnership Dao
   * 
   * Overriding the existing update for triggers and using the own update for countyOwnership
   * Trigger
   * 
   */
  @Override
  public CountyOwnership update(CountyOwnership object) {
    CountyOwnership databaseObject = find(object.getPrimaryKey());
    if (databaseObject == null) {
      String msg =
          MessageFormat.format("Unable to find entity with id={0}", object.getPrimaryKey());
      throw new EntityNotFoundException(msg);
    }
    return persist(object);
  }

}
