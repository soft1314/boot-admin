<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="drawerDialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline">
          <el-form-item label="菜单名称" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.resourceName" placeholder="请输入菜单名称" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="菜单标识" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.uri" placeholder="请输入菜单标识" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="启用状态" :label-width="formLabelWidthForSearch">
            <el-select v-model="listQuery.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value"
                :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="formLabelWidthForSearch">
            <el-button size="mini" type="success" icon="el-icon-search" @click="loadTopMenus()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!--查询条件和查询按钮区结束-->
    <!--功能按钮区开始-->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">

    <!-- <div style="margin-left: 50px;"> -->
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
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
      :header-cell-style="{'text-align':'center'}" row-key="guid" default-expand-all lazy :load="loadChildren"
      :tree-props="{hasChildren:'hasChildren'}" ref="multipleTable">
      <el-table-column prop="name" label="名称" align="left" min-width="15%" />
      <el-table-column prop="uri" label="标识" align="left" min-width="15%" />
      <el-table-column prop="sort" label="顺序号" align="left" min-width="5%" />
      <el-table-column prop="remarks" label="备注" min-width="5%" />
      <el-table-column prop="accessControlStyle" label="访问类型" min-width="5%" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.RESOURCE_ACCESS_CONTROL_STYLE())" />
      <el-table-column prop="enabled" label="启用状态" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" min-width="5%" />
      <el-table-column label="操作" min-width="10%">
        <template slot-scope="scope">
          <el-button type="warning" size="least" @click="handleEditMenu(scope.row)">编辑</el-button>
          <el-button type="primary" size="least" @click="handleAddSub(scope.row)">添加</el-button>
          <el-button type="primary" size="least" @click="handleEditPower(scope.row)">权限</el-button>
          <el-button type="danger" size="least" @click="handleDelRowConfirm(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div align="right">
      <!--分页控件开始-->
    <div ref="paginationContainer" style="text-align: center;">
        <el-pagination :current-page="listQuery.currentPage" :page-sizes="[5,10,20,50,100,500]"
          :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="listQuery.total"
          @size-change="handlePageSizeChange" @current-change="handlePageCurrentChange" />
      </div>
      <!--分页控件结束-->
    </div>
    <!--列表结束-->
    <!-- 表单区域开始 -->
    <el-dialog :visible.sync="dialogeditVisible" :close-on-click-modal="false" title="编辑">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入名称" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="标识" prop="uri">
          <el-input v-model="formData.uri" placeholder="请输入标识" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="顺序号" prop="sort">
          <el-input type="number" v-model="formData.sort" placeholder="请输入顺序号" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="formData.remarks" placeholder="请输入备注" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="访问类型" prop="accessControlStyle">
          <el-radio-group v-model="formData.accessControlStyle" size="medium">
            <el-radio-button v-for="item in optionMap.get($commonDicType.RESOURCE_ACCESS_CONTROL_STYLE())"
              :key="item.value" :value="item.value" :label="item.value">
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
        <el-form-item label="删除状态" prop="deleted">
          <el-radio-group v-model="formData.deleted" size="medium">
            <el-radio-button v-for="item in optionMap.get($commonDicType.DELETED())" :key="item.value"
              :value="item.value" :label="item.value">
              {{ item.label }}
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogeditVisible = false">
          关闭
        </el-button>
        <el-button type="warning" @click="submitEditForm()">
          保存
        </el-button>
        <el-button type="primary" @click="resetFormData()">
          重置
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogTransferVisible" :close-on-click-modal="false">
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
    <!-- 表单区域结束 -->
  </div>
</template>
<script>
  import {
    getResourceRoleTransferData,
    saveResourcePower,
    saveMenu,
    delResource,
    fetchTopMenuPage,
    fetchMenuAllChildren
  } from '@/api/manage'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  export default {
    name: 'MenuManage',
    data() {
      return {
        treeMap: new Map(),
        listLoading: false,
        radio: false,
        formLabelWidthForSearch: '80px',
        drawerDialogVisible: false,
        list: [],
        listQuery: {
          currentPage: 1,
          pageSize: 5,
          resourceName: '',
          uri: '',
          enabled: '1',
          deleted: '1',
          total: 5
        },
        dialogTransferVisible: false,
        dialogeditVisible: false,
        formData: {
          name: '',
          uri: '/api',
          accessControlStyle: '02',
          enabled: 1,
          remarks: '',
          guid: '-1',
          parentGuid: 'top',
          resourceType: '1',
          sort: 0
        },
        TransferData: {
          allData: [],
          hasData: [],
          titles: ['未分配', '已分配'],
          guid: ''
        },
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.ENABLED(),
          this.$commonDicType.DELETED(),
          this.$commonDicType.RESOURCE_ACCESS_CONTROL_STYLE()
        ],
        rules: {
          name: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }],
          uri: [{
            required: true,
            message: '请输入标识符',
            trigger: 'blur'
          }]
        }
      }
    },
    watch: {},
    created() {},
    mounted() {
      this.loadAllOptions()
      this.loadTopMenus()
    },
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
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
      async loadTopMenus() {
        this.listLoading = false
        const response = await fetchTopMenuPage(this.listQuery)
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

        this.list = data.records
        this.listQuery.total = data.total
      },
      async loadChildren(row, treeNode, resolve) {
        this.treeMap.set(row.guid, {
          row,
          treeNode,
          resolve
        })
        const response = await fetchMenuAllChildren(row.guid)
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
        resolve(data)
      },
      handleEditPower(row) {
        this.dialogTransferVisible = true
        this.loadResourceRoleTransferData(row)
      },
      async loadResourceRoleTransferData(row) {
        this.listLoading = true
        const response = await getResourceRoleTransferData(row.guid)
        this.listLoading = false
        if (response.code !== 100) {
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
        this.TransferData.guid = row.guid
      },
      closeTransferDialog() {
        this.dialogTransferVisible = false
      },
      handlePageSizeChange(val) {
        if (val != this.listQuery.pageSize) {
          this.listQuery.pageSize = val
          this.loadTopMenus()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.listQuery.currentPage) {
          this.listQuery.currentPage = val
          this.loadTopMenus()
        }
      },
      indexMethod(index) {
        return this.listQuery.pageSize * (this.listQuery.currentPage - 1) + index + 1
      },
      async savePower() {
        var mapForSave = {
          resourceGuid: this.TransferData.guid,
          roleGuids: this.TransferData.hasData
        }
        this.listLoading = true
        const response = await saveResourcePower(mapForSave)
        this.listLoading = false
        if (response.code !== 100) {
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
      },
      submitEditForm() {
        this.$refs['elForm'].validate((valid) => {
          if (valid) {
            this.saveMenuForm()
          } else {
            console.log('未通过表单校验!!');
            return false;
          }
        });
      },
      async saveMenuForm() {
        this.listLoading = true
        const response = await saveMenu(this.formData)
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        if (this.formData.parentGuid === 'top') {
          this.loadTopMenus()
        } else {
          this.refreshTable(this.formData.parentGuid)
        }
      },
      colFormatter(row,column,cellValue,key){
          return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key),cellValue + '')
      },
      resetFormData() {
        // this.formData.guid = ''
        this.formData.name = ''
        this.formData.uri = ''
        this.formData.sort = 0
        this.formData.accessControlStyle = '02'
        this.formData.deleted = '1'
        this.formData.enabled = '1'
        this.formData.remarks = '无'
      },
      initFormData() {
        this.formData.guid = ''
        this.formData.parentGuid = 'top'
        this.formData.resourceType = '1'
        this.resetFormData()
      },
      handleClickAddButton() {
        this.initFormData()
        this.dialogeditVisible = true
      },
      handleEditMenu(row) {
        this.formData = this.$commonUtils.shadowCopy(row)
        this.dialogeditVisible = true
      },
      handleAddSub(row) {
        this.initFormData()
        this.formData.parentGuid = row.guid
        this.dialogeditVisible = true
      },
      handleDelRowConfirm(row) {
        this.$confirm('此操作将删除该行数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.handleDelRow(row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      async handleDelRow(row) {
        const guidContainer = {
          guid: row.guid
        }
        this.listLoading = true
        const response = await delResource(guidContainer)
        this.listLoading = false
        if (response.code !== 100) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        if (row.parentGuid === 'top') {
          this.loadTopMenus()
        } else {
          this.refreshTable(row.parentGuid)
        }
      },
      refreshTable(parentGuid) {
        // 根据父级id取出对应节点数据
        const {
          row,
          treeNode,
          resolve
        } = this.treeMap.get(parentGuid)
        this.$set(this.$refs.multipleTable.store.states.lazyTreeNodeMap, parentGuid, [])
        if (row) {
          this.loadChildren(row, treeNode, resolve)
        }
      },
    }
  }
</script>

<style scoped>

</style>
