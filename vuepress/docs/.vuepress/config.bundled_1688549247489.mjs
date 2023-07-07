// docs/.vuepress/config.ts
import { defineConfig4CustomTheme } from "vuepress/config";
import dayjs from "dayjs";

// docs/.vuepress/config/baiduCode.ts
var baiduCode_default = "503f098e7e5b3a5b5d8c5fc2938af002";

// docs/.vuepress/config/htmlModules.ts
var htmlModule = {};
var htmlModules_default = htmlModule;

// docs/.vuepress/config.ts
var DOMAIN_NAME = "soft1314.com";
var WEB_SITE = `https://${DOMAIN_NAME}`;
var config_default = defineConfig4CustomTheme({
  theme: "vdoing",
  locales: {
    "/": {
      lang: "zh-CN",
      title: "\u8D85\u7136\u697C",
      description: "\u5B66\u4E60\u3001\u751F\u6D3B\u3001\u601D\u8003"
    }
  },
  themeConfig: {
    nav: [
      { text: "\u9996\u9875", link: "/" },
      {
        text: "\u5F00\u6E90",
        link: "/opensource/",
        items: [
          {
            text: "\u5F00\u6E90\u9879\u76EE",
            items: [
              { text: "JavaScript", link: "/pages/8143cc480faf9a11/" }
            ]
          }
        ]
      },
      {
        text: "\u535A\u5BA2",
        link: "/myblog/",
        items: [
          { text: "HTML", link: "/pages/8309a5b876fc95e3/" },
          { text: "CSS", link: "/pages/0a83b083bdf257cb/" }
        ]
      },
      {
        text: "\u8F6C\u8F7D",
        link: "/technology/",
        items: [
          { text: "\u6280\u672F\u6587\u6863", link: "/pages/9a7ee40fc232253e/" },
          { text: "GitHub\u6280\u5DE7", link: "/pages/4c778760be26d8b3/" },
          { text: "Nodejs", link: "/pages/117708e0af7f0bd9/" },
          { text: "\u535A\u5BA2\u642D\u5EFA", link: "/pages/41f87d890d0a02af/" }
        ]
      },
      { text: "\u5173\u4E8E", link: "/about/" },
      {
        text: "\u6536\u85CF",
        link: "/pages/beb6c0bd8a66cea6/"
      },
      {
        text: "\u7D22\u5F15",
        link: "/archives/",
        items: [
          { text: "\u5206\u7C7B", link: "/categories/" },
          { text: "\u6807\u7B7E", link: "/tags/" },
          { text: "\u5F52\u6863", link: "/archives/" }
        ]
      }
    ],
    sidebarDepth: 2,
    logo: "/img/logo.png",
    repo: "soft1314",
    searchMaxSuggestions: 10,
    lastUpdated: "\u4E0A\u6B21\u66F4\u65B0",
    docsDir: "docs",
    editLinks: true,
    editLinkText: "\u7F16\u8F91",
    sidebar: "structuring",
    author: {
      name: "soft1314",
      link: "https://soft1314.github.io/"
    },
    blogger: {
      avatar: "/img/logo.png",
      name: "soft1314",
      slogan: ""
    },
    social: {
      icons: [
        {
          iconClass: "icon-youjian",
          title: "\u53D1\u90AE\u4EF6",
          link: "mailto:37514367@qq.com"
        },
        {
          iconClass: "icon-github",
          title: "GitHub",
          link: "https://github.com/soft1314"
        },
        {
          iconClass: "icon-erji",
          title: "\u542C\u97F3\u4E50",
          link: "https://music.163.com"
        }
      ]
    },
    footer: {
      createYear: 2023,
      copyrightInfo: 'Soft1314 | <a href="https://github.com/soft1314/boot-admin/" target="_blank">MIT License</a>'
    },
    extendFrontmatter: {
      author: {
        name: "Soft1314",
        link: "https://github.com/soft1314"
      }
    },
    htmlModules: htmlModules_default
  },
  head: [
    ["link", { rel: "icon", href: "/img/favicon.ico" }],
    [
      "meta",
      {
        name: "keywords",
        content: "\u524D\u7AEF\u535A\u5BA2,\u4E2A\u4EBA\u6280\u672F\u535A\u5BA2,\u524D\u7AEF,\u524D\u7AEF\u5F00\u53D1,\u524D\u7AEF\u6846\u67B6,web\u524D\u7AEF,\u524D\u7AEF\u9762\u8BD5\u9898,\u6280\u672F\u6587\u6863,\u5B66\u4E60,\u9762\u8BD5,JavaScript,js,ES6,TypeScript,vue,python,css3,html5,Node,git,github,markdown"
      }
    ],
    ["meta", { name: "baidu-site-verification", content: "7F55weZDDc" }],
    ["meta", { name: "theme-color", content: "#11a8cd" }]
  ],
  plugins: [
    [
      "sitemap",
      {
        hostname: WEB_SITE
      }
    ],
    "vuepress-plugin-baidu-autopush",
    [
      "vuepress-plugin-baidu-tongji",
      {
        hm: baiduCode_default
      }
    ],
    [
      "thirdparty-search",
      {
        thirdparty: [
          {
            title: "\u5728MDN\u4E2D\u641C\u7D22",
            frontUrl: "https://developer.mozilla.org/zh-CN/search?q=",
            behindUrl: ""
          },
          {
            title: "\u5728Runoob\u4E2D\u641C\u7D22",
            frontUrl: "https://www.runoob.com/?s="
          },
          {
            title: "\u5728Vue API\u4E2D\u641C\u7D22",
            frontUrl: "https://cn.vuejs.org/v2/api/#"
          },
          {
            title: "\u5728Bing\u4E2D\u641C\u7D22",
            frontUrl: "https://cn.bing.com/search?q="
          },
          {
            title: "\u901A\u8FC7\u767E\u5EA6\u641C\u7D22\u672C\u7AD9\u7684",
            frontUrl: `https://www.baidu.com/s?wd=site%3A${DOMAIN_NAME}%20`
          }
        ]
      }
    ],
    [
      "one-click-copy",
      {
        copySelector: ['div[class*="language-"] pre', 'div[class*="aside-code"] aside'],
        copyMessage: "\u590D\u5236\u6210\u529F",
        duration: 1e3,
        showInMobile: false
      }
    ],
    [
      "demo-block",
      {
        settings: {
          jsfiddle: false,
          codepen: true,
          horizontal: false
        }
      }
    ],
    [
      "vuepress-plugin-zooming",
      {
        selector: ".theme-vdoing-content img:not(.no-zoom)",
        options: {
          bgColor: "rgba(0,0,0,0.6)"
        }
      }
    ],
    [
      "vuepress-plugin-comment",
      {
        choosen: "gitalk",
        options: {
          clientID: "a6e1355287947096b88b",
          clientSecret: "f0e77d070fabfcd5af95bebb82b2d574d7248d71",
          repo: "blog-gitalk-comment",
          owner: "xugaoyi",
          admin: ["xugaoyi"],
          pagerDirection: "last",
          id: "<%- (frontmatter.permalink || frontmatter.to.path).slice(-16) %>",
          title: "\u300C\u8BC4\u8BBA\u300D<%- frontmatter.title %>",
          labels: ["Gitalk", "Comment"],
          body: "\u9875\u9762\uFF1A<%- window.location.origin + (frontmatter.to.path || window.location.pathname) %>"
        }
      }
    ],
    [
      "@vuepress/last-updated",
      {
        transformer: (timestamp, lang) => {
          return dayjs(timestamp).format("YYYY/MM/DD, HH:mm:ss");
        }
      }
    ]
  ],
  markdown: {
    lineNumbers: true,
    extractHeaders: ["h2", "h3", "h4", "h5", "h6"]
  },
  extraWatchFiles: [
    ".vuepress/config.ts",
    ".vuepress/config/htmlModules.ts"
  ]
});
export {
  config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsiZG9jcy8udnVlcHJlc3MvY29uZmlnLnRzIiwgImRvY3MvLnZ1ZXByZXNzL2NvbmZpZy9iYWlkdUNvZGUudHMiLCAiZG9jcy8udnVlcHJlc3MvY29uZmlnL2h0bWxNb2R1bGVzLnRzIl0sCiAgInNvdXJjZXNDb250ZW50IjogWyIvKipcclxuICogXHU2M0QwXHU3OTNBXHVGRjFBXHU1OTgyXHU2MEE4XHU2MEYzXHU0RjdGXHU3NTI4SlNcdTcyNDhcdTY3MkNcdTc2ODRcdTkxNERcdTdGNkVcdTY1ODdcdTRFRjZcdTUzRUZcdTUzQzJcdTgwMDNcdUZGMUFodHRwczovL2dpdGh1Yi5jb20vc29mdDEzMTQvdnVlcHJlc3MtdGhlbWUtdmRvaW5nL3RyZWUvYTJmMDNlOTkzZGQyZjJhM2FmZGM1N2NmNzJhZGZjNmYxYjZiMGMzMi9kb2NzLy52dWVwcmVzc1xyXG4gKi9cclxuaW1wb3J0IHsgcmVzb2x2ZSB9IGZyb20gJ3BhdGgnXHJcbmltcG9ydCB7IGRlZmluZUNvbmZpZzRDdXN0b21UaGVtZSwgVXNlclBsdWdpbnMgfSBmcm9tICd2dWVwcmVzcy9jb25maWcnXHJcbmltcG9ydCB7IFZkb2luZ1RoZW1lQ29uZmlnIH0gZnJvbSAndnVlcHJlc3MtdGhlbWUtdmRvaW5nL3R5cGVzJ1xyXG5pbXBvcnQgZGF5anMgZnJvbSAnZGF5anMnXHJcbmltcG9ydCBiYWlkdUNvZGUgZnJvbSAnLi9jb25maWcvYmFpZHVDb2RlJyAvLyBcdTc2N0VcdTVFQTZcdTdFREZcdThCQTFobVx1NzgwMVxyXG5pbXBvcnQgaHRtbE1vZHVsZXMgZnJvbSAnLi9jb25maWcvaHRtbE1vZHVsZXMnIC8vIFx1ODFFQVx1NUI5QVx1NEU0OVx1NjNEMlx1NTE2NVx1NzY4NGh0bWxcdTU3NTdcclxuXHJcbmNvbnN0IERPTUFJTl9OQU1FID0gJ3NvZnQxMzE0LmNvbScgLy8gXHU1N0RGXHU1NDBEIChcdTRFMERcdTVFMjZodHRwcylcclxuY29uc3QgV0VCX1NJVEUgPSBgaHR0cHM6Ly8ke0RPTUFJTl9OQU1FfWAgLy8gXHU3RjUxXHU1NzQwXHJcblxyXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWc0Q3VzdG9tVGhlbWU8VmRvaW5nVGhlbWVDb25maWc+KHtcclxuICB0aGVtZTogJ3Zkb2luZycsIC8vIFx1NEY3Rlx1NzUyOG5wbVx1NEUzQlx1OTg5OFx1NTMwNVxyXG4gIC8vIHRoZW1lOiByZXNvbHZlKFwiQzpcXFxcbGlmZVxcXFxob2JieVxcXFx2dWVwcmVzc1xcXFx2dWVwcmVzcy10aGVtZS12ZG9pbmdcXFxcZG9jc1xcXFwudnVlcHJlc3NcIiwgJy4uLy4uL3Zkb2luZycpLCAvLyBcdTRGN0ZcdTc1MjhcdTY3MkNcdTU3MzBcdTRFM0JcdTk4OThcdTUzMDVcclxuXHJcbiAgbG9jYWxlczoge1xyXG4gICAgJy8nOiB7XHJcbiAgICAgIGxhbmc6ICd6aC1DTicsXHJcbiAgICAgIHRpdGxlOiBcIlx1OEQ4NVx1NzEzNlx1Njk3Q1wiLFxyXG4gICAgICBkZXNjcmlwdGlvbjogJ1x1NUI2Nlx1NEU2MFx1MzAwMVx1NzUxRlx1NkQzQlx1MzAwMVx1NjAxRFx1ODAwMycsXHJcbiAgICB9XHJcbiAgfSxcclxuICAvLyBiYXNlOiAnLycsIC8vIFx1OUVEOFx1OEJBNCcvJ1x1MzAwMlx1NTk4Mlx1Njc5Q1x1NEY2MFx1NjBGM1x1NUMwNlx1NEY2MFx1NzY4NFx1N0Y1MVx1N0FEOVx1OTBFOFx1N0Y3Mlx1NTIzMFx1NTk4MiBodHRwczovL2Zvby5naXRodWIuaW8vYmFyL1x1RkYwQ1x1OTBBM1x1NEU0OCBiYXNlIFx1NUU5NFx1OEJFNVx1ODhBQlx1OEJCRVx1N0Y2RVx1NjIxMCBcIi9iYXIvXCIsXHVGRjA4XHU1NDI2XHU1MjE5XHU5ODc1XHU5NzYyXHU1QzA2XHU1OTMxXHU1M0JCXHU2ODM3XHU1RjBGXHU3QjQ5XHU2NTg3XHU0RUY2XHVGRjA5XHJcblxyXG4gIC8vIFx1NEUzQlx1OTg5OFx1OTE0RFx1N0Y2RVxyXG4gIHRoZW1lQ29uZmlnOiB7XHJcbiAgICAvLyBcdTVCRkNcdTgyMkFcdTkxNERcdTdGNkVcclxuICAgIG5hdjogW1xyXG4gICAgICB7IHRleHQ6ICdcdTk5OTZcdTk4NzUnLCBsaW5rOiAnLycgfSxcclxuICAgICAge1xyXG4gICAgICAgIHRleHQ6ICdcdTVGMDBcdTZFOTAnLFxyXG4gICAgICAgIGxpbms6ICcvb3BlbnNvdXJjZS8nLCAvL1x1NzZFRVx1NUY1NVx1OTg3NVx1OTRGRVx1NjNBNVx1RkYwQ1x1NkI2NFx1NTkwNGxpbmtcdTY2MkZ2ZG9pbmdcdTRFM0JcdTk4OThcdTY1QjBcdTU4OUVcdTc2ODRcdTkxNERcdTdGNkVcdTk4NzlcdUZGMENcdTY3MDlcdTRFOENcdTdFQTdcdTVCRkNcdTgyMkFcdTY1RjZcdUZGMENcdTUzRUZcdTRFRTVcdTcwQjlcdTUxRkJcdTRFMDBcdTdFQTdcdTVCRkNcdTgyMkFcdThERjNcdTUyMzBcdTc2RUVcdTVGNTVcdTk4NzVcclxuICAgICAgICBpdGVtczogW1xyXG4gICAgICAgICAgLy8gXHU4QkY0XHU2NjBFXHVGRjFBXHU0RUU1XHU0RTBCXHU2MjQwXHU2NzA5bGlua1x1NzY4NFx1NTAzQ1x1NTNFQVx1NjYyRlx1NTcyOFx1NzZGOFx1NUU5NG1kXHU2NTg3XHU0RUY2XHU1OTM0XHU5MEU4XHU1QjlBXHU0RTQ5XHU3Njg0XHU2QzM4XHU0RTQ1XHU5NEZFXHU2M0E1XHVGRjA4XHU0RTBEXHU2NjJGXHU0RUMwXHU0RTQ4XHU3Mjc5XHU2QjhBXHU3RjE2XHU3ODAxXHVGRjA5XHUzMDAyXHU1M0U2XHU1OTE2XHVGRjBDXHU2Q0U4XHU2MTBGXHU3RUQzXHU1QzNFXHU2NjJGXHU2NzA5XHU2NTlDXHU2NzYwXHU3Njg0XHJcbiAgICAgICAgICB7XHJcbiAgICAgICAgICAgIHRleHQ6ICdcdTVGMDBcdTZFOTBcdTk4NzlcdTc2RUUnLFxyXG4gICAgICAgICAgICBpdGVtczogW1xyXG4gICAgICAgICAgICAgIHsgdGV4dDogJ0phdmFTY3JpcHQnLCBsaW5rOiAnL3BhZ2VzLzgxNDNjYzQ4MGZhZjlhMTEvJyB9LFxyXG4gICAgICAgICAgICBdLFxyXG4gICAgICAgICAgfVxyXG4gICAgICAgIF0sXHJcbiAgICAgIH0sXHJcbiAgICAgIHtcclxuICAgICAgICB0ZXh0OiAnXHU1MzVBXHU1QkEyJyxcclxuICAgICAgICBsaW5rOiAnL215YmxvZy8nLFxyXG4gICAgICAgIGl0ZW1zOiBbXHJcbiAgICAgICAgICB7IHRleHQ6ICdIVE1MJywgbGluazogJy9wYWdlcy84MzA5YTViODc2ZmM5NWUzLycgfSxcclxuICAgICAgICAgIHsgdGV4dDogJ0NTUycsIGxpbms6ICcvcGFnZXMvMGE4M2IwODNiZGYyNTdjYi8nIH0sXHJcbiAgICAgICAgXSxcclxuICAgICAgfSxcclxuICAgICAge1xyXG4gICAgICAgIHRleHQ6ICdcdThGNkNcdThGN0QnLFxyXG4gICAgICAgIGxpbms6ICcvdGVjaG5vbG9neS8nLFxyXG4gICAgICAgIGl0ZW1zOiBbXHJcbiAgICAgICAgICB7IHRleHQ6ICdcdTYyODBcdTY3MkZcdTY1ODdcdTY4NjMnLCBsaW5rOiAnL3BhZ2VzLzlhN2VlNDBmYzIzMjI1M2UvJyB9LFxyXG4gICAgICAgICAgeyB0ZXh0OiAnR2l0SHViXHU2MjgwXHU1REU3JywgbGluazogJy9wYWdlcy80Yzc3ODc2MGJlMjZkOGIzLycgfSxcclxuICAgICAgICAgIHsgdGV4dDogJ05vZGVqcycsIGxpbms6ICcvcGFnZXMvMTE3NzA4ZTBhZjdmMGJkOS8nIH0sXHJcbiAgICAgICAgICB7IHRleHQ6ICdcdTUzNUFcdTVCQTJcdTY0MkRcdTVFRkEnLCBsaW5rOiAnL3BhZ2VzLzQxZjg3ZDg5MGQwYTAyYWYvJyB9LFxyXG4gICAgICAgIF0sXHJcbiAgICAgIH0sXHJcbiAgICAgIHsgdGV4dDogJ1x1NTE3M1x1NEU4RScsIGxpbms6ICcvYWJvdXQvJyB9LFxyXG4gICAgICB7XHJcbiAgICAgICAgdGV4dDogJ1x1NjUzNlx1ODVDRicsXHJcbiAgICAgICAgbGluazogJy9wYWdlcy9iZWI2YzBiZDhhNjZjZWE2LycsXHJcbiAgICAgICAgLy8gaXRlbXM6IFtcclxuICAgICAgICAvLyAgIHsgdGV4dDogJ1x1N0Y1MVx1N0FEOScsIGxpbms6ICcvcGFnZXMvYmViNmMwYmQ4YTY2Y2VhNi8nIH0sXHJcbiAgICAgICAgLy8gICB7IHRleHQ6ICdcdThENDRcdTZFOTAnLCBsaW5rOiAnL3BhZ2VzL2VlZTgzYTkyMTFhNzBmOWQvJyB9LFxyXG4gICAgICAgIC8vICAgeyB0ZXh0OiAnVnVlXHU4RDQ0XHU2RTkwJywgbGluazogJy9wYWdlcy8xMmRmOGFjZTUyZDQ5M2Y2LycgfSxcclxuICAgICAgICAvLyBdLFxyXG4gICAgICB9LFxyXG4gICAgICB7XHJcbiAgICAgICAgdGV4dDogJ1x1N0QyMlx1NUYxNScsXHJcbiAgICAgICAgbGluazogJy9hcmNoaXZlcy8nLFxyXG4gICAgICAgIGl0ZW1zOiBbXHJcbiAgICAgICAgICB7IHRleHQ6ICdcdTUyMDZcdTdDN0InLCBsaW5rOiAnL2NhdGVnb3JpZXMvJyB9LFxyXG4gICAgICAgICAgeyB0ZXh0OiAnXHU2ODA3XHU3QjdFJywgbGluazogJy90YWdzLycgfSxcclxuICAgICAgICAgIHsgdGV4dDogJ1x1NUY1Mlx1Njg2MycsIGxpbms6ICcvYXJjaGl2ZXMvJyB9LFxyXG4gICAgICAgIF0sXHJcbiAgICAgIH0sXHJcbiAgICBdLFxyXG4gICAgc2lkZWJhckRlcHRoOiAyLCAvLyBcdTRGQTdcdThGQjlcdTY4MEZcdTY2M0VcdTc5M0FcdTZERjFcdTVFQTZcdUZGMENcdTlFRDhcdThCQTQxXHVGRjBDXHU2NzAwXHU1OTI3Mlx1RkYwOFx1NjYzRVx1NzkzQVx1NTIzMGgzXHU2ODA3XHU5ODk4XHVGRjA5XHJcbiAgICBsb2dvOiAnL2ltZy9sb2dvLnBuZycsIC8vIFx1NUJGQ1x1ODIyQVx1NjgwRmxvZ29cclxuICAgIHJlcG86ICdzb2Z0MTMxNCcsIC8vIFx1NUJGQ1x1ODIyQVx1NjgwRlx1NTNGM1x1NEZBN1x1NzUxRlx1NjIxMEdpdGh1Ylx1OTRGRVx1NjNBNVxyXG4gICAgc2VhcmNoTWF4U3VnZ2VzdGlvbnM6IDEwLCAvLyBcdTY0MUNcdTdEMjJcdTdFRDNcdTY3OUNcdTY2M0VcdTc5M0FcdTY3MDBcdTU5MjdcdTY1NzBcclxuICAgIGxhc3RVcGRhdGVkOiAnXHU0RTBBXHU2QjIxXHU2NkY0XHU2NUIwJywgLy8gXHU1RjAwXHU1NDJGXHU2NkY0XHU2NUIwXHU2NUY2XHU5NUY0XHVGRjBDXHU1RTc2XHU5MTREXHU3RjZFXHU1MjREXHU3RjAwXHU2NTg3XHU1QjU3ICAgc3RyaW5nIHwgYm9vbGVhbiAoXHU1M0Q2XHU1MDNDXHU0RTNBZ2l0XHU2M0QwXHU0RUE0XHU2NUY2XHU5NUY0KVxyXG4gICAgZG9jc0RpcjogJ2RvY3MnLCAvLyBcdTdGMTZcdThGOTFcdTc2ODRcdTY1ODdcdTRFRjZcdTU5MzlcclxuICAgIC8vIGRvY3NCcmFuY2g6ICdtYXN0ZXInLCAvLyBcdTdGMTZcdThGOTFcdTc2ODRcdTY1ODdcdTRFRjZcdTYyNDBcdTU3MjhcdTUyMDZcdTY1MkZcdUZGMENcdTlFRDhcdThCQTRtYXN0ZXJcdTMwMDIgXHU2Q0U4XHU2MTBGXHVGRjFBXHU1OTgyXHU2NzlDXHU0RjYwXHU3Njg0XHU1MjA2XHU2NTJGXHU2NjJGbWFpblx1NTIxOVx1NEZFRVx1NjUzOVx1NEUzQW1haW5cclxuICAgIGVkaXRMaW5rczogdHJ1ZSwgLy8gXHU1NDJGXHU3NTI4XHU3RjE2XHU4RjkxXHJcbiAgICBlZGl0TGlua1RleHQ6ICdcdTdGMTZcdThGOTEnLFxyXG5cclxuICAgIC8vKioqIFx1NEVFNVx1NEUwQlx1NjYyRlZkb2luZ1x1NEUzQlx1OTg5OFx1NzZGOFx1NTE3M1x1OTE0RFx1N0Y2RVx1RkYwQ1x1NjU4N1x1Njg2M1x1RkYxQWh0dHBzOi8vZG9jLnh1Z2FveWkuY29tL3BhZ2VzL2EyMGNlOC8gKioqLy9cclxuXHJcbiAgICAvLyBjYXRlZ29yeTogZmFsc2UsIC8vIFx1NjYyRlx1NTQyNlx1NjI1M1x1NUYwMFx1NTIwNlx1N0M3Qlx1NTI5Rlx1ODBGRFx1RkYwQ1x1OUVEOFx1OEJBNHRydWVcclxuICAgIC8vIHRhZzogZmFsc2UsIC8vIFx1NjYyRlx1NTQyNlx1NjI1M1x1NUYwMFx1NjgwN1x1N0I3RVx1NTI5Rlx1ODBGRFx1RkYwQ1x1OUVEOFx1OEJBNHRydWVcclxuICAgIC8vIGFyY2hpdmU6IGZhbHNlLCAvLyBcdTY2MkZcdTU0MjZcdTYyNTNcdTVGMDBcdTVGNTJcdTY4NjNcdTUyOUZcdTgwRkRcdUZGMENcdTlFRDhcdThCQTR0cnVlXHJcbiAgICAvLyBjYXRlZ29yeVRleHQ6ICdcdTk2OEZcdTdCMTQnLCAvLyBcdTc4OEVcdTcyNDdcdTUzMTZcdTY1ODdcdTdBRTBcdUZGMDhfcG9zdHNcdTY1ODdcdTRFRjZcdTU5MzlcdTc2ODRcdTY1ODdcdTdBRTBcdUZGMDlcdTk4ODRcdThCQkVcdTc1MUZcdTYyMTBcdTc2ODRcdTUyMDZcdTdDN0JcdTUwM0NcdUZGMENcdTlFRDhcdThCQTQnXHU5NjhGXHU3QjE0J1xyXG5cclxuICAgIC8vIHBhZ2VTdHlsZTogJ2xpbmUnLCAvLyBcdTk4NzVcdTk3NjJcdTk4Q0VcdTY4M0NcdUZGMENcdTUzRUZcdTkwMDlcdTUwM0NcdUZGMUEnY2FyZCdcdTUzNjFcdTcyNDcgfCAnbGluZScgXHU3RUJGXHVGRjA4XHU2NzJBXHU4QkJFXHU3RjZFYm9keUJnSW1nXHU2NUY2XHU2MjREXHU3NTFGXHU2NTQ4XHVGRjA5XHVGRjBDIFx1OUVEOFx1OEJBNCdjYXJkJ1x1MzAwMiBcdThCRjRcdTY2MEVcdUZGMUFjYXJkXHU2NUY2XHU4MENDXHU2NjZGXHU2NjNFXHU3OTNBXHU3MDcwXHU4MjcyXHU4ODZDXHU2MjU4XHU1MUZBXHU1MzYxXHU3MjQ3XHU2ODM3XHU1RjBGXHVGRjBDbGluZVx1NjVGNlx1ODBDQ1x1NjY2Rlx1NjYzRVx1NzkzQVx1N0VBRlx1ODI3Mlx1RkYwQ1x1NUU3Nlx1NEUxNFx1OTBFOFx1NTIwNlx1NkEyMVx1NTc1N1x1NUUyNlx1N0VCRlx1Njc2MVx1OEZCOVx1Njg0NlxyXG5cclxuICAgIC8vIGJvZHlCZ0ltZzogW1xyXG4gICAgLy8gICAnaHR0cHM6Ly9mYXN0bHkuanNkZWxpdnIubmV0L2doL3h1Z2FveWkvaW1hZ2Vfc3RvcmUvYmxvZy8yMDIwMDUwNzE3NTgyOC5qcGVnJyxcclxuICAgIC8vICAgJ2h0dHBzOi8vZmFzdGx5LmpzZGVsaXZyLm5ldC9naC94dWdhb3lpL2ltYWdlX3N0b3JlL2Jsb2cvMjAyMDA1MDcxNzU4NDUuanBlZycsXHJcbiAgICAvLyAgICdodHRwczovL2Zhc3RseS5qc2RlbGl2ci5uZXQvZ2gveHVnYW95aS9pbWFnZV9zdG9yZS9ibG9nLzIwMjAwNTA3MTc1ODQ2LmpwZWcnXHJcbiAgICAvLyBdLCAvLyBib2R5XHU4MENDXHU2NjZGXHU1OTI3XHU1NkZFXHVGRjBDXHU5RUQ4XHU4QkE0XHU2NUUwXHUzMDAyIFx1NTM1NVx1NUYyMFx1NTZGRVx1NzI0NyBTdHJpbmcgfCBcdTU5MUFcdTVGMjBcdTU2RkVcdTcyNDcgQXJyYXksIFx1NTkxQVx1NUYyMFx1NTZGRVx1NzI0N1x1NjVGNlx1OTY5NGJvZHlCZ0ltZ0ludGVydmFsXHU1MjA3XHU2MzYyXHU0RTAwXHU1RjIwXHUzMDAyXHJcbiAgICAvLyBib2R5QmdJbWdPcGFjaXR5OiAwLjUsIC8vIGJvZHlcdTgwQ0NcdTY2NkZcdTU2RkVcdTkwMEZcdTY2MEVcdTVFQTZcdUZGMENcdTkwMDlcdTUwM0MgMC4xfjEuMCwgXHU5RUQ4XHU4QkE0MC41XHJcbiAgICAvLyBib2R5QmdJbWdJbnRlcnZhbDogMTUsIC8vIGJvZHlcdTU5MUFcdTVGMjBcdTgwQ0NcdTY2NkZcdTU2RkVcdTY1RjZcdTc2ODRcdTUyMDdcdTYzNjJcdTk1RjRcdTk2OTQsIFx1OUVEOFx1OEJBNDE1XHVGRjBDXHU1MzU1XHU0RjREc1xyXG4gICAgLy8gdGl0bGVCYWRnZTogZmFsc2UsIC8vIFx1NjU4N1x1N0FFMFx1NjgwN1x1OTg5OFx1NTI0RFx1NzY4NFx1NTZGRVx1NjgwN1x1NjYyRlx1NTQyNlx1NjYzRVx1NzkzQVx1RkYwQ1x1OUVEOFx1OEJBNHRydWVcclxuICAgIC8vIHRpdGxlQmFkZ2VJY29uczogWyAvLyBcdTY1ODdcdTdBRTBcdTY4MDdcdTk4OThcdTUyNERcdTU2RkVcdTY4MDdcdTc2ODRcdTU3MzBcdTU3NDBcdUZGMENcdTlFRDhcdThCQTRcdTRFM0JcdTk4OThcdTUxODVcdTdGNkVcdTU2RkVcdTY4MDdcclxuICAgIC8vICAgJ1x1NTZGRVx1NjgwN1x1NTczMFx1NTc0MDEnLFxyXG4gICAgLy8gICAnXHU1NkZFXHU2ODA3XHU1NzMwXHU1NzQwMidcclxuICAgIC8vIF0sXHJcbiAgICAvLyBjb250ZW50QmdTdHlsZTogMSwgLy8gXHU2NTg3XHU3QUUwXHU1MTg1XHU1QkI5XHU1NzU3XHU3Njg0XHU4MENDXHU2NjZGXHU5OENFXHU2ODNDXHVGRjBDXHU5RUQ4XHU4QkE0XHU2NUUwLiAxIFx1NjVCOVx1NjgzQyB8IDIgXHU2QTJBXHU3RUJGIHwgMyBcdTdBRDZcdTdFQkYgfCA0IFx1NURFNlx1NjU5Q1x1N0VCRiB8IDUgXHU1M0YzXHU2NTlDXHU3RUJGIHwgNiBcdTcwQjlcdTcyQjZcclxuXHJcbiAgICAvLyB1cGRhdGVCYXI6IHsgLy8gXHU2NzAwXHU4RkQxXHU2NkY0XHU2NUIwXHU2ODBGXHJcbiAgICAvLyAgIHNob3dUb0FydGljbGU6IHRydWUsIC8vIFx1NjYzRVx1NzkzQVx1NTIzMFx1NjU4N1x1N0FFMFx1OTg3NVx1NUU5NVx1OTBFOFx1RkYwQ1x1OUVEOFx1OEJBNHRydWVcclxuICAgIC8vICAgbW9yZUFydGljbGU6ICcvYXJjaGl2ZXMnIC8vIFx1MjAxQ1x1NjZGNFx1NTkxQVx1NjU4N1x1N0FFMFx1MjAxRFx1OERGM1x1OEY2Q1x1NzY4NFx1OTg3NVx1OTc2Mlx1RkYwQ1x1OUVEOFx1OEJBNCcvYXJjaGl2ZXMnXHJcbiAgICAvLyB9LFxyXG4gICAgLy8gcmlnaHRNZW51QmFyOiBmYWxzZSwgLy8gXHU2NjJGXHU1NDI2XHU2NjNFXHU3OTNBXHU1M0YzXHU0RkE3XHU2NTg3XHU3QUUwXHU1OTI3XHU3RUIyXHU2ODBGXHVGRjBDXHU5RUQ4XHU4QkE0dHJ1ZSAoXHU1QzRGXHU1QkJEXHU1QzBGXHU0RThFMTMwMHB4XHU0RTBCXHU2NUUwXHU4QkJBXHU1OTgyXHU0RjU1XHU5MEZEXHU0RTBEXHU2NjNFXHU3OTNBKVxyXG4gICAgLy8gc2lkZWJhck9wZW46IGZhbHNlLCAvLyBcdTUyMURcdTU5Q0JcdTcyQjZcdTYwMDFcdTY2MkZcdTU0MjZcdTYyNTNcdTVGMDBcdTVERTZcdTRGQTdcdThGQjlcdTY4MEZcdUZGMENcdTlFRDhcdThCQTR0cnVlXHJcbiAgICAvLyBwYWdlQnV0dG9uOiBmYWxzZSwgLy8gXHU2NjJGXHU1NDI2XHU2NjNFXHU3OTNBXHU1RkVCXHU2Mzc3XHU3RkZCXHU5ODc1XHU2MzA5XHU5NEFFXHVGRjBDXHU5RUQ4XHU4QkE0dHJ1ZVxyXG5cclxuICAgIC8vIFx1OUVEOFx1OEJBNFx1NTkxNlx1ODlDMlx1NkEyMVx1NUYwRlx1RkYwOFx1NzUyOFx1NjIzN1x1NjcyQVx1NTcyOFx1OTg3NVx1OTc2Mlx1NjI0Qlx1NTJBOFx1NEZFRVx1NjUzOVx1OEZDN1x1NkEyMVx1NUYwRlx1NjVGNlx1NjI0RFx1NzUxRlx1NjU0OFx1RkYwQ1x1NTQyNlx1NTIxOVx1NEVFNVx1NzUyOFx1NjIzN1x1OEJCRVx1N0Y2RVx1NzY4NFx1NkEyMVx1NUYwRlx1NEUzQVx1NTFDNlx1RkYwOVx1RkYwQ1x1NTNFRlx1OTAwOVx1RkYxQSdhdXRvJyB8ICdsaWdodCcgfCAnZGFyaycgfCAncmVhZCdcdUZGMENcdTlFRDhcdThCQTQnYXV0bydcdTMwMDJcclxuICAgIC8vIGRlZmF1bHRNb2RlOiAnYXV0bycsXHJcblxyXG4gICAgLy8gXHU0RkE3XHU4RkI5XHU2ODBGICAnc3RydWN0dXJpbmcnIHwgeyBtb2RlOiAnc3RydWN0dXJpbmcnLCBjb2xsYXBzYWJsZTogQm9vbGVhbn0gfCAnYXV0bycgfCA8XHU4MUVBXHU1QjlBXHU0RTQ5PiAgICBcdTZFMjlcdTk5QThcdTYzRDBcdTc5M0FcdUZGMUFcdTc2RUVcdTVGNTVcdTk4NzVcdTY1NzBcdTYzNkVcdTRGOURcdThENTZcdTRFOEVcdTdFRDNcdTY3ODRcdTUzMTZcdTc2ODRcdTRGQTdcdThGQjlcdTY4MEZcdTY1NzBcdTYzNkVcdUZGMENcdTU5ODJcdTY3OUNcdTRGNjBcdTRFMERcdThCQkVcdTdGNkVcdTRFM0Enc3RydWN0dXJpbmcnLFx1NUMwNlx1NjVFMFx1NkNENVx1NEY3Rlx1NzUyOFx1NzZFRVx1NUY1NVx1OTg3NVxyXG4gICAgc2lkZWJhcjogJ3N0cnVjdHVyaW5nJyxcclxuXHJcbiAgICAvLyBcdTY1ODdcdTdBRTBcdTlFRDhcdThCQTRcdTc2ODRcdTRGNUNcdTgwMDVcdTRGRTFcdTYwNkZcdUZGMEMoXHU1M0VGXHU1NzI4bWRcdTY1ODdcdTRFRjZcdTRFMkRcdTUzNTVcdTcyRUNcdTkxNERcdTdGNkVcdTZCNjRcdTRGRTFcdTYwNkYpIHN0cmluZyB8IHtuYW1lOiBzdHJpbmcsIGxpbms/OiBzdHJpbmd9XHJcbiAgICBhdXRob3I6IHtcclxuICAgICAgbmFtZTogJ3NvZnQxMzE0JywgLy8gXHU1RkM1XHU5NzAwXHJcbiAgICAgIGxpbms6ICdodHRwczovL3NvZnQxMzE0LmdpdGh1Yi5pby8nLCAvLyBcdTUzRUZcdTkwMDlcdTc2ODRcclxuICAgIH0sXHJcblxyXG4gICAgLy8gXHU1MzVBXHU0RTNCXHU0RkUxXHU2MDZGIChcdTY2M0VcdTc5M0FcdTU3MjhcdTk5OTZcdTk4NzVcdTRGQTdcdThGQjlcdTY4MEYpXHJcbiAgICBibG9nZ2VyOiB7XHJcbiAgICAgIGF2YXRhcjogJy9pbWcvbG9nby5wbmcnLFxyXG4gICAgICBuYW1lOiAnc29mdDEzMTQnLFxyXG4gICAgICBzbG9nYW46ICcnLFxyXG4gICAgfSxcclxuXHJcbiAgICAvLyBcdTc5M0VcdTRFQTRcdTU2RkVcdTY4MDcgKFx1NjYzRVx1NzkzQVx1NEU4RVx1NTM1QVx1NEUzQlx1NEZFMVx1NjA2Rlx1NjgwRlx1NTQ4Q1x1OTg3NVx1ODExQVx1NjgwRlx1MzAwMlx1NTE4NVx1N0Y2RVx1NTZGRVx1NjgwN1x1RkYxQWh0dHBzOi8vZG9jLnh1Z2FveWkuY29tL3BhZ2VzL2EyMGNlOC8jc29jaWFsKVxyXG4gICAgc29jaWFsOiB7XHJcbiAgICAgIC8vIGljb25mb250Q3NzRmlsZTogJy8vYXQuYWxpY2RuLmNvbS90L3h4eC5jc3MnLCAvLyBcdTUzRUZcdTkwMDlcdUZGMENcdTk2M0ZcdTkxQ0NcdTU2RkVcdTY4MDdcdTVFOTNcdTU3MjhcdTdFQkZjc3NcdTY1ODdcdTRFRjZcdTU3MzBcdTU3NDBcdUZGMENcdTVCRjlcdTRFOEVcdTRFM0JcdTk4OThcdTZDQTFcdTY3MDlcdTc2ODRcdTU2RkVcdTY4MDdcdTUzRUZcdTgxRUFcdTVERjFcdTZERkJcdTUyQTBcdTMwMDJcdTk2M0ZcdTkxQ0NcdTU2RkVcdTcyNDdcdTVFOTNcdUZGMUFodHRwczovL3d3dy5pY29uZm9udC5jbi9cclxuICAgICAgaWNvbnM6IFtcclxuICAgICAgICB7XHJcbiAgICAgICAgICBpY29uQ2xhc3M6ICdpY29uLXlvdWppYW4nLFxyXG4gICAgICAgICAgdGl0bGU6ICdcdTUzRDFcdTkwQUVcdTRFRjYnLFxyXG4gICAgICAgICAgbGluazogJ21haWx0bzozNzUxNDM2N0BxcS5jb20nLFxyXG4gICAgICAgIH0sXHJcbiAgICAgICAge1xyXG4gICAgICAgICAgaWNvbkNsYXNzOiAnaWNvbi1naXRodWInLFxyXG4gICAgICAgICAgdGl0bGU6ICdHaXRIdWInLFxyXG4gICAgICAgICAgbGluazogJ2h0dHBzOi8vZ2l0aHViLmNvbS9zb2Z0MTMxNCcsXHJcbiAgICAgICAgfSxcclxuICAgICAgICB7XHJcbiAgICAgICAgICBpY29uQ2xhc3M6ICdpY29uLWVyamknLFxyXG4gICAgICAgICAgdGl0bGU6ICdcdTU0MkNcdTk3RjNcdTRFNTAnLFxyXG4gICAgICAgICAgbGluazogJ2h0dHBzOi8vbXVzaWMuMTYzLmNvbScsXHJcbiAgICAgICAgfSxcclxuICAgICAgXSxcclxuICAgIH0sXHJcblxyXG4gICAgLy8gXHU5ODc1XHU4MTFBXHU0RkUxXHU2MDZGXHJcbiAgICBmb290ZXI6IHtcclxuICAgICAgY3JlYXRlWWVhcjogMjAyMywgLy8gXHU1MzVBXHU1QkEyXHU1MjFCXHU1RUZBXHU1RTc0XHU0RUZEXHJcbiAgICAgIGNvcHlyaWdodEluZm86XHJcbiAgICAgICAgJ1NvZnQxMzE0IHwgPGEgaHJlZj1cImh0dHBzOi8vZ2l0aHViLmNvbS9zb2Z0MTMxNC9ib290LWFkbWluL1wiIHRhcmdldD1cIl9ibGFua1wiPk1JVCBMaWNlbnNlPC9hPicsIC8vIFx1NTM1QVx1NUJBMlx1NzI0OFx1Njc0M1x1NEZFMVx1NjA2Rlx1MzAwMVx1NTkwN1x1Njg0OFx1NEZFMVx1NjA2Rlx1N0I0OVx1RkYwQ1x1NjUyRlx1NjMwMWFcdTY4MDdcdTdCN0VcdTYyMTZcdTYzNjJcdTg4NENcdTY4MDdcdTdCN0U8L2JyPlxyXG4gICAgfSxcclxuXHJcbiAgICAvLyBcdTYyNjlcdTVDNTVcdTgxRUFcdTUyQThcdTc1MUZcdTYyMTBmcm9udG1hdHRlclx1MzAwMlx1RkYwOFx1NUY1M21kXHU2NTg3XHU0RUY2XHU3Njg0ZnJvbnRtYXR0ZXJcdTRFMERcdTVCNThcdTU3MjhcdTc2RjhcdTVFOTRcdTc2ODRcdTVCNTdcdTZCQjVcdTY1RjZcdTVDMDZcdTgxRUFcdTUyQThcdTZERkJcdTUyQTBcdTMwMDJcdTRFMERcdTRGMUFcdTg5ODZcdTc2RDZcdTVERjJcdTY3MDlcdTc2ODRcdTY1NzBcdTYzNkVcdTMwMDJcdUZGMDlcclxuICAgIGV4dGVuZEZyb250bWF0dGVyOiB7XHJcbiAgICAgIGF1dGhvcjoge1xyXG4gICAgICAgIG5hbWU6ICdTb2Z0MTMxNCcsXHJcbiAgICAgICAgbGluazogJ2h0dHBzOi8vZ2l0aHViLmNvbS9zb2Z0MTMxNCdcclxuICAgICAgfVxyXG4gICAgfSxcclxuXHJcbiAgICAvLyBcdTgxRUFcdTVCOUFcdTRFNDlobXRsKFx1NUU3Rlx1NTQ0QSlcdTZBMjFcdTU3NTdcclxuICAgIGh0bWxNb2R1bGVzXHJcbiAgfSxcclxuXHJcbiAgLy8gXHU2Q0U4XHU1MTY1XHU1MjMwXHU5ODc1XHU5NzYyPGhlYWQ+XHU0RTJEXHU3Njg0XHU2ODA3XHU3QjdFXHVGRjBDXHU2ODNDXHU1RjBGW3RhZ05hbWUsIHsgYXR0ck5hbWU6IGF0dHJWYWx1ZSB9LCBpbm5lckhUTUw/XVxyXG4gIGhlYWQ6IFtcclxuICAgIFsnbGluaycsIHsgcmVsOiAnaWNvbicsIGhyZWY6ICcvaW1nL2Zhdmljb24uaWNvJyB9XSwgLy9mYXZpY29uc1x1RkYwQ1x1OEQ0NFx1NkU5MFx1NjUzRVx1NTcyOHB1YmxpY1x1NjU4N1x1NEVGNlx1NTkzOVxyXG4gICAgW1xyXG4gICAgICAnbWV0YScsXHJcbiAgICAgIHtcclxuICAgICAgICBuYW1lOiAna2V5d29yZHMnLFxyXG4gICAgICAgIGNvbnRlbnQ6ICdcdTUyNERcdTdBRUZcdTUzNUFcdTVCQTIsXHU0RTJBXHU0RUJBXHU2MjgwXHU2NzJGXHU1MzVBXHU1QkEyLFx1NTI0RFx1N0FFRixcdTUyNERcdTdBRUZcdTVGMDBcdTUzRDEsXHU1MjREXHU3QUVGXHU2ODQ2XHU2N0I2LHdlYlx1NTI0RFx1N0FFRixcdTUyNERcdTdBRUZcdTk3NjJcdThCRDVcdTk4OTgsXHU2MjgwXHU2NzJGXHU2NTg3XHU2ODYzLFx1NUI2Nlx1NEU2MCxcdTk3NjJcdThCRDUsSmF2YVNjcmlwdCxqcyxFUzYsVHlwZVNjcmlwdCx2dWUscHl0aG9uLGNzczMsaHRtbDUsTm9kZSxnaXQsZ2l0aHViLG1hcmtkb3duJyxcclxuICAgICAgfSxcclxuICAgIF0sXHJcbiAgICBbJ21ldGEnLCB7IG5hbWU6ICdiYWlkdS1zaXRlLXZlcmlmaWNhdGlvbicsIGNvbnRlbnQ6ICc3RjU1d2VaRERjJyB9XSwgLy8gXHU3NjdFXHU1RUE2XHU3RURGXHU4QkExXHU3Njg0XHU3QUQ5XHU5NTdGXHU5QThDXHU4QkMxXHVGRjA4XHU0RjYwXHU1M0VGXHU0RUU1XHU1M0JCXHU2Mzg5XHVGRjA5XHJcbiAgICBbJ21ldGEnLCB7IG5hbWU6ICd0aGVtZS1jb2xvcicsIGNvbnRlbnQ6ICcjMTFhOGNkJyB9XSwgLy8gXHU3OUZCXHU1MkE4XHU2RDRGXHU4OUM4XHU1NjY4XHU0RTNCXHU5ODk4XHU5ODlDXHU4MjcyXHJcbiAgICAvLyBbXHJcbiAgICAvLyAgICdzY3JpcHQnLFxyXG4gICAgLy8gICB7XHJcbiAgICAvLyAgICAgJ2RhdGEtYWQtY2xpZW50JzogJ2NhLXB1Yi03ODI4MzMzNzI1OTkzNTU0JyxcclxuICAgIC8vICAgICBhc3luYzogJ2FzeW5jJyxcclxuICAgIC8vICAgICBzcmM6ICdodHRwczovL3BhZ2VhZDIuZ29vZ2xlc3luZGljYXRpb24uY29tL3BhZ2VhZC9qcy9hZHNieWdvb2dsZS5qcycsXHJcbiAgICAvLyAgIH0sXHJcbiAgICAvLyBdLCAvLyBcdTdGNTFcdTdBRDlcdTUxNzNcdTgwNTRHb29nbGUgQWRTZW5zZSBcdTRFMEUgaHRtbFx1NjgzQ1x1NUYwRlx1NUU3Rlx1NTQ0QVx1NjUyRlx1NjMwMVx1RkYwOFx1NEY2MFx1NTNFRlx1NEVFNVx1NTNCQlx1NjM4OVx1RkYwOVxyXG4gIF0sXHJcblxyXG5cclxuICAvLyBcdTYzRDJcdTRFRjZcdTkxNERcdTdGNkVcclxuICBwbHVnaW5zOiA8VXNlclBsdWdpbnM+W1xyXG4gICAgW1xyXG4gICAgICBcInNpdGVtYXBcIiwgLy8gXHU3RjUxXHU3QUQ5XHU1NzMwXHU1NkZFXHJcbiAgICAgIHtcclxuICAgICAgICBob3N0bmFtZTogV0VCX1NJVEUsXHJcbiAgICAgIH0sXHJcbiAgICBdLFxyXG5cclxuICAgICd2dWVwcmVzcy1wbHVnaW4tYmFpZHUtYXV0b3B1c2gnLCAvLyBcdTc2N0VcdTVFQTZcdTgxRUFcdTUyQThcdTYzQThcdTkwMDFcclxuXHJcbiAgICBbXHJcbiAgICAgICd2dWVwcmVzcy1wbHVnaW4tYmFpZHUtdG9uZ2ppJywgLy8gXHU3NjdFXHU1RUE2XHU3RURGXHU4QkExXHJcbiAgICAgIHtcclxuICAgICAgICBobTogYmFpZHVDb2RlLFxyXG4gICAgICB9LFxyXG4gICAgXSxcclxuXHJcbiAgICAvLyBcdTUxNjhcdTY1ODdcdTY0MUNcdTdEMjJcdTMwMDIgXHUyNkEwXHVGRTBGXHU2Q0U4XHU2MTBGXHVGRjFBXHU2QjY0XHU2M0QyXHU0RUY2XHU0RjFBXHU1NzI4XHU2MjUzXHU1RjAwXHU3RjUxXHU3QUQ5XHU2NUY2XHU1OTFBXHU1MkEwXHU4RjdEXHU5MEU4XHU1MjA2anNcdTY1ODdcdTRFRjZcdTc1MjhcdTRFOEVcdTY0MUNcdTdEMjJcdUZGMENcdTVCRkNcdTgxRjRcdTUyMURcdTZCMjFcdThCQkZcdTk1RUVcdTdGNTFcdTdBRDlcdTUzRDhcdTYxNjJcdTMwMDJcdTU5ODJcdTU3MjhcdTYxMEZcdTUyMURcdTZCMjFcdThCQkZcdTk1RUVcdTkwMUZcdTVFQTZcdTc2ODRcdThCRERcdTUzRUZcdTRFRTVcdTRFMERcdTRGN0ZcdTc1MjhcdTZCNjRcdTYzRDJcdTRFRjZcdUZGMDFcdUZGMDhcdTYzQThcdTgzNTBcdUZGMUF2dWVwcmVzcy1wbHVnaW4tdGhpcmRwYXJ0eS1zZWFyY2hcdUZGMDlcclxuICAgIC8vICdmdWxsdGV4dC1zZWFyY2gnLFxyXG5cclxuICAgIC8vIFx1NTNFRlx1NEVFNVx1NkRGQlx1NTJBMFx1N0IyQ1x1NEUwOVx1NjVCOVx1NjQxQ1x1N0QyMlx1OTRGRVx1NjNBNVx1NzY4NFx1NjQxQ1x1N0QyMlx1Njg0Nlx1RkYwOFx1N0VFN1x1NjI3Rlx1NTM5Rlx1NUI5OFx1NjVCOVx1NjQxQ1x1N0QyMlx1Njg0Nlx1NzY4NFx1OTE0RFx1N0Y2RVx1NTNDMlx1NjU3MFx1RkYwOVxyXG4gICAgW1xyXG4gICAgICAndGhpcmRwYXJ0eS1zZWFyY2gnLFxyXG4gICAgICB7XHJcbiAgICAgICAgdGhpcmRwYXJ0eTogW1xyXG4gICAgICAgICAge1xyXG4gICAgICAgICAgICB0aXRsZTogJ1x1NTcyOE1ETlx1NEUyRFx1NjQxQ1x1N0QyMicsXHJcbiAgICAgICAgICAgIGZyb250VXJsOiAnaHR0cHM6Ly9kZXZlbG9wZXIubW96aWxsYS5vcmcvemgtQ04vc2VhcmNoP3E9JywgLy8gXHU2NDFDXHU3RDIyXHU5NEZFXHU2M0E1XHU3Njg0XHU1MjREXHU5NzYyXHU5MEU4XHU1MjA2XHJcbiAgICAgICAgICAgIGJlaGluZFVybDogJycsIC8vIFx1NjQxQ1x1N0QyMlx1OTRGRVx1NjNBNVx1NzY4NFx1NTQwRVx1OTc2Mlx1OTBFOFx1NTIwNlx1RkYwQ1x1NTNFRlx1OTAwOVx1RkYwQ1x1OUVEOFx1OEJBNCAnJ1xyXG4gICAgICAgICAgfSxcclxuICAgICAgICAgIHtcclxuICAgICAgICAgICAgdGl0bGU6ICdcdTU3MjhSdW5vb2JcdTRFMkRcdTY0MUNcdTdEMjInLFxyXG4gICAgICAgICAgICBmcm9udFVybDogJ2h0dHBzOi8vd3d3LnJ1bm9vYi5jb20vP3M9JyxcclxuICAgICAgICAgIH0sXHJcbiAgICAgICAgICB7XHJcbiAgICAgICAgICAgIHRpdGxlOiAnXHU1NzI4VnVlIEFQSVx1NEUyRFx1NjQxQ1x1N0QyMicsXHJcbiAgICAgICAgICAgIGZyb250VXJsOiAnaHR0cHM6Ly9jbi52dWVqcy5vcmcvdjIvYXBpLyMnLFxyXG4gICAgICAgICAgfSxcclxuICAgICAgICAgIHtcclxuICAgICAgICAgICAgdGl0bGU6ICdcdTU3MjhCaW5nXHU0RTJEXHU2NDFDXHU3RDIyJyxcclxuICAgICAgICAgICAgZnJvbnRVcmw6ICdodHRwczovL2NuLmJpbmcuY29tL3NlYXJjaD9xPScsXHJcbiAgICAgICAgICB9LFxyXG4gICAgICAgICAge1xyXG4gICAgICAgICAgICB0aXRsZTogJ1x1OTAxQVx1OEZDN1x1NzY3RVx1NUVBNlx1NjQxQ1x1N0QyMlx1NjcyQ1x1N0FEOVx1NzY4NCcsXHJcbiAgICAgICAgICAgIGZyb250VXJsOiBgaHR0cHM6Ly93d3cuYmFpZHUuY29tL3M/d2Q9c2l0ZSUzQSR7RE9NQUlOX05BTUV9JTIwYCxcclxuICAgICAgICAgIH0sXHJcbiAgICAgICAgXSxcclxuICAgICAgfVxyXG4gICAgXSxcclxuXHJcbiAgICBbXHJcbiAgICAgICdvbmUtY2xpY2stY29weScsIC8vIFx1NEVFM1x1NzgwMVx1NTc1N1x1NTkwRFx1NTIzNlx1NjMwOVx1OTRBRVxyXG4gICAgICB7XHJcbiAgICAgICAgY29weVNlbGVjdG9yOiBbJ2RpdltjbGFzcyo9XCJsYW5ndWFnZS1cIl0gcHJlJywgJ2RpdltjbGFzcyo9XCJhc2lkZS1jb2RlXCJdIGFzaWRlJ10sIC8vIFN0cmluZyBvciBBcnJheVxyXG4gICAgICAgIGNvcHlNZXNzYWdlOiAnXHU1OTBEXHU1MjM2XHU2MjEwXHU1MjlGJywgLy8gZGVmYXVsdCBpcyAnQ29weSBzdWNjZXNzZnVsbHkgYW5kIHRoZW4gcGFzdGUgaXQgZm9yIHVzZS4nXHJcbiAgICAgICAgZHVyYXRpb246IDEwMDAsIC8vIHByb21wdCBtZXNzYWdlIGRpc3BsYXkgdGltZS5cclxuICAgICAgICBzaG93SW5Nb2JpbGU6IGZhbHNlLCAvLyB3aGV0aGVyIHRvIGRpc3BsYXkgb24gdGhlIG1vYmlsZSBzaWRlLCBkZWZhdWx0OiBmYWxzZS5cclxuICAgICAgfSxcclxuICAgIF0sXHJcblxyXG4gICAgW1xyXG4gICAgICAnZGVtby1ibG9jaycsIC8vIGRlbW9cdTZGMTRcdTc5M0FcdTZBMjFcdTU3NTcgaHR0cHM6Ly9naXRodWIuY29tL3hpZ3VheGlndWEvdnVlcHJlc3MtcGx1Z2luLWRlbW8tYmxvY2tcclxuICAgICAge1xyXG4gICAgICAgIHNldHRpbmdzOiB7XHJcbiAgICAgICAgICAvLyBqc0xpYjogWydodHRwOi8veHh4J10sIC8vIFx1NTcyOFx1N0VCRlx1NzkzQVx1NEY4Qihqc2ZpZGRsZSwgY29kZXBlbilcdTRFMkRcdTc2ODRqc1x1NEY5RFx1OEQ1NlxyXG4gICAgICAgICAgLy8gY3NzTGliOiBbJ2h0dHA6Ly94eHgnXSwgLy8gXHU1NzI4XHU3RUJGXHU3OTNBXHU0RjhCXHU0RTJEXHU3Njg0Y3NzXHU0RjlEXHU4RDU2XHJcbiAgICAgICAgICAvLyB2dWU6ICdodHRwczovL2Zhc3RseS5qc2RlbGl2ci5uZXQvbnBtL3Z1ZS9kaXN0L3Z1ZS5taW4uanMnLCAvLyBcdTU3MjhcdTdFQkZcdTc5M0FcdTRGOEJcdTRFMkRcdTc2ODR2dWVcdTRGOURcdThENTZcclxuICAgICAgICAgIGpzZmlkZGxlOiBmYWxzZSwgLy8gXHU2NjJGXHU1NDI2XHU2NjNFXHU3OTNBIGpzZmlkZGxlIFx1OTRGRVx1NjNBNVxyXG4gICAgICAgICAgY29kZXBlbjogdHJ1ZSwgLy8gXHU2NjJGXHU1NDI2XHU2NjNFXHU3OTNBIGNvZGVwZW4gXHU5NEZFXHU2M0E1XHJcbiAgICAgICAgICBob3Jpem9udGFsOiBmYWxzZSwgLy8gXHU2NjJGXHU1NDI2XHU1QzU1XHU3OTNBXHU0RTNBXHU2QTJBXHU1NDExXHU2ODM3XHU1RjBGXHJcbiAgICAgICAgfSxcclxuICAgICAgfSxcclxuICAgIF0sXHJcbiAgICBbXHJcbiAgICAgICd2dWVwcmVzcy1wbHVnaW4tem9vbWluZycsIC8vIFx1NjUzRVx1NTkyN1x1NTZGRVx1NzI0N1xyXG4gICAgICB7XHJcbiAgICAgICAgc2VsZWN0b3I6ICcudGhlbWUtdmRvaW5nLWNvbnRlbnQgaW1nOm5vdCgubm8tem9vbSknLCAvLyBcdTYzOTJcdTk2NjRjbGFzc1x1NjYyRm5vLXpvb21cdTc2ODRcdTU2RkVcdTcyNDdcclxuICAgICAgICBvcHRpb25zOiB7XHJcbiAgICAgICAgICBiZ0NvbG9yOiAncmdiYSgwLDAsMCwwLjYpJyxcclxuICAgICAgICB9LFxyXG4gICAgICB9LFxyXG4gICAgXSxcclxuICAgIFtcclxuICAgICAgJ3Z1ZXByZXNzLXBsdWdpbi1jb21tZW50JywgLy8gXHU4QkM0XHU4QkJBXHJcbiAgICAgIHtcclxuICAgICAgICBjaG9vc2VuOiAnZ2l0YWxrJyxcclxuICAgICAgICBvcHRpb25zOiB7XHJcbiAgICAgICAgICBjbGllbnRJRDogJ2E2ZTEzNTUyODc5NDcwOTZiODhiJyxcclxuICAgICAgICAgIGNsaWVudFNlY3JldDogJ2YwZTc3ZDA3MGZhYmZjZDVhZjk1YmViYjgyYjJkNTc0ZDcyNDhkNzEnLFxyXG4gICAgICAgICAgcmVwbzogJ2Jsb2ctZ2l0YWxrLWNvbW1lbnQnLCAvLyBHaXRIdWIgXHU0RUQzXHU1RTkzXHJcbiAgICAgICAgICBvd25lcjogJ3h1Z2FveWknLCAvLyBHaXRIdWJcdTRFRDNcdTVFOTNcdTYyNDBcdTY3MDlcdTgwMDVcclxuICAgICAgICAgIGFkbWluOiBbJ3h1Z2FveWknXSwgLy8gXHU1QkY5XHU0RUQzXHU1RTkzXHU2NzA5XHU1MTk5XHU2NzQzXHU5NjUwXHU3Njg0XHU0RUJBXHJcbiAgICAgICAgICAvLyBkaXN0cmFjdGlvbkZyZWVNb2RlOiB0cnVlLFxyXG4gICAgICAgICAgcGFnZXJEaXJlY3Rpb246ICdsYXN0JywgLy8gJ2ZpcnN0J1x1NkI2M1x1NUU4RiB8ICdsYXN0J1x1NTAxMlx1NUU4RlxyXG4gICAgICAgICAgaWQ6ICc8JS0gKGZyb250bWF0dGVyLnBlcm1hbGluayB8fCBmcm9udG1hdHRlci50by5wYXRoKS5zbGljZSgtMTYpICU+JywgLy8gIFx1OTg3NVx1OTc2Mlx1NzY4NFx1NTUyRlx1NEUwMFx1NjgwN1x1OEJDNixcdTk1N0ZcdTVFQTZcdTRFMERcdTgwRkRcdThEODVcdThGQzc1MFxyXG4gICAgICAgICAgdGl0bGU6ICdcdTMwMENcdThCQzRcdThCQkFcdTMwMEQ8JS0gZnJvbnRtYXR0ZXIudGl0bGUgJT4nLCAvLyBHaXRIdWIgaXNzdWUgXHU3Njg0XHU2ODA3XHU5ODk4XHJcbiAgICAgICAgICBsYWJlbHM6IFsnR2l0YWxrJywgJ0NvbW1lbnQnXSwgLy8gR2l0SHViIGlzc3VlIFx1NzY4NFx1NjgwN1x1N0I3RVxyXG4gICAgICAgICAgYm9keTpcclxuICAgICAgICAgICAgJ1x1OTg3NVx1OTc2Mlx1RkYxQTwlLSB3aW5kb3cubG9jYXRpb24ub3JpZ2luICsgKGZyb250bWF0dGVyLnRvLnBhdGggfHwgd2luZG93LmxvY2F0aW9uLnBhdGhuYW1lKSAlPicsIC8vIEdpdEh1YiBpc3N1ZSBcdTc2ODRcdTUxODVcdTVCQjlcclxuICAgICAgICB9LFxyXG4gICAgICB9LFxyXG4gICAgXSxcclxuICAgIFtcclxuICAgICAgJ0B2dWVwcmVzcy9sYXN0LXVwZGF0ZWQnLCAvLyBcIlx1NEUwQVx1NkIyMVx1NjZGNFx1NjVCMFwiXHU2NUY2XHU5NUY0XHU2ODNDXHU1RjBGXHJcbiAgICAgIHtcclxuICAgICAgICB0cmFuc2Zvcm1lcjogKHRpbWVzdGFtcCwgbGFuZykgPT4ge1xyXG4gICAgICAgICAgcmV0dXJuIGRheWpzKHRpbWVzdGFtcCkuZm9ybWF0KCdZWVlZL01NL0RELCBISDptbTpzcycpXHJcbiAgICAgICAgfSxcclxuICAgICAgfSxcclxuICAgIF0sXHJcbiAgXSxcclxuXHJcbiAgbWFya2Rvd246IHtcclxuICAgIGxpbmVOdW1iZXJzOiB0cnVlLFxyXG4gICAgZXh0cmFjdEhlYWRlcnM6IFsnaDInLCAnaDMnLCAnaDQnLCAnaDUnLCAnaDYnXSwgLy8gXHU2M0QwXHU1M0Q2XHU2ODA3XHU5ODk4XHU1MjMwXHU0RkE3XHU4RkI5XHU2ODBGXHU3Njg0XHU3RUE3XHU1MjJCXHVGRjBDXHU5RUQ4XHU4QkE0WydoMicsICdoMyddXHJcbiAgfSxcclxuXHJcbiAgLy8gXHU3NkQxXHU1NDJDXHU2NTg3XHU0RUY2XHU1M0Q4XHU1MzE2XHU1RTc2XHU5MUNEXHU2NUIwXHU2Nzg0XHU1RUZBXHJcbiAgZXh0cmFXYXRjaEZpbGVzOiBbXHJcbiAgICAnLnZ1ZXByZXNzL2NvbmZpZy50cycsXHJcbiAgICAnLnZ1ZXByZXNzL2NvbmZpZy9odG1sTW9kdWxlcy50cycsXHJcbiAgXVxyXG59KVxyXG4iLCAiZXhwb3J0IGRlZmF1bHQgJzUwM2YwOThlN2U1YjNhNWI1ZDhjNWZjMjkzOGFmMDAyJ1xyXG4iLCAiaW1wb3J0IHsgVmRvaW5nVGhlbWVDb25maWcgfSBmcm9tICd2dWVwcmVzcy10aGVtZS12ZG9pbmcvdHlwZXMnXHJcblxyXG4vKiogXHU2M0QyXHU1MTY1XHU4MUVBXHU1QjlBXHU0RTQ5aHRtbFx1NkEyMVx1NTc1NyAoXHU1M0VGXHU3NTI4XHU0RThFXHU2M0QyXHU1MTY1XHU1RTdGXHU1NDRBXHU2QTIxXHU1NzU3XHU3QjQ5KVxyXG4gKiB7XHJcbiAqICAgaG9tZVNpZGViYXJCOiBodG1sU3RyaW5nLCBcdTk5OTZcdTk4NzVcdTRGQTdcdThGQjlcdTY4MEZcdTVFOTVcdTkwRThcclxuICpcclxuICogICBzaWRlYmFyVDogaHRtbFN0cmluZywgXHU2MjQwXHU2NzA5XHU1REU2XHU0RkE3XHU4RkI5XHU2ODBGXHU5ODc2XHU5MEU4XHJcbiAqICAgc2lkZWJhckI6IGh0bWxTdHJpbmcsIFx1NjI0MFx1NjcwOVx1NURFNlx1NEZBN1x1OEZCOVx1NjgwRlx1NUU5NVx1OTBFOFxyXG4gKlxyXG4gKiAgIHBhZ2VUOiBodG1sU3RyaW5nLCBcdTk4NzVcdTk3NjJcdTk4NzZcdTkwRThcclxuICogICBwYWdlQjogaHRtbFN0cmluZywgXHU5ODc1XHU5NzYyXHU1RTk1XHU5MEU4XHJcbiAqICAgcGFnZVRzaG93TW9kZTogc3RyaW5nLCBcdTk4NzVcdTk3NjJcdTk4NzZcdTkwRTgtXHU2NjNFXHU3OTNBXHU2NUI5XHU1RjBGXHVGRjFBXHU2NzJBXHU5MTREXHU3RjZFXHU5RUQ4XHU4QkE0XHU2MjQwXHU2NzA5XHU5ODc1XHU5NzYyXHVGRjFCJ2FydGljbGUnID0+IFx1NEVDNVx1NjU4N1x1N0FFMFx1OTg3NVx1MjQ2MFx1RkYxQiAnY3VzdG9tJyA9PiBcdTRFQzVcdTgxRUFcdTVCOUFcdTRFNDlcdTk4NzVcdTI0NjBcclxuICogICBwYWdlQnNob3dNb2RlOiBzdHJpbmcsIFx1OTg3NVx1OTc2Mlx1NUU5NVx1OTBFOC1cdTY2M0VcdTc5M0FcdTY1QjlcdTVGMEZcdUZGMUFcdTY3MkFcdTkxNERcdTdGNkVcdTlFRDhcdThCQTRcdTYyNDBcdTY3MDlcdTk4NzVcdTk3NjJcdUZGMUInYXJ0aWNsZScgPT4gXHU0RUM1XHU2NTg3XHU3QUUwXHU5ODc1XHUyNDYwXHVGRjFCICdjdXN0b20nID0+IFx1NEVDNVx1ODFFQVx1NUI5QVx1NEU0OVx1OTg3NVx1MjQ2MFxyXG4gKlxyXG4gKiAgIHdpbmRvd0xCOiBodG1sU3RyaW5nLCBcdTUxNjhcdTVDNDBcdTdBOTdcdTUzRTNcdTVERTZcdTRFMEJcdTg5RDJcdTI0NjFcclxuICogICB3aW5kb3dSQjogaHRtbFN0cmluZywgXHU1MTY4XHU1QzQwXHU3QTk3XHU1M0UzXHU1M0YzXHU0RTBCXHU4OUQyXHUyNDYxXHJcbiAqIH1cclxuICpcclxuICogXHUyNDYwXHU2Q0U4XHVGRjFBXHU1NzI4Lm1kXHU2NTg3XHU0RUY2ZnJvbnQgbWF0dGVyXHU5MTREXHU3RjZFYGFydGljbGU6IGZhbHNlYFx1NzY4NFx1OTg3NVx1OTc2Mlx1NjYyRlx1ODFFQVx1NUI5QVx1NEU0OVx1OTg3NVx1RkYwQ1x1NjcyQVx1OTE0RFx1N0Y2RVx1NzY4NFx1OUVEOFx1OEJBNFx1NjYyRlx1NjU4N1x1N0FFMFx1OTg3NVx1RkYwOFx1OTk5Nlx1OTg3NVx1OTY2NFx1NTkxNlx1RkYwOVx1MzAwMlxyXG4gKiBcdTI0NjFcdTZDRThcdUZGMUF3aW5kb3dMQiBcdTU0OEMgd2luZG93UkJcdUZGMUExLlx1NUM1NVx1NzkzQVx1NTMzQVx1NTc1N1x1NjcwMFx1NTkyN1x1NUJCRFx1OUFEODIwMHB4KjQwMHB4XHUzMDAyMi5cdThCRjdcdTdFRDlcdTgxRUFcdTVCOUFcdTRFNDlcdTUxNDNcdTdEMjBcdTVCOUFcdTRFMDBcdTRFMkFcdTRFMERcdThEODVcdThGQzcyMDBweCo0MDBweFx1NzY4NFx1NUJCRFx1OUFEOFx1MzAwMjMuXHU1NzI4XHU1QzRGXHU1RTU1XHU1QkJEXHU1RUE2XHU1QzBGXHU0RThFOTYwcHhcdTY1RjZcdTY1RTBcdThCQkFcdTU5ODJcdTRGNTVcdTkwRkRcdTRFMERcdTRGMUFcdTY2M0VcdTc5M0FcdTMwMDJcclxuICovXHJcbmNvbnN0IGh0bWxNb2R1bGU6IFZkb2luZ1RoZW1lQ29uZmlnWydodG1sTW9kdWxlcyddID0ge1xyXG4gIC8vIGhvbWVTaWRlYmFyQjpcclxuICAvLyAgIGA8ZGl2IHN0eWxlPVwicGFkZGluZzogMC45NXJlbVwiPlxyXG4gIC8vICAgPHAgc3R5bGU9XCJcclxuICAvLyAgICAgY29sb3I6IHZhcigtLXRleHRDb2xvcik7XHJcbiAgLy8gICAgIG9wYWNpdHk6IDAuOTtcclxuICAvLyAgICAgZm9udC1zaXplOiAyMHB4O1xyXG4gIC8vICAgICBmb250LXdlaWdodDogYm9sZDtcclxuICAvLyAgICAgbWFyZ2luOiAwIDAgOHB4IDA7XHJcbiAgLy8gICBcIj5cdTUxNkNcdTRGMTdcdTUzRjc8L3A+XHJcbiAgLy8gICA8aW1nIHNyYz1cImh0dHBzOi8vb3Blbi53ZWl4aW4ucXEuY29tL3FyL2NvZGU/dXNlcm5hbWU9Z2hfMGNmNGI4MTM5MThjXCIgIHN0eWxlPVwid2lkdGg6MTAwJTtcIiAvPlxyXG4gIC8vICAgXHU1MTczXHU2Q0U4XHU1MTZDXHU0RjE3XHU1M0Y3XHVGRjBDXHU1NkRFXHU1OTBEWzxiPlx1NTI0RFx1N0FFRlx1OEQ0NFx1NkU5MDwvYj5dXHVGRjBDXHU1M0VGXHU4M0I3XHU1M0Q2IDxhIGhyZWY9XCJodHRwczovL2dhbWUueHVnYW95aS5jb21cIiBhcmdldD1cIl9ibGFua1wiID5cdTUyNERcdTdBRUZcdTVCNjZcdTRFNjBcdThENDRcdTZFOTA8c3Bhbj48c3ZnIHhtbG5zPVwiaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmdcIiBhcmlhLWhpZGRlbj1cInRydWVcIiBmb2N1c2FibGU9XCJmYWxzZVwiIHg9XCIwcHhcIiB5PVwiMHB4XCIgdmlld0JveD1cIjAgMCAxMDAgMTAwXCIgd2lkdGg9XCIxNVwiIGhlaWdodD1cIjE1XCIgY2xhc3M9XCJpY29uIG91dGJvdW5kXCI+PHBhdGggZmlsbD1cImN1cnJlbnRDb2xvclwiIGQ9XCJNMTguOCw4NS4xaDU2bDAsMGMyLjIsMCw0LTEuOCw0LTR2LTMyaC04djI4aC00OHYtNDhoMjh2LThoLTMybDAsMGMtMi4yLDAtNCwxLjgtNCw0djU2QzE0LjgsODMuMywxNi42LDg1LjEsMTguOCw4NS4xelwiPjwvcGF0aD4gPHBvbHlnb24gZmlsbD1cImN1cnJlbnRDb2xvclwiIHBvaW50cz1cIjQ1LjcsNDguNyA1MS4zLDU0LjMgNzcuMiwyOC41IDc3LjIsMzcuMiA4NS4yLDM3LjIgODUuMiwxNC45IDYyLjgsMTQuOSA2Mi44LDIyLjkgNzEuNSwyMi45XCI+PC9wb2x5Z29uPjwvc3ZnPiA8c3BhbiBjbGFzcz1cInNyLW9ubHlcIj4ob3BlbnMgbmV3IHdpbmRvdyk8L3NwYW4+PC9zcGFuPjwvYT5cclxuICAvLyAgIDwvcD5cclxuICAvLyAgIDwvZGl2PmAsXHJcbn1cclxuZXhwb3J0IGRlZmF1bHQgaHRtbE1vZHVsZVxyXG4iXSwKICAibWFwcGluZ3MiOiAiO0FBSUE7QUFFQTs7O0FDTkEsSUFBTyxvQkFBUTs7O0FDcUJmLElBQU0sYUFBK0M7QUFlckQsSUFBTyxzQkFBUTs7O0FGMUJmLElBQU0sY0FBYztBQUNwQixJQUFNLFdBQVcsV0FBVztBQUU1QixJQUFPLGlCQUFRLHlCQUE0QztBQUFBLEVBQ3pELE9BQU87QUFBQSxFQUdQLFNBQVM7QUFBQSxJQUNQLEtBQUs7QUFBQSxNQUNILE1BQU07QUFBQSxNQUNOLE9BQU87QUFBQSxNQUNQLGFBQWE7QUFBQTtBQUFBO0FBQUEsRUFNakIsYUFBYTtBQUFBLElBRVgsS0FBSztBQUFBLE1BQ0gsRUFBRSxNQUFNLGdCQUFNLE1BQU07QUFBQSxNQUNwQjtBQUFBLFFBQ0UsTUFBTTtBQUFBLFFBQ04sTUFBTTtBQUFBLFFBQ04sT0FBTztBQUFBLFVBRUw7QUFBQSxZQUNFLE1BQU07QUFBQSxZQUNOLE9BQU87QUFBQSxjQUNMLEVBQUUsTUFBTSxjQUFjLE1BQU07QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLE1BS3BDO0FBQUEsUUFDRSxNQUFNO0FBQUEsUUFDTixNQUFNO0FBQUEsUUFDTixPQUFPO0FBQUEsVUFDTCxFQUFFLE1BQU0sUUFBUSxNQUFNO0FBQUEsVUFDdEIsRUFBRSxNQUFNLE9BQU8sTUFBTTtBQUFBO0FBQUE7QUFBQSxNQUd6QjtBQUFBLFFBQ0UsTUFBTTtBQUFBLFFBQ04sTUFBTTtBQUFBLFFBQ04sT0FBTztBQUFBLFVBQ0wsRUFBRSxNQUFNLDRCQUFRLE1BQU07QUFBQSxVQUN0QixFQUFFLE1BQU0sc0JBQVksTUFBTTtBQUFBLFVBQzFCLEVBQUUsTUFBTSxVQUFVLE1BQU07QUFBQSxVQUN4QixFQUFFLE1BQU0sNEJBQVEsTUFBTTtBQUFBO0FBQUE7QUFBQSxNQUcxQixFQUFFLE1BQU0sZ0JBQU0sTUFBTTtBQUFB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