<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2000-08-20 -->
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
          <el-form-item label="标题" :label-width="filterDrawer.formLabelWidth" prop="title">
            <el-input
              v-model="filterDrawer.formData.title"
              placeholder="请输入标题"
              size="mini"
              prefix-icon="el-icon-search"
              clearable
            />
          </el-form-item>
          <el-form-item label="流程名称" :label-width="filterDrawer.formLabelWidth" prop="processDefinitionName">
            <el-input
              v-model="filterDrawer.formData.processDefinitionName"
              placeholder="请输入流程名称"
              size="mini"
              prefix-icon="el-icon-search"
              clearable
            />
          </el-form-item>
          <el-form-item label="节点名称" :label-width="filterDrawer.formLabelWidth" prop="taskName">
            <el-input
              v-model="filterDrawer.formData.taskName"
              placeholder="请输入节点名称"
              size="mini"
              prefix-icon="el-icon-search"
              clearable
            />
          </el-form-item>
          <el-form-item label="收件时间" :label-width="filterDrawer.formLabelWidth" prop="taskCreated">
            <el-date-picker
              v-model="filterDrawer.formData.taskCreated"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
            />
          </el-form-item>
          <el-form-item label="办结时间" :label-width="filterDrawer.formLabelWidth" prop="taskCompleted">
            <el-date-picker
              v-model="filterDrawer.formData.taskCompleted"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
            />
          </el-form-item>
          <el-form-item label="流程在途" :label-width="filterDrawer.formLabelWidth" prop="processFinish">
            <el-radio-group v-model="filterDrawer.formData.processFinish">
              <el-radio :label="3">全部</el-radio>
              <el-radio :label="6">已结束</el-radio>
              <el-radio :label="9">流转中</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleQueryButton()">查询</el-button>
            <el-button size="mini" type="primary" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置
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
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
          查询
        </el-button>
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table v-loading="loading" :data="mainTableData" border fit style="width: 100%" max-height="500">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="主键">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="结果">
                <span>{{ props.row.outcome }}</span>
              </el-form-item>
              <el-form-item label="意见">
                <span>{{ props.row.message }}</span>
              </el-form-item>
              <el-form-item label="全部意见">
                <span>{{ props.row.fullMessage }}</span>
              </el-form-item>
              <el-form-item label="KEY">
                <span>{{ props.row.key }}</span>
              </el-form-item>
              <el-form-item label="description">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="formKey">
                <span>{{ props.row.formKey }}</span>
              </el-form-item>
              <el-form-item label="assignee">
                <span>{{ props.row.assignee }}</span>
              </el-form-item>
              <el-form-item label="processInstanceId">
                <span>{{ props.row.processInstanceId }}</span>
              </el-form-item>
              <el-form-item label="processDefinitionId">
                <span>{{ props.row.processDefinitionId }}</span>
              </el-form-item>
              <el-form-item label="executionId">
                <span>{{ props.row.executionId }}</span>
              </el-form-item>
              <el-form-item label="业务ID">
                <span>{{ props.row.businessId }}</span>
              </el-form-item>
              <el-form-item label="业务表">
                <span>{{ props.row.businessTable }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column prop="title" label="标题" show-overflow-tooltip sortable />
        <el-table-column prop="processName" label="流程名称" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="节点名称" show-overflow-tooltip sortable />
        <el-table-column prop="outcome" label="结果" show-overflow-tooltip sortable />
        <el-table-column
          prop="createTime"
          label="收件时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="endTime"
          label="办结时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column prop="description" label="流程描述" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="success" @click="handleTracePhoto(scope.row)">流图</el-button>
            <el-button size="least" type="success" @click="handleDisplay(scope.row)">跟踪</el-button>
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
      :visible.sync="photoDialog.dialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="photoDialog.dialogTitle"
    >
      <div class="demo-image__placeholder">
        <div class="block">
          <el-image :src="pngsrc" />
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClosePhotoViewDialog()">
          关闭
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
  fetchAllHisPage
} from '@/api/workflow-task'
import {
  fetchTracePhoto
} from '@/api/workflow-download'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'HisTask',
  components: {
    IFrame
  },
  data() {
    return {
      loading: true,
      mainTableData: [],
      pngsrc: '',
      photoDialog: {
        dialogVisible: false,
        dialogTitle: ''
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          title: '',
          processDefinitionName: '',
          taskName: '',
          taskCreated: [],
          taskCompleted: [],
          processFinish: 3,
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      cascaderValue: {},
      fileList: [],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      rules: {
        id: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        deploymentId: [{
          required: true,
          message: '请输入部署编号',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入模型名称',
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
      const response = await fetchAllHisPage(this.filterDrawer.formData)
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
    async handleCompleteTask(row) {
      this.$nextTick(() => {
        this.$refs.dynaAuditFormComponent.setFormKey(row.formKey)
        this.$refs.dynaAuditFormComponent.setBusinessId(row.businessId)
        this.$refs.dynaAuditFormComponent.setTaskId(row.id)
        this.$refs.dynaAuditFormComponent.setProcessInstanceId(row.processInstanceId)
        this.$refs.dynaAuditFormComponent.showDialog()
      })
    },
    async handleTracePhoto_old(row) {
      this.listLoading = true
      fetchTracePhoto(row.processInstanceId).then(request => {
        const blob = new Blob([request.data], {
          type: 'image/png'
        })
        const link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = '流程图'
        link.target = '_blank'
        link.style.display = 'none'
        link.click()
      })
      this.listLoading = false
      this.$message({
        message: '数据导出成功',
        type: 'success'
      })
    },
    async handleTracePhoto(row) {
      this.listLoading = true
      const response = await fetchTracePhoto(row.processInstanceId)
      this.listLoading = false
      this.pngsrc = this.$commonUtils.genObjectURL(response.data)
      this.photoDialog.dialogVisible = true
    },
    handleDisplay(row) {
      this.$nextTick(() => {
        this.$refs.displayComponent.setSrc('/displayModel.html?processInstanceId=' + row.processInstanceId + '&nocaching=' +
            new Date().getTime())
        this.$refs.displayComponent.loadLog(row.processInstanceId)
        this.$refs.displayComponent.showDialog()
      })
    },
    handleClosePhotoViewDialog() {
      this.photoDialog.dialogVisible = false
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
  .el-drawer__container ::-webkit-scrollbar {
    display: none;
  }
</style>
