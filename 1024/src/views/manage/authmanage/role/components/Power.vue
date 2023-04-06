<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" width="60%" :title="getDialogTitle()">
      <el-tabs tab-position="left">
        <el-tab-pane label="成员用户">
          <RoleUser ref="roleUserComponent" />
        </el-tab-pane>
        <el-tab-pane label="可用资源">
          <RoleResource ref="roleResourceComponent" />
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
  import RoleUser from './RoleUser'
  import RoleResource from './RoleResource'
  export default {
    name: 'DataPermission',
    components: {
      RoleUser,
      RoleResource
    },
    data() {
      return {
        dialogVisible: false,
        formData: {
          code: '',
          name: '',
        },
        tableData: [],
      }
    },
    methods: {
      setComponentData(roleGuid, roleCode, roleName) {
        this.formData.code = roleCode
        this.formData.name = roleName
        this.$nextTick(() => {
          this.$refs.roleUserComponent.setComponentData(roleGuid, roleCode, roleName)
          this.$refs.roleResourceComponent.setComponentData(roleGuid, roleCode, roleName)
        })
      },
      getDialogTitle(){
        return '角色权限管理【'+ this.formData.code+ '_' + this.formData.name +'】'
      },
      showDialog() {
        this.dialogVisible = true
      },
    }
  };
</script>
