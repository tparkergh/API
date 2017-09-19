package gov.ca.cwds.data.cms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.ParameterMode;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 Stored Procedure.
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class SsaName3Dao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SsaName3Dao.class);

  private SessionFactory sessionFactory;
  private short s = 0;

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public SsaName3Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * @param crudOperation I/U/D
   * @param address address
   */
  public void addressSsaname3(String crudOperation, Address address) {
    callStoredProc("ADR_PHTT", crudOperation, address.getId(), "A", " ", " ", " ",
        address.getStreetNumber(), address.getStreetName(), address.getGovernmentEntityCd(),
        address.getLastUpdatedTime(), address.getLastUpdatedId());
  }

  /**
   * @param crudOperation I/U/D
   * @param client client
   */
  public void clientSsaname3(String crudOperation, Client client) {
    callStoredProc("CLT_PHTT", crudOperation, client.getId(), "C", client.getFirstName(),
        client.getMiddleName(), client.getLastName(), " ", " ", s, client.getLastUpdatedTime(),
        client.getLastUpdatedId());
  }


  /**
   * @param crudOperation I/U/D
   * @param otherClientName other client name object
   */
  public void otherClientSsaname3(String crudOperation, OtherClientName otherClientName) {
    callStoredProc("CLT_PHTT", crudOperation, otherClientName.getThirdId(), "N",
        otherClientName.getFirstName(), otherClientName.getMiddleName(),
        otherClientName.getLastName(), " ", " ", s, otherClientName.getLastUpdatedTime(),
        otherClientName.getLastUpdatedId());
  }

  /**
   * @param crudOperation I/U/D
   * @param substituteCareProvider care provider
   */
  public void subCareProviderSsaname3(String crudOperation,
      SubstituteCareProvider substituteCareProvider) {
    callStoredProc("SCP_PHTT", crudOperation, substituteCareProvider.getId(), " ",
        substituteCareProvider.getFirstName(), substituteCareProvider.getMiddleName(),
        substituteCareProvider.getLastName(), " ", " ", s,
        substituteCareProvider.getLastUpdatedTime(), substituteCareProvider.getLastUpdatedId());
  }

  /**
   * Call DB2 stored procedure SPSSANAME3 to insert soundex records for client search. Story
   * #146481759.
   * 
   * @param tableName table name
   * @param crudOper CRUD operation (I/U/D)
   * @param identifier legacy identifier
   * @param nameCd name code
   * @param firstName first name
   * @param middleName middle name
   * @param lastName last name
   * @param streettNumber street number
   * @param streetName street name
   * @param gvrEntc government entity code
   * @param updateTimeStamp update timestamp
   * @param updateId updated by user id
   */
  protected void callStoredProc(String tableName, String crudOper, String identifier, String nameCd,
      String firstName, String middleName, String lastName, String streettNumber, String streetName,
      Short gvrEntc, Date updateTimeStamp, String updateId) {
    Session session = sessionFactory.getCurrentSession();
    final String storedProcName = "SPSSANAME3";
    final String schema =
        (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");
    String strdtts = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(updateTimeStamp);

    try {
      ProcedureCall q = session.createStoredProcedureCall(schema + "." + storedProcName);

      q.registerStoredProcedureParameter("TABLENAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("CRUDFUNCT", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("IDENTIFIER", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("NAMECODE", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("FIRSTNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("MIDDLENAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("STREETNUM", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("STREETNAME", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("GVRENTC", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTUPDTM", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("LASTUPDID", String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter("RETSTATUS", String.class, ParameterMode.OUT);
      q.registerStoredProcedureParameter("RETMESSAG", String.class, ParameterMode.OUT);

      q.setParameter("TABLENAME", tableName);
      q.setParameter("CRUDFUNCT", crudOper);
      q.setParameter("IDENTIFIER", identifier);
      q.setParameter("NAMECODE", nameCd);
      q.setParameter("FIRSTNAME", firstName);
      q.setParameter("MIDDLENAME", middleName);
      q.setParameter("LASTNAME", lastName);
      q.setParameter("STREETNUM", streettNumber);
      q.setParameter("STREETNAME", streetName);
      q.setParameter("GVRENTC", String.valueOf(gvrEntc));
      q.setParameter("LASTUPDTM", strdtts);
      q.setParameter("LASTUPDID", updateId);

      q.execute();

      final String returnStatus = (String) q.getOutputParameterValue("RETSTATUS");
      final String returnMessage = (String) q.getOutputParameterValue("RETMESSAG");
      int returnCode = Integer.parseInt(returnStatus);

      LOGGER.info("storeProcReturnStatus: {}, storeProcreturnMessage: {}", returnStatus,
          returnMessage);
      /*
       * return code: 0=successful, 1=keys not generated, 2=Invalid parameters sent to stored
       * procedure 3=SQL failed, 4=Call to SSANAME3 DLL failed
       */
      if (returnCode != 0 && returnCode != 1) {
        LOGGER.error("Stored Procedure return message - ", returnMessage);
        throw new DaoException("Stored Procedure returned with ERROR - " + returnMessage);
      }

    } catch (DaoException h) {
      throw new DaoException("Call to Stored Procedure failed - " + h);
    }
  }

}
