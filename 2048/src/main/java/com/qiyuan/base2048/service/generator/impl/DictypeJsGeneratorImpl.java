package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.pojo.vo.vuecomponent.DicTypeJs;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.DictypeJsGenerator;
import com.qiyuan.base2048.service.manage.DictionaryDataGetter;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictypeJsGeneratorImpl implements DictypeJsGenerator {
    public static String DICTYPE_FILE_NAME = "commonDicType";
    @Resource
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private DictionaryDataGetter dictionaryDataGetter;

    @Override
    public ResultDTO generateJsCode(String author) throws Exception {
        List<TbDictype> tbDictypeList = dictionaryDataGetter.getValidTbDictypeList();
        DicTypeJs dicTypeJs = new DicTypeJs();
        dicTypeJs.setTypes(tbDictypeList);
        dicTypeJs.setName(DICTYPE_FILE_NAME);
        dicTypeJs.setAuthor(author);
        Map<String, Object> root = new HashMap<>();
        root.put("dicTypeJs", dicTypeJs);
        String source = freeMarkerUtils.generateSource(root, "dictypeJs.ftl");
        return ResultDTO.success(source);
    }

    @Override
    public ResultDTO generateJsFile(String path, String author, boolean overwrite) throws Exception {
        if(!overwrite) {
            if (isJsFileAlreadyExist(DICTYPE_FILE_NAME,path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        List<TbDictype> tbDictypeList = dictionaryDataGetter.getValidTbDictypeList();
        DicTypeJs dicTypeJs = new DicTypeJs();
        dicTypeJs.setTypes(tbDictypeList);
        dicTypeJs.setName(DICTYPE_FILE_NAME);
        dicTypeJs.setAuthor(author);
        Map<String, Object> root = new HashMap<>();
        root.put("dicTypeJs", dicTypeJs);

        freeMarkerUtils.generate(root, "dictypeJs.ftl", path, DICTYPE_FILE_NAME+".js");
        return ResultDTO.success();

    }

    private boolean isJsFileAlreadyExist(String fileName, String path) throws Exception {
        File file = new File(path+File.separator+fileName+".js");
        return file.exists();
    }
}
