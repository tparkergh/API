package gov.ca.cwds.rest.api.persistence;

import java.io.Serializable;

/**
 * Marker interface for all persistent classes.
 * 
 * <p>
 * Method {@link #getPrimaryKey()} intentionally returns a generic, serializable key, since the key
 * type varies across databases and other storage platforms, and further allows for composite keys,
 * where appropriate.
 * </p>
 * 
 * <p>
 * PersistentObject marks a class as transmittable and inherently serializable. All classes which
 * implement this interface, including their members and child classes, must themselves be fully
 * serializable.
 * </p>
 * 
 * @author CWDS API Team
 * @see TypedPersistentObject
 */
@FunctionalInterface
public interface PersistentObject extends Serializable {

  /**
   * The key may be any serializable object, including String, Number (and its extensions), or
   * composite key types. By definition, a primary key is uniquely identifies a record and is
   * non-null. In contrast, other unique identifiers, such as unique indexes, are not necessarily
   * constrained the non-null mandate, depending on the platform.
   * 
   * @return the unique primary key
   */
  public Serializable getPrimaryKey();

}
