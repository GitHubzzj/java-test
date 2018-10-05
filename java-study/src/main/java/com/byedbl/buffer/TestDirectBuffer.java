package com.byedbl.buffer;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TestDirectBuffer {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024  *  1024 * 1024);
        // -1
        System.out.println("mark:" + buffer.mark());
        //0
        System.out.println("pos:" + buffer.position());
        //10
        System.out.println("lim:" + buffer.limit());
        //10
        System.out.println("cap:" + buffer.capacity());
        //10
        System.out.println("remaining:" + buffer.remaining());

        //写入数据
        buffer.put((byte)1);
        buffer.put((byte)2);
        buffer.put((byte)3);
        buffer.put((byte)4);
        System.out.println("========================写入数据================");
        //4
        System.out.println("pos:" + buffer.position());
        //10
        System.out.println("lim:" + buffer.limit());
        //10
        System.out.println("cap:" + buffer.capacity());
        //6
        System.out.println("remaining:" + buffer.remaining());
        System.out.println("========================读取数据================");
        //读取,get也会将position的值加1
//        System.out.println(buffer.get());

        buffer.flip(); //翻板
        //0
        System.out.println("pos:" + buffer.position());
        //4
        System.out.println("lim:" + buffer.limit());
        //10
        System.out.println("cap:" + buffer.capacity());
        //4
        System.out.println("remaining:" + buffer.remaining());

        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
//        System.out.println(buffer.get());

        clearDirectBuffer(buffer);
    }

    private static void clearDirectBuffer(ByteBuffer buffer) throws Exception {
        Class clazz = Class.forName("java.nio.DirectByteBuffer");
        Method m = clazz.getDeclaredMethod("cleaner");
        m.setAccessible(true);
        Object cleaner = m.invoke(buffer);

        clazz = Class.forName("sun.misc.Cleaner");
        Method clean = clazz.getDeclaredMethod("clean");
        clean.setAccessible(true);
        clean.invoke(cleaner);
    }
}
