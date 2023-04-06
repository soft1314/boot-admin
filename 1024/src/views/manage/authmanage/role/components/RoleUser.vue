<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <div style="text-align:left;height:50px;margin-top: 10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="granted.firstName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="granted.lastName" placeholder="请输入别名" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="granted.logonName" placeholder="请输入登录名" size="mini" prefix-icon="el-icon-search" clearable />
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantedQueryButton()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->
    <el-table :data="granted.tableData" stripe border highlight-current-row fit style="width: 800px"
      :header-cell-style="{'text-align':'center'}">
      <el-table-column prop="firstName" label="姓名" align="left" min-width="15%" />
      <el-table-column prop="lastName" label="别名" align="left" min-width="15%" />
      <el-table-column prop="logonName" label="登录名" align="left" min-width="15%" />
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
            <el-input v-model="grantable.firstName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.lastName" placeholder="请输入别名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.logonName" placeholder="请输入登录名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantableQueryButton()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--查询条件和查询按钮区结束-->
      <el-table :data="grantable.tableData" stripe border highlight-current-row fit style="width: 800px"
        :header-cell-style="{'text-align':'center'}">
        <el-table-column prop="firstName" label="姓名" align="left" min-width="15%" />
        <el-table-column prop="lastName" label="别名" align="left" min-width="15%" />
        <el-table-column prop="logonName" label="登录名" align="left" min-width="15%" />
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
    fetchGrantedUserPage,
    fetchGrantableUserPage,
    grantOneUserToRole,
    revokeOneUserFromRole
  } from '@/api/tr-role-user'
  export default {
    name: 'RoleUserPermission',
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
          firstName: '',
          lastName: '',
          logonName: '',
        },
        grantable: {
          page: {
            currentPage: 1,
            pageSize: 10,
            total: 2
          },
          tableData: [],
          firstName: '',
          lastName: '',
          logonName: '',
          dialogVisible: false
        },
      }
    },
    mounted() {},
    methods: {
      setComponentData(roleGuid,roleCode,roleName) {
        this.formData.roleGuid = roleGuid
        this.formData.roleCode= roleCode
        this.formData.roleName = roleName
        this.granted.page.currentPage=1
        this.loadGrantedUser()
      },
      getDialogTitle(){
        return '将用户添加到角色【'+ this.formData.roleCode + '_' + this.formData.roleName +'】'
      },
      handleGrantedPageSizeChange(val) {
        if (val !== this.granted.page.pageSize) {
          this.granted.page.pageSize = val
          this.loadGrantedUser()
        }
      },
      handleGrantablePageSizeChange(val) {
        if (val !== this.grantable.page.pageSize) {
          this.grantable.page.pageSize = val
          this.loadGrantableUser()
        }
      },
      handleGrantedPageCurrentChange(val) {
        if (val !== this.granted.page.currentPage) {
          this.granted.page.currentPage = val
          this.loadGrantedUser()
        }
      },
      handleGrantablePageCurrentChange(val) {
        if (val !== this.grantable.page.currentPage) {
          this.grantable.page.currentPage = val
          this.loadGrantableUser()
        }
      },
      async handleRevokeGrantedRow(row) {
        const RoleUserVO = {
          roleGuid: this.formData.roleCode,
          userGuid: row.guid
        }
        const response = await revokeOneUserFromRole(RoleUserVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedUser()
      },
      async handleClickGrantRowButton(row) {
        const RoleUserVO = {
          roleGuid: this.formData.roleCode,
          userGuid: row.guid
        }
        const response = await grantOneUserToRole(RoleUserVO)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.loadGrantedUser()
        this.loadGrantableUser()
      },
      handleClickGrantedQueryButton(){
        debugger
        this.granted.page.currentPage=1
        this.loadGrantedUser()
      },
      async loadGrantedUser() {
        const QueryByGuidDTO = {
          roleGuid: this.formData.roleCode,
          currentPage: this.granted.page.currentPage,
          pageSize: this.granted.page.pageSize,
          code: this.granted.logonName,
          name: this.granted.firstName,
          lastName: this.granted.lastName
        }
        const response = await fetchGrantedUserPage(QueryByGuidDTO)
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
        this.loadGrantableUser()
      },
      handleClickGrantableQueryButton(){
        this.grantable.page.currentPage = 1
        this.loadGrantableUser()
      },
      async loadGrantableUser() {
        const QueryByGuidDTO = {
          roleGuid: this.formData.roleCode,
          currentPage: this.grantable.page.currentPage,
          pageSize: this.grantable.page.pageSize,
          code: this.grantable.logonName,
          name: this.grantable.firstName,
          lastName: this.grantable.lastName
        }
        const response = await fetchGrantableUserPage(QueryByGuidDTO)
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
