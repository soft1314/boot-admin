<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="80%" height="100%" title="">
      <div>
        <iframe ref="IFrame" id="map" scrolling="auto" v-bind:src="contents"
          frameborder="0" style="top:0px;left: 0px;right:0px;bottom:0px;width: 100%;height: 600px;"></iframe>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'iFrame',
    data() {
      return {
        dialogVisible: false,
        contents: ""
      }
    },
    mounted() {},
    methods: {
      setSrc(src){
        this.contents=src
      },
      showDialog() {
        this.dialogVisible = true
      },
    }
  }
</script>
