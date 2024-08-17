import request from '@/utils/request'

// 查询流程模板列表
export function listTemplate(query) {
  return request({
    url: '/sys/activiti/template/list',
    method: 'get',
    params: query
  })
}

// 查询流程模板详细
export function getTemplate(id) {
  return request({
    url: '/sys/activiti/template/getTemplate/' + id,
    method: 'get'
  })
}

// 新增流程模板
export function addTemplate(data) {
  return request({
    url: '/sys/activiti/template/add',
    method: 'post',
    data: data
  })
}

// 修改流程模板
export function updateTemplate(data) {
  return request({
    url: '/sys/activiti/template',
    method: 'put',
    data: data
  })
}

// 删除流程模板
export function delTemplate(id) {
  return request({
    url: '/sys/activiti/template/' + id,
    method: 'delete'
  })
}

// 查询审批任务
export function findTask(key,assignee) {
  return request({
    url: '/sys/activiti/template/findTask/' + key+"/"+assignee,
    method: 'get'
  })
}

export function completeTask(data) {
  return request({
    url: '/sys/activiti/template/completeTask',
    method: 'post',
    data: data
  })
}

// 历史审批记录
export function findHistory(key) {
  return request({
    url: '/sys/activiti/template/findHistory/' + key,
    method: 'get'
  })
}

export function findAllNodes(key) {
  return request({
    url: '/sys/activiti/template/findAllNodes/' + key,
    method: 'get'
  })
}

export function currentProcess(key) {
  return request({
    url: '/sys/activiti/template/currentProcess/' + key,
    method: 'get'
  })
}


