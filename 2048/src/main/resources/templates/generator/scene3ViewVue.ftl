<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author ${marker.author} -->
<!-- @since ${.now} -->
<template>
    <div class="app-container background-white">
        <!-- 表数据编辑对话框区开始 -->
        <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" size="medium" label-width="150px">
            <#list marker.cols as col>
                <#if col.prop != 'version' && col.prop != 'tenantId' && col.prop != 'guid' && col.prop != 'createBy' && col.prop != 'createTime' && col.prop != 'modifyBy' && col.prop != 'modifyTime' && col.prop != 'datestamp'>
                    <#if col.type == 'NORMAL'>
                        <el-form-item label="${col.label}">
                            <span>{{ mainDataForm.editingRecord.${col.prop} }}</span>
                        </el-form-item>
                    </#if>
                    <#if col.type == 'DATE'>
                        <el-form-item label="${col.label}">
                            <span>{{ this.$commonUtils.dateTimeFormat(mainDataForm.editingRecord.${col.prop}) }}</span>
                        </el-form-item>
                    </#if>
                    <#if col.type == 'CODE'>
                        <el-form-item label="${col.label}">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.${col.dicType}()),mainDataForm.editingRecord.${col.prop}) }}</span>
                        </el-form-item>
                    </#if>
                    <#if col.type == 'LAZYCODE'>
                        <el-form-item label="${col.label}">
                            <span>{{ mainDataForm.editingRecord.${col.prop} }}</span>
                        </el-form-item>
                    </#if>
                </#if>
            </#list>
        </el-form>
    </div>
</template>
<script>
    import {
        fetch${marker.entityName}Info
    } from '@/api/generated/${marker.apiJsFileName}-scene3'
    import {
        getDictionaryOptionsByItemType
    } from '@/api/dictionary'
    export default {
        name: '${marker.apiJsFileName}-view',
        data() {
            const that = this;
            return {
                loading: true,
                mainDataForm: {
                    editingRecord: {
                    },
                },
                optionMap: new Map(),
                //本页需要加载的option数据类型罗列在下面的数组中
                optionKey: [
                    <#list marker.cols as col>
                        <#if col.type == 'CODE'>
                            this.$commonDicType.${col.dicType}(),
                        </#if>
                    </#list>
                ],
            }
        },
        props: {
            businessId: String
        },
        created() {},
        mounted() {
            this.loadAllOptions()
            this.fetchAuditInfo(this.businessId)
        },
        watch: {},
        inject: ['reload'],
        methods: {
            refresh() {
                this.reload()
            },
            setBusinessId(businessId){
                this.fetchAuditInfo(businessId)
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
            async fetchAuditInfo(businessId) {
                const guidContainer = {
                    guid: businessId
                }
                const response = await fetch${marker.entityName}Info(guidContainer)
                if (response.code !== 100) {
                    this.$message({
                        message: response.message,
                        type: 'warning'
                    })
                    return
                }
                this.mainDataForm.editingRecord = response.data
            },
        }
    }
</script>
<style>
    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        width: 190px;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        text-align: left;
        margin-right: 0;
        margin-bottom: 0;
        width: 100%;
    }

    /*1.显示滚动条：当内容超出容器的时候，可以拖动：*/
    .el-drawer__body {
        overflow: auto;
        /* overflow-x: auto; */
    }

    /*2.隐藏滚动条，太丑了*/
    .el-drawer__container ::-webkit-scrollbar {
        display: none;
    }
</style>
