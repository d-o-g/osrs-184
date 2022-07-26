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

    public static void process(BitBuffer var0) {
        ClassStructure var2 = list.head();
        if (var2 != null) {
            int var3 = var0.pos;
            var0.p4(var2.size);

            for (int var4 = 0; var4 < var2.elementCount; ++var4) {
                if (var2.errors[var4] != 0) {
                    var0.p1(var2.errors[var4]);
                } else {
                    try {
                        int var5 = var2.types[var4];
                        Field var6;
                        int var7;
                        if (var5 == 0) {
                            var6 = var2.fields[var4];
                            var7 = var6.getInt(null);
                            var0.p1(0);
                            var0.p4(var7);
                        } else if (var5 == 1) {
                            var6 = var2.fields[var4];
                            var6.setInt(null, var2.fieldIntValues[var4]);
                            var0.p1(0);
                        } else if (var5 == 2) {
                            var6 = var2.fields[var4];
                            var7 = var6.getModifiers();
                            var0.p1(0);
                            var0.p4(var7);
                        }

                        Method var26;
                        if (var5 != 3) {
                            if (var5 == 4) {
                                var26 = var2.methods[var4];
                                var7 = var26.getModifiers();
                                var0.p1(0);
                                var0.p4(var7);
                            }
                        } else {
                            var26 = var2.methods[var4];
                            byte[][] var8 = var2.methodArgs[var4];
                            Object[] var9 = new Object[var8.length];

                            for (int var10 = 0; var10 < var8.length; ++var10) {
                                ObjectInputStream var11 = new ObjectInputStream(new ByteArrayInputStream(var8[var10]));
                                var9[var10] = var11.readObject();
                            }

                            Object var12 = var26.invoke(null, var9);
                            if (var12 == null) {
                                var0.p1(0);
                            } else if (var12 instanceof Number) {
                                var0.p1(1);
                                var0.p8(((Number) var12).longValue());
                            } else if (var12 instanceof String) {
                                var0.p1(2);
                                var0.pcstr((String) var12);
                            } else {
                                var0.p1(4);
                            }
                        }
                    } catch (ClassNotFoundException var14) {
                        var0.p1(-10);
                    } catch (InvalidClassException var15) {
                        var0.p1(-11);
                    } catch (StreamCorruptedException var16) {
                        var0.p1(-12);
                    } catch (OptionalDataException var17) {
                        var0.p1(-13);
                    } catch (IllegalAccessException var18) {
                        var0.p1(-14);
                    } catch (IllegalArgumentException var19) {
                        var0.p1(-15);
                    } catch (InvocationTargetException var20) {
                        var0.p1(-16);
                    } catch (SecurityException var21) {
                        var0.p1(-17);
                    } catch (IOException var22) {
                        var0.p1(-18);
                    } catch (NullPointerException var23) {
                        var0.p1(-19);
                    } catch (Exception var24) {
                        var0.p1(-20);
                    } catch (Throwable var25) {
                        var0.p1(-21);
                    }
                }
            }

            var0.pcrc(var3);
            var2.unlink();
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
