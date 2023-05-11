const commonResultCode = {
  SUCCESS() {
    // 正确
    return 100
  },
  FUNCTION_ALREADY_EXIST_ERROR() {
    // 文件已存在
    return 104
  }
}
export default commonResultCode
