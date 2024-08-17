import request from '@/utils/request'

// 查询访问统计列表
export function listKmsVisit(query) {
  return request({
    url: '/kms/kms-visit/list',
    method: 'get',
    params: query
  })
}

// 查询访问统计详细
export function getKmsVisit(id) {
  return request({
    url: '/kms/kms-visit/' + id,
    method: 'get'
  })
}

// 新增访问统计
export function addKmsVisit(data) {
  return request({
    url: '/kms/kms-visit',
    method: 'post',
    data: data
  })
}

// 修改访问统计
export function updateKmsVisit(data) {
  return request({
    url: '/kms/kms-visit',
    method: 'put',
    data: data
  })
}

// 删除访问统计
export function delKmsVisit(id) {
  return request({
    url: '/kms/kms-visit/' + id,
    method: 'delete'
  })
}
