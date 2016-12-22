package gov.ca.cwds.rest.util.jni;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Generate JUnit test cases for target class.
 * 
 * @author CWDS API Team
 */
public class JUnitGenerator {

  private static final String SPACER =
      "\n----------------------------------------------------------\n";
  private static final String CLASS_SPACER =
      "\n==========================================================\n";

  /**
   * Split annotation settings String into key/value pairs.
   * 
   * @param s annotation String
   * @return map of annotation key/value pairs
   */
  protected static Map<String, String> splitAnnotationSettings(String s) {
    // final String[] inners = s.replace('(', '~').replace(')', '~').split("~");
    final String[] inners = s.split("[()]");
    final String[] settings = inners[1].trim().split(",");
    Map<String, String> ret = new HashMap<String, String>();

    for (String pair : settings) {
      final String[] tokens = pair.split("[=]");
      if (tokens.length > 1) {
        ret.put(tokens[0].trim(), tokens[1].trim());
      }
    }

    return ret;
  }

  protected static void drillAnnotation(Annotation ann) {
    final String s = ann.toString();
    System.out.println("\t\tAnnotation: " + s);
    final Map<String, String> m = splitAnnotationSettings(s);
    System.out.println("\t\t\tSettings: " + m);
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

  protected static void drillClass(Class<?> klazz) throws Exception {
    System.out.println(CLASS_SPACER + "CLASS:\n" + klazz.getName() + ":" + CLASS_SPACER);

    final Class<?>[] intrfcs = klazz.getInterfaces();
    if (intrfcs != null && intrfcs.length > 0) {
      System.out.println(SPACER + "INTERFACES:\n" + klazz.getName() + ":" + SPACER);
      for (Class<?> intrfce : intrfcs) {
        System.out.println(intrfce);
      }
    }

    // Class level annotations:
    System.out.println(SPACER + "CLASS DECLARED ANNOTATIONS:" + SPACER);
    drillAnnotations("Class Declared", klazz.getDeclaredAnnotations());

    // CONSTRUCTORS:
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

    // FIELDS:
    System.out.println(SPACER + "DECLARED FIELDS:" + SPACER);
    for (Field f : klazz.getDeclaredFields()) {
      if (!f.getName().contains(".serialVersionUID")) {
        System.out.println(f);
        drillAnnotations("Field Declared", f.getDeclaredAnnotations());
      }
    }

    // METHODS:
    System.out.println(SPACER + "DECLARED METHODS:" + SPACER);
    for (Method m : klazz.getDeclaredMethods()) {
      System.out.println(m);
      drillAnnotations("Method Declared", m.getDeclaredAnnotations());
    }

    // PARENT CLASS:
    final Class<?> superKlazz = klazz.getSuperclass();
    if (superKlazz != null && !"java.lang.Object".equals(superKlazz.getName())) {
      System.out.println(SPACER + "PARENT CLASS:\n" + klazz.getSuperclass() + " :" + SPACER);
      drillClass(superKlazz);
    }
  }

  protected static void drillClass(String className) throws Exception {
    drillClass(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
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
      // Heck, it's a *main* method ... in a *utility class*. Swallow the exception. :-)
      e.printStackTrace();
    }
  }

}

