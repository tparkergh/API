package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryResourceBuilder {

  String countySpecificCode = "99";
  String perpetratorClientId = "2345678ABC";
  String allegationId = "1234567ABC";
  String perpetratorUpdateDate = "2017-07-19";

  /**
   * @param countySpecificCode - two character county code
   * @return - AllegationPerpetratorHistory
   */
  public AllegationPerpetratorHistoryResourceBuilder setCountySpecificCode(
      String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  /**
   * @param allegationId - Allegation Id
   * @return - AllegationPerpetratorHistory
   */
  public AllegationPerpetratorHistoryResourceBuilder setAllegationId(String allegationId) {
    this.allegationId = allegationId;
    return this;
  }

  /**
   * @param perpetratorClientId - Client Id of perpetrator
   * @return - AllegationPerpetratorHistory
   */
  public AllegationPerpetratorHistoryResourceBuilder setPerpertratorClientId(
      String perpetratorClientId) {
    this.perpetratorClientId = perpetratorClientId;
    return this;
  }

  /**
   * @param perpetratorUpdateDate - Date when perpetrator was specified on allegation
   * @return - AllegationPerpetratorHistory
   */
  public AllegationPerpetratorHistoryResourceBuilder setPerpetratorUpdateDate(
      String perpetratorUpdateDate) {
    this.perpetratorUpdateDate = perpetratorUpdateDate;
    return this;
  }

  /**
   * @return - the cms domain AllegationPerpetratorHistory
   */
  public AllegationPerpetratorHistory createAllegationPerpetratorHistory() {
    return new AllegationPerpetratorHistory(countySpecificCode, perpetratorClientId, allegationId,
        perpetratorUpdateDate);

  }
}
