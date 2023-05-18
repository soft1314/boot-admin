<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-4-13 -->
<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="filterDrawer.dialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form ref="drawerForm" class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" :model="filterDrawer.formData">
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
            label="单位名称"
            :label-width="filterDrawer.formLabelWidth"
            prop="name"
          >
            <el-input
              v-model="filterDrawer.formData.name"
              placeholder="请输入单位名称"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="社会信用代码"
            :label-width="filterDrawer.formLabelWidth"
            prop="code"
          >
            <el-input
              v-model="filterDrawer.formData.code"
              placeholder="请输入社会信用代码"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="组织类别"
            :label-width="filterDrawer.formLabelWidth"
            prop="orgtype"
          >
            <el-select
              v-model="filterDrawer.formData.orgtype"
              placeholder="请选择组织类别"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.ORGTYPE())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="行业类别" :label-width="filterDrawer.formLabelWidth" prop="industryCategory">
            <el-cascader ref="industryCategoryQueryCascaderId" :props="industryCategoryCascaderProps" clearable style="width: 100%;" @change="industryCategoryQueryCascaderChangeEvent" />
            {{ filterDrawer.formData.industryCategory }}
          </el-form-item>
          <el-form-item label="成立时间" prop="foundTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.foundTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item
            label="单位级别"
            :label-width="filterDrawer.formLabelWidth"
            prop="rankCode"
          >
            <el-select
              v-model="filterDrawer.formData.rankCode"
              placeholder="请选择单位级别"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.UNITLEVEL())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="编制人数"
            :label-width="filterDrawer.formLabelWidth"
            prop="num"
          >
            <el-input
              v-model="filterDrawer.formData.num"
              placeholder="请输入编制人数"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="办公地址"
            :label-width="filterDrawer.formLabelWidth"
            prop="addr"
          >
            <el-input
              v-model="filterDrawer.formData.addr"
              placeholder="请输入办公地址"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="负责人"
            :label-width="filterDrawer.formLabelWidth"
            prop="leader"
          >
            <el-input
              v-model="filterDrawer.formData.leader"
              placeholder="请输入负责人"
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
          <el-form-item label="记录创建时间" prop="createTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.createTime" type="date" placeholder="选择日期" />
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
          <el-form-item label="记录最后修改时间" prop="modifyTime" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.modifyTime" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="时间戳" prop="datestamp" :label-width="filterDrawer.formLabelWidth">
            <el-date-picker v-model="filterDrawer.formData.datestamp" type="date" placeholder="选择日期" />
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
          <el-form-item
            label="是否末级"
            :label-width="filterDrawer.formLabelWidth"
            prop="leaf"
          >
            <el-select
              v-model="filterDrawer.formData.leaf"
              placeholder="请选择是否末级"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in optionMap.get($commonDicType.YESNO())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="父级GUID"
            :label-width="filterDrawer.formLabelWidth"
            prop="parentGuid"
          >
            <el-input
              v-model="filterDrawer.formData.parentGuid"
              placeholder="请输入父级GUID"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item
            label="顺序号"
            :label-width="filterDrawer.formLabelWidth"
            prop="sort"
          >
            <el-input
              v-model="filterDrawer.formData.sort"
              placeholder="请输入顺序号"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadTopLevel()">顶层</el-button>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadAllLevel()">查询</el-button>
            <el-button size="mini" type="warning" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置</el-button>
            <el-button size="mini" icon="el-icon-close" @click="hideDrawer()">关闭</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!--查询条件和查询按钮区结束-->
    <!--功能按钮区开始-->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-add" @click="handleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="handleClickSearchButton()">
          查询
        </el-button>
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="mainTableData"
      stripe
      border
      highlight-current-row
      fit
      style="width: 100%"
      :header-cell-style="{'text-align':'center'}"
      row-key="guid"
      default-expand-all
      lazy
      :load="loadChildren"
      :tree-props="{hasChildren:'hasChildren'}"
    >
      <el-table-column prop="guid" label="主键" show-overflow-tooltip sortable />
      <el-table-column prop="name" label="单位名称" show-overflow-tooltip sortable />
      <el-table-column prop="code" label="社会信用代码" show-overflow-tooltip sortable />
      <el-table-column prop="orgtype" label="组织类别" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ORGTYPE())" />
      <el-table-column prop="industryCategory" label="行业类别" show-overflow-tooltip sortable />
      <el-table-column prop="foundTime" label="成立时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="rankCode" label="单位级别" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.UNITLEVEL())" />
      <el-table-column prop="num" label="编制人数" show-overflow-tooltip sortable />
      <el-table-column prop="addr" label="办公地址" show-overflow-tooltip sortable />
      <el-table-column prop="leader" label="负责人" show-overflow-tooltip sortable />
      <el-table-column prop="createBy" label="记录创建者" show-overflow-tooltip sortable />
      <el-table-column prop="createTime" label="记录创建时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="modifyBy" label="记录最后修改者" show-overflow-tooltip sortable />
      <el-table-column prop="modifyTime" label="记录最后修改时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="datestamp" label="时间戳" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)" />
      <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
      <el-table-column prop="deleted" label="删除状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.DELETED())" />
      <el-table-column prop="remarks" label="备注" show-overflow-tooltip sortable />
      <el-table-column prop="leaf" label="是否末级" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())" />
      <el-table-column prop="parentGuid" label="父级GUID" show-overflow-tooltip sortable />
      <el-table-column prop="sort" label="顺序号" show-overflow-tooltip sortable />
      <el-table-column label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="least" @click="handleClickEditButton(scope.row)">编辑</el-button>
          <el-button type="primary" size="least" @click="handleAddSubButton(scope.row)">添加</el-button>
          <el-button type="danger" size="least" @click="handleDelRowButton(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <div align="right">
      <!--分页控件开始-->
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
      <!--分页控件结束-->
    </div>
    <!-- 表单区域开始 -->
    <el-dialog
      :visible.sync="mainDataForm.mainDataFormDialogVisible"
      :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle"
    >
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="单位名称" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.name"
            placeholder="请输入单位名称"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="社会信用代码" prop="code">
          <el-input
            v-model="mainDataForm.editingRecord.code"
            placeholder="请输入社会信用代码"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="组织类别" prop="orgtype">
          <el-select v-model="mainDataForm.editingRecord.orgtype" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.ORGTYPE())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="行业类别" prop="industryCategory">
          <el-cascader
            ref="industryCategoryEditCascaderId"
            v-model="cascaderValue.industryCategory"
            :props="industryCategoryCascaderProps"
            style="width: 50%;"
            @change="industryCategoryEditCascaderChangeEvent"
          />
          {{ mainDataForm.editingRecord.industryCategory }}
        </el-form-item>
        <el-form-item label="成立时间" prop="foundTime">
          <el-date-picker v-model="mainDataForm.editingRecord.foundTime" type="date" placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="单位级别" prop="rankCode">
          <el-select v-model="mainDataForm.editingRecord.rankCode" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.UNITLEVEL())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="编制人数" prop="num">
          <el-input
            v-model="mainDataForm.editingRecord.num"
            placeholder="请输入编制人数"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="办公地址" prop="addr">
          <el-input
            v-model="mainDataForm.editingRecord.addr"
            placeholder="请输入办公地址"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="负责人" prop="leader">
          <el-input
            v-model="mainDataForm.editingRecord.leader"
            placeholder="请输入负责人"
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
        <el-form-item label="是否末级" prop="leaf">
          <el-select v-model="mainDataForm.editingRecord.leaf" placeholder="请选择">
            <el-option
              v-for="item in optionMap.get($commonDicType.YESNO())"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="顺序号" prop="sort">
          <el-input
            v-model="mainDataForm.editingRecord.sort"
            placeholder="请输入顺序号"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="mainDataForm.mainDataFormDialogVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="submitMainDataForm()">
          保存
        </el-button>
        <el-button type="warning" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>
<script>
import {
  saveDemoOrg,
  delDemoOrg,
  fetchTopDemoOrgPage,
  fetchDemoOrgPage,
  fetchDemoOrgAllChildren
} from '@/api/generated/demo-org-scene2'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'DemoOrg',
  data() {
    const that = this
    return {
      listLoading: false,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {
          name: '',
          code: '',
          orgtype: '',
          industryCategory: '',
          foundTime: null,
          rankCode: '',
          num: '',
          addr: '',
          leader: '',
          enabled: '1',
          deleted: '1',
          remarks: '无',
          leaf: '0',
          sort: ''

        },
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        showTopOnly: true,
        formData: {
          guid: '',
          name: '',
          code: '',
          orgtype: '',
          industryCategory: '',
          foundTime: '',
          rankCode: '',
          num: '',
          addr: '',
          leader: '',
          createBy: '',
          createTime: '',
          modifyBy: '',
          modifyTime: '',
          datestamp: '',
          enabled: '',
          deleted: '',
          remarks: '',
          leaf: '',
          parentGuid: '',
          sort: '',
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
      treeMap: new Map(),
      radio: false,
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.ORGTYPE(),
        this.$commonDicType.UNITLEVEL(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED(),
        this.$commonDicType.YESNO()
      ],
      industryCategoryCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.industryCategoryLazyLoadData(node, resolve)
        }
      },
      cascaderValue: {
        industryCategory: []
      },
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入单位名称',
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入社会信用代码',
          trigger: 'blur'
        }],
        orgtype: [{
          required: true,
          message: '请输入组织类别',
          trigger: 'blur'
        }],
        industryCategory: [{
          required: true,
          message: '请输入行业类别',
          trigger: 'blur'
        }],
        foundTime: [{
          required: true,
          message: '请输入成立时间',
          trigger: 'blur'
        }],
        rankCode: [{
          required: true,
          message: '请输入单位级别',
          trigger: 'blur'
        }],
        num: [{
          required: true,
          message: '请输入编制人数',
          trigger: 'blur'
        }],
        addr: [{
          required: true,
          message: '请输入办公地址',
          trigger: 'blur'
        }],
        leader: [{
          required: true,
          message: '请输入负责人',
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
        }],
        leaf: [{
          required: true,
          message: '请输入是否末级',
          trigger: 'blur'
        }],
        parentGuid: [{
          required: true,
          message: '请输入父级GUID',
          trigger: 'blur'
        }],
        sort: [{
          required: true,
          message: '请输入顺序号',
          trigger: 'blur'
        }]
      }
    }
  },
  watch: {},
  inject: ['reload'],
  created() {},
  mounted() {
    this.loadAllOptions()
    this.loadTopLevel()
  },
  methods: {
    refresh() {
      this.reload()
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    hideDrawer() {
      this.filterDrawer.dialogVisible = false
    },
    handleClickSearchButton() {
      this.filterDrawer.dialogVisible = true
    },
    loadAllOptions() {
      for (var i = 0; i < this.optionKey.length; i++) {
        this.loadDictionaryOptions(this.optionKey[i], false)
      }
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
    handleLoadTopLevel() {
      this.filterDrawer.formData.currentPage = 1
      this.loadTopLevel()
    },
    async loadTopLevel() {
      this.filterDrawer.showTopOnly = true
      this.listLoading = false
      const response = await fetchTopDemoOrgPage(this.filterDrawer.formData)
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

      this.mainTableData = data.records
      this.filterDrawer.formData.total = data.total
    },
    handleLoadAllLevel() {
      this.filterDrawer.formData.currentPage = 1
      this.loadAllLevel()
    },
    async loadAllLevel() {
      this.filterDrawer.showTopOnly = false
      this.listLoading = false
      const response = await fetchDemoOrgPage(this.filterDrawer.formData)
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

      this.mainTableData = data.records
      this.filterDrawer.formData.total = data.total
    },
    async loadChildren(row, treeNode, resolve) {
      this.treeMap.set(row.guid, {
        row,
        treeNode,
        resolve
      })
      const response = await fetchDemoOrgAllChildren(row.guid)
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
      resolve(data)
    },
    handlePageSizeChange(val) {
      if (val !== this.filterDrawer.formData.pageSize) {
        this.filterDrawer.formData.pageSize = val
        this.loadDataForTurnPage()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.filterDrawer.formData.currentPage) {
        this.filterDrawer.formData.currentPage = val
        this.loadDataForTurnPage()
      }
    },
    loadDataForTurnPage() {
      if (this.filterDrawer.showTopOnly) {
        this.loadTopLevel()
      } else {
        this.loadAllLevel()
      }
    },
    indexMethod(index) {
      return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1
    },
    submitMainDataForm() {
      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.saveMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async saveMainDataForm() {
      this.listLoading = true
      const response = await saveDemoOrg(this.mainDataForm.editingRecord)
      this.listLoading = false
      this.$message({
        message: response.message,
        type: 'warning'
      })
      if (this.mainDataForm.editingRecord.parentGuid === 'top') {
        this.loadTopLevel()
      } else {
        this.refreshTable(this.mainDataForm.editingRecord.parentGuid)
      }
    },
    colFormatter(row, column, cellValue, key) {
      return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue)
    },
    dateTimeColFormatter(row, column, cellValue) {
      return this.$commonUtils.dateTimeFormat(cellValue)
    },
    initFormData() {
      this.mainDataForm.editingRecord.guid = ''
      this.mainDataForm.editingRecord.parentGuid = 'top'
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initFormData()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleClickEditButton(row) {
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = this.$commonUtils.shadowCopy(row)
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleAddSubButton(row) {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initFormData()
      this.mainDataForm.editingRecord.parentGuid = row.guid
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDelRowButton(row) {
      this.$confirm('此操作将删除该行数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handleDelRow(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async handleDelRow(row) {
      const guidContainer = {
        guid: row.guid
      }
      this.listLoading = true
      const response = await delDemoOrg(guidContainer)
      this.listLoading = false
      if (response.code !== 100) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      if (row.parentGuid === 'top') {
        this.loadTopLevel()
      } else {
        this.refreshTable(row.parentGuid)
      }
    },
    refreshTable(parentGuid) {
      // 根据父级id取出对应节点数据
      const {
        row,
        treeNode,
        resolve
      } = this.treeMap.get(parentGuid)
      this.$set(this.$refs.multipleTable.store.states.lazyTreeNodeMap, parentGuid, [])
      if (row) {
        this.loadChildren(row, treeNode, resolve)
      }
    },
    industryCategoryLazyLoadData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadLazyCodeNode(this.$commonDicType.INDUSTRY_CATEGORY(), 'top', resolve)
      } else {
        const {
          value
        } = node
        this.loadLazyCodeNode(this.$commonDicType.INDUSTRY_CATEGORY(), value, resolve)
      }
    },
    industryCategoryEditCascaderChangeEvent(value) {
      const code = value[value.length - 1]
      const name = this.$refs.industryCategoryEditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
      this.mainDataForm.editingRecord.industryCategory = code + '_' + name
    },
    industryCategoryQueryCascaderChangeEvent(value) {
      if (value.length === 0) {
        this.filterDrawer.formData.industryCategory = ''
      } else {
        const code = value[value.length - 1]
        const name = this.$refs.industryCategoryQueryCascaderId.getCheckedNodes()[0].pathLabels.join('/')
        this.filterDrawer.formData.industryCategory = code + '_' + name
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
