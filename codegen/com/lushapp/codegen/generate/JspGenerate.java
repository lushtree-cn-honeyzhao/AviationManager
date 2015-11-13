package com.lushapp.codegen.generate;

import java.io.StringWriter;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.lushapp.codegen.util.FileType;
import com.lushapp.codegen.util.FileUtil;
import com.lushapp.codegen.util.Resources;
import com.lushapp.codegen.util.VelocityUtil;
import com.lushapp.codegen.vo.Table;

public class JspGenerate implements Generate {
	FileType jspFileType = null;

	public JspGenerate(FileType jspFileType) {
		this.jspFileType = jspFileType;
	}

	public void generate(Table table) throws Exception {
		if (jspFileType == null)
			throw new Exception(JspGenerate.class.getName() + ": JspFileType 为null");
		/* get the Template */
		Template t = new VelocityUtil().getTemplate(Resources.TEMPLATE_PATH+"/"+jspFileType.getTemplate());
		
		VelocityContext context = new VelocityContext();

		// context.put("daoPackage", Resources.getPackage(JavaFileType.DAO));
		String entityName = table.getEntityName();
		String entityInstance=table.getEntityName().substring(0,1).toLowerCase()+table.getEntityName().substring(1);
		context.put("entityName", entityName);
		context.put("entityInstance", entityInstance);
		context.put("columns", table.getColumns());
		context.put("module", Resources.MODULE);
		context.put("requestMapping", Resources.REQUEST_MAPPING);

		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		FileUtil.create(Resources.JSP_STORE_PATH +"/modules/"+Resources.MODULE,entityInstance + jspFileType.getFileNameExtension(), writer.toString());
	}
	
	public void generate(List<Table> tables) throws Exception {
		// TODO Auto-generated method stub

	}

}
