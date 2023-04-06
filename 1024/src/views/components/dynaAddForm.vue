<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" :title="dialogTitle">
      <div>
        <component v-for="comp in comps" :key="comp.id" :is="allComps[comp]" :procDefKey="procDefKey" @hideDialog="hideDialog"></component>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import allComps from './add/index.js'
  export default {
    name: 'DynaAddForm',
    data() {
      return {
        dialogVisible: false,
        dialogTitle: '流程启动',
        // 此处组件名即文件名
        comps: ['defaultForm'],
        allComps: allComps,
        procDefKey: ''
      }
    },
    mounted() {
    },
    methods: {
      setDialogTitle(title){
        this.dialogTitle = title
      },
      setFormKey(formKey) {
        this.comps[0] = formKey
      },
      setProcDefKey(procDefKey){
        this.procDefKey = procDefKey
      },
      showDialog() {
        this.dialogVisible = true
      },
      hideDialog(){
        this.dialogVisible = false
      }
    }
  }
</script>
