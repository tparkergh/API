package gov.ca.cwds.rest.api.domain.es;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AutoCompletePersonTest {

  String id = "1234567ABC";
  String firstName = "Mike";
  String lastName = "Smith";
  String gender = "M";
  String birthDate = "2000-10-31";
  String ssn = "111122333";
  String sourceType = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient";
  String sourceJson =
      "{\"adjudicatedDelinquentIndicator\":\"\",\"adoptionStatusCode\":\"N\",\"alienRegistrationNumber\":\"\",\"birthCity\":\"\",\"birthCountryCodeType\":0,\"birthDate\":\"1953-03-12\",\"birthFacilityName\":\"\",\"birthStateCodeType\":0,\"birthplaceVerifiedIndicator\":\"N\",\"childClientIndicatorVar\":\"N\",\"clientIndexNumber\":\"\",\"commentDescription\":\"\",\"commonFirstName\":\"Louis\",\"commonLastName\":\"Smith\",\"commonMiddleName\":\"\",\"confidentialityActionDate\":null,\"confidentialityInEffectIndicator\":\"N\",\"creationDate\":\"2003-03-12\",\"currCaChildrenServIndicator\":\"N\",\"currentlyOtherDescription\":\"\",\"currentlyRegionalCenterIndicator\":\"N\",\"deathDate\":null,\"deathDateVerifiedIndicator\":\"N\",\"deathPlace\":\"\",\"deathReasonText\":\"\",\"driverLicenseNumber\":\"\",\"driverLicenseStateCodeType\":0,\"emailAddress\":\"\",\"estimatedDobCode\":\"Y\",\"ethUnableToDetReasonCode\":\"\",\"fatherParentalRightTermDate\":null,\"genderCode\":\"M\",\"healthSummaryText\":\"\",\"hispUnableToDetReasonCode\":\"\",\"hispanicOriginCode\":\"U\",\"id\":\"LDSPRcl0I3\",\"immigrationCountryCodeType\":0,\"immigrationStatusType\":0,\"incapacitatedParentCode\":\"U\",\"individualHealthCarePlanIndicator\":\"N\",\"lastUpdatedId\":\"0I3\",\"lastUpdatedTime\":1047479557134,\"limitationOnScpHealthIndicator\":\"N\",\"literateCode\":\"U\",\"maritalCohabitatnHstryIndicatorVar\":\"N\",\"maritalStatusType\":0,\"militaryStatusCode\":\"U\",\"motherParentalRightTermDate\":null,\"namePrefixDescription\":\"\",\"nameType\":{\"sys_id\":1313,\"short_description\":\"Legal\",\"logical_id\":\"0003\"},\"outstandingWarrantIndicator\":\"N\",\"prevCaChildrenServIndicator\":\"N\",\"prevOtherDescription\":\"\",\"prevRegionalCenterIndicator\":\"N\",\"primaryEthnicityType\":{\"sys_id\":839,\"short_description\":\"White*\",\"logical_id\":\"01\"},\"primaryKey\":\"LDSPRcl0I3\",\"primaryLanguageType\":{\"sys_id\":1253,\"short_description\":\"English\",\"logical_id\":\"07\"},\"religionType\":0,\"replicationDate\":1047479557134,\"replicationOperation\":\"U\",\"secondaryLanguageType\":0,\"sensitiveHlthInfoOnFileIndicator\":\"N\",\"sensitivityIndicator\":\"N\",\"soc158PlacementCode\":\"N\",\"soc158SealedClientIndicator\":\"N\",\"socialSecurityNumChangedCode\":\"N\",\"socialSecurityNumber\":\"\",\"suffixTitleDescription\":\"\",\"tribalAncestryClientIndicatorVar\":\"N\",\"tribalMembrshpVerifctnIndicatorVar\":\"N\",\"unemployedParentCode\":\"U\",\"zippyCreatedIndicator\":\"Y\"}";
  String highlight = "{\"firstName\":\"<em>mik</em>e\", \"lastName\":\"sm<em>ith</em>\"}";

  String city = "San Jose";
  String countyCode = "43";
  String countyName = "Santa Clara";
  String state = "1828";
  String stateCode = "CA";
  String address = "123 first street";
  String zip = "98765";

  String testGet = "test get";
  String testGetCounty = "57";
  String testCountyName = "Yolo";
  String testGetStateCode = "NV";
  String testGetStateType = "NEVADA";


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Before
  public void setup() {}

  @Test
  public void testAutoCompletePersonAddressConstuctor() {
    ApiAddressAware add = new Address(city, countyCode, state, address, zip);

    AutoCompletePerson.AutoCompletePersonAddress acpa =
        new AutoCompletePerson.AutoCompletePersonAddress(add);
    assertThat(acpa, notNullValue());
  }

  @Test
  public void testAutoCompletePersonAddressGetters() {
    ApiAddressAware add = new Address(city, countyCode, state, address, zip);

    AutoCompletePerson.AutoCompletePersonAddress acpa =
        new AutoCompletePerson.AutoCompletePersonAddress(add);

    assertThat(acpa.getCity(), is(equalTo(city)));
    assertThat(acpa.getCountyType(), is(equalTo(countyName)));
    assertThat(acpa.getState(), is(equalTo(stateCode)));
    assertThat(acpa.getStreetAddress(), is(equalTo(address)));
    assertThat(acpa.getZip(), is(equalTo(zip)));

  }

  @Test
  public void testAutoCompletePersonAddressSetters() {
    ApiAddressAware add = new Address(city, countyCode, state, address, zip);

    AutoCompletePerson.AutoCompletePersonAddress acpa =
        new AutoCompletePerson.AutoCompletePersonAddress(add);

    acpa.setCity(testGet);
    assertThat(acpa.getCity(), is(equalTo(testGet)));
    acpa.setCounty(testGetCounty);
    assertThat(acpa.getCountyType(), is(equalTo(testCountyName)));
    acpa.setStreetAddress(testGet);
    assertThat(acpa.getStreetAddress(), is(equalTo(testGet)));
    acpa.setState(testGetStateCode);
    assertThat(acpa.getState(), is(equalTo(testGetStateCode)));
  }

  @Test
  public void testConstuctorSuccess() {

    ElasticSearchPerson esp = validESP();

    AutoCompletePerson acp = new AutoCompletePerson(esp);
    assertThat(acp, notNullValue());

  }

  // @Test
  // public void testGenderMSuccess() throws JsonProcessingException {
  //
  // ElasticSearchPerson esp = validESP();
  //
  // AutoCompletePerson acp = new AutoCompletePerson(esp);
  //
  // assertThat(acp.getGender(), is(equalTo("Male")));
  //
  // }

  // @Test
  // public void testSerializeAutoCompletePersonFromJSON() throws Exception {
  //
  // ElasticSearchPerson esp = validESP();
  //
  // AutoCompletePerson expected = new AutoCompletePerson(esp);
  //
  // AutoCompletePerson serialized = MAPPER.readValue(
  // fixture("fixtures/domain/es/autoCompletePerson/valid.json"), AutoCompletePerson.class);
  //
  // // final String json = serialized.getHighlight().replaceAll("\\s+\",", "\",");
  // // System.out.println("acp = " + serialized.getHighlight());
  // // System.out.println("json = " + json);
  // assertThat(serialized, is(expected));
  //
  // }

  // @Test
  // public void testDeserializeAutoCompletePersonToJSON() throws IOException {
  //
  // ElasticSearchPerson esp = validESP();
  //
  // AutoCompletePerson actual = new AutoCompletePerson(esp);
  //
  // final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(
  // (MAPPER.readValue(fixture("fixtures/domain/es/autoCompletePerson/valid.json"),
  // AutoCompletePerson.class)));
  //
  // String pa = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(actual);
  // // System.out.println(pa);
  // // System.out.println(expected);
  //
  // assertThat(pa).isEqualTo(expected);
  //
  // }

  // @Test
  // public void testUnkownGenderSuccess() {
  //
  // ElasticSearchPerson esp = validESP();
  // esp.setGender("U");
  //
  // AutoCompletePerson acp = new AutoCompletePerson(esp);
  //
  // assertThat(acp.getGender(), is(equalTo("Unknown")));
  //
  // }

  // @Test
  // public void testIsApiPersonAware() {
  //
  // ElasticSearchPerson esp = validESP();
  //
  // Object sourceObj = esp.getSourceObj();
  //
  // if (esp.getSourceObj() instanceof ApiPersonAware) {
  // System.out.println("is person aware" + sourceObj.getClass().getName());
  // } else {
  // System.out.println("is NOT person aware" + sourceObj.getClass().getName());
  // }
  //
  // }

  private ElasticSearchPerson validESP() {

    ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, null, null,
        gender, birthDate, ssn, sourceType, sourceJson, highlight, null, null, null);

    final String json = esp.getHighlightFields().replaceAll("\\s+\",", "\",");
    //
    // System.out.println("highlight = " + esp.getHighlightFields());
    // System.out.println("json = " + json);
    //
    return esp;
  }

  public static final class Address implements ApiAddressAware {

    private String city;
    private String county;
    private String state;
    private String streetAddress;
    private String zip;

    public Address(String city, String county, String state, String address, String zip) {
      this.city = city;
      this.county = county;
      this.state = state;
      this.streetAddress = address;
      this.zip = zip;
    }

    @Override
    public String getCity() {
      // TODO Auto-generated method stub
      return this.city;
    }

    @Override
    public String getCounty() {
      // TODO Auto-generated method stub
      return this.county;
    }

    @Override
    public String getState() {
      // TODO Auto-generated method stub
      return this.state;
    }

    @Override
    public String getStreetAddress() {
      // TODO Auto-generated method stub
      return this.streetAddress;
    }

    @Override
    public String getZip() {
      // TODO Auto-generated method stub
      return this.zip;
    }

  }
}
