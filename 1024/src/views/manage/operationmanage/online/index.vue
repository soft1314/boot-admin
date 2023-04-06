<template>
  <div class="app-container background-white">
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
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
              <el-form-item label="登录名">
                <span>{{ props.row.logonName }}</span>
              </el-form-item>
              <el-form-item label="用户IP">
                <span>{{ props.row.ipAddr }}</span>
              </el-form-item>
              <el-form-item label="浏览器">
                <span>{{ props.row.browser }}</span>
              </el-form-item>
              <el-form-item label="操作系统">
                <span>{{ props.row.os }}</span>
              </el-form-item>
              <el-form-item label="登入时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.logonTime) }}</span>
              </el-form-item>
              <el-form-item label="令牌刷新时间">
                <span>{{ $commonUtils.dateTimeFormat(props.row.refreshTime) }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" :index="indexMethod" width="70">
        </el-table-column>
        <el-table-column prop="logonName" label="登录名" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="ipAddr" label="IP地址" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="browser" label="浏览器" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="os" label="操作系统" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="logonTime" label="登录时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"></el-table-column>
        <el-table-column prop="refreshTime" label="令牌刷新时间" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"></el-table-column>

        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="180">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleClickKickoutRowButton(scope.row)">踢</el-button>
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
  </div>
</template>
<script>
  import {
    fetchOnlineUser
  } from '@/api/online-user'

  export default {
    name: 'online-user',
    computed: {},
    data() {
      return {
        loading: true,
        mainTableData: [],
        filterDrawer: {
          formData: {
            currentPage: 1,
            pageSize: 10,
            total: 0,
          },
        },
      }
    },
    created() {},
    mounted() {
      this.getMainTableData()
    },
    watch: {},
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      dateTimeColFormatter(row, column, cellValue) {
        return this.$commonUtils.dateTimeFormat(cellValue)
      },
      handlePageSizeChange(val) {
        if (val != this.filterDrawer.formData.pageSize) {
          this.filterDrawer.formData.pageSize = val;
          this.getMainTableData()
        }
      },
      handlePageCurrentChange(val) {
        if (val != this.filterDrawer.formData.currentPage) {
          this.filterDrawer.formData.currentPage = val;
          this.getMainTableData()
        }
      },
      indexMethod(index) {
        return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1;
      },
      async getMainTableData() {
        this.loading = false
        const response = await fetchOnlineUser(this.filterDrawer.formData)
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
        this.mainTableData = data
        // this.filterDrawer.formData.total = data.total
      },
      handleClickKickoutRowButton(row) {
          this.$message({
            message: '踢人干嘛！',
            type: 'warning'
          })
      },
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
