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

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/23
 * @since 1.0.0
 */
public class FormPropertyBean implements Serializable {

    private String id;

    private String name;

    private String type;

    private Object value;

    public FormPropertyBean() {
    }

    public FormPropertyBean(String id, String name, String type, Object value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
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
