<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <!--查询条件和查询按钮区-->
    <el-drawer ref="drawer" :visible.sync="drawerDialogVisible" direction="rtl" custom-class="demo-drawer">
      <div class="demo-drawer__content">
        <el-form class="demo-form-inline">
          <el-form-item label="名称" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.name" placeholder="请输入规则名称" size="mini" prefix-icon="el-icon-search" />
          </el-form-item>
          <el-form-item label="行政区划" :label-width="formLabelWidthForSearch">
            <el-input v-model="listQuery.mofDivName" placeholder="行政区划">
              <el-button slot="append" icon="el-icon-more" @click="handleTree('mof-div')" />
            </el-input>
          </el-form-item>
          <el-form-item label="规则状态" :label-width="formLabelWidthForSearch">
            <el-select v-model="listQuery.isEnabled" placeholder="请选择规则状态" size="mini">
              <el-option v-for="item in ruleStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="formLabelWidthForSearch">
            <el-button size="mini" type="success" icon="el-icon-search" @click="getList()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!--查询条件和查询按钮区结束-->

    <!--功能按钮区开始-->
    <div style="margin-left: 50px;">
      <div align="left" style="float:left">
        <el-button size="mini" class="btn-item" type="primary" @click="addRule">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="drawerDialogVisible = true">
          查询
        </el-button>
      </div>
      <div align="right">
        <!--分页控件开始-->
        <div style="align:right">
          <el-pagination
            :current-page="listQuery.currentPage"
            :page-sizes="[5,10,20,50,100,500]"
            :page-size="listQuery.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="listQuery.total"
            @size-change="handlePageSizeChange"
            @current-change="handlePageCurrentChange"
          />
        </div>
        <!--分页控件结束-->
      </div>
    </div>
    <!--功能按钮区结束-->
    <!--列表开始-->
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column type="index" :index="indexMethod" width="80" />
      <el-table-column prop="name" label="规则名称" width="500" />
      <el-table-column prop="mofDivCode" label="区划编码" width="120" />
      <el-table-column prop="enabled" label="状态" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="primary" size="least" @click="viewRule(scope.row)">查看</el-button>
          <el-button type="primary" size="least" @click="editRule(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--列表结束-->
    <!-- 树形组件开始 -->
    <el-dialog :title="treeTextMap[treeDialogStatus]" :visible.sync="treeDialogVisible" width="30%">
      <el-input v-model="treeFilterText" placeholder="输入关键字进行过滤" size="mini" />
      <div class="device-tree" style="height: 300px;">
        <el-scrollbar style="height:100%">
          <el-tree
            ref="tree"
            v-loading="treeLoading"
            :data="treeData"
            node-key="code"
            highlight-current
            :check-strictly="true"
            :filter-node-method="filterNode"
          />
        </el-scrollbar>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTreeMsg">确定</el-button>
        <el-button @click="closeTreeDialog">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 树形组件结束 -->
    <el-dialog :visible.sync="viewRuleDialogVisible" width="85%">
      <el-input v-model="currentRow.rule" type="textarea" :rows="20" readonly="true" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="viewRuleDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="editRuleDialogVisible" width="85%">
      规则名称：{{ currentRow.name }}-- 区划编码：{{ currentRow.mofDivCode }}
      <el-input v-model="currentRow.rule" type="textarea" :rows="20" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="saveRule">保存</el-button>
        <el-button @click="editRuleDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="addRuleDialogVisible" width="85%">
      <el-form class="demo-form-inline">
        <el-form-item label="名称" :label-width="formLabelWidthForSearch">
          <el-input v-model="newRuleRow.name" placeholder="请输入规则名称" size="mini" prefix-icon="el-icon-search" />
        </el-form-item>
        <el-form-item label="行政区划" :label-width="formLabelWidthForSearch">
          <el-input v-model="newRuleRow.mofDivCode" placeholder="行政区划">
            <el-button slot="append" icon="el-icon-more" @click="handleTree('mof-div')" />
          </el-input>
        </el-form-item>
        <el-form-item label="规则状态" :label-width="formLabelWidthForSearch">
          <el-input v-model="newRuleRow.rule" type="textarea" :rows="20" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="saveNewRule">保存</el-button>
        <el-button @click="addRuleDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import {
  fetchDroolsRulePage,
  addDroolsRule,
  updateDroolsRule
} from '@/api/manage'
import {
  getClassTreeNode,
  getDictionaryOptionsByItemType
} from '@/api/dictionary'

export default {
  name: 'Droolsrulemanage',
  data: function() {
    return {
      treeLoading: false,
      treeDialogVisible: false,
      treeDialogStatus: '',
      treeData: [],
      formLabelWidthForSearch: '80px',
      treeFilterText: '',
      drawerDialogVisible: false,
      viewRuleDialogVisible: false,
      editRuleDialogVisible: false,
      addRuleDialogVisible: false,
      list: null,
      listLoading: true,
      ruleStatusOptions: [],
      currentRow: '',
      newRuleRow: {
        name: '',
        mofDivCode: '',
        rule: ''
      },
      listQuery: {
        currentPage: 1,
        pageSize: 5,
        codename: '',
        name: '',
        mofDivCode: '',
        mofDivName: '',
        isEnabled: '',
        total: 5
      },
      treeTextMap: {
        'agency-info': '采购预算单位',
        'exp-func': '支出功能分类科目',
        'pur-catalog': '政府采购目录'
      }
    }
  },
  created() {},
  mounted() {
    this.getList()
    this.loadRuleStatusOptionsData()
  },
  methods: {
    async getList() {
      this.listLoading = false
      const response = await fetchDroolsRulePage(this.listQuery)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response

      this.list = data.records
      this.listQuery.total = data.total
      this.listLoading = false
    },
    handlePageSizeChange(val) {
      if (val != this.listQuery.pageSize) {
        this.listQuery.pageSize = val
        this.getList()
      }
    },
    handlePageCurrentChange(val) {
      if (val != this.listQuery.currentPage) {
        this.listQuery.currentPage = val
        this.getList()
      }
    },
    indexMethod(index) {
      return this.listQuery.pageSize * (this.listQuery.currentPage - 1) + index + 1
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    closeTreeDialog() {
      this.treeDialogVisible = false
      this.treeFilterText = ''
    },
    submitTreeMsg() {
      const curTreeNode = this.$refs.tree.getCurrentNode()
      if (curTreeNode == null) {
        this.$message({
          message: '请选择' + this.treeTextMap[this.treeDialogStatus],
          type: 'warning'
        })
        return
      }

      this.newRuleRow.mofDivCode = curTreeNode.code
      this.$refs.tree.setCurrentKey(null)
      this.treeDialogVisible = false
      this.treeFilterText = ''
    },
    async handleTree(treeType) {
      this.treeData = []
      this.treeDialogVisible = true
      this.treeDialogStatus = treeType
      this.treeLoading = true
      const response = await getClassTreeNode(treeType)
      if (response.code !== 100) {
        this.treeLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.treeData = data
      this.treeLoading = false
    },
    async loadRuleStatusOptionsData() {
      const response = await getDictionaryOptionsByItemType(3501, false)
      if (response.code !== 100) {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.ruleStatusOptions = data
    },
    viewRule(row) {
      this.currentRow = row
      this.viewRuleDialogVisible = true
    },
    editRule(row) {
      this.currentRow = row
      this.editRuleDialogVisible = true
    },
    async saveRule() {
      const response = await updateDroolsRule(this.currentRow)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.getList()
    },
    addRule() {
      this.newRuleRow.name = ''
      this.newRuleRow.mofDivCode = ''
      this.newRuleRow.rule = ''
      this.addRuleDialogVisible = true
    },
    async saveNewRule() {
      const response = await addDroolsRule(this.newRuleRow)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.getList()
    }
  }
}
</script>

<style>
</style>
