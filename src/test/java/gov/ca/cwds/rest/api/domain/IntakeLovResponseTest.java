package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;

public class IntakeLovResponseTest {

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovResponse.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    assertThat(target, notNullValue());
  }

  @Test
  public void getLovEntries_Args__() throws Exception {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    List<IntakeLovEntry> actual = target.getLovEntries();
    List<IntakeLovEntry> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLovEntries_Args__List() throws Exception {
    List<IntakeLovEntry> lovs = new ArrayList<IntakeLovEntry>();
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    target.setLovEntries(lovs);
  }

  @Test
  public void hashCode_Args__() throws Exception {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    int actual = target.hashCode();
    int expected = 629;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    List<IntakeLovEntry> lovs = null;
    IntakeLovResponse target = new IntakeLovResponse(lovs);
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void main_Args__StringArray() throws Exception {
    final List<IntakeLovEntry> lovs = new ArrayList<>();
    lovs.add(new IntakeLovEntry("1128", "", "ADDR_TPC", "ADDRESS_TYPE", "res", "Residence", false));
    lovs.add(new IntakeLovEntry("1823", "AK", "STATE_C", "STATE_TYPE", "ak", "Alaska", true));

    IntakeLovResponse response = new IntakeLovResponse(lovs);
    final ObjectMapper mapper = ObjectMapperUtils.createObjectMapper();

    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(response);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    // LOGGER.info(jsonString);
  }

}
