import request from '@/utils/request'

// 查询保管单位列表
export function listCustodyUnit(query) {
  return request({
    url: '/kms/custody-unit/list',
    method: 'get',
    params: query
  })
}

// 查询保管单位详细
export function getCustodyUnit(id) {
  return request({
    url: '/kms/custody-unit/' + id,
    method: 'get'
  })
}

// 新增保管单位
export function addCustodyUnit(data) {
  return request({
    url: '/kms/custody-unit',
    method: 'post',
    data: data
  })
}

// 修改保管单位
export function updateCustodyUnit(data) {
  return request({
    url: '/kms/custody-unit',
    method: 'put',
    data: data
  })
}

// 删除保管单位
export function delCustodyUnit(id) {
  return request({
    url: '/kms/custody-unit/' + id,
    method: 'delete'
  })
}
