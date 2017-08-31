package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing a StaffPersonCaseLoad.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Allegation.findByStaffId",
    query = "FROM StaffPersonCaseLoad WHERE fkStaffPerson = :staffId")
@SuppressWarnings("serial")
@Entity
@Table(name = "STFCSLDT")
public class StaffPersonCaseLoad extends CmsPersistentObject {

  @Column(name = "CNTY_SPFCD")
  private String countyCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKCASE_LDT", length = CMS_ID_LEN)
  private String fkCaseLoad;

  @Column(name = "FKSTFPERST", length = 3)
  private String fkStaffPerson;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoad() {
    super();
  }

  /**
   * @param countyCode - county specific code
   * @param endDate - end data of staff person case load
   * @param fkCase - primary key to Case Load
   * @param fkStaffPerson - primary key to Staff Person
   * @param startDate - start date of staff person case load
   * @param thirdId - primary key to Staff Person Case Load table
   */
  public StaffPersonCaseLoad(String countyCode, Date endDate, String fkCase, String fkStaffPerson,
      Date startDate, String thirdId) {
    super();
    this.countyCode = countyCode;
    this.endDate = endDate;
    this.fkCaseLoad = fkCase;
    this.fkStaffPerson = fkStaffPerson;
    this.startDate = startDate;
    this.thirdId = thirdId;
  }

  /**
   * Constructor using domain
   * 
   * @param thirdId The id
   * @param persistedStaffPersonCaseLoad The domain object to construct this object from
   * @param lastUpdatedId the id of the last user to update this object
   */
  public StaffPersonCaseLoad(String thirdId,
      gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad persistedStaffPersonCaseLoad,
      String lastUpdatedId) {
    super(lastUpdatedId);
    initialize(thirdId, persistedStaffPersonCaseLoad);
  }

  @SuppressWarnings("unused")
  private void initialize(String thirdId,
      gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad persistedStaffPersonCaseLoad) {
    this.countyCode = persistedStaffPersonCaseLoad.getCountyCode();
    this.endDate = DomainChef.uncookDateString(persistedStaffPersonCaseLoad.getEndDate());
    this.fkCaseLoad = persistedStaffPersonCaseLoad.getFkCaseLoad();
    this.fkStaffPerson = persistedStaffPersonCaseLoad.getFkStaffPerson();
    this.startDate = DomainChef.uncookDateString(persistedStaffPersonCaseLoad.getStartDate());
    this.thirdId = persistedStaffPersonCaseLoad.getThirdId();
  }

  @Override
  public Serializable getPrimaryKey() {
    return getThirdId();
  }

  @SuppressWarnings("javadoc")
  public String getCountyCode() {
    return countyCode;
  }

  @SuppressWarnings("javadoc")
  public Date getEndDate() {
    return endDate;
  }

  @SuppressWarnings("javadoc")
  public String getFkCase() {
    return fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  @SuppressWarnings("javadoc")
  public Date getStartDate() {
    return startDate;
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return thirdId;
  }
}
