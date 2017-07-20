package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a EducationProviderContact.
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAll",
        query = "FROM EducationProviderContact"),
    @NamedQuery(
        name = "gov.ca.cwds.data.persistence.cms.EducationProviderContact.findAllUpdatedAfter",
        query = "FROM EducationProviderContact WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "EDPRVCNT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationProviderContact extends BaseEducationProviderContact {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public EducationProviderContact() {
    super();
  }

  /**
   * Constructor
   * 
   * @param departmentOfEducationIndicator Indicates contact came from DOE
   * @param emailAddress The email address
   * @param faxNumber The fax number
   * @param firstName The first name
   * @param fKeyEducationProvider The foreign key to EDUCATION_PROVIDER_CONTACT
   * @param id The unique identifier
   * @param lastName The last name
   * @param middleName The middle name
   * @param namePrefixDescription The name prefix description
   * @param phoneExtension The phone number extension
   * @param phoneNumber The phone number
   * @param primaryContactIndicator Indicates that there is primary EDUCATION_PROVIDER_CONTACT
   * @param suffixTitleDescription The title suffix
   * @param titleDescription The title description
   */
  public EducationProviderContact(String departmentOfEducationIndicator, String emailAddress,
      BigDecimal faxNumber, String firstName, String fKeyEducationProvider, String id,
      String lastName, String middleName, String namePrefixDescription, Integer phoneExtension,
      BigDecimal phoneNumber, String primaryContactIndicator, String suffixTitleDescription,
      String titleDescription) {

    this.departmentOfEducationIndicator = departmentOfEducationIndicator;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.fKeyEducationProvider = fKeyEducationProvider;
    this.id = id;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixDescription = namePrefixDescription;
    this.phoneExtensionNumber = phoneExtension;
    this.phoneNumber = phoneNumber;
    this.primaryContactIndicator = primaryContactIndicator;
    this.suffixTitleDescription = suffixTitleDescription;
    this.titleDescription = titleDescription;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
