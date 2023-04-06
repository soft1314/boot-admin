<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 张三丰 -->
<!-- @since 2022-11-24 16:03:26 -->
<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="filterDrawer.dialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
          :model="filterDrawer.formData">
          <el-form-item label="行政区划代码" :label-width="filterDrawer.formLabelWidth" prop="admDivCode">
            <el-input placeholder="请输入行政区划代码" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.admDivCode">
            </el-input>
          </el-form-item>
          <el-form-item label="行政区划名称" :label-width="filterDrawer.formLabelWidth" prop="admDivName">
            <el-input placeholder="请输入行政区划名称" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.admDivName">
            </el-input>
          </el-form-item>
          <el-form-item label="启用状态" :label-width="filterDrawer.formLabelWidth" prop="enabled">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="备注" :label-width="filterDrawer.formLabelWidth" prop="remarks">
            <el-input placeholder="请输入备注" size="mini" prefix-icon="el-icon-search"
              v-model="filterDrawer.formData.remarks">
            </el-input>
          </el-form-item>
          <el-form-item label="是否末级" :label-width="filterDrawer.formLabelWidth" prop="leaf">
            <el-select v-model="filterDrawer.formData.leaf" placeholder="请选择是否末级" size="mini" clearable>
              <el-option v-for="item in optionMap.get($commonDicType.YESNO())" :key="item.value" :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadTopLevel()">顶层</el-button>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadAllLevel()">查询</el-button>
            <el-button v-on:click="resetForm('drawerForm')" size="mini" type="primary" icon="el-icon-refresh">重置
            </el-button>
            <el-button v-on:click="hideDrawer()" size="mini" icon="el-icon-close">关闭</el-button>
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
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="handleClickSearchButton()">
          查询
        </el-button>
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table v-loading="listLoading" :data="mainTableData" stripe border highlight-current-row fit style="width: 100%"
      :header-cell-style="{'text-align':'center'}" row-key="guid" default-expand-all lazy :load="loadChildren"
      :tree-props="{hasChildren:'hasChildren'}" ref="multipleTable">
      <el-table-column prop="admDivCode" label="行政区划代码" show-overflow-tooltip sortable align="left"></el-table-column>
      <el-table-column prop="admDivName" label="行政区划名称" show-overflow-tooltip sortable align="left"></el-table-column>
      <el-table-column prop="enabled" label="启用状态" show-overflow-tooltip sortable
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())">
      </el-table-column>
      <el-table-column prop="leaf" label="是否末级" show-overflow-tooltip sortable
        :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())">
      </el-table-column>
      <el-table-column prop="sort" label="顺序号" show-overflow-tooltip sortable></el-table-column>
      <el-table-column prop="remarks" label="备注" show-overflow-tooltip width="50"></el-table-column>
      <el-table-column label="操作" width="180">
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
        <el-pagination :current-page="filterDrawer.formData.currentPage" :page-sizes="[5,10,20,50,100,500]"
          :page-size="filterDrawer.formData.pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="filterDrawer.formData.total" @size-change="handlePageSizeChange"
          @current-change="handlePageCurrentChange" />
      </div>
      <!--分页控件结束-->
    </div>
    <!-- 表单区域开始 -->
    <el-dialog :visible.sync="mainDataForm.mainDataFormDialogVisible" :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle">
      <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="行政区划代码" prop="admDivCode">
          <el-input v-model="mainDataForm.editingRecord.admDivCode" placeholder="请输入行政区划代码" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="行政区划名称" prop="admDivName">
          <el-input v-model="mainDataForm.editingRecord.admDivName" placeholder="请输入行政区划名称" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-select v-model="mainDataForm.editingRecord.enabled" placeholder="请选择">
            <el-option v-for="item in optionMap.get($commonDicType.ENABLED())" :key="item.value" :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="mainDataForm.editingRecord.remarks" placeholder="请输入备注" clearable
            :style="{width: '100%'}" />
        </el-form-item>
        <el-form-item label="是否末级" prop="leaf">
          <el-select v-model="mainDataForm.editingRecord.leaf" placeholder="请选择">
            <el-option v-for="item in optionMap.get($commonDicType.YESNO())" :key="item.value" :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="顺序号" prop="sort">
          <el-input v-model="mainDataForm.editingRecord.sort" placeholder="请输入顺序号" clearable :style="{width: '100%'}" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="mainDataForm.mainDataFormDialogVisible = false">
          关闭
        </el-button>
        <el-button type="warning" @click="submitMainDataForm()">
          保存
        </el-button>
        <el-button type="primary" @click="resetForm('mainEditForm')">
          重置
        </el-button>
      </div>
    </el-dialog>
    <!-- 表单区域结束 -->
  </div>
</template>
<script>
  import {
    saveTbAdmDiv,
    delTbAdmDiv,
    fetchTopTbAdmDivPage,
    fetchTbAdmDivPage,
    fetchTbAdmDivAllChildren
  } from '@/api/tb-adm-div-scene2'
  import {
    getDictionaryOptionsByItemType
  } from '@/api/dictionary'
  export default {
    name: 'DivManage',
    data() {
      return {
        listLoading: false,
        mainTableData: [],
        mainDataForm: {
          editingRecord: {
            admDivCode: '',
            admDivName: '',
            enabled: '1',
            deleted: '1',
            remarks: '无',
            leaf: '0',
            sort: '',

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
            admDivCode: '',
            admDivName: '',
            createBy: '',
            createTime: null,
            modifyBy: '',
            modifyTime: null,
            datestamp: null,
            enabled: '',
            deleted: '',
            remarks: '',
            parentGuid: '',
            leaf: '',
            sort: null,
            currentPage: 1,
            pageSize: 10,
            total: 0,
          },
        },
        treeMap: new Map(),
        radio: false,
        optionMap: new Map(),
        //本页需要加载的option数据类型罗列在下面的数组中
        optionKey: [
          this.$commonDicType.ENABLED(),
          this.$commonDicType.DELETED(),
          this.$commonDicType.YESNO(),
        ],
        rules: {
          guid: [{
            required: true,
            message: '请输入主键',
            trigger: 'blur'
          }],
          admDivCode: [{
            required: true,
            message: '请输入行政区划代码',
            trigger: 'blur'
          }],
          admDivName: [{
            required: true,
            message: '请输入行政区划名称',
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
          parentGuid: [{
            required: true,
            message: '请输入父级GUID',
            trigger: 'blur'
          }],
          leaf: [{
            required: true,
            message: '请输入是否末级',
            trigger: 'blur'
          }],
          sort: [{
            required: true,
            message: '请输入顺序号',
            trigger: 'blur'
          }],
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
        this.$refs[formName].resetFields();
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
      handleLoadTopLevel(){
        this.filterDrawer.formData.currentPage = 1
        this.loadTopLevel()
      },
      async loadTopLevel() {
        this.filterDrawer.showTopOnly = true
        this.listLoading = false
        const response = await fetchTopTbAdmDivPage(this.filterDrawer.formData)
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
      handleLoadAllLevel(){
        this.filterDrawer.formData.currentPage = 1
        this.loadAllLevel()
      },
      async loadAllLevel() {
        this.filterDrawer.showTopOnly = false
        this.listLoading = false
        const response = await fetchTbAdmDivPage(this.filterDrawer.formData)
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
        const response = await fetchTbAdmDivAllChildren(row.guid)
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
        if (val != this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val
          this.loadDataForTurnPage()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val
          this.loadDataForTurnPage()
        }
      },
      loadDataForTurnPage(){
        if(this.filterDrawer.showTopOnly){
          this.loadTopLevel()
        }else{
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
            console.log('未通过表单校验!!');
            return false;
          }
        });
      },
      async saveMainDataForm() {
        this.listLoading = true
        const response = await saveTbAdmDiv(this.mainDataForm.editingRecord)
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
          });
        });
      },
      async handleDelRow(row) {
        const guidContainer = {
          guid: row.guid
        }
        this.listLoading = true
        const response = await delTbAdmDiv(guidContainer)
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
    }
  }
</script>
<style scoped>
</style>
