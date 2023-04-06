<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer :visible.sync="filterDrawer.dialogVisible" direction="rtl" title="请输入查询条件" :with-header="false"
      size="30%">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
          :model="filterDrawer.formData">
          <el-form-item label="资源名称" :label-width="filterDrawer.formLabelWidth" prop="resourceName">
            <el-input v-model="filterDrawer.formData.resourceName" placeholder="请输入资源名称" size="mini" clearable
              prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="资源标识" :label-width="filterDrawer.formLabelWidth" prop="uri">
            <el-input v-model="filterDrawer.formData.uri" placeholder="请输入资源标识" size="mini" clearable
              prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="资源状态" :label-width="filterDrawer.formLabelWidth" prop="enabled">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择资源状态" size="mini" clearable>
              <el-option v-for="item in enabledStatusOptions" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>

          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button v-on:click="loadResource()" size="mini" type="success" icon="el-icon-search">查询</el-button>
            <el-button v-on:click="resetForm('drawerForm')" size="mini" type="primary" icon="el-icon-refresh">重置
            </el-button>
            <el-button v-on:click="hideDrawer()" size="mini" icon="el-icon-close">关闭</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!-- 查询抽屉结束 -->

    <!--查询条件和查询按钮区-->

    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="addFormDialogVisible = true">
          新增
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
          查询
        </el-button>
      </div>
    </div>
    <!--功能按钮区结束-->
    <el-table v-loading="listLoading" :data="list" stripe border fit highlight-current-row style="width: 100%">
      <el-table-column label="名称" align="left" min-width="5%">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input v-model="row.name" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
          <span v-else>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标识" align="left" min-width="15%">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input v-model="row.uri" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
          <span v-else>{{ row.uri }}</span>
        </template>
      </el-table-column>
      <el-table-column label="访问类型" min-width="10%">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <div>
              <el-radio-group v-model="row.accessControlStyle" size="mini">
                <el-radio-button v-for="item in accessControlStyleOptionsOnly" :key="item.value" :value="item.value"
                  :label="item.value">
                  {{ item.label }}
                </el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <span v-else>{{ $commonUtils.optoinValue2Lable(accessControlStyleOptionsOnly,row.accessControlStyle) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" min-width="5%">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <span class="cell-edit-switch">
              <el-switch v-model="row.enabled" style="display: block;height: 30px;" active-color="#13ce66"
                inactive-color="#ff4949" active-value='1' inactive-value='2' active-text="启用" inactive-text="禁用" />
            </span>
          </template>
          <span v-else>{{ $commonUtils.optoinValue2Lable(enabledStatusOptions,row.enabled) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="5%">
        <template slot-scope="{row}">
          <template v-if="row.edit" style="display: inline-block">
            <el-button type="success" size="least" @click="confirmEdit(row)">
              保存
            </el-button>
            <el-button type="warning" size="least" @click="cancelEdit(row)">
              取消
            </el-button>
          </template>
          <template v-else style="display: inline-block">
            <el-button type="warning" size="least" @click="row.edit=!row.edit">
              修改
            </el-button>
            <el-button type="primary" size="least" @click="openTransferDialog(row)">
              角色
            </el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="5%">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input v-model="row.remarks" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
          <span v-else>{{ row.remarks }}</span>
        </template>
      </el-table-column>
    </el-table>
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination :current-page="filterDrawer.formData.currentPage" :page-sizes="[5,10,20,50,100,500]"
        :page-size="filterDrawer.formData.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="filterDrawer.formData.total" @size-change="handlePageSizeChange"
        @current-change="handlePageCurrentChange" />
    </div>
    <!-- 添加表单区域开始 -->
    <el-dialog :visible.sync="addFormDialogVisible" width="80%" :close-on-click-modal="false" @close="getPage()">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入资源名称" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="资源标识符" prop="uri">
          <el-input v-model="formData.uri" placeholder="请输入资源标识符" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="访问控制类型" prop="accessControlStyle">
          <el-radio-group v-model="formData.accessControlStyle" size="medium">
            <el-radio-button v-for="item in accessControlStyleOptionsOnly" :key="item.value" :value="item.value"
              :label="item.value">
              {{ item.label }}
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否启用" prop="enabled" required>
          <el-switch v-model="formData.enabled" :active-value='1' :inactive-value='2' active-color="#13ce66"
            inactive-color="#ff4949" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="formData.remarks" placeholder="请输入备注" clearable :style="{width: '100%'}" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormDialogVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="submitForm()">
          添加
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
    <el-dialog :visible.sync="dialogTransferVisible" :close-on-click-modal="false">
      <el-transfer v-model="TransferData.hasData" :titles="TransferData.titles" :data="TransferData.allData" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTransferVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="savePower()">
          保存
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    fetchSysResurceList,
    updateSysResource,
    addSysResource,
    getResourceRoleTransferData,
    saveResourcePower
  } from '@/api/manage'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'

  export default {
    name: 'ResourceManage',
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'info',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        list: null,
        listLoading: false,
        resourceTypeOptions: [],
        enabledStatusOptions: [],
        accessControlStyleOptionsOnly: [],
        filterDrawer: {
          dialogVisible: false,
          formLabelWidth: '100px',
          formData: {
            resourceName: '',
            uri: '',
            resourceType: '2',
            enabled: '1',
            currentPage: 1,
            pageSize: 10,
            total: 0,
          },
        },
        addFormDialogVisible: false,
        dialogTransferVisible: false,
        formData: {
          resourceType: '2',
          name: '',
          uri: '/api',
          accessControlStyle: '02',
          enabled: '1',
          remarks: '无',
          guid: '-1'
        },
        TransferData: {
          allData: [],
          hasData: [],
          titles: ['未分配', '已分配'],
          resourceGuid: ''
        },
        rules: {
          resourceType: [{
            required: true,
            message: '资源类型不能为空',
            trigger: 'change'
          }],
          name: [{
            required: true,
            message: '请输入资源名称',
            trigger: 'blur'
          }],
          uri: [{
            required: true,
            message: '请输入资源标识符',
            trigger: 'blur'
          }],
          remarks: []
        }
      }
    },
    created() {},
    mounted() {
      this.loadDictionaryOptions(this.$commonDicType.RESOURCE_TYPE(), false)
      this.loadDictionaryOptions(this.$commonDicType.ENABLED(), false)
      this.loadDictionaryOptions(this.$commonDicType.RESOURCE_ACCESS_CONTROL_STYLE(), false)
      this.getPage()
    },
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      loadResource() {
        this.filterDrawer.formData.currentPage = 1
        this.getPage()
      },
      async getPage() {
        this.listLoading = true
        const response = await fetchSysResurceList(this.filterDrawer.formData)
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
        const items = data.records
        this.list = items.map(v => {
          this.$set(v, 'edit', false)
          this.SaveSomething(v)
          return v
        })
        this.filterDrawer.formData.total = data.total
        this.listLoading = false
      },
      cancelEdit(row) {
        this.RestoreSomething(row)
        row.edit = false
        this.$message({
          message: '未保存数据变动已丢弃！',
          type: 'warning'
        })
      },
      async confirmEdit(row) {
        row.edit = false
        this.listLoading = true
        const response = await updateSysResource(row)
        if (response.code !== 100) {
          this.listLoading = false
          this.$message({
            message: response.message,
            type: 'error',
            duration: 10 * 1000
          })
          return
        }
        this.listLoading = false
        this.SaveSomething(row)
        this.$message({
          message: '数据已保存',
          type: 'success'
        })
      },
      SaveSomething(row) {
        row.originalResourceType = row.resourceType
        row.originalName = row.name
        row.originalUri = row.uri
        row.originalEnabled = row.enabled
        row.originalRemarks = row.remarks
        row.originalAccessControlStyle = row.accessControlStyle
      },
      RestoreSomething(row) {
        row.resourceType = row.originalResourceType
        row.name = row.originalName
        row.uri = row.originalUri
        row.enabled = row.originalEnabled
        row.remarks = row.originalRemarks
        row.accessControlStyle = row.originalAccessControlStyle
      },
      submitForm() {
        this.$refs['elForm'].validate(valid => {
          if (!valid) return
          this.addResource()
        })
      },
      async addResource() {
        this.listLoading = true
        const response = await addSysResource(this.formData)
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
          message: '数据已添加',
          type: 'success'
        })
      },
      handlePageSizeChange(val) {
        if (val !== this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val
          this.getPage()
        }
      },
      handlePageCurrentChange(val) {
        if (val !== this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val
          this.getPage()
        }
      },
      openTransferDialog(row) {
        this.dialogTransferVisible = true
        this.loadResourceRoleTransferData(row)
      },
      async loadResourceRoleTransferData(row) {
        const response = await getResourceRoleTransferData(row.guid)
        if (response.code !== 100) {
          this.listLoading = false
          this.$message({
            message: response.message,
            type: 'error'
          })
          return
        }
        const {
          data
        } = response
        this.TransferData.allData = data.allData
        this.TransferData.hasData = data.hasData
        this.TransferData.resourceGuid = row.guid
      },
      async savePower() {
        var mapForSave = {
          resourceGuid: this.TransferData.resourceGuid,
          roleGuids: this.TransferData.hasData
        }
        this.listLoading = true
        const response = await saveResourcePower(mapForSave)
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
      async loadDictionaryOptions(itemType, includeAllOptions) {
        const response = await getDictionaryOptionsByItemType(itemType, includeAllOptions)
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
        if (itemType === this.$commonDicType.RESOURCE_TYPE()) {
          this.resourceTypeOptions = data
        }
        if (itemType === this.$commonDicType.ENABLED()) {
          this.enabledStatusOptions = data
        }
        if (itemType === this.$commonDicType.RESOURCE_ACCESS_CONTROL_STYLE()) {
          this.accessControlStyleOptionsOnly = data
        }
      },
      showDrawer() {
        this.filterDrawer.dialogVisible = true
      },
      hideDrawer() {
        this.filterDrawer.dialogVisible = false
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  }
</script>

<style scoped>

</style>
