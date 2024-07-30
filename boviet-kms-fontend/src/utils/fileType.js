// 根据文件类型（文件后缀）获取icon的Class名
export function fileIconStyle(fileType) {
  fileType = fileType ? fileType.toLowerCase() : ''
  let iconClass = ''
  switch (fileType) {
    case 'doc':
    case 'docx':
      iconClass = 'ic-square_word'
      break
    case 'xls':
    case 'xlsx':
      iconClass = 'ic-square_excel'
      break
    case 'ppt':
    case 'pptx':
      iconClass = 'ic-square_pdf'
      break
    case 'pdf':
      iconClass = 'ic-pdf1'
      break
    case 'txt':
      iconClass = 'ic-square_txt'
      break
    case 'zip':
      iconClass = 'ic-zhishifenlei'
      break
    case 'jpg':
    case 'jpeg':
    case 'png':
    case 'gif':
      iconClass = 'ic-photo'
      break
    case 'mp4':
    case 'avi':
    case 'rmvb':
    case '3gp':
    case 'rm':
    case 'm4v':
      iconClass = 'ic-video'
      break
    case 'mp3':
    case 'acc':
    case 'm4p':
      iconClass = 'ic-audio'
      break
    default:
      iconClass = 'ic-wendang'
      break
  }
  return iconClass
}

// 判断是否有文件后缀
export function isFile(file) {
  const arr = ['doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'pdf', 'txt', 'zip', 'jpg', 'jpeg', 'png', 'gif', 'mp4'
    , 'avi', 'rmvb', '3gp', 'rm', 'm4v', 'mp3', 'acc', 'm4p']
  return arr.indexOf(file) !== -1
}

// 获取文件类型后缀名
export function fileType(file) {
  const arr = file.replace(/<[^>]+>/g, '').split('.')
  const arrLen = file.replace(/<[^>]+>/g, '').split('.').length
  return arr[arrLen - 1].toLowerCase()
}

// 根据文件类型 定义icon
export function fileIcon(file) {
  let icon = ''
  switch (file) {
    case 'doc':
    case 'docx':
      icon = 'ic-square_word'
      break
    case 'xls':
    case 'xlsx':
      icon = 'ic-square_excel'
      break
    case 'ppt':
    case 'pptx':
      icon = 'ic-square_pdf'
      break
    case 'pdf':
      icon = 'ic-PDF1'
      break
    case 'txt':
      icon = 'ic-square_txt'
      break
    case 'zip':
      icon = 'ic-zhishifenlei'
      break
    case 'jpg':
    case 'jpeg':
    case 'png':
    case 'gif':
      icon = 'ic-photo'
      break
    case 'mp4':
    case 'avi':
    case 'rmvb':
    case '3gp':
    case 'rm':
    case 'm4v':
      icon = 'ic-video'
      break
    case 'mp3':
    case 'acc':
    case 'm4p':
      icon = 'ic-audio'
      break
  }
  return icon
}
