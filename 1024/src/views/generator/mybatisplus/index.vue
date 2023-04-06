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
            <!-- <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'VO')">VO</el-button> -->
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'ENTITY')">Entity</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'MAPPER')">Mapper</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'SERVICE')">Service</el-button>
            <el-button size="least" type="primary" @click="handleGenerateButton(scope.row.tablename,'CONTROLLER')">Controller
            </el-button>
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
    fetchTableAndViewsPage,
    generateEntity,
    generateEntitySource,
    generateVo,generateVoSource,
    generateMapper,generateMapperSource,
    generateService,generateServiceSource,
    generateController,generateControllerSource
  } from '@/api/generatormp'

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
        this.mainDataForm.editingRecord.tableName = tableName
        this.mainDataForm.editingRecord.sourceType = sourceType
        if('VO' === sourceType){
          this.asyncFetchVoSource(tableName)
        }
        if('ENTITY' === sourceType){
          this.asyncFetchEntitySource(tableName)
        }
        if('MAPPER' === sourceType){
          this.asyncFetchMapperSource(tableName)
        }
        if('SERVICE' === sourceType){
          this.asyncFetchServiceSource(tableName)
        }
        if('CONTROLLER' === sourceType){
          this.asyncFetchControllerSource(tableName)
        }


        this.mainDataForm.formDialogVisible = true
      },
      handleSaveFileButton(){
        if("VO" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveVoFileButton()
        }
        if("ENTITY" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveEntityFileButton()
        }
        if("MAPPER" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveMapperFileButton()
        }
        if("SERVICE" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveServiceFileButton()
        }
        if("CONTROLLER" === this.mainDataForm.editingRecord.sourceType){
          this.handleSaveControllerFileButton()
        }

      },

      async asyncFetchVoSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateVoSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveVoFileButton() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateVo(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateVoOverwrite()
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
      async generateVoOverwrite() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: true
        }
        const result = await generateVo(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },

      async asyncFetchEntitySource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateEntitySource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveEntityFileButton() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateEntity(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateEntityOverwrite()
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
      async generateEntityOverwrite() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: true
        }
        const result = await generateEntity(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },

      async asyncFetchMapperSource(tableName) {
        const para = {
          tableName: tableName,
          overwrite: false
        }
        const result = await generateMapperSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.mainDataForm.editingRecord.sourceCode = result.data.sourceCode;
          this.mainDataForm.editingRecord.fullFileName = result.data.fileName;
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveMapperFileButton() {
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateMapper(para)
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
        const result = await generateServiceSource(para)
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
        const result = await generateService(para)
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
        const result = await generateControllerSource(para)
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
        const para = {
          tableName: this.mainDataForm.editingRecord.tableName,
          overwrite: false
        }
        const result = await generateController(para)
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
