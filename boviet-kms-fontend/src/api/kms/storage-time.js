import request from '@/utils/request'

// 查询保管期限列表
export function listStorageTime(query) {
  return request({
    url: '/kms/storage-time/list',
    method: 'get',
    params: query
  })
}

// 查询保管期限详细
export function getStorageTime(id) {
  return request({
    url: '/kms/storage-time/' + id,
    method: 'get'
  })
}

// 新增保管期限
export function addStorageTime(data) {
  return request({
    url: '/kms/storage-time',
    method: 'post',
    data: data
  })
}

// 修改保管期限
export function updateStorageTime(data) {
  return request({
    url: '/kms/storage-time',
    method: 'put',
    data: data
  })
}

// 删除保管期限
export function delStorageTime(id) {
  return request({
    url: '/kms/storage-time/' + id,
    method: 'delete'
  })
}
