package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.cache.DictionaryCacheService;
import com.qiyuan.base2048.pojo.vo.vuecomponent.OptionsQueryDTO;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.constant.ConstantDictionaryType;
import com.qiyuan.bautil.dto.DictionaryNode;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/system/auth/generator/dictionary/options")
@Slf4j
@ResponseBodyInController
@Api("代码集相关接口")
public class OptionsGetterController {
    @Autowired
    private DictionaryCacheService dictionaryCacheService;
    @Resource
    private FormValidator formValidator;

    @ApiOperation("Oracle对象类型")
    @GetMapping("/oracle/object/type")
    public List<Map> fetchOracleObjectTypeOptions() throws Exception{
        return dictionaryCacheService.getOracleObjectTypeOptions();
    }
    @ApiOperation("获取Options数据")
    @PostMapping("/type")
    public ResultDTO fetchOptionsByType(@Valid @RequestBody OptionsQueryDTO optionsQueryDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return ResultDTO.success(dictionaryCacheService.getDictionaryByType(ConstantDictionaryType.DICTYPE_ORACLE_OBJECT_TYPE));
    }

}
