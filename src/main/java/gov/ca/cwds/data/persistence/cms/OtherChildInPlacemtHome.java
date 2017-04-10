package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing an OtherChildInPlacemtHome.
 * 
 * @author CWDS API Team
 */
// @NamedQueries({
// @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAll",
// query = "FROM OtherChildInPlacemtHome"),
// @NamedQuery(
// name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter",
// query = "FROM OtherChildInPlacemtHome WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "OTH_KIDT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherChildInPlacemtHome extends BaseOtherChildInPlacemtHome {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherChildInPlacemtHome() {
    super();
  }

  /**
   * @param annualUnearnedIncomeAmount The annualUnearnedIncomeAmount
   * @param birthDate The birthDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param name The name
   */
  public OtherChildInPlacemtHome(BigDecimal annualUnearnedIncomeAmount, Date birthDate,
      String fkplcHmT, String genderCode, String id, String name) {
    super();
    this.annualUnearnedIncomeAmount = annualUnearnedIncomeAmount;
    this.birthDate = birthDate;
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.name = name;
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
