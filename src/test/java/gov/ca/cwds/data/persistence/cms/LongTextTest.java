package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.LongTextResourceBuilder;

/**
 * @author CWDS API Team
 */
public class LongTextTest implements PersistentTestTemplate {

  private String id = "ABC1234567";
  private String lastUpdatedId = "0X5";

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(LongText.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    LongText validLongText = new LongTextEntityBuilder().build();

    gov.ca.cwds.data.persistence.cms.LongText persistent =
        new gov.ca.cwds.data.persistence.cms.LongText(id, validLongText.getCountySpecificCode(),
            validLongText.getTextDescription());

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validLongText.getCountySpecificCode())));
    assertThat(persistent.getTextDescription(), is(equalTo(validLongText.getTextDescription())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.LongText domain = new LongTextResourceBuilder().build();

    LongText persistent = new LongText(id, domain, lastUpdatedId);

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(persistent.getTextDescription(), is(equalTo(domain.getTextDescription())));
  }

}
