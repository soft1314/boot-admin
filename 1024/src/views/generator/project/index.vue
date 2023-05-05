<template>
  <div class="app-container background-white">
    <!-- 查询抽屉开始 -->
    <el-drawer
      :visible.sync="filterDrawer.dialogVisible"
      direction="rtl"
      title="请输入查询条件"
      :with-header="false"
      size="30%"
    >
      <div class="demo-drawer__content">
        <el-form
          ref="drawerForm"
          class="demo-form-inline"
          style="margin-top: 25px;margin-right: 20px;"
          :model="filterDrawer.formData"
        >
          <el-form-item label="项目名称" :label-width="filterDrawer.formLabelWidth" prop="name">
            <el-input
              v-model="filterDrawer.formData.name"
              placeholder="请输入项目名称"
              size="mini"
              prefix-icon="el-icon-search"
            />
          </el-form-item>
          <el-form-item label="是否活动" :label-width="filterDrawer.formLabelWidth" prop="defaultProj">
            <el-select v-model="filterDrawer.formData.defaultProj" placeholder="请选择是否活动" size="mini">
              <el-option
                v-for="item in optionMap.get($commonDicType.YESNO())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="启用状态" :label-width="filterDrawer.formLabelWidth">
            <el-select v-model="filterDrawer.formData.enabled" placeholder="请选择启用状态" size="mini" clearable>
              <el-option
                v-for="item in optionMap.get($commonDicType.ENABLED())"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item :label-width="filterDrawer.formLabelWidth">
            <el-button size="mini" type="success" icon="el-icon-search" @click="getMainTableData()">查询</el-button>
            <el-button size="mini" type="primary" icon="el-icon-refresh" @click="resetForm('drawerForm')">重置
            </el-button>
            <el-button size="mini" icon="el-icon-close" @click="hideDrawer()">关闭</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
    <!-- 查询抽屉结束 -->
    <!-- 按钮区域开始 -->
    <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
      <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
          刷新
        </el-button>
        <el-button size="mini" class="btn-item" type="primary" icon="el-icon-edit" @click="handleClickAddButton()">
          添加
        </el-button>
        <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
          查询
        </el-button>
      </div>
    </div>
    <!-- 按钮区域接收 -->
    <!-- 数据列表区域开始 -->
    <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
      <el-table
        v-loading="loading"
        :data="mainTableData"
        border
        fit
        style="width: 100%"
        max-height="500"
        :row-class-name="tableRowClassName"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="项目包名">
                <span>{{ props.row.packageProj }}</span>
              </el-form-item>
              <el-form-item label="MP-ENTITY包名">
                <span>{{ props.row.packageEntity }}</span>
              </el-form-item>
              <el-form-item label="MP-MAPPER类包名">
                <span>{{ props.row.packageMapper }}</span>
              </el-form-item>
              <el-form-item label="MP-SERVICE包名">
                <span>{{ props.row.packageService }}</span>
              </el-form-item>
              <el-form-item label="MP-CONTROLLER包名">
                <span>{{ props.row.packageController }}</span>
              </el-form-item>
              <el-form-item label="剧情SERVICE包名">
                <span>{{ props.row.packageServiceSc }}</span>
              </el-form-item>
              <el-form-item label="剧情CONTROLLER包名">
                <span>{{ props.row.packageControllerSc }}</span>
              </el-form-item>
              <el-form-item label="VO包名">
                <span>{{ props.row.packageVo }}</span>
              </el-form-item>
              <el-form-item label="Mapstruct包名">
                <span>{{ props.row.packageMapstruct }}</span>
              </el-form-item>
              <el-form-item label="项目JAVA路径">
                <span>{{ props.row.javaOutputDir }}</span>
              </el-form-item>
              <el-form-item label="项目Mapper XML路径">
                <span>{{ props.row.xmlOutputDir }}</span>
              </el-form-item>
              <el-form-item label="VUE文件路径">
                <span>{{ props.row.vueDir }}</span>
              </el-form-item>
              <el-form-item label="API文件路径">
                <span>{{ props.row.apiJsDir }}</span>
              </el-form-item>
              <el-form-item label="代码类型定义JS路径">
                <span>{{ props.row.dictypeJsDir }}</span>
              </el-form-item>
              <el-form-item label="ChangeLog路径">
                <span>{{ props.row.changeLogDir }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" :index="indexMethod" width="70" />
        <el-table-column prop="groupId" label="组织(groupId)" show-overflow-tooltip sortable />
        <el-table-column prop="artifactId" label="项目(artifactId)" show-overflow-tooltip sortable />
        <el-table-column prop="name" label="项目名称" show-overflow-tooltip sortable />
        <el-table-column prop="defaultProj" label="活动状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())" />
        <el-table-column prop="enabled" label="有效状态" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.ENABLED())" />
        <el-table-column prop="enableSwagger" label="启用Swagger" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.YESNO())" />
        <el-table-column prop="author" label="作者" show-overflow-tooltip sortable />
        <el-table-column align="center" label="操作" show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <el-button size="least" type="primary" @click="handleEditRow(scope.row)">修改</el-button>
            <el-button size="least" type="success" @click="handleActiveRow(scope.row)">激活</el-button>
            <el-button size="least" type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 数据列表区域结束 -->
    <!-- 分页组件开始 -->
    <div ref="paginationContainer" style="text-align: center;">
      <el-pagination
        :current-page="filterDrawer.formData.currentPage"
        :page-sizes="[5,10,20,50,100,500]"
        :page-size="filterDrawer.formData.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="filterDrawer.formData.total"
        @size-change="handlePageSizeChange"
        @current-change="handlePageCurrentChange"
      />
    </div>
    <!-- 分页组件结束 -->
    <!-- 表数据编辑对话框区开始 -->
    <el-dialog
      :visible.sync="mainDataForm.mainDataFormDialogVisible"
      width="80%"
      :close-on-click-modal="false"
      :title="mainDataForm.mainDataFormDialogTitle"
    >
      <el-form ref="elForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="组织(groupId)" prop="groupId">
          <el-input
            v-model="mainDataForm.editingRecord.groupId"
            placeholder="请输入组织(groupId)"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目(artifactId)" prop="artifactId">
          <el-input
            v-model="mainDataForm.editingRecord.artifactId"
            placeholder="请输入项目(artifactId)"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目名称" prop="name">
          <el-input
            v-model="mainDataForm.editingRecord.name"
            placeholder="请输入项目名称"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="是否启用Swagger" prop="enableSwagger" required>
          <el-switch
            v-model="mainDataForm.editingRecord.enableSwagger"
            :active-value="'1'"
            :inactive-value="'0'"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </el-form-item>
        <el-form-item label="代码作者" prop="author">
          <el-input
            v-model="mainDataForm.editingRecord.author"
            placeholder="请输入项目代码作者姓名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageProj"
            placeholder="请输入项目包的包名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="MP-Entity包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageEntity"
            placeholder="请输入项目实体类的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="MP-Mapper包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageMapper"
            placeholder="请输入项目Mapper的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="MP-Service包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageService"
            placeholder="请输入项目Service的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="MP-Controller包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageController"
            placeholder="请输入项目Controller的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="剧情Service包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageServiceSc"
            placeholder="请输入项目剧情Service的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="剧情Controller包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageControllerSc"
            placeholder="请输入项目剧情Controller的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="VO包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageVo"
            placeholder="请输入项目VO的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="Mapstruct包名">
          <el-input
            v-model="mainDataForm.editingRecord.packageMapstruct"
            placeholder="请输入项目Mapstruct的包全名"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目JAVA路径">
          <el-input
            v-model="mainDataForm.editingRecord.javaOutputDir"
            placeholder="请输入项目JAVA全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目Mapper XML路径">
          <el-input
            v-model="mainDataForm.editingRecord.xmlOutputDir"
            placeholder="请输入项目Mapper XML全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目前端VUE路径">
          <el-input
            v-model="mainDataForm.editingRecord.vueDir"
            placeholder="请输入前端项目VUE全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目前端API声明JS路径">
          <el-input
            v-model="mainDataForm.editingRecord.apiJsDir"
            placeholder="请输入前端项目API声明JS全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="项目前端字典型别JS路径">
          <el-input
            v-model="mainDataForm.editingRecord.dictypeJsDir"
            placeholder="请输入前端项目代码类别全局定义JS全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
        <el-form-item label="ChangeLog路径">
          <el-input
            v-model="mainDataForm.editingRecord.changeLogDir"
            placeholder="请输入ChangeLog全路径"
            clearable
            :style="{width: '100%'}"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseMainDataFormDialog()">
          关闭
        </el-button>
        <el-button type="primary" @click="handleSubmitMainDataForm()">
          保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 表数据编辑对话框区结束 -->
  </div>
</template>
<script>
import {
  fetchProjectPage,
  saveTgProject,
  delTgProject,
  activeTgProject
} from '@/api/tg-project'
import {
  getDictionaryOptionsByItemType
} from '@/api/dictionary'
export default {
  name: 'Generatorproject',
  components: {},
  data() {
    return {
      loading: true,
      mainTableData: [],
      mainDataForm: {
        editingRecord: {},
        mainDataFormDialogVisible: false,
        mainDataFormDialogTitle: '连续新增'
      },
      filterDrawer: {
        dialogVisible: false,
        formLabelWidth: '100px',
        formData: {
          name: '',
          defaultProj: '',
          enabled: '1',
          currentPage: 1,
          pageSize: 10,
          total: 0
        }
      },
      optionMap: new Map(),
      // 本页需要加载的option数据类型罗列在下面的数组中
      optionKey: [
        this.$commonDicType.ENABLED(),
        this.$commonDicType.YESNO()
      ],
      rules: {
        name: [{
          required: true,
          message: '请输入名称',
          trigger: 'blur'
        }],
        uri: [{
          required: true,
          message: '请输入标识符',
          trigger: 'blur'
        }]
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.loadAllOptions()
    this.getMainTableData()
  },
  inject: ['reload'],
  methods: {
    refresh() {
      this.reload()
    },
    loadAllOptions() {
      for (var i = 0; i < this.optionKey.length; i++) {
        this.loadDictionaryOptions(this.optionKey[i], false)
      }
    },
    colFormatter(row, column, cellValue, key) {
      return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue + '')
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
    handlePageSizeChange(val) {
      if (val !== this.filterDrawer.formData.pageSize) {
        this.filterDrawer.formData.pageSize = val
        this.getMainTableData()
      }
    },
    handlePageCurrentChange(val) {
      if (val !== this.filterDrawer.formData.currentPage) {
        this.filterDrawer.formData.currentPage = val
        this.getMainTableData()
      }
    },
    indexMethod(index) {
      return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    showDrawer() {
      this.filterDrawer.dialogVisible = true
    },
    hideDrawer() {
      this.filterDrawer.dialogVisible = false
    },

    async getMainTableData() {
      this.loading = false
      const response = await fetchProjectPage(this.filterDrawer.formData)
      this.loading = false
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
      this.mainTableData = data.records
      this.filterDrawer.formData.total = data.total
    },
    handleEditRow(row) {
      this.mainDataForm.mainDataFormDialogTitle = '修改'
      this.mainDataForm.editingRecord = row
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    handleDeleteRow(row) {
      this.$confirm('此操作将删除选中的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitDelProject(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitDelProject(guid) {
      const guidVO = {
        guid
      }
      const result = await delTgProject(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.getMainTableData()
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleActiveRow(row) {
      this.$confirm('此操作将激活选中的数据，使之为缺省生成的项目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.awaitActiveProject(row.guid)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async awaitActiveProject(guid) {
      const guidVO = {
        guid
      }
      const result = await activeTgProject(guidVO)
      if (this.$commonResultCode.SUCCESS() === result.code) {
        this.getMainTableData()
      }
      this.$message({
        message: result.message,
        type: 'warning'
      })
    },
    handleClickAddButton() {
      this.mainDataForm.mainDataFormDialogTitle = '连续新增'
      this.initmainDataForm()
      this.mainDataForm.mainDataFormDialogVisible = true
    },
    initmainDataForm() {
      this.mainDataForm.editingRecord.guid = ''
      // this.resetFormData()
    },
    handleSubmitMainDataForm() {
      this.$refs['elForm'].validate((valid) => {
        if (valid) {
          this.submitMainDataForm()
        } else {
          console.log('未通过表单校验!!')
          return false
        }
      })
    },
    async submitMainDataForm() {
      const response = await saveTgProject(this.mainDataForm.editingRecord)
      this.$message({
        message: response.message,
        type: 'warning'
      })
      if (this.mainDataForm.mainDataFormDialogTitle === '修改') {
        this.getMainTableData()
        this.mainDataForm.mainDataFormDialogVisible = false
      }
    },
    handleCloseMainDataFormDialog() {
      this.getMainTableData()
      this.mainDataForm.mainDataFormDialogVisible = false
    },
    tableRowClassName({
      row,
      rowIndex
    }) {
      if (row.defaultProj === '1' && row.enabled === '1') {
        return 'active-row'
      }
      return ''
    }
  }
}
</script>
<style>
  .el-table .active-row {
    background: #00ffff;
  }
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
</style>
