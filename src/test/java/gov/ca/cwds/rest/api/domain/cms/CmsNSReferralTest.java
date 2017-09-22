package gov.ca.cwds.rest.api.domain.cms;

import static org.junit.Assert.*;

import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.PersonResourceBuilder;
import gov.ca.cwds.rest.api.domain.Person;
import org.junit.Test;

public class CmsNSReferralTest {

  @Test
  public void shouldBeEqualWhenValuesAreTheSame(){
    Referral referral = new ReferralResourceBuilder().build();
    Person person = new PersonResourceBuilder().build();
    CmsNSReferral cmsReferral = new CmsNSReferral(referral, person);
    CmsNSReferral cmsReferral2 = new CmsNSReferral(referral, person);
    assertEquals(cmsReferral, cmsReferral2);
  }

  @Test
  public void shouldNotBeEqualWhenValuesAreDifferent(){
    Referral referral = new ReferralResourceBuilder().build();
    Person person1 = new PersonResourceBuilder().setFirstName("fred").build();
    Person person2 = new PersonResourceBuilder().setFirstName("bill").build();
    CmsNSReferral cmsReferral = new CmsNSReferral(referral, person1);
    CmsNSReferral cmsReferral2 = new CmsNSReferral(referral, person2);
    assertNotEquals(cmsReferral, cmsReferral2);
  }
}