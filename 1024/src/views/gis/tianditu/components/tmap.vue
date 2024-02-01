<template>
  <div>
    <remote-js src="http://api.tianditu.gov.cn/api?v=4.0&tk=81bf1620a6d719f1ed9bfd37c45fa64b" @load="initMap" />
    <div id="mapDiv" />
  </div>
</template>

<script>
export default {
  name: 'Tmap',
  components: {
    'remote-js': {
      render(createElement) {
        var self = this
        return createElement('script', {
          attrs: {
            type: 'text/javascript',
            src: this.src
          },
          on: {
            load: function(event) {
              self.$emit('load', event)
            },
            error: function(event) {
              self.$emit('error', event)
            },
            readystatechange: function(event) {
              if (this.readyState == 'complete') {
                self.$emit('load', event)
              }
            }
          }
        })
      },
      props: {
        src: {
          type: String,
          required: true
        }
      }
    }
  },
  data() {
    return {
      map: null,
      zoom: 16,
      lnglat: {
        x: 117.118,
        y: 36.65659
      }
    }
  },
  created() {},
  mounted() {

  },
  methods: {
    // EPSG:900913(墨卡托投影)
    initMap() {
      this.map = new T.Map('mapDiv', {
        projection: 'EPSG:4326'
      })
      this.map.centerAndZoom(new T.LngLat(this.lnglat.x, this.lnglat.y), this.zoom)
      var military = new T.Control.militarySymbols({
        position: T_ANCHOR_TOP_LEFT
      })
      this.map.addControl(military)
      map = this.map
    }
  }
}
</script>

<style lang="scss" scoped>
  #mapDiv {
    width: 100%;
    height: calc(100vh - 100px);
  }
</style>
