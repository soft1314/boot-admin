import request from '@/utils/request'
/**
 * 获取下一层树节点
 */
export function lazyFetchAdmDivTreeNode(guid) {
  return request({
    url: '/api/system/auth/admdiv/lazy/tree/node',
    method: 'get',
    params: { guid }
  })
}
/**
 * 获取通知通告行政区划树缺省勾选的枝叶
 */
export function fetchAdmDivDefaultCheckedKeys(notifyGuid) {
  return request({
    url: '/api/system/auth/admdiv/default/checked/keys',
    method: 'get',
    params: { notifyGuid }
  })
}
/**
 * 获取通知通告行政区划树缺省展开的树枝
 */
export function fetchAdmDivDefaultExpandedKeys(notifyGuid) {
  return request({
    url: '/api/system/auth/admdiv/default/expanded/keys',
    method: 'get',
    params: { notifyGuid }
  })
}

/**
 * 不用了
 */
export function fetchInitCheckedTree(notifyGuid) {
  return request({
    url: '/api/system/auth/admdiv/tree/init',
    method: 'get',
    params: { notifyGuid }
  })
}
