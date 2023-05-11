<!-- 懒加载，可多选，行政区划，树形控件 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-5-11 23:52:49 -->
<template>
  <div class="app-container background-white">
    <el-dialog :visible.sync="dialogVisible" width="80%" :close-on-click-modal="false" title="选择拟通知人员">
      <div style="text-align:center;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="granted.logonName" placeholder="请输入账号" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="granted.empName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-cascader ref="cascaderAdminDiv" v-model="granted.currentRegion" :props="regionCascaderProps" />
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="granted.ignoreAdmDiv">忽略区划</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantedQueryButton()">查询
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table
        :data="granted.tableData"
        stripe
        border
        highlight-current-row
        fit
        :header-cell-style="{'text-align':'center'}"
      >
        <el-table-column prop="orgName" label="机构" align="left" min-width="15%" />
        <el-table-column prop="name" label="姓名" align="left" min-width="15%" />
        <el-table-column prop="admDivCode" label="区划编码" align="left" min-width="15%" />
        <el-table-column prop="admDivName" label="区划名称" align="left" min-width="15%" />
        <el-table-column label="操作" min-width="10%">
          <template slot="header">
            操作
            <el-button size="least" type="primary" icon="el-icon-plus" @click="handleClickAddButton()" />
          </template>
          <template slot-scope="scope">
            <el-button type="danger" size="least" @click="handleRevokeGrantedRow(scope.row)">撤销</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div ref="grantedPaginationContainer" style="text-align: center;">
        <el-pagination
          :current-page="granted.page.currentPage"
          :page-sizes="[5,10,20,50,100,500]"
          :page-size="granted.page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="granted.page.total"
          @size-change="handleGrantedPageSizeChange"
          @current-change="handleGrantedPageCurrentChange"
        />
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="grantable.dialogVisible"
      width="70%"
      title="可添加机构"
      append-to-body
      :close-on-click-modal="false"
    >
      <!--查询条件和查询按钮区-->
      <div style="text-align:left;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-cascader
              ref="cascaderAdminDivForGrantable"
              v-model="grantable.currentRegion"
              :props="regionCascaderProps"
              clearable=""
            />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.logonName" placeholder="请输入账号" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="grantable.empName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickGrantableQueryButton()">查询
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--查询条件和查询按钮区结束-->
      <el-table
        :data="grantable.tableData"
        stripe
        border
        highlight-current-row
        fit
        :header-cell-style="{'text-align':'center'}"
      >
        <el-table-column prop="orgCode" label="机构编码" align="left" min-width="15%" />
        <el-table-column prop="orgName" label="机构" align="left" min-width="15%" />
        <el-table-column prop="name" label="名称" align="left" min-width="15%" />
        <el-table-column label="操作" min-width="10%">
          <template slot-scope="scope">
            <el-button type="danger" size="least" @click="handleClickGrantableRowButton(scope.row)">选中</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件开始 -->
      <div ref="grantablePaginationContainer" style="text-align: center;">
        <el-pagination
          :current-page="grantable.page.currentPage"
          :page-sizes="[5,10,20,50,100,500]"
          :page-size="grantable.page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="grantable.page.total"
          @size-change="handleGrantablePageSizeChange"
          @current-change="handleGrantablePageCurrentChange"
        />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  fetchGrantedEmpPage,
  fetchGrantableEmpPage,
  grantOneEmpToNotify,
  revokeOneEmpFromNotify
} from '@/api/tr-notify-emp'
import {
  fetchTbAdmDivChildrenNode,
  fetchTbAdmDivDefaultCascaderValue
} from '@/api/tb-adm-div-scene2.js'
export default {
  name: 'CheckEmp',
  components: {},
  data() {
    const that = this
    return {
      listLoading: false,
      dialogVisible: false,
      granted: {
        page: {
          currentPage: 1,
          pageSize: 10,
          total: 2
        },
        tableData: [],
        logonName: '',
        empName: '',
        ignoreAdmDiv: true,
        currentRegion: []
      },
      grantable: {
        page: {
          currentPage: 1,
          pageSize: 10,
          total: 2
        },
        tableData: [],
        logonName: '',
        empName: '',
        dialogVisible: false,
        currentRegion: []
      },
      notifyGuid: '',
      regionCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.lazyLoadRegionData(node, resolve)
        }
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.loadTbAdmDivDefaultCascaderValue()
  },
  methods: {
    showDialog(notifyGuid) {
      this.notifyGuid = notifyGuid
      this.loadGrantedEmp()
      this.dialogVisible = true
    },
    handleCloseDialog() {
      this.dialogVisible = false
    },
    handleGrantedPageSizeChange(val) {
      if (val !== this.granted.page.pageSize) {
        this.granted.page.pageSize = val
        this.loadGrantedEmp()
      }
    },
    handleGrantablePageSizeChange(val) {
      if (val !== this.grantable.page.pageSize) {
        this.grantable.page.pageSize = val
        this.loadGrantableEmp()
      }
    },
    handleGrantedPageCurrentChange(val) {
      if (val !== this.granted.page.currentPage) {
        this.granted.page.currentPage = val
        this.loadGrantedEmp()
      }
    },
    handleGrantablePageCurrentChange(val) {
      if (val !== this.grantable.page.currentPage) {
        this.grantable.page.currentPage = val
        this.loadGrantableEmp()
      }
    },
    handleClickAddButton() {
      this.loadGrantableEmp()
    },
    handleClickGrantedQueryButton() {
      this.granted.page.currentPage = 1
      this.loadGrantedEmp()
    },
    handleClickGrantableQueryButton() {
      this.grantable.page.currentPage = 1
      this.loadGrantableEmp()
    },
    async handleRevokeGrantedRow(row) {
      const notifyEmpVO = {
        notifyGuid: this.notifyGuid,
        empGuid: row.guid
      }
      const response = await revokeOneEmpFromNotify(notifyEmpVO)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.loadGrantedEmp()
    },
    async loadGrantedEmp() {
      const QueryByGuidDTO = {
        notifyGuid: this.notifyGuid,
        currentPage: this.granted.page.currentPage,
        pageSize: this.granted.page.pageSize,
        logonName: this.granted.logonName,
        empName: this.granted.empName,
        admDivCode: this.getAdminDivCascaderCurrentValueForGranted(),
        ignoreAdmDiv: this.granted.ignoreAdmDiv
      }
      const response = await fetchGrantedEmpPage(QueryByGuidDTO)
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
      this.granted.page.total = data.total
      this.granted.tableData = data.records
    },
    async loadGrantableEmp() {
      const QueryByGuidDTO = {
        notifyGuid: this.notifyGuid,
        currentPage: this.grantable.page.currentPage,
        pageSize: this.grantable.page.pageSize,
        logonName: this.grantable.logonName,
        empName: this.grantable.empName,
        admDivCode: this.getAdminDivCascaderCurrentValueForGrantable(),
        ignoreAdmDiv: false
      }
      const response = await fetchGrantableEmpPage(QueryByGuidDTO)
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
      this.grantable.tableData = data.records
      this.grantable.page.total = data.total
      this.grantable.dialogVisible = true
    },
    async handleClickGrantableRowButton(row) {
      const notifyEmpVO = {
        notifyGuid: this.notifyGuid,
        empGuid: row.guid
      }
      const response = await grantOneEmpToNotify(notifyEmpVO)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      this.loadGrantedEmp()
      this.loadGrantableEmp()
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
      resolve(data)
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
      this.granted.currentRegion = data.value
      this.grantable.currentRegion = this.granted.currentRegion
    },
    getAdminDivCascaderCurrentValueForGranted() {
      if (this.granted.currentRegion.length > 0) {
        return this.granted.currentRegion[this.granted.currentRegion.length - 1]
      } else {
        return ''
      }
    },
    getAdminDivCascaderCurrentValueForGrantable() {
      if (this.grantable.currentRegion.length > 0) {
        return this.grantable.currentRegion[this.grantable.currentRegion.length - 1]
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
