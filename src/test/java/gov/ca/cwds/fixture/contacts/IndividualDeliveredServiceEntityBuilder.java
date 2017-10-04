package gov.ca.cwds.fixture.contacts;

import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;

import java.util.Date;

/**
 * The Builder class for {@link IndividualDeliveredServiceEntity}
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class IndividualDeliveredServiceEntityBuilder {

  String countySpecificCode = "99";
  Date endDate = new Date();
  Short serviceContactType = (short) 408;
  Date startDate = new Date();
  private String deliveredServiceId = "Aabg4cV0AB";
  private String deliveredToIndividualCode = "C";
  private String deliveredToIndividualId = "A0YcYQV0AB";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  public IndividualDeliveredServiceEntity buildIndividualDeliveredServiceEntity() {
    return new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
        deliveredToIndividualId, countySpecificCode, endDate, serviceContactType, startDate,
        lastUpdatedId, lastUpdatedTime);
  }

  /**
   * @param deliveredToIndividualId the deliveredToIndividualId to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setDeliveredToIndividualId(
      String deliveredToIndividualId) {
    this.deliveredToIndividualId = deliveredToIndividualId;
    return this;
  }

  /**
   * @param deliveredToIndividualCode the deliveredToIndividualCode to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setDeliveredToIndividualCode(
      String deliveredToIndividualCode) {
    this.deliveredToIndividualCode = deliveredToIndividualCode;
    return this;
  }

  /**
   * @param countySpecificCode the countySpecificCode to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  /**
   * @param endDate the endDate to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * @param serviceContactType the serviceContactType to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setServiceContactType(Short serviceContactType) {
    this.serviceContactType = serviceContactType;
    return this;
  }

  /**
   * @param startDate the startDate to set
   * @return the builder
   */
  public IndividualDeliveredServiceEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

}
