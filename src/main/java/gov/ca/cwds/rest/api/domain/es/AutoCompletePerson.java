package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.inject.Inject;

import gov.ca.cwds.data.ApiSysCodeAware;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiAddressAwareWritable;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.data.std.ApiMultipleAddressesAware;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPersonAwareWritable;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.data.std.ApiPhoneAwareWritable;
import gov.ca.cwds.inject.SystemCodeCache;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.validation.MaskString;
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
    implements Serializable, ApiPersonAwareWritable, ApiTypedIdentifier<String> {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletePerson.class);

  private static ApiSystemCodeCache systemCodes;

  /**
   * Name suffix.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum AutoCompleteNameSuffix {

    ESQUIRE("esq", new String[] {"esq", "eq", "esqu"}),

    SECOND("ii", new String[] {"ii", "2", "2nd", "second", "02"}),

    THIRD("iii", new String[] {"iii", "3", "3rd", "third", "03"}),

    FOURTH("iv", new String[] {"iv", "iiii", "4", "4th", "fourth", "04"}),

    JR("jr", new String[] {"jr", "junior", "jnr"}),

    SR("sr", new String[] {"sr", "senior", "snr"}),

    MD("md", new String[] {"md", "dr", "doc", "doctor"}),

    PHD("phd", new String[] {"phd", "professor", "prof"}),

    JD("jd", new String[] {"jd"});

    private final String intake;

    @JsonIgnore
    private final String[] legacy;

    // Key = legacy free-form value.
    @JsonIgnore
    private static final Map<String, AutoCompleteNameSuffix> mapLegacy = new HashMap<>();

    // Key = Intake value.
    @JsonIgnore
    private static final Map<String, AutoCompleteNameSuffix> mapIntake = new HashMap<>();

    private AutoCompleteNameSuffix(String intake, String[] legacy) {
      this.intake = intake;
      this.legacy = legacy;
    }

    @JsonValue
    public String getIntake() {
      return intake;
    }

    @JsonIgnore
    public String[] getLegacy() {
      return legacy;
    }

    public AutoCompleteNameSuffix lookupLegacy(String val) {
      return AutoCompleteNameSuffix.findByLegacy(val);
    }

    public static AutoCompleteNameSuffix findByLegacy(String legacy) {
      return mapLegacy.get(legacy);
    }

    public AutoCompleteNameSuffix lookupIntake(String val) {
      return AutoCompleteNameSuffix.findByIntake(val);
    }

    public static AutoCompleteNameSuffix findByIntake(String legacy) {
      return mapIntake.get(legacy);
    }

    static {
      for (AutoCompleteNameSuffix e : AutoCompleteNameSuffix.values()) {
        mapIntake.put(e.intake, e);
        for (String leg : e.getLegacy()) {
          mapLegacy.put(leg, e);
        }
      }
    }

  }

  /**
   * County.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum AutoCompleteCounty implements ApiSysCodeAware {

    NONE(0, "None", "0"),

    ALAMEDA(6079, "Alameda", "1"),

    BUTTE(6080, "Butte", "4"),

    CONTRA_COSTA(6081, "Contra Costa", "7"),

    DEL_NORTE(6082, "Del Norte", "8"),

    EL_DORADO(6083, "El Dorado", "9"),

    FRESNO(6084, "Fresno", "10"),

    GLENN(6085, "Glenn", "11"),

    IMPERIAL(6086, "Imperial", "13"),

    INYO(6087, "Inyo", "14"),

    KERN(6088, "Kern", "15"),

    KINGS(6089, "Kings", "16"),

    MARIN(6090, "Marin", "21"),

    MARIPOSA(6091, "Mariposa", "22"),

    MENDOCINO(6092, "Mendocino", "23"),

    MERCED(6093, "Merced", "24"),

    MONTEREY(6094, "Monterey", "27"), NAPA(6095, "Napa", "28"),

    ORANGE(6096, "Orange", "30"), PLACER(6097, "Placer", "31"),

    SACRAMENTO(6098, "Sacramento", "34"),

    SAN_BENITO(6099, "San Benito", "35"),

    SAN_BERNARDINO(6100, "San Bernardino", "36"),

    SAN_DIEGO(6101, "San Diego", "37"),

    SAN_FRANCISCO(6102, "San Francisco", "38"),

    SAN_JOAQUIN(6103, "San Joaquin", "39"),

    SAN_LUIS_OBISPO(6104, "San Luis Obispo", "40"),

    SAN_MATEO(6105, "San Mateo", "41"),

    SANTA_BARBARA(6106, "Santa Barbara", "42"),

    SANTA_CLARA(6107, "Santa Clara", "43"),

    SANTA_CRUZ(6108, "Santa Cruz", "44"),

    SHASTA(6109, "Shasta", "45"),

    SOLANO(6110, "Solano", "48"),

    SONOMA(6111, "Sonoma", "49"),

    STANISLAUS(6112, "Stanislaus", "50"),

    SUTTER(6113, "Sutter", "51"),

    TEHAMA(6114, "Tehama", "52"),

    TRINITY(6115, "Trinity", "53"),

    TULARE(6116, "Tulare", "54"),

    TUOLUMNE(6117, "Tuolumne", "55"),

    VENTURA(6118, "Ventura", "56"),

    YOLO(6119, "Yolo", "57"),

    YUBA(6120, "Yuba", "58");

    private final int sysId;
    private final String description;
    private final String countyCd;

    private static final Map<Integer, AutoCompleteCounty> mapBySysId = new HashMap<>();
    private static final Map<String, AutoCompleteCounty> mapByCountyCd = new HashMap<>();

    private AutoCompleteCounty(int sysId, String description, String countyCd) {
      this.sysId = sysId;
      this.description = description;
      this.countyCd = countyCd;
    }

    /**
     * Getter for SYS_ID in CMS table SYS_CD_C.
     * 
     * @return SYS_ID
     */
    @Override
    public int getSysId() {
      return sysId;
    }

    @Override
    @JsonValue
    public String getDescription() {
      return description;
    }

    @Override
    public ApiSysCodeAware lookupBySysId(int sysId) {
      return AutoCompleteCounty.findBySysId(sysId);
    }

    public static AutoCompleteCounty findBySysId(int sysId) {
      return mapBySysId.get(sysId);
    }

    public static AutoCompleteCounty findByCountyCd(String countyCd) {
      return mapByCountyCd.containsKey(countyCd) ? mapByCountyCd.get(countyCd)
          : AutoCompleteCounty.NONE;
    }

    static {
      for (AutoCompleteCounty e : AutoCompleteCounty.values()) {
        mapBySysId.put(e.sysId, e);
        mapByCountyCd.put(e.countyCd, e);
      }
    }

    public String getCountyCd() {
      return countyCd;
    }

  }

  /**
   * State.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum AutoCompleteState implements ApiSysCodeAware {

    NONE(0, "None", null),

    ALABAMA(1824, "Alabama", "AL"),

    ALASKA(1823, "Alaska", "AK"),

    AMERICAN_SAMOA(1825, "American Samoa", "AM"),

    ARIZONA(1827, "Arizona", "AZ"),

    ARKANSAS(1826, "Arkansas", "AR"),

    CALIFORNIA(1828, "California", "CA"),

    CANAL_ZONE(1832, "Canal Zone", "CZ"),

    COLORADO(1830, "Colorado", "CO"),

    CONNECTICUT(1831, "Connecticut", "CT"),

    DELAWARE(1834, "Delaware", "DE"),

    DISTRICT_OF_COLUMBIA(1833, "District of Columbia", "DC"),

    FLORIDA(1835, "Florida", "FL"),

    GEORGIA(1836, "Georgia", "GA"),

    GUAM(1837, "Guam", "GU"),

    HAWAII(1838, "Hawaii", "HI"),

    IDAHO(1840, "Idaho", "ID"),

    ILLINOIS(1841, "Illinois", "IL"),

    INDIANA(1842, "Indiana", "IN"),

    IOWA(1839, "Iowa", "IA"),

    KANSAS(1843, "Kansas", "KS"),

    KENTUCKY(1844, "Kentucky", "KY"),

    LOUISIANA(1845, "Louisiana", "LA"),

    MAINE(1848, "Maine", "ME"),

    MARYLAND(1847, "Maryland", "MD"),

    MASSACHUSETTS(1846, "Massachusetts", "MA"),

    MICHIGAN(1849, "Michigan", "MI"),

    MINNESOTA(1850, "Minnesota", "MN"),

    MISSISSIPPI(1852, "Mississippi", "MS"),

    MISSOURI(1851, "Missouri", "MO"),

    MONTANA(1853, "Montana", "MT"),

    NEBRASKA(1856, "Nebraska", "NE"),

    NEVADA(1860, "Nevada", "NV"),

    NEW_HAMPSHIRE(1857, "New Hampshire", "NH"),

    NEW_JERSEY(1858, "New Jersey", "NJ"),

    NEW_MEXICO(1859, "New Mexico", "NM"),

    NEW_YORK(1861, "New York", "NY"),

    NORTH_CAROLINA(1854, "North Carolina", "NC"),

    NORTH_DAKOTA(1855, "North Dakota", "ND"),

    NORTHERN_MARIANAS_ISLANDS(1829, "Northern Marianas Islands", "CM"),

    OHIO(1862, "Ohio", "OH"),

    OKLAHOMA(1863, "Oklahoma", "OK"),

    OREGON(1864, "Oregon", "OR"),

    PENNSYLVANIA(1865, "Pennsylvania", "PA"),

    PUERTO_RICO(1866, "Puerto Rico", "PR"),

    RHODE_ISLAND(1867, "Rhode Island", "RI"),

    SOUTH_CAROLINA(1868, "South Carolina", "SC"),

    SOUTH_DAKOTA(1869, "South Dakota", "SD"),

    TENNESSEE(1870, "Tennessee", "TN"),

    TEXAS(1872, "Texas", "TX"),

    TRUST_TERRITORIES(1871, "Trust Territories", "TT"),

    UTAH(1873, "Utah", "UT"),

    VERMONT(1876, "Vermont", "VT"),

    VIRGIN_ISLANDS(1875, "Virgin Islands", "VI"),

    VIRGINIA(1874, "Virginia", "VA"),

    WASHINGTON(1877, "Washington", "WA"),

    WEST_VIRGINIA(1879, "West Virginia", "WV"),

    WISCONSIN(1878, "Wisconsin", "WI"),

    WYOMING(1880, "Wyoming", "WY");

    private final int sysId;
    private final String description;
    private final String stateCd;

    private static final Map<Integer, AutoCompleteState> mapBySysId = new HashMap<>();
    private static final Map<String, AutoCompleteState> mapByStateCd = new HashMap<>();

    private AutoCompleteState(int sysId, String description, String stateCd) {
      this.sysId = sysId;
      this.description = description;
      this.stateCd = stateCd;
    }

    /**
     * Getter for SYS_ID in CMS table SYS_CD_C.
     * 
     * @return SYS_ID
     */
    @Override
    public int getSysId() {
      return sysId;
    }

    @Override
    @JsonIgnore
    public String getDescription() {
      return description;
    }

    @JsonValue
    public String getStateCd() {
      return stateCd;
    }

    @Override
    public ApiSysCodeAware lookupBySysId(int sysId) {
      return AutoCompleteState.findBySysId(sysId);
    }

    public static AutoCompleteState findBySysId(int sysId) {
      return mapBySysId.get(sysId);
    }

    public static AutoCompleteState findByStateCd(final String stateCd) {
      return mapBySysId.get(stateCd);
    }

    static {
      for (AutoCompleteState e : AutoCompleteState.values()) {
        mapBySysId.put(e.sysId, e);
        mapByStateCd.put(e.stateCd, e);
      }
    }

  }

  /**
   * Languages.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  public enum AutoCompleteLanguage implements ApiSysCodeAware {

    ENGLISH(1253, "English", 7),

    SPANISH(1274, "Spanish", 1),

    AMERICAN_SIGN_LANGUAGE(1248, "American Sign Language", 13),

    ARABIC(1249, "Arabic", 14),

    ARMENIAN(1250, "Armenian", 15),

    CAMBODIAN(1251, "Cambodian", 19),

    CANTONESE(1252, "Cantonese", 74),

    FARSI(1254, "Farsi", 41),

    FILIPINO(3198, "Filipino", 49),

    FRENCH(1255, "French", 28),

    GERMAN(1267, "German", 29),

    HAWAIIAN(1268, "Hawaiian", 99),

    HEBREW(1256, "Hebrew", 33),

    HMONG(1257, "Hmong", 35),

    ILACANO(1258, "Ilacano", 77),

    INDOCHINESE(3199, "Indochinese", 99),

    ITALIAN(1259, "Italian", 42),

    JAPANESE(1260, "Japanese", 3),

    KOREAN(1261, "Korean", 4),

    LAO(1262, "Lao", 43),

    MANDARIN(1263, "Mandarin", 75),

    MIEN(1264, "Mien", 76),

    OTHER_CHINESE(1265, "Other Chinese", 2),

    OTHER_NON_ENGLISH(1266, "Other Non-English", 99),

    POLISH(1269, "Polish", 50),

    PORTUGUESE(1270, "Portuguese", 51),

    ROMANIAN(3200, "Romanian", 99),

    RUSSIAN(1271, "Russian", 54),

    SAMOAN(1272, "Samoan", 55),

    SIGN_LANGUAGE_NOT_ASL(1273, "Sign Language (Not ASL)", 78),

    TAGALOG(1275, "Tagalog", 5),

    THAI(1276, "Thai", 65),

    TURKISH(1277, "Turkish", 67),

    VIETNAMESE(1278, "Vietnamese", 69);

    private final int sysId;
    private final String description;
    private final int displayOrder;

    private static final Map<Integer, AutoCompleteLanguage> mapBySysId = new HashMap<>();

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
    @Override
    public int getSysId() {
      return sysId;
    }

    @Override
    @JsonValue
    public String getDescription() {
      return description;
    }

    public int getDisplayOrder() {
      return displayOrder;
    }

    @Override
    public ApiSysCodeAware lookupBySysId(int sysId) {
      return AutoCompleteLanguage.findBySysId(sysId);
    }

    public static AutoCompleteLanguage findBySysId(int sysId) {
      return mapBySysId.get(sysId);
    }

    static {
      for (AutoCompleteLanguage e : AutoCompleteLanguage.values()) {
        mapBySysId.put(e.sysId, e);
      }
    }

  }

  /**
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("javadoc")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public enum AutoCompletePersonAddressType {
    Home, School, Work, Placement, Homeless, Other // NOSONAR
  }

  /**
   * Child class. Represents the Address section of Intake Auto-complete.
   * 
   * @author CWDS API Team
   */
  public static final class AutoCompletePersonAddress
      implements Serializable, ApiTypedIdentifier<String>, ApiAddressAwareWritable {

    /**
     * Base serialization version. Increment by class version.
     */
    private static final long serialVersionUID = 1L;

    private String id;

    @JsonProperty("street_address")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String streetAddress;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String city;

    @JsonProperty("state")
    private AutoCompleteState stateType = AutoCompleteState.NONE;

    // Bug #141508231: county not in Intake API swagger.yml. Intake JSON parsing error.
    @JsonIgnore
    private AutoCompleteCounty county;

    // Getter displays JSON.
    @JsonIgnore
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
    public AutoCompletePersonAddress(ApiAddressAware addr) {
      if (StringUtils.isNotBlank(addr.getAddressId())) {
        this.setId(addr.getAddressId());
      }
      if (StringUtils.isNotBlank(addr.getCity())) {
        this.setCity(addr.getCity());
      }
      if (StringUtils.isNotBlank(addr.getCounty())) {
        this.setCounty(AutoCompleteCounty.findByCountyCd(addr.getCounty()).getCountyCd());
      }
      if (StringUtils.isNotBlank(addr.getState()) && NumberUtils.isDigits(addr.getState())) {
        this.setStateType(AutoCompleteState.findBySysId(Integer.parseInt(addr.getState())));
      }
      if (StringUtils.isNotBlank(addr.getStreetAddress())) {
        this.setStreetAddress(addr.getStreetAddress());
      }
      if (StringUtils.isNotBlank(addr.getZip())) {
        this.setZip(addr.getZip());
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
    @JsonProperty("state")
    public String getState() {
      return this.stateType != null ? this.stateType.getStateCd() : null;
    }

    @Override
    public void setState(String state) {
      if (StringUtils.isNotBlank(state)) {
        this.stateType = AutoCompleteState.findByStateCd(state);
      }
    }

    @JsonProperty("zip")
    @Override
    public String getZip() {
      return StringUtils.isNotBlank(this.zip) && !"0".equals(this.zip) ? this.zip : null;
    }

    @Override
    public void setZip(String zip) {
      this.zip = zip;
    }

    @Override
    @JsonIgnore
    public String getCounty() {
      return county != null ? county.getCountyCd() : null;
    }

    @SuppressWarnings("javadoc")
    @JsonProperty("county")
    @JsonIgnore
    public String getCountyType() {
      return county != null ? county.getDescription() : null;
    }

    @Override
    public void setCounty(String county) {
      if (StringUtils.isNotBlank(county)) {
        this.county = AutoCompleteCounty.findByCountyCd(county);
      }
    }

    /**
     * Getter for address type.
     * 
     * @return address type
     */
    public AutoCompletePersonAddressType getAddressType() {
      return addressType;
    }

    /**
     * Setter for address type.
     * 
     * @param addressType address type
     */
    public void setAddressType(AutoCompletePersonAddressType addressType) {
      this.addressType = addressType;
    }

    /**
     * Getter for state type.
     * 
     * @return state type
     */
    public AutoCompleteState getStateType() {
      return stateType;
    }

    /**
     * Setter for state type.
     * 
     * @param stateType state type
     */
    public void setStateType(AutoCompleteState stateType) {
      this.stateType = stateType;
    }

    @JsonIgnore
    @Override
    public String getAddressId() {
      return this.id;
    }

  }

  /**
   * Child class. Represents the Phone section of Intake Auto-complete.
   * 
   * @author CWDS API Team
   */
  public static final class AutoCompletePersonPhone
      implements Serializable, ApiTypedIdentifier<String>, ApiPhoneAwareWritable {

    /**
     * Base serialization version. Increment by class version.
     */
    private static final long serialVersionUID = 1L;

    private String id;

    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String phoneNumber;

    /**
     * Not specified by Intake Person Auto-complete YAML.
     */
    @JsonIgnore
    private String phoneNumberExtension;

    @JsonProperty("type")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ApiPhoneAware.PhoneType phoneType;

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
    public AutoCompletePersonPhone(ApiPhoneAware other) {
      if (other != null) {
        setPhoneNumber(other.getPhoneNumber());
        setPhoneType(other.getPhoneType());
      }
    }

    @JsonIgnore
    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String id) {
      this.id = id;
    }

    @Override
    @JsonIgnore
    public String getPhoneNumber() {
      return this.phoneNumber;
    }

    @Override
    @JsonIgnore
    public String getPhoneNumberExtension() {
      return this.phoneNumberExtension;
    }

    @Override
    @JsonIgnore
    public PhoneType getPhoneType() {
      return this.phoneType;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @Override
    public void getPhoneNumberExtension(String phoneNumberExtension) {
      this.phoneNumberExtension = phoneNumberExtension;
    }

    @Override
    public void setPhoneType(PhoneType phoneType) {
      this.phoneType = phoneType;
    }

    @JsonIgnore
    @Override
    public String getPhoneId() {
      return this.id;
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
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String middleName;

  @JsonProperty("last_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String lastName;

  @JsonProperty("name_suffix")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private AutoCompleteNameSuffix nameSuffix;

  @JsonProperty("gender")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String gender;

  /**
   * Getter method {@link #getSsn()} masks outbound SSN.
   */
  @JsonIgnore
  private String ssn;

  @JsonProperty("date_of_birth")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String dateOfBirth;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<AutoCompletePersonAddress> addresses = new ArrayList<>();

  @JsonProperty("phone_numbers")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<AutoCompletePersonPhone> phoneNumbers = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<AutoCompleteLanguage> languages = new ArrayList<>();

  private Map<String, String> highlight;

  /**
   * Default constructor.
   */
  public AutoCompletePerson() {
    // default, no-op.
  }

  /**
   * OLD SCHOOL: Construct from incoming ElasticSearchPerson by reading the underlying "source"
   * data.
   * 
   * @param esp incoming {@link ElasticSearchPerson}
   */
  public AutoCompletePerson(ElasticSearchPerson esp) {
    this.setId(esp.getId());

    // #136570057: mask results, not data in Elastic search.
    // Minimal system code translation to meet contract interface.

    if (esp.getSourceObj() != null) {

      if (esp.getSourceObj() instanceof ApiPersonAware) {
        final ApiPersonAware personAware = (ApiPersonAware) esp.getSourceObj();

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
          switch (personAware.getGender()) {
            case "M":
              this.setGender("male");
              break;
            case "F":
              this.setGender("female");
              break;
            // case "U":
            // default:
            // this.setGender("unknown");
            // break;
          }
        }
        if (personAware.getBirthDate() != null) {
          this.setBirthDate(personAware.getBirthDate());
        }
        if (StringUtils.isNotBlank(personAware.getSsn())) {
          this.setSsn(personAware.getSsn());
        }

        // Legacy name suffix is free-form, whereas Intake is an enum.
        // Implement AFTER R1 production release.
        if (StringUtils.isNotBlank(personAware.getNameSuffix())) {
          this.setNameSuffix(personAware.getNameSuffix());
        }

        // Highlights.
        if (esp.getHighlights() != null && !esp.getHighlights().isEmpty()) {
          this.setHighlight(esp.getHighlights());
        }
      }

      // Address.
      if (esp.getSourceObj() instanceof ApiAddressAware) {
        addAddress(new AutoCompletePersonAddress((ApiAddressAware) esp.getSourceObj()));
      }

      if (esp.getSourceObj() instanceof ApiMultipleAddressesAware) {
        final ApiMultipleAddressesAware theAddresses =
            (ApiMultipleAddressesAware) esp.getSourceObj();
        for (ApiAddressAware phone : theAddresses.getAddresses()) {
          addAddress(new AutoCompletePersonAddress(phone));
        }
      }

      // Phone.
      if (esp.getSourceObj() instanceof ApiMultiplePhonesAware) {
        final ApiMultiplePhonesAware thePhones = (ApiMultiplePhonesAware) esp.getSourceObj();
        for (ApiPhoneAware phone : thePhones.getPhones()) {
          addPhone(new AutoCompletePersonPhone(phone));
        }
      } else if (esp.getSourceObj() instanceof ApiPhoneAware) {
        final ApiPhoneAware phone = (ApiPhoneAware) esp.getSourceObj();
        addPhone(new AutoCompletePersonPhone(phone));
      }

      // Language.
      if (esp.getSourceObj() instanceof ApiMultipleLanguagesAware) {
        final ApiMultipleLanguagesAware langs = (ApiMultipleLanguagesAware) esp.getSourceObj();
        for (ApiLanguageAware lang : langs.getLanguages()) {
          addLanguage(AutoCompleteLanguage.findBySysId(lang.getLanguageSysId()));
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
    if (firstName != null && !firstName.isEmpty()) {
      this.firstName = firstName.trim();
    }
  }

  @Override
  public String getMiddleName() {
    return middleName;
  }

  @Override
  public void setMiddleName(String middleName) {
    if (middleName != null && !middleName.isEmpty()) {
      this.middleName = middleName.trim();
    }
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    if (lastName != null && !lastName.isEmpty()) {
      this.lastName = lastName.trim();
    }
  }

  /**
   * Getter for name suffix
   * 
   * @return name suffix
   */
  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return this.nameSuffix == null ? null : this.nameSuffix.intake;
  }

  /**
   * Setter for name suffix.
   * 
   * @param nameSuffix name suffix
   */
  @Override
  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = AutoCompleteNameSuffix
        .findByLegacy(nameSuffix.trim().toLowerCase().replaceAll("[^a-zA-Z0-9]", ""));
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return gender;
  }

  @Override
  public void setGender(String gender) {
    this.gender = gender != null ? gender.trim().toLowerCase() : null;
  }

  @JsonProperty("ssn")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @Override
  public String getSsn() {
    return new MaskString().maskSsn(ssn);
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
  @JsonIgnore
  public void addPhone(AutoCompletePersonPhone phone) {
    if (this.phoneNumbers == null) {
      this.phoneNumbers = new ArrayList<>();
    }

    this.phoneNumbers.add(phone);
  }

  /**
   * Add a language
   * 
   * @param language language to add
   */
  @JsonIgnore
  public void addLanguage(AutoCompleteLanguage language) {
    if (this.languages == null) {
      this.languages = new ArrayList<>();
    }

    this.languages.add(language);
  }

  /**
   * Getter for birth date. Alias to {@link #getDateOfBirth()}.
   * 
   * @return date of birth
   */
  @Override
  @JsonIgnore
  public Date getBirthDate() {
    return StringUtils.isNotBlank(this.dateOfBirth) ? DomainChef.uncookDateString(dateOfBirth)
        : null;
  }

  @Override
  @JsonIgnore
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

  /**
   * Getter for highlights. Key = field name, Value = match with mark-up.
   * 
   * @return Map of highlights
   */
  public Map<String, String> getHighlight() {
    return highlight;
  }

  /**
   * Setter for highlights.
   * 
   * @param highlight Key = field name, Value = match with mark-up.
   */
  public void setHighlight(Map<String, String> highlight) {
    if (highlight != null) {
      this.highlight = highlight;
    } else {
      this.highlight.clear();
    }
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  /**
   * Getter for CMS system code cache.
   * 
   * @return reference to CMS system code cache
   */
  public static ApiSystemCodeCache getSystemCodes() {
    return systemCodes;
  }

  /**
   * Store a reference to the singleton CMS system code cache for quick convenient access.
   * 
   * @param systemCodes CMS system code cache
   */
  @Inject
  public static void setSystemCodes(@SystemCodeCache ApiSystemCodeCache systemCodes) {
    AutoCompletePerson.systemCodes = systemCodes;
  }

  @JsonProperty("id")
  @Override
  public Serializable getPrimaryKey() {
    return this.getId();
  }

}
