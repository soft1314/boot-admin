<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author ${marker.author} -->
<!-- @since ${.now} -->
<template>
    <div class="app-container background-white">
        <!-- 表数据编辑对话框区开始 -->
        <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" :rules="rules" size="medium" label-width="150px">
            <#list marker.cols as col>
                <#if col.prop != 'version' && col.prop != 'tenantId' && col.prop != 'guid' && col.prop != 'createBy' && col.prop != 'createTime' && col.prop != 'modifyBy' && col.prop != 'modifyTime' && col.prop != 'datestamp' && col.prop != 'flowStatus'>
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
        <div slot="footer" style="text-align: center;">
            <el-button type="primary" @click="handleSubmitMainDataForm()">
                提交
            </el-button>
            <el-button type="warning" @click="resetForm('mainEditForm')">
                重置
            </el-button>
        </div>
    </div>
</template>
<script>
    import {
        save${marker.entityName}AndStartProcess
    } from '@/api/generated/${marker.apiJsFileName}-scene3'
    import {
        getDictionaryOptionsByItemType,
        lazyFetchDictionaryNode
    } from '@/api/dictionary'
    export default {
        name: '${marker.apiJsFileName}-add',
        computed: {},
        components: {},
        data() {
            const that = this;
            return {
                loading: true,
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
                        procDefKey: ''
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
        props: {
            procDefKey: String
        },
        created() {},
        mounted() {
            this.loadAllOptions()
        },
        watch: {},
        inject: ['reload'],
        methods: {
            refresh() {
                this.reload()
            },
            loadAllOptions() {
                for (var i = 0; i < this.optionKey.length; i++) {
                    this.loadDictionaryOptions(this.optionKey[i], false)
                }
            },
            colFormatter(row, column, cellValue, key) {
                return this.$commonUtils.optoinValue2Lable(this.optionMap.get(key), cellValue)
            },
            dateTimeColFormatter(row, column, cellValue) {
                return this.$commonUtils.dateTimeFormat(cellValue)
            },
            dateColFormatter(row, column, cellValue) {
                return this.$commonUtils.dateFormat(cellValue)
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
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            handleSubmitMainDataForm() {
                this.mainDataForm.editingRecord.procDefKey = this.procDefKey
                this.$refs['mainEditForm'].validate((valid) => {
                    if (valid) {
                        this.submitMainDataForm()
                    } else {
                        console.log('未通过表单校验!!');
                        return false;
                    }
                });
            },
            async submitMainDataForm() {
                const response = await save${marker.entityName}AndStartProcess(this.mainDataForm.editingRecord)
                this.$message({
                    message: response.message,
                    type: 'warning'
                })

                this.resetForm('mainEditForm')
                <#list marker.cols as col>
                    <#if col.type == 'LAZYCODE'>
                        this.$refs.${col.prop}EditCascaderId.$refs.panel.clearCheckedNodes()
                    </#if>
                </#list>
                this.$emit("hideDialog", true)
            },
            <#list marker.cols as col>
                <#if col.type == 'LAZYCODE'>
                    ${col.prop}EditCascaderChangeEvent(value){
                        const code=value[value.length-1]
                        if(code === undefined){
                            this.mainDataForm.editingRecord.${col.prop} = ''
                            return
                        }
                        const name=this.$refs.${col.prop}EditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
                        this.mainDataForm.editingRecord.${col.prop} = code+'_'+name
                    },
                    ${col.prop}LazyLoadData(node, resolve) {
                        const {
                            level,
                        } = node;
                        if (level === 0) {
                            this.loadLazyCodeNode(this.$commonDicType.${col.dicType}(), 'top', resolve)
                        } else {
                            const {
                                value,
                            } = node;
                            this.loadLazyCodeNode(this.$commonDicType.${col.dicType}(), value, resolve)
                        }
                    },
                </#if>
            </#list>
            async loadLazyCodeNode(dicType, code, resolve) {
                this.listLoading = true
                const response = await lazyFetchDictionaryNode(dicType, code)
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
                resolve(data);
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
