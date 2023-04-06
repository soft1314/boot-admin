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
              <el-option v-for="item in options.tableTypeOptions" :key="item.value" :label="item.label"
                :value="item.value">
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
            <el-button v-on:click="getTableData()" size="mini" type="success" icon="el-icon-search">查询</el-button>
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
      <el-table v-loading="loading" :data="tableData" border fit highlight-current-row style="width: 100%"
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
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70">
        </el-table-column>
        <el-table-column prop="tablename" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="tabletype" label="类别" show-overflow-tooltip :formatter="OracleObjectTypeConverterLocal"
          sortable></el-table-column>
        <el-table-column prop="comments" label="描述" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="代码生成操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'QueryVO')">查询VO</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'DataVO')">数据VO</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Mapstruct')">MapStruct</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Service')">Service</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Controller')">Controller</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'Liquibase')">Liquibase</el-button>
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
      <span style="font-style: italic;color: red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟保存文件名： {{mainDataForm.editingRecord.fullFileName}}</span>
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
    fetchOracleObjectTypeOptions
  } from '@/api/options'
  import {
    optionValueLabelConverter
  } from '@/utils/arrayutil'
  import {
    fetchTableAndViewsPage
  } from '@/api/generatormp'
  import {
    generateScene1DataVoSource,generateScene1DataVoFile
  } from '@/api/scene1-data-vo-generator'
  import {
    generateScene1QueryVoSource,generateScene1QueryVoFile
  } from '@/api/scene1-query-vo-generator'
  import {
    generateScene1MapstructSource,generateScene1MapstructFile
  } from '@/api/scene1-mapstruct-generator'
  import{
    generateScene1ServiceSource,generateScene1ServiceFile
  } from '@/api/scene1-service-generator'
  import{
    generateScene1ControllerSource,generateScene1ControllerFile
  } from '@/api/scene1-controller-generator'
  import{
    generateScene1LiquibaseSource,generateScene1LiquibaseFile
  } from '@/api/scene1-liquibase-generator'

  export default {
    name: 'mybatisplus',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        tableData: [],
        mainDataForm: {
          editingRecord: {
            sourceCode: '',
            sourceType: '',
            tableName: '',
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
        tableNameContainer: {
          tableName: '',
          overwrite: false
        },
        options: {
          tableTypeOptions: []
        }
      }
    },
    created() {},
    mounted() {
      this.fillOracleObjectTypeOptions()
      this.getTableData()
    },
    watch: {},
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      handlePageSizeChange(val) {
        if (val != this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val;
          this.getTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val;
          this.getTableData()
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


      async getTableData() {
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
        this.tableData = data.records
        this.filterDrawer.formData.total = data.total
        this.loading = false
      },
      deleteItems() {
        this.$confirm('此操作将永久删除选中的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      async fillOracleObjectTypeOptions() {
        const response = await fetchOracleObjectTypeOptions()
        this.options.tableTypeOptions = response.data
      },
      OracleObjectTypeConverterLocal(val) {
        return optionValueLabelConverter(this.options.tableTypeOptions, val.tabletype)
      },

      handleGenerateButton(tableName,sourceType) {
        debugger
        this.mainDataForm.editingRecord.tableName = tableName
        this.mainDataForm.editingRecord.sourceType = sourceType
        if('QueryVO' === sourceType){
          this.asyncFetchQueryVoSource(tableName)
        }
        if('DataVO' === sourceType){
          this.asyncFetchDataVoSource(tableName)
        }
        if('Mapstruct' === sourceType){
          this.asyncFetchMapstructSource(tableName)
        }
        if('Service' === sourceType){
          this.asyncFetchServiceSource(tableName)
        }
        if('Controller' === sourceType){
          this.asyncFetchControllerSource(tableName)
        }
        if('Liquibase' === sourceType){
          this.asyncFetchLiquibaseSource(tableName)
        }

        this.mainDataForm.formDialogVisible = true
      },
      handleSaveFileButton(){
        if("QueryVO" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveQueryVoFileButton()
        }
        if("DataVO" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveDataVoFileButton()
        }
        if("Mapstruct" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveMapstructFileButton()
        }
        if("Service" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveServiceFileButton()
        }
        if("Controller" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveControllerFileButton()
        }
        if("Liquibase" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveLiquibaseFileButton()
        }

      },

      async asyncFetchQueryVoSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateScene1QueryVoSource(para)
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
        const result = await generateScene1QueryVoFile(para)
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
        const result = await generateScene1QueryVoFile(para)
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
        const result = await generateScene1DataVoSource(para)
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
        const result = await generateScene1DataVoFile(para)
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
        const result = await generateScene1DataVoFile(para)
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
        const result = await generateScene1MapstructSource(para)
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
        const result = await generateScene1MapstructFile(para)
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
        const result = await generateScene1MapstructFile(para)
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
        const result = await generateScene1ServiceSource(para)
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
        debugger
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateScene1ServiceFile(para)
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
      async generateServiceOverwrite() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: true
        }
        const result = await generateScene1ServiceFile(para)
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
        const result = await generateScene1ControllerSource(para)
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
        const result = await generateScene1ControllerFile(para)
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
        const result = await generateScene1ControllerFile(para)
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
        const result = await generateScene1LiquibaseSource(para)
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
        const result = await generateScene1LiquibaseFile(para)
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
        const result = await generateScene1LiquibaseFile(para)
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
