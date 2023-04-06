<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <div style="text-align:left;height:50px;margin-top: 10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="granted.name" placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="granted.uri" placeholder="请输入定位符" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item label="请选择类别" :label-width="formLabelWidthForSearch">
          <el-select v-model="granted.resourceType" placeholder="请选择类别" size="mini" clearable>
            <el-option v-for="item in optionMap.get($commonDicType.RESOURCE_TYPE())" :key="item.value"
              :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantedQueryButton()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->
    <el-table :data="granted.tableData" stripe border highlight-current-row fit style="width: 800px"
      :header-cell-style="{'text-align':'center'}">
      <el-table-column prop="name" label="名称" align="left" min-width="15%" />
      <el-table-column prop="uri" label="定位符" align="left" min-width="15%" />
      <el-table-column prop="resourceType" label="类别" align="left" min-width="15%" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.RESOURCE_TYPE())" />
      <el-table-column label="操作" min-width="10%">
        <template slot="header" slot-scope="scope">
          操作
          <el-button size="least" type="primary" icon="el-icon-plus" @click="handleClickAddButton()" />
        </template>
        <template slot-scope="scope">
          <el-button type="danger" size="least" @click="handleRevokeGrantedRow(scope.row)">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件开始 -->
    <div ref="grantedPaginationContainer" style="text-align: center;">
      <el-pagination v-on:size-change="handleGrantedPageSizeChange" v-on:current-change="handleGrantedPageCurrentChange"
        :current-page="granted.page.currentPage" :page-sizes="[5,10,20,50,100,500]" :page-size="granted.page.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="granted.page.total">
      </el-pagination>
    </div>
    <!-- 分页组件结束 -->
    <el-dialog :visible.sync="grantable.dialogVisible" :title="getDialogTitle()" append-to-body>
      <!--查询条件和查询按钮区-->
      <div style="text-align:left;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="grantable.name" placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.uri" placeholder="请输入定位符" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item label="请选择类别" :label-width="formLabelWidthForSearch">
            <el-select v-model="grantable.resourceType" placeholder="请选择类别" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.RESOURCE_TYPE())" :key="item.value"
                :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantableQueryButton()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--查询条件和查询按钮区结束-->
      <el-table :data="grantable.tableData" stripe border highlight-current-row fit style="width: 800px"
        :header-cell-style="{'text-align':'center'}">
        <el-table-column prop="name" label="名称" align="left" min-width="15%" />
        <el-table-column prop="uri" label="定位符" align="left" min-width="15%" />
        <el-table-column prop="resourceType" label="类别" align="left" min-width="15%" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.RESOURCE_TYPE())" />
        <el-table-column label="操作" min-width="10%">
          <template slot-scope="scope">
            <el-button type="danger" size="least" @click="handleClickGrantRowButton(scope.row)">赋权</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件开始 -->
      <div ref="grantablePaginationContainer" style="text-align: center;">
        <el-pagination v-on:size-change="handleGrantablePageSizeChange"
          v-on:current-change="handleGrantablePageCurrentChange" :current-page="grantable.page.currentPage"
          :page-sizes="[5,10,20,50,100,500]" :page-size="grantable.page.pageSize"
          layout="total, sizes, prev, pager, next, jumper" :total="grantable.page.total">
        </el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    fetchGrantedResourcePage,
    fetchGrantableResourcePage,
    grantOneResourceToRole,
    revokeOneResourceFromRole
  } from '@/api/tr-role-resource'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'

  export default {
    name: 'RoleResourcePermission',
    data() {
      return {
        formData: {
          roleGuid: '',
          roleCode: '',
          roleName: ''
        },
        granted: {
          page: {
            currentPage: 1,
            pageSize: 10,
            total: 2
          },
          tableData: [],
          name: '',
          uri: '',
          resourceType: '',
        },
        grantable: {
          page: {
            currentPage: 1,
            pageSize: 10,
            total: 2
          },
          tableData: [],
          name: '',
          uri: '',
          resourceType: '',
          dialogVisible: false
        },
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.RESOURCE_TYPE()
        ],
      }
    },
    mounted() {
      this.loadAllOptions()
    },
    methods: {
      loadAllOptions() {
        for (var i = 0; i < this.optionKey.length; i++) {
          this.loadDictionaryOptions(this.optionKey[i], false)
        }
      },
      async loadDictionaryOptions(itemType, includeAllOptions) {
        this.listLoading = true
        const response = await getDictionaryOptionsByItemType(itemType, includeAllOptions)
        this.listLoading = false
        if (response.code !== 100) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.optionMap.set(itemType, data)
      },
      colFormatter(row,column,cellValue,key){
          return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key),cellValue + '')
      },
      setComponentData(roleGuid,roleCode,roleName) {
        this.formData.roleGuid = roleGuid
        this.formData.roleCode= roleCode
        this.formData.roleName = roleName
        this.granted.page.currentPage=1
        this.loadGrantedResource()
      },
      getDialogTitle(){
        return '将资源授予到角色【'+ this.formData.roleCode + '_' + this.formData.roleName +'】'
      },
      handleGrantedPageSizeChange(val) {
        if (val !== this.granted.page.pageSize) {
          this.granted.page.pageSize = val
          this.loadGrantedResource()
        }
      },
      handleGrantablePageSizeChange(val) {
        if (val !== this.grantable.page.pageSize) {
          this.grantable.page.pageSize = val
          this.loadGrantableResource()
        }
      },
      handleGrantedPageCurrentChange(val) {
        if (val !== this.granted.page.currentPage) {
          this.granted.page.currentPage = val
          this.loadGrantedResource()
        }
      },
      handleGrantablePageCurrentChange(val) {
        if (val !== this.grantable.page.currentPage) {
          this.grantable.page.currentPage = val
          this.loadGrantableResource()
        }
      },
      async handleRevokeGrantedRow(row) {
        const RoleResourceVO = {
          roleGuid: this.formData.roleCode,
          resourceGuid: row.guid
        }
        const response = await revokeOneResourceFromRole(RoleResourceVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedResource()
      },
      async handleClickGrantRowButton(row) {
        const RoleResourceVO = {
          roleGuid: this.formData.roleCode,
          resourceGuid: row.guid
        }
        const response = await grantOneResourceToRole(RoleResourceVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedResource()
        this.loadGrantableResource()
      },
      handleClickGrantedQueryButton(){
        this.granted.page.currentPage=1
        this.loadGrantedResource()
      },
      async loadGrantedResource() {
        const QueryByGuidDTO = {
          roleGuid: this.formData.roleCode,
          currentPage: this.granted.page.currentPage,
          pageSize: this.granted.page.pageSize,
          code: this.granted.resourceType,
          name: this.granted.name,
          lastName: this.granted.uri
        }
        const response = await fetchGrantedResourcePage(QueryByGuidDTO)
        if (response.code !== 100) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.granted.page.total = data.total
        this.granted.tableData = data.records
      },
      handleClickAddButton() {
        this.loadGrantableResource()
      },
      handleClickGrantableQueryButton(){
        this.grantable.page.currentPage = 1
        this.loadGrantableResource()
      },
      async loadGrantableResource() {
        const QueryByGuidDTO = {
          roleGuid: this.formData.roleCode,
          currentPage: this.grantable.page.currentPage,
          pageSize: this.grantable.page.pageSize,
          code: this.grantable.resourceType,
          name: this.grantable.name,
          lastName: this.grantable.uri
        }
        const response = await fetchGrantableResourcePage(QueryByGuidDTO)
        if (response.code !== 100) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.grantable.tableData = data.records
        this.grantable.page.total = data.total
        this.grantable.dialogVisible = true
      },
    },
  };
</script>

<style>
</style>
