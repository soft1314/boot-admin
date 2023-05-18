<!-- 懒加载，可多选，行政区划，树形控件 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-5-8 -->
<template>
  <div class="app-container background-white">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="60%" title="选择行政区划">
      <el-tree
        ref="tree"
        :props="props"
        :load="loadNode"
        :default-expanded-keys="defaultExpandedKeys"
        node-key="value"
        lazy
        show-checkbox
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSaveChecked()">
          保存
        </el-button>
        <el-button type="warning" @click="resetChecked()">
          重置
        </el-button>
        <el-button type="warning" @click="clearChecked()">
          清空
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  lazyFetchAdmDivTreeNode
} from '@/api/admdiv'
export default {
  name: 'LazyAdminDivSelectTree',
  components: {},
  data() {
    return {
      listLoading: false,
      dialogMsg: '',
      dialogVisible: false,
      notifyGuid: '',
      props: {
        id: 'id',
        label: 'label',
        value: 'value',
        children: 'children',
        isLeaf: 'leaf'
      },
      defaultExpandedKeys: [],
      defaultCheckedKeys: []
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    showDialog(notifyGuid) {
      this.notifyGuid = notifyGuid
      this.dialogVisible = true
    },
    handleCloseDialog() {
      this.dialogVisible = false
    },
    pushDialogTitle(title) {
      this.dialogMsg = title
    },
    pushTreedefaultCheckedKeys(keys) {
      this.defaultCheckedKeys = keys
      this.clearChecked()
      this.resetChecked()
    },
    pushTreedefaultExpandedKeys(keys) {
      this.defaultExpandedKeys = keys
    },
    loadNode(node, resolve) {
      if (node.level === 0) {
        return resolve([{ label: '全部区划', value: '00', id: 'top', leaf: false }])
      }
      if (node.level > 0) {
        this.loadLazyTreeNode(node.data.id, resolve, node)
      }
    },
    async loadLazyTreeNode(id, resolve, node) {
      this.listLoading = true
      const response = await lazyFetchAdmDivTreeNode(id)
      this.listLoading = false
      if (response.code !== 100) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      const {
        data
      } = response
      resolve(data)
      /** 放在其他地方，有可能懒加载未结束，会乱，放在此处，暂时 OK **/
      this.resetChecked()
    },
    resetChecked() {
      this.$refs.tree.setCheckedKeys(this.defaultCheckedKeys)
    },
    clearChecked() {
      this.$refs.tree.setCheckedKeys([])
    },
    handleSaveChecked() {
      this.$emit('sendDataToParent', this.$refs.tree.getCheckedKeys(), this.notifyGuid)
    }
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
