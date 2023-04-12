<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-4-12 22:27:45 -->
<template>
  <div class="app-container background-white">
    <!-- 表数据编辑对话框区开始 -->
    <el-form ref="mainEditForm" :model="mainDataForm.data" :rules="rules" size="medium" label-width="150px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="mainDataForm.data.title"
          placeholder="请输入标题"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="报销金额" prop="money">
        <el-input
          v-model="mainDataForm.data.money"
          placeholder="请输入报销金额"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="情况说明" prop="note">
        <el-input
          v-model="mainDataForm.data.note"
          placeholder="请输入情况说明"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="业务日期" prop="bizDate">
        <el-input
          v-model="mainDataForm.data.bizDate"
          placeholder="请输入业务日期"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="报销人" prop="empName">
        <el-input
          v-model="mainDataForm.data.empName"
          placeholder="请输入报销人"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="职称" prop="techTitle">
        <el-cascader
          ref="techTitleEditCascaderId"
          v-model="cascaderValue.techTitle"
          :props="techTitleCascaderProps"
          style="width: 50%;"
          @change="techTitleEditCascaderChangeEvent"
        />
        {{ mainDataForm.data.techTitle }}
      </el-form-item>
      <el-form-item label="员工级别" prop="empLevel">
        <el-select v-model="mainDataForm.data.empLevel" placeholder="请选择">
          <el-option
            v-for="item in optionMap.get($commonDicType.EMP_LEVEL())"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="mainDataForm.data.sex" placeholder="请选择">
          <el-option
            v-for="item in optionMap.get($commonDicType.SEX())"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流转状态" prop="flowStatus">
        <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.FLOW_STATUS()),mainDataForm.data.flowStatus) }}</span>
      </el-form-item>
      <el-form-item label="流程定义ID" prop="processDefinitionId">
        <el-input
          v-model="mainDataForm.data.processDefinitionId"
          placeholder="请输入流程定义ID"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="流程实例ID" prop="processInstanceId">
        <el-input
          v-model="mainDataForm.data.processInstanceId"
          placeholder="请输入流程实例ID"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="启用状态" prop="enabled">
        <el-select v-model="mainDataForm.data.enabled" placeholder="请选择">
          <el-option
            v-for="item in optionMap.get($commonDicType.ENABLED())"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="删除状态" prop="deleted">
        <el-select v-model="mainDataForm.data.deleted" placeholder="请选择">
          <el-option
            v-for="item in optionMap.get($commonDicType.DELETED())"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="mainDataForm.data.remarks"
          placeholder="请输入备注"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="提交或销毁批注" prop="reviewComments">
        <el-input
          v-model="mainDataForm.info.reviewComments"
          type="textarea"
          :rows="2"
          placeholder="请输入批注"
          clearable
          :style="{width: '100%'}"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" style="text-align: center;">
      <el-button type="warning" @click="handleSubmitMainDataForm('通过')">
        提交
      </el-button>
      <el-button type="warning" @click="handleSubmitMainDataForm('销毁')">
        销毁
      </el-button>
      <el-button type="primary" @click="resetForm('mainEditForm')">
        重置
      </el-button>
    </div>
  </div>
</template>
<script>
import {
  fetchDemoExpenseInfo,
  saveDemoExpenseAndCompleteTask
} from '@/api/generated/demo-expense-scene4'
import {
  getDictionaryOptionsByItemType,
  lazyFetchDictionaryNode
} from '@/api/dictionary'
export default {
  name: 'DemoExpenseEdit',
  props: {
    processInstanceId: String,
    taskId: String,
    businessId: String
  },
  data() {
    const that = this
    return {
      loading: true,
      mainDataForm: {
        data: {
          title: '',
          money: '',
          note: '',
          bizDate: '',
          empName: '',
          techTitle: '',
          empLevel: '',
          sex: '',
          flowStatus: '',
          processDefinitionId: '',
          processInstanceId: '',
          enabled: '1',
          deleted: '1',
          remarks: '无'
        },
        info: {
          processInstanceId: '',
          taskId: '',
          result: '',
          reviewComments: ''
        }
      },
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.EMP_LEVEL(),
        this.$commonDicType.SEX(),
        this.$commonDicType.FLOW_STATUS(),
        this.$commonDicType.ENABLED(),
        this.$commonDicType.DELETED()
      ],
      techTitleCascaderProps: {
        lazy: true,
        lazyLoad(node, resolve) {
          that.techTitleLazyLoadData(node, resolve)
        }
      },
      cascaderValue: {
        techTitle: []
      },
      rules: {
        guid: [{
          required: true,
          message: '请输入主键',
          trigger: 'blur'
        }],
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        money: [{
          required: true,
          message: '请输入报销金额',
          trigger: 'blur'
        }],
        note: [{
          required: true,
          message: '请输入情况说明',
          trigger: 'blur'
        }],
        bizDate: [{
          required: true,
          message: '请输入业务日期',
          trigger: 'blur'
        }],
        empName: [{
          required: true,
          message: '请输入报销人',
          trigger: 'blur'
        }],
        techTitle: [{
          required: true,
          message: '请输入职称',
          trigger: 'blur'
        }],
        empLevel: [{
          required: true,
          message: '请输入员工级别',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '请输入性别',
          trigger: 'blur'
        }],
        flowStatus: [{
          required: true,
          message: '请输入流转状态',
          trigger: 'blur'
        }],
        processDefinitionId: [{
          required: true,
          message: '请输入流程定义ID',
          trigger: 'blur'
        }],
        processInstanceId: [{
          required: true,
          message: '请输入流程实例ID',
          trigger: 'blur'
        }],
        createBy: [{
          required: true,
          message: '请输入记录创建者',
          trigger: 'blur'
        }],
        createTime: [{
          required: true,
          message: '请输入记录创建时间',
          trigger: 'blur'
        }],
        modifyBy: [{
          required: true,
          message: '请输入记录最后修改者',
          trigger: 'blur'
        }],
        modifyTime: [{
          required: true,
          message: '请输入记录最后修改时间',
          trigger: 'blur'
        }],
        datestamp: [{
          required: true,
          message: '请输入时间戳',
          trigger: 'blur'
        }],
        enabled: [{
          required: true,
          message: '请输入启用状态',
          trigger: 'blur'
        }],
        deleted: [{
          required: true,
          message: '请输入删除状态',
          trigger: 'blur'
        }]
      }
    }
  },
  watch: {},
  created() {},
  mounted() {
    this.loadAllOptions()
    this.fetchAuditInfo(this.businessId)
  },
  inject: ['reload'],
  methods: {
    refresh() {
      this.reload()
    },
    setBusinessId(businessId) {
      this.fetchAuditInfo(businessId)
    },
    loadAllOptions() {
      for (var i = 0; i < this.optionKey.length; i++) {
        this.loadDictionaryOptions(this.optionKey[i], false)
      }
    },
    async loadDictionaryOptions(itemType, includeAllOptions) {
      this.listLoading = true
      const response = await getDictionaryOptionsByItemType(itemType, includeAllOptions)
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
      this.optionMap.set(itemType, data)
    },
    async fetchAuditInfo(businessId) {
      const guidContainer = {
        guid: businessId
      }
      const response = await fetchDemoExpenseInfo(guidContainer)
      if (response.code !== 100) {
        this.$message({
          message: response.message,
          type: 'warning'
        })
        return
      }
      this.mainDataForm.data = response.data
    },
    resetForm(formName) {
      this.$refs.techTitleEditCascaderId.$refs.panel.clearCheckedNodes()
      this.fetchAuditInfo(this.businessId)
    },
    handleSubmitMainDataForm(result) {
      this.mainDataForm.info.result = result
      this.mainDataForm.info.processInstanceId = this.processInstanceId
      this.mainDataForm.info.taskId = this.taskId

      this.$refs['mainEditForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async submitMainDataForm() {
      const response = await saveDemoExpenseAndCompleteTask(this.mainDataForm)
      this.$message({
        message: response.message,
        type: 'warning'
      })

      this.$emit('hideDialog', true)
    },
    techTitleEditCascaderChangeEvent(value) {
      const code = value[value.length - 1]
      if (code === undefined) {
        this.mainDataForm.data.techTitle = ''
        return
      }
      const name = this.$refs.techTitleEditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
      this.mainDataForm.data.techTitle = code + '_' + name
    },
    techTitleLazyLoadData(node, resolve) {
      const {
        level
      } = node
      if (level === 0) {
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), 'top', resolve)
      } else {
        const {
          value
        } = node
        this.loadLazyCodeNode(this.$commonDicType.TECH_TITLE(), value, resolve)
      }
    },
    async loadLazyCodeNode(dicType, code, resolve) {
      this.listLoading = true
      const response = await lazyFetchDictionaryNode(dicType, code)
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
      // 通过调用resolve将子节点数据返回，通知组件数据加载完成
      resolve(data)
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
