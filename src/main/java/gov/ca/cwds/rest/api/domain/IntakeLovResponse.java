package gov.ca.cwds.rest.api.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.inject.Inject;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovResponse.class);

  public static class IntakeLovSerializer extends JsonSerializer<IntakeLovResponse> {

    private ObjectMapper mapper;

    public IntakeLovSerializer() {
      this.mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Inject
    public IntakeLovSerializer(ObjectMapper mapper) {
      this.mapper = mapper;
    }

    protected String jsonify(Object obj) {
      String ret = "";
      try {
        ret = mapper.writeValueAsString(obj);
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("ERROR SERIALIZING OBJECT {} TO JSON", obj, e);
      }
      return ret;
    }

    private void writeCategory(final JsonGenerator g, Map.Entry<String, List<IntakeLovEntry>> cat) {
      try {
        g.writeArrayFieldStart(cat.getKey().toLowerCase());
        final String strLovs = String.join(",",
            cat.getValue().stream().map(this::jsonify).collect(Collectors.toList()));
        g.writeString("[" + strLovs + "]");
        g.writeEndArray();
      } catch (IOException e) {
        throw new ServiceException(e);
      }
    }

    @Override
    public void serialize(IntakeLovResponse value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {

      final Map<String, List<IntakeLovEntry>> lovsByCategory = value.getLovEntries().stream()
          .sorted((e1, e2) -> e1.getLegacyMeta().compareTo(e2.getLegacyMeta()))
          .collect(Collectors.groupingBy(IntakeLovEntry::getLegacyMeta));

      gen.writeStartObject();
      lovsByCategory.entrySet().stream().forEach(e -> writeCategory(gen, e));
      gen.writeEndObject();
    }

    public ObjectMapper getMapper() {
      return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
      this.mapper = mapper;
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
   * Preferred constructor. Build from LOV entry array.
   * 
   * @param lovs array of {@link IntakeLovEntry}
   */
  public IntakeLovResponse(List<IntakeLovEntry> lovs) {
    this.lovs = lovs;
  }

  /**
   * Getter for array of {@link IntakeLovEntry LOV's}.
   * 
   * @return LOV objects
   */
  @JsonValue
  public List<IntakeLovEntry> getLovEntries() {
    return lovs;
  }

  /**
   * Setter for array of {@link IntakeLovEntry lovs}.
   * 
   * @param lovs LOV entries
   */
  public void setLovEntries(List<IntakeLovEntry> lovs) {
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
    final List<IntakeLovEntry> lovs = new ArrayList<>();
    lovs.add(
        new IntakeLovEntry("1128", "", "ADDR_TPC", "ADDRESS_TYPE", "1128", "Residence", false));
    lovs.add(new IntakeLovEntry("1823", "AK", "STATE_C", "STATE_TYPE", "1128", "Alaska", true));

    IntakeLovResponse term = new IntakeLovResponse(lovs);
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
