package gov.ca.cwds.rest.resources.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * Feature used to dynamically apply {@link EmptyBody} filter to resources annotated with
 * {@link EmptyBody}
 * 
 * @author CWDS API Team
 */
@Provider
public class EmptyBodyFeature implements DynamicFeature {

  /**
   * {@inheritDoc}
   * 
   * @see javax.ws.rs.container.DynamicFeature#configure(javax.ws.rs.container.ResourceInfo,
   *      javax.ws.rs.core.FeatureContext)
   */
  @Override
  public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    if (resourceInfo.getResourceMethod().getAnnotation(EmptyBody.class) != null) {
      context.register(EmptyBodyFilter.class);
    }
  }
}
