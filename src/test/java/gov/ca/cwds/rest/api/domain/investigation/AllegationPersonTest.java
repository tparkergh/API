package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.AllegationPersonEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;


@SuppressWarnings("javadoc")
public class AllegationPersonTest {
  protected String firstName = "Joanna";
  protected String lastName = "Kenneson";
  protected String middleName = "";
  protected String suffixTitle = "phd";
  protected String prefixTitle = "Ms";
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  @Before
  public void setup() {}

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    AllegationPerson allegationPerson = new AllegationPerson();
    assertNotNull(allegationPerson);
  }

  @Test
  public void domainConstrutorTest() {
    AllegationPerson domain = new AllegationPerson(firstName, lastName, middleName, suffixTitle,
        prefixTitle, legacyDescriptor);
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleName(), is(equalTo(middleName)));
    assertThat(domain.getSuffixTitle(), is(equalTo(suffixTitle)));
    assertThat(domain.getPrefixTitle(), is(equalTo(prefixTitle)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    AllegationPerson allegationPerson = new AllegationPersonEntityBuilder().build();
    AllegationPerson otherAllegationPerson =
        new AllegationPersonEntityBuilder().setFirstName("Jerry").build();
    assertThat(allegationPerson, is(not(equals(otherAllegationPerson))));
  }

}
