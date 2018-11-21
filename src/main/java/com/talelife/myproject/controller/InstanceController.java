package com.talelife.myproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talelife.myproject.dto.ProcessInstanceDto;
import com.talelife.util.Result;

import io.swagger.annotations.Api;

/**
 * 流程实例控制器
 * @author lwy
 *
 */
@Api(tags="流程实例接口")
@RestController
@RequestMapping("/instance")
public class InstanceController extends BaseController {
	
	@Autowired
	private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 发起流程实例
     */
    @GetMapping(value = "/start_process_instance_by_key")
    public Result<Object> startProcessInstanceByKey(String processDefinitionKey,String businessKey){
    	Map<String, Object> variables = new HashMap<>();
    	variables.put("startUser11", 1);
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
    	runtimeService.setProcessInstanceName(processInstance.getProcessInstanceId(), "流程实例"+processDefinitionKey+businessKey);
        return Result.success();
    }

    /**
     * 流程实例列表
     * @return
     */
    @GetMapping(value = "/instance_list")
    public Result<List<ProcessInstanceDto>> processInstanceList(){
    	List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
    	List<ProcessInstanceDto> data = new ArrayList<>();
    	if(!processInstances.isEmpty()){
    		for (ProcessInstance processInstance : processInstances) {
    			ProcessInstanceDto executionEntity = new ProcessInstanceDto();
    			copyProperties(processInstance, executionEntity); 
    			data.add(executionEntity);
			}
    	}
        return Result.success(data);
    }

    /**
     * 删除流程实例
     * @param processInstanceId 实例id
     * @param deleteReason 删除原因
     * @return
     */
    @PostMapping(value = "/delete_process_instance")
    public Result<Object> deleteProcessInstance(String processInstanceId,String deleteReason){
    	runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
        return Result.success();
    }
    
	private void copyProperties(ProcessInstance processInstance,ProcessInstanceDto dto) {
		dto.setName(processInstance.getName());
		dto.setBusinessKey(processInstance.getBusinessKey());
		dto.setId(processInstance.getId());
		dto.setIsEnded(processInstance.isEnded());
		dto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		dto.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
		dto.setProcessDefinitionName(processInstance.getProcessDefinitionName());
		dto.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
		dto.setTenantId(processInstance.getTenantId());
	}
   

}
