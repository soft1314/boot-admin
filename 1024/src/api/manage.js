import request from '@/utils/request'

export function fetchSysResurceList(data) {
  return request({
    url: '/api/system/auth/manage/resource/page',
    method: 'post',
    data
  })
}

export function updateSysResource(data) {
  return request({
    url: '/api/system/auth/manage/resource/update',
    method: 'post',
    data
  })
}

export function addSysResource(data) {
  return request({
    url: '/api/system/auth/manage/resource/add',
    method: 'post',
    data
  })
}

export function fetchSysUserPage(data) {
  return request({
    url: '/api/system/auth/manage/user/page',
    method: 'post',
    data
  })
}

export function getUserRoleTransferData(userGuid) {
  return request({
    url: '/api/system/auth/manage/user/role/transfer',
    method: 'get',
    params: { userGuid }
  })
}
export function getColumnUserRoleTransferData(userGuid) {
  return request({
    url: '/api/system/auth/manage/user/role/transfer-column',
    method: 'get',
    params: { userGuid }
  })
}

export function saveUserPower(data) {
  return request({
    url: '/api/system/auth/manage/user/role/save',
    method: 'post',
    data
  })
}
export function saveUserColPower(data) {
  return request({
    url: '/api/system/auth/manage/user/role/save-col',
    method: 'post',
    data
  })
}

export function getResourceRoleTransferData(resourceGuid) {
  return request({
    url: '/api/system/auth/manage/resource/role/transfer',
    method: 'get',
    params: { resourceGuid }
  })
}

export function saveResourcePower(data) {
  return request({
    url: '/api/system/auth/manage/resource/role/save',
    method: 'post',
    data
  })
}

export function fetchGpRolsPage(data) {
  return request({
    url: '/api/system/auth/manage/role/page',
    method: 'post',
    data
  })
}

export function fetchSysUserPageByRoleId(data) {
  return request({
    url: '/api/system/auth/manage/user/page/byroleid',
    method: 'post',
    data
  })
}

export function fetchSysFuncPageByRoleId(data) {
  return request({
    url: '/api/system/auth/manage/function/page/byroleid',
    method: 'post',
    data
  })
}

export function saveRoleUsersPower(data) {
  return request({
    url: '/api/system/auth/manage/user/role/save/byrole',
    method: 'post',
    data
  })
}

export function revokeUserRolePower(data) {
  return request({
    url: '/api/system/auth/manage/user/role/revoke',
    method: 'post',
    data
  })
}

export function clearRoleAllUsers(roleId) {
  return request({
    url: '/api/system/auth/manage/user/role/clearusers',
    method: 'get',
    params: { roleId }
  })
}

export function addFunctionsToOneRole(data) {
  return request({
    url: '/api/system/auth/manage/function/role/save/byrole',
    method: 'post',
    data
  })
}

export function revokeFunctionRolePower(data) {
  return request({
    url: '/api/system/auth/manage/function/role/revoke',
    method: 'post',
    data
  })
}

export function clearRoleAllFunctions(roleId) {
  return request({
    url: '/api/system/auth/manage/function/role/clearfunctions',
    method: 'get',
    params: { roleId }
  })
}

export function fetchDroolsRulePage(data) {
  return request({
    url: '/api/system/auth/manage/drools/rule/page',
    method: 'post',
    data
  })
}

export function fetchMofDeptMappingPage(data) {
  return request({
    url: '/api/system/auth/mof/dep/mapping/page',
    method: 'post',
    data
  })
}

export function fetchAgencyMappingPage(data) {
  return request({
    url: '/api/system/auth/agency/mapping/page',
    method: 'post',
    data
  })
}

export function saveAgencyMapping(data) {
  return request({
    url: '/api/system/auth/manage/mapping/agency/save',
    method: 'post',
    data
  })
}

export function delAgencyMapping(data) {
  return request({
    url: '/api/system/auth/manage/mapping/agency/del',
    method: 'post',
    data
  })
}

export function saveMofdepMapping(data) {
  return request({
    url: '/api/system/auth/manage/mapping/mofdep/save',
    method: 'post',
    data
  })
}

export function delMofdepMapping(data) {
  return request({
    url: '/api/system/auth/manage/mapping/mofdep/del',
    method: 'post',
    data
  })
}

export function getSdgpUnitCodeByCodeAndRegion(region, code) {
  return request({
    url: '/api/system/auth/agency/byregionandcode',
    method: 'get',
    params: { region, code }
  })
}

export function addDroolsRule(data) {
  return request({
    url: '/api/system/auth/manage/drools/rule/add',
    method: 'post',
    data
  })
}

export function updateDroolsRule(data) {
  return request({
    url: '/api/system/auth/manage/drools/rule/update',
    method: 'post',
    data
  })
}

export function getSysAnySqlPage(data) {
  return request({
    url: '/api/system/auth/setup/sysanysql/page',
    method: 'post',
    data
  })
}

export function saveSysAnySqlEntity(data) {
  return request({
    url: '/api/system/auth/setup/sysanysql/entity/save',
    method: 'post',
    data
  })
}

export function execQuerySql(sysAnySqlId) {
  return request({
    url: '/api/system/auth/setup/sysanysql/query/exec',
    method: 'get',
    params: { sysAnySqlId }
  })
}

export function submitMySql(sysAnySqlId) {
  return request({
    url: '/api/system/auth/setup/sysanysql/submit',
    method: 'get',
    params: { sysAnySqlId }
  })
}

export function revokeMySql(sysAnySqlId) {
  return request({
    url: '/api/system/auth/setup/sysanysql/revoke',
    method: 'get',
    params: { sysAnySqlId }
  })
}

export function execMySql(sysAnySqlId) {
  return request({
    url: '/api/system/auth/setup/sysanysql/modify/exec',
    method: 'get',
    params: { sysAnySqlId }
  })
}

export function saveUser(data) {
  return request({
    url: '/api/system/auth/manage/user/save',
    method: 'post',
    data
  })
}
export function delUser(data) {
  return request({
    url: '/api/system/auth/manage/user/del',
    method: 'post',
    data
  })
}
export function fetchTopMenuPage(data) {
  return request({
    url: '/api/system/auth/manage/resource/menu/top/page',
    method: 'post',
    data
  })
}
export function fetchMenuAllChildren(guid) {
  return request({
    url: '/api/system/auth/manage/resource/menu/children/all',
    method: 'get',
    params: { guid }
  })
}
export function saveMenu(data) {
  return request({
    url: '/api/system/auth/manage/resource/menu/save',
    method: 'post',
    data
  })
}
export function delResource(data) {
  return request({
    url: '/api/system/auth/manage/resource/del',
    method: 'post',
    data
  })
}
export function saveUserEmpInfo(data) {
  return request({
    url: '/api/system/auth/manage/user/emp/save',
    method: 'post',
    data
  })
}

export function saveUserAdmDivDataPermissionType(data) {
  return request({
    url: '/api/system/auth/manage/user/datapermission/admdiv/type/save',
    method: 'post',
    data
  })
}

export function saveUserOrgDataPermissionType(data) {
  return request({
    url: '/api/system/auth/manage/user/datapermission/org/type/save',
    method: 'post',
    data
  })
}