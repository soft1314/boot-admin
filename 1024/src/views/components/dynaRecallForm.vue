<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" :title="dialogTitle">
      <div>
        <component ref="child" :businessId="businessId" v-for="comp in comps" :key="comp.id" :is="allComps[comp]"
          @hideDialog="hideDialog"></component>
      </div>
      <div>
        <el-form ref="mainEditForm" size="medium" label-width="150px">
          <el-form-item label="您的意见" prop="remarks">
            <el-input type="textarea" :rows="2" v-model="reviewComments" placeholder="请输入审核意见" clearable
              :style="{width: '100%'}" />
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center;">
          <el-button type="primary" @click="handleReview('撤回')">
            撤回
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    reviewTask
  } from '@/api/workflow-task'
  import allComps from './audit/index.js'
  export default {
    name: 'DynaAuditForm',
    data() {
      return {
        dialogVisible: false,
        dialogTitle: '撤回',
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
        debugger
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
        this.reviewComments = ''
        this.$emit("refreshTable",true)
      },
      async handleReview(result) {
        if('撤回' === result){
          if(this.reviewComments === null || this.reviewComments === undefined || this.reviewComments === ''){
            this.$message({
              message: '请填写您的意见！',
              type: 'warning'
            })
            return
          }
        }
        const reviewContainer = {
          result: result,
          reviewComments: this.reviewComments,
          taskId: this.taskId,
          processInstanceId: this.processInstanceId
        }
        const response = await reviewTask(reviewContainer)
        this.$message({
          message: response.message,
          type: 'warning'
        })
        this.hideDialog()
      }
    }
  }
</script>
