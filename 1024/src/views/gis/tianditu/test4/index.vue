<template>
  <div id="mapDiv" />
</template>
<script>
export default {
  name: 'Map',
  data() {
    return {
    }
  },
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      // 初始化地图对象
      var zoom = 12
      var map = new window.T.Map('mapDiv')
      // 设置显示地图的中心点和级别
      map.centerAndZoom(new window.T.LngLat(116.40969, 39.89945), zoom)
      // 允许鼠标滚轮缩放地图
      map.enableScrollWheelZoom()
      var military = new window.T.Control.militarySymbols({
        position: T_ANCHOR_TOP_LEFT
      })
      map.addControl(military)
    }
  }
}
</script>
<style type="text/css">
  body,
  html {
    width: 100%;
    height: 100%;
    margin: 0;
    font-family: "Microsoft YaHei"
  }

  #mapDiv {
    width: 100%;
    height: 400px
  }

  input,
  b,
  p {
    margin-left: 5px;
    font-size: 14px
  }
</style>
