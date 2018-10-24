/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FormPropertyBean
 * Author:   admin
 * Date:     2018/10/23 10:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.common;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.impl.form.BooleanFormType;
import org.activiti.engine.impl.form.EnumFormType;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/23
 * @since 1.0.0
 */
public class FormPropertyBean {

    private String id;

    private String name;

    private String type;

    private Object value;

    public FormPropertyBean(FormProperty formProperty) {
        this.id = formProperty.getId();
        this.name = formProperty.getName();

        if ( formProperty.getType() instanceof EnumFormType) {
            this.type  = formProperty.getType().getName();
            this.value = formProperty.getType().getInformation("values");
        } else if ( formProperty.getType() instanceof BooleanFormType) {
            this.type  = formProperty.getType().getName();
            this.value = formProperty.getType().getInformation("values");
        } else {
            this.type  = formProperty.getType().getName();
            this.value = formProperty.getType().getInformation("values");
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
