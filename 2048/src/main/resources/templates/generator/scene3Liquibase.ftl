<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd">
  <changeSet id="${marker.changeSetId}" author="Administrator">
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情3表${marker.tableNameLowercase}插入数据并启动流程"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene3/start"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="MODIFY_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
      <column name="DATESTAMP" valueComputed="${r"${time.current}"}"/>
    </insert>
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情3表${marker.tableNameLowercase}数据修改并完成任务"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene3/task/complete"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="MODIFY_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
      <column name="DATESTAMP" valueComputed="${r"${time.current}"}"/>
    </insert>
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情3按表${marker.tableNameLowercase}数据查询"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene3/info"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="MODIFY_TIME" valueComputed="${r"${time.current}"}"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
      <column name="DATESTAMP" valueComputed="${r"${time.current}"}"/>
    </insert>
  </changeSet>
</databaseChangeLog>