package com.youyun.codegen.util;

import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {

	public Template getTemplate(String resource) throws Exception {
		Properties prop = new Properties();
		prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		VelocityEngine ve = new VelocityEngine();
		ve.init(prop);
		return ve.getTemplate(resource);
	}

}
