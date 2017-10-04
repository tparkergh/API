package gov.ca.cwds.rest.api.domain.investigation.contact;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Contact Request
 * 
 * @author CWDS API Team
 */
@JsonPropertyOrder({"referralId", "contactRequest"})
public class ContactReferralRequest implements Request {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  @NotEmpty
  @JsonProperty("referralId")
  private String referralId;


  @JsonProperty("contactRequest")
  private ContactRequest contactRequest;

  /**
   * 
   * @param referralId the Referral Identifier
   * @param contactRequest the Contact Request
   */
  public ContactReferralRequest(String referralId, ContactRequest contactRequest) {
    super();
    this.referralId = referralId;
    this.contactRequest = contactRequest;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the contactRequest
   */
  public ContactRequest getContactRequest() {
    return contactRequest;
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
