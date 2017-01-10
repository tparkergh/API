package gov.ca.cwds.data;

/**
 * Represents an object capable of holding multiple languages.
 * 
 * @author CWDS API Team
 */
public interface IMultipleLanguagesAware {

  /**
   * Get all languages available on this object.
   * 
   * @return array of languages
   */
  ILanguageAware[] getLanguages();

}
