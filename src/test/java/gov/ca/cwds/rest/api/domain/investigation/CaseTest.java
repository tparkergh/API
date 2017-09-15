package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public class CaseTest {

  @Test
  public void type() throws Exception {
    assertThat(Case.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    assertThat(target, notNullValue());
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    String actual = target.getEndDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCountyName_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    String actual = target.getCountyName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    SimpleLegacyDescriptor actual = target.getLegacyDescriptor();
    SimpleLegacyDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFocusChild_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    SimplePerson actual = target.getFocusChild();
    SimplePerson expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getServiceComponent_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    String actual = target.getServiceComponent();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    SimplePerson actual = target.getAssignedSocialWorker();
    SimplePerson expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAccessLimitation_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    LimitedAccess actual = target.getAccessLimitation();
    LimitedAccess expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getServiceComponentId_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    String actual = target.getServiceComponentId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    String actual = target.getStartDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getParents_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    Set<SimplePersonWithRelationship> actual = target.getParents();
    Set<SimplePersonWithRelationship> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_Args__() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    int actual = target.hashCode();
    int expected = -1130789618;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    String endDate = null;
    String countyName = null;
    SimpleLegacyDescriptor legacyDescriptor = null;
    SimplePerson focusChild = null;
    String serviceComponent = null;
    SimplePerson assignedSocialWorker = null;
    LimitedAccess accessLimitation = null;
    String serviceComponentId = null;
    String startDate = null;
    Set<SimplePersonWithRelationship> parents = null;
    Case target = new Case(endDate, countyName, legacyDescriptor, focusChild, serviceComponent,
        assignedSocialWorker, accessLimitation, serviceComponentId, startDate, parents);
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
