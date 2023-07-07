package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.vuecomponent.*;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.VueElementAdminFileGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VueElementAdminFileGeneratorImpl implements VueElementAdminFileGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Override
    public ResultDTO generate(Vue vue) throws Exception {
        vue = this.prepare(vue);
        Map<String, Object> root = new HashMap<>();
        root.put("vue",vue);
        freeMarkerUtils.generate(root,"indexVue1.ftl",vue.getPath(),vue.getName()+".vue");
        return ResultDTO.success();
    }

    /**
     * 准备数据
     * @param vue
     * @return
     * @throws Exception
     */
    private Vue prepare(Vue vue) throws Exception{
        for(VueTableColumnDefineDTO vueTableColumnDefineDTO:vue.getVueTableColumnDefineDTOList()){
            vueTableColumnDefineDTO.setVmodel(freeMarkerUtils.underlineToHump(vueTableColumnDefineDTO.getColumnName()));

            /** 方法名如果不是以 ） 结尾，自动添加 （） ,如 Foo 改为 Foo() **/
            if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getMethodName())){
                if(!vueTableColumnDefineDTO.getMethodName().endsWith(")")){
                    vueTableColumnDefineDTO.setMethodName(vueTableColumnDefineDTO.getMethodName()+"()");
                }
            }
        }

        if(vue.isShowQueryButton()) {
            vue.setDrawer(prepareDrawer(vue));
        }
        if(vue.isShowAddButton()){
            vue.setElDialogForAddOrEditItem(this.prepareDialogForAddOrEditItem(vue));
        }
        if(vue.isShowElTable()){
            vue.setElTable(prepareElTable(vue));
        }
        if(vue.getDataFetcher() != null && vue.getDataFetcher().size() == 2){
            String jsFileName = StringTool.toLowerCaseFirstOne(vue.getDataFetcher().get(0));
            if (jsFileName.endsWith("Controller")) {
                jsFileName = StringTool.replaceLast(jsFileName, "Controller", "");
            }
            vue.setDataFetcherApiFileName(jsFileName);
            vue.setDataFetcherMethodName(StringTool.toLowerCaseFirstOne(vue.getDataFetcher().get(1)));
        }
//        vue.setElTableColumnOptionDTOS(this.prepareElTableColumnOptionList(vue.getVueTableColumnDefineDTOList()));
        return vue;
    }
    private ElDrawer prepareDrawer(Vue vue) throws Exception{
        ElDrawer elDrawer = new ElDrawer();
        List formItemList = new ArrayList();
        for(VueTableColumnDefineDTO vueTableColumnDefineDTO: vue.getVueTableColumnDefineDTOList()){
            String vmodel = freeMarkerUtils.underlineToHump(vueTableColumnDefineDTO.getColumnName());
            if(vueTableColumnDefineDTO.isInDrawer()){
                if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getOptionsName())){
                    ElSelect elSelect = new ElSelect(vueTableColumnDefineDTO.getComments(),vmodel,vueTableColumnDefineDTO.getOptionsName());
                    elSelect.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elSelect);
                }else if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getMethodName())){
                    ElInputWithButton elInputWithButton = new ElInputWithButton(vueTableColumnDefineDTO.getComments(), vmodel, vueTableColumnDefineDTO.getMethodName());
                    elInputWithButton.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elInputWithButton);
                }else{
                    ElInput elInput = new ElInput(vueTableColumnDefineDTO.getComments(), vmodel);
                    elInput.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elInput);
                }
            }
        }
        elDrawer.setFormItemList(formItemList);
        return elDrawer;
    }
    private ElDialogForAddOrEditItem prepareDialogForAddOrEditItem(Vue vue) throws Exception{
        ElDialogForAddOrEditItem elDialogForAddOrEditItem = new ElDialogForAddOrEditItem();
        List formItemList = new ArrayList();
        for(VueTableColumnDefineDTO vueTableColumnDefineDTO: vue.getVueTableColumnDefineDTOList()){
            String vmodel = freeMarkerUtils.underlineToHump(vueTableColumnDefineDTO.getColumnName());
            if(vueTableColumnDefineDTO.isInAddOrEditForm()){
                if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getOptionsName())){
                    ElSelect elSelect = new ElSelect(vueTableColumnDefineDTO.getComments(),vmodel,vueTableColumnDefineDTO.getOptionsName());
                    elSelect.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elSelect);
                }else if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getMethodName())){
                    ElInputWithButton elInputWithButton = new ElInputWithButton(vueTableColumnDefineDTO.getComments(), vmodel, vueTableColumnDefineDTO.getMethodName());
                    elInputWithButton.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elInputWithButton);
                }else{
                    ElInput elInput = new ElInput(vueTableColumnDefineDTO.getComments(), vmodel);
                    elInput.setDefaultValue(vueTableColumnDefineDTO.getDataDefault());
                    formItemList.add(elInput);
                }
            }
        }
        elDialogForAddOrEditItem.setFormItemList(formItemList);
        return elDialogForAddOrEditItem;
    }
    private ElTable prepareElTable(Vue vue) throws Exception{
        ElTable elTable = new ElTable();
        elTable.setShowCheckedColumn(vue.getElTable().isShowCheckedColumn());
        elTable.setShowExpandColumn(vue.getElTable().isShowExpandColumn());
        elTable.setShowIndexColumn(vue.getElTable().isShowIndexColumn());
        elTable.setShowOperateColumn(vue.getElTable().isShowOperateColumn());
        elTable.setElTableColumnList(this.prepareElTableColumns(vue.getVueTableColumnDefineDTOList()));
        elTable.setExpandColumnList(this.prepareElTableExpandColumnList(vue.getVueTableColumnDefineDTOList()));
        return elTable;
    }
    private List<ElTableColumn> prepareElTableColumns(List<VueTableColumnDefineDTO> vueTableColumnDefineDTOList) throws Exception{
        List<ElTableColumn> elTableColumnList = new ArrayList<>();
        for(VueTableColumnDefineDTO vueTableColumnDefineDTO:vueTableColumnDefineDTOList){
            if(vueTableColumnDefineDTO.isInElTable()) {
                String vmodel = freeMarkerUtils.underlineToHump(vueTableColumnDefineDTO.getColumnName());
                ElTableColumn elTableColumn = new ElTableColumn();
                elTableColumn.setLabel(vueTableColumnDefineDTO.getComments());
                elTableColumn.setProp(vmodel);
                if(StringUtils.isNotBlank(vueTableColumnDefineDTO.getOptionsName())) {
                    elTableColumn.setUseFormatter(true);
                }
                elTableColumnList.add(elTableColumn);
            }
        }
        return elTableColumnList;
    }
    private List<ElTableColumn> prepareElTableExpandColumnList(List<VueTableColumnDefineDTO> vueTableColumnDefineDTOList) throws Exception{
        List<ElTableColumn> elTableExpandColumnList = new ArrayList<>();
        for(VueTableColumnDefineDTO vueTableColumnDefineDTO:vueTableColumnDefineDTOList){
            if(vueTableColumnDefineDTO.isInElTableExpand()) {
                String vmodel = freeMarkerUtils.underlineToHump(vueTableColumnDefineDTO.getColumnName());
                ElTableColumn elTableColumn = new ElTableColumn();
                elTableColumn.setLabel(vueTableColumnDefineDTO.getComments());
                elTableColumn.setProp(vmodel);
                elTableExpandColumnList.add(elTableColumn);
            }
        }
        return elTableExpandColumnList;
    }
}

