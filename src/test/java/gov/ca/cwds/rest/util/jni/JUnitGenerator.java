package gov.ca.cwds.rest.util.jni;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Generate JUnit test cases for target class.
 * 
 * @author CWDS API Team
 */
public class JUnitGenerator {

  protected static void printElementDetails() {

  }

  /**
   * @param args command line
   */
  public static void main(String[] args) {

    try {
      String className = "gov.ca.cwds.rest.api.domain.auth.StaffAuthorityPrivilege";

      System.out.println("Class: " + className);
      final Class<?> klazz =
          Class.forName(className, false, Thread.currentThread().getContextClassLoader());

      // ConstructorUtils.getMatchingAccessibleConstructor(cls, parameterTypes)

      final Constructor<?>[] ctors = klazz.getDeclaredConstructors();
      for (Constructor<?> ctor : ctors) {
        System.out.println("Constructor" + ctor);
      }

      klazz.getDeclaredAnnotations();

      final Field[] fields = klazz.getDeclaredFields();
      for (Field f : fields) {
        System.out.println("Field: " + f);
        for (Annotation ann : f.getAnnotations()) {
          System.out.println("\tAnnotation: " + ann);
        }

      }

      // final Object bean = klazz.newInstance();
      // StaffAuthorityPrivilege bean = new StaffAuthorityPrivilege("type", "", "");
      // @SuppressWarnings("rawtypes")
      // final Map desc = BeanUtils.describe(bean);
      // System.out.println(desc);

    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

}

