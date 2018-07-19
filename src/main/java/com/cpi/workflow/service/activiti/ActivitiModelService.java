/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ModelService
 * Author:   admin
 * Date:     2018/4/18 16:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.service.activiti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/4/18
 * @since 1.0.0
 */

@Service
public class ActivitiModelService {

    private final Logger log = LoggerFactory.getLogger(ActivitiModelService.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProcessEngine processEngine;

    public Model createNewModel(String name, String description, String key) {
        Model model = repositoryService.newModel();

        int revision = 1;

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        return model;
    }

    public ObjectNode createEditorNode() {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        return editorNode;
    }


//    /**
//     * 新建一个空模型
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @PostMapping
//    public Object newModel() throws UnsupportedEncodingException {
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        //初始化一个空模型
//        Model model = repositoryService.newModel();
//
//        //设置一些默认信息
//        String name = "new-process";
//        String description = "";
//        int revision = 1;
//        String key = "process";
//
//        ObjectNode modelNode = objectMapper.createObjectNode();
//        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
//        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
//        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
//
//        model.setName(name);
//        model.setKey(key);
//        model.setMetaInfo(modelNode.toString());
//
//        repositoryService.saveModel(model);
//        String id = model.getId();
//
//        //完善ModelEditorSource
//        ObjectNode editorNode = objectMapper.createObjectNode();
//        editorNode.put("id", "canvas");
//        editorNode.put("resourceId", "canvas");
//        ObjectNode stencilSetNode = objectMapper.createObjectNode();
//        stencilSetNode.put("namespace",
//            "http://b3mn.org/stencilset/bpmn2.0#");
//        editorNode.put("stencilset", stencilSetNode);
//        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
//        return ToWeb.buildResult().redirectUrl("/modeler.html?modelId="+id);
//    }
//
//    /**
//     * 获取所有模型
//     * @return
//     */
//    @GetMapping
//    public Object modelList(){
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        List<Model> models = repositoryService.createModelQuery().list();
//        return ToWeb.buildResult().putData("models", models);
//    }
//
//    /**
//     * 删除模型
//     * @param id
//     * @return
//     */
//    @DeleteMapping("{id}")
//    public Object deleteModel(@PathVariable("id")String id){
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        repositoryService.deleteModel(id);
//        return ToWeb.buildResult().refresh();
//    }
//
//    /**
//     * 发布模型为流程定义
//     * @param id
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("{id}/deployment")
//    public Object deploy(@PathVariable("id")String id) throws Exception {
//
//        //获取模型
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Model modelData = repositoryService.getModel(id);
//        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
//
//        if (bytes == null) {
//            return ToWeb.buildResult().status(Config.FAIL)
//                .msg("模型数据为空，请先设计流程并成功保存，再进行发布。");
//        }
//
//        JsonNode modelNode = new ObjectMapper().readTree(bytes);
//
//        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
//        if(model.getProcesses().size()==0){
//            return ToWeb.buildResult().status(Config.FAIL)
//                .msg("数据模型不符要求，请至少设计一条主线流程。");
//        }
//        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//
//        //发布流程
//        String processName = modelData.getName() + ".bpmn20.xml";
//        Deployment deployment = repositoryService.createDeployment()
//            .name(modelData.getName())
//            .addString(processName, new String(bpmnBytes, "UTF-8"))
//            .deploy();
//        modelData.setDeploymentId(deployment.getId());
//        repositoryService.saveModel(modelData);
//
//        return ToWeb.buildResult().refresh();
//    }
}
