import request from '@/utils/request'

// 查询档案类型列表
export function listAttachmentType(query) {
  return request({
    url: '/kms/attachment-type/list',
    method: 'get',
    params: query
  })
}

// 查询档案类型详细
export function getAttachmentType(id) {
  return request({
    url: '/kms/attachment-type/' + id,
    method: 'get'
  })
}

// 新增档案类型
export function addAttachmentType(data) {
  return request({
    url: '/kms/attachment-type',
    method: 'post',
    data: data
  })
}

// 修改档案类型
export function updateAttachmentType(data) {
  return request({
    url: '/kms/attachment-type',
    method: 'put',
    data: data
  })
}

// 删除档案类型
export function delAttachmentType(id) {
  return request({
    url: '/kms/attachment-type/' + id,
    method: 'delete'
  })
}
