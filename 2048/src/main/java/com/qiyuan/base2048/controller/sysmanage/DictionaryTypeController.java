package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.eltable.TbDictypeVO;
import com.qiyuan.base2048.pojo.vo.query.DictypeQueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.service.manage.DictionaryDataGetter;
import com.qiyuan.base2048.service.manage.DictionaryDataSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/system/auth/manage/dictionary/type")
@Slf4j
@ResponseBodyInController
@Api("系统数据字典类型相关的api")
public class DictionaryTypeController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private DictionaryDataGetter dictionaryDataGetter;
    @Resource
    private DictionaryDataSaver dictionaryDataSaver;

    @PostMapping("/page")
    @ApiOperation("分页获取代码类别数据列表")
    public ResultDTO getTablePage(@Valid @RequestBody DictypeQueryVO dictypeQueryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return dictionaryDataGetter.getTbDictypeVOPage(dictypeQueryVO,false);
    }
    @PostMapping("/save")
    @ApiOperation("保存代码类别数据")
    public ResultDTO save(@Valid @RequestBody TbDictypeVO tbDictypeVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        return dictionaryDataSaver.insertOrUpdateTbDictype(tbDictypeVO,baseUser);
    }

    @PostMapping("/del")
    @ApiOperation("删除代码类别数据")
    public ResultDTO save(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return dictionaryDataSaver.deleteTbDictype(guidContainerVO.getGuid());
    }
}
