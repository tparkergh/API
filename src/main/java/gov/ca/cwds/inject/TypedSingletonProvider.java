package gov.ca.cwds.inject;

import com.google.inject.Provider;

import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.data.std.ApiMarker;

public abstract class TypedSingletonProvider<T extends ApiMarker> implements Provider<T> {

  public TypedSingletonProvider() {}

  protected final void init() {
    if (DeferredRegistry.<T>unwrap(this.getClassType()) == null) {
      DeferredRegistry.register(this.getClassType(), createInstance());
    }
  }

  protected abstract T createInstance();

  protected abstract Class<T> getClassType();

  @Override
  public final T get() {
    return DeferredRegistry.<T>unwrap(this.getClassType());
  }

}
