<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" title="流程跟踪">
      <div>
        <iframe ref="IFrame" id="map" scrolling="auto" v-bind:src="contents"
          frameborder="0" style="top:0px;left: 0px;right:0px;bottom:0px;width: 100%;height: 300px;"></iframe>
      </div>
      <div>
      <el-table v-loading="loading" :data="mainTableData" border fit style="width: 100%" max-height="500">
        <el-table-column type="index" label="序号" width="70">
        </el-table-column>
        <el-table-column prop="name" label="名称" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="assignee" label="经办人" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="notes" label="批注" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="startTime" label="开始时间" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"></el-table-column>
        <el-table-column prop="endTime" label="完成时间" show-overflow-tooltip sortable
          :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"></el-table-column>
      </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    fetchFlowLog
  } from '@/api/workflow-task'
  export default {
    name: 'iFrame',
    data() {
      return {
        dialogVisible: false,
        contents: "",
        loading: true,
        mainTableData: []
      }
    },
    mounted() {

    },
    methods: {
      setSrc(src){
        this.contents=src
      },
      showDialog() {
        this.dialogVisible = true
      },
      loadLog(processInstanceId){
        this.fetchProcessLog(processInstanceId)
      },
      async fetchProcessLog(processInstanceId){
        const guidContainer = {
          guid: processInstanceId
        }
        this.loading = true
        const response = await fetchFlowLog(guidContainer)
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
      },
      dateTimeColFormatter(row, column, cellValue) {
        return this.$commonUtils.dateTimeFormat(cellValue)
      },
    }
  }
</script>
