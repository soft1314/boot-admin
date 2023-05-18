<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-5-8 20:37:53 -->
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
          <el-form-item label="主键" :label-width="filterDrawer.formLabelWidth" prop="guid">
            <el-input
              v-model="filterDrawer.formData.guid"
              placeholder="请输入主键"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="类别" :label-width="filterDrawer.formLabelWidth" prop="notifyType">
            <el-select v-model="filterDrawer.formData.notifyType" placeholder="请选择类别" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.NOTIFY_TYPE())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标题" :label-width="filterDrawer.formLabelWidth" prop="title">
            <el-input
              v-model="filterDrawer.formData.title"
              placeholder="请输入标题"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="内容概要" :label-width="filterDrawer.formLabelWidth" prop="summary">
            <el-input
              v-model="filterDrawer.formData.summary"
              placeholder="请输入内容概要"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="关键词" :label-width="filterDrawer.formLabelWidth" prop="keyword">
            <el-input
              v-model="filterDrawer.formData.keyword"
              placeholder="请输入关键词"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="是否已发布" :label-width="filterDrawer.formLabelWidth" prop="haveRelease">
            <el-select v-model="filterDrawer.formData.haveRelease" placeholder="请选择是否已发布" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.YESNO())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="发布时间" prop="releaseTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.releaseTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="发布人" :label-width="filterDrawer.formLabelWidth" prop="releaseBy">
            <el-input
              v-model="filterDrawer.formData.releaseBy"
              placeholder="请输入发布人"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录创建者" :label-width="filterDrawer.formLabelWidth" prop="createBy">
            <el-input
              v-model="filterDrawer.formData.createBy"
              placeholder="请输入记录创建者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录创建时间" prop="createTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.createTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="记录最后修改者" :label-width="filterDrawer.formLabelWidth" prop="modifyBy">
            <el-input
              v-model="filterDrawer.formData.modifyBy"
              placeholder="请输入记录最后修改者"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="记录最后修改时间" prop="modifyTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.modifyTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="时间戳" prop="datestamp" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.datestamp" type="date" placeholder="选择日期" />
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
          <el-form-item label="删除状态" :label-width="filterDrawer.formLabelWidth" prop="deleted">
            <el-select v-model="filterDrawer.formData.deleted" placeholder="请选择删除状态" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.DELETED())"
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
      <el-table v-loading="loading" :data="mainTableData" border fit style="width: 100%" max-height="500">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="主键">
                <span>{{ props.row.guid }}</span>
              </el-form-item>
              <el-form-item label="类别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.NOTIFY_TYPE()),props.row.notifyType) }}</span>
              </el-form-item>
              <el-form-item label="标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="内容概要">
                <span>{{ props.row.summary }}</span>
              </el-form-item>
              <el-form-item label="关键词">
                <span>{{ props.row.keyword }}</span>
              </el-form-item>
              <el-form-item label="是否已发布">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.YESNO()),props.row.haveRelease) }}</span>
              </el-form-item>
              <el-form-item label="发布时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.releaseTime) }}</span>
              </el-form-item>
              <el-form-item label="发布人">
                <span>{{ props.row.releaseBy }}</span>
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
        <!--        <el-table-column type="selection" width="40" />
 -->
        <el-table-column type="index" label="序号" :index="indexMethod" width="50" />
        <el-table-column
          prop="notifyType"
          label="类别"
          show-overflow-tooltip
          sortable
          width="80"
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.NOTIFY_TYPE())"
        />
        <el-table-column prop="title" label="标题" show-overflow-tooltip sortable align="left" />
        <!--        <el-table-column prop="summary" label="内容概要" show-overflow-tooltip sortable />
 -->        <!-- <el-table-column prop="keyword" label="关键词" show-overflow-tooltip sortable /> -->
        <el-table-column align="center" label="正文及附件" width="70">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.haveRelease==='0'"
              size="least"
              type="primary"
              @click="handleEditMainBodyButtonInRow(scope.row)"
            >编辑</el-button>
            <el-button
              v-if="scope.row.haveRelease==='1'"
              size="least"
              type="success"
              @click="handleViewMainBodyButtonInRow(scope.row)"
            >查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="haveRelease"
          label="是否已发布"
          show-overflow-tooltip
          sortable
          width="50"
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())"
        />
        <el-table-column
          prop="releaseTime"
          label="发布时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <!--        <el-table-column prop="releaseBy" label="发布人" show-overflow-tooltip sortable />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable /> -->
        <el-table-column align="center" label="通知范围设置与跟踪">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleAdmDivRowButton(scope.row)">区划</el-button>
            <el-button size="least" type="primary" @click="handleOrgRowButton(scope.row)">单位</el-button>
            <el-button size="least" type="primary" @click="handleEmpRowButton(scope.row)">人员</el-button>
            <el-button size="least" type="success" @click="handleTrackButton(scope.row)">跟踪</el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.haveRelease==='0'" size="least" type="primary" @click="handleEditRow(scope.row)">修改</el-button>
            <el-button size="least" type="primary" @click="handleReleaseRow(scope.row)">发布</el-button>
            <el-button v-if="scope.row.haveRelease==='0'" size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
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
        <el-form-item label="类别" prop="notifyType">
          <el-select v-model="mainDataForm.editingRecord.notifyType" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.NOTIFY_TYPE())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="mainDataForm.editingRecord.title" placeholder="请输入标题" clearable :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="内容概要" prop="summary">
          <el-input
            v-model="mainDataForm.editingRecord.summary"
            type="textarea"
            placeholder="请输入内容概要"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="关键词" prop="keyword">
          <el-input
            v-model="mainDataForm.editingRecord.keyword"
            placeholder="请输入关键词"
            clearable
            :style="{width: '100%'}"
          />
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
    <!-- 按区划维护通知范围 start-->
    <AdmDivTree ref="admDivTreeComponent" @sendDataToParent="receiveDataFromAdmDivTreeChild" />
    <!-- 按区划维护通知范围 end -->
    <!-- 按组织机构维护通知范围对话框start -->
    <CheckOrg ref="checkOrgComponent" />
    <!-- 按组织机构维护通知范围对话框end -->
    <!-- 按人员维护通知范围对话框start -->
    <CheckEmp ref="checkEmpComponent" />
    <!-- 按人员维护通知范围对话框end -->
    <!-- 正文编辑对话框 -->
    <el-dialog :visible.sync="tinymceForm.dialogVisible" width="80%" :close-on-click-modal="false" title="正文及附件">
      <tinymce
        v-if="tinymceForm.dialogVisible"
        ref="tinemceEditComponent"
        v-model="tinymceForm.content"
        :height="300"
      />
      <div style="margin-top: 10px;">
        <MultiUpload
          ref="attachmentUploadComponent"
          append-to-body
          :can-upload="true"
          :attach-main-id="tinymceForm.notifyGuid"
          :upload-data="uploadForm.uploadData"
          :limit="3"
          :multiple="true"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="tinymceForm.dialogVisible=false">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveTinymceContent()">
          保存
        </el-button>
        <el-button type="warning" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 正文查看对话框 -->
    <el-dialog
      :visible.sync="mainContentViewForm.dialogVisible"
      width="80%"
      :close-on-click-modal="false"
      title="正文及附件"
    >
      <TinymceView
        v-if="mainContentViewForm.dialogVisible"
        ref="tinemceViewComponent"
        v-model="mainContentViewForm.content"
        :height="300"
      />
      <div style="margin-top: 10px;">
        <MultiUpload
          ref="attachmentViewComponent"
          append-to-body
          :can-upload="false"
          :attach-main-id="tinymceForm.notifyGuid"
          :upload-data="uploadForm.uploadData"
          :limit="3"
          :multiple="true"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="mainContentViewForm.dialogVisible=false">
          关闭
        </el-button>
      </div>
    </el-dialog>
    <!-- 阅读追踪start -->
    <TrackNotify ref="trackNotifyComponent" />
    <!-- 阅读追踪end -->
  </div>
</template>
<script>
import Tinymce from '@/components/Tinymce'
import TinymceView from '@/components/TinymceView'
import AdmDivTree from '@/views/components/AdmDivTree.vue'
import CheckOrg from '@/views/work/notice/components/CheckOrg.vue'
import CheckEmp from '@/views/work/notice/components/CheckEmp.vue'
import MultiUpload from '@/views/components/upload/MutiUpload'
import TrackNotify from '@/views/work/notice/components/TrackNotify.vue'
import {
  fetchAdmDivDefaultCheckedKeys,
  fetchAdmDivDefaultExpandedKeys
} from '@/api/admdiv'
import {
  fetchToNotifyPage,
  saveToNotify,
  delToNotify,
  saveTrNotifyDiv,
  sendNotify,
  preSendNotify,
  fetchNotifyContent,
  saveNotifyContent
} from '@/api/to-notify-scene1'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'Notify',
  components: {
    Tinymce,
    TinymceView,
    AdmDivTree,
    CheckOrg,
    CheckEmp,
    MultiUpload,
    TrackNotify
  },
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          notifyType: '',
          title: '',
          summary: '',
          keyword: '',
          haveRelease: '',
          releaseTime: null,
          releaseBy: '',
          enabled: '1',
          deleted: '1',
          remarks: '无'
        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      tinymceForm: {
        dialogVisible: false,
        notifyGuid: '',
        content: ''
      },
      mainContentViewForm: {
        dialogVisible: false,
        notifyGuid: '',
        content: ''
      },
      uploadForm: {
        canUpload: true,
        clear: false,
        uploadData: {
          mainGuid: '',
          mainTableName: 'tb_notify',
          mainStyle: 'common'
        },
        fileString: []
      },
      empCheckForm: {
        dialogVisible: false
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          guid: '',
          notifyType: '',
          title: '',
          summary: '',
          keyword: '',
          haveRelease: '',
          releaseTime: '',
          releaseBy: '',
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
        this.$commonDicType.NOTIFY_TYPE(),
        this.$commonDicType.YESNO(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      cascaderValue: {},
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        notifyType: [{
          required: true,
          message: '请输入类别',
          trigger: 'blur'
        }],
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        summary: [{
          required: true,
          message: '请输入内容概要',
          trigger: 'blur'
        }],
        keyword: [{
          required: true,
          message: '请输入关键词',
          trigger: 'blur'
        }],
        haveRelease: [{
          required: true,
          message: '请输入是否已发布',
          trigger: 'blur'
        }],
        releaseTime: [{
          required: true,
          message: '请输入发布时间',
          trigger: 'blur'
        }],
        releaseBy: [{
          required: true,
          message: '请输入发布人',
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
      const response = await fetchToNotifyPage(this.filterDrawer.formData)
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
    async handleReleaseRow(row) {
      const guidVO = {
        guid: row.guid
      }
      this.loading = false
      const response = await preSendNotify(guidVO)
      this.loading = false
      debugger
      if (this.$commonResultCode.SUCCESS() === response.code) {
        this.$confirm(response.message + '是否发布?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.asyncReleaseRow(row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
      } else {
        this.$message({
          message: response.message,
          type: 'warning'
        })
      }
    },
    async asyncReleaseRow(row) {
      const guidVO = {
        guid: row.guid
      }
      this.loading = false
      const response = await sendNotify(guidVO)
      this.loading = false
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.getMainTableData()
    },
    handleAdmDivRowButton(row) {
      this.fetchAdmDivTreeDefaultExpandedKeys(row.guid)
    },
    handleOrgRowButton(row) {
      this.$nextTick(() => {
        this.$refs.checkOrgComponent.showDialog(row.guid)
      })
    },
    handleEmpRowButton(row) {
      this.$nextTick(() => {
        this.$refs.checkEmpComponent.showDialog(row.guid)
      })
    },
    handleTrackButton(row) {
      this.$nextTick(() => {
        this.$refs.trackNotifyComponent.showDialog(row.guid)
      })
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelToNotify(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelToNotify(guid) {
      const guidVO = {
        guid
      }
      const result = await delToNotify(guidVO)
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
      const response = await saveToNotify(this.mainDataForm.editingRecord)
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
    async loadLazyCodeNode(dicType, code, resolve) {
      this.loading = true
      const response = await lazyFetchDictionaryNode(dicType, code)
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
      // 通过调用resolve将子节点数据返回，通知组件数据加载完成
      resolve(data)
    },
    /**
       * 获取行政区划树缺省初始展开的树枝
       */
    async fetchAdmDivTreeDefaultCheckedKeys(notifyGuid) {
      const response = await fetchAdmDivDefaultCheckedKeys(notifyGuid)
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
      this.$nextTick(() => {
        this.$refs.admDivTreeComponent.pushTreedefaultCheckedKeys(data)
      })
    },
    /**
       * 获取行政区划树缺省初始勾选的枝叶
       */
    async fetchAdmDivTreeDefaultExpandedKeys(notifyGuid) {
      const response = await fetchAdmDivDefaultExpandedKeys(notifyGuid)
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
      this.$nextTick(() => {
        this.$refs.admDivTreeComponent.pushTreedefaultExpandedKeys(data)
        this.$refs.admDivTreeComponent.showDialog(notifyGuid)
        this.$refs.admDivTreeComponent.pushDialogTitle('拟通知到所勾选区划下所有用户')
        this.fetchAdmDivTreeDefaultCheckedKeys(notifyGuid)
      })
    },
    receiveDataFromAdmDivTreeChild(checkedCodes, notifyGuid) {
      const vo = {
        notifyGuid: notifyGuid,
        admDivCodes: checkedCodes
      }
      this.saveNotifyDiv(vo)
    },
    async saveNotifyDiv(vo) {
      const response = await saveTrNotifyDiv(vo)
      this.$message({
        message: response.message,
        type: 'warning'
      })
    },
    handleEditMainBodyButtonInRow(row) {
      this.tinymceForm.notifyGuid = row.guid
      this.uploadForm.uploadData.mainGuid = row.guid
      this.asyncHandleEditMainBodyButtonInRow(row)
    },
    async asyncHandleEditMainBodyButtonInRow(row) {
      this.loading = true
      const response = await fetchNotifyContent(row.guid)
      this.loading = false
      if (this.$commonResultCode.SUCCESS() !== response.code) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.tinymceForm.content = ''
        return
      }
      const {
        data
      } = response
      this.tinymceForm.content = data
      this.tinymceForm.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.attachmentUploadComponent.setMainData(this.uploadForm.uploadData.mainTableName, this
          .uploadForm.uploadData.mainStyle, this.uploadForm.uploadData.mainGuid)
      })
    },
    handleViewMainBodyButtonInRow(row) {
      this.mainContentViewForm.notifyGuid = row.guid
      this.asyncHandleViewMainBodyButtonInRow(row)
      this.uploadForm.uploadData.mainGuid = row.guid
    },
    async asyncHandleViewMainBodyButtonInRow(row) {
      this.loading = true
      const response = await fetchNotifyContent(row.guid)
      this.loading = false
      if (this.$commonResultCode.SUCCESS() !== response.code) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.mainContentViewForm.content = ''
        return
      }
      const {
        data
      } = response
      this.mainContentViewForm.content = data
      this.mainContentViewForm.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.attachmentViewComponent.setMainData(this.uploadForm.uploadData.mainTableName, this.uploadForm
          .uploadData.mainStyle, this.uploadForm.uploadData.mainGuid)
      })
    },
    async handleSaveTinymceContent() {
      console.log(this.tinymceForm.content)
      const data = {
        notifyGuid: this.tinymceForm.notifyGuid,
        content: this.tinymceForm.content
      }
      this.loading = true
      const response = await saveNotifyContent(data)
      this.loading = false
      this.$message({
        message: response.message,
        type: 'warning'
      })
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

  .editor-content>>>table {
    border: 1px solid #ccc;
  }

  /*    table th,
    .editor-content table td {
      border: 1px solid #ccc;
    }

    table td {
      padding: 0px 5px;
    } */
  /* } */
</style>
