<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" title="模型编辑器" @close="closeDialog">
      <div>
        <iframe ref="Modeler" id="map" scrolling="auto" v-bind:src="contents"
          frameborder="0" style="top:0px;left: 0px;right:0px;bottom:0px;width: 100%;height: 600px;"></iframe>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'Modeler',
    data() {
      return {
        dialogVisible: false,
        contents: "/modeler.html?modelId=639b963a-b41f-11ed-8448-4c77cb6b51e3"
      }
    },
    mounted() {
    },
    methods: {
      setSrc(src){
        this.contents="/modeler.html?modelId="+src
      },
      showDialog() {
        this.dialogVisible = true
      },
      closeDialog(){
        this.$emit("refreshTable",true)
      }

    }
  }
</script>
