import request from '@/utils/request'

// 查询所属区域列表
export function listArea(query) {
  return request({
    url: '/kms/area/list',
    method: 'get',
    params: query
  })
}

// 查询所属区域详细
export function getArea(id) {
  return request({
    url: '/kms/area/' + id,
    method: 'get'
  })
}

// 新增所属区域
export function addArea(data) {
  return request({
    url: '/kms/area',
    method: 'post',
    data: data
  })
}

// 修改所属区域
export function updateArea(data) {
  return request({
    url: '/kms/area',
    method: 'put',
    data: data
  })
}

// 删除所属区域
export function delArea(id) {
  return request({
    url: '/kms/area/' + id,
    method: 'delete'
  })
}
