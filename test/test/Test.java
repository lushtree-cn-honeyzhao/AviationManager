package test;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.lushapp.common.utils.collections.Collections3;

/**
 *   测试.
 */
public class Test {

	public static void main(String[] args) {


        System.out.println(new NullPointerException("空指针一次").getClass().getSimpleName());
        System.out.println(new NullPointerException("空指针一次").getClass().getName());
        List<Long> ids = null;
		if (!Collections3.isEmpty(ids)) {
			for (Long id : ids) {
				System.out.println(id);
			}
		}
		
		
		try {
			Validate.notBlank("", "queryString不能为空");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
