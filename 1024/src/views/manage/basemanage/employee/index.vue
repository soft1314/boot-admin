<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2022-12-5 -->
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
          <el-form-item label="行政区划" :label-width="filterDrawer.formLabelWidth" prop="admDivCode">
            {{ filterDrawer.formData.admDivCode }}-{{ filterDrawer.formData.admDivName }}
          </el-form-item>
          <el-form-item label="组织机构" :label-width="filterDrawer.formLabelWidth" prop="orgCode">
            {{ filterDrawer.formData.orgCode }}-{{ filterDrawer.formData.orgName }}
          </el-form-item>
          <el-form-item label="姓名" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input v-model="filterDrawer.formData.name" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="性别" :label-width="filterDrawer.formLabelWidth" prop="sex">
            <el-select v-model="filterDrawer.formData.sex" placeholder="请选择性别" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.SEX())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="入职时间" prop="entryTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.entryTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.birthday" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="职务" :label-width="filterDrawer.formLabelWidth" prop="job">
            <el-input v-model="filterDrawer.formData.job" placeholder="请输入职务" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="行政级" :label-width="filterDrawer.formLabelWidth" prop="adminLevel">
            <el-input
              v-model="filterDrawer.formData.adminLevel"
              placeholder="请输入行政级"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="技术级" :label-width="filterDrawer.formLabelWidth" prop="techLevel">
            <el-input
              v-model="filterDrawer.formData.techLevel"
              placeholder="请输入技术级"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="职称" :label-width="filterDrawer.formLabelWidth" prop="techTitle">
            <el-cascader
              ref="techTitleQueryCascaderId"
              :props="techTitleCascaderProps"
              clearable
              style="width: 100%;"
              @change="techTitleQueryCascaderChangeEvent"
            />
            {{ filterDrawer.formData.techTitle }}
          </el-form-item>
          <el-form-item label="办公电话" :label-width="filterDrawer.formLabelWidth" prop="telephone">
            <el-input
              v-model="filterDrawer.formData.telephone"
              placeholder="请输入办公电话"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="手机号码" :label-width="filterDrawer.formLabelWidth" prop="mobile">
            <el-input
              v-model="filterDrawer.formData.mobile"
              placeholder="请输入手机号码"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="地址" :label-width="filterDrawer.formLabelWidth" prop="address">
            <el-input
              v-model="filterDrawer.formData.address"
              placeholder="请输入地址"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="启用状态" :label-width="filterDrawer.formLabelWidth" prop="enabled">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.ENABLED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="备注" :label-width="filterDrawer.formLabelWidth" prop="remarks">
            <el-input
              v-model="filterDrawer.formData.remarks"
              placeholder="请输入备注"
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
    <div ref="regionContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <i class="el-icon-location" />
      行政区划:<el-cascader
        ref="cascaderAdminDivId"
        v-model="currentRegion"
        :props="regionCascaderProps"
        style="width: 600px;"
        @change="regionCascaderChangeEvent"
      />
      <i class="el-icon-s-home" />
      组织机构:<el-cascader
        ref="cascaderOrgId"
        v-model="currentOrg"
        :props="organiztionCascaderProps"
        style="width: 600px;"
        :options="orgCascaderOptions"
        @change="orgCascaderChangeEvent"
      />
    </div>
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
                <span>{{ props.row.guid }}</span>
              </el-form-item>
              <el-form-item label="行政区划">
                <span>{{ props.row.admDivCode }}-{{ props.row.admDivName }}</span>
              </el-form-item>
              <el-form-item label="组织机构">
                <span>{{ props.row.orgCode }}-{{ props.row.orgName }}</span>
              </el-form-item>
              <el-form-item label="姓名">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="性别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.SEX()),props.row.sex) }}</span>
              </el-form-item>
              <el-form-item label="入职时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.entryTime) }}</span>
              </el-form-item>
              <el-form-item label="出生日期">
                <span>{{ $commonUtils.dateTimeFormat(props.row.birthday) }}</span>
              </el-form-item>
              <el-form-item label="职务">
                <span>{{ props.row.job }}</span>
              </el-form-item>
              <el-form-item label="行政级">
                <span>{{ props.row.adminLevel }}</span>
              </el-form-item>
              <el-form-item label="技术级">
                <span>{{ props.row.techLevel }}</span>
              </el-form-item>
              <el-form-item label="职称">
                <span>{{ props.row.techTitle }}</span>
              </el-form-item>
              <el-form-item label="办公电话">
                <span>{{ props.row.telephone }}</span>
              </el-form-item>
              <el-form-item label="手机号码">
                <span>{{ props.row.mobile }}</span>
              </el-form-item>
              <el-form-item label="地址">
                <span>{{ props.row.address }}</span>
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
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column prop="orgName" label="组织机构" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="姓名" show-overflow-tooltip sortable />
        <el-table-column
          prop="sex"
          label="性别"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.SEX())"
        />
        <el-table-column
          prop="entryTime"
          label="入职时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="birthday"
          label="出生日期"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateColFormatter(row,column,cellValue)"
        />
        <el-table-column prop="job" label="职务" show-overflow-tooltip sortable />
        <el-table-column prop="adminLevel" label="行政级" show-overflow-tooltip sortable />
        <el-table-column prop="techLevel" label="技术级" show-overflow-tooltip sortable />
        <el-table-column prop="techTitle" label="职称" show-overflow-tooltip sortable />
        <el-table-column prop="telephone" label="办公电话" show-overflow-tooltip sortable />
        <el-table-column prop="mobile" label="手机号码" show-overflow-tooltip sortable />
        <el-table-column prop="address" label="地址" show-overflow-tooltip sortable />
        <el-table-column
          prop="enabled"
          label="启用状态"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())"
        />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable width="50" />
        <el-table-column align="center" label="操作" show-overflow-tooltip width="180">
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
        <el-form-item label="行政区划" prop="admDivCode">
          {{ mainDataForm.editingRecord.admDivCode }}-{{ mainDataForm.editingRecord.admDivName }}
        </el-form-item>
        <el-form-item label="组织机构" prop="orgCode">
          {{ mainDataForm.editingRecord.orgCode }}-{{ mainDataForm.editingRecord.orgName }}
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input v-model="mainDataForm.editingRecord.name" placeholder="请输入姓名" clearable :style="{width: '100%'}" />
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
        <el-form-item label="入职时间" prop="entryTime">
          <el-date-picker v-model="mainDataForm.editingRecord.entryTime" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker v-model="mainDataForm.editingRecord.birthday" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="职务" prop="job">
          <el-input v-model="mainDataForm.editingRecord.job" placeholder="请输入职务" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="行政级" prop="adminLevel">
          <el-input
            v-model="mainDataForm.editingRecord.adminLevel"
            placeholder="请输入行政级"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="技术级" prop="techLevel">
          <el-input
            v-model="mainDataForm.editingRecord.techLevel"
            placeholder="请输入技术级"
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
        <el-form-item label="办公电话" prop="telephone">
          <el-input
            v-model="mainDataForm.editingRecord.telephone"
            placeholder="请输入办公电话"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input
            v-model="mainDataForm.editingRecord.mobile"
            placeholder="请输入手机号码"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input
            v-model="mainDataForm.editingRecord.address"
            placeholder="请输入地址"
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
        <el-button type="primary" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
  </div>
</template>
<script>
import {
  fetchTbEmpPage,
  saveTbEmp,
  delTbEmp
} from '@/api/tb-emp-scene1'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
import {
  fetchTbAdmDivChildrenNode,
  fetchTbAdmDivDefaultCascaderValue
} from '@/api/tb-adm-div-scene2.js'
import {
  fetchOrgChildrenNode
} from '@/api/tb-organization-scene2.js'
export default {
  name: 'TbEmp',
  components: {},
  data() {
    const that = this
    return {
      loading: false,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          admDivCode: '',
          admDivName: '',
          orgCode: '',
          orgName: '',
          name: '',
          sex: '',
          entryTime: null,
          birthday: null,
          job: '',
          adminLevel: '',
          techLevel: '',
          techTitle: '',
          telephone: '',
          mobile: '',
          address: '',
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
          entryTime: null,
          birthday: null,
          job: '',
          adminLevel: null,
          techLevel: null,
          techTitle: '',
          telephone: '',
          mobile: '',
          address: '',
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
        this.$commonDicType.SEX(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      currentRegion: [],
      regionCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.lazyLoadRegionData(node, resolve)
        }
      },
      currentOrg: [],
      orgCascaderOptions: [],
      organiztionCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.lazyLoadOrgData(node, resolve)
        }
      },
      techTitleCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.techTitleLazyLoadData(node, resolve)
        }
      },
      cascaderValue: {
        job: [],
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
        entryTime: [{
          required: true,
          message: '请输入入职时间',
          trigger: 'blur'
        }],
        birthday: [{
          required: true,
          message: '请输入出生日期',
          trigger: 'blur'
        }],
        job: [{
          required: true,
          message: '请输入职务',
          trigger: 'blur'
        }],
        adminLevel: [{
          required: true,
          message: '请输入行政级',
          trigger: 'blur'
        }],
        techLevel: [{
          required: true,
          message: '请输入技术级',
          trigger: 'blur'
        }],
        techTitle: [{
          required: true,
          message: '请输入职称',
          trigger: 'blur'
        }],
        telephone: [{
          required: true,
          message: '请输入办公电话',
          trigger: 'blur'
        }],
        mobile: [{
          required: true,
          message: '请输入手机号码',
          trigger: 'blur'
        }],
        address: [{
          required: true,
          message: '请输入地址',
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
    this.loadTbAdmDivDefaultCascaderValue()
    this.loadAllOptions()
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
      const response = await fetchTbEmpPage(this.filterDrawer.formData)
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
        this.awaitDelTbEmp(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelTbEmp(guid) {
      const guidVO = {
        guid
      }
      const result = await delTbEmp(guidVO)
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
      if (this.mainDataForm.editingRecord.orgCode === '') {
        this.$message({
          message: '请先选择组织机构',
          type: 'warning'
        })
      } else {
        this.mainDataForm.mainDataFormDialogVisible = true
      }
    },
    initmainDataForm() {
      this.mainDataForm.editingRecord.guid = ''
      this.mainDataForm.editingRecord.admDivCode = this.getAdminDivCascaderCurrentValue()
      this.mainDataForm.editingRecord.admDivName = this.getAdminDivCascaderCurrentLable()
      this.mainDataForm.editingRecord.orgCode = this.getOrgCascaderCurrentValue()
      this.mainDataForm.editingRecord.orgName = this.getOrgCascaderCurrentLable()
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
      const response = await saveTbEmp(this.mainDataForm.editingRecord)
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
    jobLazyLoadData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadLazyCodeNode(this.$commonDicType.TEST(), 'top', resolve)
      } else {
        const {
          value
        } = node
        this.loadLazyCodeNode(this.$commonDicType.TEST(), value, resolve)
      }
    },
    jobEditCascaderChangeEvent(value) {
      const code = value[value.length - 1]
      const name = this.$refs.jobEditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
      this.mainDataForm.editingRecord.job = code + '_' + name
    },
    jobQueryCascaderChangeEvent(value) {
      if (value.length === 0) {
        this.filterDrawer.formData.job = ''
      } else {
        const code = value[value.length - 1]
        const name = this.$refs.jobQueryCascaderId.getCheckedNodes()[0].pathLabels.join('/')
        this.filterDrawer.formData.job = code + '_' + name
      }
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
    regionCascaderChangeEvent(value) {
      this.filterDrawer.formData.admDivCode = this.getAdminDivCascaderCurrentValue()
      this.filterDrawer.formData.admDivName = this.getAdminDivCascaderCurrentLable()
      const admDivCode = this.getAdminDivCascaderCurrentValue()
      this.loadOrgTopNode(admDivCode)
    },
    orgCascaderChangeEvent(value) {
      this.filterDrawer.formData.orgCode = this.getOrgCascaderCurrentValue()
      this.filterDrawer.formData.orgName = this.getOrgCascaderCurrentLable()
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
    lazyLoadRegionData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadRegionChildrenNode('top', resolve)
      } else {
        const {
          value
        } = node
        this.loadRegionChildrenNode(value, resolve)
      }
    },
    lazyLoadOrgData(node, resolve) {
      const {
        level
      } = node
      const admDivCode = this.getAdminDivCascaderCurrentValue()
      if (admDivCode === '') {
        return
      }
      if (level === 0) {
        this.loadOrgChildrenNode('top', admDivCode, resolve)
      } else {
        const {
          value
        } = node
        this.loadOrgChildrenNode(value, admDivCode, resolve)
      }
    },
    async loadRegionChildrenNode(code, resolve) {
      this.listLoading = true
      const response = await fetchTbAdmDivChildrenNode(code)
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
    async loadOrgChildrenNode(code, admDivCode, resolve) {
      this.listLoading = true
      const response = await fetchOrgChildrenNode(code, admDivCode)
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
    async loadOrgTopNode(admDivCode) {
      this.listLoading = true
      const response = await fetchOrgChildrenNode('top', admDivCode)
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
      this.orgCascaderOptions = data
    },
    async loadTbAdmDivDefaultCascaderValue() {
      const response = await fetchTbAdmDivDefaultCascaderValue()
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
      this.currentRegion = data.value
      this.filterDrawer.formData.admDivCode = this.getAdminDivCascaderCurrentValue()
      this.filterDrawer.formData.admDivName = data.leafLabel
      const admDivCode = this.getAdminDivCascaderCurrentValue()
      this.loadOrgTopNode(admDivCode)
      this.getMainTableData()
    },
    loadTopLevelWhenMounted() {
      this.filterDrawer.formData.admDivCode = this.getAdminDivCascaderCurrentValue()
      this.filterDrawer.formData.admDivName = this.getAdminDivCascaderCurrentLable()
    },
    getAdminDivCascaderCurrentValue() {
      if (this.currentRegion.length > 0) {
        return this.currentRegion[this.currentRegion.length - 1]
      } else {
        return ''
      }
    },
    getAdminDivCascaderCurrentLable() {
      const checkedNode = this.$refs['cascaderAdminDivId'].getCheckedNodes()
      if (checkedNode.length === 1) {
        return checkedNode[0].label
      } else {
        return ''
      }
    },
    getOrgCascaderCurrentValue() {
      if (this.currentOrg.length > 0) {
        return this.currentOrg[this.currentOrg.length - 1]
      } else {
        return ''
      }
    },
    getOrgCascaderCurrentLable() {
      const checkedNode = this.$refs['cascaderOrgId'].getCheckedNodes()
      if (checkedNode.length === 1) {
        return checkedNode[0].label
      } else {
        return ''
      }
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
