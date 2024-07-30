import request from '@/utils/request'

// 查询知识列表
export function listKms(query) {
  return request({
    url: '/kms/main/list',
    method: 'get',
    params: query
  })
}



// 查询知识详细
export function getKms(kId) {
  return request({
    url: '/kms/main/view' + kId,
    method: 'get'
  })
}


// 新增知识
export function addKms(data) {
  return request({
    url: '/kms/main/add',
    method: 'post',
    data: data
  })
}

// 修改知识
export function updateKms(data) {
  return request({
    url: '/kms/main/update',
    method: 'put',
    data: data
  })
}

// 删除知识
export function delKms(kId) {
  return request({
    url: '/kms/main/delete' + kId,
    method: 'delete'
  })
}
