<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-4-13 13:51:21 -->
<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="filterDrawer.dialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form ref="drawerForm" class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" :model="filterDrawer.formData">
          <el-form-item
            label="主键"
            :label-width="filterDrawer.formLabelWidth"
            prop="guid"
          >
            <el-input
              v-model="filterDrawer.formData.guid"
              placeholder="请输入主键"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="标题"
            :label-width="filterDrawer.formLabelWidth"
            prop="title"
          >
            <el-input
              v-model="filterDrawer.formData.title"
              placeholder="请输入标题"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="报销金额"
            :label-width="filterDrawer.formLabelWidth"
            prop="money"
          >
            <el-input
              v-model="filterDrawer.formData.money"
              placeholder="请输入报销金额"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="情况说明"
            :label-width="filterDrawer.formLabelWidth"
            prop="note"
          >
            <el-input
              v-model="filterDrawer.formData.note"
              placeholder="请输入情况说明"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="业务日期" prop="bizDate" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.bizDate" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item
            label="报销人"
            :label-width="filterDrawer.formLabelWidth"
            prop="empName"
          >
            <el-input
              v-model="filterDrawer.formData.empName"
              placeholder="请输入报销人"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="职称" :label-width="filterDrawer.formLabelWidth" prop="techTitle">
            <el-cascader ref="techTitleQueryCascaderId" :props="techTitleCascaderProps" clearable style="width: 100%;" @change="techTitleQueryCascaderChangeEvent" />
            {{ filterDrawer.formData.techTitle }}
          </el-form-item>
          <el-form-item
            label="员工级别"
            :label-width="filterDrawer.formLabelWidth"
            prop="empLevel"
          >
            <el-select
              v-model="filterDrawer.formData.empLevel"
              placeholder="请选择员工级别"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.EMP_LEVEL())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="性别"
            :label-width="filterDrawer.formLabelWidth"
            prop="sex"
          >
            <el-select
              v-model="filterDrawer.formData.sex"
              placeholder="请选择性别"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.SEX())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="流转状态"
            :label-width="filterDrawer.formLabelWidth"
            prop="flowStatus"
          >
            <el-select
              v-model="filterDrawer.formData.flowStatus"
              placeholder="请选择流转状态"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.FLOW_STATUS())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="流程定义ID"
            :label-width="filterDrawer.formLabelWidth"
            prop="processDefinitionId"
          >
            <el-input
              v-model="filterDrawer.formData.processDefinitionId"
              placeholder="请输入流程定义ID"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="流程实例ID"
            :label-width="filterDrawer.formLabelWidth"
            prop="processInstanceId"
          >
            <el-input
              v-model="filterDrawer.formData.processInstanceId"
              placeholder="请输入流程实例ID"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="记录创建者"
            :label-width="filterDrawer.formLabelWidth"
            prop="createBy"
          >
            <el-input
              v-model="filterDrawer.formData.createBy"
              placeholder="请输入记录创建者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录创建时间" prop="createTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.createTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item
            label="记录最后修改者"
            :label-width="filterDrawer.formLabelWidth"
            prop="modifyBy"
          >
            <el-input
              v-model="filterDrawer.formData.modifyBy"
              placeholder="请输入记录最后修改者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录最后修改时间" prop="modifyTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.modifyTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="时间戳" prop="datestamp" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.datestamp" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item
            label="启用状态"
            :label-width="filterDrawer.formLabelWidth"
            prop="enabled"
          >
            <el-select
              v-model="filterDrawer.formData.enabled"
              placeholder="请选择启用状态"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.ENABLED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="删除状态"
            :label-width="filterDrawer.formLabelWidth"
            prop="deleted"
          >
            <el-select
              v-model="filterDrawer.formData.deleted"
              placeholder="请选择删除状态"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.DELETED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="备注"
            :label-width="filterDrawer.formLabelWidth"
            prop="remarks"
          >
            <el-input
              v-model="filterDrawer.formData.remarks"
              placeholder="请输入备注"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadTopLevel()">顶层</el-button>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadAllLevel()">查询</el-button>
            <el-button size="mini" type="warning" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置</el-button>
            <el-button size="mini" icon="el-icon-close" @click="hideDrawer()">关闭</el-button>
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-add" @click="handleClickAddButton()">
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
      v-loading="listLoading"
      :data="mainTableData"
      stripe
      border
      highlight-current-row
      fit
      style="width: 100%"
      ref="multipleTable"
      :header-cell-style="{'text-align':'center'}"
      row-key="guid"
      default-expand-all
      lazy
      :load="loadChildren"
      :tree-props="{hasChildren:'hasChildren'}"
    >
      <el-table-column prop="guid" label="主键" show-overflow-tooltip sortable />
      <el-table-column prop="title" label="标题" show-overflow-tooltip sortable />
      <el-table-column prop="money" label="报销金额" show-overflow-tooltip sortable />
      <el-table-column prop="note" label="情况说明" show-overflow-tooltip sortable />
      <el-table-column prop="bizDate" label="业务日期" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="empName" label="报销人" show-overflow-tooltip sortable />
      <el-table-column prop="techTitle" label="职称" show-overflow-tooltip sortable />
      <el-table-column prop="empLevel" label="员工级别" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.EMP_LEVEL())" />
      <el-table-column prop="sex" label="性别" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.SEX())" />
      <el-table-column prop="flowStatus" label="流转状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.FLOW_STATUS())" />
      <el-table-column prop="processDefinitionId" label="流程定义ID" show-overflow-tooltip sortable />
      <el-table-column prop="processInstanceId" label="流程实例ID" show-overflow-tooltip sortable />
      <el-table-column prop="createBy" label="记录创建者" show-overflow-tooltip sortable />
      <el-table-column prop="createTime" label="记录创建时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="modifyBy" label="记录最后修改者" show-overflow-tooltip sortable />
      <el-table-column prop="modifyTime" label="记录最后修改时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="datestamp" label="时间戳" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
      <el-table-column prop="deleted" label="删除状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())" />
      <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
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
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="mainDataForm.editingRecord.title"
            placeholder="请输入标题"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="报销金额" prop="money">
          <el-input
            v-model="mainDataForm.editingRecord.money"
            placeholder="请输入报销金额"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="情况说明" prop="note">
          <el-input
            v-model="mainDataForm.editingRecord.note"
            placeholder="请输入情况说明"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="业务日期" prop="bizDate">
          <el-date-picker v-model="mainDataForm.editingRecord.bizDate" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="报销人" prop="empName">
          <el-input
            v-model="mainDataForm.editingRecord.empName"
            placeholder="请输入报销人"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="职称" prop="techTitle">
          <el-cascader
            ref="techTitleEditCascaderId"
            v-model="cascaderValue.techTitle"
            :props="techTitleCascaderProps"
            style="width: 50%;"
            @change="techTitleEditCascaderChangeEvent"
          />
          {{ mainDataForm.editingRecord.techTitle }}
        </el-form-item>
        <el-form-item label="员工级别" prop="empLevel">
          <el-select v-model="mainDataForm.editingRecord.empLevel" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.EMP_LEVEL())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="mainDataForm.editingRecord.sex" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.SEX())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="流转状态" prop="flowStatus">
          <el-select v-model="mainDataForm.editingRecord.flowStatus" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.FLOW_STATUS())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="流程定义ID" prop="processDefinitionId">
          <el-input
            v-model="mainDataForm.editingRecord.processDefinitionId"
            placeholder="请输入流程定义ID"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="流程实例ID" prop="processInstanceId">
          <el-input
            v-model="mainDataForm.editingRecord.processInstanceId"
            placeholder="请输入流程实例ID"
            clearable
            :style="{width: '100%'}"
          />
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="mainDataForm.mainDataFormDialogVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="submitMainDataForm()">
          保存
        </el-button>
        <el-button type="warning" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>
<script>
import {
  saveDemoExpense,
  delDemoExpense,
  fetchTopDemoExpensePage,
  fetchDemoExpensePage,
  fetchDemoExpenseAllChildren
} from '@/api/generated/demo-expense-scene2'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'DemoExpense',
  data() {
    const that = this
    return {
      listLoading: false,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          title: '',
          money: '',
          note: '',
          bizDate: null,
          empName: '',
          techTitle: '',
          empLevel: '',
          sex: '',
          flowStatus: '',
          processDefinitionId: '',
          processInstanceId: '',
          enabled: '1',
          deleted: '1',
          remarks: '无'

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
          title: '',
          money: '',
          note: '',
          bizDate: '',
          empName: '',
          techTitle: '',
          empLevel: '',
          sex: '',
          flowStatus: '',
          processDefinitionId: '',
          processInstanceId: '',
          createBy: '',
          createTime: '',
          modifyBy: '',
          modifyTime: '',
          datestamp: '',
          enabled: '',
          deleted: '',
          remarks: '',
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
        this.$commonDicType.EMP_LEVEL(),
        this.$commonDicType.SEX(),
        this.$commonDicType.FLOW_STATUS(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      techTitleCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.techTitleLazyLoadData(node, resolve)
        }
      },
      cascaderValue: {
        techTitle: []
      },
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        money: [{
          required: true,
          message: '请输入报销金额',
          trigger: 'blur'
        }],
        note: [{
          required: true,
          message: '请输入情况说明',
          trigger: 'blur'
        }],
        bizDate: [{
          required: true,
          message: '请输入业务日期',
          trigger: 'blur'
        }],
        empName: [{
          required: true,
          message: '请输入报销人',
          trigger: 'blur'
        }],
        techTitle: [{
          required: true,
          message: '请输入职称',
          trigger: 'blur'
        }],
        empLevel: [{
          required: true,
          message: '请输入员工级别',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '请输入性别',
          trigger: 'blur'
        }],
        flowStatus: [{
          required: true,
          message: '请输入流转状态',
          trigger: 'blur'
        }],
        processDefinitionId: [{
          required: true,
          message: '请输入流程定义ID',
          trigger: 'blur'
        }],
        processInstanceId: [{
          required: true,
          message: '请输入流程实例ID',
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
        }]
      }
    }
  },
  watch: {},
  inject: ['reload'],
  created() {},
  mounted() {
    this.loadAllOptions()
    this.loadTopLevel()
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
      this.filterDrawer.formData.currentPage = 1
      this.loadTopLevel()
    },
    async loadTopLevel() {
      this.filterDrawer.showTopOnly = true
      this.listLoading = false
      const response = await fetchTopDemoExpensePage(this.filterDrawer.formData)
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
      this.filterDrawer.formData.currentPage = 1
      this.loadAllLevel()
    },
    async loadAllLevel() {
      this.filterDrawer.showTopOnly = false
      this.listLoading = false
      const response = await fetchDemoExpensePage(this.filterDrawer.formData)
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
      const response = await fetchDemoExpenseAllChildren(row.guid)
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
      const response = await saveDemoExpense(this.mainDataForm.editingRecord)
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
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initFormData()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleClickEditButton(row) {
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = this.$commonUtils.shadowCopy(row)
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
      const response = await delDemoExpense(guidContainer)
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
    techTitleLazyLoadData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), 'top', resolve)
      } else {
        const {
          value
        } = node
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), value, resolve)
      }
    },
    techTitleEditCascaderChangeEvent(value) {
      const code = value[value.length - 1]
      const name = this.$refs.techTitleEditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
      this.mainDataForm.editingRecord.techTitle = code + '_' + name
    },
    techTitleQueryCascaderChangeEvent(value) {
      if (value.length === 0) {
        this.filterDrawer.formData.techTitle = ''
      } else {
        const code = value[value.length - 1]
        const name = this.$refs.techTitleQueryCascaderId.getCheckedNodes()[0].pathLabels.join('/')
        this.filterDrawer.formData.techTitle = code + '_' + name
      }
    },
    async loadLazyCodeNode(dicType, code, resolve) {
      this.listLoading = true
      const response = await lazyFetchDictionaryNode(dicType, code)
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
    .el-drawer__container ::-webkit-scrollbar{
        display: none;
    }
</style>
