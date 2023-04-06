<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    用户区划权限类型:
    <el-radio-group v-model="formData.admDivPermissionType" size="medium"
      @input="handleAdmDivPermissionTypeChangeEvent">
      <el-radio-button v-for="item in formData.permissionOptions" :key="item.value" :value="item.value"
        :label="item.value">
        {{ item.label }}
      </el-radio-button>
    </el-radio-group>
    <!--查询条件和查询按钮区-->
    <div style="text-align:left;height:50px;margin-top: 10px;" v-if="formData.admDivPermissionType == $commonConstant.DATA_PERMISSION_CUSTMOS()">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="granted.code" placeholder="请输入编码" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="granted.name" placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantedQueryButton()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->

    <el-table :data="granted.tableData" stripe border highlight-current-row fit style="width: 800px"
      :header-cell-style="{'text-align':'center'}"
      v-if="formData.admDivPermissionType == $commonConstant.DATA_PERMISSION_CUSTMOS()">
      <el-table-column prop="admDivCode" label="编码" align="left" min-width="15%" />
      <el-table-column prop="admDivName" label="名称" align="left" min-width="15%" />
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
    <div ref="grantedPaginationContainer" style="text-align: center;"
      v-if="formData.admDivPermissionType == $commonConstant.DATA_PERMISSION_CUSTMOS()">
      <el-pagination v-on:size-change="handleGrantedPageSizeChange" v-on:current-change="handleGrantedPageCurrentChange"
        :current-page="granted.page.currentPage" :page-sizes="[5,10,20,50,100,500]" :page-size="granted.page.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="granted.page.total">
      </el-pagination>
    </div>
    <!-- 分页组件结束 -->
    <el-dialog :visible.sync="grantable.dialogVisible" :title="getDialogTitle()" append-to-body>
      <!--查询条件和查询按钮区-->
      <div style="text-align:left;height:50px;margin-top: 10px;" v-if="formData.admDivPermissionType == $commonConstant.DATA_PERMISSION_CUSTMOS()">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="grantable.code" placeholder="请输入编码" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.name" placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantableQueryButton()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--查询条件和查询按钮区结束-->
      <el-table :data="grantable.tableData" stripe border highlight-current-row fit style="width: 800px"
        :header-cell-style="{'text-align':'center'}">
        <el-table-column prop="admDivCode" label="编码" align="left" min-width="15%" />
        <el-table-column prop="admDivName" label="名称" align="left" min-width="15%" />
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
    fetchGrantedAdmDivPage,
    fetchGrantableAdmDivPage,
    grantOneDivToUser,
    revokeOneDivFromUser
  } from '@/api/tr-user-div'
  import {
    saveUserAdmDivDataPermissionType
  } from '@/api/manage'

  export default {
    name: 'RegionPermission',
    data() {
      return {
        formData: {
          userGuid: '',
          logonName: '',
          admDivPermissionType: '',
          permissionOptions: []
        },
        granted: {
          page: {
            currentPage: 1,
            pageSize: 10,
            total: 2
          },
          tableData: [],
          code: "",
          name: ""
        },
        grantable: {
          page: {
            currentPage: 1,
            pageSize: 10,
            total: 2
          },
          tableData: [],
          code: "",
          name: "",
          dialogVisible: false
        },
      }
    },
    mounted() {},
    methods: {
      setComponentData(userGuid, logonName, admDivPermissionType, permissionOptions) {
        this.formData.userGuid = userGuid
        this.formData.logonName = logonName
        this.formData.admDivPermissionType = admDivPermissionType
        this.formData.permissionOptions = permissionOptions
        this.granted.page.currentPage=1
        this.loadGrantedDiv()
      },
      getDialogTitle(){
        return '用户行政区划权限授予【'+ this.formData.logonName +'】'
      },
      handleGrantedPageSizeChange(val) {
        if (val !== this.granted.page.pageSize) {
          this.granted.page.pageSize = val
          this.loadGrantedDiv()
        }
      },
      handleGrantablePageSizeChange(val) {
        if (val !== this.grantable.page.pageSize) {
          this.grantable.page.pageSize = val
          this.loadGrantableDiv()
        }
      },
      handleGrantedPageCurrentChange(val) {
        if (val !== this.granted.page.currentPage) {
          this.granted.page.currentPage = val
          this.loadGrantedDiv()
        }
      },
      handleGrantablePageCurrentChange(val) {
        if (val !== this.grantable.page.currentPage) {
          this.grantable.page.currentPage = val
          this.loadGrantableDiv()
        }
      },
      handleAdmDivPermissionTypeChangeEvent(val) {
        const data = {
          userGuid: this.formData.userGuid,
          permissionType: val
        }
        this.setUserAdmDivDataPermissionType(data)
        if (this.$commonConstant.DATA_PERMISSION_CUSTMOS() == val) {
          this.loadGrantedDiv()
        }
      },
      async setUserAdmDivDataPermissionType(data) {
        const response = await saveUserAdmDivDataPermissionType(data)
        this.$message({
          message: response.message,
          type: 'warning'
        })
      },
      async handleRevokeGrantedRow(row) {
        const UserDivVO = {
          userGuid: this.formData.userGuid,
          divCode: row.admDivCode
        }
        const response = await revokeOneDivFromUser(UserDivVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedDiv()
      },
      async handleClickGrantRowButton(row) {
        const UserDivVO = {
          userGuid: this.formData.userGuid,
          divCode: row.admDivCode
        }
        const response = await grantOneDivToUser(UserDivVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedDiv()
        this.loadGrantableDiv()
      },
      handleClickGrantedQueryButton(){
        this.granted.page.currentPage=1
        this.loadGrantedDiv()
      },
      async loadGrantedDiv() {
        const QueryByGuidDTO = {
          guid: this.formData.userGuid,
          currentPage: this.granted.page.currentPage,
          pageSize: this.granted.page.pageSize,
          code: this.granted.code,
          name: this.granted.name
        }
        const response = await fetchGrantedAdmDivPage(QueryByGuidDTO)
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
        this.loadGrantableDiv()
      },
      handleClickGrantableQueryButton(){
        this.grantable.page.currentPage = 1
        this.loadGrantableDiv()
      },
      async loadGrantableDiv() {
        const QueryByGuidDTO = {
          guid: this.formData.userGuid,
          currentPage: this.grantable.page.currentPage,
          pageSize: this.grantable.page.pageSize,
          code: this.grantable.code,
          name: this.grantable.name
        }
        const response = await fetchGrantableAdmDivPage(QueryByGuidDTO)
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
