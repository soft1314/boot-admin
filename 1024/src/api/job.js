import request from '@/utils/request'

export function getBlankJob() {
  return request({
    url: '/api/system/auth/job/blank',
    method: 'get'
  })
}

export function fetchJobPage(data) {
  return request({
    url: '/api/system/auth/job/page',
    method: 'post',
    data
  })
}

export function getUpdateObject(data) {
  return request({
    url: '/api/system/auth/job/dataforupdate',
    method: 'post',
    data
  })
}

export function saveJob(data) {
  return request({
    url: '/api/system/auth/job/save',
    method: 'post',
    data
  })
}

export function pauseJob(data) {
  return request({
    url: '/api/system/auth/job/pause',
    method: 'post',
    data
  })
}

export function resumeJob(data) {
  return request({
    url: '/api/system/auth/job/resume',
    method: 'post',
    data
  })
}

export function deleteJob(data) {
  return request({
    url: '/api/system/auth/job/delete',
    method: 'post',
    data
  })
}
