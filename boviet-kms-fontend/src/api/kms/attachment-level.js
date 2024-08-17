import request from '@/utils/request'

// 查询密级程度列表
export function listAttachmentLevel(query) {
  return request({
    url: '/kms/attachment-level/list',
    method: 'get',
    params: query
  })
}

// 查询密级程度详细
export function getAttachmentLevel(id) {
  return request({
    url: '/kms/attachment-level/' + id,
    method: 'get'
  })
}

// 新增密级程度
export function addAttachmentLevel(data) {
  return request({
    url: '/kms/attachment-level',
    method: 'post',
    data: data
  })
}

// 修改密级程度
export function updateAttachmentLevel(data) {
  return request({
    url: '/kms/attachment-level',
    method: 'put',
    data: data
  })
}

// 删除密级程度
export function delAttachmentLevel(id) {
  return request({
    url: '/kms/attachment-level/' + id,
    method: 'delete'
  })
}
