package com.eugene.Message;

public class Message {

	public void msg(Object m) {
		System.out.println(m);
	}
	public void msg(Object m, boolean nl) {
		if (nl)	System.out.println(m + "\n");
		else	System.out.println(m);
	}
	
	public static void msgS(Object m) {
		System.out.println(m);
	}
	public static void msgS(Object m, boolean nl) {
		if (nl)	System.out.println(m + "\n");
		else	System.out.println(m);
	}
	
	
	public void nl() {
		System.out.println();
	}
	
	
	
}
