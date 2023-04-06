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
              <el-option v-for="item in optionMap.get($commonDicType.DATA_SOURCE())" :key="item.value"
                :label="item.label" :value="item.value">
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
        <el-table-column prop="tabletype" label="类别" show-overflow-tooltip
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ORACLE_OBJ_TYPE())"
          sortable></el-table-column>
        <el-table-column prop="comments" label="描述" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="代码生成操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleClickCodeGenerateDto(scope.row.tablename)">打开生成窗口
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
    <el-dialog :visible.sync="tableColumnShowDialogVisible" width="80%" :close-on-click-modal="false"
       title="列属性">
      <el-table v-loading="loading" :data="columnData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="comments" label="描述" min-width="100" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="columnName" label="列名" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="dataType" label="类型" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="dataDefault" label="缺省值" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="作为条件">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inDrawer" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
       <el-table-column align="center" label="作为表列">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inElTable" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="作为展开列">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inElTableExpand" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="加入编辑表单">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inAddOrEditForm" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="代码类别名">
          <template slot-scope="{row}">
            <el-input v-model="row.optionsName" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="弹窗方法名">
          <template slot-scope="{row}">
            <el-input v-model="row.methodName" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
        </el-table-column>
      </el-table>


    </el-dialog>
    <!-- 对话框区结束 -->
  </div>
</template>
<script>
  import {
    fetchTableAndViewsPage
  } from '@/api/generatormp'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  import {
    fetchTableColumnList
  } from '@/api/oracle-dictionary'

  export default {
    name: 'dto',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        tableData: [],
        columnData: [],
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
        tableColumnShowDialogVisible: false,
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.ORACLE_OBJ_TYPE(),
          this.$commonDicType.DATA_SOURCE()
        ],
      }
    },
    created() {},
    mounted() {
      this.loadAllOptions()
      this.getTableData()
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

      async getTableData() {
        this.loading = true
        const response = await fetchTableAndViewsPage(this.filterDrawer.formData)
        this.loading = false
        if (this.$commonResultCode.SUCCESS() !== response.code) {
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
      },
      async handleClickCodeGenerateDto(tbName) {
        this.tableColumnShowDialogVisible = true
        const tableNameDTO = {
          tableName: tbName,
          dataSource: 'master'
        }
        this.loading = true
        const response = await fetchTableColumnList(tableNameDTO)
        this.loading = false
        if (this.$commonResultCode.SUCCESS() !== response.code) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        debugger
        this.columnData = data

      }
    }
  }
</script>
<style>
</style>
