package gov.ca.cwds.rest.util.jni;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Generate JUnit test cases for target class.
 * 
 * @author CWDS API Team
 */
public class JUnitGenerator {

  private static final String SPACER = "\n--------------------------\n";

  protected static void drillAnnotation(Annotation ann) {
    System.out.println("\t\tAnnotation: " + ann);
  }

  protected static void drillAnnotations(String msg, Annotation[] anns) {
    if (anns != null && anns.length > 0) {
      drillAnnotations(msg, anns, 1);
    }
  }

  protected static void drillAnnotations(String msg, Annotation[] anns, int indent) {
    if (anns != null && anns.length > 0) {
      // System.out.println(StringUtils.repeat('\t', indent) + "Annotations: " + msg + ":");
      for (Annotation ann : anns) {
        drillAnnotation(ann);
      }
    }
  }

  protected static void drillClass(String className) throws Exception {
    System.out.println("Class: " + className);
    final Class<?> klazz =
        Class.forName(className, false, Thread.currentThread().getContextClassLoader());

    System.out.println(SPACER + "CLASS DECLARED ANNOTATIONS:" + SPACER);

    // Class level annotations:
    drillAnnotations("Class Declared", klazz.getDeclaredAnnotations());

    // Constructors:
    final Constructor<?>[] ctors = klazz.getDeclaredConstructors();
    for (Constructor<?> c : ctors) {
      System.out.println("Constructor: " + c);

      int counter = 0;
      for (Parameter p : c.getParameters()) {
        ++counter;
        System.out.println("\tparam " + counter + ": " + p);
        drillAnnotations("", p.getDeclaredAnnotations(), 2);
      }
    }

    // Fields on this class:
    System.out.println(SPACER + "DECLARED FIELDS:" + SPACER);
    for (Field f : klazz.getDeclaredFields()) {
      System.out.println(f);
      drillAnnotations("Field Declared", f.getDeclaredAnnotations());
    }

    // Methods on this class:
    System.out.println(SPACER + "DECLARED METHODS:" + SPACER);
    for (Method m : klazz.getDeclaredMethods()) {
      System.out.println(m);
      drillAnnotations("Method Declared", m.getDeclaredAnnotations());
    }


  }

  /**
   * @param args command line
   */
  public static void main(String[] args) {

    try {
      final String className = args[0];
      drillClass(className);

      // ConstructorUtils.getMatchingAccessibleConstructor(cls, parameterTypes)
      // final Object bean = klazz.newInstance();
      // StaffAuthorityPrivilege bean = new StaffAuthorityPrivilege("type", "", "");
      // @SuppressWarnings("rawtypes")
      // final Map desc = BeanUtils.describe(bean);
      // System.out.println(desc);

    } catch (Throwable e) {
      // Heck, it's a *main* method in a *utility class*. Swallow the exception. :-)
      e.printStackTrace();
    }
  }

}

