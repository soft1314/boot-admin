<template>
  <div class="app-container background-white">
    <el-form :model="vueForm.formData" :rules="rules" ref="vueForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="路由名称" prop="name" :label-width="vueForm.formLabelWidth">
        <el-input v-model="vueForm.formData.name"></el-input>
      </el-form-item>
      <el-form-item label="VUE文件保存位置" prop="path" :label-width="vueForm.formLabelWidth">
        <el-input v-model="vueForm.formData.path"></el-input>
      </el-form-item>
      <el-form-item label="数据表名" prop="tablename" :label-width="vueForm.formLabelWidth">
        <el-input v-model="fetchTableColumnFilterFormData.tableName"></el-input>
      </el-form-item>
      <el-divider content-position="left">
        <el-tag type="success">功能按钮区</el-tag>
      </el-divider>
      <el-row>
        <el-col :span="2"><div class="grid-content bg-purple">
      <el-form-item label="刷新" prop="showRefreshButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showRefreshButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
      <el-form-item label="查询" prop="showQueryButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showQueryButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple">
      <el-form-item label="添加" prop="showAddButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showAddButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
      <el-form-item label="删除" prop="showDelButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showDelButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple">
      <el-form-item label="导出" prop="showExportButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showExportButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
      <el-form-item label="提交" prop="showSubmitButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showSubmitButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
      <el-form-item label="审核" prop="showAuditButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showAuditButton"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="2"><div class="grid-content bg-purple-light">
      <el-form-item label="撤回" prop="showRevokeButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showRevokeButton"></el-switch>
      </el-form-item>
        </div></el-col>
      </el-row>
      <el-divider content-position="left">
        <el-tag type="success">数据表区</el-tag>
      </el-divider>
      <el-row>
        <el-col :span="3"><div class="grid-content bg-purple">
      <el-form-item label="表格组件" prop="showQueryButton" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.showElTable"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="3"><div class="grid-content bg-purple">
      <el-form-item label="索引列" prop="showIndexColumn" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.elTable.showIndexColumn"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="3"><div class="grid-content bg-purple">
      <el-form-item label="选框列" prop="showCheckColumn" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.elTable.showCheckedColumn"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="3"><div class="grid-content bg-purple">
      <el-form-item label="展开列" prop="showExpandColumn" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.elTable.showExpandColumn"></el-switch>
      </el-form-item>
        </div></el-col>
        <el-col :span="3"><div class="grid-content bg-purple">
      <el-form-item label="操作列" prop="showOperateColumn" :label-width="vueForm.formLabelWidth">
        <el-switch v-model="vueForm.formData.elTable.showOperateColumn"></el-switch>
      </el-form-item>
        </div></el-col>
      </el-row>
      <el-form-item label="表格数据查询入口" prop="path" :label-width="vueForm.formLabelWidth">
        <!-- <el-input v-model="vueForm.formData.dataFetcher"></el-input> -->
<!--        <div class="block"> -->
          <el-cascader
            v-model="vueForm.formData.dataFetcher"
            :options="options.dataFetcherOptions"></el-cascader>
        <!-- </div> -->

      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="loadColumns()">重新载入表列</el-button>
      </el-form-item>
      <!-- 表列表格开始 -->
      <el-table v-loading="listLoading" :data="vueForm.formData.vueTableColumnDefineDTOList" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="comments" label="描述" min-width="100" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="columnName" label="列名" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="dataType" label="类型" show-overflow-tooltip sortable></el-table-column>
        <el-table-column prop="dataDefault" label="缺省值" show-overflow-tooltip sortable></el-table-column>
        <el-table-column align="center" label="作为条件">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inDrawer" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="作为表列">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inElTable" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="作为展开列">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inElTableExpand" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="加入编辑表单">
          <template slot-scope="{row}">
            <span class="cell-edit-switch">
              <el-switch style="display: block;height: 30px;" v-model="row.inAddOrEditForm" active-color="#13ce66"
                inactive-color="#ff4949" :active-value=1 :inactive-value=0 active-text="" inactive-text="">
              </el-switch>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="代码类别名">
          <template slot-scope="{row}">
            <el-input v-model="row.optionsName" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="弹窗方法名">
          <template slot-scope="{row}">
            <el-input v-model="row.methodName" class="edit-input" size="mini" style="padding-right: 5px;" />
          </template>
        </el-table-column>
      </el-table>
      <!-- 表列表格结束 -->
      <el-divider content-position="left">
        <el-tag type="success">操作区</el-tag>
      </el-divider>
      <el-form-item>
        <el-button type="primary" @click="submitForm('vueForm')">生成VUE文件</el-button>
        <el-button type="primary" @click="submitForm('vueForm')">生成Router代码片断</el-button>
        <el-button type="primary" @click="submitForm('vueForm')">生成API代码片断</el-button>
        <el-button @click="resetForm('vueForm')">表单重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {
    fetchVueFilePath,
    fetchVueApiFilePath,
    generateVueFile
  } from '@/api/generatorvue'
  import {
    fetchTableColumnList
  } from '@/api/oracle-dictionary'
  import {
    fetchAllCascaderData
  } from '@/api/controllerInfo'


  export default {
    name: 'vueelementindex',
    computed: {},
    components: {},
    data() {
      return {
        loading: true,
        vueForm: {
          dialogVisible: false,
          formLabelWidth: '140px',
          formData: {
            name: 'test1',
            path: '',
            dataFetcher: [],
            tableName: 'TB_USER',

            showRefreshButton: true,
            showQueryButton: true,
            showAddButton: false,
            showDelButton: false,
            showExportButton: false,
            showSubmitButton: false,
            showAuditButton: false,
            showRevokeButton: false,
            showElTable: true,
            elTable: {
              showIndexColumn: true,
              showCheckedColumn: true,
              showOperateColumn: true,
              showExpandColumn: true,
              elOperateColumnButtonList: [],
            },
            vueTableColumnDefineDTOList: [],

          },
        },
        fetchTableColumnFilterFormData: {
          tableName: 'TB_USER',
          dataSource: "master"
        },
        listLoading: false,
        options: {
          dataFetcherOptions: []
        },
        rules: {
          name: [{
              required: true,
              message: '请输入活动名称',
              trigger: 'blur'
            },
            {
              min: 5,
              max: 50,
              message: '长度在 5 到 50 个字符',
              trigger: 'blur'
            }
          ],
          path: [{
              required: true,
              message: '请输入文件保存路径（位置）',
              trigger: 'blur'
            },
            {
              min: 5,
              max: 500,
              message: '长度在 5 到 500 个字符',
              trigger: 'blur'
            }
          ]
        }
      }
    },
    created() {},
    mounted() {
      this.fetchVueFilePath()
      this.fetchVueApiFilePath()
      this.fetchDataFetcherOptions()
    },
    watch: {},
    inject: ['reload'],
    methods: {
      refresh() {
        this.reload()
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.generateVueFile()
          } else {
            this.$message({
              message: '表单验证未通过！',
              type: 'warning'
            })
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      async generateVueFile() {
        this.loading = false
        const response = await generateVueFile(this.vueForm.formData)
        if (100 !== response.code) {
          this.loading = false
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        this.loading = false
        this.$message({
          message: '操作完成。',
          type: 'warning'
        })
      },
      async fetchVueFilePath() {
        const response = await fetchVueFilePath()
        this.vueForm.formData.path = response.data
      },
      async fetchVueApiFilePath() {
        const response = await fetchVueApiFilePath()
        this.vueForm.formData.apipath = response.data
      },
      async fetchDataFetcherOptions() {
        const response = await fetchAllCascaderData()
        this.options.dataFetcherOptions = response.data
      },
      codeGenerateEntity(tableName) {
        this.$confirm('此操作将生成数据表【' + tableName + '】对应的实体类源代码（JAVA）, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '完成源代码生成!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成操作!'
          });
        });
      },
      async loadColumns() {
        this.loading = false
        const response = await fetchTableColumnList(this.fetchTableColumnFilterFormData)
        if (100 !== response.code) {
          this.loading = false
          this.$message({
            message: response.message,
            type: 'warning'
          })
          return
        }
        this.loading = false
        const {
          data
        } = response
        this.vueForm.formData.vueTableColumnDefineDTOList = data
      },

    }
  }
</script>
<style>
</style>
