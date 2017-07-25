package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedStaffPerson extends StaffPerson {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("county")
  private String county;


  @JsonProperty("staff_id")
  private String id;

  /**
   * Constructor
   * 
   * @param staffPerson The persisted staffPerson
   */
  public PostedStaffPerson(gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson) {
    super(staffPerson);

    if (StringUtils.isBlank(staffPerson.getId())) {
      throw new ServiceException("StaffPerson ID cannot be empty");
    }
    this.county = GovernmentEntityType.findByCountyCd(staffPerson.getCountyCode()).getDescription();
    this.id = staffPerson.getId();

  }

  /**
   * Constructor
   * 
   * @param staffPerson The domain StaffPerson
   * @param id The Unique Identifier
   * 
   */
  public PostedStaffPerson(StaffPerson staffPerson, String id) {
    super(staffPerson.getEndDate(), staffPerson.getFirstName(), staffPerson.getJobTitle(),
        staffPerson.getLastName(), staffPerson.getMiddleInitial(), staffPerson.getNamePrefix(),
        staffPerson.getPhoneNumber(), staffPerson.getPhoneExt(), staffPerson.getStartDate(),
        staffPerson.getNameSuffix(), staffPerson.getTelecommuterIndicator(), staffPerson
            .getCwsOffice(),

        staffPerson.getAvailabilityAndLocationDescription(),
        staffPerson.getSsrsLicensingWorkerId(), staffPerson.getCountyCode(), staffPerson
            .getDutyWorkerIndicator(), staffPerson.getCwsOfficeAddress(), staffPerson
            .getEmailAddress());
    if (StringUtils.isBlank(id)) {
      throw new ServiceException("StaffPerson ID cannot be empty");
    }
    this.county = GovernmentEntityType.findByCountyCd(staffPerson.getCountyCode()).getDescription();
    this.id = id;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the county
   */
  public String getCounty() {
    return county;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
