<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer :visible.sync="filterDrawer.dialogVisible" direction="rtl" title="请输入查询条件" :with-header="false"
      size="30%">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
          :model="filterDrawer.formData">
          <el-form-item label="编码" :label-width="filterDrawer.formLabelWidth" prop="code">
            <el-input placeholder="请输入编码" size="mini" prefix-icon="el-icon-search" v-model="filterDrawer.formData.code">
            </el-input>
          </el-form-item>
          <el-form-item label="名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input placeholder="请输入名称" size="mini" prefix-icon="el-icon-search" v-model="filterDrawer.formData.name">
            </el-input>
          </el-form-item>
          <el-form-item label="短语" :label-width="filterDrawer.formLabelWidth" prop="varName">
            <el-input placeholder="请输入短语" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.varName">
            </el-input>
          </el-form-item>
          <el-form-item label="启用状态" :label-width="filterDrawer.formLabelWidth" prop="enabled">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button v-on:click="handleClickQueryButton()" size="mini" type="success" icon="el-icon-search">查询</el-button>
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-refresh" @click="handleClickAddButton()">
          新增
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
          查询
        </el-button>
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table v-loading="loading" :data="mainTableData" border fit highlight-current-row style="width: 100%"
        max-height="500">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="代码" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="name" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="varName" label="短语" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())">
        </el-table-column>
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip width="60"></el-table-column>
        <el-table-column align="center" label="操作" show-overflow-tooltip width="180">
          <template slot-scope="scope">
            <el-button type="warning" size="least" @click="handleEditRowButton(scope.row)">编辑</el-button>
            <el-button type="primary" size="least" @click="handleDetailButton(scope.row)">详情</el-button>
            <el-button type="danger" size="least" @click="handleDelRowButton(scope.row)">删除</el-button>
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
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-refresh"
          @click="handleClickAddDetailButton()">
          新增
        </el-button>
        <el-button size="mini" type="text">
          <el-breadcrumb separator="|">
            <el-breadcrumb-item>当前字典类别</el-breadcrumb-item>
            <el-breadcrumb-item>代码:{{this.detailDataForm.editingRecord.itemTypeCode}}</el-breadcrumb-item>
            <el-breadcrumb-item>名称:{{this.detailDataForm.editingRecord.itemTypeName}}</el-breadcrumb-item>
          </el-breadcrumb>
        </el-button>
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table v-loading="loading" :data="detailTableData" border fit highlight-current-row style="width: 100%"
        max-height="500" stripe :header-cell-style="{'text-align':'center'}" row-key="guid" lazy
        :load="loadChildren" :tree-props="{hasChildren:'hasChildren'}" ref="multipleTable">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="备注">
                <span>{{ props.row.remarks }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="itemCode" label="编码" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="itemName" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="leaf" label="是否末级" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())">
        </el-table-column>
        <el-table-column prop="sort" label="顺序号" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())">
        </el-table-column>
        <el-table-column prop="remarks" label="备注" show-overflow-tooltip width="60"></el-table-column>
        <el-table-column align="center" label="操作" show-overflow-tooltip width="180">
          <template slot-scope="scope">
            <el-button type="warning" size="least" @click="handleEditDetailRowButton(scope.row)">编辑</el-button>
            <el-button type="primary" size="least" @click="handleAppendSubDetailRowButton(scope.row)">添加</el-button>
            <el-button type="danger" size="least" @click="handleDelDetailRowButton(scope.row)">删除</el-button>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 分页组件开始 -->
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination v-on:size-change="handlePageSizeChangeDetail" v-on:current-change="handlePageCurrentChangeDetail"
        :current-page="detailPagination.currentPage" :page-sizes="[5,10,20,50,100,500]"
        :page-size="detailPagination.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="detailPagination.total">
      </el-pagination>
    </div>
    <!-- 分页组件结束 -->
    <!-- 对话框区开始 -->
    <el-dialog :visible.sync="mainDataForm.dataFormDialogVisible" width="80%" :close-on-click-modal="false"
      :title="mainDataForm.dataFormDialogTitle">
      <el-form ref="elForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="编码(code)" prop="code">
          <el-input v-model="mainDataForm.editingRecord.code" placeholder="请输入组织编码(code)" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="名称(name)" prop="name">
          <el-input v-model="mainDataForm.editingRecord.name" placeholder="请输入类别名称(name)" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="短语" prop="varName">
          <el-input v-model="mainDataForm.editingRecord.varName" placeholder="请输入短语" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="mainDataForm.editingRecord.remarks" placeholder="请输入备注" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-select v-model="mainDataForm.editingRecord.enabled" placeholder="请选择">
            <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
    <el-dialog :visible.sync="detailDataForm.dataFormDialogVisible" width="80%" :close-on-click-modal="false"
      :title="detailDataForm.dataFormDialogTitle">
      <el-form ref="elForm" :model="detailDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="类别编码" prop="itemTypeCode">
          {{detailDataForm.editingRecord.itemTypeCode}}
        </el-form-item>
        <el-form-item label="类别名称" prop="itemTypeName">
          {{detailDataForm.editingRecord.itemTypeName}}
        </el-form-item>
        <el-form-item label="编码" prop="itemCode">
          <el-input v-model="detailDataForm.editingRecord.itemCode" placeholder="请输入编码" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="名称" prop="itemName">
          <el-input v-model="detailDataForm.editingRecord.itemName" placeholder="请输入名称" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="detailDataForm.editingRecord.remarks" placeholder="请输入备注" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="是否末级" prop="leaf">
          <el-select v-model="detailDataForm.editingRecord.leaf" placeholder="请选择">
            <el-option v-for="item in optionMap.get($commonDicType.YESNO())" :key="item.value" :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="显示顺序" prop="sort">
          <el-input v-model="detailDataForm.editingRecord.sort" placeholder="请输入显示顺序号" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-select v-model="detailDataForm.editingRecord.enabled" placeholder="请选择">
            <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseDetailDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSubmitDetailDataForm()">
          保存
        </el-button>
      </div>
    </el-dialog>

    <!-- 对话框区结束 -->
  </div>
</template>
<script>
  import {
    fetchDicTypePage,
    saveTbDictype,
    delTbDictype
  } from '@/api/tb-dictype'
  import {
    fetchTopLevelDicTionaryPage,
    fetchTbDictionaryAllChildren,
    saveTbDictionary,
    delTbDictionary
  } from '@/api/tb-dictionary'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  import {
    fetchTableColumnList
  } from '@/api/oracle-dictionary'

  export default {
    name: 'DicManage',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        filterDrawer: {
          dialogVisible: false,
          formLabelWidth: '100px',
          formData: {
            tableName: '',
            tableType: 'TABLE',
            tableComments: '',
            dataSource: 'master',
            currentPage: 1,
            pageSize: 5,
            total: 0,
          },
        },
        mainTableData: [],
        mainDataForm: {
          editingRecord: {
            guid: '',
            code: '',
            name: '',
            varName: '',
            remarks: '无',
            enabled: '1'
          },
          dataFormDialogVisible: false,
          dataFormDialogTitle: '连续新增'
        },
        detailTableData: [],
        detailDataForm: {
          editingRecord: {},
          dataFormDialogVisible: false,
          dataFormDialogTitle: '连续新增'
        },
        detailPagination: {
          currentPage: 1,
          pageSize: 5,
          total: 0,
        },
        treeMap: new Map(),
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.YESNO(),
          this.$commonDicType.ENABLED()
        ],
        rules: {
          name: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }],
          code: [{
            required: true,
            message: '请输入编码',
            trigger: 'blur'
          }],
          varName: [{
            required: true,
            message: '请输入短语',
            trigger: 'blur'
          }],
          itemName: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }],
          itemCode: [{
            required: true,
            message: '请输入编码',
            trigger: 'blur'
          }],
          sort: [{
            required: true,
            message: '请输入顺序号',
            trigger: 'blur'
          }],
          leaf: [{
            required: true,
            message: '请选择是否末级',
            trigger: 'blur'
          }],
        }
      }
    },
    created() {},
    mounted() {
      this.loadAllOptions()
      this.getMainTableData()
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
          this.getMainTableData()
        }
      },
      handlePageSizeChangeDetail(val) {
        if (val != this.detailPagination.pageSize) {
          this.detailPagination.pageSize = val;
          this.getDetailTopLevelTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val;
          this.getMainTableData()
        }
      },
      handlePageCurrentChangeDetail(val) {
        if (val != this.detailPagination.currentPage) {
          this.detailPagination.currentPage = val;
          this.getDetailTopLevelTableData()
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
      handleClickQueryButton(){
        this.filterDrawer.formData.currentPage = 1
        this.getMainTableData()
      },
      async getMainTableData() {
        this.loading = false
        const response = await fetchDicTypePage(this.filterDrawer.formData)
        this.loading = false
        if (100 !== response.code) {
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
      async getDetailTopLevelTableData() {
        const tbDictionaryQueryVO = {
          itemTypeCode: this.detailDataForm.editingRecord.itemTypeCode,
          currentPage: this.detailPagination.currentPage,
          pageSize: this.detailPagination.pageSize
        }
        this.loading = false
        const response = await fetchTopLevelDicTionaryPage(tbDictionaryQueryVO)
        this.loading = false
        if (100 !== response.code) {
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.detailTableData = data.records
        this.detailPagination.total = data.total
        if (this.detailTableData.length == 0) {
          this.$message({
            message: '无数据',
            type: 'warning'
          })
        }
      },
      handleClickAddButton() {
        this.initMainFormData()
        this.mainDataForm.dataFormDialogVisible = true
      },
      handleClickAddDetailButton() {
        if (this.detailDataForm.editingRecord.itemTypeCode == undefined) {
          this.$message({
            message: '请先点击上面表格中【详情】按钮。',
            type: 'warning'
          })
          return
        }
        this.initDetailFormData()
        this.detailDataForm.dataFormDialogVisible = true
      },
      initMainFormData() {
        this.mainDataForm.editingRecord.guid = ''
        this.mainDataForm.editingRecord.code = ''
        this.mainDataForm.editingRecord.name = ''
        this.mainDataForm.editingRecord.varName = ''
        this.mainDataForm.editingRecord.remarks = '无'
        this.mainDataForm.editingRecord.enabled = '1'
      },
      initDetailFormData() {
        this.detailDataForm.editingRecord.guid = ''
        this.detailDataForm.editingRecord.parentGuid = 'top'
        this.detailDataForm.editingRecord.enabled = '1'
        this.detailDataForm.editingRecord.leaf = '1'
        this.detailDataForm.editingRecord.sort = 0
        this.resetDetailFormData()
      },
      resetDetailFormData() {
        this.detailDataForm.editingRecord.remarks = '无'
      },
      handleEditRowButton(row) {
        this.mainDataForm.dataFormDialogTitle = '修改'
        this.mainDataForm.editingRecord = row;
        this.mainDataForm.dataFormDialogVisible = true
      },
      handleEditDetailRowButton(row) {
        this.detailDataForm.dataFormDialogTitle = '修改'
        this.detailDataForm.editingRecord = row;
        this.detailDataForm.dataFormDialogVisible = true
      },
      handleAppendSubDetailRowButton(row) {
        this.detailDataForm.dataFormDialogTitle = '连续新增'
        this.initDetailFormData()
        this.detailDataForm.editingRecord.parentGuid = row.guid
        row.hasChildren = true
        this.detailDataForm.dataFormDialogVisible = true
      },
      handleDetailButton(row) {
        this.detailDataForm.editingRecord.itemTypeCode = row.code
        this.detailDataForm.editingRecord.itemTypeName = row.name
        this.getDetailTopLevelTableData()
      },
      handleDelRowButton(row) {
        this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.awaitDelDictype(row.guid)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleDelDetailRowButton(row) {
        this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.awaitDelDictionary(row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleCloseMainDataFormDialog() {
        this.getMainTableData()
        this.mainDataForm.dataFormDialogVisible = false
      },
      handleCloseDetailDataFormDialog() {
        this.detailDataForm.dataFormDialogVisible = false
        if (this.detailDataForm.editingRecord.parentGuid === 'top') {
          this.getDetailTopLevelTableData()
        } else {
          this.refreshTable(this.detailDataForm.editingRecord.parentGuid)
        }
      },
      handleSubmitMainDataForm() {
        this.$refs['elForm'].validate((valid) => {
          if (valid) {
            this.submitMainDataForm()
          } else {
            console.log('未通过表单校验!!');
            return false;
          }
        });
      },
      handleSubmitDetailDataForm() {
        this.$refs['elForm'].validate((valid) => {
          if (valid) {
            this.submitDetailDataForm()
          } else {
            console.log('未通过表单校验!!');
            return false;
          }
        });
      },
      async submitMainDataForm() {
        const response = await saveTbDictype(this.mainDataForm.editingRecord)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        if (this.mainDataForm.dataFormDialogTitle == '修改') {
          this.getMainTableData()
          this.mainDataForm.dataFormDialogVisible = false
        }
      },
      async submitDetailDataForm() {
        const response = await saveTbDictionary(this.detailDataForm.editingRecord)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        if (this.detailDataForm.dataFormDialogTitle == '修改') {
          if (this.detailDataForm.editingRecord.parentGuid === 'top') {
            this.getDetailTopLevelTableData()
          } else {
            this.refreshTable(this.detailDataForm.editingRecord.parentGuid)
          }

          this.detailDataForm.dataFormDialogVisible = false
        }
      },
      async awaitDelDictype(guid) {
        const guidVO = {
          guid
        }
        const result = await delTbDictype(guidVO)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.getMainTableData()
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async awaitDelDictionary(row) {
        let guid = row.guid
        const guidVO = {
          guid
        }
        const result = await delTbDictionary(guidVO)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          if (row.parentGuid === 'top') {
            this.getDetailTopLevelTableData()
          } else {
            this.refreshTable(row.parentGuid)
          }
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async loadChildren(row, treeNode, resolve) {
        this.treeMap.set(row.guid, {
          row,
          treeNode,
          resolve
        })
        const response = await fetchTbDictionaryAllChildren(row.guid)
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
    }
  }
</script>
<style>
</style>
