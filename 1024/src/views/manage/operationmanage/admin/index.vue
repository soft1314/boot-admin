<template>
    <div >
        <iframe src="http://127.0.0.1:5120/wallboard" id="map" scrolling="auto" frameborder="0" style="position:absolute;top:4px;left: 10px;right:0px;bottom:10px;"></iframe>
    </div>
</template>


<script>
    export default {
        data () {
            return {
            }
        },
        mounted(){
            /**
            * iframe-宽高自适应显示
            */
            function changeMapIframe(){
                const map = document.getElementById('map');
                const deviceWidth = document.body.clientWidth;
                const deviceHeight = document.body.clientHeight;
                map.style.width = (Number(deviceWidth)-240) + 'px'; //数字是页面布局宽度差值
                map.style.height = (Number(deviceHeight)) + 'px'; //数字是页面布局高度差
            }

            changeMapIframe()

            window.onresize = function(){
                changeMapIframe()
            }
        }
    }
</script>
