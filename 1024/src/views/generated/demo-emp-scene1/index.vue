<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-4-12 22:26:18 -->
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
            label="姓名"
            :label-width="filterDrawer.formLabelWidth"
            prop="name"
          >
            <el-input
              v-model="filterDrawer.formData.name"
              placeholder="请输入姓名"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="性别"
            :label-width="filterDrawer.formLabelWidth"
            prop="sex"
          >
            <el-select
              v-model="filterDrawer.formData.sex"
              placeholder="请选择性别"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.SEX())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="出生年月"
            :label-width="filterDrawer.formLabelWidth"
            prop="birthday"
          >
            <el-input
              v-model="filterDrawer.formData.birthday"
              placeholder="请输入出生年月"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="职称" :label-width="filterDrawer.formLabelWidth" prop="techTitle">
            <el-cascader ref="techTitleQueryCascaderId" :props="techTitleCascaderProps" clearable style="width: 100%;" @change="techTitleQueryCascaderChangeEvent" />
            {{ filterDrawer.formData.techTitle }}
          </el-form-item>
          <el-form-item
            label="薪水"
            :label-width="filterDrawer.formLabelWidth"
            prop="salary"
          >
            <el-input
              v-model="filterDrawer.formData.salary"
              placeholder="请输入薪水"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="情况说明"
            :label-width="filterDrawer.formLabelWidth"
            prop="note"
          >
            <el-input
              v-model="filterDrawer.formData.note"
              placeholder="请输入情况说明"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="记录创建者"
            :label-width="filterDrawer.formLabelWidth"
            prop="createBy"
          >
            <el-input
              v-model="filterDrawer.formData.createBy"
              placeholder="请输入记录创建者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="记录创建时间"
            :label-width="filterDrawer.formLabelWidth"
            prop="createTime"
          >
            <el-input
              v-model="filterDrawer.formData.createTime"
              placeholder="请输入记录创建时间"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="记录最后修改者"
            :label-width="filterDrawer.formLabelWidth"
            prop="modifyBy"
          >
            <el-input
              v-model="filterDrawer.formData.modifyBy"
              placeholder="请输入记录最后修改者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="记录最后修改时间"
            :label-width="filterDrawer.formLabelWidth"
            prop="modifyTime"
          >
            <el-input
              v-model="filterDrawer.formData.modifyTime"
              placeholder="请输入记录最后修改时间"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="时间戳"
            :label-width="filterDrawer.formLabelWidth"
            prop="datestamp"
          >
            <el-input
              v-model="filterDrawer.formData.datestamp"
              placeholder="请输入时间戳"
              size="mini"
              prefix-icon="el-icon-search"
            />
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
              clearable
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
              clearable
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
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleQueryButton()">查询</el-button>
            <el-button size="mini" type="warning" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-add" @click="handleClickAddButton()">
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
              <el-form-item label="姓名">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="性别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.SEX()),props.row.sex) }}</span>
              </el-form-item>
              <el-form-item label="出生年月">
                <span>{{ props.row.birthday }}</span>
              </el-form-item>
              <el-form-item label="职称">
                <span>{{ props.row.techTitle }}</span>
              </el-form-item>
              <el-form-item label="薪水">
                <span>{{ props.row.salary }}</span>
              </el-form-item>
              <el-form-item label="情况说明">
                <span>{{ props.row.note }}</span>
              </el-form-item>
              <el-form-item label="记录创建者">
                <span>{{ props.row.createBy }}</span>
              </el-form-item>
              <el-form-item label="记录创建时间">
                <span>{{ props.row.createTime }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改者">
                <span>{{ props.row.modifyBy }}</span>
              </el-form-item>
              <el-form-item label="记录最后修改时间">
                <span>{{ props.row.modifyTime }}</span>
              </el-form-item>
              <el-form-item label="时间戳">
                <span>{{ props.row.datestamp }}</span>
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
        <el-table-column prop="guid" label="主键" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="姓名" show-overflow-tooltip sortable />
        <el-table-column prop="sex" label="性别" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.SEX())" />
        <el-table-column prop="birthday" label="出生年月" show-overflow-tooltip sortable />
        <el-table-column prop="techTitle" label="职称" show-overflow-tooltip sortable />
        <el-table-column prop="salary" label="薪水" show-overflow-tooltip sortable />
        <el-table-column prop="note" label="情况说明" show-overflow-tooltip sortable />
        <el-table-column prop="createBy" label="记录创建者" show-overflow-tooltip sortable />
        <el-table-column prop="createTime" label="记录创建时间" show-overflow-tooltip sortable />
        <el-table-column prop="modifyBy" label="记录最后修改者" show-overflow-tooltip sortable />
        <el-table-column prop="modifyTime" label="记录最后修改时间" show-overflow-tooltip sortable />
        <el-table-column prop="datestamp" label="时间戳" show-overflow-tooltip sortable />
        <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
        <el-table-column prop="deleted" label="删除状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())" />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="180">
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
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.name"
            placeholder="请输入姓名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="mainDataForm.editingRecord.sex" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.SEX())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出生年月" prop="birthday">
          <el-input
            v-model="mainDataForm.editingRecord.birthday"
            placeholder="请输入出生年月"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="职称" prop="techTitle">
          <el-cascader
            ref="techTitleEditCascaderId"
            v-model="cascaderValue.techTitle"
            :props="techTitleCascaderProps"
            style="width: 50%;"
            @change="techTitleEditCascaderChangeEvent"
          />
          {{ mainDataForm.editingRecord.techTitle }}
        </el-form-item>
        <el-form-item label="薪水" prop="salary">
          <el-input
            v-model="mainDataForm.editingRecord.salary"
            placeholder="请输入薪水"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="情况说明" prop="note">
          <el-input
            v-model="mainDataForm.editingRecord.note"
            placeholder="请输入情况说明"
            clearable
            :style="{width: '100%'}"
          />
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
        <el-button type="warning" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
  </div>
</template>
<script>
import {
  fetchDemoEmpPage,
  saveDemoEmp,
  delDemoEmp
} from '@/api/generated/demo-emp-scene1'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'DemoEmp',
  components: {},
  data() {
    const that = this
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          name: '',
          sex: '',
          birthday: '',
          techTitle: '',
          salary: '',
          note: '',
          enabled: '1',
          deleted: '1',
          remarks: '无'
        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          guid: '',
          name: '',
          sex: '',
          birthday: '',
          techTitle: '',
          salary: '',
          note: '',
          createBy: '',
          createTime: '',
          modifyBy: '',
          modifyTime: '',
          datestamp: '',
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
        this.$commonDicType.SEX(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      techTitleCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.techTitleLazyLoadData(node, resolve)
        }
      },
      cascaderValue: {
        techTitle: []
      },
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '请输入性别',
          trigger: 'blur'
        }],
        birthday: [{
          required: true,
          message: '请输入出生年月',
          trigger: 'blur'
        }],
        techTitle: [{
          required: true,
          message: '请输入职称',
          trigger: 'blur'
        }],
        salary: [{
          required: true,
          message: '请输入薪水',
          trigger: 'blur'
        }],
        note: [{
          required: true,
          message: '请输入情况说明',
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
      const response = await fetchDemoEmpPage(this.filterDrawer.formData)
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
        this.awaitDelDemoEmp(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelDemoEmp(guid) {
      const guidVO = {
        guid
      }
      const result = await delDemoEmp(guidVO)
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
      const response = await saveDemoEmp(this.mainDataForm.editingRecord)
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
    },
    techTitleLazyLoadData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), 'top', resolve)
      } else {
        const {
          value
        } = node
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), value, resolve)
      }
    },
    techTitleEditCascaderChangeEvent(value) {
      const code = value[value.length - 1]
      const name = this.$refs.techTitleEditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
      this.mainDataForm.editingRecord.techTitle = code + '_' + name
    },
    techTitleQueryCascaderChangeEvent(value) {
      if (value.length === 0) {
        this.filterDrawer.formData.techTitle = ''
      } else {
        const code = value[value.length - 1]
        const name = this.$refs.techTitleQueryCascaderId.getCheckedNodes()[0].pathLabels.join('/')
        this.filterDrawer.formData.techTitle = code + '_' + name
      }
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
