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
          <el-form-item label="部署编号" :label-width="filterDrawer.formLabelWidth" prop="deploymentId">
            <el-input
              v-model="filterDrawer.formData.deploymentId"
              placeholder="请输入部署编号"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input v-model="filterDrawer.formData.name" placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="类别" :label-width="filterDrawer.formLabelWidth" prop="category">
            <el-input
              v-model="filterDrawer.formData.category"
              placeholder="请输入类别"
              size="mini"
              prefix-icon="el-icon-search"
            />
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
          添加
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
              <el-form-item label="部署编号">
                <span>{{ props.row.deploymentId }}</span>
              </el-form-item>
              <el-form-item label="名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="类别">
                <span>{{ props.row.category }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <!-- <el-table-column prop="id" label="主键" show-overflow-tooltip sortable></el-table-column> -->
        <!-- <el-table-column prop="deploymentId" label="部署编号" show-overflow-tooltip sortable></el-table-column> -->
        <el-table-column prop="key" label="标识" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="名称" show-overflow-tooltip sortable />
        <el-table-column prop="category" label="类别" show-overflow-tooltip sortable />
        <el-table-column prop="version" label="版本" show-overflow-tooltip sortable />
        <el-table-column prop="description" label="描述" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="success" @click="handleSeeResource(scope.row,'image/png')">流图</el-button>
            <el-button size="least" type="success" @click="handleSeeResource(scope.row,'text/xml')">XML</el-button>
            <el-button size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
            <el-button size="least" type="primary" @click="handleDeployModel(scope.row)">转模型</el-button>
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
        <el-form-item label="类别" prop="processCategory">
          <el-input
            v-model="mainDataForm.editingRecord.processCategory"
            placeholder="请输入类别"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-upload
          ref="upload"
          class="upload-demo"
          :action="action"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          multiple
          :limit="1"
          :data="mainDataForm.editingRecord"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">导入流程</el-button>
          <div slot="tip" class="el-upload__tip">只能上传zip/bar/jpg/png/bpmn20.xml文件，且不超过500kb</div>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="photoDialog.dialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="photoDialog.dialogTitle"
    >
      <div class="demo-image__placeholder">
        <div class="block">
          <el-image :src="pngsrc" />
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClosePhotoViewDialog()">
          关闭
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
  </div>
</template>
<script>
import {
  fetchAllProcessPage,
  delProcess,
  deployModel
} from '@/api/workflow-process'
import {
  fetchResource
} from '@/api/workflow-download'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'Process',
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          processCategory: ''
        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      pngsrc: '',
      photoDialog: {
        dialogVisible: false,
        dialogTitle: ''
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          id: '',
          deploymentId: '',
          name: '',
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
      fileList: [],
      rules: {
        id: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        deploymentId: [{
          required: true,
          message: '请输入部署编号',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入模型名称',
          trigger: 'blur'
        }]
      }
    }
  },
  computed: {
    action() {
      return process.env.VUE_APP_BASE_API + '/api/workflow/free/activiti/process/add'
    }
  },
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
      const response = await fetchAllProcessPage(this.filterDrawer.formData)
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
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelProcess(row.deploymentId)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async handleSeeResource_old(row, resType) {
      this.listLoading = true
      fetchResource(row.id, resType).then(request => {
        const blob = new Blob([request.data], {
          type: resType
        })
        const link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = '流程'
        link.target = '_blank'
        link.style.display = 'none'
        link.click()
      })
      this.listLoading = false
      this.$message({
        message: '数据导出成功',
        type: 'success'
      })
    },
    async handleSeeResource(row, resType) {
      this.listLoading = true
      const response = await fetchResource(row.id, resType)
      this.listLoading = false
      if (resType === 'text/xml') {
        const url = this.$commonUtils.genObjectURL(response.data)
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.download = url
        link.target = '_blank'
        link.click()
      } else if (resType === 'image/png') {
        this.pngsrc = this.$commonUtils.genObjectURL(response.data)
        this.photoDialog.dialogVisible = true
      }
    },
    handleClosePhotoViewDialog() {
      this.photoDialog.dialogVisible = false
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
    async awaitDelProcess(guid) {
      const guidVO = {
        guid
      }
      const result = await delProcess(guidVO)
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
      this.mainDataForm.mainDataFormDialogTitle = '导入流程'
      this.mainDataForm.mainDataFormDialogVisible = true
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
    submitUpload() {
      this.$refs.upload.submit()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
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
