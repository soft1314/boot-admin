package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.cache.DictionaryCacheService;
import com.qiyuan.base2048.service.manage.DictionaryDataGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.IntegerOptionNode;
import com.qiyuan.bautil.dto.OptionNode;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-02 11:21
 **/
@RestController
@RequestMapping("/api/system/auth/dic")
@ResponseBodyInController
@Slf4j
@Api("字典获取相关接口")
public class DictionaryController {
    @Resource
    private DictionaryCacheService dictionaryCacheService;
    @Resource
    private DictionaryDataGetter dictionaryDataGetter;
    /**
     * 提供下拉列表的Options数据
     * @param itemType
     * @param includeAllOptions
     * @return
     * @throws Exception
     */
    @GetMapping("/options")
    @ApiOperation("根据代码类别取代码Options")
    public ResultDTO getDictionaryOptionsByItemType(@RequestParam("itemType") String itemType, @RequestParam("includeAllOptions") boolean includeAllOptions) throws Exception{
        List<OptionNode> dictionaryNodeList = dictionaryCacheService.getDictionaryByType(itemType);
        if(includeAllOptions){
            OptionNode optionNode = new OptionNode();
            optionNode.setLabel("");
            optionNode.setValue("");
            dictionaryNodeList.add(optionNode);
        }
        return ResultDTO.success(dictionaryNodeList);
    }

    /**
     * 提供整型是否下拉列表的Options数据
     * @return
     * @throws Exception
     */
    @GetMapping("/yes-no-options")
    @ApiOperation("获取是否Options")
    public ResultDTO getYesNoOption() throws Exception{
        List<IntegerOptionNode> optionNodeList = new ArrayList<>(2);
        IntegerOptionNode yesNode = new IntegerOptionNode();
        yesNode.setLabel("是");
        yesNode.setValue(1);
        optionNodeList.add(yesNode);
        IntegerOptionNode noNode = new IntegerOptionNode();
        noNode.setLabel("否");
        noNode.setValue(0);
        optionNodeList.add(noNode);
        return ResultDTO.success(optionNodeList);
    }
    @GetMapping("/lazy/node")
    @ApiOperation("懒获取一层dictionary的级联数据")
    public ResultDTO getLazyDictionary(@RequestParam("dicType") String dicType,@RequestParam("code") String code) throws Exception{
        return dictionaryDataGetter.getOneLevelNode(dicType,code);
    }
}
