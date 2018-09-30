package com.byedbl.buffer;

import java.nio.ByteBuffer;

public class TestBuffer {

    /**
     * <pre>
     * capacity: 容量,最大元素个数
     * position: 位置,当前 数据 操作开始的地方
     * limit:  限制,第一个不能 读写的元素
     * mark : 标记当前的position的位置
     *
     * -1<= mark <= position <= limit <= capacity
     * 0<= position <= limit <= capacity
     * 默认 limit = capacity,mark = undefined,position=0
     * clear : p = 0,l = c ,mark = undefined
     * flip : l = p, p=0,mark = undefined
     * rewind : p = 0,mark = undefined
     * remaining 剩余: = limit - position
     * reset  : p = mark
     * slice :  切片,从一个 缓冲区到另一个 缓冲区的 起始位置
     * compact : remaining 移到最前面
     *
     *</pre>
     *
     *
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
