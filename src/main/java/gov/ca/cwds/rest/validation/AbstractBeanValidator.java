package gov.ca.cwds.rest.validation;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author CWDS API Team
 *
 */
public abstract class AbstractBeanValidator {
  protected String readBeanValue(Object bean, String property) {
    try {
      return BeanUtils.getProperty(bean, property);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new ValidationException(
          MessageFormat.format("Unable to read '{0}' from bean:{1}", property, bean), e);
    }
  }
}
