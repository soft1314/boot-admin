<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer :visible.sync="filterDrawer.dialogVisible" direction="rtl" title="请输入查询条件" :with-header="false"
      size="30%">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
          :model="filterDrawer.formData">
          <el-form-item label="编码" :label-width="filterDrawer.formLabelWidth" prop="code">
            <el-input placeholder="请输入编码" size="mini" prefix-icon="el-icon-search" v-model="filterDrawer.formData.code">
            </el-input>
          </el-form-item>
          <el-form-item label="名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" v-model="filterDrawer.formData.name">
            </el-input>
          </el-form-item>
          <el-form-item label="短语" :label-width="filterDrawer.formLabelWidth" prop="varName">
            <el-input placeholder="请输入短语" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.varName">
            </el-input>
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-refresh"
          @click="handleClickGenerateButton()">
          生成
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
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <!-- <el-table-column type="selection" width="55"> -->
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70">
        </el-table-column>
        <el-table-column prop="code" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="name" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="varName" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button type="primary" size="least" @click="handleDetailButton(scope.row)">详情</el-button>
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
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table v-loading="loading" :data="detailTableData" border fit highlight-current-row style="width: 100%"
        max-height="500">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <!-- <el-table-column type="selection" width="55"> -->
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70">
        </el-table-column>
        <el-table-column prop="itemCode" label="编码" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="itemName" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable></el-table-column>
<!--        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button type="warning" size="least" @click="handleEditDetailRowButton(scope.row)">编辑</el-button>
            <el-button type="danger" size="least" @click="handleDelDetailRowButton(scope.row)">删除</el-button>
            </el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 分页组件开始 -->
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination v-on:size-change="handlePageSizeChangeDetail" v-on:current-change="handlePageCurrentChangeDetail"
        :current-page="detailPagination.currentPage" :page-sizes="[5,10,20,50,100,500]"
        :page-size="detailPagination.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="detailPagination.total">
      </el-pagination>
    </div>
    <!-- 分页组件结束 -->
    <!-- 对话框区开始 -->
    <el-dialog :visible.sync="mainDataForm.dataFormDialogVisible" width="80%" :close-on-click-modal="false"
      :title="mainDataForm.dataFormDialogTitle">
      <el-input type="textarea" v-model="sourceCode" :rows="20" readonly="true"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveJsFileButton()">
          生成文件
        </el-button>
      </div>
    </el-dialog>
    <!-- 对话框区结束 -->
  </div>
</template>
<script>
  import {
    fetchDicTypePage,
    fetchDictypeJsSource,
    generateDictypeJsFile
  } from '@/api/tb-dictype'
  import {
    fetchDicTionaryPage
  } from '@/api/tb-dictionary'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  import {
    fetchTableColumnList
  } from '@/api/oracle-dictionary'

  export default {
    name: 'dictype',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        filterDrawer: {
          dialogVisible: false,
          formLabelWidth: '100px',
          formData: {
            tableName: '',
            tableType: 'TABLE',
            tableComments: '',
            dataSource: 'master',
            currentPage: 1,
            pageSize: 5,
            total: 0,
          },
        },
        mainTableData: [],
        mainDataForm: {
          dataFormDialogVisible: false,
          currentItemTypeCode: ''
        },
        detailTableData: [],
        detailPagination: {
          currentPage: 1,
          pageSize: 5,
          total: 0,
        },
        sourceCode: '',
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [],
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
      colFormatter(row, column, cellValue, key) {
        return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue + '')
      },
      async loadDictionaryOptions(itemType, includeAllOptions) {
        this.loading = true
        const response = await getDictionaryOptionsByItemType(itemType, includeAllOptions)
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
        this.optionMap.set(itemType, data)
      },
      handlePageSizeChange(val) {
        if (val != this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val;
          this.getMainTableData()
        }
      },
      handlePageSizeChangeDetail(val) {
        if (val != this.detailPagination.pageSize) {
          this.detailPagination.pageSize = val;
          this.getDetailTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val;
          this.getMainTableData()
        }
      },
      handlePageCurrentChangeDetail(val) {
        if (val != this.detailPagination.currentPage) {
          this.detailPagination.currentPage = val;
          this.getDetailTableData()
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

      async getMainTableData() {
        this.loading = false
        const response = await fetchDicTypePage(this.filterDrawer.formData)
        this.loading = false
        if (100 !== response.code) {
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
      async getDetailTableData() {
        const tbDictionaryQueryVO = {
          itemTypeCode: this.mainDataForm.currentItemTypeCode,
          currentPage: this.detailPagination.currentPage,
          pageSize: this.detailPagination.pageSize
        }
        this.loading = false
        const response = await fetchDicTionaryPage(tbDictionaryQueryVO)
        this.loading = false
        if (100 !== response.code) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.detailTableData = data.records
        this.detailPagination.total = data.total
        if (this.detailTableData.length == 0) {
          this.$message({
            message: '无数据',
            type: 'warning'
          })
        }
      },
      handleDetailButton(row) {
        this.mainDataForm.currentItemTypeCode = row.code
        this.getDetailTableData()
      },
      handleCloseMainDataFormDialog() {
        this.getMainTableData()
        this.mainDataForm.dataFormDialogVisible = false
      },
      handleClickGenerateButton() {
        this.awaitFetchDictypeJsSource()
        this.mainDataForm.dataFormDialogVisible = true
      },
      async awaitFetchDictypeJsSource() {
        const result = await fetchDictypeJsSource()
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.sourceCode = result.data
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveJsFileButton() {
        const overWrite = {
          boolTrue: false
        }
        const result = await generateDictypeJsFile(overWrite)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('JS文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateDictypeJsFileOverwrite()
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
      async generateDictypeJsFileOverwrite() {
        const overWrite = {
          boolTrue: true
        }
        const result = await generateDictypeJsFile(overWrite)
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
