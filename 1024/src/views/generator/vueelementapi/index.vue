<template>
  <div class="app-container background-white">
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="warning" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
<!--        <el-button size="mini" class="btn-item" type="warning" icon="el-icon-video-play" @click="generateAll()">
          全部生成
        </el-button> -->
      </div>
    </div>
    <!-- 按钮区域结束 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table v-loading="loading" :data="tableData" border fit highlight-current-row style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="名称">
                <div v-html="props.row.methods"></div>
                </span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" width="100"></el-table-column>
        <el-table-column prop="apiMessage" label="描述"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="uri" label="资源定位符"></el-table-column>
        <el-table-column align="center" label="操作" min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="success" @click="handleClickGenerateCodeButton(scope.row)">代码生成</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 对话框区开始 -->
    <el-dialog :visible.sync="sourceDialogVisible" width="80%" :close-on-click-modal="false"
      title="代码阅读">
      <el-input type="textarea" v-model="sourceCode" :rows="20" readonly></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sourceDialogVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveJsFileButton()">
          生成文件
        </el-button>
      </div>
    </el-dialog>
    <!-- 对话框区结束 -->
  </div>
</template>
<script>
  import {
    fetchControllerInfoList
  } from '@/api/controllerInfo'
  import {
    fetchVueApiFilePath,
    generateVueApiFile,
    createAllApiJsFile,
    fetchApiJsSource
  } from '@/api/generatorvue'

  export default {
    name: 'vueelementapi',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        formLabelWidth: '200px',
        filePath: '',
        tableData: [],
        sourceDialogVisible: false,
        sourceCode: '',
        currentRow: {}
      }
    },
    created() {},
    mounted() {
      this.fetchApiFilePath()
      this.getTableData()
    },
    watch: {},
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      handleClickGenerateCodeButton(row){
        this.currentRow = row
        this.awaitFetchApiJsSource(row)
        this.sourceDialogVisible = true
      },
      async awaitFetchApiJsSource(row) {
        const para = {
          controllerName: row.name,
          path: '',
          overwrite: false
        }
        const result = await fetchApiJsSource(para)
        if (this.$commonResultCode.SUCCESS() == result.code) {
          this.sourceCode = result.data
        }
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },
      async handleSaveJsFileButton() {
        const para = {
          controllerName: this.currentRow.name,
          path: '',
          overwrite: false
        }
        const result = await generateVueApiFile(para)
        if (this.$commonResultCode.FUNCTION_ALREADY_EXIST_ERROR() == result.code) {
          this.$confirm('JS文件已存在，【确定】将要覆盖文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.generateApiJsFileOverwrite()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成操作!'
            });
          });
        } else {
          this.$message({
            message: result.message,
            type: 'warning'
          })
        }
      },
      async generateApiJsFileOverwrite() {
        const para = {
          controllerName: this.currentRow.name,
          path: '',
          overwrite: true
        }
        const result = await generateVueApiFile(para)
        this.$message({
          message: result.message,
          type: 'warning'
        })
      },



      // async generateCode(row) {
      //   let para = new Object();
      //   para["controllerName"] = row.name
      //   para["path"] = this.filePath
      //   this.loading = false
      //   const response = await generateVueApiFile(para)
      //   if (100 !== response.code) {
      //     this.loading = false
      //     this.$message({
      //       message: response.message,
      //       type: 'warning'
      //     })
      //     return
      //   }
      //   this.loading = false
      //   this.$message({
      //     message: '操作完成。',
      //     type: 'warning'
      //   })
      // },
      // async generateAll(){
      //   debugger
      //   let para = new Object();
      //   para["controllerName"] = 'nouse'
      //   para["path"] = this.filePath
      //   this.loading = false
      //   const response = await createAllApiJsFile(para)
      //   if (100 !== response.code) {
      //     this.loading = false
      //     this.$message({
      //       message: response.message,
      //       type: 'warning'
      //     })
      //     return
      //   }
      //   this.loading = false
      //   this.$message({
      //     message: '操作完成。',
      //     type: 'warning'
      //   })
      // },
      async getTableData() {
        this.loading = false
        const response = await fetchControllerInfoList()
        if (100 !== response.code) {
          this.loading = false
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        const {
          data
        } = response
        this.tableData = data
        this.loading = false
      },
      async fetchApiFilePath() {
        const response = await fetchVueApiFilePath()
        this.filePath = response.data
      },
    }
  }
</script>
<style>
</style>
