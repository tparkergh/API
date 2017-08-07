package gov.ca.cwds.fixture.contacts;

import java.util.Date;

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class DeliveredServiceEntityBuilder {

  Short cftLeadAgencyType = (short) 12;
  String coreServiceIndicator = "N";
  Short communicationMethodType = (short) 408;
  Short contactLocationType = (short) 415;
  String contactVisitCode = "C";
  String countySpecificCode = "99";
  String detailText = "ABC12345679";
  String hardCopyDocumentOnFileCode = "N";
  String detailTextContinuation = "ABC12345t7";
  Date endDate = new Date();
  Date endTime = new Date();
  String primaryDeliveredServiceId = "ABC1at0875";
  String id = "ABC1234567";
  String otherParticipantsDesc = "description of the world";
  String providedByCode = "S";
  String providedById = "ABC097r534";
  Date startDate = new Date();
  Date startTime = new Date();
  String statusCode = "C";
  String supervisionCode = "C";
  Short serviceContactType = (short) 408;
  String wraparoundServiceIndicator = "N";

  public DeliveredServiceEntity buildDeliveredServiceEntity() {
    return new DeliveredServiceEntity(cftLeadAgencyType, coreServiceIndicator,
        communicationMethodType, contactLocationType, contactVisitCode, countySpecificCode,
        detailText, hardCopyDocumentOnFileCode, detailTextContinuation, endDate, endTime,
        primaryDeliveredServiceId, id, otherParticipantsDesc, providedByCode, providedById,
        startDate, startTime, statusCode, supervisionCode, serviceContactType,
        wraparoundServiceIndicator);
  }

  public Short getCftLeadAgencyType() {
    return cftLeadAgencyType;
  }

  public DeliveredServiceEntityBuilder setCftLeadAgencyType(Short cftLeadAgencyType) {
    this.cftLeadAgencyType = cftLeadAgencyType;
    return this;
  }

  public String getCoreServiceIndicator() {
    return coreServiceIndicator;
  }

  public DeliveredServiceEntityBuilder setCoreServiceIndicator(String coreServiceIndicator) {
    this.coreServiceIndicator = coreServiceIndicator;
    return this;
  }

  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  public DeliveredServiceEntityBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  public Short getContactLocationType() {
    return contactLocationType;
  }

  public DeliveredServiceEntityBuilder setContactLocationType(Short contactLocationType) {
    this.contactLocationType = contactLocationType;
    return this;
  }

  public String getContactVisitCode() {
    return contactVisitCode;
  }

  public DeliveredServiceEntityBuilder setContactVisitCode(String contactVisitCode) {
    this.contactVisitCode = contactVisitCode;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public DeliveredServiceEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getDetailText() {
    return detailText;
  }

  public DeliveredServiceEntityBuilder setDetailText(String detailText) {
    this.detailText = detailText;
    return this;
  }

  public String getHardCopyDocumentOnFileCode() {
    return hardCopyDocumentOnFileCode;
  }

  public DeliveredServiceEntityBuilder setHardCopyDocumentOnFileCode(
      String hardCopyDocumentOnFileCode) {
    this.hardCopyDocumentOnFileCode = hardCopyDocumentOnFileCode;
    return this;
  }

  public String getDetailTextContinuation() {
    return detailTextContinuation;
  }

  public DeliveredServiceEntityBuilder setDetailTextContinuation(String detailTextContinuation) {
    this.detailTextContinuation = detailTextContinuation;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public DeliveredServiceEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Date getEndTime() {
    return endTime;
  }

  public DeliveredServiceEntityBuilder setEndTime(Date endTime) {
    this.endTime = endTime;
    return this;
  }

  public String getPrimaryDeliveredServiceId() {
    return primaryDeliveredServiceId;
  }

  public DeliveredServiceEntityBuilder setPrimaryDeliveredServiceId(
      String primaryDeliveredServiceId) {
    this.primaryDeliveredServiceId = primaryDeliveredServiceId;
    return this;
  }

  public String getId() {
    return id;
  }

  public DeliveredServiceEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getOtherParticipantsDesc() {
    return otherParticipantsDesc;
  }

  public DeliveredServiceEntityBuilder setOtherParticipantsDesc(String otherParticipantsDesc) {
    this.otherParticipantsDesc = otherParticipantsDesc;
    return this;
  }

  public String getProvidedByCode() {
    return providedByCode;
  }

  public DeliveredServiceEntityBuilder setProvidedByCode(String providedByCode) {
    this.providedByCode = providedByCode;
    return this;
  }

  public String getProvidedById() {
    return providedById;
  }

  public DeliveredServiceEntityBuilder setProvidedById(String providedById) {
    this.providedById = providedById;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public DeliveredServiceEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getStartTime() {
    return startTime;
  }

  public DeliveredServiceEntityBuilder setStartTime(Date startTime) {
    this.startTime = startTime;
    return this;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public DeliveredServiceEntityBuilder setStatusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  public String getSupervisionCode() {
    return supervisionCode;
  }

  public DeliveredServiceEntityBuilder setSupervisionCode(String supervisionCode) {
    this.supervisionCode = supervisionCode;
    return this;
  }

  public Short getServiceContactType() {
    return serviceContactType;
  }

  public DeliveredServiceEntityBuilder setServiceContactType(Short serviceContactType) {
    this.serviceContactType = serviceContactType;
    return this;
  }

  public String getWraparoundServiceIndicator() {
    return wraparoundServiceIndicator;
  }

  public DeliveredServiceEntityBuilder setWraparoundServiceIndicator(
      String wraparoundServiceIndicator) {
    this.wraparoundServiceIndicator = wraparoundServiceIndicator;
    return this;
  }

}
