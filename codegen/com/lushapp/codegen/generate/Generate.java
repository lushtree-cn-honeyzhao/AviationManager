package com.lushapp.codegen.generate;

import java.util.List;

import com.lushapp.codegen.vo.Table;

/**
 * 根据模板生成文件
 */
public interface Generate {

	public void generate(Table table) throws Exception;

	public void generate(List<Table> tables) throws Exception;

}
