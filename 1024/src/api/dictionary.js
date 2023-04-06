import request from '@/utils/request'

export function getDictionaryOptionsByItemType(itemType, includeAllOptions) {
  return request({
    url: '/api/system/auth/dic/options',
    method: 'get',
    params: { itemType, includeAllOptions }
  })
}
export function getClassTreeNode(itemType) {
  return request({
    url: '/api/system/auth/dic/tree/' + itemType,
    method: 'get'
  })
}
export function getUnitCodeTreeNode(mofDivCode) {
  return request({
    url: '/api/system/auth/dic/tree/unit-code',
    method: 'get',
    params: { mofDivCode }
  })
}
export function getDeptCodeTreeNode(mofDivCode) {
  return request({
    url: '/api/system/auth/dic/tree/dept-code',
    method: 'get',
    params: { mofDivCode }
  })
}
export function getClassLazyTreeNodeTopLevle(itemType) {
  return request({
    url: '/api/system/auth/dic/tree/' + itemType + '/toplevel',
    method: 'get'
  })
}
export function getClassLazyTreeNodeNextLevle(itemType, code) {
  return request({
    url: '/api/system/auth/dic/tree/' + itemType + '/nextlevel',
    method: 'get',
    params: { code }
  })
}
export function getAllMofDivCodeOptions() {
  return request({
    url: '/api/system/free/dic/options/modDiv',
    method: 'get'
  })
}
export function getYesNoOption() {
  return request({
    url: '/api/system/auth/dic/yes-no-options',
    method: 'get'
  })
}
// 用于单表基于某个按照某个字段划分层级关系
export function getTreeNode(itemType) {
  return request({
    url: '/api/system/auth/dic/tree/' + itemType + '/toplevel',
    method: 'get'
  })
}

// export const fileUrl = ""

export function lazyFetchDictionaryNode(dicType,code) {
  return request({
    url: '/api/system/auth/dic/lazy/node',
    method: 'get',
    params: { dicType,code }
  })
}
