package com.qiyuan.base2048.service.database.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.database.ColumnService;
import com.qiyuan.bautil.enums.DataTypeEnum;
import com.qiyuan.bautil.enums.TableColStyleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;
    @Resource
    private FreeMarkerUtils freeMarkerUtils;

    /**
     * todo mysql有关时间类型判断不对
     * @param tableName
     * @return
     * @throws Exception
     */
    @Override
    public List<TableColVO> getTableCols(String tableName) throws Exception {
        List<UserTableColumnResultDTO> userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName);
        List<TableColVO> tableColVOList = new ArrayList<>();
        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            TableColVO tableColVO = new TableColVO();
            /** 字段名 转 驼峰 **/
            tableColVO.setProp(freeMarkerUtils.underlineToHump(userTableColumnResultDTO.getColumnName()));
            /** ; 分隔，前面是字段label,后面是对应的代码类型短语 **/
            String[] commentsArray = userTableColumnResultDTO.getComments().split(";");
            if(commentsArray.length == 1) {
                if("DATE".equalsIgnoreCase(userTableColumnResultDTO.getDataType()) || "TIMESTAMP".equalsIgnoreCase(userTableColumnResultDTO.getDataType())){
                    tableColVO.setType(TableColStyleEnum.DATE);
                }else {
                    tableColVO.setType(TableColStyleEnum.NORMAL);
                }
                tableColVO.setLabel(userTableColumnResultDTO.getComments());
            }else if(commentsArray.length == 2){
                tableColVO.setType(TableColStyleEnum.CODE);
                tableColVO.setLabel(commentsArray[0]);
                tableColVO.setDicType(commentsArray[1]);
            }else if(commentsArray.length == 3 && "LAZY".equalsIgnoreCase(commentsArray[2])){
                tableColVO.setType(TableColStyleEnum.LAZYCODE);
                tableColVO.setLabel(commentsArray[0]);
                tableColVO.setDicType(commentsArray[1]);
            }else{
                throw new Exception("tableName:"+tableName+";字段名："+userTableColumnResultDTO.getColumnName()+";字段描述："+userTableColumnResultDTO.getComments()+";字段描述格式不正确。");
            }
            /** 数据类型 **/
            if(userTableColumnResultDTO.getDataType().startsWith("NUMBER")){
                tableColVO.setDataType(DataTypeEnum.NUMBER);
            }else if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")){
                tableColVO.setDataType(DataTypeEnum.STRING);
            }else if(userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                tableColVO.setDataType(DataTypeEnum.STRING);
            }else if("DATE".equals(userTableColumnResultDTO.getDataType())){
                tableColVO.setDataType(DataTypeEnum.DATE);
            }else if(userTableColumnResultDTO.getDataType().startsWith("TIME")){
                tableColVO.setDataType(DataTypeEnum.DATE);
            }else{
                tableColVO.setDataType(DataTypeEnum.STRING);
            }

            tableColVOList.add(tableColVO);
        }
        return tableColVOList;
    }
}
