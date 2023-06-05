<!-- 跟踪通知通行的通知与阅读情况 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2000-08-20 -->
<template>
  <div class="app-container background-white">
    <el-dialog :visible.sync="dialogVisible" width="80%" :close-on-click-modal="false" title="阅读跟踪">
      <div style="text-align:center;height:50px;margin-top: 10px;">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="logonName" placeholder="请输入账号" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-input v-model="empName" placeholder="请输入姓名" size="mini" prefix-icon="el-icon-search" clearable />
          </el-form-item>
          <el-form-item>
            <el-cascader ref="cascaderAdminDiv" v-model="currentRegion" :props="regionCascaderProps" />
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="ignoreAdmDiv">忽略区划</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" icon="el-icon-search" @click="handleClickQueryButton()">查询
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-table :data="tableData" stripe border highlight-current-row fit :header-cell-style="{'text-align':'center'}">
        <el-table-column prop="orgName" label="机构" align="left" />
        <el-table-column prop="firstName" label="姓名" align="left" />
        <el-table-column prop="lastName" label="别名" align="left" />
        <el-table-column prop="logonName" label="账号" align="left" />
        <el-table-column
          prop="createTime"
          label="通知时间"
          align="left"
          min-width="110"
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column
          prop="haveRead"
          label="是否已读"
          align="left"
          min-width="110"
          :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())"
        />
        <el-table-column
          prop="readTime"
          label="阅读时间"
          align="left"
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"
        />
        <el-table-column prop="admDivCode" label="区划编码" align="left" />
        <el-table-column prop="admDivName" label="区划名称" align="left" />
      </el-table>
      <div style="text-align: center;">
        <el-pagination
          :current-page="page.currentPage"
          :page-sizes="[5,10,20,50,100,500]"
          :page-size="page.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="page.total"
          @size-change="handlePageSizeChange"
          @current-change="handlePageCurrentChange"
        />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  fetchTbAdmDivChildrenNode,
  fetchTbAdmDivDefaultCascaderValue
} from '@/api/tb-adm-div-scene2.js'
import {
  fetchUserNotifyRecordPageByNotifyGuid
} from '@/api/to-notify-record'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
export default {
  name: 'TrackNotify',
  components: {},
  data() {
    const that = this
    return {
      listLoading: false,
      dialogVisible: false,
      page: {
        currentPage: 1,
        pageSize: 10,
        total: 2
      },
      tableData: [],
      logonName: '',
      empName: '',
      ignoreAdmDiv: true,
      currentRegion: [],
      notifyGuid: '',
      regionCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.lazyLoadRegionData(node, resolve)
        }
      },
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.YESNO()
      ]
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.loadAllOptions()
    this.loadTbAdmDivDefaultCascaderValue()
  },
  methods: {
    showDialog(notifyGuid) {
      this.notifyGuid = notifyGuid
      this.loadUserNotifyRecord()
      this.dialogVisible = true
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
      if (val !== this.page.pageSize) {
        this.page.pageSize = val
        this.loadUserNotifyRecord()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.page.currentPage) {
        this.page.currentPage = val
        this.loadUserNotifyRecord()
      }
    },
    handleClickQueryButton() {
      this.page.currentPage = 1
      this.loadUserNotifyRecord()
    },
    async loadUserNotifyRecord() {
      const QueryByGuidDTO = {
        notifyGuid: this.notifyGuid,
        currentPage: this.page.currentPage,
        pageSize: this.page.pageSize,
        logonName: this.logonName,
        empName: this.empName,
        admDivCode: this.getAdminDivCascaderCurrentValue(),
        ignoreAdmDiv: this.ignoreAdmDiv
      }
      const response = await fetchUserNotifyRecordPageByNotifyGuid(QueryByGuidDTO)
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
      this.page.total = data.total
      this.tableData = data.records
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
      this.currentRegion = data.value
    },
    getAdminDivCascaderCurrentValue() {
      if (this.currentRegion.length > 0) {
        return this.currentRegion[this.currentRegion.length - 1]
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
