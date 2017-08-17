package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import java.util.Date;

public class AllegationPerpetratorHistoryEntityBuilder {
  String id = "12345ASDFG";
  String countySpecificCode = "";
  String perpetratorClientId = "";
  String allegationId = "";
  Date perpetratorUpdateDate = new Date();

  public AllegationPerpetratorHistoryEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public AllegationPerpetratorHistoryEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public AllegationPerpetratorHistoryEntityBuilder setPerpetratorClientId(String perpetratorClientId) {
    this.perpetratorClientId = perpetratorClientId;
    return this;
  }

  public AllegationPerpetratorHistoryEntityBuilder setAllegationId(String allegationId) {
    this.allegationId = allegationId;
    return this;
  }

  public AllegationPerpetratorHistoryEntityBuilder setPerpetratorUpdateDate(Date perpetratorUpdateDate) {
    this.perpetratorUpdateDate = perpetratorUpdateDate;
    return this;
  }

  public AllegationPerpetratorHistory build(){
    return new AllegationPerpetratorHistory(id, countySpecificCode,
      perpetratorClientId, allegationId, perpetratorUpdateDate);
  }
}
