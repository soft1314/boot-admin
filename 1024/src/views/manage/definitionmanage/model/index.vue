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
          <el-form-item label="主键" :label-width="filterDrawer.formLabelWidth" prop="id">
            <el-input v-model="filterDrawer.formData.id" placeholder="请输入主键" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="模型标识" :label-width="filterDrawer.formLabelWidth" prop="key">
            <el-input
              v-model="filterDrawer.formData.key"
              placeholder="请输入模型标识"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="模型名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input
              v-model="filterDrawer.formData.name"
              placeholder="请输入模型名称"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="版本号" :label-width="filterDrawer.formLabelWidth" prop="version">
            <el-input
              v-model="filterDrawer.formData.version"
              placeholder="请输入版本号"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录创建时间" prop="createTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.createTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="记录最后修改时间" prop="lastUpdateTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.lastUpdateTime" type="date" placeholder="选择日期" />
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
          新建
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
              <el-form-item label="模型标识">
                <span>{{ props.row.key }}</span>
              </el-form-item>
              <el-form-item label="模型名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="版本号">
                <span>{{ props.row.version }}</span>
              </el-form-item>
              <el-form-item label="记录创建时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.createTime) }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.lastUpdateTime) }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <!-- <el-table-column prop="id" label="主键" show-overflow-tooltip sortable width="120"></el-table-column> -->
        <el-table-column prop="key" label="模型标识" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="模型名称" show-overflow-tooltip sortable />
        <el-table-column prop="category" label="类别" show-overflow-tooltip sortable />
        <el-table-column prop="version" label="版本" show-overflow-tooltip sortable width="50" />
        <el-table-column
          prop="createTime"
          label="记录创建时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="lastUpdateTime"
          label="记录最后修改时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleEditRow(scope.row)">修改</el-button>
            <el-button size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
            <el-button size="least" type="warning" @click="handleDeployModel(scope.row)">部署</el-button>
            <el-button size="least" type="success" @click="handleFetchXml(scope.row)">XML</el-button>
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
      :visible.sync="mainDataForm.mainDataFormDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle"
    >
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="模型标识" prop="key">
          <el-input v-model="mainDataForm.editingRecord.key" placeholder="请输入模型标识" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="模型名称" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.name"
            placeholder="请输入模型名称"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="模型说明" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.description"
            placeholder="请输入模型说明"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSubmitMainDataForm()">
          创建
        </el-button>
        <el-button type="primary" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="sourceCodeForm.dialogVisible"
      width="80%"
      :close-on-click-modal="false"
      title="XML"
    >
      <el-input v-model="sourceCodeForm.editingRecord.sourceCode" type="textarea" :rows="20" readonly />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseSourceCodeDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveFileButton()">
          生成文件
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
    <!-- 模型编辑 -->
    <Modeler ref="modelerComponent" @refreshTable="getMainTableData" />
  </div>
</template>
<script>
import Modeler from './components/Modeler'
import {
  fetchModelPage,
  saveNewModel,
  delModel,
  deployModel,
  fetchXml
} from '@/api/workflow-model'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'Model',
  components: {
    Modeler
  },
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          key: '',
          name: '',
          version: '',
          enabled: '1',
          deleted: '1',
          description: '无'
        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      sourceCodeForm: {
        editingRecord: {
          sourceCode: ''
        },
        dialogVisible: false
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          id: '',
          key: '',
          name: '',
          version: null,
          createTime: null,
          lastUpdateTime: null,
          datestamp: null,
          enabled: '',
          deleted: '',
          description: '',
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
      rules: {
        id: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        key: [{
          required: true,
          message: '请输入模型标识',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入模型名称',
          trigger: 'blur'
        }],
        version: [{
          required: true,
          message: '请输入版本号',
          trigger: 'blur'
        }],
        createTime: [{
          required: true,
          message: '请输入记录创建时间',
          trigger: 'blur'
        }],
        lastUpdateTime: [{
          required: true,
          message: '请输入记录最后修改时间',
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
      const response = await fetchModelPage(this.filterDrawer.formData)
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
    handleEditRow(row) {
      this.$nextTick(() => {
        this.$refs.modelerComponent.setSrc(row.id)
        this.$refs.modelerComponent.showDialog()
      })
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelModel(row.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleDeployModel(row) {
      this.$confirm('此操作将部署选中的模型, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDeployModel(row.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消部署'
        })
      })
    },
    async handleFetchXml(row) {
      const guidVO = {
        guid: row.id
      }
      const result = await fetchXml(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.sourceCodeForm.editingRecord.sourceCode = result.data
        this.sourceCodeForm.dialogVisible = true
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async awaitDelModel(guid) {
      const guidVO = {
        guid
      }
      const result = await delModel(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.getMainTableData()
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    async awaitDeployModel(guid) {
      const guidVO = {
        guid
      }
      const result = await deployModel(guidVO)
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '创建新的模型'
      this.initmainDataForm()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    initmainDataForm() {
      this.mainDataForm.editingRecord.id = ''
      this.mainDataForm.editingRecord.key = ''
      this.mainDataForm.editingRecord.name = ''
      this.mainDataForm.editingRecord.description = ''
    },
    handleSubmitMainDataForm() {
      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async submitMainDataForm() {
      const response = await saveNewModel(this.mainDataForm.editingRecord)
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
      this.mainDataForm.mainDataFormDialogVisible = false

      this.$nextTick(() => {
        this.$refs.modelerComponent.setSrc(data)
        this.$refs.modelerComponent.showDialog()
      })
    },
    handleCloseMainDataFormDialog() {
      this.getMainTableData()
      this.mainDataForm.mainDataFormDialogVisible = false
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
    },
    handleCloseSourceCodeDialog() {
      this.sourceCodeForm.dialogVisible = false
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
