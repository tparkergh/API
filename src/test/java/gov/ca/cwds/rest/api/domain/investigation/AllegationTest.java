package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class AllegationTest {

  private String victimFirstName = "sharon";
  private String perpetratorLastName = "w.";
  private String perpetratorFirstName = "ricky";
  private String dispositionDescription = "substantiated";
  private String victimLastName = "w.";
  private String allegationDescription = "sexual abuse";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Allegation domain =
        new Allegation(victimLastName, victimFirstName, perpetratorLastName, perpetratorFirstName,
            dispositionDescription, allegationDescription);
    assertThat(domain.getVictimLastName(), is(equalTo(victimLastName)));
    assertThat(domain.getVictimFirstName(), is(equalTo(victimFirstName)));
    assertThat(domain.getPerpetratorLastName(), is(equalTo(perpetratorLastName)));
    assertThat(domain.getPerpetratorFirstName(), is(equalTo(perpetratorFirstName)));
    assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(domain.getAllegationDescription(), is(equalTo(allegationDescription)));

  }

}
