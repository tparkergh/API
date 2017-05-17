package gov.ca.cwds.data.cms;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.inject.CmsSessionFactory;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

/**
 * Hibernate DAO for DB2 {@link SystemCode}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeDao extends CrudsDaoImpl<SystemCode> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public SystemCodeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unchecked")
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    Query query =
        this.getSessionFactory().getCurrentSession()
            .getNamedQuery("gov.ca.cwds.data.persistence.cms.SystemCode.findByForeignKeyMetaTable")
            .setString("foreignKeyMetaTable", foreignKeyMetaTable);
    return (SystemCode[]) query.list().toArray(new SystemCode[0]);

  }

}
