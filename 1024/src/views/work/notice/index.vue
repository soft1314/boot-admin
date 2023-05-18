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
          <el-form-item label="关键词" :label-width="filterDrawer.formLabelWidth" prop="keyword">
            <el-input
              v-model="filterDrawer.formData.keyword"
              placeholder="请输入关键词"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="是否已阅读" :label-width="filterDrawer.formLabelWidth" prop="haveRead">
            <el-select v-model="filterDrawer.formData.haveRead" placeholder="请选择是否已阅读" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.YESNO())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <!--          <el-form-item label="发布时间" prop="releaseTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.releaseTime" type="date" placeholder="选择日期" />
          </el-form-item> -->
          <el-form-item label="发布人" :label-width="filterDrawer.formLabelWidth" prop="releaseBy">
            <el-input
              v-model="filterDrawer.formData.releaseBy"
              placeholder="请输入发布人"
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
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column
          prop="notifyType"
          label="类别"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.NOTIFY_TYPE())"
        />
        <el-table-column prop="title" label="标题" show-overflow-tooltip sortable />
        <el-table-column prop="summary" label="内容概要" show-overflow-tooltip sortable />
        <el-table-column prop="keyword" label="关键词" show-overflow-tooltip sortable />
        <el-table-column align="center" label="正文及附件" show-overflow-tooltip min-width="70">
          <template slot-scope="scope">
            <el-button size="least" type="success" @click="handleMainBodyButtonInRow(scope.row)">阅读</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="haveRead"
          label="是否已阅读"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())"
        />
        <el-table-column
          prop="readTime"
          label="阅读时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column prop="releaseBy" label="发布人" show-overflow-tooltip sortable />
        <el-table-column
          prop="releaseTime"
          label="发布时间"
          show-overflow-tooltip
          sortable
          min-width="110"
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
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
  </div>
</template>
<script>
import TinymceView from '@/components/TinymceView'
import MultiUpload from '@/views/components/upload/MutiUpload'
import {
  fetchUserNotifyRecordPageByUserGuid,
  setNotifyReaded
} from '@/api/to-notify-record'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
import {
  fetchNotifyContent
} from '@/api/to-notify-scene1'
export default {
  name: 'MyNotify',
  components: {
    TinymceView,
    MultiUpload
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
      empCheckForm: {
        dialogVisible: false
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          haveRead: null,
          notifyType: '',
          title: '',
          keyword: '',
          releaseTime: '',
          releaseBy: '',
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
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
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.NOTIFY_TYPE(),
        this.$commonDicType.YESNO(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      cascaderValue: {}
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
      const response = await fetchUserNotifyRecordPageByUserGuid(this.filterDrawer.formData)
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
    handleMainBodyButtonInRow(row) {
      row.haveRead = '1'
      row.readTime = new Date()
      this.mainContentViewForm.notifyGuid = row.guid
      this.asyncSetReaed(row)
      this.asyncHandleViewMainBodyButtonInRow(row)
      this.uploadForm.uploadData.mainGuid = row.guid
    },
    async asyncHandleViewMainBodyButtonInRow(row) {
      this.listLoading = true
      const response = await fetchNotifyContent(row.guid)
      this.listLoading = false
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
    async asyncSetReaed(row) {
      const response = await setNotifyReaded(row.guid)
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
</style>
