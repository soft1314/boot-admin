<template>
    <div class="app-container background-white">
        <#if vue.showQueryButton>
            <!-- 查询抽屉开始 -->
            <el-drawer :visible.sync="drawerDialogVisible" direction="rtl" title="请输入查询条件" :with-header="false"
                       size="30%">
                <div class="demo-drawer__content">
                    <el-form class="demo-form-inline" style="margin-top: 25px;margin-right: 20px;" ref="drawerForm"
                             :model="listQuery">
                        <#list vue.drawer.formItemList as col>
                            <#if col.type == 'INPUT'>
                                <el-form-item label="${col.label}" :label-width="drawerFormLabelWidth" prop="${col.vmodel}">
                                    <el-input placeholder="${col.placeholder}" size="mini" prefix-icon="el-icon-search"
                                              v-model="listQuery.${col.vmodel}">
                                    </el-input>
                                </el-form-item>
                            </#if>
                            <#if col.type == 'SELECT'>
                                <el-form-item label="${col.label}" :label-width="drawerFormLabelWidth" prop="${col.vmodel}">
                                    <el-select v-model="listQuery.${col.vmodel}" placeholder="${col.placeholder}" size="mini">
                                        <el-option v-for="item in options.${col.vmodel}Options" :key="item.value"
                                                   :label="item.label" :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </#if>
                            <#if col.type == 'INPUTWITHBUTTON'>
                                <el-form-item label="${col.label}" :label-width="drawerFormLabelWidth">
                                    <el-input v-model="listQuery.${col.vmodel}" placeholder="${col.placeholder}"  size="mini">
                                        <el-button slot="append" icon="el-icon-more" @click="${col.click}"></el-button>
                                    </el-input>
                                </el-form-item>
                            </#if>
                        </#list>
                        <el-form-item :label-width="drawerFormLabelWidth">
                            <el-button v-on:click="fetchTableData()" size="mini" type="success" icon="el-icon-search">查询
                            </el-button>
                            <el-button v-on:click="resetForm('drawerForm')" size="mini" type="warning"
                                       icon="el-icon-refresh">重置
                            </el-button>
                            <el-button v-on:click="hideDrawer()" size="mini" icon="el-icon-close">关闭</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-drawer>
            <!-- 查询抽屉结束 -->
        </#if>
        <!-- 按钮区域开始 -->
        <div ref="buttonContainer" class="background-gray" style="margin-top: 0px;margin-bottom: 0px;padding-top: 0px;">
            <div class="btn-container" style="padding-top: 5px;padding-bottom: 5px;margin-top: 0px;">
                <#if vue.showRefreshButton>
                    <el-button size="mini" class="btn-item" type="warning" icon="el-icon-refresh" @click="refresh()">
                        刷新
                    </el-button>
                </#if>
                <#if vue.showAddButton>
                    <el-button size="mini" class="btn-item" type="primary" icon="el-icon-circle-plus-outline"
                               @click="addItem()">
                        添加
                    </el-button>
                </#if>
                <#if vue.showDelButton>
                    <el-button size="mini" class="btn-item" type="danger" icon="el-icon-remove-outline"
                               @click="delItem()">
                        删除
                    </el-button>
                </#if>
                <#if vue.showExportButton>
                    <el-button size="mini" class="btn-item" type="primary" icon="el-icon-download"
                               @click="exportList()">
                        导出
                    </el-button>
                </#if>
                <#if vue.showSubmitButton>
                    <el-button size="mini" class="btn-item" type="primary" icon="el-icon-finished" @click="send()">
                        提交
                    </el-button>
                </#if>
                <#if vue.showAuditButton>
                    <el-button size="mini" class="btn-item" type="primary" icon="el-icon-document-checked"
                               @click="audit()">
                        审核
                    </el-button>
                </#if>
                <#if vue.showRevokeButton>
                    <el-button size="mini" class="btn-item" type="primary" icon="el-icon-back" @click="revoke()">
                        撤回
                    </el-button>
                </#if>
                <#if vue.showQueryButton>
                    <el-button size="mini" class="btn-item" type="success" icon="el-icon-search" @click="showDrawer()">
                        查询
                    </el-button>
                </#if>
            </div>
        </div>
        <!-- 按钮区域接收 -->
        <#if vue.showElTable>
            <!-- 数据列表区域开始 -->
            <div class="table-container" style="padding: 0;margin: 0px 0px 0px 0px;">
                <el-table v-loading="loading" :data="tableData" border fit highlight-current-row style="width: 100%">
                    <#if vue.elTable.showExpandColumn>
                        <el-table-column type="expand">
                            <template slot-scope="props">
                                <el-form label-position="left" inline class="demo-table-expand">
                                    <#list vue.elTable.expandColumnList as col>
                                        <el-form-item label="${col.label}">
                                            <span>{{props.row.${ col.prop }}}</span>
                                        </el-form-item>
                                    </#list>
                                </el-form>
                            </template>
                        </el-table-column>
                    </#if>
                    <#if vue.elTable.showCheckedColumn>
                        <el-table-column type="selection" width="55"></el-table-column>
                    </#if>
                    <#if vue.elTable.showIndexColumn>
                        <el-table-column type="index" label="序号" :index="indexMethod" width="100"></el-table-column>
                    </#if>
                    <#list vue.elTable.elTableColumnList as col>
                        <el-table-column prop="${ col.prop }" label="${ col.label }" <#if col.useFormatter>:formatter="${col.prop}ConverterLocal"</#if>></el-table-column>
                    </#list>
                    <#if vue.elTable.showOperateColumn>
                        <el-table-column align="center" label="操作" min-width="120">
                            <template slot-scope="scope">
                                <el-button size="least" type="success" @click="showDetail(scope.row)">详情</el-button>
                            </template>
                        </el-table-column>
                    </#if>
                </el-table>
            </div>
            <!-- 数据列表区域结束 -->
            <!-- 分页组件开始 -->
            <div ref="paginationContainer" style="text-align: center;">
                <el-pagination v-on:size-change="handlePageSizeChange" v-on:current-change="handlePageCurrentChange"
                               :current-page="listQuery.page" :page-sizes="[5,10,20,50,100,500]"
                               :page-size="listQuery.limit"
                               layout="total, sizes, prev, pager, next, jumper" :total="listQuery.total">
                </el-pagination>
            </div>
            <!-- 分页组件结束 -->
        </#if>
        <!-- 对话框区开始 -->

        <!-- 对话框区结束 -->
    </div>
</template>
<script>
    import {
        fetchOptionsByType
    } from '@/api/options'
    import {
        optionValueLabelConverter
    } from '@/utils/arrayutil'

    import {
        ${vue.dataFetcherMethodName}
    } from '@/api/auto/${vue.dataFetcherApiFileName}'

    export default {
        name: 'mybatisplus',
        computed: {},
        components: {},
        data() {
            return {
                loading: true,
                tableData: [],
                <#if vue.showQueryButton>
                drawerDialogVisible: false,
                drawerFormLabelWidth: '100px',
                </#if>
                listQuery: {
            <#if vue.showQueryButton>
            <#list vue.drawer.formItemList as col>
            ${col.vmodel}: '',
                </#list>
                </#if>
                page: 1,
                limit: 5,
                total: 5,
        },
            options: {
                tableTypeOptions: [],
                    <#list vue.elTableColumnOptionDTOS as col>
                    ${col.columnName}Filter: {
                    optionType: '${col.optionType}'
                },
                ${col.columnName}Options: []
                </#list>
            }
        }
        },
        created() {
        },
        mounted() {
            this.fetchTableData()
            <#list vue.elTableColumnOptionDTOS as col>
            this.${col.columnName}OptionsFetcher()
            </#list>
        },
        watch: {},
        <#if vue.showRefreshButton>
        inject: ['reload'],
        </#if>
        methods: {
            <#if vue.showRefreshButton>
            refresh() {
                this.reload()
            },
            </#if>
            handlePageSizeChange(val) {
                if (val != this.listQuery.limit) {
                    this.listQuery.limit = val;
                    this.fetchTableData()
                }
            },
            handlePageCurrentChange(val) {
                if (val != this.listQuery.page) {
                    this.listQuery.page = val;
                    this.fetchTableData()
                }
            },
            indexMethod(index) {
                return this.listQuery.limit * (this.listQuery.page - 1) + index + 1
            },
            resetForm(formName) {
                this.$refs[formName].resetFields()
            },
            <#if vue.showAddButton>
            addItem() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showDelButton>
            delItem() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showExportButton>
            exportList() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showSubmitButton>
            send() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showAuditButton>
            audit() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showRevokeButton>
            revoke() {
                alert("todo 在此添加您的代码。")
            },
            </#if>
            <#if vue.showQueryButton>
            showDrawer() {
                this.drawerDialogVisible = true
            },
            hideDrawer() {
                this.drawerDialogVisible = false
            },
            </#if>
            <#if vue.elTable.showOperateColumn>
            showDetail(row){
                alert("todo 在此添加您的代码。");
            },
            </#if>
            <#list vue.elTableColumnOptionDTOS as col>
            async ${col.columnName}OptionsFetcher() {
                const response = await fetchOptionsByType(this.options.${col.columnName}Filter)
                this.options.${col.columnName}Options = response.data
            },
            ${col.columnName}ConverterLocal(val) {
                return optionValueLabelConverter(this.options.${col.columnName}Options, val.${col.columnName})
            },
    </#list>
    <#list vue.drawer.formItemList as col>
    <#if col.type == 'INPUTWITHBUTTON'>
    ${col.click} {
        alert("todo 在此添加您的代码。")
    },
    </#if>
    </#list>
    async fetchTableData() {
        this.loading = false
        const response = await ${vue.dataFetcherMethodName}(this.listQuery)
        if (100 !== response.code) {
            this.loading = false
            this.$message({
                message: response.message,
                type: 'warning'
            })
            return
        }
        const {
            data
        } = response
        this.tableData = data.records
        this.listQuery.total = data.total
        this.loading = false
    },
    deleteItems() {
        this.$confirm('此操作将永久删除选中的数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$message({
                type: 'success',
                message: '删除成功!'
            });
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除'
            });
        });
    },
    codeGenerateEntity(tableName) {
        this.$confirm('此操作将生成数据表【' + tableName + '】对应的实体类源代码（JAVA）, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$message({
                type: 'success',
                message: '完成源代码生成!'
            });
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消生成操作!'
            });
        });
    }
    }
    }
</script>
<style>
</style>
