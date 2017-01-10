package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.data.IAddressAware;
import gov.ca.cwds.data.IAddressAwareWritable;
import gov.ca.cwds.data.IMultiplePhonesAware;
import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.IPersonAwareWritable;
import gov.ca.cwds.data.IPhoneAware;
import gov.ca.cwds.data.IPhoneAwareWritable;
import gov.ca.cwds.data.ITypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainChef;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * <p>
 * The Intake Auto-complete for Person takes a single search term, which is used to query
 * Elasticsearch Person documents by ALL relevant fields. For example, search strings consisting of
 * only digits could be phone numbers, social security numbers, or street address numbers. Search
 * strings consisting of only letters could be last name, first name, city, state, language, and so
 * forth.
 * </p>
 * 
 * <p>
 * This class transforms a raw {@link ElasticSearchPerson} object into a format consumable by
 * Intake.
 * </p>
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AutoCompletePerson
    implements Serializable, IPersonAwareWritable, ITypedIdentifier<String> {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletePerson.class);

  // name_suffix:
  // enum:
  // - esq
  // - ii
  // - iii
  // - iv
  // - jr
  // - sr
  // - md
  // - phd
  // - jd
  //
  // gender:
  // type: string
  // enum:
  // - male
  // - female


  // SAMPLE AUTO-COMPLETE RESULT: (Intake assumes all fields are potentially searchable.)
  // [{
  // "id": 1,
  // "date_of_birth": "1964-01-14",
  // "first_name": "John",
  // "gender": null,
  // "last_name": "Smith",
  // "middle_name": null,
  // "ssn": "858584561",
  // "name_suffix": null,
  // "addresses": [
  // {
  // "city": "city",
  // "id": 6,
  // "state": "IN",
  // "street_address": "876 home",
  // "zip": "66666",
  // "type": "Placement"
  // }
  // ],
  // "phone_numbers": [],
  // "languages": []
  // }]

  // SAMPLE ELASTICSEARCH PERSON DOCUMENT:
  // {
  // "first_name": "Todd",
  // "last_name": "B.",
  // "gender": "",
  // "birth_date": "",
  // "ssn": "",
  // "id": "TZDqRCG0XH",
  // "type": "gov.ca.cwds.data.persistence.cms.Reporter",
  // "source_object": {
  // "lastUpdatedId": "0XH",
  // "lastUpdatedTime": 1479394080309,
  // "referralId": "TZDqRCG0XH",
  // "badgeNumber": "",
  // "cityName": "Police",
  // "colltrClientRptrReltnshpType": 0,
  // "communicationMethodType": 410,
  // "confidentialWaiverIndicator": "Y",
  // "employerName": "test name",
  // "feedbackDate": 1479340800000,
  // "feedbackRequiredIndicator": "Y",
  // "firstName": "Todd",
  // "lastName": "B.",
  // "mandatedReporterIndicator": "Y",
  // "messagePhoneExtensionNumber": 0,
  // "messagePhoneNumber": 0,
  // "middleInitialName": "",
  // "namePrefixDescription": "Mr.",
  // "primaryPhoneNumber": 4650009886,
  // "primaryPhoneExtensionNumber": 0,
  // "stateCodeType": 1828,
  // "streetName": "Mock Plaza",
  // "streetNumber": "2323",
  // "suffixTitleDescription": "",
  // "zipNumber": 95656,
  // "zipSuffixNumber": 0,
  // "countySpecificCode": "28",
  // "primaryKey": "TZDqRCG0XH"
  // }
  // },

  /**
   * Languages.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  // @JsonFormat(shape = JsonFormat.Shape.STRING)
  public enum AutoCompleteLanguage {

    @JsonProperty("English")
    ENGLISH(1253, "English", 7),

    @JsonProperty("Spanish")
    SPANISH(1274, "Spanish", 1),

    @JsonProperty("American Sign Language")
    AMERICAN_SIGN_LANGUAGE(1248, "American Sign Language", 13),

    @JsonProperty("Arabic")
    ARABIC(1249, "Arabic", 14),

    @JsonProperty("Armenian")
    ARMENIAN(1250, "Armenian", 15), CAMBODIAN(1251, "Cambodian", 19), CANTONESE(1252, "Cantonese", 74),

    @JsonProperty("Arabic")
    FARSI(1254, "Farsi", 41), FILIPINO(3198, "Filipino", 49), FRENCH(1255, "French", 28),

    @JsonProperty("German")
    GERMAN(1267, "German", 29), HAWAIIAN(1268, "Hawaiian", 99), HEBREW(1256, "Hebrew", 33), HMONG(1257, "Hmong", 35), ILACANO(1258, "Ilacano", 77),

    @JsonProperty("Indochinese")
    INDOCHINESE(3199, "Indochinese", 99), ITALIAN(1259, "Italian", 42), JAPANESE(1260, "Japanese", 3), KOREAN(1261, "Korean", 4), LAO(1262, "Lao", 43),

    @JsonProperty("Mandarin")
    MANDARIN(1263, "Mandarin", 75), MIEN(1264, "Mien", 76), OTHER_CHINESE(1265, "Other Chinese", 2), OTHER_NON_ENGLISH(1266, "Other Non-English", 99),

    @JsonProperty("Arabic")
    POLISH(1269, "Polish", 50), PORTUGUESE(1270, "Portuguese", 51), ROMANIAN(3200, "Romanian", 99), RUSSIAN(1271, "Russian", 54),

    @JsonProperty("Samoan")
    SAMOAN(1272, "Samoan", 55), SIGN_LANGUAGE_NOT_ASL(1273, "Sign Language (Not ASL)", 78), TAGALOG(1275, "Tagalog", 5),

    @JsonProperty("Thai")
    THAI(1276, "Thai", 65), TURKISH(1277, "Turkish", 67), VIETNAMESE(1278, "Vietnamese", 69);

    private final int sysId;
    private final String description;
    private final int displayOrder;

    private AutoCompleteLanguage(int sysId, String description, int displayOrder) {
      this.sysId = sysId;
      this.description = description;
      this.displayOrder = displayOrder;
    }

    /**
     * Getter for SYS_ID in CMS table SYS_CD_C.
     * 
     * @return SYS_ID
     */
    public int getSysId() {
      return sysId;
    }

    @JsonValue
    public String getDescription() {
      return description;
    }

    public int getDisplayOrder() {
      return displayOrder;
    }
  }

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum AutoCompletePersonAddressType {
    Home, School, Work, Placement, Homeless, Other
  }

  /**
   * Child class. Represents the Address section of Intake Auto-complete.
   * 
   * @author CWDS API Team
   */
  public static final class AutoCompletePersonAddress
      implements Serializable, ITypedIdentifier<Long>, IAddressAwareWritable {

    /**
     * Base serialization version. Increment by class version.
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("street_address")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String streetAddress;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String city;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String state;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String county;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String zip;

    @JsonProperty("type")
    private AutoCompletePersonAddressType addressType;

    /**
     * Default constructor.
     */
    public AutoCompletePersonAddress() {
      // default, no-op.
    }

    /**
     * Construct from a fellow address class.
     * 
     * @param addr incoming address object
     */
    public AutoCompletePersonAddress(IAddressAware addr) {
      if (StringUtils.isNotBlank(addr.getCity())) {
        this.setCity(addr.getCity());
      }
      if (StringUtils.isNotBlank(addr.getCounty())) {
        this.setCounty(addr.getCounty());
      }
      if (StringUtils.isNotBlank(addr.getState())) {
        this.setState(addr.getState());
      }
      if (StringUtils.isNotBlank(addr.getStreetAddress())) {
        this.setStreetAddress(addr.getStreetAddress());
      }
      if (StringUtils.isNotBlank(addr.getZip())) {
        this.setZip(addr.getZip());
      }
    }

    @Override
    public Long getId() {
      return id;
    }

    @Override
    public void setId(Long id) {
      this.id = id;
    }

    @Override
    public String getStreetAddress() {
      return streetAddress;
    }

    @Override
    public void setStreetAddress(String streetAddress) {
      this.streetAddress = streetAddress;
    }

    @Override
    public String getCity() {
      return city;
    }

    @Override
    public void setCity(String city) {
      this.city = city;
    }

    @Override
    public String getState() {
      return state;
    }

    @Override
    public void setState(String state) {
      this.state = state;
    }

    @Override
    public String getZip() {
      return zip;
    }

    @Override
    public void setZip(String zip) {
      this.zip = zip;
    }

    @Override
    public String getCounty() {
      return county;
    }

    @Override
    public void setCounty(String county) {
      this.county = county;
    }

    public AutoCompletePersonAddressType getAddressType() {
      return addressType;
    }

    public void setAddressType(AutoCompletePersonAddressType addressType) {
      this.addressType = addressType;
    }

  }

  /**
   * Child class. Represents the Phone section of Intake Auto-complete.
   * 
   * @author CWDS API Team
   */
  public static final class AutoCompletePersonPhone
      implements Serializable, ITypedIdentifier<Long>, IPhoneAwareWritable {

    /**
     * Base serialization version. Increment by class version.
     */
    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long id;

    @JsonProperty("phone_number")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String phoneNumber;

    /**
     * Not specified by Intake Person Auto-complete YAML.
     */
    @JsonIgnore
    private String phoneNumberExtension;

    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private IPhoneAware.PhoneType phoneType;

    /**
     * Default constructor.
     */
    public AutoCompletePersonPhone() {
      // Default, no-op.
    }

    /**
     * Construct a phone from a phone-aware object.
     * 
     * @param other another phone object
     */
    public AutoCompletePersonPhone(IPhoneAware other) {

      if (other != null) {
        setPhoneNumber(other.getPhoneNumber());
        setPhoneType(other.getPhoneType());
      }

    }

    @Override
    public Long getId() {
      return id;
    }

    @Override
    public void setId(Long id) {
      this.id = id;
    }

    @Override
    public String getPhoneNumber() {
      return this.phoneNumber;
    }

    @Override
    public String getPhoneNumberExtension() {
      return this.phoneNumberExtension;
    }

    @Override
    public PhoneType getPhoneType() {
      return this.phoneType;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    @Override
    public void getPhoneNumberExtension(String phoneNumberExtension) {
      this.phoneNumberExtension = phoneNumberExtension;
    }

    @Override
    public void setPhoneType(PhoneType phoneType) {
      this.phoneType = phoneType;
    }

  }

  // =================
  // MEMBERS:
  // =================

  @JsonProperty("id")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String id;

  @JsonProperty("first_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String firstName;

  @JsonProperty("middle_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String middleName;

  @JsonProperty("last_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String lastName;

  @JsonProperty("name_suffix")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String nameSuffix;

  @JsonProperty("gender")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String gender;

  @JsonProperty("ssn")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String ssn;

  @JsonProperty("date_of_birth")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String dateOfBirth;

  @JsonProperty("addresses")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private List<AutoCompletePersonAddress> addresses = new ArrayList<>();

  @JsonProperty("phone_numbers")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private List<AutoCompletePersonPhone> phoneNumbers;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private List<AutoCompleteLanguage> languages;

  /**
   * Default constructor.
   */
  public AutoCompletePerson() {
    // Default, no-op.
  }

  /**
   * Construct from incoming ElasticSearchPerson.
   * 
   * @param esp incoming {@link ElasticSearchPerson}
   */
  public AutoCompletePerson(ElasticSearchPerson esp) {
    this.setId(esp.getId());

    if (esp.getSourceObj() != null) {

      if (esp.getSourceObj() instanceof IPersonAware) {
        LOGGER.info("IPersonAware!");
        final IPersonAware personAware = (IPersonAware) esp.getSourceObj();

        if (StringUtils.isNotBlank(personAware.getFirstName())) {
          this.setFirstName(personAware.getFirstName());
        }
        if (StringUtils.isNotBlank(personAware.getMiddleName())) {
          this.setMiddleName(personAware.getMiddleName());
        }
        if (StringUtils.isNotBlank(personAware.getLastName())) {
          this.setLastName(personAware.getLastName());
        }
        if (StringUtils.isNotBlank(personAware.getGender())) {
          this.setGender(personAware.getGender());
        }
        if (personAware.getBirthDate() != null) {
          this.setBirthDate(personAware.getBirthDate());
        }
        if (StringUtils.isNotBlank(personAware.getSsn())) {
          this.setSsn(personAware.getSsn());
        }

      }

      if (esp.getSourceObj() instanceof IAddressAware) {
        LOGGER.info("IAddressAware!");
        addAddress(new AutoCompletePersonAddress((IAddressAware) esp.getSourceObj()));
      }

      if (esp.getSourceObj() instanceof IMultiplePhonesAware) {
        LOGGER.info("IMultiplePhonesAware!");
        final IMultiplePhonesAware thePhones = (IMultiplePhonesAware) esp.getSourceObj();
        for (IPhoneAware phone : thePhones.getPhones()) {
          addPhone(new AutoCompletePersonPhone(phone));
        }
      }

    }

  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getMiddleName() {
    return middleName;
  }

  @Override
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Getter for name suffix
   * 
   * @return name suffix
   */
  @Override
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * Setter for name suffix.
   * 
   * @param nameSuffix name suffix
   */
  @Override
  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  @Override
  public String getGender() {
    return gender;
  }

  @Override
  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public String getSsn() {
    return ssn;
  }

  @Override
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  /**
   * Getter for date of birth
   * 
   * @return date of birth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Setter for date of birth
   * 
   * @param dateOfBirth date of birth
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Getter for list of addresses
   * 
   * @return list of addresses
   */
  public List<AutoCompletePersonAddress> getAddresses() {
    return addresses;
  }

  /**
   * Setter for addresses
   * 
   * @param addresses list of addresses
   */
  public void setAddresses(List<AutoCompletePersonAddress> addresses) {
    this.addresses = addresses;
  }

  /**
   * Add an address
   * 
   * @param addr address to add
   */
  public void addAddress(AutoCompletePersonAddress addr) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<>();
    }

    this.addresses.add(addr);
  }

  /**
   * Getter for list of phone numbers.
   * 
   * @return list of phone numbers
   */
  public List<AutoCompletePersonPhone> getPhoneNumbers() {
    return phoneNumbers;
  }

  /**
   * Setter for phone numbers
   * 
   * @param phoneNumbers list of phone numbers
   */
  public void setPhoneNumbers(List<AutoCompletePersonPhone> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  /**
   * Add a phone
   * 
   * @param phone phone to add
   */
  public void addPhone(AutoCompletePersonPhone phone) {
    if (this.phoneNumbers == null) {
      this.phoneNumbers = new ArrayList<>();
    }

    this.phoneNumbers.add(phone);
  }

  /**
   * Getter for birth date. Alias to {@link #getDateOfBirth()}.
   * 
   * @return date of birth
   */
  @Override
  public Date getBirthDate() {
    return StringUtils.isNotBlank(this.dateOfBirth) ? DomainChef.uncookDateString(dateOfBirth)
        : null;
  }

  @Override
  public void setBirthDate(Date birthDate) {
    if (birthDate != null) {
      this.dateOfBirth = DomainChef.cookDate(birthDate);
    }
  }

  /**
   * Getter for language
   * 
   * @return languages
   */
  public List<AutoCompleteLanguage> getLanguages() {
    return languages;
  }

  /**
   * Setter for languages
   * 
   * @param languages list of languages
   */
  public void setLanguages(List<AutoCompleteLanguage> languages) {
    this.languages = languages;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
