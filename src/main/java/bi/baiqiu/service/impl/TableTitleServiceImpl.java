package bi.baiqiu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;

import bi.baiqiu.mapper.TableTitleMapper;
import bi.baiqiu.pojo.TableTitleBean;

@Service
@Transactional
public class TableTitleServiceImpl {
	@Autowired
	private TableTitleMapper tableTitleDao;

	/**
	 * 分页查询方法
	 * 
	 * @param bean
	 * @param page
	 * @return
	 */
	public List<TableTitleBean> queryTableTitle(String tableName) throws Exception {

		Example example = new Example(TableTitleBean.class);
		example.setOrderByClause("sort asc");
		example.createCriteria().andEqualTo("tableName", tableName).andIsNull("deleted");

		return tableTitleDao.selectByExample(example);
	}

}
