package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.CascaderOptionsVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.VueControllerResultDTO;

import java.util.List;

public interface ControllerInfoGetter {
    List<VueControllerResultDTO> getAllInfo() throws Exception;
    List<CascaderOptionsVO> getAllCascaderOptions() throws Exception;
}
