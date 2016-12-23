package gov.ca.cwds.rest.util.jni;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;


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


  private static final class ClassStorage {
    Class<?> klazz;

    Map<String, String> classAnnotations;
    Map<Class<?>, Map<String, String>> interfaceAnnotations;
    Map<Constructor<?>, Map<String, String>> ctorAnnotations;
    Map<Field, Map<String, String>> fieldAnnotations;
    Map<Method, Map<String, String>> methodAnnotations;

    private ClassStorage() {}
  }

  private JUnitGenerator() {}

  /**
   * Split annotation settings String into key/value pairs.
   * 
   * @param s annotation String
   * @return map of annotation key/value pairs
   */
  protected Map<String, String> splitAnnotationSettings(String s) {
    Map<String, String> ret = null;

    final String[] inners = s.split("[()]");

    if (inners != null && inners.length > 1) {
      final String[] settings = inners[1].trim().split(",");
      ret = new HashMap<String, String>();

      for (String pair : settings) {
        final String[] tokens = pair.split("[=]");
        if (tokens.length > 1) {
          ret.put(tokens[0].trim(), tokens[1].trim());
        }
      }
    }

    return ret;
  }

  protected void drillAnnotation(Annotation ann) {
    final String s = ann.toString();
    System.out.println("\t\tAnnotation: " + s);
    final Map<String, String> m = splitAnnotationSettings(s);
    System.out.println("\t\t\tSettings: " + m);
  }

  protected void drillAnnotations(String msg, Annotation[] anns) {
    if (anns != null && anns.length > 0) {
      drillAnnotations(msg, anns, 1);
    }
  }

  protected void drillAnnotations(String msg, Annotation[] anns, int indent) {
    if (anns != null && anns.length > 0) {
      // System.out.println(StringUtils.repeat('\t', indent) + "Annotations: " + msg + ":");
      for (Annotation ann : anns) {
        drillAnnotation(ann);
      }
    }
  }

  protected void drillClass(Class<?> klazz) throws Exception {
    System.out.println(CLASS_SPACER + "CLASS:\n" + klazz.getName() + ":" + CLASS_SPACER);

    // INTERFACES:
    final Class<?>[] intrfcs = klazz.getInterfaces();
    if (intrfcs != null && intrfcs.length > 0) {
      System.out.println(SPACER + "INTERFACES:\n" + klazz.getName() + ":" + SPACER);
      for (Class<?> intrfce : intrfcs) {
        System.out.println(intrfce);
        drillAnnotations("Interface Declared", intrfce.getDeclaredAnnotations());
      }
    }

    // CLASS ANNOTATIONS:
    System.out.println(SPACER + "CLASS ANNOTATIONS:" + SPACER);
    drillAnnotations("Class Declared", klazz.getDeclaredAnnotations());

    // CONSTRUCTORS:
    System.out.println(SPACER + "CONSTRUCTORS:" + SPACER);
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
    System.out.println(SPACER + "FIELDS:" + SPACER);
    for (Field f : klazz.getDeclaredFields()) {
      if (!f.getName().contains(".serialVersionUID")) {
        System.out.println(f);
        drillAnnotations("Field Declared", f.getDeclaredAnnotations());
      }
    }

    // METHODS:
    System.out.println(SPACER + "METHODS:" + SPACER);
    for (Method m : klazz.getDeclaredMethods()) {
      System.out.println(m);
      drillAnnotations("Method Declared", m.getDeclaredAnnotations());
      final Class<?>[] params = m.getParameterTypes();
      if (params != null && params.length > 0) {
        int counter = 0;
        for (Class<?> p : params) {
          ++counter;
          System.out.println("\tparam " + counter + ": " + p);
          drillAnnotations("", p.getDeclaredAnnotations(), 2);
        }
      }
    }

    // PARENT CLASS:
    final Class<?> superKlazz = klazz.getSuperclass();
    if (superKlazz != null && !"java.lang.Object".equals(superKlazz.getName())) {
      System.out.println(SPACER + "PARENT CLASS:\n" + klazz.getSuperclass() + " :" + SPACER);
      drillClass(superKlazz);
    }
  }

  protected void drillClass(String className) throws Exception {
    drillClass(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
  }

  public static class YourClassVisitor extends ClassVisitor {

    public YourClassVisitor(int api, ClassVisitor cv) {
      super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
        String[] exceptions) {
      MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
      Printer p = new Textifier(Opcodes.ASM5) {
        @Override
        public void visitMethodEnd() {
          print(new PrintWriter(System.out)); // print it after it has been visited
        }
      };
      return new TraceMethodVisitor(mv, p);
    }
  }

  /**
   * @param args command line
   */
  public static void main(String[] args) {

    try {
      final String className = args[0];
      JUnitGenerator generator = new JUnitGenerator();
      generator.drillClass(className);

      final byte[] classBytes =
          IOUtils.toByteArray(new FileInputStream("bin/" + className.replace('.', '/') + ".class"));

      // Setup for asm ClassReader, ClassWriter and your implementation of the ClassVisitor(e.g.:
      // YourClassVisitor)
      final ClassReader classReader = new ClassReader(classBytes);
      PrintWriter printWriter = new PrintWriter(System.out);
      TraceClassVisitor traceClassVisitor = new TraceClassVisitor(printWriter);
      classReader.accept(traceClassVisitor, ClassReader.SKIP_DEBUG);

      // {
      // ClassReader cr = new ClassReader(classBytes);
      // ClassNode cn = new ClassNode();
      // cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.EXPAND_FRAMES);
      // List<MethodNode> methods = cn.methods;
      //
      // for (MethodNode method : methods) {
      // System.out.println(method.name + " " + method.desc);
      //
      // if (method.instructions.size() > 0) {
      // Analyzer a = new Analyzer(new BasicInterpreter());
      // a.analyze(cn.name, method);
      // Frame[] frames = a.getFrames();
      //
      // if (frames.length > 0) {
      // for (Frame f : frames) {
      // System.out.println(f);
      //
      // // for (int i = 0; i < f.getLocals(); i++) {
      // // f.getLocal(i);
      // // }
      //
      // }
      // }
      //
      // // Elements of the frames array now contains info for each instruction
      // // from the analyzed method. BasicInterpreter creates BasicValue, that
      // // is using simplified type system that distinguishes the UNINITIALZED,
      // // INT, FLOAT, LONG, DOUBLE, REFERENCE and RETURNADDRESS types.
      // }
      // }
      // }

    } catch (Throwable e) {
      // Heck, it's a *main* method ... in a *utility class*.
      // Just swallow the exception. :-)
      e.printStackTrace();
    }
  }

}

