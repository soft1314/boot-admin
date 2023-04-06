package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-16 16:08
 **/
@Data
public class Vue {
    /** 以下是输出的 **/
    @NotBlank(message = "Vue路由名称是必填项。")
    private String name;
    private ElDrawer drawer;
    private ElTable elTable;
    private ElDialogForAddOrEditItem elDialogForAddOrEditItem;
    private List<String> dataFetcher;
    private String dataFetcherApiFileName;
    private String dataFetcherMethodName;
    private boolean showRefreshButton = true;
    private boolean showQueryButton = true;
    private boolean showAddButton = false;
    private boolean showDelButton = false;
    private boolean showExportButton = false;
    private boolean showSubmitButton = false;
    private boolean showAuditButton = false;
    private boolean showRevokeButton = false;
    private boolean showElTable = true;
    /** 以下是输入用的 **/
    private String path;
    private List<VueTableColumnDefineDTO> vueTableColumnDefineDTOList;
}
