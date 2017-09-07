package gov.ca.cwds.rest.api.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for Intake LOV listings.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
@JsonSerialize(using = IntakeLovResponse.IntakeLovSerializer.class)
public class IntakeLovResponse implements Response, ApiMarker {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  public static class IntakeLovSerializer extends JsonSerializer<IntakeLovResponse> {
    @Override
    public void serialize(IntakeLovResponse value, JsonGenerator jsonGenerator,
        SerializerProvider provider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeStringField("address_type", "fred");// dynamic field name
      jsonGenerator.writeEndObject();
    }
  }

  @JsonIgnore
  private List<IntakeLovEntry> lovs = new ArrayList<>();

  /**
   * Disallow use of default constructor.
   */
  @SuppressWarnings("unused")
  private IntakeLovResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from person array.
   * 
   * @param lovs array of {@link IntakeLovEntry}
   */
  public IntakeLovResponse(List<IntakeLovEntry> lovs) {
    this.lovs = lovs;
  }

  /**
   * Getter for array of {@link IntakeLovEntry LOV's}
   * 
   * @return LOV objects
   */
  @JsonValue
  public List<IntakeLovEntry> getPersons() {
    return lovs;
  }

  /**
   * Setter for array of {@link IntakeLovEntry lovs}.
   * 
   * @param lovs person objects suitable for Intake LOV
   */
  public void setPersons(List<IntakeLovEntry> lovs) {
    this.lovs = lovs;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public static void main(String[] args) {
    IntakeLovResponse term = new IntakeLovResponse();
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(term);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(jsonString);
  }
}
