<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <div style="text-align: center;">
      <span>当前角色编码：</span><span style="color: red;">[{{ roleCodeName }}]</span>
      <span style="margin-left: 50px;">当前角色名称：</span><span style="color: red;">[{{ roleName }}]</span>
    </div>

    <!--查询条件和查询按钮区-->
    <div style="text-align:center;height:50px;margin-top: 10px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="listQuery.logonName" placeholder="请输入账号" size="medium" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="listQuery.userName" placeholder="请输入名称" size="medium" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="listQuery.orgType" placeholder="请选择机构类别">
            <el-option v-for="item in orgTypeOptions" :key="'a55'+item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="listQuery.userStatus" placeholder="请选择用户状态">
            <el-option v-for="item in userStatusOptions" :key="'a4'+item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="loadUsers()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div style="margin-left: 50px;">
      <div align="left" style="float:left">
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="openAddUserDialog">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="danger" icon="el-icon-edit" @click="clearRoleAllUsers">
          清空
        </el-button>
      </div>
      <div align="right">
        <!--分页控件开始-->
        <div style="align:right">
          <el-pagination
            :current-page="listQuery.currentPage"
            :page-sizes="[5,10,20,50,100,500]"
            :page-size="listQuery.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="listQuery.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageCurrentChange"
          />
        </div>
        <!--分页控件结束-->
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column type="index" :index="indexMethod" />
      <el-table-column prop="logonname" label="账号" />
      <el-table-column prop="username" label="名称" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="revokePower(scope.row)">撤销授权</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <!-- 表单区域开始 -->
    <el-dialog :visible.sync="dialogAddUserVisible" :close-on-click-modal="false" width="80%" @close="handleCloseDialog">
      <div style="text-align:center;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="listAllQuery.logonName" placeholder="请输入账号" size="medium" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="listAllQuery.userName" placeholder="请输入名称" size="medium" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-select v-model="listAllQuery.orgType" placeholder="请选择机构类别">
              <el-option v-for="item in orgTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="listAllQuery.userStatus" placeholder="请选择用户状态">
              <el-option v-for="item in userStatusOptions" :key="'a3'+item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="loadAllUsers()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-left: 50px;">
        <div align="left" style="float:left">
          <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="saveRoleUsers">
            授权
          </el-button>
        </div>
        <div align="right">
          <div style="align:right">
            <el-pagination
              :current-page="listAllQuery.currentPage"
              :page-sizes="[5,10,20,50,100,500]"
              :page-size="listAllQuery.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="listAllQuery.total"
              @size-change="handleAlluserPageSizeChange"
              @current-change="handleAlluserPageCurrentChange"
            />
          </div>
        </div>
      </div>
      <el-table v-loading="listLoading" :data="allUserList" border fit highlight-current-row style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="logonname" label="账号" />
        <el-table-column prop="username" label="名称" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="warning" @click="closeAddUserDialog()">
          关闭
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>

<script>
import {
  fetchSysUserPage,
  getUserRoleTransferData,
  saveUserPower,
  saveRoleUsersPower,
  revokeUserRolePower,
  clearRoleAllUsers
} from '@/api/manage'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'

export default {
  name: 'UsersofroleManage',
  data() {
    return {
      list: null,
      allUserList: null,
      listLoading: true,
      orgTypeOptions: [],
      userStatusOptions: [],
      roleCodeName: '',
      roleName: '',
      multipleSelection: [],
      listQuery: {
        currentPage: 1,
        pageSize: 5,
        logonName: '',
        userName: '',
        orgType: '01',
        userStatus: '',
        total: 5,
        roleId: ''
      },
      listAllQuery: {
        currentPage: 1,
        pageSize: 5,
        logonName: '',
        userName: '',
        orgType: '01',
        userStatus: '',
        total: 5
      },
      dialogAddUserVisible: false,
      rules: {
      }
    }
  },
  created() {},
  mounted() {
    this.listQuery.roleId = this.$route.query.roleId
    this.roleCodeName = this.$route.query.codename
    this.roleName = this.$route.query.name
    this.getList()
    this.loadOrgTypeOptionsData()
    this.loadUserStatusOptionsData()
  },
  methods: {
    loadUsers() {
      this.listQuery.currentPage = 1
      this.getList()
    },
    async getList() {
      this.listLoading = false
      const response = await fetchSysUserPage(this.listQuery)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response

      this.list = data.records
      this.listQuery.total = data.total
      this.listLoading = false
    },
    openAddUserDialog() {
      this.dialogAddUserVisible = true
      this.loadAllUserPage()
    },
    closeAddUserDialog() {
      this.dialogAddUserVisible = false
    },
    loadAllUsers() {
      this.listAllQuery.currentPage = 1
      this.loadAllUserPage()
    },
    async loadAllUserPage() {
      this.listLoading = false
      const response = await fetchSysUserPage(this.listAllQuery)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response

      this.allUserList = data.records
      this.listAllQuery.total = data.total
      this.listLoading = false
    },
    closeTransferDialog() {
      this.dialogTransferVisible = false
    },
    handleCloseDialog() {
      this.getList()
    },
    handlePageSizeChange(val) {
      if (val != this.listQuery.pageSize) {
        this.listQuery.pageSize = val
        this.getList()
      }
    },
    handleAlluserPageSizeChange(val) {
      if (val != this.listAllQuery.pageSize) {
        this.listAllQuery.pageSize = val
        this.loadAllUserPage()
      }
    },
    handlePageCurrentChange(val) {
      if (val != this.listQuery.currentPage) {
        this.listQuery.currentPage = val
        this.getList()
      }
    },
    handleAlluserPageCurrentChange(val) {
      if (val != this.listAllQuery.currentPage) {
        this.listAllQuery.currentPage = val
        this.loadAllUserPage()
      }
    },
    indexMethod(index) {
      return this.listQuery.pageSize * (this.listQuery.currentPage - 1) + index + 1
    },
    async loadOrgTypeOptionsData() {
      const response = await getDictionaryOptionsByItemType(3502, true)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.orgTypeOptions = data
    },
    async loadUserStatusOptionsData() {
      const response = await getDictionaryOptionsByItemType(3503, true)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.userStatusOptions = data
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    async saveRoleUsers() {
      const userGuids = new Array()
      for (var i = 0; i < this.multipleSelection.length; i++) {
        userGuids[i] = this.multipleSelection[i].guid
      }
      var mapForSave = {
        roleId: this.listQuery.roleId,
        userGuids: userGuids
      }
      this.listLoading = true
      const response = await saveRoleUsersPower(mapForSave)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.listLoading = false
      this.$message({
        message: '权限已添加',
        type: 'success'
      })
    },
    async revokePower(row) {
      var mapForSave = {
        roleId: this.listQuery.roleId,
        userGuid: row.guid
      }
      this.listLoading = true
      const response = await revokeUserRolePower(mapForSave)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.listLoading = false
      this.$message({
        message: '权限已收回',
        type: 'success'
      })
      this.getList()
    },
    async clearRoleAllUsers() {
      this.listLoading = true
      const response = await clearRoleAllUsers(this.listQuery.roleId)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.listLoading = false
      this.$message({
        message: '权限已收回',
        type: 'success'
      })
      this.getList()
    }
  }
}
</script>

<style scoped>
  .edit-input {
    padding-right: 100px;
  }

  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
</style>
