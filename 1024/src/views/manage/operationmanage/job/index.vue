<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--功能按钮区开始-->
    <div class="cl pd-5 bg-1 bk-gray">
      <div align="left" style="float:left">
        <el-button size="mini" type="primary" @click="search()">查询</el-button>
        <el-button size="mini" type="primary" @click="handleadd()">添加</el-button>
      </div>
      <div align="right">
        <!--分页控件开始-->
        <div style="align:right">
          <el-pagination
            :current-page="BaseTableData.page.currentPage"
            :page-sizes="[5,10,20,50,100,500]"
            :page-size="BaseTableData.page.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="BaseTableData.page.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageCurrentChange"
          />
        </div>
        <!--分页控件结束-->
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--表格开始-->
    <el-table max-height="100%" :data="BaseTableData.table" style="width: 100%" :border="true">
      <el-table-column type="index" :index="indexMethod" />
      <el-table-column prop="jobName" label="任务名称" width="100px" />
      <el-table-column prop="jobGroup" label="任务所在组" width="100px" />
      <el-table-column prop="jobClassName" label="任务类名" />
      <el-table-column prop="cronExpression" label="表达式" width="120" />
      <el-table-column prop="timeZoneId" label="时区" width="120" />
      <el-table-column prop="startTime" label="开始" width="120" :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"/>
      <el-table-column prop="nextFireTime" label="下次" width="120" :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"/>
      <el-table-column prop="previousFireTime" label="上次" width="120" :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"/>

      <el-table-column prop="triggerState" label="状态" width="80">
        <template slot-scope="scope">
          <p v-if="scope.row.triggerState=='NORMAL'">等待</p>
          <p v-if="scope.row.triggerState=='PAUSED'">暂停</p>
          <p v-if="scope.row.triggerState=='NONE'">删除</p>
          <p v-if="scope.row.triggerState=='COMPLETE'">结束</p>
          <p v-if="scope.row.triggerState=='ERROR'">错误</p>
          <p v-if="scope.row.triggerState=='BLOCKED'">阻塞</p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220px">
        <template slot-scope="scope">
          <el-button type="warning" size="least" title="挂起" @click="handlePause(scope.row)">挂起</el-button>
          <el-button type="primary" size="least" title="恢复" @click="handleResume(scope.row)">恢复</el-button>
          <el-button type="danger" size="least" title="删除" @click="handleDelete(scope.row)">删除</el-button>
          <el-button type="success" size="least" title="修改" @click="handleUpdate(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--表格结束-->

    <!--主表单弹出窗口开始-->
    <el-dialog
      v-cloak
      title="维护"
      :visible.sync="InputBaseInfoDialogData.dialogVisible"
      :close-on-click-modal="InputBaseInfoDialogData.showCloseButton"
      top="5vh"
      :show-close="InputBaseInfoDialogData.showCloseButton"
      :fullscreen="InputBaseInfoDialogData.dialogFullScreen"
    >
      <!--弹窗头部header开始-->
      <div slot="title" style="margin-bottom: 10px">
        <div align="left" style="float:left">
          <h3>定时任务管理</h3>
        </div>
        <div align="right">
          <el-button type="text" title="全屏显示" @click="resizeInputBaseInfoDialogMax()"><i class="el-icon-arrow-up" /></el-button>
          <el-button type="text" title="以弹出窗口形式显示" @click="resizeInputBaseInfoDialogNormal()"><i class="el-icon-arrow-down" /></el-button>
          <el-button type="text" title="关闭" @click="closeInputBaseInfoDialog()"><i class="el-icon-error" /></el-button>
        </div>
      </div>
      <!--弹窗头部header结束-->

      <!--弹窗表单开始-->
      <el-form
        ref="InputBaseInfoForm"
        :status-icon="InputBaseInfoDialogData.statusIcon"
        :model="InputBaseInfoDialogData.data"
        class="demo-ruleForm"
      >
        <el-form-item label="原任务名称" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="jobName">
          {{ InputBaseInfoDialogData.data.oldJobName }}【修改任务时使用】
        </el-form-item>
        <el-form-item label="原任务分组" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="jobGroup">
          {{ InputBaseInfoDialogData.data.oldJobGroup }}【修改任务时使用】
        </el-form-item>

        <el-form-item label="任务名称" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="jobName">
          <el-input v-model="InputBaseInfoDialogData.data.jobName" auto-complete="off" />
        </el-form-item>
        <el-form-item label="任务分组" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="jobGroup">
          <el-input v-model="InputBaseInfoDialogData.data.jobGroup" auto-complete="off" />
        </el-form-item>
        <el-form-item label="类名" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="jobClassName">
          <el-input v-model="InputBaseInfoDialogData.data.jobClassName" auto-complete="off" />
        </el-form-item>
        <el-form-item label="表达式" :label-width="InputBaseInfoDialogData.formLabelWidth" prop="cronExpression">
          <el-input v-model="InputBaseInfoDialogData.data.cronExpression" auto-complete="off" />
        </el-form-item>
      </el-form>
      <!--弹窗表单结束-->

      <!--弹窗尾部footer开始-->
      <div slot="footer" class="dialog-footer">
        <!--<el-button type="warning" v-on:click="resetForm('InputBaseInfoForm')">重 查</el-button>-->
        <el-button type="primary" @click="saveInputBaseInfoForm()">保 存</el-button>
      </div>
      <!--弹窗尾部footer开始-->
    </el-dialog>
    <!--弹出窗口结束-->

    <!--查看场所弹出窗口开始-->
    <el-dialog
      v-cloak
      title="修改任务"
      :visible.sync="ViewBaseInfoDialogData.dialogVisible"
      :close-on-click-modal="ViewBaseInfoDialogData.showCloseButton"
      top="5vh"
      :show-close="ViewBaseInfoDialogData.showCloseButton"
      :fullscreen="ViewBaseInfoDialogData.dialogFullScreen"
    >
      <!--弹窗头部header开始-->
      <div slot="title" style="margin-bottom: 10px">
        <div align="left" style="float:left">
          <h3>修改任务</h3>
        </div>
        <div align="right">
          <el-button type="text" @click="dialogResize('ViewBaseInfoDialog',true)"><i class="el-icon-arrow-up" title="全屏显示" /></el-button>
          <el-button type="text" @click="dialogResize('ViewBaseInfoDialog',false)"><i
            class="el-icon-arrow-down"
            title="以弹出窗口形式显示"
          /></el-button>
          <el-button type="text" @click="dialogClose('ViewBaseInfoDialog')"><i class="el-icon-error" title="关闭" /></el-button>
        </div>
      </div>
      <!--弹窗头部header结束-->

      <!--弹窗表单开始-->
      <el-form
        ref="ViewBaseInfoForm"
        :status-icon="ViewBaseInfoDialogData.statusIcon"
        :model="ViewBaseInfoDialogData.data"
        class="demo-ruleForm"
      >
        <el-form-item label="表达式" :label-width="ViewBaseInfoDialogData.formLabelWidth" prop="cronExpression">
          {{ this.BaseTableData.currentRow.cronExpression }}
        </el-form-item>
      </el-form>
      <!--弹窗表单结束-->
    </el-dialog>
  </div>
</template>

<script>
import {
  getBlankJob,
  fetchJobPage,
  getUpdateObject,
  saveJob,
  pauseJob,
  resumeJob,
  deleteJob
} from '@/api/job'

export default {
  name: 'Jobmanage',
  data: function() {
    return {
      /**
         * 后台服务忙，防止重复提交的控制变量
         * */
      ServiceRunning: false,
      /**
         *表格和分页组件
         * */
      BaseTableData: {
        currentRow: {},
        page: {
          currentPage: 1,
          pageSize: 20,
          pageNum: 1,
          pages: 1,
          size: 5,
          total: 1
        },
        /**
           *主表格数据
           * */
        table: [],
        /**
           *勾选选中的数据
           * */
        selected: []
      },
      InputBaseInfoDialogData: {
        data: {},
        dialogVisible: false,
        dialogFullScreen: false,
        formLabelWidth: '180px',
        showCloseButton: false,
        statusIcon: true
      },
      ViewBaseInfoDialogData: {
        cronExpression: '',
        dialogVisible: false,
        dialogFullScreen: true,
        formLabelWidth: '180px'
      }
    }
  },
  /**
     *初始化自动执行查询表格数据--不用调整
     **/
  mounted: function() {
    this.loadTableData()
  },
  methods: {
    /**
       * 查询---------根据实际调整参数
       */
    async loadTableData() {
      if (this.ServiceRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = true
      const response = await fetchJobPage(this.BaseTableData.page)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.BaseTableData.page.total = data.total
      this.BaseTableData.table = data.records
      this.ServiceRunning = false
    },
    /**
       * 每页大小调整事件
       * @param val
       */
    handlePageSizeChange(val) {
      if (val != this.BaseTableData.page.pageSize) {
        this.BaseTableData.page.pageSize = val
        this.loadTableData()
      }
    },
    /**
       * 当前面号调整事件
       * @param val
       */
    handlePageCurrentChange(val) {
      if (val != this.BaseTableData.page.currentPage) {
        this.BaseTableData.page.currentPage = val
        this.loadTableData()
      }
    },
    dialogResize(dialogName, toMax) {
      VFC_dialogResize(dialogName, toMax)
    },
    resizeInputBaseInfoDialogMax() {
      this.InputBaseInfoDialogData.dialogFullScreen = true
    },
    resizeInputBaseInfoDialogNormal() {
      this.InputBaseInfoDialogData.dialogFullScreen = false
    },
    dialogClose(dialogName) {
      // VFC_dialogClose(dialogName);
      // this.loadTableData();
    },
    closeInputBaseInfoDialog() {
      this.InputBaseInfoDialogData.dialogVisible = false
      this.loadTableData()
    },
    async getBlankForm() {
      const response = await getBlankJob()
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response

      this.InputBaseInfoDialogData.data = data
    },
    async getUpdateForm(row) {
      const response = await getUpdateObject(row)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response

      this.InputBaseInfoDialogData.data = data
    },
    // 弹出对话框
    handleadd() {
      this.getBlankForm()
      this.InputBaseInfoDialogData.dialogVisible = true
    },
    handleUpdate(row) {
      if (row.triggerState !== 'PAUSED') {
        this.$message({
          message: '请先挂起任务，再修改。',
          type: 'warning'
        })
        return
      }
      this.getUpdateForm(row)
      this.InputBaseInfoDialogData.dialogVisible = true
    },
    search() {
      this.loadTableData()
    },
    /**
       * 提交修改主表单
       */
    async saveInputBaseInfoForm() {
      if (this.ServiceRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = true
      const response = await saveJob(this.InputBaseInfoDialogData.data)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = false
      this.$message({
        message: '数据保存成功。',
        type: 'success'
      })
      this.loadTableData()
    },
    async handlePause(row) {
      if (this.ServiceRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = true
      const response = await pauseJob(row)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = false
      this.$message({
        message: '任务成功挂起。',
        type: 'success'
      })
      this.loadTableData()
    },
    async handleResume(row) {
      if (this.ServiceRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = true
      const response = await resumeJob(row)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = false
      this.$message({
        message: '任务成功恢复。',
        type: 'success'
      })
      this.loadTableData()
    },
    async handleDelete(row) {
      if (row.triggerState !== 'PAUSED') {
        this.$message({
          message: '请先挂起任务，再删除。',
          type: 'warning'
        })
        return
      }
      if (this.ServiceRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = true
      const response = await deleteJob(row)
      if (response.code !== 100) {
        this.ServiceRunning = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.ServiceRunning = false
      this.$message({
        message: '任务成功删除。',
        type: 'success'
      })
      this.loadTableData()
    },
    indexMethod(index) {
      return this.BaseTableData.page.pageSize * (this.BaseTableData.page.currentPage - 1) + index + 1
    },
    dateTimeColFormatter(row, column, cellValue) {
      return this.$commonUtils.dateTimeFormat(cellValue)
    },
  }
}
</script>

<style>

</style>
