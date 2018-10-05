package com.byedbl.buffer;

import java.nio.ByteBuffer;

public class TestByteBufferInt {
    public static void main(String[] args) {
        ByteBuffer bBuf = ByteBuffer.allocate(512);
        //28个字节
        bBuf.putInt(1);
        bBuf.mark();
        bBuf.putInt(2);
        bBuf.putInt(3);
        bBuf.putInt(4);
        bBuf.putInt(5);
        bBuf.putInt(6);
        bBuf.putInt(7);
        //l =  p,p =0,mark =-1
        bBuf.flip();
        //让mark= p=0
        bBuf.mark();
        bBuf.putInt(8);
        bBuf.putInt(9);
        //8,28
        System.out.println("缓冲区Pos：" + bBuf.position() + "  缓冲区Limit："
                + bBuf.limit());
        // p=0
//        bBuf.rewind();
        bBuf.reset();
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());
        System.out.println(bBuf.getInt());

    }
}
