import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by covers1624 on 2/12/2017.
 */
public class Day2Wrapper {

    public static void main(String[] args) throws Throwable {
        ASMClassLoader loader = new ASMClassLoader(Day2Wrapper.class.getClassLoader());
        Class<?> clazz = Class.forName("Day2Stub", true, loader);
        clazz.getDeclaredMethod("run").invoke(null);
    }

    public static void inject(MethodVisitor v) {
        v.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        v.visitLdcInsn("WHEEEE, Raw Bytecode!! (post me) NEVER FUCKING AGAIN");
        v.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

        v.visitIntInsn(BIPUSH, 16);
        v.visitIntInsn(BIPUSH, 16);
        v.visitMultiANewArrayInsn("[[I", 2);
        v.visitVarInsn(ASTORE, 0);//new int[16][16]

        v.visitTypeInsn(NEW, "java/io/BufferedReader");
        v.visitInsn(DUP);
        v.visitTypeInsn(NEW, "java/io/InputStreamReader");
        v.visitInsn(DUP);
        v.visitLdcInsn("Day2Stub");
        v.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        v.visitLdcInsn("/day2.txt");
        v.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getResourceAsStream", "(Ljava/lang/String;)Ljava/io/InputStream;");
        v.visitMethodInsn(INVOKESPECIAL, "java/io/InputStreamReader", "<init>", "(Ljava/io/InputStream;)V");
        v.visitMethodInsn(INVOKESPECIAL, "java/io/BufferedReader", "<init>", "(Ljava/io/Reader;)V");
        v.visitVarInsn(ASTORE, 1);//Create our buffered reader.

        v.visitInsn(ICONST_0);
        v.visitVarInsn(ISTORE, 2);

        Label l_while = new Label();
        Label l_break = new Label();
        {
            v.visitLabel(l_while);
            v.visitFrame(F_APPEND, 3, new Object[] { "[[I", INTEGER, "java/io/BufferedReader" }, 0, null);
            v.visitVarInsn(ALOAD, 1);
            v.visitMethodInsn(INVOKEVIRTUAL, "java/io/BufferedReader", "readLine", "()Ljava/lang/String;");
            v.visitInsn(DUP);
            v.visitVarInsn(ASTORE, 3);
            v.visitJumpInsn(IFNULL, l_break);

            v.visitVarInsn(ALOAD, 3);
            v.visitLdcInsn("\\t");
            v.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "split", "(Ljava/lang/String;)[Ljava/lang/String;");
            v.visitVarInsn(ASTORE, 4);
            v.visitInsn(ICONST_0);
            v.visitVarInsn(ISTORE, 5);

            Label l_pWhile = new Label();
            Label l_pBreak = new Label();
            {
                v.visitLabel(l_pWhile);
                v.visitFrame(F_APPEND, 3, new Object[] { "java/lang/String", "[Ljava/lang/String;", INTEGER }, 0, null);
                v.visitVarInsn(ILOAD, 5);
                v.visitIntInsn(BIPUSH, 16);
                v.visitJumpInsn(IF_ICMPGE, l_pBreak);

                v.visitVarInsn(ALOAD, 0);
                v.visitVarInsn(ILOAD, 2);
                v.visitInsn(AALOAD);
                v.visitVarInsn(ILOAD, 5);
                v.visitVarInsn(ALOAD, 4);
                v.visitVarInsn(ILOAD, 5);
                v.visitInsn(AALOAD);
                v.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I");
                v.visitInsn(IASTORE);

                v.visitIincInsn(5, 1);
                v.visitJumpInsn(GOTO, l_pWhile);
            }
            v.visitLabel(l_pBreak);
            v.visitFrame(F_CHOP, 2, null, 0, null);

            v.visitIincInsn(2, 1);
            v.visitJumpInsn(GOTO, l_while);
        }
        v.visitLabel(l_break);
        v.visitFrame(F_SAME, 0, null, 0, null);

        v.visitInsn(ICONST_0);
        v.visitVarInsn(ISTORE, 2);//checksum

        v.visitInsn(ICONST_0);
        v.visitVarInsn(ISTORE, 3);//for i
        {//Part 1
            Label l_Fori = new Label();
            Label l_Breaki = new Label();
            {
                v.visitLabel(l_Fori);
                v.visitFrame(F_APPEND, 2, new Object[] { INTEGER, INTEGER }, 0, null);
                v.visitVarInsn(ILOAD, 3);
                v.visitIntInsn(BIPUSH, 16);
                v.visitJumpInsn(IF_ICMPGE, l_Breaki);

                v.visitLdcInsn(Integer.MAX_VALUE);
                v.visitVarInsn(ISTORE, 4);//min
                v.visitLdcInsn(Integer.MIN_VALUE);
                v.visitVarInsn(ISTORE, 5);//max

                v.visitInsn(ICONST_0);
                v.visitVarInsn(ISTORE, 6);//for j

                Label l_Forj = new Label();
                Label l_Breakj = new Label();
                {
                    v.visitLabel(l_Forj);
                    v.visitFrame(F_APPEND, 3, new Object[] { INTEGER, INTEGER, INTEGER }, 0, null);
                    v.visitVarInsn(ILOAD, 6);
                    v.visitIntInsn(BIPUSH, 16);
                    v.visitJumpInsn(IF_ICMPGE, l_Breakj);

                    v.visitVarInsn(ALOAD, 0);
                    v.visitVarInsn(ILOAD, 3);
                    v.visitInsn(AALOAD);
                    v.visitVarInsn(ILOAD, 6);
                    v.visitInsn(IALOAD);
                    v.visitVarInsn(ISTORE, 7);

                    Label l_if1 = new Label();
                    v.visitVarInsn(ILOAD, 7);
                    v.visitVarInsn(ILOAD, 4);
                    v.visitJumpInsn(IF_ICMPGE, l_if1);
                    v.visitVarInsn(ILOAD, 7);
                    v.visitVarInsn(ISTORE, 4);
                    v.visitLabel(l_if1);

                    Label l_if2 = new Label();
                    v.visitVarInsn(ILOAD, 7);
                    v.visitVarInsn(ILOAD, 5);
                    v.visitJumpInsn(IF_ICMPLE, l_if2);
                    v.visitVarInsn(ILOAD, 7);
                    v.visitVarInsn(ISTORE, 5);
                    v.visitLabel(l_if2);

                    v.visitIincInsn(6, 1);
                    v.visitJumpInsn(GOTO, l_Forj);
                }
                v.visitLabel(l_Breakj);
                v.visitFrame(F_CHOP, 3, null, 0, null);

                v.visitVarInsn(ILOAD, 2);
                v.visitVarInsn(ILOAD, 5);
                v.visitVarInsn(ILOAD, 4);
                v.visitInsn(ISUB);
                v.visitInsn(IADD);
                v.visitVarInsn(ISTORE, 2);

                v.visitIincInsn(3, 1);
                v.visitJumpInsn(GOTO, l_Fori);
            }
            v.visitLabel(l_Breaki);
            v.visitFrame(F_CHOP, 2, null, 0, null);

            v.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            v.visitVarInsn(ILOAD, 2);
            v.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "toString", "(I)Ljava/lang/String;");
            v.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        v.visitInsn(ICONST_0);
        v.visitVarInsn(ISTORE, 2);

        v.visitInsn(ICONST_0);
        v.visitVarInsn(ISTORE, 3);

        {//Part 2
            Label l_Fori = new Label();
            Label l_Breaki = new Label();
            {
                v.visitLabel(l_Fori);
                v.visitFrame(F_APPEND, 2, new Object[] { INTEGER, INTEGER }, 0, null);
                v.visitVarInsn(ILOAD, 3);
                v.visitIntInsn(BIPUSH, 16);
                v.visitJumpInsn(IF_ICMPGE, l_Breaki);

                v.visitInsn(ICONST_0);
                v.visitVarInsn(ISTORE, 4);//for j

                Label l_Forj = new Label();
                Label l_Breakj = new Label();
                {
                    v.visitLabel(l_Forj);
                    v.visitFrame(F_APPEND, 1, new Object[] { INTEGER }, 0, null);
                    v.visitVarInsn(ILOAD, 4);
                    v.visitIntInsn(BIPUSH, 16);
                    v.visitJumpInsn(IF_ICMPGE, l_Breakj);

                    v.visitVarInsn(ALOAD, 0);
                    v.visitVarInsn(ILOAD, 3);
                    v.visitInsn(AALOAD);
                    v.visitVarInsn(ILOAD, 4);
                    v.visitInsn(IALOAD);
                    v.visitVarInsn(ISTORE, 5);//Num1

                    v.visitInsn(ICONST_0);
                    v.visitVarInsn(ISTORE, 6);//for k

                    Label l_Fork = new Label();
                    Label l_Breakk = new Label();
                    {
                        v.visitLabel(l_Fork);
                        v.visitFrame(F_APPEND, 2, new Object[] { INTEGER, INTEGER }, 0, null);
                        v.visitVarInsn(ILOAD, 6);
                        v.visitIntInsn(BIPUSH, 16);
                        v.visitJumpInsn(IF_ICMPGE, l_Breakk);

                        v.visitVarInsn(ALOAD, 0);
                        v.visitVarInsn(ILOAD, 3);
                        v.visitInsn(AALOAD);
                        v.visitVarInsn(ILOAD, 6);
                        v.visitInsn(IALOAD);
                        v.visitVarInsn(ISTORE, 7);//Num2

                        Label l_if1 = new Label();
                        v.visitVarInsn(ILOAD, 5);
                        v.visitVarInsn(ILOAD, 7);
                        v.visitJumpInsn(IF_ICMPEQ, l_if1);
                        Label l_if2 = new Label();
                        v.visitVarInsn(ILOAD, 5);
                        v.visitInsn(I2F);
                        v.visitVarInsn(ILOAD, 7);
                        v.visitInsn(I2F);
                        v.visitInsn(FDIV);
                        v.visitInsn(DUP);
                        v.visitVarInsn(FSTORE, 8);
                        v.visitInsn(F2I);
                        v.visitVarInsn(ISTORE, 9);

                        v.visitVarInsn(FLOAD, 8);
                        v.visitVarInsn(ILOAD, 9);
                        v.visitInsn(I2F);
                        v.visitInsn(FCMPL);
                        v.visitJumpInsn(IFNE, l_if2);
                        v.visitVarInsn(ILOAD, 2);
                        v.visitVarInsn(ILOAD, 9);
                        v.visitInsn(IADD);
                        v.visitVarInsn(ISTORE, 2);

                        v.visitLabel(l_if2);
                        v.visitLabel(l_if1);

                        v.visitIincInsn(6, 1);
                        v.visitJumpInsn(GOTO, l_Fork);
                    }
                    v.visitLabel(l_Breakk);
                    v.visitFrame(F_CHOP, 2, null, 0, null);

                    v.visitIincInsn(4, 1);
                    v.visitJumpInsn(GOTO, l_Forj);
                }
                v.visitLabel(l_Breakj);
                v.visitFrame(F_CHOP, 1, null, 0, null);

                v.visitIincInsn(3, 1);
                v.visitJumpInsn(GOTO, l_Fori);
            }
            v.visitLabel(l_Breaki);
            v.visitFrame(F_CHOP, 2, null, 0, null);

            v.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            v.visitVarInsn(ILOAD, 2);
            v.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "toString", "(I)Ljava/lang/String;");
            v.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        v.visitInsn(RETURN);
    }

    public static class ASMClassLoader extends ClassLoader {

        public ClassLoader delegate;

        public ASMClassLoader(ClassLoader delegate) {
            super(null);
            this.delegate = delegate;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals("Day2Stub")) {
                try {
                    byte[] pre_bytes = IOUtils.toByteArray(delegate.getResourceAsStream(name + ".class"));
                    ClassReader reader = new ClassReader(pre_bytes);
                    ClassNode node = new ClassNode();
                    reader.accept(node, ClassReader.SKIP_FRAMES);

                    MethodNode m = node.methods.stream().filter(a -> a.name.equals("run")).findFirst().get();
                    m.instructions.clear();
                    inject(m);
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                    node.accept(writer);
                    byte[] post_bytes = writer.toByteArray();
                    if (false) {
                        File file = new File(node.name.replace('/', '#') + ".class");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(post_bytes);
                        fos.flush();
                        fos.close();
                    }
                    return defineClass(name, post_bytes, 0, post_bytes.length);

                } catch (IOException e) {
                    throw new RuntimeException("Unable to transform class.", e);
                }
            }
            return delegate.loadClass(name);
        }

        @Override
        public InputStream getResourceAsStream(String name) {
            return delegate.getResourceAsStream(name);
        }

        @Override
        public Enumeration<URL> getResources(String name) throws IOException {
            return delegate.getResources(name);
        }
    }

}
