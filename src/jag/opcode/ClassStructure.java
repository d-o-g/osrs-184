package jag.opcode;

import jag.commons.collection.LinkedList;
import jag.commons.collection.Node;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassStructure extends Node {

    public static LinkedList<ClassStructure> list = new LinkedList<>();

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

        for (int i = 0; i < struc.elementCount; ++i) {
            try {
                int var4 = buffer.g1();
                String var5;
                String var6;
                int var7;
                if (var4 != 0 && var4 != 1 && var4 != 2) {
                    if (var4 == 3 || var4 == 4) {
                        var5 = buffer.gstr();
                        var6 = buffer.gstr();
                        var7 = buffer.g1();
                        String[] var8 = new String[var7];

                        for (int var9 = 0; var9 < var7; ++var9) {
                            var8[var9] = buffer.gstr();
                        }

                        String var10 = buffer.gstr();
                        byte[][] var11 = new byte[var7][];
                        int var13;
                        if (var4 == 3) {
                            for (int var12 = 0; var12 < var7; ++var12) {
                                var13 = buffer.g4();
                                var11[var12] = new byte[var13];
                                buffer.gdata(var11[var12], 0, var13);
                            }
                        }

                        struc.types[i] = var4;
                        Class[] var14 = new Class[var7];

                        for (var13 = 0; var13 < var7; ++var13) {
                            var14[var13] = forName(var8[var13]);
                        }

                        Class var15 = forName(var10);
                        if (forName(var5).getClassLoader() == null) {
                            throw new SecurityException();
                        }

                        for (Method var19 : forName(var5).getDeclaredMethods()) {
                            if (var19.getName().equals(var6)) {
                                Class[] var20 = var19.getParameterTypes();
                                if (var14.length == var20.length) {
                                    boolean var21 = true;

                                    for (int var22 = 0; var22 < var14.length; ++var22) {
                                        if (var20[var22] != var14[var22]) {
                                            var21 = false;
                                            break;
                                        }
                                    }

                                    if (var21 && var15 == var19.getReturnType()) {
                                        struc.methods[i] = var19;
                                    }
                                }
                            }
                        }

                        struc.methodArgs[i] = var11;
                    }
                } else {
                    var5 = buffer.gstr();
                    var6 = buffer.gstr();
                    var7 = 0;
                    if (var4 == 1) {
                        var7 = buffer.g4();
                    }

                    struc.types[i] = var4;
                    struc.fieldIntValues[i] = var7;
                    if (forName(var5).getClassLoader() == null) {
                        throw new SecurityException();
                    }

                    struc.fields[i] = forName(var5).getDeclaredField(var6);
                }
            } catch (ClassNotFoundException e) {
                struc.errors[i] = -1;
            } catch (SecurityException e) {
                struc.errors[i] = -2;
            } catch (NullPointerException e) {
                struc.errors[i] = -3;
            } catch (Exception e) {
                struc.errors[i] = -4;
            } catch (Throwable e) {
                struc.errors[i] = -5;
            }
        }

        list.pushBack(struc);
    }

    public static void process(BitBuffer buffer) {
        ClassStructure cls = list.head();
        if (cls != null) {
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
    }

    public static Class forName(String var0) throws ClassNotFoundException {
        if (var0.equals("B")) {
            return Byte.TYPE;
        }
        if (var0.equals("I")) {
            return Integer.TYPE;
        }
        if (var0.equals("S")) {
            return Short.TYPE;
        }
        if (var0.equals("J")) {
            return Long.TYPE;
        }
        if (var0.equals("Z")) {
            return Boolean.TYPE;
        }
        if (var0.equals("F")) {
            return Float.TYPE;
        }
        if (var0.equals("D")) {
            return Double.TYPE;
        }
        if (var0.equals("C")) {
            return Character.TYPE;
        }
        return var0.equals("void") ? Void.TYPE : Class.forName(var0);
    }
}
