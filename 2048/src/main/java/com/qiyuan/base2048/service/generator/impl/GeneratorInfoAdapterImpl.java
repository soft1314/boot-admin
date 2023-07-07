package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GeneratorInfoAdapterImpl implements GeneratorInfoAdapter {
    @Override
    public String getMpEntityFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageEntity());
    }

    @Override
    public String getVoFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageVo());
    }

    @Override
    public String getMapstructFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageMapstruct());
    }

    @Override
    public String getMpMapperFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageMapper());
    }

    @Override
    public String getMpServiceFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageService());
    }

    @Override
    public String getSceneServiceFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageServiceSc());
    }

    @Override
    public String getMpControllerFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageController());
    }

    @Override
    public String getSceneControllerFilePath(TgProject tgProject) throws Exception {
        return this.joinPath(tgProject.getJavaOutputDir(),tgProject.getPackageControllerSc());
    }

    /**
     * java输出文件夹，结合包结构，确定具体位置
     * @param baseDir
     * @param packageName
     * @return
     * @throws Exception
     */
    private String joinPath(String baseDir,String packageName) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(baseDir);
        String[] pkNameArray = packageName.split("\\.");
        for(int i=0;i<pkNameArray.length;i++){
            stringBuffer.append(File.separator).append(pkNameArray[i]);
        }
        return stringBuffer.toString();
    }
}
