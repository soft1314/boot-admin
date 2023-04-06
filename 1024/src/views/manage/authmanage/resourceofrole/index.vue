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
          <el-input v-model="listQuery.funName" placeholder="请输入资源名称" size="medium" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="listQuery.uri" placeholder="请输入资源标识" size="medium" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="listQuery.resourceType" placeholder="请选择资源类别">
            <el-option v-for="item in resourceTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="listQuery.isEnabled" placeholder="请选择资源状态">
            <el-option v-for="item in enabledStatusOptions" :key="'a1'+item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="loadFunctions()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div style="margin-left: 50px;">
      <div align="left" style="float:left">
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="openAddFunctionDialog">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="danger" icon="el-icon-edit" @click="clearAllFunctionsOfOneRole">
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
      <el-table-column prop="resourceType" label="资源类别" :formatter="ResourceTypeConverterLocal" />
      <el-table-column prop="funName" label="资源名称" />
      <el-table-column prop="uri" label="资源标识" />
      <el-table-column prop="accessControlStyle" label="访问类型" :formatter="AccessControlStyleConverterLocal" />
      <el-table-column prop="isEnabled" label="是否启用" :formatter="EnabledStatusConverterLocal" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="revokePower(scope.row)">撤销授权</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <!-- 表单区域开始 -->
    <el-dialog
      :visible.sync="dialogAddFunctionVisible"
      :close-on-click-modal="false"
      width="80%"
      top="4vh"
      @close="handleCloseDialog"
    >
      <div style="text-align:center;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="listAllQuery.funName" placeholder="请输入资源名称" size="medium" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="listAllQuery.uri" placeholder="请输入资源标识" size="medium" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-select v-model="listAllQuery.resourceType" placeholder="请选择资源类别">
              <el-option v-for="item in resourceTypeOptions" :key="'b1'+item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="listAllQuery.isEnabled" placeholder="请选择资源状态">
              <el-option v-for="item in enabledStatusOptions" :key="'b2'+item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="loadAllFunctions()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-left: 50px;">
        <div align="left" style="float:left">
          <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="addFunctionsToRole">
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
              @size-change="handleAllFunctionPageSizeChange"
              @current-change="handleAllFunctionPageCurrentChange"
            />
          </div>
        </div>
      </div>
      <el-table
        v-loading="listLoading"
        :data="allFunctionList"
        border
        fit
        highlight-current-row
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="resourceType" label="资源类别" :formatter="ResourceTypeConverterLocal" />
        <el-table-column prop="funName" label="资源名称" />
        <el-table-column prop="uri" label="资源标识" />
        <el-table-column prop="accessControlStyle" label="访问类型" :formatter="AccessControlStyleConverterLocal" />
        <el-table-column prop="isEnabled" label="是否启用" :formatter="EnabledStatusConverterLocal" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="warning" @click="closeAddFunctionDialog()">
          关闭
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
    <remote-js src="/api/system/free/ResourceTypeConverter.js" />
    <remote-js src="/api/system/free/YesNoConverter.js" />
    <remote-js src="/api/system/free/AccessControlStyleConverter.js" />
  </div>
</template>

<script>
import {
  fetchResourceList,
  addFunctionsToOneRole,
  revokeFunctionRolePower,
  clearRoleAllFunctions
} from '@/api/manage'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'

export default {
  name: 'FunctionsofroleManage',
  components: {
    'remote-js': {
      render(createElement) {
        return createElement('script', {
          attrs: {
            type: 'text/javascript',
            src: process.env.VUE_APP_BASE_API + this.src
          }
        })
      },
      props: {
        src: {
          type: String,
          required: true
        }
      }
    }
  },
  data() {
    return {
      list: null,
      allFunctionList: null,
      listLoading: true,
      roleCodeName: '',
      roleName: '',
      multipleSelection: [],
      resourceTypeOptions: [],
      resourceTypeOptionsOnly: [],
      enabledStatusOptions: [],
      accessControlStyleOptionsOnly: [],
      listQuery: {
        currentPage: 1,
        pageSize: 5,
        total: 5,
        funName: '',
        uri: '',
        resourceType: '',
        isEnabled: '',
        roleId: ''
      },
      listAllQuery: {
        currentPage: 1,
        pageSize: 5,
        total: 5,
        funName: '',
        uri: '',
        resourceType: '',
        isEnabled: ''
      },
      dialogAddFunctionVisible: false,
      rules: {}
    }
  },
  created() {},
  mounted() {
    this.listQuery.roleId = this.$route.query.roleId
    this.roleCodeName = this.$route.query.codename
    this.roleName = this.$route.query.name
    this.getList()
    this.loadEnabledStatusOptionsData()
    this.loadEnabledStatusOptionsOnlyData()
    this.loadResourceTypeOptionsData()
    this.loadAccessControlStyleOptionsOnly()
  },
  methods: {
    loadFunctions() {
      this.listQuery.currentPage = 1
      this.getList()
    },
    async getList() {
      this.listLoading = false
      const response = await fetchResourceList(this.listQuery)
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
    openAddFunctionDialog() {
      this.dialogAddFunctionVisible = true
      this.loadAllFunctionPage()
    },
    closeAddFunctionDialog() {
      this.dialogAddFunctionVisible = false
    },
    loadAllFunctions() {
      this.listAllQuery.currentPage = 1
      this.loadAllFunctionPage()
    },
    async loadAllFunctionPage() {
      this.listLoading = false
      const response = await fetchResourceList(this.listAllQuery)
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

      this.allFunctionList = data.records
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
      if (val !== this.listQuery.pageSize) {
        this.listQuery.pageSize = val
        this.getList()
      }
    },
    handleAllFunctionPageSizeChange(val) {
      if (val !== this.listAllQuery.pageSize) {
        this.listAllQuery.pageSize = val
        this.loadAllFunctionPage()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.listQuery.currentPage) {
        this.listQuery.currentPage = val
        this.getList()
      }
    },
    handleAllFunctionPageCurrentChange(val) {
      if (val !== this.listAllQuery.currentPage) {
        this.listAllQuery.currentPage = val
        this.loadAllFunctionPage()
      }
    },
    indexMethod(index) {
      return this.listQuery.pageSize * (this.listQuery.currentPage - 1) + index + 1
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    async addFunctionsToRole() {
      const functionIds = new Array()
      for (var i = 0; i < this.multipleSelection.length; i++) {
        functionIds[i] = this.multipleSelection[i].gpSysFunId
      }
      var mapForSave = {
        roleId: this.listQuery.roleId,
        functionIds: functionIds
      }
      this.listLoading = true
      const response = await addFunctionsToOneRole(mapForSave)
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
        functionId: row.gpSysFunId
      }
      this.listLoading = true
      const response = await revokeFunctionRolePower(mapForSave)
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
    async clearAllFunctionsOfOneRole() {
      this.listLoading = true
      const response = await clearRoleAllFunctions(this.listQuery.roleId)
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
    async loadEnabledStatusOptionsData() {
      const response = await getDictionaryOptionsByItemType(3501, true)
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
      this.enabledStatusOptions = data
    },
    async loadEnabledStatusOptionsOnlyData() {
      const response = await getDictionaryOptionsByItemType(3500, false)
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
      this.resourceTypeOptionsOnly = data
    },
    async loadResourceTypeOptionsData() {
      const response = await getDictionaryOptionsByItemType(3500, true)
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
      this.resourceTypeOptions = data
    },
    async loadAccessControlStyleOptionsOnly() {
      const response = await getDictionaryOptionsByItemType(3511, false)
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
      this.accessControlStyleOptionsOnly = data
    },
    ResourceTypeConverterLocal(val) {
      return ResourceTypeConverter(val.resourceType)
    },
    EnabledStatusConverterLocal(val) {
      return YesNoConverter(val.isEnabled)
    },
    AccessControlStyleConverterLocal(val) {
      return AccessControlStyleConverter(val.accessControlStyle)
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
