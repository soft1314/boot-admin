<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author ${marker.author} -->
<!-- @since ${.now} -->
<template>
    <div class="app-container" style="background-color: #FFFFFF;">
        <!--查询条件和查询按钮区-->
        <el-drawer ref="drawer" :visible.sync="filterDrawer.dialogVisible" direction="rtl" custom-class="demo-drawer">
            <div class="demo-drawer__content">
                <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm" :model="filterDrawer.formData">
                    <#list marker.cols as col>
                        <#if col.prop != 'version' && col.prop != 'tenantId'>
                            <#if col.type == 'NORMAL'>
                                <el-form-item label="${col.label}" :label-width="filterDrawer.formLabelWidth"
                                              prop="${col.prop}">
                                    <el-input placeholder="请输入${col.label}" size="mini" prefix-icon="el-icon-search"
                                              v-model="filterDrawer.formData.${col.prop}">
                                    </el-input>
                                </el-form-item>
                            </#if>
                            <#if col.type == 'CODE'>
                                <el-form-item label="${col.label}" :label-width="filterDrawer.formLabelWidth"
                                              prop="${col.prop}">
                                    <el-select v-model="filterDrawer.formData.${col.prop}" placeholder="请选择${col.label}"
                                               size="mini" clearable>
                                        <el-option v-for="item in optionMap.get($commonDicType.${col.dicType}())"
                                                   :key="item.value"
                                                   :label="item.label" :value="item.value"/>
                                    </el-select>
                                </el-form-item>
                            </#if>
                            <#if col.type == 'LAZYCODE'>
                                <el-form-item label="${col.label}" :label-width="filterDrawer.formLabelWidth" prop="${col.prop}">
                                    <el-cascader :props="${col.prop}CascaderProps" ref="${col.prop}QueryCascaderId" @change="${col.prop}QueryCascaderChangeEvent" clearable style="width: 100%;">
                                    </el-cascader>
                                    {{filterDrawer.formData.${col.prop}}}
                                </el-form-item>
                            </#if>
                            <#if col.type == 'DATE'>
                                <el-form-item label="${col.label}" prop="${col.prop}" :label-width="filterDrawer.formLabelWidth">
                                    <el-date-picker v-model="filterDrawer.formData.${col.prop}" type="date" placeholder="选择日期">
                                    </el-date-picker>
                                </el-form-item>
                            </#if>
                        </#if>
                    </#list>
                    <el-form-item :label-width="filterDrawer.formLabelWidth">
                        <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadTopLevel()">顶层</el-button>
                        <el-button size="mini" type="success" icon="el-icon-search" @click="handleLoadAllLevel()">查询</el-button>
                        <el-button v-on:click="resetForm('drawerForm')" size="mini" type="warning" icon="el-icon-refresh">重置</el-button>
                        <el-button v-on:click="hideDrawer()" size="mini" icon="el-icon-close">关闭</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-drawer>
        <!--查询条件和查询按钮区结束-->
        <!--功能按钮区开始-->
        <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
            <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
                <el-button size="mini" class="btn-item" type="success" icon="el-icon-refresh" @click="refresh()">
                    刷新
                </el-button>
                <el-button size="mini" class="btn-item" type="primary" icon="el-icon-add" @click="handleClickAddButton()">
                    添加
                </el-button>
                <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="handleClickSearchButton()">
                    查询
                </el-button>
            </div>
        </div>
        <!--功能按钮区结束-->
        <!--列表开始-->
        <el-table v-loading="listLoading" :data="mainTableData" stripe border highlight-current-row fit style="width: 100%"
                  :header-cell-style="{'text-align':'center'}" row-key="guid" default-expand-all lazy :load="loadChildren"
                  :tree-props="{hasChildren:'hasChildren'}" ref="multipleTable">
            <#list marker.cols as col>
                <#if col.prop != 'version' && col.prop != 'tenantId'>
                    <#if col.type == 'NORMAL'  || col.type == 'LAZYCODE'>
                        <el-table-column prop="${col.prop}" label="${col.label}" show-overflow-tooltip sortable></el-table-column>
                    </#if>
                    <#if col.type == 'DATE'>
                        <el-table-column prop="${col.prop}" label="${col.label}" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => dateTimeColFormatter(row,column,cellValue)"></el-table-column>
                    </#if>
                    <#if col.type == 'CODE'>
                        <el-table-column prop="${col.prop}" label="${col.label}" show-overflow-tooltip sortable :formatter="(row,column,cellValue) => colFormatter(row,column,cellValue, $commonDicType.${col.dicType}())"></el-table-column>
                    </#if>
                </#if>
            </#list>
            <el-table-column label="操作" min-width="180">
                <template slot-scope="scope">
                    <el-button type="warning" size="least" @click="handleClickEditButton(scope.row)">编辑</el-button>
                    <el-button type="primary" size="least" @click="handleAddSubButton(scope.row)">添加</el-button>
                    <el-button type="danger" size="least" @click="handleDelRowButton(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--列表结束-->
        <div align="right">
            <!--分页控件开始-->
            <div ref="paginationContainer" style="text-align: center;">
                <el-pagination :current-page="filterDrawer.formData.currentPage" :page-sizes="[5,10,20,50,100,500]"
                               :page-size="filterDrawer.formData.pageSize" layout="total, sizes, prev, pager, next, jumper"
                               :total="filterDrawer.formData.total" @size-change="handlePageSizeChange"
                               @current-change="handlePageCurrentChange" />
            </div>
            <!--分页控件结束-->
        </div>
        <!-- 表单区域开始 -->
        <el-dialog :visible.sync="mainDataForm.mainDataFormDialogVisible" :close-on-click-modal="false"
                   :title="mainDataForm.mainDataFormDialogTitle">
            <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="100px">
                <#list marker.cols as col>
                    <#if col.prop != 'version' && col.prop != 'tenantId' && col.prop != 'guid' && col.prop != 'createBy' && col.prop != 'createTime' && col.prop != 'modifyBy' && col.prop != 'modifyTime' && col.prop != 'datestamp' && col.prop != 'parentGuid'>
                        <#if col.type == 'NORMAL'>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <el-input v-model="mainDataForm.editingRecord.${col.prop}" placeholder="请输入${col.label}" clearable
                                          :style="{width: '100%'}" />
                            </el-form-item>
                        </#if>
                        <#if col.type == 'DATE'>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <el-date-picker v-model="mainDataForm.editingRecord.${col.prop}" type="date" placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </#if>
                        <#if col.type == 'CODE'>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <el-select v-model="mainDataForm.editingRecord.${col.prop}" placeholder="请选择">
                                    <el-option
                                            v-for="item in optionMap.get($commonDicType.${col.dicType}())"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </#if>
                        <#if col.type == 'LAZYCODE'>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <el-cascader v-model="cascaderValue.${col.prop}" :props="${col.prop}CascaderProps" ref="${col.prop}EditCascaderId"
                                             style="width: 50%;" @change="${col.prop}EditCascaderChangeEvent">
                                </el-cascader>
                                {{mainDataForm.editingRecord.${col.prop}}}
                            </el-form-item>
                        </#if>
                    </#if>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="mainDataForm.mainDataFormDialogVisible = false">
                    关闭
                </el-button>
                <el-button type="primary" @click="submitMainDataForm()">
                    保存
                </el-button>
                <el-button type="warning" @click="resetForm('mainEditForm')">
                    重置
                </el-button>
            </div>
        </el-dialog>
        <!-- 表单区域结束 -->
    </div>
</template>
<script>
    import {
        save${marker.entityName},
        del${marker.entityName},
        fetchTop${marker.entityName}Page,
        fetch${marker.entityName}Page,
        fetch${marker.entityName}AllChildren
    } from '@/api/generated/${marker.apiJsFileName}-scene2'
    import {
        getDictionaryOptionsByItemType,
        lazyFetchDictionaryNode
    } from '@/api/dictionary'
    export default {
        name: '${marker.apiJsFileName}',
        data() {
            const that = this;
            return {
                listLoading: false,
                mainTableData: [],
                mainDataForm: {
                    editingRecord: {
                        <#list marker.cols as col>
                            <#if col.prop != 'version' && col.prop != 'tenantId' && col.prop != 'guid' && col.prop != 'createBy' && col.prop != 'createTime' && col.prop != 'modifyBy' && col.prop != 'modifyTime' && col.prop != 'datestamp' && col.prop != 'parentGuid'>
                                <#if col.prop == 'deleted'>
                                    deleted: '1',
                                <#elseif col.prop == 'enabled'>
                                    enabled: '1',
                                <#elseif col.prop == 'remarks'>
                                    remarks: '无',
                                <#elseif col.prop == 'leaf'>
                                    leaf: '0',
                                <#elseif col.type == 'DATE'>
                                    ${col.prop}: null,
                                <#else>
                                    ${col.prop}: '',
                                </#if>
                            </#if>
                        </#list>

                    },
                    mainDataFormDialogVisible: false,
                    mainDataFormDialogTitle: '连续新增'
                },
                filterDrawer: {
                    dialogVisible: false,
                    formLabelWidth: '100px',
                    showTopOnly: true,
                    formData: {
                        <#list marker.cols as col>
                            <#if col.prop != 'version' && col.prop != 'tenantId'>
                                <#if col.dataType == 'STRING'>
                                    ${col.prop}: '',
                                </#if>
                                <#if col.dataType == 'NUMBER'>
                                    ${col.prop}: null,
                                </#if>
                                <#if col.dataType == 'DATE'>
                                    ${col.prop}: null,
                                </#if>
                            </#if>
                        </#list>
                        currentPage: 1,
                        pageSize: 10,
                        total: 0,
                    },
                },
                treeMap: new Map(),
                radio: false,
                optionMap: new Map(),
                //本页需要加载的option数据类型罗列在下面的数组中
                optionKey: [
                    <#list marker.cols as col>
                        <#if col.type == 'CODE'>
                            this.$commonDicType.${col.dicType}(),
                        </#if>
                    </#list>
                ],
                <#list marker.cols as col>
                    <#if col.type == 'LAZYCODE'>
                        ${col.prop}CascaderProps: {
                            lazy: true,
                            lazyLoad(node, resolve) {
                                that.${col.prop}LazyLoadData(node, resolve)
                            }
                        },
                    </#if>
                </#list>
                cascaderValue: {
                    <#list marker.cols as col>
                        <#if col.type == 'LAZYCODE'>
                            ${col.prop}: [],
                        </#if>
                    </#list>
                },
                rules: {
                    <#list marker.cols as col>
                        <#if col.prop != 'version' && col.prop != 'tenantId'>
                            <#if col.prop != 'remarks'>
                                ${col.prop}: [{
                                    required: true,
                                    message: '请输入${col.label}',
                                    trigger: 'blur'
                                }],
                            </#if>
                        </#if>
                    </#list>
                }
            }
        },
        watch: {},
        inject: ['reload'],
        created() {},
        mounted() {
            this.loadAllOptions()
            this.loadTopLevel()
        },
        methods: {
            refresh() {
                this.reload()
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            hideDrawer() {
                this.filterDrawer.dialogVisible = false
            },
            handleClickSearchButton() {
                this.filterDrawer.dialogVisible = true
            },
            loadAllOptions() {
                for (var i = 0; i < this.optionKey.length; i++) {
                    this.loadDictionaryOptions(this.optionKey[i], false)
                }
            },
            async loadDictionaryOptions(itemType, includeAllOptions) {
                this.listLoading = true
                const response = await getDictionaryOptionsByItemType(itemType, includeAllOptions)
                this.listLoading = false
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                const {
                    data
                } = response
                this.optionMap.set(itemType, data)
            },
            handleLoadTopLevel(){
                this.filterDrawer.formData.currentPage = 1
                this.loadTopLevel()
            },
            async loadTopLevel() {
                this.filterDrawer.showTopOnly = true
                this.listLoading = false
                const response = await fetchTop${marker.entityName}Page(this.filterDrawer.formData)
                this.listLoading = false
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                const {
                    data
                } = response

                this.mainTableData = data.records
                this.filterDrawer.formData.total = data.total
            },
            handleLoadAllLevel(){
                this.filterDrawer.formData.currentPage = 1
                this.loadAllLevel()
            },
            async loadAllLevel() {
                this.filterDrawer.showTopOnly = false
                this.listLoading = false
                const response = await fetch${marker.entityName}Page(this.filterDrawer.formData)
                this.listLoading = false
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                const {
                    data
                } = response

                this.mainTableData = data.records
                this.filterDrawer.formData.total = data.total
            },
            async loadChildren(row, treeNode, resolve) {
                this.treeMap.set(row.guid, {
                    row,
                    treeNode,
                    resolve
                })
                const response = await fetch${marker.entityName}AllChildren(row.guid)
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                const {
                    data
                } = response
                resolve(data)
            },
            handlePageSizeChange(val) {
                if (val != this.filterDrawer.formData.pageSize) {
                    this.filterDrawer.formData.pageSize = val
                    this.loadDataForTurnPage()
                }
            },
            handlePageCurrentChange(val) {
                if (val != this.filterDrawer.formData.currentPage) {
                    this.filterDrawer.formData.currentPage = val
                    this.loadDataForTurnPage()
                }
            },
            loadDataForTurnPage(){
                if(this.filterDrawer.showTopOnly){
                    this.loadTopLevel()
                }else{
                    this.loadAllLevel()
                }
            },
            indexMethod(index) {
                return this.filterDrawer.formData.pageSize * (this.filterDrawer.formData.currentPage - 1) + index + 1
            },
            submitMainDataForm() {
                this.$refs['mainEditForm'].validate((valid) => {
                    if (valid) {
                        this.saveMainDataForm()
                    } else {
                        console.log('未通过表单校验!!');
                        return false;
                    }
                });
            },
            async saveMainDataForm() {
                this.listLoading = true
                const response = await save${marker.entityName}(this.mainDataForm.editingRecord)
                this.listLoading = false
                this.$message({
                    message: response.message,
                    type: 'warning'
                })
                if (this.mainDataForm.editingRecord.parentGuid === 'top') {
                    this.loadTopLevel()
                } else {
                    this.refreshTable(this.mainDataForm.editingRecord.parentGuid)
                }
            },
            colFormatter(row, column, cellValue, key) {
                return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key),cellValue)
            },
            dateTimeColFormatter(row,column,cellValue){
                return this.$commonUtils.dateTimeFormat(cellValue)
            },
            initFormData() {
                this.mainDataForm.editingRecord.guid = ''
                this.mainDataForm.editingRecord.parentGuid = 'top'
            },
            handleClickAddButton() {
                this.mainDataForm.mainDataFormDialogTitle = '连续新增'
                this.initFormData()
                this.mainDataForm.mainDataFormDialogVisible = true
            },
            handleClickEditButton(row) {
                this.mainDataForm.mainDataFormDialogTitle = '修改'
                this.mainDataForm.editingRecord = this.$commonUtils.shadowCopy(row)
                this.mainDataForm.mainDataFormDialogVisible = true
            },
            handleAddSubButton(row) {
                this.mainDataForm.mainDataFormDialogTitle = '连续新增'
                this.initFormData()
                this.mainDataForm.editingRecord.parentGuid = row.guid
                this.mainDataForm.mainDataFormDialogVisible = true
            },
            handleDelRowButton(row) {
                this.$confirm('此操作将删除该行数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.handleDelRow(row)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            async handleDelRow(row) {
                const guidContainer = {
                    guid: row.guid
                }
                this.listLoading = true
                const response = await del${marker.entityName}(guidContainer)
                this.listLoading = false
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                if (row.parentGuid === 'top') {
                    this.loadTopLevel()
                } else {
                    this.refreshTable(row.parentGuid)
                }
            },
            refreshTable(parentGuid) {
                // 根据父级id取出对应节点数据
                const {
                    row,
                    treeNode,
                    resolve
                } = this.treeMap.get(parentGuid)
                this.$set(this.$refs.multipleTable.store.states.lazyTreeNodeMap, parentGuid, [])
                if (row) {
                    this.loadChildren(row, treeNode, resolve)
                }
            },
            <#list marker.cols as col>
                <#if col.type == 'LAZYCODE'>
                    ${col.prop}LazyLoadData(node, resolve) {
                        const {
                            level,
                        } = node;
                        if(level == 0){
                            const nodes = this.loadLazyCodeNode(this.$commonDicType.${col.dicType}(),'top',resolve)
                        }else{
                            const {
                                value,
                            } = node;
                            const nodes = this.loadLazyCodeNode(this.$commonDicType.${col.dicType}(),value,resolve)
                        }
                    },
                    ${col.prop}EditCascaderChangeEvent(value){
                        const code=value[value.length-1]
                        const name=this.$refs.${col.prop}EditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
                        this.mainDataForm.editingRecord.${col.prop} = code+'_'+name
                    },
                    ${col.prop}QueryCascaderChangeEvent(value){
                        if(value.length == 0){
                            this.filterDrawer.formData.${col.prop} = ''
                        }else{
                            const code=value[value.length-1]
                            const name=this.$refs.${col.prop}QueryCascaderId.getCheckedNodes()[0].pathLabels.join('/')
                            this.filterDrawer.formData.${col.prop} = code+'_'+name
                        }
                    },
                </#if>
            </#list>
            async loadLazyCodeNode(dicType,code,resolve) {
                this.listLoading = true
                const response = await lazyFetchDictionaryNode(dicType,code)
                this.listLoading = false
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                const {
                    data
                } = response
                // 通过调用resolve将子节点数据返回，通知组件数据加载完成
                resolve(data);
            },
        }
    }
</script>
<style>
    /*1.显示滚动条：当内容超出容器的时候，可以拖动：*/
    .el-drawer__body {
        overflow: auto;
        /* overflow-x: auto; */
    }

    /*2.隐藏滚动条，太丑了*/
    .el-drawer__container ::-webkit-scrollbar{
        display: none;
    }
</style>
