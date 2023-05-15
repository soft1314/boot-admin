<template>
  <div class="app-container background-white">
    <el-upload
      v-if="canUpload"
      ref="multiplyupload"
      class="upload-demo"
      :action="action"
      :on-success="uploadSuccess"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :before-upload="uploadBtnBeforeUpload"
      :headers="headers"
      :data="uploadData"
      :multiple="multiple"
      :limit="limit"
      :on-exceed="handleExceed"
      :file-list="fileList"
    >
      <el-button size="small" type="primary">点击选择，上传附件</el-button>
      <span slot="tip" class="el-upload__tip">只能上传不超过 10MB 的PDF、TXT、ZIP或者图片（JPG、PNG）等文件{{ " "+tips }}</span>
    </el-upload>

    <el-button-group v-if="!canUpload" style="height:100px">
      <el-link
        v-for="(item,key) in fileList"
        :key="key"
        style="display:block;height: 20px;margin:8px 20px;"
        :underline="false"
        type="primary"
        @click="handlePreview(item)"
      >
        {{ item.name }}
        <br>
      </el-link>
    </el-button-group>

    <el-dialog append-to-body :visible.sync="photoDialog.dialogVisible" width="80%" :close-on-click-modal="false" title="图片附件">
      <div class="demo-image__placeholder">
        <div class="block">
          <el-image :src="photoDialog.pngsrc" />
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="photoDialog.dialogVisible=false">
          关闭
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript-6">
import {
  getToken
} from '@/utils/auth.js'
import {
  fetchAttachmentList
} from '@/api/attachment'
import {
  fetchAttachment
} from '@/api/attachment-download'
export default {
  name: 'MultiUpload',
  props: {
    tips: {
      type: String,
      default: '选择附件后点击保存按钮才可生效'
    },
    apiUrl: {
      type: String,
      default: '/api/system/auth/file/upload-with-data'
    },
    uploadData: {
      type: Object,
      default: () => {}
    },
    fileAcceptRegex: {
      type: String,
      default: '(image/png|image/gif|image/jpeg|image/jpeg|application/x-zip-compressed|image/bmp|application/msword|application/zip|application/pdf|text/plain)$'
    },
    canUpload: {
      type: Boolean,
      default: false
    },
    limit: {
      type: Number,
      default: 1
    },
    multiple: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      listLoading: false,
      action: process.env.VUE_APP_BASE_API + this.apiUrl,
      fileList: [],
      photoDialog: {
        dialogVisible: false,
        pngsrc: ''
      },
      mainData: {
        mainTableName: '',
        mainStyle: '',
        mainGuid: ''
      }
    }
  },
  computed: {
    headers() {
      return {
        'X-Token': getToken()
      }
    }
  },
  mounted() {},
  methods: {
    setMainData(mainTableName, mainStyle, mainGuid) {
      this.mainData.mainGuid = mainGuid
      this.mainData.mainStyle = mainStyle
      this.mainData.mainTableName = mainTableName

      this.asyncHandleFetchAttachmentList()
    },
    submitUpload() {
      this.$refs.multiplyupload.submit()
    },
    uploadSuccess(data) {
      this.fileList.push(data.data.data)
    },
    uploadBtnBeforeUpload(file) {
      if (file.size === 0) {
        this.$message.error('文件大小不能为 0')
      }
      const isRightSize = file.size / 1024 / 1024 < 20
      if (!isRightSize) {
        this.$message.error('文件大小超过 20MB')
      }
      const isAccept = new RegExp(this.fileAcceptRegex).test(file.type)
      if (!isAccept) {
        this.$message.error('不可上传' + file.type + '格式的文件')
      }
      return isRightSize && isAccept
    },
    clearAttachFile() {
      this.$emit('clearAttachFile')
    },
    handleRemove(file, fileList) {
      if (this.fileList.length > 0) {
        this.fileList.forEach((item, index) => {
          if (item.fileNumberId === file.fileNumberId) {
            this.fileList.splice(index, 1)
          }
        })
      }
    },
    handleUploadChange(file, fileList) {
      this.fileList = fileList
    },
    handlePreview(file) {
      if (file == null) {
        this.$message({
          message: '未提交附件，请本地查看',
          type: 'warning'
        })
        return
      }
      this.handleSeeAttachment(file.guid, file.contentType)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择` + this.limit +
          `个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    async handleSeeAttachment(guid, resType) {
      this.listLoading = true
      const response = await fetchAttachment(guid)
      this.listLoading = false
      if (resType === 'image/png' || resType === 'image/jpeg') {
        this.photoDialog.pngsrc = this.$commonUtils.genObjectURL(response.data)
        this.photoDialog.dialogVisible = true
      } else {
        const url = this.$commonUtils.genObjectURL(response.data)
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.download = url
        link.target = '_blank'
        link.click()
      }
    },
    async asyncHandleFetchAttachmentList() {
      this.listLoading = true
      const response = await fetchAttachmentList(this.mainData)
      this.listLoading = false
      if (this.$commonResultCode.SUCCESS() !== response.code) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      this.fileList = data
    }
  }
}
</script>
<style>

</style>
