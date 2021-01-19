package com.baidu.shop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "分类实体类") //swagger 的注解 ： 声明模型
@Data
@Table(name = "tb_category") //java 的实体类和数据表中的表做映射 一样
public class CategoryEntity {

    @Id //声明主键
    @ApiModelProperty(value = "类目id",example = "1")
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "父级分类",example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "是否为父节点",example = "1")
    private Integer isParent;

    @ApiModelProperty(value = "排序",example = "1")
    private Integer sort;

}
