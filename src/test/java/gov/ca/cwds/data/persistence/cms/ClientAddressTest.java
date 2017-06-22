package gov.ca.cwds.data.persistence.cms;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Test;

public class ClientAddressTest {
  @Test
  public void shouldCreateClientAddressFromValues(){
    String id = "111";
    Short addressType = 0;
    String bkInmtId = "";
    Date effEndDt = new Date();
    Date effStartDt = new Date();
    String fkAddress = "123";
    String fkClient = "456";
    String homelessInd = "homeless";
    String fkReferral = "567";
    Address addresses = new Address();
    ClientAddress address = new ClientAddress(id, addressType,bkInmtId,effEndDt, effStartDt, fkAddress,fkClient, homelessInd,
        fkReferral,addresses);
    assertNotNull("Expected an addresss to be created", address);
    assertEquals("Expected id to have been set in constructor", id, address.getId());
    assertEquals("Expected addressType to have been set in constructor", addressType, address.getAddressType());
    assertEquals("Expected bkInmtId to have been set in constructor", bkInmtId, address.getBkInmtId());
    assertEquals("Expected effEndDt to have been set in constructor", effEndDt, address.getEffEndDt());
    assertEquals("Expected effStartDt to have been set in constructor", effStartDt, address.getEffStartDt());
    assertEquals("Expected fkAddress to have been set in constructor", fkAddress, address.getFkAddress());
    assertEquals("Expected fkClient to have been set in constructor", fkClient, address.getFkClient());
    assertEquals("Expected homelessInd to have been set in constructor", homelessInd, address.getHomelessInd());
    assertEquals("Expected fkReferral to have been set in constructor", fkReferral, address.getFkReferral());
    assertEquals("Expected addresses to have been set in constructor", addresses, address.getAddresses());
  }

  @Test
  public void shouldCreateClientAddressFromValuesWithLastUpdatedId(){
    String id = "111";
    Short addressType = 0;
    String bkInmtId = "";
    Date effEndDt = new Date();
    Date effStartDt = new Date();
    String fkAddress = "123";
    String fkClient = "456";
    String homelessInd = "homeless";
    String fkReferral = "567";
    Address addresses = new Address();
    String lastUpdateId = "0X5";
    ClientAddress address = new ClientAddress(id, addressType,bkInmtId,effEndDt, effStartDt, fkAddress,fkClient, homelessInd,
        fkReferral,addresses,lastUpdateId);
    assertNotNull("Expected an addresss to be created", address);
    assertEquals("Expected id to have been set in constructor", id, address.getId());
    assertEquals("Expected addressType to have been set in constructor", addressType, address.getAddressType());
    assertEquals("Expected bkInmtId to have been set in constructor", bkInmtId, address.getBkInmtId());
    assertEquals("Expected effEndDt to have been set in constructor", effEndDt, address.getEffEndDt());
    assertEquals("Expected effStartDt to have been set in constructor", effStartDt, address.getEffStartDt());
    assertEquals("Expected fkAddress to have been set in constructor", fkAddress, address.getFkAddress());
    assertEquals("Expected fkClient to have been set in constructor", fkClient, address.getFkClient());
    assertEquals("Expected homelessInd to have been set in constructor", homelessInd, address.getHomelessInd());
    assertEquals("Expected fkReferral to have been set in constructor", fkReferral, address.getFkReferral());
    assertEquals("Expected addresses to have been set in constructor", addresses, address.getAddresses());
    assertEquals("Expected lastUpdateId to have been set in constructor", lastUpdateId, address.getLastUpdatedId());

  }

}