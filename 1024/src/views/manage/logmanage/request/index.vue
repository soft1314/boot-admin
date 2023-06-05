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
          <el-form-item label="GUID" :label-width="filterDrawer.formLabelWidth" prop="guid">
            <el-input v-model="filterDrawer.formData.guid" placeholder="请输入" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="IP地址" :label-width="filterDrawer.formLabelWidth" prop="operIp">
            <el-input
              v-model="filterDrawer.formData.operIp"
              placeholder="请输入IP地址"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="访问点" :label-width="filterDrawer.formLabelWidth" prop="uri">
            <el-input v-model="filterDrawer.formData.uri" placeholder="请输入访问点" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="异常消息" :label-width="filterDrawer.formLabelWidth" prop="errorMsg">
            <el-input
              v-model="filterDrawer.formData.errorMsg"
              placeholder="请输入异常消息"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="访问方法" :label-width="filterDrawer.formLabelWidth" prop="method">
            <el-input
              v-model="filterDrawer.formData.method"
              placeholder="请输入访问方法"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="请求方式" :label-width="filterDrawer.formLabelWidth" prop="requestMethod">
            <el-input
              v-model="filterDrawer.formData.requestMethod"
              placeholder="请输入请求方式"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="消耗时间" :label-width="filterDrawer.formLabelWidth" prop="costTime">
            <el-input
              v-model="filterDrawer.formData.costTime"
              placeholder="请输入消耗时间"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="业务类型" :label-width="filterDrawer.formLabelWidth" prop="businessType">
            <el-select v-model="filterDrawer.formData.businessType" placeholder="请选择业务类型" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.BUSINESS_TYPE())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="操作模块" :label-width="filterDrawer.formLabelWidth" prop="businessModule">
            <el-input
              v-model="filterDrawer.formData.businessModule"
              placeholder="请输入操作模块"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="操作人员类别" :label-width="filterDrawer.formLabelWidth" prop="operatorType">
            <el-select v-model="filterDrawer.formData.operatorType" placeholder="请选择操作人员类别" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.OPERATOR_TYPE())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="请求参数" :label-width="filterDrawer.formLabelWidth" prop="operParam">
            <el-input
              v-model="filterDrawer.formData.operParam"
              placeholder="请输入请求参数"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="响应结果" :label-width="filterDrawer.formLabelWidth" prop="jsonResult">
            <el-input
              v-model="filterDrawer.formData.jsonResult"
              placeholder="请输入响应结果"
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
              <el-form-item label="">
                <span>{{ props.row.guid }}</span>
              </el-form-item>
              <el-form-item label="IP地址">
                <span>{{ props.row.operIp }}</span>
              </el-form-item>
              <el-form-item label="访问点">
                <span>{{ props.row.uri }}</span>
              </el-form-item>
              <el-form-item label="异常消息">
                <span>{{ props.row.errorMsg }}</span>
              </el-form-item>
              <el-form-item label="访问方法">
                <span>{{ props.row.method }}</span>
              </el-form-item>
              <el-form-item label="请求方式">
                <span>{{ props.row.requestMethod }}</span>
              </el-form-item>
              <el-form-item label="消耗时间">
                <span>{{ props.row.costTime }}</span>
              </el-form-item>
              <el-form-item label="业务类型">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.BUSINESS_TYPE()),props.row.businessType) }}</span>
              </el-form-item>
              <el-form-item label="操作模块">
                <span>{{ props.row.businessModule }}</span>
              </el-form-item>
              <el-form-item label="操作人员类别">
                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.OPERATOR_TYPE()),props.row.operatorType) }}</span>
              </el-form-item>
              <el-form-item label="请求参数" />
              <el-form-item label="请求参数">
                <JsonViewer :value="JSON.parse(props.row.operParam)" />
              </el-form-item>
              <el-form-item label="响应结果" />
              <el-form-item label="响应结果">
                <JsonViewer :value="JSON.parse(props.row.jsonResult)" />
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
        <el-table-column prop="guid" label="" show-overflow-tooltip sortable />
        <el-table-column prop="operIp" label="IP地址" show-overflow-tooltip sortable />
        <el-table-column prop="uri" label="访问点" show-overflow-tooltip sortable />
        <el-table-column prop="errorMsg" label="异常消息" show-overflow-tooltip sortable />
        <el-table-column prop="method" label="访问方法" show-overflow-tooltip sortable />
        <el-table-column prop="requestMethod" label="请求方式" show-overflow-tooltip sortable />
        <el-table-column prop="costTime" label="消耗时间" show-overflow-tooltip sortable />
        <el-table-column
          prop="businessType"
          label="业务类型"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.BUSINESS_TYPE())"
        />
        <el-table-column prop="businessModule" label="操作模块" show-overflow-tooltip sortable />
        <el-table-column
          prop="operatorType"
          label="操作人员类别"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.OPERATOR_TYPE())"
        />
        <el-table-column prop="operParam" label="请求参数" show-overflow-tooltip sortable />
        <el-table-column prop="jsonResult" label="响应结果" show-overflow-tooltip sortable />
        <el-table-column prop="createBy" label="记录创建者" show-overflow-tooltip sortable />
        <el-table-column
          prop="createTime"
          label="记录创建时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column prop="modifyBy" label="记录最后修改者" show-overflow-tooltip sortable />
        <el-table-column
          prop="modifyTime"
          label="记录最后修改时间"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="datestamp"
          label="时间戳"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="enabled"
          label="启用状态"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())"
        />
        <el-table-column
          prop="deleted"
          label="删除状态"
          show-overflow-tooltip
          sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())"
        />
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="180">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleEditRow(scope.row)">备注</el-button>
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
        <el-form-item label="IP地址">
          <span>{{ mainDataForm.editingRecord.operIp }}</span>
        </el-form-item>
        <el-form-item label="访问点">
          <span>{{ mainDataForm.editingRecord.uri }}</span>
        </el-form-item>
        <el-form-item label="异常消息">
          <span>{{ mainDataForm.editingRecord.errorMsg }}</span>
        </el-form-item>
        <el-form-item label="访问方法">
          <span>{{ mainDataForm.editingRecord.method }}</span>
        </el-form-item>
        <el-form-item label="请求方式">
          <span>{{ mainDataForm.editingRecord.requestMethod }}</span>
        </el-form-item>
        <el-form-item label="消耗时间">
          <span>{{ mainDataForm.editingRecord.costTime }}</span>
        </el-form-item>
        <el-form-item label="业务类型">
          <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.BUSINESS_TYPE()),mainDataForm.editingRecord.businessType) }}</span>
        </el-form-item>
        <el-form-item label="操作模块">
          <span>{{ mainDataForm.editingRecord.businessModule }}</span>
        </el-form-item>
        <el-form-item label="操作人员类别">
          <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.OPERATOR_TYPE()),mainDataForm.editingRecord.operatorType) }}</span>
        </el-form-item>
        <el-form-item label="请求参数">
          <JsonViewer :value="mainDataForm.editingRecord.operParamJson" />
        </el-form-item>
        <el-form-item label="响应结果">
          <JsonViewer :value="mainDataForm.editingRecord.jsonResultJson" />
        </el-form-item>
        <el-form-item label="启用状态">
          <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.ENABLED()),mainDataForm.editingRecord.enabled) }}</span>
        </el-form-item>
        <el-form-item label="删除状态">
          <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.DELETED()),mainDataForm.editingRecord.deleted) }}</span>
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
import JsonViewer from 'vue-json-viewer'

import {
  fetchTbLogRequestPage,
  saveTbLogRequest,
  delTbLogRequest
} from '@/api/tb-log-request-scene1'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'TbLogRequest',
  components: {
    JsonViewer
  },
  data() {
    return {
      testjson: { 'deleted': '1', 'enabled': '1', 'guid': '', 'haveRelease': '', 'keyword': '12', 'notifyType': '02', 'releaseBy': '', 'remarks': '无', 'summary': '12', 'title': '12' },
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          operIp: '',
          uri: '',
          errorMsg: '',
          method: '',
          requestMethod: '',
          costTime: '',
          businessType: '',
          businessModule: '',
          operatorType: '',
          operParam: '',
          jsonResult: '',
          enabled: '1',
          deleted: '1',
          remarks: '无',
          operParamJson: {},
          jsonResultJson: {}
        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          guid: '',
          operIp: '',
          uri: '',
          errorMsg: '',
          method: '',
          requestMethod: '',
          costTime: '',
          businessType: '',
          businessModule: '',
          operatorType: '',
          operParam: '',
          jsonResult: '',
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
        this.$commonDicType.BUSINESS_TYPE(),
        this.$commonDicType.OPERATOR_TYPE(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      cascaderValue: {},
      rules: {
        guid: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        operIp: [{
          required: true,
          message: '请输入IP地址',
          trigger: 'blur'
        }],
        uri: [{
          required: true,
          message: '请输入访问点',
          trigger: 'blur'
        }],
        errorMsg: [{
          required: true,
          message: '请输入异常消息',
          trigger: 'blur'
        }],
        method: [{
          required: true,
          message: '请输入访问方法',
          trigger: 'blur'
        }],
        requestMethod: [{
          required: true,
          message: '请输入请求方式',
          trigger: 'blur'
        }],
        costTime: [{
          required: true,
          message: '请输入消耗时间',
          trigger: 'blur'
        }],
        businessType: [{
          required: true,
          message: '请输入业务类型',
          trigger: 'blur'
        }],
        businessModule: [{
          required: true,
          message: '请输入操作模块',
          trigger: 'blur'
        }],
        operatorType: [{
          required: true,
          message: '请输入操作人员类别',
          trigger: 'blur'
        }],
        operParam: [{
          required: true,
          message: '请输入请求参数',
          trigger: 'blur'
        }],
        jsonResult: [{
          required: true,
          message: '请输入响应结果',
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
      const response = await fetchTbLogRequestPage(this.filterDrawer.formData)
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
      this.mainDataForm.mainDataFormDialogTitle = '备注'
      this.mainDataForm.editingRecord = row
      this.mainDataForm.editingRecord.operParamJson = JSON.parse(this.mainDataForm.editingRecord.operParam)
      this.mainDataForm.editingRecord.jsonResultJson = JSON.parse(this.mainDataForm.editingRecord.jsonResult)
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelTbLogRequest(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelTbLogRequest(guid) {
      const guidVO = {
        guid
      }
      const result = await delTbLogRequest(guidVO)
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
      const response = await saveTbLogRequest(this.mainDataForm.editingRecord)
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
  .el-drawer__container ::-webkit-scrollbar {
    display: none;
  }
</style>
