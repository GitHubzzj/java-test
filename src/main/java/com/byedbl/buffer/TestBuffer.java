package com.byedbl.buffer;

import java.nio.ByteBuffer;

public class TestBuffer {

    /**
     * <p>
     *     byte ,1个字节 [-128,127]
     *     short,2个字节 [-32768,32767]
     *     int ,4个字节 [-2,147,483,648,2,147,485,647]
     *     long, 8个字节 [-9,223,372,036,854,775,808（-2^63）,9,223,372,036,854,775,807（2^63 -1）]
     *     float 4个字节
     *     double
     *     boolean
     *     char 2 个字节
     * </p>
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
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
        System.out.println(buffer.get());
    }
}
