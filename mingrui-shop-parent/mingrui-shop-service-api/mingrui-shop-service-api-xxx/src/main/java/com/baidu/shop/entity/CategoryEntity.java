package com.baidu.shop.entity;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "分类实体类") //swagger 的注解 ： 声明模型
@Data
@Table(name = "tb_category") //java 的实体类和数据表中的表做映射 一样
public class CategoryEntity {

    @Id //声明主键
    @ApiModelProperty(value = "类目id",example = "1")
    @NotNull(message = "id不能为空", groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    @NotEmpty(message = "分类名称不能为空", groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "父级分类",example = "1")
    @NotNull(message = "父级分类不能为null",groups = {MingruiOperation.Add.class})
    private Integer parentId;

    @ApiModelProperty(value = "是否为父节点",example = "1")
    @NotNull(message = "是否为父级节点不能为null",groups = {MingruiOperation.Add.class})
    private Integer isParent;

    @ApiModelProperty(value = "排序",example = "1")
    @NotNull(message = "排序字段不能为null",groups = {MingruiOperation.Add.class})
    private Integer sort;

}
