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
          生成文件
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="mainDataForm.frontEndDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.formDialogTitle"
    >
      <el-tabs tab-position="left" value="vue">
        <el-tab-pane label="VUE" name="vue">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.frontEnd.vueFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.vueSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="API">
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
        <el-tab-pane label="ROUTER">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以下路由片断仅供阅读</span><br><br>
          <el-input
            v-model="mainDataForm.editingRecord.frontEnd.routerSource"
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
        <el-button v-if="mainDataForm.needFileName" type="primary" @click="handleSaveAllFrontEndFileButton()">
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
      <el-tabs tab-position="left" value="queryVO">
        <el-tab-pane label="QueryVO" name="queryVO">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.queryVOFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.queryVOSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="DataSaveVO">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.dataSaveVOFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.dataSaveVOSource"
            style="width: 80%;"
            type="textarea"
            :rows="15"
            readonly
          />
        </el-tab-pane>
        <el-tab-pane label="DataRespVO">
          <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
            {{ mainDataForm.editingRecord.backEnd.dataRespVOFileName }}</span>
          <br><br>
          <el-input
            v-model="mainDataForm.editingRecord.backEnd.dataRespVOSource"
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
  generateScene2TableCheckResult,
  generateScene2Story
} from '@/api/scene2-checker'
import {
  generateScene2JsApiSource,
  generateScene2JsApiFile
} from '@/api/scene2-js-api-generator'
import {
  generateScene2VueSource,
  generateScene2VueFile
} from '@/api/scene2-vue-generator'
import {
  generateScene2VueRouterSource
} from '@/api/scene2-vuerouter-generator'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
import {
  fetchTableAndViewsPage
} from '@/api/generatormp'
import {
  generateScene2DataSaveVoSource,
  generateScene2DataSaveVoFile,
  generateScene2DataRespVoSource,
  generateScene2DataRespVoFile
} from '@/api/scene2-data-vo-generator'
import {
  generateScene2QueryVoSource,
  generateScene2QueryVoFile
} from '@/api/scene2-query-vo-generator'
import {
  generateScene2MapstructSource,
  generateScene2MapstructFile
} from '@/api/scene2-mapstruct-generator'
import {
  generateScene2ServiceSource,
  generateScene2ServiceFile
} from '@/api/scene2-service-generator'
import {
  generateScene2ControllerSource,
  generateScene2ControllerFile
} from '@/api/scene2-controller-generator'
import {
  generateScene2LiquibaseSource,
  generateScene2LiquibaseFile
} from '@/api/scene2-liquibase-generator'

export default {
  name: 'Scenario2',
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
            vueSource: '',
            vueFileName: '',
            apiSource: '',
            apiFileName: '',
            routerSource: '',
            routerFileName: ''
          },
          backEnd: {
            queryVOSource: '',
            queryVOFileName: '',
            dataSaveVOSource: '',
            dataSaveVOFileName: '',
            dataRespVOSource: '',
            dataRespVOFileName: '',
            mapstructSource: '',
            mapstructFileName: '',
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
        this.asyncFetchScene2Story(tableName)
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
        this.asyncFetchVueSource(tableName)
        this.asyncFetchJsApiSource(tableName)
        this.asyncFetchVueRouterSource(tableName)
        this.mainDataForm.formDialogTitle = '前端代码生成'
        this.mainDataForm.frontEndDialogVisible = true
        this.mainDataForm.needFileName = true
      }
      if (cmd === 'BACKEND') {
        this.asyncFetchQueryVoSource(tableName)
        this.asyncFetchDataSaveVoSource(tableName)
        this.asyncFetchDataRespVoSource(tableName)
        this.asyncFetchMapstructSource(tableName)
        this.asyncFetchServiceSource(tableName)
        this.asyncFetchControllerSource(tableName)
        this.mainDataForm.formDialogTitle = '后端代码生成'
        this.mainDataForm.backEndDialogVisible = true
        this.mainDataForm.needFileName = true
      }
      if (cmd === 'Liquibase') {
        this.asyncFetchLiquibaseSource(tableName)
        this.mainDataForm.formDialogTitle = 'Liquibase changeSet生成'
        this.mainDataForm.formDialogVisible = true
        this.mainDataForm.needFileName = true
      }
    },
    async asyncFetchVueSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2VueSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.vueSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.vueFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchJsApiSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2JsApiSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.apiSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.apiFileName = result.data.fileName
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
      const result = await generateScene2TableCheckResult(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
        this.mainDataForm.editingRecord.fullFileName = ''
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchVueRouterSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2VueRouterSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.frontEnd.routerSource = result.data.sourceCode
        this.mainDataForm.editingRecord.frontEnd.routerFileName = ''
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchScene2Story(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2Story(para)
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
      if (this.mainDataForm.editingRecord.sourceType === 'CHECK') {
        this.$message({
          type: 'info',
          message: '检测信息仅供阅读!'
        })
      }
      if (this.mainDataForm.editingRecord.sourceType === 'ROUTER') {
        this.$message({
          type: 'info',
          message: 'ROUTER片断仅供阅读!'
        })
      }
      if (this.mainDataForm.editingRecord.sourceType === 'STORY') {
        this.$message({
          type: 'info',
          message: '车间职能介绍仅供阅读!'
        })
      }

      if (this.mainDataForm.editingRecord.sourceType === 'Liquibase') {
        this.handleSaveLiquibaseFileButton()
      }
    },
    handleSaveAllFrontEndFileButton() {
      this.generateVueFile()
      this.generateJsApiFile()
      this.$message({
        type: 'info',
        message: 'ROUTER片断仅供阅读!'
      })
    },
    handleSaveAllBackEndFileButton() {
      this.handleSaveQueryVoFileButton()
      this.handleSaveDataSaveVoFileButton()
      this.handleSaveDataRespVoFileButton()
      this.handleSaveMapstructFileButton()
      this.handleSaveServiceFileButton()
      this.handleSaveControllerFileButton()
    },
    async generateJsApiFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2JsApiFile(para)
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
      const result = await generateScene2JsApiFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateVueFile() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2VueFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateVueFileOverwrite()
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
    async generateVueFileOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene2VueFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchQueryVoSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2QueryVoSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.queryVOSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.queryVOFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveQueryVoFileButton() {
      debugger
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2QueryVoFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('QueryVO文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateQueryVoOverwrite()
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
    async generateQueryVoOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene2QueryVoFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchDataSaveVoSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2DataSaveVoSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.dataSaveVOSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.dataSaveVOFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchDataRespVoSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2DataRespVoSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.dataRespVOSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.dataRespVOFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveDataSaveVoFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2DataSaveVoFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('保存DataVO文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateDataSaveVoOverwrite()
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
    async handleSaveDataRespVoFileButton() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2DataRespVoFile(para)
      if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() === result.code) {
        this.$confirm('响应DataVO文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.generateDataRespVoOverwrite()
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
    async generateDataSaveVoOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene2DataSaveVoFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async generateDataRespVoOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene2DataRespVoFile(para)
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
      const result = await generateScene2MapstructSource(para)
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
      const result = await generateScene2MapstructFile(para)
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
      const result = await generateScene2MapstructFile(para)
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
      const result = await generateScene2ServiceSource(para)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.mainDataForm.editingRecord.backEnd.serviceSource = result.data.sourceCode
        this.mainDataForm.editingRecord.backEnd.serviceFileName = result.data.fileName
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async handleSaveServiceFileButton() {
      debugger
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: false
      }
      const result = await generateScene2ServiceFile(para)
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
    async generateServiceOverwrite() {
      const para = {
        tableName: this.mainDataForm.editingRecord.tableName,
        overwrite: true
      }
      const result = await generateScene2ServiceFile(para)
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
      const result = await generateScene2ControllerSource(para)
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
      const result = await generateScene2ControllerFile(para)
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
      const result = await generateScene2ControllerFile(para)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async asyncFetchLiquibaseSource(tableName) {
      const para = {
        tableName: tableName,
        overwrite: false
      }
      const result = await generateScene2LiquibaseSource(para)
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
      const result = await generateScene2LiquibaseFile(para)
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
      const result = await generateScene2LiquibaseFile(para)
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
