package com.eugene;

import com.eugene.Message.*;
import com.eugene.Util.JSONUtil;

public class Driver extends Message {
	
	public static void main(String[] args) {
		JSONUtil util = new JSONUtil(System.getProperty("user.dir") + "\\work", "\\MS Song Library.json");
		util.parseJson();
	}
	
}
