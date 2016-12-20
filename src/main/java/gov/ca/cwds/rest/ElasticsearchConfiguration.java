package gov.ca.cwds.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;

/**
 * Represents the configuration settings for {@link ElasticsearchDao}.
 * 
 * @author CWDS API Team
 */
public class ElasticsearchConfiguration {

  @NotNull
  @JsonProperty("elasticsearch.host")
  private String elasticsearchHost;

  @NotNull
  @JsonProperty("elasticsearch.cluster")
  private String elasticsearchCluster;

  @NotNull
  @JsonProperty("elasticsearch.clientnode")
  private String elasticsearchClientNodeName;

  @NotNull
  @JsonProperty("elasticsearch.clientport")
  private String elasticsearchClientPort;

  @NotNull
  @JsonProperty("elasticsearch.port")
  private String elasticsearchPort;

  @NotNull
  @JsonProperty("peopleIndexName")
  private String peopleIndexName;

  @NotNull
  @JsonProperty("peopleIndexType")
  private String peopleIndexType;

  /**
   * @return the elasticsearchHost
   */
  public String getElasticsearchHost() {
    return elasticsearchHost;
  }

  /**
   * @return the elasticsearchCluster
   */
  public String getElasticsearchCluster() {
    return elasticsearchCluster;
  }

  /**
   * @return the elasticsearchClientNodeName
   */
  public String getElasticsearchClientNodeName() {
    return elasticsearchClientNodeName;
  }

  /**
   * @return the elasticsearchClientPort
   */
  public String getElasticsearchClientPort() {
    return elasticsearchClientPort;
  }

  /**
   * @return the elasticsearchPort
   */
  public String getElasticsearchPort() {
    return elasticsearchPort;
  }

  /**
   * @return the peopleIndexName
   */
  public String getPeopleIndexName() {
    return peopleIndexName;
  }

  /**
   * @return the peopleIndexType
   */
  public String getPeopleIndexType() {
    return peopleIndexType;
  }

}
