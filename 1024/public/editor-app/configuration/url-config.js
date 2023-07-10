/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
var KISBPM = KISBPM || {};

KISBPM.URL = {

  getModel: function(modelId) {
    return ACTIVITI.CONFIG.contextRoot + '/model/json?modelId=' + modelId;
    // return ACTIVITI.CONFIG.contextRoot + '/model/' + modelId + '/json';
  },

  getStencilSet: function() {
    return ACTIVITI.CONFIG.contextRoot + '/editor/stencilset?version=' + Date.now();
    // return 'http://localhost:4096/api/workflow/free/activiti'+ '/editor/stencilset?version=' + Date.now();
  },
  getFreeStencilSet: function() {
    return ACTIVITI.CONFIG.contextFreeRoot + '/editor/stencilset?version=' + Date.now();
    // return 'http://localhost:4096/api/workflow/free/activiti'+ '/editor/stencilset?version=' + Date.now();
  },
  putModel: function(modelId) {
    return ACTIVITI.CONFIG.contextRoot + '/model/save?modelId=' + modelId;
    // return ACTIVITI.CONFIG.contextRoot + '/model/' + modelId + '/save';
  },

  getToken: function() {
    var cookies = document.cookie;
    var list = cookies.split("; "); // 解析出名/值对列表

    for (var i = 0; i < list.length; i++) {
      var arr = list[i].split("="); // 解析出名和值
      if (arr[0] == "Admin-Token") {
        var cookieVal = decodeURIComponent(arr[1]); // 对cookie值解码
        break;
      }
    }
    return 'Bearer' + cookieVal;
  }
};
