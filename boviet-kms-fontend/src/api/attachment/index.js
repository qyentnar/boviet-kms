import request from '@/utils/request'

export function getFileById(id){
    return request({
        url: '/sys/attachment/file'+ id,
        method: 'get'
    })
}