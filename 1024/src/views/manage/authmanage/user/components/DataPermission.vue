<template>
  <div class="app-container" style="background-color: #FFFFFF;">
    <el-dialog :visible.sync="dialogEditDataPowerVisible" :close-on-click-modal="false" width="60%" :title="getDialogTitle()" @close="dialogCloseEvent">
      <el-tabs tab-position="left">
        <el-tab-pane label="行政区划">
          <RegionPermission ref="regionPermissionComponent" />
        </el-tab-pane>
        <el-tab-pane label="组织机构">
          <OrgPermission ref="orgPermissionComponent" />
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
  import RegionPermission from './RegionPermission'
  import OrgPermission from './OrgPermission'
  export default {
    name: 'DataPermission',
    components: {
      RegionPermission,
      OrgPermission
    },
    data() {
      return {
        dialogEditDataPowerVisible: false,
        formData: {
          userGuid: '',
          logonName: '',
          admDivPermissionType: '',
          orgPermissionType: '',
          RegionPermissionOptions: [],
        },
        tableData: [],
      }
    },
    methods: {
      setComponentData(userGuid, logonName, admDivPermissionType, orgPermissionType,admDivPermissionOptions,orgPermissionOptions) {
        this.formData.logonName = logonName
        this.$nextTick(() => {
          this.$refs.regionPermissionComponent.setComponentData(userGuid, logonName, admDivPermissionType,admDivPermissionOptions)
          this.$refs.orgPermissionComponent.setComponentData(userGuid, logonName, orgPermissionType,orgPermissionOptions)
        })
      },
      getDialogTitle(){
        return '用户数据权限管理【'+ this.formData.logonName +'】'
      },
      showDialog() {
        this.dialogEditDataPowerVisible = true
      },
      dialogCloseEvent(){
        this.$parent.loadUsers()
      }
    }
  };
</script>
