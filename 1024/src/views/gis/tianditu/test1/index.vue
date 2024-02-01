<template>
  <div>
    <div id="yzMap" style="position: absolute; width: 85%; height: 80%" />
  </div>
</template>
<script>
export default {
  name: 'Tianditu',
  data() {
    return {
    }
  },
  mounted: () => {
    const a = new Promise((resolve, reject) => {
      console.log(reject)
      // 如果已加载直接返回
      if (window.T) {
        console.log('地图脚本初始化成功...')
        resolve(window.T)
      }
    })
    const map = new window.T.Map('yzMap')
    map.centerAndZoom(new window.T.LngLat(116.401003, 39.903117), 12)
    console.log(a)
  },
  methods: {
  }
}

</script>
<style scoped>
</style>
