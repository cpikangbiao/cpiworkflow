/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TaskRepresentation
 * Author:   admin
 * Date:     2018/10/22 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti.common;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/10/22
 * @since 1.0.0
 */
public class TaskRepresentation implements Serializable {

    private String id;

    private String name;

    public TaskRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
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

}
