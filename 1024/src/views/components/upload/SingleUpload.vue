<template>
  <el-input v-model="fileOriginalName" readonly size="mini">
    <el-upload
      v-if="canUpload"
      ref="uploadBtn"
      slot="prepend"
      :headers="headers"
      :on-success="uploadSuccess"
      :action="action"
      :data="uploadData"
      :api-url="apiUrl"
      :auto-upload="false"
      :before-upload="uploadBtnBeforeUpload"
      :on-change="handleUploadChange"
      :show-file-list="false"
    >
      <el-tooltip
        class="item"
        effect="dark"
        content="点击此处选择文件上传,只能上传不超过 20MB 的文件.更改附件后点击保存按钮,才可生效"
        placement="bottom"
      >
        <el-button size="least" type="primary" icon="el-icon-upload">点此上传</el-button>
      </el-tooltip>
    </el-upload>
    <el-button-group
      v-if="fileId!=null&&fileId>0"
      slot="append"
    >
      <el-tooltip class="item" effect="dark" content="查看附件" placement="bottom">
        <el-button type="success" size="least" icon="el-icon-download" @click="viewAttachFile" />
      </el-tooltip>
      <el-tooltip v-if="canClear" class="item" effect="dark" content="清除附件,点击保存后正式生效" placement="bottom">
        <el-button type="danger" size="least" icon="el-icon-delete" @click="clearAttachFile" />
      </el-tooltip>
    </el-button-group>
  </el-input>
</template>

<script type="text/ecmascript-6">
import {
  getToken
} from '@/utils/auth.js'
export default {
  name: 'SingleUpload',
  props: {
    fileId: {
      type: Number,
      default: -1
    },
    fileName: {
      type: String,
      default: ''
    },
    fileOriginalName: {
      type: String,
      default: ''
    },
    fileBucketName: {
      type: String,
      default: ''
    },
    apiUrl: {
      type: String,
      default: '/api/system/auth/gp-db/file/upload-with-data'
    },
    uploadData: {
      type: Object,
      default: () => {}
    },
    fileAcceptRegex: {
      type: String,
      default: '(image/gif|image/jpeg|image/pjpeg|application/vnd.ms-powerpoint|application/x-zip-compressed|image/bmp|application/msword|application/zip|application/pdf|text/plain|application/vnd.ms-excel|application/vnd.openxmlformats-officedocument.wordprocessingml.document|application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)$'
    },
    canUpload: {
      type: Boolean,
      default: true
    },
    canClear: {
      type: Boolean,
      default: true
    },
    showLoading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      headers: {}
    }
  },
  computed: {
    action() {
      return process.env.VUE_APP_BASE_API + this.apiUrl
    }
  },
  mounted() {
    // this.headers = {
    //   'X-Token':  getToken() // 直接从本地获取token就行
    // }

  },
  methods: {
    uploadSuccess(data) {
      this.$emit('uploadSuccess', data.data.data)
    },
    uploadBtnBeforeUpload(file) {
      const isRightSize = file.size / 1024 / 1024 < 20
      if (!isRightSize) {
        this.$message.error('文件大小超过 20MB')
        this.$emit('setLoadingVisible', false)
      }
      const isAccept = new RegExp(this.fileAcceptRegex).test(file.type)
      if (!isAccept) {
        this.$message.error('不可上传' + file.type + '格式的文件')
        this.$emit('setLoadingVisible', false)
      }
      return isRightSize && isAccept
    },
    handleUploadChange(file, fileList) {
      if (file.status === 'ready') {
        this.headers = {
          'X-Token': getToken() // 真正上传前再获取一次token
        }
        this.$nextTick(() => {
          this.$refs.uploadBtn.submit()
        })
      }
      if (fileList.length > 1) {
        fileList.splice(0, 1)
      }
      if (this.showLoading) {
        if (file.status === 'ready') {
          this.$emit('setLoadingVisible', true)
        } else {
          this.$emit('setLoadingVisible', false)
        }
      }
    },
    viewAttachFile() {
      if (this.fileId == null || this.fileId <= 0) {
        this.$message({
          message: '并未上传附件，无法下载',
          type: 'warning'
        })
        return
      }
      const url =
          process.env.VUE_APP_BASE_API +
          '/api/system/free/file/' +
          this.fileBucketName +
          '/' + this.fileName +
          '/' + this.fileOriginalName
      // 从url中找到文件名的索引位置
      const index = url.lastIndexOf('/') + 1
      // 匹配url后缀，检查是否为pdf，是则窗口打开，否则下载到本地
      // if (url.lastIndexOf('.pdf') === -1) {
      // 创建下载
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载,请稍后在本地查看`,
        type: 'success'
      })
      // } else {
      //   window.open(url, url.substring(index))
      // }
    },
    clearAttachFile() {
      this.$emit('clearAttachFile')
    }
  }
}
</script>

<style>
</style>
