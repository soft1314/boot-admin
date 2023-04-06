<template>
  <a href="http://127.0.0.1:9527/#/table/dynamic-table" class="todo-corner" aria-label="View source on Github">
    <svg width="80" height="80" viewBox="0 0 250 250" style="fill:#40c9c6; color:#fff;" aria-hidden="true">
      <path d="M0,0 L250,250 L250,0 Z" />
    </svg>
    <div id="divPiao" style="position:absolute; top:20px; left:45px; width:30px; height:20px; background-color:#40c9c6;">
      <el-tooltip class="item" effect="dark" content="Left Bottom 提示文字" placement="left-end">
        <el-badge :value="12" class="item" />
      </el-tooltip>
    </div>
  </a>
</template>

<style scoped>
  .todo-corner:hover .octo-arm {
    animation: octocat-wave 560ms ease-in-out
  }

  @keyframes octocat-wave {

    0%,
    100% {
      transform: rotate(0)
    }

    20%,
    60% {
      transform: rotate(-25deg)
    }

    40%,
    80% {
      transform: rotate(10deg)
    }
  }

  @media (max-width:500px) {
    .todo-corner:hover .octo-arm {
      animation: none
    }

    .todo-corner .octo-arm {
      animation: octocat-wave 560ms ease-in-out
    }
  }
</style>
