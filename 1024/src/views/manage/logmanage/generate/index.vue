<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 张三丰 -->
<!-- @since 2022-11-23 -->
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
          <el-form-item
            label="主键"
            :label-width="filterDrawer.formLabelWidth"
            prop="guid"
          >
            <el-input
              v-model="filterDrawer.formData.guid"
              placeholder="请输入主键"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="文件名"
            :label-width="filterDrawer.formLabelWidth"
            prop="fileName"
          >
            <el-input
              v-model="filterDrawer.formData.fileName"
              placeholder="请输入文件名"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="摘要"
            :label-width="filterDrawer.formLabelWidth"
            prop="summary"
          >
            <el-input
              v-model="filterDrawer.formData.summary"
              placeholder="请输入摘要"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录创建时间" prop="createTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.createTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="记录最后修改时间" prop="modifyTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.modifyTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item
            label="启用状态"
            :label-width="filterDrawer.formLabelWidth"
            prop="enabled"
          >
            <el-select
              v-model="filterDrawer.formData.enabled"
              placeholder="请选择启用状态"
              size="mini"
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.ENABLED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="删除状态"
            :label-width="filterDrawer.formLabelWidth"
            prop="deleted"
          >
            <el-select
              v-model="filterDrawer.formData.deleted"
              placeholder="请选择删除状态"
              size="mini"
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.DELETED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="备注"
            :label-width="filterDrawer.formLabelWidth"
            prop="remarks"
          >
            <el-input
              v-model="filterDrawer.formData.remarks"
              placeholder="请输入备注"
              size="mini"
              prefix-icon="el-icon-search"
            />
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
        <!--                <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
                    添加
                </el-button> -->
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
        style="width: 100%"
        max-height="500"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="主键">
                <span>{{ props.row.guid }}</span>
              </el-form-item>
              <el-form-item label="文件名">
                <span>{{ props.row.fileName }}</span>
              </el-form-item>
              <el-form-item label="摘要">
                <span>{{ props.row.summary }}</span>
              </el-form-item>
              <el-form-item label="记录创建者">
                <span>{{ props.row.createBy }}</span>
              </el-form-item>
              <el-form-item label="记录创建时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.createTime) }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改者">
                <span>{{ props.row.modifyBy }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.modifyTime) }}</span>
              </el-form-item>
              <el-form-item label="时间戳">
                <span>{{ $commonUtils.dateTimeFormat(props.row.datestamp) }}</span>
              </el-form-item>
              <el-form-item label="启用状态">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.ENABLED()),props.row.enabled) }}</span>
              </el-form-item>
              <el-form-item label="删除状态">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.DELETED()),props.row.deleted) }}</span>
              </el-form-item>
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column prop="fileName" label="文件名" show-overflow-tooltip sortable min-width="500" align="left" />
        <el-table-column prop="createTime" label="记录创建时间" show-overflow-tooltip sortable min-width="120" :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
        <el-table-column prop="modifyTime" label="记录最后修改时间" show-overflow-tooltip sortable min-width="120" :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
        <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable min-width="80" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
        <el-table-column prop="deleted" label="删除状态" show-overflow-tooltip sortable min-width="80" :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())" />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable min-width="80" />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="80">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleEditRow(scope.row)">修改</el-button>
            <el-button size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
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
      <el-form ref="elForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="文件名" prop="fileName">
          <el-input
            v-model="mainDataForm.editingRecord.fileName"
            placeholder="请输入文件名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="摘要" prop="summary">
          <el-input
            v-model="mainDataForm.editingRecord.summary"
            placeholder="请输入摘要"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="记录创建者" prop="createBy">
          <el-input
            v-model="mainDataForm.editingRecord.createBy"
            placeholder="请输入记录创建者"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="记录创建时间" prop="createTime">
          <el-date-picker v-model="mainDataForm.editingRecord.createTime" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="记录最后修改者" prop="modifyBy">
          <el-input
            v-model="mainDataForm.editingRecord.modifyBy"
            placeholder="请输入记录最后修改者"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="记录最后修改时间" prop="modifyTime">
          <el-date-picker v-model="mainDataForm.editingRecord.modifyTime" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="时间戳" prop="datestamp">
          <el-date-picker v-model="mainDataForm.editingRecord.datestamp" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-select v-model="mainDataForm.editingRecord.enabled" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.ENABLED())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="删除状态" prop="deleted">
          <el-select v-model="mainDataForm.editingRecord.deleted" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.DELETED())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input
            v-model="mainDataForm.editingRecord.remarks"
            placeholder="请输入备注"
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
          保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
  </div>
</template>
<script>
import {
  fetchTgFileSummaryPage,
  saveTgFileSummary,
  delTgFileSummary
} from '@/api/tg-file-summary-scene1'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
export default {
  name: 'TgFileSummary',
  components: {},
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {},
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          guid: '',
          fileName: '',
          summary: '',
          createBy: '',
          createTime: null,
          modifyBy: '',
          modifyTime: null,
          datestamp: null,
          enabled: '',
          deleted: '',
          remarks: '',
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
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        fileName: [{
          required: true,
          message: '请输入文件名',
          trigger: 'blur'
        }],
        summary: [{
          required: true,
          message: '请输入摘要',
          trigger: 'blur'
        }],
        createBy: [{
          required: true,
          message: '请输入记录创建者',
          trigger: 'blur'
        }],
        createTime: [{
          required: true,
          message: '请输入记录创建时间',
          trigger: 'blur'
        }],
        modifyBy: [{
          required: true,
          message: '请输入记录最后修改者',
          trigger: 'blur'
        }],
        modifyTime: [{
          required: true,
          message: '请输入记录最后修改时间',
          trigger: 'blur'
        }],
        datestamp: [{
          required: true,
          message: '请输入时间戳',
          trigger: 'blur'
        }],
        enabled: [{
          required: true,
          message: '请输入启用状态',
          trigger: 'blur'
        }],
        deleted: [{
          required: true,
          message: '请输入删除状态',
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

    async getMainTableData() {
      this.loading = false
      const response = await fetchTgFileSummaryPage(this.filterDrawer.formData)
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
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = row
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelTgFileSummary(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelTgFileSummary(guid) {
      const guidVO = {
        guid
      }
      const result = await delTgFileSummary(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.getMainTableData()
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initmainDataForm()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    initmainDataForm() {
      this.mainDataForm.editingRecord.guid = ''
      // this.resetFormData()
    },
    handleSubmitMainDataForm() {
      this.$refs['elForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async submitMainDataForm() {
      const response = await saveTgFileSummary(this.mainDataForm.editingRecord)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      if (this.mainDataForm.mainDataFormDialogTitle === '修改') {
        this.getMainTableData()
        this.mainDataForm.mainDataFormDialogVisible = false
      }
    },
    handleCloseMainDataFormDialog() {
      this.getMainTableData()
      this.mainDataForm.mainDataFormDialogVisible = false
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
    .el-drawer__container ::-webkit-scrollbar{
        display: none;
    }
</style>
