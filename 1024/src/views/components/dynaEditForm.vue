<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" :title="dialogTitle">
      <div>
        <component ref="child" :businessId="businessId" :processInstanceId="processInstanceId" :taskId="taskId" v-for="comp in comps" :key="comp.id" :is="allComps[comp]"
          @hideDialog="hideDialog"></component>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    reviewTask
  } from '@/api/workflow-task'
  import allComps from './edit/index.js'
  export default {
    name: 'DynaEditForm',
    data() {
      return {
        dialogVisible: false,
        dialogTitle: '办理窗口',
        // 此处组件名即文件名
        comps: ['defaultForm'],
        allComps: allComps,
        taskId: '',
        processInstanceId: '',
        businessId: '',
        reviewComments: ''
      }
    },
    mounted() {},
    methods: {
      setDialogTitle(title){
        this.dialogTitle = title
      },
      setFormKey(formKey) {
        this.comps[0] = formKey
      },
      setTaskId(taskId) {
        this.taskId = taskId
      },
      setProcessInstanceId(processInstanceId) {
        this.processInstanceId = processInstanceId
      },
      setBusinessId(businessId) {
        this.businessId = businessId
        this.$nextTick(() => {
          this.$refs.child[0].setBusinessId(businessId)
        })
      },
      showDialog() {
        this.dialogVisible = true
      },
      hideDialog() {
        this.dialogVisible = false
        this.$emit("refreshTable",true)
      }
    }
  }
</script>
