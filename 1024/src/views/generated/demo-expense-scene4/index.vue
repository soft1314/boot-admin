<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-4-13 13:53:18 -->
<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer
      :visible.sync="filterDrawer.dialogVisible"
      direction="rtl"
      title="请输入查询条件"
      :with-header="false"
      size="30%"
    >
      <div class="demo-drawer__content">
        <el-form
          ref="drawerForm"
          class="demo-form-inline"
          style="margin-top: 25px;margin-right: 20px;"
          :model="filterDrawer.formData"
        >
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
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleQueryButton()">查询</el-button>
            <el-button size="mini" type="warning" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置
            </el-button>
            <el-button size="mini" icon="el-icon-close" @click="hideDrawer()">关闭</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!-- 查询抽屉结束 -->
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-add" @click="handleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
          查询
        </el-button>
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table
        v-loading="loading"
        :data="mainTableData"
        border
        fit
        style="width: 100%"
        max-height="500"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="主键">
                <span>{{ props.row.guid }}</span>
              </el-form-item>
              <el-form-item label="标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="报销金额">
                <span>{{ props.row.money }}</span>
              </el-form-item>
              <el-form-item label="情况说明">
                <span>{{ props.row.note }}</span>
              </el-form-item>
              <el-form-item label="业务日期">
                <span>{{ $commonUtils.dateTimeFormat(props.row.bizDate) }}</span>
              </el-form-item>
              <el-form-item label="报销人">
                <span>{{ props.row.empName }}</span>
              </el-form-item>
              <el-form-item label="职称">
                <span>{{ props.row.techTitle }}</span>
              </el-form-item>
              <el-form-item label="员工级别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.EMP_LEVEL()),props.row.empLevel) }}</span>
              </el-form-item>
              <el-form-item label="性别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.SEX()),props.row.sex) }}</span>
              </el-form-item>
              <el-form-item label="流转状态">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.FLOW_STATUS()),props.row.flowStatus) }}</span>
              </el-form-item>
              <el-form-item label="流程定义ID">
                <span>{{ props.row.processDefinitionId }}</span>
              </el-form-item>
              <el-form-item label="流程实例ID">
                <span>{{ props.row.processInstanceId }}</span>
              </el-form-item>
              <el-form-item label="记录创建者">
                <span>{{ props.row.createBy }}</span>
              </el-form-item>
              <el-form-item label="记录创建时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.createTime) }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改者">
                <span>{{ props.row.modifyBy }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.modifyTime) }}</span>
              </el-form-item>
              <el-form-item label="时间戳">
                <span>{{ $commonUtils.dateTimeFormat(props.row.datestamp) }}</span>
              </el-form-item>
              <el-form-item label="启用状态">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.ENABLED()),props.row.enabled) }}</span>
              </el-form-item>
              <el-form-item label="删除状态">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.DELETED()),props.row.deleted) }}</span>
              </el-form-item>
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
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
        <el-table-column prop="createBy" label="记录创建者" show-overflow-tooltip sortable />
        <el-table-column prop="createTime" label="记录创建时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
        <el-table-column prop="modifyBy" label="记录最后修改者" show-overflow-tooltip sortable />
        <el-table-column prop="modifyTime" label="记录最后修改时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
        <el-table-column prop="datestamp" label="时间戳" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
        <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
        <el-table-column prop="deleted" label="删除状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())" />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="180">
          <template slot-scope="scope">
            <el-button v-if="scope.row.flowStatus=='00'" size="least" type="primary" @click="handleEditRow(scope.row)">修改</el-button>
            <el-button v-if="scope.row.flowStatus=='00'" size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
            <el-button v-if="scope.row.flowStatus!='00'" size="least" type="success" @click="handleDisplay(scope.row)">流程跟踪</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 分页组件开始 -->
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
    <!-- 分页组件结束 -->
    <!-- 表数据编辑对话框区开始 -->
    <el-dialog
      :visible.sync="mainDataForm.mainDataFormDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle"
    >
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
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
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSubmitMainDataForm()">
          保存
        </el-button>
        <el-button type="warning" @click="resetForm('mainEditForm')">
          重置
        </el-button>
        <el-button type="primary" @click="handleSubmitMainDataFormAndStartProcess()">
          保存并提交
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
    <IFrame ref="displayComponent" />
  </div>
</template>
<script>
import IFrame from '@/views/manage/operationmanage/task/flowLog'
import {
  fetchDemoExpensePage,
  saveDemoExpense,
  delDemoExpense,
  saveAndStartProcessDemoExpense
} from '@/api/generated/demo-expense-scene4'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'DemoExpense',
  components: {
    IFrame
  },
  data() {
    const that = this
    return {
      loading: true,
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
          deleted: '1',
          remarks: '',
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
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
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.loadAllOptions()
    this.getMainTableData()
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
    handlePageSizeChange(val) {
      if (val !== this.filterDrawer.formData.pageSize) {
        this.filterDrawer.formData.pageSize = val
        this.getMainTableData()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.filterDrawer.formData.currentPage) {
        this.filterDrawer.formData.currentPage = val
        this.getMainTableData()
      }
    },
    indexMethod(index) {
      return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    showDrawer() {
      this.filterDrawer.dialogVisible = true
    },
    hideDrawer() {
      this.filterDrawer.dialogVisible = false
    },
    handleQueryButton() {
      this.filterDrawer.formData.currentPage = 1
      this.getMainTableData()
    },
    async getMainTableData() {
      this.loading = false
      const response = await fetchDemoExpensePage(this.filterDrawer.formData)
      this.loading = false
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
    handleEditRow(row) {
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = row
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelDemoExpense(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelDemoExpense(guid) {
      const guidVO = {
        guid
      }
      const result = await delDemoExpense(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.getMainTableData()
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initmainDataForm()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    initmainDataForm() {
      this.mainDataForm.editingRecord.guid = ''
      // this.resetFormData()
    },
    handleSubmitMainDataForm() {
      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    handleSubmitMainDataFormAndStartProcess() {
      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataFormAndStartProcess()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    handleDisplay(row) {
      if (row.processInstanceId === null || row.processInstanceId === '') {
        this.$message({
          message: '数据未提交到流程',
          type: 'warning'
        })
      } else {
        this.$nextTick(() => {
          this.$refs.displayComponent.setSrc('/displayModel.html?processInstanceId=' + row.processInstanceId + '&nocaching=' +
                            new Date().getTime())
          this.$refs.displayComponent.loadLog(row.processInstanceId)
          this.$refs.displayComponent.showDialog()
        })
      }
    },
    async submitMainDataForm() {
      const response = await saveDemoExpense(this.mainDataForm.editingRecord)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      if (this.mainDataForm.mainDataFormDialogTitle === '修改') {
        this.getMainTableData()
        this.mainDataForm.mainDataFormDialogVisible = false
      }
    },
    async submitMainDataFormAndStartProcess() {
      const response = await saveAndStartProcessDemoExpense(this.mainDataForm.editingRecord)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.getMainTableData()
      this.mainDataForm.mainDataFormDialogVisible = false
    },
    handleCloseMainDataFormDialog() {
      this.getMainTableData()
      this.mainDataForm.mainDataFormDialogVisible = false
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
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 190px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        text-align: left;
        margin-right: 0;
        margin-bottom: 0;
        width: 100%;
    }
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
