package gov.ca.cwds.data.cms;

import javax.persistence.ParameterMode;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link Client}.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ClientDao extends BaseDaoImpl<Client> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  // CALL CWSNS4.SPSSANAME3(
  // 'CLT_PHTT',
  // 'I',
  // 'KATlYxa0JL',
  // 'C',
  // 'JAMES',
  // 'BOND',
  // 'SMITH', ' ',' ',
  // '2017-03-22 18:32:21.198',
  // 'q1p',?,?);

  /**
   * Call DB2 stored procedure SPSSANAME3 to insert soundex records for client search. Story
   * #146481759.
   * 
   * @return DB2 result code
   */
  public String callStoredProc() {
    String ret = null;
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      // txn = session.beginTransaction(); // If transaction not yet started.
      txn = session.getTransaction(); // For JUnit.
      LOGGER.debug(session.getSessionFactory().getProperties().toString());
      final String schema =
          (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
      LOGGER.debug("schema={}", schema);
      ProcedureCall q = session.createStoredProcedureCall("CWSNS4.SPSSANAME3");

      int pos = -1;
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN); // timestamp
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.IN); // staff
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.OUT); // result code
      q.registerStoredProcedureParameter(++pos, String.class, ParameterMode.OUT); // error message

      pos = -1;
      q.setParameter(++pos, "CLT_PHTT");
      q.setParameter(++pos, "I");
      q.setParameter(++pos, "Taylr1SWft");
      q.setParameter(++pos, "C");
      q.setParameter(++pos, "JAMES");
      q.setParameter(++pos, "BOND");
      q.setParameter(++pos, "XYZNAME");
      q.setParameter(++pos, " ");
      q.setParameter(++pos, " ");
      q.setParameter(++pos, " ");
      q.setParameter(++pos, "2017-03-22 18:32:21.198");
      q.setParameter(++pos, "q1p");

      q.execute();

      final String resultCode = (String) q.getOutputParameterValue(11);
      final String resultMsg = (String) q.getOutputParameterValue(12);
      ret = resultCode;

      LOGGER.debug("resultCode: {}, resultMsg: {}", resultCode, resultMsg);

      txn.commit();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }

    return ret;
  }

}
