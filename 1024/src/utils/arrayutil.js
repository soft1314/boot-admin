/**
 * Created by Lilin on 2021/04/26.
 * 数据、集合类工具
 */

export function optionValueLabelConverter(option, value) {
  for (var i = 0; i < option.length; i++) {
    if (value === option[i].value || ''+value === option[i].value || value === ''+option[i].value) {
      return option[i].label
    }
  }
  return '未知'
}
