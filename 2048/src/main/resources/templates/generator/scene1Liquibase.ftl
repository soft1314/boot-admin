<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd">
  <changeSet id="${marker.changeSetId}" author="Administrator">
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情1获取表${marker.tableNameLowercase}分页数据"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene1/table/page"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="sysdate"/>
      <column name="MODIFY_TIME" valueComputed="sysdate"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
    </insert>
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情1保存表${marker.tableNameLowercase}数据"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene1/save"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="sysdate"/>
      <column name="MODIFY_TIME" valueComputed="sysdate"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
    </insert>
    <insert tableName="TB_RESOURCE">
      <column name="GUID" valueComputed="sys_guid()"/>
      <column name="NAME" value="剧情1按GUID删除表${marker.tableNameLowercase}数据"/>
      <column name="URI" value="/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene1/del"/>
      <column name="RESOURCE_TYPE" value="2"/>
      <column name="ACCESS_CONTROL_STYLE" value="02"/>
      <column name="REMARKS" value="无"/>
      <column name="CREATE_TIME" valueComputed="sysdate"/>
      <column name="MODIFY_TIME" valueComputed="sysdate"/>
      <column name="ENABLED" valueNumeric="1"/>
      <column name="DELETED" valueNumeric="1"/>
      <column name="TENANT_ID_" value="DEMO"/>
      <column name="CREATE_BY" value="admin"/>
      <column name="MODIFY_BY" value="admin"/>
      <column name="SORT" valueNumeric="0"/>
      <column name="LEAF" value="1"/>
      <column name="VERSION" valueNumeric="0"/>
      <column name="PARENT_GUID" value="function"/>
    </insert>
  </changeSet>
</databaseChangeLog>