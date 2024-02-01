<template>
  <div id="mapDiv" />
</template>
<script>
export default {
  name: 'Map',
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      // 初始化地图对象
      var map = new T.Map('mapDiv')
      // 确定经纬度坐标
      var lnglat = new T.LngLat(117.118, 36.65659)
      // 设置地图级别
      var zoom = 16
      // 地图初始化,定位，级别
      map.centerAndZoom(lnglat, zoom)

      // 创建标注对象
      var marker = new T.Marker(new T.LngLat(116.411794, 39.9068))
      // 向地图上添加标注
      map.addOverLay(marker)
      // 创建标注对象
      var marker1 = new T.Marker(new T.LngLat(117.118, 36.65659))
      // 向地图上添加标注
      map.addOverLay(marker1)
      // 添加比例尺控件
      map.addControl(new T.Control.Scale())
      // map.addControl(new T.Control.MousePosition())
      // 添加缩放控件
      // map.addControl(new T.Control.Zoom())
      // 允许鼠标滚轮缩放地图
      map.enableScrollWheelZoom()
      var military = new T.Control.militarySymbols({
        position: T_ANCHOR_TOP_LEFT
      })
      map.addControl(military)
    }
  }
}
</script>
<style>
  #mapDiv {
    width: 100%;
    height: 800px;
  }
  body,html{width:100%;height:100%;margin:0;font-family:"Microsoft YaHei"}
  input,b,p{margin-left:5px;font-size:14px}
</style>
