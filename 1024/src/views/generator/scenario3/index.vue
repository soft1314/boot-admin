<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 t-->
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
          <el-form-item label="名称" :label-width="filterDrawer.formLabelWidth" prop="tableName">
            <el-input
              v-model="filterDrawer.formData.tableName"
              placeholder="请输入表或视图名称"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="类别" :label-width="filterDrawer.formLabelWidth" prop="tableType">
            <el-select v-model="filterDrawer.formData.tableType" placeholder="请选择对象类别" size="mini">
              <el-option
                v-for="item in optionMap.get($commonDicType.ORACLE_OBJ_TYPE())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="描述" :label-width="filterDrawer.formLabelWidth" prop="tableComments">
            <el-input
              v-model="filterDrawer.formData.tableComments"
              placeholder="请输入对象描述"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="数据源" :label-width="filterDrawer.formLabelWidth" prop="dataSource">
            <el-select v-model="filterDrawer.formData.dataSource" placeholder="请选择数据源" size="mini">
              <el-option
                v-for="item in options.dataSourceOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="getMainTableData()">查询</el-button>
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
      <el-table
        v-loading="loading"
        :data="mainTableData"
        border
        fit
        highlight-current-row
        style="width: 100%"
        max-height="500"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="名称">
                <span>{{ props.row.tablename }}</span>
              </el-form-item>
              <el-form-item label="类别">
                <span>{{ props.row.tabletype }}</span>
              </el-form-item>
              <el-form-item label="描述">
                <span>{{ props.row.comments }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column prop="tablename" label="名称" show-overflow-tooltip sortable />
        <el-table-column
          prop="tabletype"
          label="类别"
          show-overflow-tooltip
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ORACLE_OBJ_TYPE())"
          sortable
        />
        <el-table-column prop="comments" label="描述" show-overflow-tooltip sortable />
        <el-table-column align="center" label="检查操作" min-width="70">
          <template slot-scope="scope">
            <el-button size="least" type="success" @click="handleGenerateButton(scope.row.tablename,'STORY')">职能
            </el-button>
            <el-button size="least" type="success" @click="handleGenerateButton(scope.row.tablename,'CHECK')">预检
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="代码生成操作" min-width="80">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'FRONT-END')">前端
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'BACKEND')">后端
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Liquibase')">
              ChangeSet</el-button>
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
    <!-- 对话框区开始 -->
    <el-dialog
      :visible.sync="mainDataForm.formDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.formDialogTitle"
    >
      <div v-if="mainDataForm.needFileName">
        <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
          {{ mainDataForm.editingRecord.fullFileName }}</span>
        <br><br>
      </div>
      <el-input v-model="mainDataForm.editingRecord.sourceCode" type="textarea" :rows="20" readonly />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button v-if="mainDataForm.needFileName" type="primary" @click="handleSaveFileButton()">
          写文件
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="mainDataForm.frontEndDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.formDialogTitle"
    >
      <el-tabs tab-position="left" value="api">
        <el-tab-pane label="API" name="api">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.frontEnd.apiFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.apiSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="录入表单">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.frontEnd.addFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.addSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="编辑表单">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.frontEnd.editFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.editSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="查看表单">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.frontEnd.viewFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.viewSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
      </el-tabs>
      <br><br>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button @click="handleCloseMainDataFormFrontEndDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveAllFrontEndFileButton()">
          全部写文件
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="mainDataForm.backEndDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.formDialogTitle"
    >
      <el-tabs tab-position="left" value="startVO">
        <el-tab-pane label="StartVO" name="startVO">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.startVOFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.startVOSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="CompleteVO">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.completeVOFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.completeVOSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="Mapstruct">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.mapstructFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.mapstructSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="Trans">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.transFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.transSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="Service">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.serviceFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.serviceSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="Controller">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.controllerFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.controllerSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
      </el-tabs>
      <br><br>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button @click="handleCloseMainDataFormBackEndDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveAllBackEndFileButton()">
          全部写文件
        </el-button>
      </div>
    </el-dialog>
    <!-- 对话框区结束 -->
  </div>
</template>
<script>
import {
  generateScene3TableCheckResult,
  generateScene3Story
} from '@/api/scene3-checker'
import {
  generateScene3JsApiSource,
  generateScene3JsApiFile
} from '@/api/scene3-js-api-generator'
import {
  generateScene3EditVueSource,
  generateScene3EditVueFile
} from '@/api/scene3-edit-vue-generator'
import {
  generateScene3AddVueSource,
  generateScene3AddVueFile
} from '@/api/scene3-add-vue-generator'
import {
  generateScene3ViewVueSource,
  generateScene3ViewVueFile
} from '@/api/scene3-view-vue-generator'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
import {
  fetchTableAndViewsPage
} from '@/api/generatormp'
import {
  generateScene3CompleteVoSource,
  generateScene3CompleteVoFile
} from '@/api/scene3-complete-vo-generator'
import {
  generateScene3StartVoSource,
  generateScene3StartVoFile
} from '@/api/scene3-start-vo-generator'
import {
  generateScene3MapstructSource,
  generateScene3MapstructFile
} from '@/api/scene3-mapstruct-generator'
import {
  generateScene3ServiceSource,
  generateScene3ServiceFile
} from '@/api/scene3-service-generator'
import {
  generateScene3TransactionSource,
  generateScene3TransactionFile
} from '@/api/scene3-transaction-generator'
import {
  generateScene3ControllerSource,
  generateScene3ControllerFile
} from '@/api/scene3-controller-generator'
import {
  generateScene3LiquibaseSource,
  generateScene3LiquibaseFile
} from '@/api/scene3-liquibase-generator'

export default {
  name: 'Scenario3',
  components: {},
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          sourceCode: '',
          tableName: '',
          sourceType: '',
          fullFileName: '',
          frontEnd: {
            apiSource: '',
            apiFileName: '',
            addSource: '',
            addFileName: '',
            editSource: '',
            editFileName: '',
            viewSource: '',
            viewFileName: ''
          },
          backEnd: {
            startVOSource: '',
            startVOFileName: '',
            completeVOSource: '',
            completeVOFileName: '',
            mapstructSource: '',
            mapstructFileName: '',
            transSource: '',
            transFileName: '',
            serviceSource: '',
            serviceFileName: '',
            controllerSource: '',
            controllerFileName: ''
          }
        },
        formDialogVisible: false,
        frontEndDialogVisible: false,
        backEndDialogVisible: false,
        formDialogTitle: '查看代码',
        needFileName: true
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          tableName: '',
          tableType: 'TABLE',
          tableComments: '',
          dataSource: 'master',
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.ORACLE_OBJ_TYPE()
      ],
      options: {
        tableTypeOptions: []
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
    colFormatter(row, column, cellValue, key) {
      return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue + '')
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
    handleCloseMainDataFormDialog() {
      this.mainDataForm.formDialogVisible = false
    },
    handleCloseMainDataFormFrontEndDialog() {
      this.mainDataForm.frontEndDialogVisible = false
    },
    handleCloseMainDataFormBackEndDialog() {
      this.mainDataForm.backEndDialogVisible = false
    },
    async getMainTableData() {
      this.loading = false
      const response = await fetchTableAndViewsPage(this.filterDrawer.formData)
      if (response.code !== 100) {
        this.loading = false
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
      this.loading = false
    },

    handleGenerateButton(tableName, cmd) {
      this.mainDataForm.editingRecord.tableName = tableName
      this.mainDataForm.editingRecord.sourceType = cmd
      if (cmd === 'STORY') {
        this.asyncFetchScene3Story(tableName)
        this.mainDataForm.formDialogTitle = '车间职能介绍'
        this.mainDataForm.formDialogVisible = true
        this.mainDataForm.needFileName = false
      }
      if (cmd === 'CHECK') {
        this.asyncFetchCheckResult(tableName)
        this.mainDataForm.formDialogTitle = '表结构检查'
        this.mainDataForm.formDialogVisible = true
        this.mainDataForm.needFileName = false
      }
      if (cmd === 'FRONT-END') {
        this.asyncFetchJsApiSource(tableName)
        this.asyncFetchAddVueSource(tableName)
        this.asyncFetchEditVueSource(tableName)
        this.asyncFetchViewVueSource(tableName)
        this.mainDataForm.formDialogTitle = '前端代码生成'
        this.mainDataForm.frontEndDialogVisible = true
      }
      if (cmd === 'BACKEND') {
        this.asyncFetchStartVoSource(tableName)
        this.asyncFetchCompleteVoSource(tableName)
        this.asyncFetchMapstructSource(tableName)
        this.asyncFetchServiceSource(tableName)
        this.asyncFetchTransactionSource(tableName)
        this.asyncFetchControllerSource(tableName)
        this.mainDataForm.formDialogTitle = '后端代码生成'
        this.mainDataForm.backEndDialogVisible = true
      }
      if (cmd === 'Liquibase') {
        this.asyncFetchLiquibaseSource(tableName)
        this.mainDataForm.formDialogTitle = 'Liquibase changeSet生成'
        this.mainDataForm.formDialogVisible = true
        this.mainDataForm.needFileName = true
      }
    },
    async asyncFetchJsApiSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3JsApiSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.apiSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.apiFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchAddVueSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3AddVueSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.addSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.addFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchEditVueSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3EditVueSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.editSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.editFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchViewVueSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3ViewVueSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.viewSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.viewFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchCheckResult(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3TableCheckResult(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
        this.mainDataForm.editingRecord.fullFileName = ''
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchScene3Story(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3Story(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
        this.mainDataForm.editingRecord.fullFileName = ''
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleSaveFileButton() {
      if (this.mainDataForm.editingRecord.sourceType === 'API') {
        this.generateJsApiFile()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'ADD') {
        this.generateAddVueFile()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'EDIT') {
        this.generateEditVueFile()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'VIEW') {
        this.generateViewVueFile()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'CHECK') {
        this.$message({
          type: 'info',
          message: '检测信息仅供阅读!'
        })
      }
      if (this.mainDataForm.editingRecord.sourceType === 'STORY') {
        this.$message({
          type: 'info',
          message: '车间职能介绍仅供阅读!'
        })
      }
      if (this.mainDataForm.editingRecord.sourceType === 'StartVO') {
        this.handleSaveStartVoFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'CompleteVO') {
        this.handleSaveCompleteVoFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'Mapstruct') {
        this.handleSaveMapstructFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'Service') {
        this.handleSaveServiceFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'Trans') {
        this.handleSaveTransactionFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'Controller') {
        this.handleSaveControllerFileButton()
      }
      if (this.mainDataForm.editingRecord.sourceType === 'Liquibase') {
        this.handleSaveLiquibaseFileButton()
      }
    },
    handleSaveAllFrontEndFileButton() {
      this.generateJsApiFile()
      this.generateAddVueFile()
      this.generateEditVueFile()
      this.generateViewVueFile()
    },
    handleSaveAllBackEndFileButton() {
      this.handleSaveStartVoFileButton()
      this.handleSaveCompleteVoFileButton()
      this.handleSaveMapstructFileButton()
      this.handleSaveServiceFileButton()
      this.handleSaveTransactionFileButton()
      this.handleSaveControllerFileButton()
    },
    async generateJsApiFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3JsApiFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('API JS文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateJsApiFileOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateJsApiFileOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3JsApiFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateAddVueFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3AddVueFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('录入表单VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateAddVueFileOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateEditVueFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3EditVueFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('编辑表单VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateEditVueFileOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateViewVueFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3ViewVueFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('查看表单VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateViewVueFileOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateAddVueFileOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3AddVueFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateEditVueFileOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3EditVueFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateViewVueFileOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3ViewVueFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchStartVoSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3StartVoSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.startVOSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.startVOFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveStartVoFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3StartVoFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('流程启动StartVO文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateStartVoOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateStartVoOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3StartVoFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchCompleteVoSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3CompleteVoSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.completeVOSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.completeVOFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveCompleteVoFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3CompleteVoFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('事项办理CompleteVO文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateCompleteVoOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateCompleteVoOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3CompleteVoFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },

    async asyncFetchMapstructSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3MapstructSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.mapstructSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.mapstructFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveMapstructFileButton() {
      debugger
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3MapstructFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('Mapstruct文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateMapstructOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateMapstructOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3MapstructFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },

    async asyncFetchServiceSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3ServiceSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.serviceSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.serviceFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },

    async asyncFetchTransactionSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3TransactionSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.transSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.transFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveServiceFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3ServiceFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('Service文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateServiceOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async handleSaveTransactionFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3TransactionFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('全局事务Trans文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateTransactionOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateServiceOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3ServiceFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateTransactionOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3TransactionFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchControllerSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3ControllerSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.controllerSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.controllerFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveControllerFileButton() {
      debugger
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3ControllerFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('Controller文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateControllerOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateControllerOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3ControllerFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    // /////////////////
    async asyncFetchLiquibaseSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene3LiquibaseSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
        this.mainDataForm.editingRecord.fullFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveLiquibaseFileButton() {
      debugger
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene3LiquibaseFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('Liquibase文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateLiquibaseOverwrite()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          })
        })
      } else {
        this.$message({
          message: result.message,
          type: 'warning'
        })
      }
    },
    async generateLiquibaseOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene3LiquibaseFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    }

  }
}
</script>
<style>
</style>
