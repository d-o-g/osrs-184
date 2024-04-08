package jagex.messaging;

import jagex.datastructure.instrusive.linklist.LinkedList;
import jagex.datastructure.Node;

import java.io.*;
import java.lang.reflect.*;

public class ClassStructure extends Node {

  public static LinkedList<ClassStructure> pending = new LinkedList<>();

  public int size;
  public int elementCount;

  public int[] errors;
  public int[] types;
  public int[] fieldIntValues;

  public byte[][][] methodArgs;

  public Field[] fields;
  public Method[] methods;

  public ClassStructure() {

  }

  public static void decode(Buffer buffer) {
    ClassStructure struc = new ClassStructure();
    struc.elementCount = buffer.g1();
    struc.size = buffer.g4();
    struc.types = new int[struc.elementCount];
    struc.errors = new int[struc.elementCount];
    struc.fields = new Field[struc.elementCount];
    struc.fieldIntValues = new int[struc.elementCount];
    struc.methods = new Method[struc.elementCount];
    struc.methodArgs = new byte[struc.elementCount][][];

    for (int elementIndex = 0; elementIndex < struc.elementCount; ++elementIndex) {
      try {
        int type = buffer.g1();
        if (type == 0 || type == 1 || type == 2) {
          String className = buffer.gstr();
          String fieldName = buffer.gstr();
          int constantFieldValue = 0;
          if (type == 1) {
            constantFieldValue = buffer.g4();
          }

          struc.types[elementIndex] = type;
          struc.fieldIntValues[elementIndex] = constantFieldValue;
          if (getClass(className).getClassLoader() == null) {
            throw new SecurityException();
          }

          struc.fields[elementIndex] = getClass(className).getDeclaredField(fieldName);
        } else if (type == 3 || type == 4) {
          String className = buffer.gstr();
          String methodName = buffer.gstr();
          int argumentCount = buffer.g1();
          String[] argumentTypeClassNames = new String[argumentCount];

          for (int argumentIndex = 0; argumentIndex < argumentCount; ++argumentIndex) {
            argumentTypeClassNames[argumentIndex] = buffer.gstr();
          }

          String returnTypeClassName = buffer.gstr();
          byte[][] argumentValues = new byte[argumentCount][];
          if (type == 3) {
            for (int argument = 0; argument < argumentCount; ++argument) {
              int len = buffer.g4();
              argumentValues[argument] = new byte[len];
              buffer.gdata(argumentValues[argument], 0, len);
            }
          }

          struc.types[elementIndex] = type;

          Class<?>[] expectedArgumentTypes = new Class[argumentCount];
          for (int argumentIndex = 0; argumentIndex < argumentCount; ++argumentIndex) {
            expectedArgumentTypes[argumentIndex] = getClass(argumentTypeClassNames[argumentIndex]);
          }

          Class<?> returnType = getClass(returnTypeClassName);
          if (getClass(className).getClassLoader() == null) {
            throw new SecurityException();
          }

          for (Method method : getClass(className).getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
              Class<?>[] argumentTypes = method.getParameterTypes();
              if (argumentTypes.length == expectedArgumentTypes.length) {
                boolean valid = true;

                for (int expectedArgumentIndex = 0; expectedArgumentIndex < expectedArgumentTypes.length; ++expectedArgumentIndex) {
                  if (argumentTypes[expectedArgumentIndex] != expectedArgumentTypes[expectedArgumentIndex]) {
                    valid = false;
                    break;
                  }
                }

                if (valid && returnType == method.getReturnType()) {
                  struc.methods[elementIndex] = method;
                }
              }
            }
          }

          struc.methodArgs[elementIndex] = argumentValues;
        }
      } catch (ClassNotFoundException e) {
        struc.errors[elementIndex] = -1;
      } catch (SecurityException e) {
        struc.errors[elementIndex] = -2;
      } catch (NullPointerException e) {
        struc.errors[elementIndex] = -3;
      } catch (Exception e) {
        struc.errors[elementIndex] = -4;
      } catch (Throwable e) {
        struc.errors[elementIndex] = -5;
      }
    }

    pending.pushBack(struc);
  }

  public static void process(BitBuffer buffer) {
    ClassStructure cls = pending.head();
    if (cls == null) {
      return;
    }

    int offset = buffer.pos;
    buffer.p4(cls.size);

    for (int i = 0; i < cls.elementCount; ++i) {
      if (cls.errors[i] != 0) {
        buffer.p1(cls.errors[i]);
      } else {
        try {
          int type = cls.types[i];
          if (type == 0) {
            Field field = cls.fields[i];
            int value = field.getInt(null);
            buffer.p1(0);
            buffer.p4(value);
          } else if (type == 1) {
            Field field = cls.fields[i];
            field.setInt(null, cls.fieldIntValues[i]);
            buffer.p1(0);
          } else if (type == 2) {
            Field field = cls.fields[i];
            int access = field.getModifiers();
            buffer.p1(0);
            buffer.p4(access);
          }

          if (type == 3) {
            Method method = cls.methods[i];
            byte[][] args = cls.methodArgs[i];
            Object[] params = new Object[args.length];

            for (int j = 0; j < args.length; ++j) {
              ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(args[j]));
              params[j] = stream.readObject();
            }

            Object returned = method.invoke(null, params);
            if (returned == null) {
              buffer.p1(0);
            } else if (returned instanceof Number) {
              buffer.p1(1);
              buffer.p8(((Number) returned).longValue());
            } else if (returned instanceof String) {
              buffer.p1(2);
              buffer.pcstr((String) returned);
            } else {
              buffer.p1(4);
            }
          } else if (type == 4) {
            Method method = cls.methods[i];
            int access = method.getModifiers();
            buffer.p1(0);
            buffer.p4(access);
          }
        } catch (ClassNotFoundException e) {
          buffer.p1(-10);
        } catch (InvalidClassException e) {
          buffer.p1(-11);
        } catch (StreamCorruptedException e) {
          buffer.p1(-12);
        } catch (OptionalDataException e) {
          buffer.p1(-13);
        } catch (IllegalAccessException e) {
          buffer.p1(-14);
        } catch (IllegalArgumentException e) {
          buffer.p1(-15);
        } catch (InvocationTargetException e) {
          buffer.p1(-16);
        } catch (SecurityException e) {
          buffer.p1(-17);
        } catch (IOException e) {
          buffer.p1(-18);
        } catch (NullPointerException e) {
          buffer.p1(-19);
        } catch (Exception e) {
          buffer.p1(-20);
        } catch (Throwable e) {
          buffer.p1(-21);
        }
      }
    }

    buffer.pCrc(offset);
    cls.unlink();
  }

  public static Class<?> getClass(String descriptor) throws ClassNotFoundException {
    switch (descriptor) {
      case "B":
        return Byte.TYPE;
      case "I":
        return Integer.TYPE;
      case "S":
        return Short.TYPE;
      case "J":
        return Long.TYPE;
      case "Z":
        return Boolean.TYPE;
      case "F":
        return Float.TYPE;
      case "D":
        return Double.TYPE;
      case "C":
        return Character.TYPE;
      default: {
        return descriptor.equals("void") ? Void.TYPE : Class.forName(descriptor);
      }
    }
  }
}
