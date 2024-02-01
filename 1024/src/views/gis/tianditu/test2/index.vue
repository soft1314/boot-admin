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

      map.addControl(new T.Control.Scale())
      map.addControl(new T.Control.MousePosition())
      map.addControl(new T.Control.Zoom())
    }
  }
}
</script>
<style>
  #mapDiv {
    width: 100%;
    height: 800px;
  }
</style>
