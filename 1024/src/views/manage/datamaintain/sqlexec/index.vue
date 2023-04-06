<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <div style="text-align:center;height:50px">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="FilterData.keyword" placeholder="请输入关键字" size="medium" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="FilterData.flowAudit" placeholder="流程节点">
            <el-option v-for="item in FilterOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" type="success" icon="el-icon-search" @click="loadTableData()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div class="cl pd-5 bg-1 bk-gray">
      <div align="left" style="float:left" />
      <div align="right">
        <!--分页控件开始-->
        <div style="align:right">
          <el-pagination
            :current-page="FilterData.currentPage"
            :page-sizes="[5,10,20,50,100,500]"
            :page-size="FilterData.pageSize"
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

    <!--列表开始-->
    <el-table
      v-loading="loading"
      max-height="410"
      :data="BaseTableData.table"
      style="width: 100%"
      :border="true"
      @selection-change="handleTableSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column prop="sqlType" label="类别" />
      <el-table-column prop="tableName" label="表名" />
      <el-table-column prop="sqlContent" label="SQL主体" />
      <el-table-column prop="flowAudit" label="流程" :formatter="WorkFlowStateConverterLocal" />
      <el-table-column prop="resultContent" label="执行结果" width="400" />
      <el-table-column prop="insertOn" label="录入时间" />
      <el-table-column prop="status" label="状态" :formatter="ValidFormatterLocal" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" title="查看" @click="viewBaseInfo(scope.row)"><i class="el-icon-view" /></el-button>
          <el-button
            v-if="scope.row.flowAudit === 0 && scope.row.status === 1"
            type="text"
            title="驳回"
            @click="revokeSql(scope.row)"
          ><i class="el-icon-circle-close" /></el-button>
          <el-button
            v-if="scope.row.flowAudit === 0 && scope.row.status === 1"
            type="text"
            title="执行"
            @click="execmodify(scope.row)"
          ><i class="el-icon-setting" /></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <!--主显示表单弹出窗口开始-->
    <el-dialog
      v-cloak
      title="信息显示"
      :visible.sync="ShowBaseInfoDialogData.dialogVisible"
      :close-on-click-modal="false"
      top="5vh"
      :show-close="true"
      :fullscreen="ShowBaseInfoDialogData.dialogFullScreen"
    >
      <!--弹窗表单开始-->
      <el-form class="demo-ruleForm">
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">SQL说明：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.remark }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">SQL类型：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">
              {{ this.BaseTableData.currentRow.sqlType }}
            </div>
          </el-col>
        </el-row>
        <el-row v-if="this.BaseTableData.currentRow.sqlType !== 'select'" :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">表名：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.tableName }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">SQL命令：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.sqlContent }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">执行结果：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.resultContent }}</div>
          </el-col>
        </el-row>
        <el-row v-if="this.BaseTableData.currentRow.sqlType !== 'select'" :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">备份结果：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.bakResultContent }}</div>
          </el-col>
        </el-row>
        <el-row v-if="this.BaseTableData.currentRow.sqlType !== 'select'" :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">匹配记录数：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.affectRowCount }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">录入时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.insertOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">修改时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.updateOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">提交时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.submitOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">驳回时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.revokeOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">执行时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.execOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">失效时间：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.deleteOn }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">流程状态：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.flowAudit }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">状态：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.status }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">录入人员：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.operator }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">执行人员：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.execOperator }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">删除人员：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.deleteOperator }}</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="grid-content bg-purple" align="right">时间戳：</div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content bg-purple">{{ this.BaseTableData.currentRow.dateStamp }}</div>
          </el-col>
        </el-row>
      </el-form>
      <!--弹窗表单结束-->
    </el-dialog>
    <!--弹出窗口结束-->
    <!--主EDIT表单弹出窗口开始-->
    <el-dialog
      v-cloak
      title="维护"
      :visible.sync="EditBaseInfoDialogData.dialogVisible"
      :close-on-click-modal="false"
      top="5vh"
      :show-close="false"
      :fullscreen="EditBaseInfoDialogData.dialogFullScreen"
    >
      <!--弹窗头部header开始-->
      <div slot="title" style="margin-bottom: 10px">
        <div align="left" style="float:left">
          <h3>SQL信息修改</h3>
        </div>
        <div align="right">
          <el-button type="text" @click="dialogResize('EditBaseInfoDialog',true)"><i class="el-icon-arrow-up" title="全屏显示" /></el-button>
          <el-button type="text" @click="dialogResize('EditBaseInfoDialog',false)"><i
            class="el-icon-arrow-down"
            title="以弹出窗口形式显示"
          /></el-button>
          <el-button type="text" @click="dialogClose('EditBaseInfoDialog')"><i class="el-icon-error" title="关闭" /></el-button>
        </div>
      </div>
      <!--弹窗头部header结束-->

      <!--弹窗表单开始-->
      <el-form
        ref="EditBaseInfoForm"
        :status-icon="true"
        :model="EditBaseInfoDialogData.data"
        :rules="EditBaseInfoFormRules"
        class="demo-ruleForm"
      >
        <el-form-item label="SQL类型" :label-width="EditBaseInfoDialogData.formLabelWidth" prop="sqlType">
          <el-radio-group v-model="EditBaseInfoDialogData.data.sqlType">
            <el-radio :label="'select'">select</el-radio>
            <el-radio :label="'insert'">insert</el-radio>
            <el-radio :label="'update'">update</el-radio>
            <el-radio :label="'delete'">delete</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          v-if="EditBaseInfoDialogData.data.sqlType !== 'select'"
          label="表名"
          :label-width="EditBaseInfoDialogData.formLabelWidth"
          prop="tableName"
        >
          <el-input v-model="EditBaseInfoDialogData.data.tableName" auto-complete="off" />
        </el-form-item>
        <el-form-item label="SQL" :label-width="EditBaseInfoDialogData.formLabelWidth" prop="sqlContent">
          <el-input
            v-model="EditBaseInfoDialogData.data.sqlContent"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入SQL内容。select录入完整SQL；update从set开始录入【例：set name='abc' where id=1】；insert从表名后面开始录入【例：(id,guid,name) values (dept_code_seq.nextval,sys_guid(),'abc'）】；delete从where开始录入【例：where id=1】。"
            auto-complete="off"
          />
        </el-form-item>
        <el-form-item label="SQL说明" :label-width="EditBaseInfoDialogData.formLabelWidth" prop="remark">
          <el-input
            v-model="EditBaseInfoDialogData.data.remark"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入SQL的功能等说明性信息。"
            auto-complete="off"
          />
        </el-form-item>
      </el-form>
      <!--弹窗表单结束-->

      <!--弹窗尾部footer开始-->
      <div slot="footer" class="dialog-footer">
        <el-button type="warning" @click="reloadEditBaseInfoDialogData()">重查</el-button>
        <el-button type="primary" @click="submitEditBaseInfoForm('EditBaseInfoForm')">保存</el-button>
      </div>
      <!--弹窗尾部footer开始-->
    </el-dialog>
    <!--主EDIT表单弹出窗口结束-->
    <!--SQL执行结果显示弹出窗口开始-->
    <el-dialog
      v-cloak
      title="SQL执行结果显示"
      :visible.sync="ShowBaseInfoDialogData.sqlResultVisible"
      :close-on-click-modal="true"
      top="5vh"
      :show-close="true"
      :fullscreen="true"
    >

      {{ ShowBaseInfoDialogData.sqlResultContent }}
    </el-dialog>
    <!--弹出窗口结束-->

  </div>
</template>

<script>
import {
  getSysAnySqlPage,
  revokeMySql,
  execMySql
} from '@/api/manage'
import {

} from '@/api/dictionary'
export default {
  name: 'Sqlexec',
  data() {
    return {
      loading: false,
      AxiosRunning: false,
      FilterOptions: [{
        value: '-1',
        label: '未提交'
      }, {
        value: '0',
        label: '办理中'
      }, {
        value: '2',
        label: '被驳回'
      }, {
        value: '6',
        label: '已终审'
      }, {
        value: '',
        label: '全部'
      }],
      /**
         *查询条件输入域数据---------根据实际调整
         * */
      FilterData: {
        currentPage: 1,
        pageSize: 5,

        keyword: '',
        flowAudit: '0',
        execMode: true
      },
      /**
         *表格和分页组件
         * */
      BaseTableData: {
        page: {
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
        selected: [],
        /**
           * 当前正在处理的行
           * */
        currentRow: {}
      },
      /**
         *主显示表单数据
         * */
      ShowBaseInfoDialogData: {
        dialogVisible: false,
        dialogFullScreen: true,
        formLabelWidth: '80px',
        sqlResultVisible: false,
        sqlResultContent: ''
      },
      /**
         *主表单数据---------根据实际调整
         * */
      EditBaseInfoDialogData: {
        data: {
          id: 0,
          sqlType: 'select'
        },
        dialogVisible: false,
        dialogFullScreen: true,
        formLabelWidth: '180px'
      },
      EditBaseInfoFormRules: {
        suppOrgcode: [{
          required: true,
          message: '统一社会信息代码不能为空',
          trigger: 'blur'
        }],
        suppChinaname: [{
          required: true,
          message: '供应商名称不能为空',
          trigger: 'blur'
        }]
      }
    }
  },
  mounted: function() {
    this.loadTableData()
  },
  methods: {
    /**
       * 每页大小调整事件
       * @param val
       */
    handlePageSizeChange: function(val) {
      if (val != this.BaseTableData.page.pageSize) {
        this.BaseTableData.page.pageSize = val
        this.loadTableData()
      }
    },
    /**
       * 当前面号调整事件
       * @param val
       */
    handlePageCurrentChange: function(val) {
      if (val != this.FilterData.currentPage) {
        this.FilterData.currentPage = val
        this.loadTableData()
      }
    },
    /**
       * 查询
       */
    async loadTableData() {
      if (this.AxiosRunning) {
        this.$message({
          message: '请不要重复点击',
          type: 'warning'
        })
        return
      }
      this.AxiosRunning = true
      const response = await getSysAnySqlPage(this.FilterData)
      if (response.code !== 100) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.AxiosRunning = false
        return
      }
      const {
        data
      } = response
      this.AxiosRunning = false
      this.BaseTableData.table = data.records
      this.BaseTableData.page.total = data.total
    },
    /**
       * 代码转换显示
       * */
    formatterColumn: function(row, column) {
      return ValidFormatter(row.validState)
    },
    /**
       * 打开显示信息的对话框
       * */
    viewBaseInfo: function(row) {
      this.BaseTableData.currentRow = row
      this.ShowBaseInfoDialogData.dialogVisible = true
    },
    /**
       * 打开修改信息的对话框
       * */
    editBaseInfo: function(row) {
      this.EditBaseInfoDialogData.data = VFC_mapCopyReferSrc(row, this.EditBaseInfoDialogData.data)
      this.EditBaseInfoDialogData.dialogVisible = true
    },
    addBaseInfo: function() {
      VFC_mapClear(this.EditBaseInfoDialogData.data)
      this.EditBaseInfoDialogData.data.id = 0
      this.EditBaseInfoDialogData.data.sqlType = 'select'
      this.EditBaseInfoDialogData.dialogVisible = true
    },
    async revokeSql(row) {
      if (row.id === 0) {
        this.$message({
          message: '请先保存。',
          type: 'warning'
        })
        return
      }
      if (this.AxiosRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.AxiosRunning = true

      const response = await revokeMySql(row.id)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.AxiosRunning = false
      this.loadTableData()
    },
    async execmodify(row) {
      if (row.id === 0) {
        this.$message({
          message: '请先保存。',
          type: 'warning'
        })
        return
      }
      if (this.AxiosRunning) {
        this.$message({
          message: '请不要重复点击。',
          type: 'warning'
        })
        return
      }
      this.AxiosRunning = true
      const response = await execMySql(row.id)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.AxiosRunning = false
      this.loadTableData()
    },
    /**
       * 改变对话框显示模式（最大化，正常）
       * */
    dialogResize: function(dialogName, toMax) {
      VFC_dialogResize(dialogName, toMax)
    },
    /**
       * 关闭（隐藏）对话框
       * */
    dialogClose: function(dialogName) {
      VFC_dialogClose(dialogName)
    },
    /**
       * 勾选事件(本页面没用，要求表格为多选模式)
       * @param val
       */
    handleTableSelectionChange: function(val) {
      this.BaseTableData.selected = val
    },
    WorkFlowStateConverterLocal: function(row) {
      if (row.flowAudit == '-1') {
        return '未提交'
      } else if (row.flowAudit == '0') {
        return '办理中'
      } else if (row.flowAudit == '2') {
        return '被驳回'
      } else if (row.flowAudit == '6') {
        return '已终审'
      } else {
        return '全部'
      }
    },
    ValidFormatterLocal: function(row) {
      if (row.status == '1') {
        return '有效'
      } else if (row.status == '0') {
        return '无效'
      } else {
        return '未知'
      }
    }
  }

}
</script>

<style>
</style>
