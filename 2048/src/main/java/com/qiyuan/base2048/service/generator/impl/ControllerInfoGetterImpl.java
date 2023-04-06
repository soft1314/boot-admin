package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.vuecomponent.CascaderOptionsVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.MappingResultDTO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.VueControllerResultDTO;
import com.qiyuan.base2048.service.generator.ControllerInfoGetter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ControllerInfoGetterImpl implements ControllerInfoGetter {
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public List<VueControllerResultDTO> getAllInfo() throws Exception {
        List<VueControllerResultDTO> controllerDTOList = new ArrayList<>();
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object value = entry.getValue();
            Class<?> aClass = AopUtils.getTargetClass(value);
            RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
            VueControllerResultDTO controllerDTO = new VueControllerResultDTO();
            controllerDTO.setName(entry.getKey());
            if (annotation == null) {
                controllerDTO.setUri("");
            } else {
                controllerDTO.setUri(annotation.value()[0]);
            }
            Api api=( Api) aClass.getAnnotation( Api.class);
            if(api != null) {
                controllerDTO.setApiMessage(api.value());
            }
            List<Method> declaredMethods = Arrays.asList(aClass.getDeclaredMethods());
            List<MappingResultDTO> mappingDTOList = new ArrayList<>();
            for (int i = 0; i < declaredMethods.size(); i++) {
                MappingResultDTO mappingDTO = new MappingResultDTO();
                mappingDTO.setName(declaredMethods.get(i).getName());
                GetMapping getMapping = declaredMethods.get(i).getAnnotation(GetMapping.class);
                if (getMapping != null) {
                    mappingDTO.setMothed("GET");
                    mappingDTO.setUri(getMapping.value()[0].toString());
                }
                PostMapping postMapping = declaredMethods.get(i).getDeclaredAnnotation(PostMapping.class);
                if (postMapping != null) {
                    mappingDTO.setMothed("POST");
                    mappingDTO.setUri(postMapping.value()[0].toString());
                }
                ApiOperation apiOperation = declaredMethods.get(i).getDeclaredAnnotation(ApiOperation.class);
                if (apiOperation != null) {
                    mappingDTO.setApiOperationMessage(apiOperation.value());
                }
                mappingDTOList.add(mappingDTO);
            }
            controllerDTO.setMappingDTOS(mappingDTOList);
            controllerDTOList.add(controllerDTO);
        }
        controllerDTOList = this.fillMethods(controllerDTOList);
        return controllerDTOList;
    }

    @Override
    public List<CascaderOptionsVO> getAllCascaderOptions() throws Exception {
        List<VueControllerResultDTO> controllerDTOList = this.getAllInfo();
        List<CascaderOptionsVO> cascaderOptionsDTOListAll = new ArrayList<>();
        for(VueControllerResultDTO controllerDTO:controllerDTOList){
            CascaderOptionsVO cascaderOptionsDTO = new CascaderOptionsVO();
            cascaderOptionsDTO.setLabel(controllerDTO.getApiMessage());
            cascaderOptionsDTO.setValue(controllerDTO.getName());
            List<CascaderOptionsVO> cascaderOptionsDTOList = new ArrayList<>();
            for(MappingResultDTO mappingDTO:controllerDTO.getMappingDTOS()){
                CascaderOptionsVO child = new CascaderOptionsVO();
                child.setValue(mappingDTO.getName());
                child.setLabel(mappingDTO.getApiOperationMessage());
                cascaderOptionsDTOList.add(child);
            }
            cascaderOptionsDTO.setChildren(cascaderOptionsDTOList);
            cascaderOptionsDTOListAll.add(cascaderOptionsDTO);
        }
        return cascaderOptionsDTOListAll;
    }

    /**
     * 将所有方法信息组成一个串
     * @param controllerDTOList
     * @return
     * @throws Exception
     */
    private List<VueControllerResultDTO> fillMethods(List<VueControllerResultDTO> controllerDTOList) throws Exception{
        for(VueControllerResultDTO controllerDTO:controllerDTOList){
            StringBuffer stringBuffer = new StringBuffer();
            for(MappingResultDTO mappingDTO:controllerDTO.getMappingDTOS()){
                stringBuffer.append("<br/>").append(mappingDTO.getName());
                if(StringUtils.isNotBlank(mappingDTO.getApiOperationMessage())){
                    stringBuffer.append("---").append(mappingDTO.getApiOperationMessage());
                }
            }
            controllerDTO.setMethods(stringBuffer.toString());
        }
        return controllerDTOList;
    }
}

