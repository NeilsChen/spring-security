package com.chen.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-01
 */
@TableName("sys_dictionary")
@ApiModel(value="Dictionary对象", description="")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字典代码")
    private String dictCode;

    @ApiModelProperty(value = "字典名")
    private String dictName;

    @ApiModelProperty(value = "数据值")
    private String dataValue;

    @ApiModelProperty(value = "数据名")
    private String dataName;

    @ApiModelProperty(value = "数据顺序")
    private Integer valueOrder;

    @ApiModelProperty(value = "描述")
    private String descn;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getValueOrder() {
        return valueOrder;
    }

    public void setValueOrder(Integer valueOrder) {
        this.valueOrder = valueOrder;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
        "id=" + id +
        ", dictCode=" + dictCode +
        ", dictName=" + dictName +
        ", dataValue=" + dataValue +
        ", dataName=" + dataName +
        ", valueOrder=" + valueOrder +
        ", descn=" + descn +
        "}";
    }
}
