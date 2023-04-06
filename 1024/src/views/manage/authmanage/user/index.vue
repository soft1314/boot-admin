<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="drawerDialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline">
          <el-form-item label="账号" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.logonName" placeholder="请输入账号" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="姓名" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.firstName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="别名" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.lastName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="机构类别" :label-width="formLabelWidthForSearch">
            <el-select v-model="listQuery.orgType" placeholder="请选择机构类别" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.ORGTYPE())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户状态" :label-width="formLabelWidthForSearch">
            <el-select v-model="listQuery.enabled" placeholder="请选择用户状态" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="删除状态" :label-width="formLabelWidthForSearch">
            <el-select v-model="listQuery.deleted" placeholder="请选择删除状态" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.DELETED())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="formLabelWidthForSearch">
            <el-button size="mini" type="success" icon="el-icon-search" @click="loadUsers()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="hanleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search"
          @click="drawerDialogVisible = true">
          查询
        </el-button>
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table v-loading="listLoading" :data="list" stripe border highlight-current-row fit style="width: 100%"
      :header-cell-style="{'text-align':'center'}">
      <el-table-column type="index" :index="indexMethod" align="right" min-width="5%" />
      <el-table-column prop="logonName" label="账号" align="left" min-width="5%" />
      <el-table-column prop="firstName" label="姓名" align="left" min-width="5%" />
      <el-table-column prop="lastName" label="别名" align="left" min-width="5%" />
      <el-table-column prop="email" label="邮件" align="left" min-width="5%" />
      <el-table-column prop="mobile" label="手机" min-width="5%" />
      <el-table-column prop="officeTel" label="办公电话" min-width="5%" />
      <el-table-column prop="admDivCode" label="区划编码" align="left" min-width="5%" />
      <el-table-column prop="admDivName" label="区划名称" align="left" min-width="5%" />
      <el-table-column prop="orgCode" label="机构编码" align="left" min-width="5%" />
      <el-table-column prop="orgName" label="机构名称" align="left" min-width="5%" />
      <el-table-column prop="orgType" label="机构类型"
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ORGTYPE())"
        min-width="5%" />
      <el-table-column prop="enabled" label="用户状态"
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())"
        min-width="5%" />
      <el-table-column prop="deleted" label="删除状态"
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())"
        min-width="5%" />
      <el-table-column prop="remarks" label="备注" min-width="15%" />
      <el-table-column label="操作" width="200px">
        <template slot-scope="scope">
          <el-button type="warning" size="least" @click="editUser(scope.row)">编辑</el-button>
          <el-button type="primary" size="least" @click="handleClickOwnerButton(scope.row)">隶属</el-button>
          <el-button type="danger" size="least" @click="handleDelUserConfirm(scope.row)">删除</el-button>
          <el-button type="primary" size="least" @click="handleClickEditRolePower(scope.row)">角色权限</el-button>
          <el-button type="primary" size="least" @click="handleClickEditDataPower(scope.row)">数据权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination :current-page="listQuery.currentPage" :page-sizes="[5,10,20,50,100,500]"
        :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="listQuery.total"
        @size-change="handlePageSizeChange" @current-change="handlePageCurrentChange" />
      <!--分页控件结束-->
    </div>
    <!-- 表单区域开始 -->
    <el-dialog :visible.sync="dialogEditUserVisible" :close-on-click-modal="false" title="编辑用户">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="登录名" prop="logonName">
          <el-input v-model="formData.logonName" placeholder="请输入登录名" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="别名" prop="logonName">
          <el-input v-model="formData.lastName" placeholder="请输入别名" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入电子邮箱" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="移动电话" prop="mobile">
          <el-input v-model="formData.mobile" placeholder="请输入移动电话" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="办公电话" prop="officeTel">
          <el-input v-model="formData.officeTel" placeholder="请输入办公电话" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="所属机构类型" prop="orgType">
          <el-radio-group v-model="formData.orgType" size="medium">
            <el-radio-button v-for="item in optionMap.get($commonDicType.ORGTYPE())" :key="item.value"
              :value="item.value" :label="item.value">
              {{ item.label }}
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-radio-group v-model="formData.enabled" size="medium">
            <el-radio-button v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value"
              :value="item.value" :label="item.value">
              {{ item.label }}
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="formData.remarks" placeholder="请输入备注" clearable :style="{width: '100%'}" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditUserVisible = false">
          关闭
        </el-button>
        <el-button type="warning" @click="submitUserForm()">
          保存
        </el-button>
        <el-button type="primary" @click="resetFormData()">
          重置
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogEditRolePowerVisible" :close-on-click-modal="false">
      <el-transfer v-model="TransferData.hasData" :titles="TransferData.titles" :data="TransferData.allData" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeTransferDialog()">
          关闭
        </el-button>
        <el-button type="warning" @click="savePower()">
          保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 数据权限子组件 start -->
    <DataPermission ref="dataPermissionComponent" />
    <!-- 数据权限子组件 end -->
    <el-dialog :visible.sync="ownerDialog.dialogVisible" :close-on-click-modal="false" :title="ownerDialog.title"
      width="70%">
      <!--查询条件和查询按钮区-->
      <div style="text-align:center;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="ownerDialog.listQuery.name" placeholder="请输入人员姓名" size="mini"
              prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="ownerDialog.listQuery.mobile" placeholder="请输入手机号码" size="mini"
              prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickQueryEmpButton()">查询
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--查询条件和查询按钮区结束-->
      <!-- 表格区 -->
      <el-table v-loading="ownerDialog.loading" :data="ownerDialog.mainTableData" border fit style="width: 100%"
        max-height="500">
        <el-table-column prop="name" label="姓名" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="sex" label="性别" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.SEX())">
        </el-table-column>
        <el-table-column prop="telephone" label="办公电话" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="mobile" label="手机号码" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="orgName" label="机构" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="admDivName" label="区划" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="操作" show-overflow-tooltip width="180">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleClickConfirmEmpButton(scope.row)">确认</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 表格区结束 -->
      <!--功能按钮区开始-->
      <div style="margin-left: 50px;">
        <div align="right">
          <!--分页控件开始-->
          <div style="align:right">
            <el-pagination :current-page="ownerDialog.listQuery.currentPage" :page-sizes="[5,10,20,50,100,500]"
              :page-size="ownerDialog.listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper"
              :total="ownerDialog.listQuery.total" @size-change="handleEmpPageSizeChange"
              @current-change="handleEmpPageCurrentChange" />
          </div>
          <!--分页控件结束-->
        </div>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>

<script>
  import DataPermission from './components/DataPermission'
  import {
    fetchSysUserPage,
    getUserRoleTransferData,
    saveUserPower,
    saveUser,
    delUser,
    saveUserEmpInfo
  } from '@/api/manage'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  import {
    fetchTbEmpPage
  } from '@/api/tb-emp-scene1'
  export default {
    name: 'UserManage',
    components: {
      DataPermission
    },
    data() {
      return {
        listLoading: true,
        radio: false,
        formLabelWidthForSearch: '80px',
        drawerDialogVisible: false,
        list: null,
        listQuery: {
          currentPage: 1,
          pageSize: 5,
          logonName: '',
          firstName: '',
          lastName: '',
          orgType: '',
          enabled: '1',
          deleted: '1',
          total: 5
        },
        dialogEditRolePowerVisible: false,
        dialogEditUserVisible: false,
        formData: {
          guid: '',
          firstName: '',
          lastName: '',
          logonName: '',
          email: '',
          mobile: '',
          officeTel: '',
          orgType: '02',
          enabled: '1',
          remarks: '无',
          version: 0,
        },
        TransferData: {
          allData: [],
          hasData: [],
          titles: ['未分配', '已分配'],
          userGuid: ''
        },
        ownerDialog: {
          loading: false,
          title: '设置隶属人员',
          dialogVisible: false,
          logonName: '',
          userGuid: '',
          version: 0,
          listQuery: {
            currentPage: 1,
            pageSize: 5,
            name: '',
            mobile: '',
            total: 5
          },
          mainTableData: []
        },
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.SEX(),
          this.$commonDicType.ORGTYPE(),
          this.$commonDicType.ENABLED(),
          this.$commonDicType.DELETED(),
          this.$commonDicType.ADM_DIV_PERMISSION(),
          this.$commonDicType.ORG_PERMISSION()
        ],
        dataPermission: {
          activeTabPane: 'admDiv',
          userGuid: '',
          logonName: '',
          admDiv: {
            permissionType: '02',
            tableData: [{
                admDivCode: '370200',
                admDivName: '青岛市本级'
              },
              {
                admDivCode: '370100',
                admDivName: '济南市本级'
              },
            ]
          },
          org: {
            permissionType: '02',
            tableData: []
          },
        },
        rules: {
          resourceType: [{
            required: true,
            message: '资源类型不能为空',
            trigger: 'change'
          }],
          funName: [{
            required: true,
            message: '请输入资源名称',
            trigger: 'blur'
          }],
          uri: [{
            required: true,
            message: '请输入资源标识符',
            trigger: 'blur'
          }],
          remark: []
        }
      }
    },
    watch: {
      treeFilterText(val) {
        this.$refs.tree.filter(val)
      }
    },
    created() {},
    mounted() {
      this.loadAllOptions()
      this.loadUsers()
    },
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      loadUsers() {
        this.listQuery.currentPage = 1
        this.getPage()
      },
      async getPage() {
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
      handleClickEditRolePower(row) {
        this.dialogEditRolePowerVisible = true
        this.loadUserRoleTransfer(row)
      },
      handleClickEditDataPower(row) {
        this.$nextTick(() => {
          this.$refs.dataPermissionComponent.showDialog()
          this.$refs.dataPermissionComponent.setComponentData(row.guid, row.logonName, row.divRangeType, row
            .orgRangeType, this.optionMap.get(this.$commonDicType
              .ADM_DIV_PERMISSION()), this.optionMap.get(this.$commonDicType
              .ORG_PERMISSION()))
        })
        this.dataPermission.logonName = row.logonName
        this.dataPermission.userGuid = row.guid
      },
      async loadUserRoleTransfer(row) {
        this.listLoading = true
        const response = await getUserRoleTransferData(row.guid)
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

        this.TransferData.allData = data.allData
        this.TransferData.hasData = data.hasData
        this.TransferData.userGuid = row.guid
        this.listLoading = false
      },
      closeTransferDialog() {
        this.dialogEditRolePowerVisible = false
      },
      handlePageSizeChange(val) {
        if (val != this.listQuery.pageSize) {
          this.listQuery.pageSize = val
          this.getPage()
        }
      },
      handleEmpPageSizeChange(val) {
        if (val != this.ownerDialog.listQuery.pageSize) {
          this.ownerDialog.listQuery.pageSize = val
          this.getEmpTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.listQuery.currentPage) {
          this.listQuery.currentPage = val
          this.getPage()
        }
      },
      handleEmpPageCurrentChange(val) {
        if (val != this.ownerDialog.listQuery.currentPage) {
          this.ownerDialog.listQuery.currentPage = val
          this.getEmpTableData()
        }
      },
      indexMethod(index) {
        return this.listQuery.pageSize * (this.listQuery.currentPage - 1) + index + 1
      },
      async savePower() {
        var mapForSave = {
          userGuid: this.TransferData.userGuid,
          userRoleGuids: this.TransferData.hasData
        }
        this.listLoading = true
        const response = await saveUserPower(mapForSave)
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
          message: response.message,
          type: 'success'
        })
      },
      async submitUserForm() {
        this.listLoading = true
        const response = await saveUser(this.formData)
        if (response.code !== 100) {
          this.listLoading = false
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        this.$message({
          message: response.message,
          type: 'success'
        })
        this.getPage()
        this.listLoading = false
      },
      resetFormData() {
        // this.formData.guid = ''
        this.formData.firstName = ''
        this.formData.lastName = ''
        this.formData.logonName = ''
        this.formData.email = ''
        this.formData.mobile = ''
        this.formData.officeTel = ''
        this.formData.orgType = '02'
        this.formData.enabled = '1'
        this.formData.remarks = '无'
      },
      initFormData() {
        this.formData.guid = ''
        this.resetFormData()
      },
      hanleClickAddButton() {
        this.initFormData()
        this.dialogEditUserVisible = true
      },
      editUser(row) {
        this.formData = this.$commonUtils.shadowCopy(row)
        this.dialogEditUserVisible = true
      },
      handleDelUserConfirm(row) {
        this.$confirm('此操作将删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleDelUser(row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      async handleDelUser(row) {
        const guidContainer = {
          guid: row.guid
        }
        const response = await delUser(guidContainer)
        if (response.code !== 100) {
          this.listLoading = false
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        this.getPage()
        this.listLoading = false
      },
      handleClickOwnerButton(row) {
        this.ownerDialog.logonName = row.logonName
        this.ownerDialog.userGuid = row.guid
        this.ownerDialog.version = row.version

        this.ownerDialog.title = '为账号【' + row.logonName + '】设置隶属人员'
        this.ownerDialog.dialogVisible = true
      },
      async handleClickConfirmEmpButton(row) {
        const userEmpVO = {
          userGuid: this.ownerDialog.userGuid,
          logonName: this.ownerDialog.logonName,
          empGuid: row.guid,
          empName: row.name,
          admDivCode: row.admDivCode,
          admDivName: row.admDivName,
          orgCode: row.orgCode,
          orgName: row.orgName,
          version: this.ownerDialog.version
        }
        const response = await saveUserEmpInfo(userEmpVO)
        if (100 !== response.code) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        this.$message({
          message: '确认成功。',
          type: 'success'
        })
        this.getPage()
        this.ownerDialog.dialogVisible = false
      },
      handleClickQueryEmpButton() {
        this.ownerDialog.listQuery.currentPage = 1
        this.getEmpTableData()
      },
      async getEmpTableData() {
        this.ownerDialog.loading = false
        const response = await fetchTbEmpPage(this.ownerDialog.listQuery)
        this.ownerDialog.loading = false
        if (100 !== response.code) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.ownerDialog.mainTableData = data.records
        this.ownerDialog.listQuery.total = data.total
      },
      loadAllOptions() {
        for (var i = 0; i < this.optionKey.length; i++) {
          this.loadDictionaryOptions(this.optionKey[i], false)
        }
      },
      colFormatter(row, column, cellValue, key) {
        return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue)
      },
      dateTimeColFormatter(row, column, cellValue) {
        return this.$commonUtils.dateTimeFormat(cellValue)
      },
      dateColFormatter(row, column, cellValue) {
        return this.$commonUtils.dateFormat(cellValue)
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
    }
  }
</script>

<style scoped>

</style>
