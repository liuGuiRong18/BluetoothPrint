package com.xmwdkk.boothprint.print;

/**
 * Created by liuguirong on 8/1/17.
 * print message event
 */
public class PrintMsgEvent {
    public int type;
    public String msg;

    public PrintMsgEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
