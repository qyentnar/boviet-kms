import request from '@/utils/request'

//部署流程
export function deploy(data) {
  return request({
    url: '/sys/activiti/deploy',
    method: 'post',
    data: data
  })
}
export function add(data) {
  return request({
    url: '/sys/activiti/add',
    method: 'post',
    data: data
  })
}


//开启流程实例
export function startProcess(data) {
  return request({
    url: '/sys/activiti/startProcess',
    method: 'post',
    data: data
  })
}
