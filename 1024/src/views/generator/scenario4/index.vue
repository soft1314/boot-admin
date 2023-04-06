<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer :visible.sync="filterDrawer.dialogVisible" direction="rtl" title="请输入查询条件" :with-header="false"
      size="30%">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
          :model="filterDrawer.formData">
          <el-form-item label="名称" :label-width="filterDrawer.formLabelWidth" prop="tableName">
            <el-input placeholder="请输入表或视图名称" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.tableName">
            </el-input>
          </el-form-item>
          <el-form-item label="类别" :label-width="filterDrawer.formLabelWidth" prop="tableType">
            <el-select v-model="filterDrawer.formData.tableType" placeholder="请选择对象类别" size="mini">
              <el-option v-for="item in optionMap.get($commonDicType.ORACLE_OBJ_TYPE())" :key="item.value"
                :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述" :label-width="filterDrawer.formLabelWidth" prop="tableComments">
            <el-input placeholder="请输入对象描述" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.tableComments">
            </el-input>
          </el-form-item>
          <el-form-item label="数据源" :label-width="filterDrawer.formLabelWidth" prop="dataSource">
            <el-select v-model="filterDrawer.formData.dataSource" placeholder="请选择数据源" size="mini">
              <el-option v-for="item in options.dataSourceOptions" :key="item.value" :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button v-on:click="getMainTableData()" size="mini" type="success" icon="el-icon-search">查询</el-button>
            <el-button v-on:click="resetForm('drawerForm')" size="mini" type="primary" icon="el-icon-refresh">重置
            </el-button>
            <el-button v-on:click="hideDrawer()" size="mini" icon="el-icon-close">关闭</el-button>
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
      <el-table v-loading="loading" :data="mainTableData" border fit highlight-current-row style="width: 100%"
        max-height="500">
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
        <el-table-column type="index" label="序号" :index="indexMethod" width="70">
        </el-table-column>
        <el-table-column prop="tablename" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="tabletype" label="类别" show-overflow-tooltip
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ORACLE_OBJ_TYPE())"
          sortable></el-table-column>
        <el-table-column prop="comments" label="描述" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="检查操作" show-overflow-tooltip min-width="60">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'STORY')">职能
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'CHECK')">预检
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="前端生成操作" min-width="80">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'API')">JsApi
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'VUE')">Vue
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'EDIT')">修改
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'VIEW')">查看
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'ROUTER')">Router
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="后端生成操作" min-width="200">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'QueryVO')">查询VO
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'DataVO')">数据VO
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'CompleteVO')">任务VO
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Mapstruct')">
              MapStruct</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Trans')">事务
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Service')">Service
            </el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Controller')">
              Controller</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Liquibase')">
              Liquibase</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 分页组件开始 -->
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination v-on:size-change="handlePageSizeChange" v-on:current-change="handlePageCurrentChange"
        :current-page="filterDrawer.formData.currentPage" :page-sizes="[5,10,20,50,100,500]"
        :page-size="filterDrawer.formData.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="filterDrawer.formData.total">
      </el-pagination>
    </div>
    <!-- 分页组件结束 -->
    <!-- 对话框区开始 -->
    <el-dialog :visible.sync="mainDataForm.formDialogVisible" width="80%" :close-on-click-modal="false"
      :title="mainDataForm.formDialogTitle">
      <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名：
        {{mainDataForm.editingRecord.fullFileName}}</span>
      <br /><br />
      <el-input type="textarea" v-model="mainDataForm.editingRecord.sourceCode" :rows="20" readonly></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveFileButton()">
          生成文件
        </el-button>
      </div>
    </el-dialog>
    <!-- 对话框区结束 -->
  </div>
</template>
<script>
  import {
    generateScene4VueRouterSource
  } from '@/api/scene4-vuerouter-generator'
  import {
    generateScene4VueSource,
    generateScene4VueFile
  } from '@/api/scene4-vue-generator'
  import {
    generateScene4JsApiSource,
    generateScene4JsApiFile,
  } from '@/api/scene4-js-api-generator'
  import {
    generateScene4TableCheckResult,
    generateScene4Story
  } from '@/api/scene4-checker'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  import {
    fetchTableAndViewsPage,
    generateVo,
  } from '@/api/generatormp'
  import {
    generateScene4CompleteVoSource,
    generateScene4CompleteVoFile
  } from '@/api/scene4-complete-vo-generator'
  import {
    fetchOracleObjectTypeOptions
  } from '@/api/options'
  import {
    optionValueLabelConverter
  } from '@/utils/arrayutil'
  import {
    generateScene4DataVoSource,
    generateScene4DataVoFile
  } from '@/api/scene4-data-vo-generator'
  import {
    generateScene4QueryVoSource,
    generateScene4QueryVoFile
  } from '@/api/scene4-query-vo-generator'
  import {
    generateScene4MapstructSource,
    generateScene4MapstructFile
  } from '@/api/scene4-mapstruct-generator'
  import {
    generateScene4ServiceSource,
    generateScene4ServiceFile
  } from '@/api/scene4-service-generator'
  import {
    generateScene4TransactionSource,
    generateScene4TransactionFile
  } from '@/api/scene4-transaction-generator'
  import {
    generateScene4ControllerSource,
    generateScene4ControllerFile
  } from '@/api/scene4-controller-generator'
  import {
    generateScene4LiquibaseSource,
    generateScene4LiquibaseFile
  } from '@/api/scene4-liquibase-generator'
  import {
    generateScene4EditVueSource,
    generateScene4EditVueFile,
  } from '@/api/scene4-edit-vue-generator'
  import {
    generateScene4ViewVueSource,
    generateScene4ViewVueFile
  } from '@/api/scene4-view-vue-generator'
  export default {
    name: 'scenario1',
    computed: {},
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
            fullFileName: ''
          },
          formDialogVisible: false,
          formDialogTitle: '查看代码'
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
            total: 0,
          },
        },
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.ORACLE_OBJ_TYPE()
        ],
        options: {
          tableTypeOptions: []
        }
      }
    },
    created() {},
    mounted() {
      this.loadAllOptions()
      this.getMainTableData()
    },
    watch: {},
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
        if (val != this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val;
          this.getMainTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val;
          this.getMainTableData()
        }
      },
      indexMethod(index) {
        return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1;
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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


      async getMainTableData() {
        this.loading = false
        const response = await fetchTableAndViewsPage(this.filterDrawer.formData)
        if (100 !== response.code) {
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
        if ('STORY' === cmd) {
          this.asyncFetchScene4Story(tableName)
        }
        if ('CHECK' === cmd) {
          this.asyncFetchCheckResult(tableName)
        }
        if ('VUE' === cmd) {
          this.asyncFetchVueSource(tableName)
        }
        if ('API' === cmd) {
          this.asyncFetchJsApiSource(tableName)
        }
        if ('EDIT' === cmd) {
          this.asyncFetchEditVueSource(tableName)
        }
        if ('VIEW' === cmd) {
          this.asyncFetchViewVueSource(tableName)
        }
        if ('ROUTER' === cmd) {
          this.asyncFetchVueRouterSource(tableName)
        }
        if ('QueryVO' === cmd) {
          this.asyncFetchQueryVoSource(tableName)
        }
        if ('DataVO' === cmd) {
          this.asyncFetchDataVoSource(tableName)
        }
        if ('CompleteVO' === cmd) {
          this.asyncFetchCompleteVoSource(tableName)
        }
        if ('Mapstruct' === cmd) {
          this.asyncFetchMapstructSource(tableName)
        }
        if ('Service' === cmd) {
          this.asyncFetchServiceSource(tableName)
        }
        if ('Trans' === cmd) {
          this.asyncFetchTransactionSource(tableName)
        }
        if ('Controller' === cmd) {
          this.asyncFetchControllerSource(tableName)
        }
        if ('Liquibase' === cmd) {
          this.asyncFetchLiquibaseSource(tableName)
        }

        this.mainDataForm.formDialogVisible = true
      },
      async asyncFetchVueSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateScene4VueSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName
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
        const result = await generateScene4JsApiSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName
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
        const result = await generateScene4TableCheckResult(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
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
        const result = await generateScene4VueRouterSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = ''
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async asyncFetchScene4Story(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateScene4Story(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = ''
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      handleSaveFileButton() {
        if ('API' == this.mainDataForm.editingRecord.sourceType) {
          this.generateJsApiFile()
        }
        if ("VUE" == this.mainDataForm.editingRecord.sourceType) {
          this.generateVueFile()
        }
        if ("EDIT" == this.mainDataForm.editingRecord.sourceType) {
          this.generateEditVueFile()
        }
        if ("VIEW" == this.mainDataForm.editingRecord.sourceType) {
          this.generateViewVueFile()
        }
        if ("CHECK" == this.mainDataForm.editingRecord.sourceType) {
          this.$message({
            type: 'info',
            message: '检测信息仅供阅读!'
          });
        }
        if ("ROUTER" == this.mainDataForm.editingRecord.sourceType) {
          this.$message({
            type: 'info',
            message: 'ROUTER片断仅供阅读!'
          });
        }
        if ("STORY" == this.mainDataForm.editingRecord.sourceType) {
          this.$message({
            type: 'info',
            message: '车间职能介绍仅供阅读!'
          });
        }
        if ("QueryVO" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveQueryVoFileButton()
        }
        if ("DataVO" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveDataVoFileButton()
        }
        if ("CompleteVO" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveCompleteVoFileButton()
        }
        if ("Mapstruct" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveMapstructFileButton()
        }
        if ("Service" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveServiceFileButton()
        }
        if ("Trans" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveTransactionFileButton()
        }
        if ("Controller" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveControllerFileButton()
        }
        if ("Liquibase" === this.mainDataForm.editingRecord.sourceType) {
          this.handleSaveLiquibaseFileButton()
        }
      },
      async generateJsApiFile() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateScene4JsApiFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateJsApiFileOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4JsApiFile(para)
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
        const result = await generateScene4VueFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
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
            });
          });
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
        const result = await generateScene4VueFile(para)
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
        const result = await generateScene4QueryVoSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4QueryVoFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateQueryVoOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4QueryVoFile(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },

      async asyncFetchDataVoSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateScene4DataVoSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveDataVoFileButton() {
        debugger
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateScene4DataVoFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateDataVoOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
        } else {
          this.$message({
            message: result.message,
            type: 'warning'
          })
        }
      },
      async generateDataVoOverwrite() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: true
        }
        const result = await generateScene4DataVoFile(para)
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
        const result = await generateScene4MapstructSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4MapstructFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateMapstructOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4MapstructFile(para)
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
        const result = await generateScene4ServiceSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4TransactionSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4ServiceFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateServiceOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4TransactionFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateTransactionOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4ServiceFile(para)
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
        const result = await generateScene4TransactionFile(para)
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
        const result = await generateScene4ControllerSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4ControllerFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateControllerOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4ControllerFile(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      ///////////////////
      async asyncFetchLiquibaseSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateScene4LiquibaseSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
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
        const result = await generateScene4LiquibaseFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateLiquibaseOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4LiquibaseFile(para)
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
        const result = await generateScene4EditVueSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName
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
        const result = await generateScene4ViewVueSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async generateEditVueFile() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateScene4EditVueFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateEditVueFileOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4ViewVueFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('VUE文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateViewVueFileOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
        } else {
          this.$message({
            message: result.message,
            type: 'warning'
          })
        }
      },
      async generateEditVueFileOverwrite() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: true
        }
        const result = await generateScene4EditVueFile(para)
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
        const result = await generateScene4ViewVueFile(para)
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
        const result = await generateScene4CompleteVoSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveCompleteVoFileButton() {
        debugger
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateScene4CompleteVoFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateCompleteVoOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
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
        const result = await generateScene4CompleteVoFile(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
    }
  }
</script>
<style>
</style>
