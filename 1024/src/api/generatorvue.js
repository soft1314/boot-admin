import request from '@/utils/request'

export function fetchVueFilePath() {
  return request({
    url: '/api/system/auth/generator/vueelement/vuefilepath',
    method: 'get',
  })
}

export function fetchVueApiFilePath() {
  return request({
    url: '/api/system/auth/generator/vueelement/vueapifilepath',
    method: 'get',
  })
}

export function generateVueFile(data) {
  return request({
    url: '/api/system/auth/generator/vueelement/vuefile',
    method: 'post',
    data
  })
}

export function generateVueApiFile(data) {
  return request({
    url: '/api/system/auth/generator/vueelement/apijsfile',
    method: 'post',
    data
  })
}

export function createAllApiJsFile(data) {
  return request({
    url: '/api/system/auth/generator/vueelement/allapijsfile',
    method: 'post',
    data
  })
}

export function fetchApiJsSource(data) {
  return request({
    url: '/api/system/auth/generator/vueelement/apijsfile/source',
    method: 'post',
    data
  })
}
