const commonUtils = {
  CheckPositiveNumber: function(num) {
    const reg = /^[1-9]\d*$/
    return reg.test(num)
  },
  /**
   * 代码转名称 value--->label
   */
  optoinValue2Lable(options, val) {
    if (options === undefined) {
      return val
    }
    val = val + ''
    for (var i = 0; i < options.length; i++) {
      if (options[i].value === val) {
        return options[i].label
      }
    }
    return val
  },
  /**
   * 代码转名称 value--->label
   */
  yesNo2Lable(val) {
    if (val === '1') {
      return '是'
    } else if (val === '0') {
      return '否'
    } else {
      return val
    }
  },
  /**
   * Map复制
   */
  mapCopyReferSrc(src, dst) {
    for (const key in src) {
      dst[key] = src[key] // 将每个属性复制到dst对象
    }
    return dst
  },

  /**
   * Map复制
   */
  mapCopyReferDst(src, dst) {
    for (const key in dst) {
      dst[key] = src[key] // 将每个属性复制到dst对象
    }
    return dst
  },

  /**
   * 合并多个MAP
   */
  mergeMap(map1) {
    const map = {}
    for (let i = 0; i < arguments.length; i++) {
      const currentMap = arguments[i]
      for (var key in currentMap) {
        map[key] = currentMap[key]
      }
    }
    return map
  },
  shadowCopy(obj) {
    if (typeof obj !== 'object') return
    var newObj
    // 保留对象的constructor属性
    if (obj.constructor === Array) {
      newObj = []
    } else {
      newObj = {}
      newObj.constructor = obj.constructor
    }
    for (const prop in obj) {
      // if (obj.hasOwnProperty(prop)) {
      if (Object.prototype.hasOwnProperty.call(obj, prop)) {
        newObj[prop] = obj[prop]
      }
    }
    return newObj
  },
  // deepCopy(obj) {
  //   var newObj = obj.constructor === Array ? [] : {}
  //   newObj.constructor = obj.constructor
  //   if (typeof obj !== 'object') {
  //     return
  //   } else if (window.JSON) {
  //     // 若需要考虑特殊的数据类型，如正则，函数等，需把这个else if去掉即可
  //     newObj = JSON.parse(JSON.stringify(obj))
  //   } else {
  //     for (var prop in obj) {
  //       if (obj[prop].constructor === RegExp || obj[prop].constructor === Date) {
  //         newObj[prop] = obj[prop]
  //       } else if (typeof obj[prop] === 'object') {
  //         // 递归
  //         newObj[prop] = deepCopy(obj[prop])
  //       } else {
  //         newObj[prop] = obj[prop]
  //       }
  //     }
  //   }
  //   return newObj
  // },
  // 格式化时间
  dateTimeFormat(dt) {
    if (dt == null) {
      return null
    }
    const dat = new Date(dt)
    // 获取年月日，时间
    var year = dat.getFullYear()
    var mon = (dat.getMonth() + 1) < 10 ? '0' + (dat.getMonth() + 1) : dat.getMonth() + 1
    var data = dat.getDate() < 10 ? '0' + (dat.getDate()) : dat.getDate()
    var hour = dat.getHours() < 10 ? '0' + (dat.getHours()) : dat.getHours()
    var min = dat.getMinutes() < 10 ? '0' + (dat.getMinutes()) : dat.getMinutes()
    var seon = dat.getSeconds() < 10 ? '0' + (dat.getSeconds()) : dat.getSeconds()

    var newDate = year + '-' + mon + '-' + data + ' ' + hour + ':' + min + ':' + seon
    return newDate
  },
  dateFormat(dt) {
    if (dt == null) {
      return null
    }
    const dat = new Date(dt)
    // 获取年月日，时间
    var year = dat.getFullYear()
    var mon = (dat.getMonth() + 1) < 10 ? '0' + (dat.getMonth() + 1) : dat.getMonth() + 1
    var data = dat.getDate() < 10 ? '0' + (dat.getDate()) : dat.getDate()

    var newDate = year + '-' + mon + '-' + data
    return newDate
  },
  genObjectURL(file) {
    let url
    if (window.createObjectURL) {
      url = window.createObjectURL(file)
    } else if (window.URL) {
      url = window.URL.createObjectURL(file)
    } else if (window.webkitURL) {
      url = window.webkitURL.createObjectURL(file)
    }
    return url
  },
  contains(arr, val) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i] === val) {
        return true
      }
    }
    return false
  }
}
export default commonUtils
