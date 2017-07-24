package gov.ca.cwds.rest.business.rules;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.ExternalInterfaceDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ExternalInterface;
import gov.ca.cwds.rest.filters.ApiRequestCommonInfo;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to update upper case tables
 * 
 *
 * @author CWDS API Team
 */
public class ExternalInterfaceTables {

  private static final String SOURCE_TBL_CLIENT = "CLIENT_T";
  private static final String SOURCE_TBL_ASSIGNMENT = "ASGNM_T";
  private static final String OPERATION_TYPE_DELETE = "D";

  private static final Logger LOGGER = LoggerFactory.getLogger(ExternalInterfaceTables.class);

  private ExternalInterfaceDao externalInterfaceDao;
  private ApiRequestCommonInfo apiRequestCommonInfo = ApiRequestCommonInfo.getRequestCommon();

  /**
   * @param externalInterfaceDao - external interface table
   */
  @Inject
  public ExternalInterfaceTables(ExternalInterfaceDao externalInterfaceDao) {
    this.externalInterfaceDao = externalInterfaceDao;
  }


  /**
   * @param client Client creates the external interface with the client Id
   * @param operType operation type
   */
  public void createExtInterClient(Client client, String operType) {
    ExternalInterface externalInterface = new ExternalInterface();
    externalInterface
        .setSequenceNumber(apiRequestCommonInfo.incrementAndGetSequenceExternalTable());
    externalInterface.setSubmitlTimestamp(client.getLastUpdatedTime());
    externalInterface.setTableName(SOURCE_TBL_CLIENT);
    externalInterface.setOperationType(operType);
    externalInterface.setPrimaryKey1(client.getId());
    externalInterface.setLogonUserId(apiRequestCommonInfo.getRacf());

    try {
      externalInterfaceDao.create(externalInterface);
      LOGGER.info("External Interface row is created");
    } catch (ServiceException se) {
      throw new DaoException("Insert to extinf failed - " + se);
    }
  }


  /**
   * @param assignment Assignment creates the external interface with the assignment Id
   * @param operType crud operation type
   */
  public void createExtInterAssignment(Assignment assignment, String operType) {
    ExternalInterface externalInterface = new ExternalInterface();
    externalInterface
        .setSequenceNumber(apiRequestCommonInfo.incrementAndGetSequenceExternalTable());
    externalInterface.setSubmitlTimestamp(assignment.getLastUpdatedTime());
    externalInterface.setTableName(SOURCE_TBL_ASSIGNMENT);
    externalInterface.setOperationType(operType);
    externalInterface.setPrimaryKey1(assignment.getId());
    externalInterface.setLogonUserId(apiRequestCommonInfo.getRacf());

    try {
      externalInterfaceDao.create(externalInterface);
      LOGGER.info("External Interface row is created");
    } catch (ServiceException se) {
      throw new DaoException("Insert to extinf failed - " + se);
    }
  }

  /**
   * @param primaryKey the primary key
   * @param sourceTable the source table name
   * 
   */
  public void createExtInterForDelete(Serializable primaryKey, String sourceTable) {
    ExternalInterface externalInterface = new ExternalInterface();
    externalInterface
        .setSequenceNumber(apiRequestCommonInfo.incrementAndGetSequenceExternalTable());
    externalInterface.setSubmitlTimestamp(apiRequestCommonInfo.getRequestBegin());
    externalInterface.setTableName(sourceTable);
    externalInterface.setOperationType(OPERATION_TYPE_DELETE);
    externalInterface.setPrimaryKey1((String) primaryKey);
    externalInterface.setLogonUserId(apiRequestCommonInfo.getRacf());

    try {
      externalInterfaceDao.create(externalInterface);
      LOGGER.info("External Interface row is created");
    } catch (ServiceException se) {
      throw new DaoException("Insert to extinf failed - " + se);
    }

  }


}
