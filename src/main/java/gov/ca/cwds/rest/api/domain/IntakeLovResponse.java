package gov.ca.cwds.rest.api.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.inject.Inject;

import gov.ca.cwds.ObjectMapperUtils;
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
// @JsonSerialize(using = IntakeLovResponse.IntakeLovSerializer.class)
public class IntakeLovResponse implements Response, ApiMarker {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovResponse.class);

  /**
   * Since LOV entries change infrequently (i.e., when did America last admit another state or when
   * did California split a county?), the Intake has requested that the LOV API return LOV arrays
   * under the attribute name of the parent category. That is, LOV data drive the JSON attribute
   * names; the attributes are not fixed, like most other APIs.
   * 
   * @author CWDS API Team
   */
  public static class IntakeLovSerializer extends JsonSerializer<IntakeLovResponse> {

    private ObjectMapper mapper;

    public IntakeLovSerializer() {
      this.mapper = ObjectMapperUtils.createObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Inject
    public IntakeLovSerializer(ObjectMapper mapper) {
      this.mapper = mapper;
    }

    private void jsonify(final JsonGenerator g, final IntakeLovEntry lov) {
      try {
        g.writeStartObject();
        g.writeStringField("code", lov.isUseLogical() ? lov.getLegacyLogicalCode()
            : Long.toString(lov.getLegacySystemCodeId()));
        g.writeStringField("value", lov.getIntakeValue());

        if (StringUtils.isNotBlank(lov.getCategory())) {
          g.writeStringField("parent_type", lov.getCategory());
        }

        g.writeEndObject();
      } catch (Exception e) { // NOSONAR
        LOGGER.warn("ERROR SERIALIZING INTAKE LOV CATEGORY {} TO JSON", lov.getSubCategory(), e);
      }
    }

    private void writeCategory(final JsonGenerator g, Map.Entry<String, List<IntakeLovEntry>> cat) {
      try {
        g.writeArrayFieldStart(cat.getKey().toLowerCase());
        cat.getValue().stream().forEach(lov -> jsonify(g, lov));
        g.writeEndArray();
      } catch (IOException e) {
        throw new ServiceException("ERROR streaming JSON for LOV category " + cat.getKey(), e);
      }
    }

    @Override
    public void serialize(IntakeLovResponse value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {

      final Map<String, List<IntakeLovEntry>> lovsByCategory = value.getLovEntries().stream()
          .sorted((e1, e2) -> e1.getSubCategory().compareTo(e2.getSubCategory()))
          .collect(Collectors.groupingBy(IntakeLovEntry::getSubCategory));

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

  @JsonUnwrapped
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

}
