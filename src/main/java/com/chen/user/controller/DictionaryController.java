package com.chen.user.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.common.ResponseObj;
import com.chen.user.pojo.Dictionary;
import com.chen.user.service.IDictionaryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-01
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	@ApiOperation(value = "排序分页查询")
	@GetMapping("selectOrderBy")
	public ResponseObj<Object> selectOrderBy(@ApiParam(value = "页次，从1开始", required = false, example = "1") @RequestParam(name = "current", required = false) Integer current,
			@ApiParam(name = "pagesize", value = "每页大小", required = false, example = "10") @RequestParam(name = "pagesize", required = false) Integer pagesize,
			@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc,
			@ApiParam(name = "dictName", value = "字典类型", required = false) @RequestParam(name = "dictName", required = false) String dictName,
			@ApiParam(name = "dataValue", value = "数据值", required = false) @RequestParam(name = "dataValue", required = false) String dataValue) {

		try {
			current = current == null ? 0 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			isAsc = isAsc == null ? true : isAsc;
			
			// 添加分页数据
			Page<Dictionary> page = new Page<Dictionary>(current, pagesize);
			// 添加排序字段
			QueryWrapper<Dictionary> qw = new QueryWrapper<Dictionary>();
			
			if(StringUtils.isNotEmpty(dictName)) {
				// java1.8 函数式编程 (param1,param2...paramN) -> { statement,statement... return value }
				//  一个参数时 可省略小括号    	 param1 -> { statement,statement... return value }
				//  方法体只有一行语句时 省略大括号  param1 -> value  返回value
				qw.and(i -> i.like("dict_code", dictName).or().like("dict_name", dictName));
			}
			
			if(StringUtils.isNotEmpty(dataValue)) {
				qw.and(i -> i.like("data_value", dataValue).or().like("data_name", dataValue));
			}
			
			if (StringUtils.isNotEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}else {
				qw.orderBy(true, true, "dict_code","value_order", "data_value");
			}
			IPage<Dictionary> pageData = dictionaryService.page(page, qw);
			return ResponseObj.success(pageData);
		} catch (Exception e) {
			log.error("操作失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "根据字典代码查询")
	@GetMapping("selectByCode")
	public ResponseObj<Object> selectByCode(@ApiParam(value = "字典代码", required = true) 
	@RequestParam(name = "dictCode", required = true) String dictCode) {

		try {
			// 添加排序字段
			QueryWrapper<Dictionary> qw = new QueryWrapper<Dictionary>();
			qw.eq("dict_code", dictCode.toUpperCase());
			qw.orderBy(true, true, "dict_code","value_order");
			List<Dictionary> list = dictionaryService.list(qw);
			Map<String,String> data = new LinkedHashMap<String,String>();
			for(int i=0;i<list.size();i++) {
				Dictionary dictionary = list.get(i);
				data.put(dictionary.getDataValue(), dictionary.getDataName());
			}
			return ResponseObj.success(data);
		} catch (Exception e) {
			log.error("操作失败 ", e);
			return ResponseObj.badRequest();
		}

	}
	
	@ApiOperation(value = "添加或更新数据")
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "dictCode", required = true) String dictCode,
			@RequestParam(name = "dictName", required = false) String dictName,
			@RequestParam(name = "dataValue", required = true) String dataValue,
			@RequestParam(name = "dataName", required = true) String dataName,
			@RequestParam(name = "valueOrder", required = false) Integer valueOrder,
			@RequestParam(name = "descn", required = false) String descn) {
		try {
			
			QueryWrapper<Dictionary> qw = new QueryWrapper<Dictionary>();
			qw.eq("dict_code", dictCode.toUpperCase()).eq("data_value", dataValue);
			int count = dictionaryService.count(qw);
			if(id==null && count > 0) {
				return ResponseObj.badRequest("唯一约束：字典代码和数据值已存在！");
			}
			Dictionary dict = new Dictionary();
			dict.setId(id);
			dict.setDictCode(dictCode.toUpperCase());
			dict.setDictName(dictName);
			dict.setDataValue(dataValue);
			dict.setDataName(dataName);
			dict.setValueOrder(valueOrder);
			dict.setDescn(descn);
			boolean save = dictionaryService.saveOrUpdate(dict);
			if(save) {
				return ResponseObj.success();
			}else {
				return ResponseObj.badRequest();
			}
		} catch (Exception e) {
			log.error("操作失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "删除数据")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@ApiParam( required = true) 
	@RequestParam(name = "ids", required = true) String ids) {
		try {
			QueryWrapper<Dictionary> qw = new QueryWrapper<Dictionary>();
			qw.inSql("id", ids);
			boolean remove = dictionaryService.remove(qw);
			if(remove) {
				return ResponseObj.success();
			}else {
				return ResponseObj.badRequest();
			}
		} catch (Exception e) {
			log.error("操作失败 ", e);
			return ResponseObj.badRequest();
		}
	}
	
}

