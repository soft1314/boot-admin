package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableNameDTO;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@Slf4j
@RequestMapping("/api/system/auth/dictionary/oracle")
@Api("Oracle数据字典相关接口")
public class OracleDictionaryController {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private FormValidator formValidator;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @PostMapping("/column/list")
    @ApiOperation("获取指定数据表的字段信息")
    public ResultDTO getTableColumnList(@Valid @RequestBody TableNameDTO tableNameDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        if ("master".equalsIgnoreCase(tableNameDTO.getDataSource())) {
            return ResultDTO.success(userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableNameDTO.getTableName()));
        } else {
            return ResultDTO.failureCustom("无效的数据源名称。");
        }
    }
}
