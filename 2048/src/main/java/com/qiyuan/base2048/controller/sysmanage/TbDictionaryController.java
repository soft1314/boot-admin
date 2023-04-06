package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.eltable.TbDictionaryVO;
import com.qiyuan.base2048.pojo.vo.eltable.TbDictypeVO;
import com.qiyuan.base2048.pojo.vo.query.DictypeQueryVO;
import com.qiyuan.base2048.pojo.vo.query.TbDictionaryQueryVO;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/system/auth/manage/dictionary")
@Slf4j
@ResponseBodyInController
@Api("系统数据字典代码相关的api")
public class TbDictionaryController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private DictionaryDataGetter dictionaryDataGetter;
    @Resource
    private DictionaryDataSaver dictionaryDataSaver;

    @PostMapping("/page")
    @ApiOperation("分页代码数据列表")
    public ResultDTO getTablePage(@Valid @RequestBody TbDictionaryQueryVO tbDictionaryQueryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return dictionaryDataGetter.getTbDictionaryVOPage(tbDictionaryQueryVO,false);
    }
    @PostMapping("/top/page")
    @ApiOperation("分页代码首层数据列表")
    public ResultDTO getTopTablePage(@Valid @RequestBody TbDictionaryQueryVO tbDictionaryQueryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return dictionaryDataGetter.getTopTbDictionaryVOPage(tbDictionaryQueryVO,false);
    }
    @GetMapping("/children/all")
    @ApiOperation("查询下一层数据")
    public ResultDTO fetchAllChildren(@RequestParam("guid") String guid) throws Exception{
        ResultDTO resultDTO = dictionaryDataGetter.getAllChilden(guid);
        return resultDTO;
    }
    @PostMapping("/save")
    @ApiOperation("保存代码数据")
    public ResultDTO save(@Valid @RequestBody TbDictionaryVO tbDictionaryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        return dictionaryDataSaver.insertOrUpdateTbDictionary(tbDictionaryVO,baseUser);
    }

    @PostMapping("/del")
    @ApiOperation("删除代码数据")
    public ResultDTO save(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return dictionaryDataSaver.deleteTbDictionary(guidContainerVO.getGuid());
    }
}
