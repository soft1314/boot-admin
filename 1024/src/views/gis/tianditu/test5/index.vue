<template>
  <Tmap />
</template>
<script>
import Tmap from '@/views/gis/tianditu/components/tmap'
export default {
  name: 'Map',
  components: {
    Tmap
  },
  mounted() {},
  methods: {
    initMap() {}
  }
}
</script>
<style>
  #mapDiv {
    width: 100%;
    height: 800px;
  }
</style>
