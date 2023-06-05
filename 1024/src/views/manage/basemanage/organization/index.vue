<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2000-08-20 -->
<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="filterDrawer.dialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form
          ref="drawerForm"
          class="demo-form-inline"
          style="margin-top: 25px;margin-right: 20px;"
          :model="filterDrawer.formData"
        >
          <el-form-item label="机构编码" :label-width="filterDrawer.formLabelWidth" prop="code">
            <el-input
              v-model="filterDrawer.formData.code"
              placeholder="请输入机构编码"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="机构名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input
              v-model="filterDrawer.formData.name"
              placeholder="请输入机构名称"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="所属区划" :label-width="filterDrawer.formLabelWidth" prop="divCode">
            <el-cascader
              ref="cascaderAdminDivFilter"
              v-model="currentRegion"
              :props="regionCascaderProps"
              clearable
              style="width: 100%;"
              @change="defaultAdmDivFilterChangeEvent"
            />
          </el-form-item>
          <el-form-item label="启用状态" :label-width="filterDrawer.formLabelWidth" prop="enabled">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.ENABLED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="备注" :label-width="filterDrawer.formLabelWidth" prop="remarks">
            <el-input
              v-model="filterDrawer.formData.remarks"
              placeholder="请输入备注"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="是否末级" :label-width="filterDrawer.formLabelWidth" prop="leaf">
            <el-select v-model="filterDrawer.formData.leaf" placeholder="请选择是否末级" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.YESNO())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadTopLevel()">顶层</el-button>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadAllLevel()">查询</el-button>
            <el-button size="mini" type="primary" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置
            </el-button>
            <el-button size="mini" icon="el-icon-close" @click="hideDrawer()">关闭</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!--查询条件和查询按钮区结束-->
    <div ref="regionContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      当前行政区划:
      <el-cascader
        ref="cascaderAdminDiv"
        v-model="currentRegion"
        :props="regionCascaderProps"
        style="width: 600px;"
        @change="defaultAdmDivChangeEvent"
      />
    </div>
    <!--功能按钮区开始-->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="handleClickSearchButton()">
          查询
        </el-button>
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="mainTableData"
      stripe
      border
      highlight-current-row
      fit
      style="width: 100%"
      :header-cell-style="{'text-align':'center'}"
      row-key="guid"
      default-expand-all
      lazy
      :load="loadChildren"
      :tree-props="{hasChildren:'hasChildren'}"
    >
      <!--      <el-table-column prop="guid" label="主键" show-overflow-tooltip sortable></el-table-column>
 -->
      <el-table-column prop="code" label="机构编码" show-overflow-tooltip sortable align="left" />
      <el-table-column prop="name" label="机构名称" show-overflow-tooltip sortable />
      <el-table-column prop="divCode" label="所属区划" show-overflow-tooltip sortable />
      <el-table-column prop="divName" label="所属区划名称" show-overflow-tooltip sortable />
      <el-table-column
        prop="enabled"
        label="启用状态"
        show-overflow-tooltip
        sortable
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())"
      />
      <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
      <el-table-column
        prop="leaf"
        label="是否末级"
        show-overflow-tooltip
        sortable
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())"
      />
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="least" @click="handleClickEditButton(scope.row)">编辑</el-button>
          <el-button type="primary" size="least" @click="handleAddSubButton(scope.row)">添加</el-button>
          <el-button type="danger" size="least" @click="handleDelRowButton(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <div align="right">
      <!--分页控件开始-->
      <div ref="paginationContainer" style="text-align: center;">
        <el-pagination
          :current-page="filterDrawer.formData.currentPage"
          :page-sizes="[5,10,20,50,100,500]"
          :page-size="filterDrawer.formData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="filterDrawer.formData.total"
          @size-change="handlePageSizeChange"
          @current-change="handlePageCurrentChange"
        />
      </div>
      <!--分页控件结束-->
    </div>
    <!-- 表单区域开始 -->
    <el-dialog
      :visible.sync="mainDataForm.mainDataFormDialogVisible"
      :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle"
    >
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="机构编码" prop="code">
          <el-input
            v-model="mainDataForm.editingRecord.code"
            placeholder="请输入机构编码"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="机构名称" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.name"
            placeholder="请输入机构名称"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="所属区划" prop="divCode">
          {{ mainDataForm.editingRecord.divCode }}-{{ mainDataForm.editingRecord.divName }}
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-select v-model="mainDataForm.editingRecord.enabled" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.ENABLED())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="删除状态" prop="deleted">
          <el-select v-model="mainDataForm.editingRecord.deleted" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.DELETED())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input
            v-model="mainDataForm.editingRecord.remarks"
            placeholder="请输入备注"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="是否末级" prop="leaf">
          <el-select v-model="mainDataForm.editingRecord.leaf" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.YESNO())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="顺序号" prop="sort">
          <el-input v-model="mainDataForm.editingRecord.sort" placeholder="请输入顺序号" clearable :style="{width: '100%'}" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="mainDataForm.mainDataFormDialogVisible = false">
          关闭
        </el-button>
        <el-button type="warning" @click="submitMainDataForm()">
          保存
        </el-button>
        <el-button type="primary" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>
<script>
import {
  saveTbOrganization,
  delTbOrganization,
  fetchTopTbOrganizationPage,
  fetchTbOrganizationPage,
  fetchTbOrganizationAllChildren
} from '@/api/tb-organization-scene2'
import {
  fetchTbAdmDivChildrenNode,
  fetchTbAdmDivDefaultCascaderValue
} from '@/api/tb-adm-div-scene2.js'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
export default {
  name: 'TbOrganization',
  data() {
    const that = this
    return {
      listLoading: false,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          code: '',
          name: '',
          divCode: '',
          divName: '',
          enabled: '1',
          deleted: '1',
          remarks: '无',
          leaf: '0',
          sort: ''

        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        showTopOnly: true,
        formData: {
          guid: '',
          code: '',
          name: '',
          divCode: '',
          createBy: '',
          createTime: null,
          modifyBy: '',
          modifyTime: null,
          datestamp: null,
          enabled: '',
          deleted: '',
          remarks: '',
          leaf: '',
          parentGuid: '',
          sort: null,
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
      treeMap: new Map(),
      radio: false,
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED(),
        this.$commonDicType.YESNO()
      ],

      currentRegion: [],
      currentRegionLabel: '',
      regionCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.lazyLoadRegionData(node, resolve)
        }
      },
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入机构编码',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入机构名称',
          trigger: 'blur'
        }],
        divCode: [{
          required: true,
          message: '请输入所属区划',
          trigger: 'blur'
        }],
        createBy: [{
          required: true,
          message: '请输入记录创建者',
          trigger: 'blur'
        }],
        createTime: [{
          required: true,
          message: '请输入记录创建时间',
          trigger: 'blur'
        }],
        modifyBy: [{
          required: true,
          message: '请输入记录最后修改者',
          trigger: 'blur'
        }],
        modifyTime: [{
          required: true,
          message: '请输入记录最后修改时间',
          trigger: 'blur'
        }],
        datestamp: [{
          required: true,
          message: '请输入时间戳',
          trigger: 'blur'
        }],
        enabled: [{
          required: true,
          message: '请输入启用状态',
          trigger: 'blur'
        }],
        deleted: [{
          required: true,
          message: '请输入删除状态',
          trigger: 'blur'
        }],
        leaf: [{
          required: true,
          message: '请输入是否末级',
          trigger: 'blur'
        }],
        parentGuid: [{
          required: true,
          message: '请输入父级GUID',
          trigger: 'blur'
        }],
        sort: [{
          required: true,
          message: '请输入顺序号',
          trigger: 'blur'
        }]
      }
    }
  },
  watch: {},
  inject: ['reload'],
  created() {},
  mounted() {
    this.loadTbAdmDivDefaultCascaderValue()
    this.loadAllOptions()
  },
  methods: {
    refresh() {
      this.reload()
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    hideDrawer() {
      this.filterDrawer.dialogVisible = false
    },
    handleClickSearchButton() {
      this.filterDrawer.formData.divCode = this.getAdminDivCascaderCurrentValue()
      this.filterDrawer.dialogVisible = true
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
    handleLoadTopLevel() {
      this.filterDrawer.formData.divCode = this.getAdminDivCascaderFilterValue()
      this.filterDrawer.formData.currentPage = 1
      this.loadTopLevel()
    },
    loadTopLevelWhenMounted() {
      this.filterDrawer.formData.divCode = this.getAdminDivCascaderCurrentValue()
      this.loadTopLevel()
    },
    async loadTopLevel() {
      this.filterDrawer.showTopOnly = true
      this.listLoading = false
      const response = await fetchTopTbOrganizationPage(this.filterDrawer.formData)
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

      this.mainTableData = data.records
      this.filterDrawer.formData.total = data.total
    },
    handleLoadAllLevel() {
      this.filterDrawer.formData.divCode = this.getAdminDivCascaderFilterValue()
      this.filterDrawer.formData.currentPage = 1
      this.loadAllLevel()
    },
    async loadAllLevel() {
      this.filterDrawer.showTopOnly = false
      this.listLoading = false
      const response = await fetchTbOrganizationPage(this.filterDrawer.formData)
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

      this.mainTableData = data.records
      this.filterDrawer.formData.total = data.total
    },
    async loadChildren(row, treeNode, resolve) {
      this.treeMap.set(row.guid, {
        row,
        treeNode,
        resolve
      })
      const response = await fetchTbOrganizationAllChildren(row.guid)
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
    handlePageSizeChange(val) {
      if (val !== this.filterDrawer.formData.pageSize) {
        this.filterDrawer.formData.pageSize = val
        this.loadDataForTurnPage()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.filterDrawer.formData.currentPage) {
        this.filterDrawer.formData.currentPage = val
        this.loadDataForTurnPage()
      }
    },
    loadDataForTurnPage() {
      if (this.filterDrawer.showTopOnly) {
        this.loadTopLevel()
      } else {
        this.loadAllLevel()
      }
    },
    indexMethod(index) {
      return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1
    },
    submitMainDataForm() {
      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.saveMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async saveMainDataForm() {
      this.listLoading = true
      const response = await saveTbOrganization(this.mainDataForm.editingRecord)
      this.listLoading = false
      this.$message({
        message: response.message,
        type: 'warning'
      })
      if (this.mainDataForm.editingRecord.parentGuid === 'top') {
        this.loadTopLevel()
      } else {
        this.refreshTable(this.mainDataForm.editingRecord.parentGuid)
      }
    },
    colFormatter(row, column, cellValue, key) {
      return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue)
    },
    dateTimeColFormatter(row, column, cellValue) {
      return this.$commonUtils.dateTimeFormat(cellValue)
    },
    initFormData() {
      this.mainDataForm.editingRecord.guid = ''
      this.mainDataForm.editingRecord.parentGuid = 'top'
      this.mainDataForm.editingRecord.divCode = this.getAdminDivCascaderCurrentValue()
      this.mainDataForm.editingRecord.divName = this.getAdminDivCascaderCurrentLabel()

      this.currentRegionLabel = this.getAdminDivCascaderCurrentLabel()
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initFormData()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleClickEditButton(row) {
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = this.$commonUtils.shadowCopy(row)
      if (this.getAdminDivCascaderCurrentValue() !== this.mainDataForm.editingRecord.divCode) {
        this.mainDataForm.editingRecord.divCode = this.getAdminDivCascaderCurrentValue()
        this.mainDataForm.editingRecord.divName = this.getAdminDivCascaderCurrentLabel()
        this.currentRegionLabel = this.getAdminDivCascaderCurrentLabel()
        this.$message({
          message: '所属区划已自动更新，请保存数据。',
          type: 'warning'
        })
      }
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleAddSubButton(row) {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initFormData()
      this.mainDataForm.editingRecord.parentGuid = row.guid
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDelRowButton(row) {
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
        })
      })
    },
    async handleDelRow(row) {
      const guidContainer = {
        guid: row.guid
      }
      this.listLoading = true
      const response = await delTbOrganization(guidContainer)
      this.listLoading = false
      if (response.code !== 100) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      if (row.parentGuid === 'top') {
        this.loadTopLevel()
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
    lazyLoadRegionData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadRegionChildrenNode('top', resolve)
      } else {
        const {
          value
        } = node
        this.loadRegionChildrenNode(value, resolve)
      }
    },
    async loadRegionChildrenNode(code, resolve) {
      this.listLoading = true
      const response = await fetchTbAdmDivChildrenNode(code)
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

      // 通过调用resolve将子节点数据返回，通知组件数据加载完成
      resolve(data)
    },
    async loadTbAdmDivDefaultCascaderValue() {
      const response = await fetchTbAdmDivDefaultCascaderValue()
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
      this.currentRegion = data.value
      this.loadTopLevelWhenMounted()
    },
    getAdminDivCascaderCurrentLabel() {
      const checkedNode = this.$refs['cascaderAdminDiv'].getCheckedNodes()
      if (checkedNode.length === 1) {
        return checkedNode[0].label
      } else {
        return ''
      }
    },
    getAdminDivCascaderCurrentValue() {
      if (this.currentRegion.length > 0) {
        return this.currentRegion[this.currentRegion.length - 1]
      } else {
        return ''
      }
    },
    getAdminDivCascaderFilterValue() {
      const checkedNode = this.$refs['cascaderAdminDivFilter'].getCheckedNodes()
      if (checkedNode.length === 1) {
        return checkedNode[0].value
      } else {
        return ''
      }
    },
    defaultAdmDivChangeEvent(value) {
      this.filterDrawer.formData.divCode = this.getAdminDivCascaderCurrentValue()
      this.filterDrawer.formData.currentPage = 1
      this.loadTopLevel()
    },
    defaultAdmDivFilterChangeEvent(value) {
      this.handleLoadTopLevel()
    }
  }
}
</script>
<style>
  /*1.显示滚动条：当内容超出容器的时候，可以拖动：*/
  .el-drawer__body {
    overflow: auto;
    /* overflow-x: auto; */
  }

  /*2.隐藏滚动条，太丑了*/
  .el-drawer__container ::-webkit-scrollbar {
    display: none;
  }
</style>
