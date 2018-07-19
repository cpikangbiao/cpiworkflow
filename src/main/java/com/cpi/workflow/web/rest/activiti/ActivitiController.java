/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ActivitiController
 * Author:   admin
 * Date:     2018/7/18 9:26
 * Description: ActivitiController
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.web.rest.activiti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈ActivitiController 〉
 *
 * @author admin
 * @create 2018/7/18
 * @since 1.0.0
 */

@RestController
@RequestMapping("/activiti")
public class ActivitiController {
    @RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            Model model = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "hello1111");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = "hello1111";
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelObjectNode.toString());
            model.setName("hello1111");
            model.setKey("12313123");

            //保存模型
            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + model.getId());
        } catch (Exception e) {
            System.out.println("创建模型失败：");
        }
    }
}
