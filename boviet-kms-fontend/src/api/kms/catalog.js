import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listCatalog(query) {
  return request({
    url: '/kms/catalog/list',
    method: 'get',
    params: query
  })
}

// 查询列表
export function listCatalogForTree(query) {
  return request({
    url: '/kms/catalog/listForTree',
    method: 'get',
    params: query
  })
}

// 查询列表（排除节点）
export function listCatalogExcludeChild(catalogId) {
  return request({
    url: '/kms/catalog/list/exclude/' + catalogId,
    method: 'get'
  })
}

// 查询【请填写功能名称】详细
export function getCatalog(id) {
  return request({
    url: '/kms/catalog/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addCatalog(data) {
  return request({
    url: '/kms/catalog',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCatalog(data) {
  return request({
    url: '/kms/catalog',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCatalog(id) {
  return request({
    url: '/kms/catalog/' + id,
    method: 'delete'
  })
}
