<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author ${marker.author} -->
<!-- @since ${.now} -->
<template>
    <div class="app-container background-white">
        <!-- 表数据编辑对话框区开始 -->
        <el-form ref="mainEditForm" :model="mainDataForm.data" :rules="rules" size="medium" label-width="150px">
            <#list marker.cols as col>
                <#if col.prop != 'version' && col.prop != 'tenantId' && col.prop != 'guid' && col.prop != 'createBy' && col.prop != 'createTime' && col.prop != 'modifyBy' && col.prop != 'modifyTime' && col.prop != 'datestamp'>
                    <#if col.type == 'NORMAL'>
                        <el-form-item label="${col.label}" prop="${col.prop}">
                            <el-input v-model="mainDataForm.data.${col.prop}" placeholder="请输入${col.label}" clearable
                                      :style="{width: '100%'}" />
                        </el-form-item>
                    </#if>
                    <#if col.type == 'DATE'>
                        <el-form-item label="${col.label}" prop="${col.prop}">
                            <el-date-picker v-model="mainDataForm.data.${col.prop}" type="date" placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </#if>
                    <#if col.type == 'CODE'>
                        <#if col.prop == 'flowStatus'>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.${col.dicType}()),mainDataForm.data.${col.prop}) }}</span>
                            </el-form-item>
                        <#else>
                            <el-form-item label="${col.label}" prop="${col.prop}">
                                <el-select v-model="mainDataForm.data.${col.prop}" placeholder="请选择">
                                    <el-option
                                        v-for="item in optionMap.get($commonDicType.${col.dicType}())"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </#if>
                    </#if>
                    <#if col.type == 'LAZYCODE'>
                        <el-form-item label="${col.label}" prop="${col.prop}">
                            <el-cascader v-model="cascaderValue.${col.prop}" :props="${col.prop}CascaderProps" ref="${col.prop}EditCascaderId"
                                         style="width: 50%;" @change="${col.prop}EditCascaderChangeEvent">
                            </el-cascader>
                            {{mainDataForm.data.${col.prop}}}
                        </el-form-item>
                    </#if>
                </#if>
            </#list>
            <el-form-item label="提交或销毁批注" prop="reviewComments">
                <el-input type="textarea" :rows="2" v-model="mainDataForm.info.reviewComments" placeholder="请输入批注" clearable
                          :style="{width: '100%'}" />
            </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center;">
            <el-button type="warning" @click="handleSubmitMainDataForm('通过')">
                提交
            </el-button>
            <el-button type="warning" @click="handleSubmitMainDataForm('销毁')">
                销毁
            </el-button>
            <el-button type="primary" @click="resetForm('mainEditForm')">
                重置
            </el-button>
        </div>
    </div>
</template>
<script>
    import {
        fetch${marker.entityName}Info,
        save${marker.entityName}AndCompleteTask
    } from '@/api/generated/${marker.apiJsFileName}-scene4'
    import {
        getDictionaryOptionsByItemType,
        lazyFetchDictionaryNode
    } from '@/api/dictionary'
    export default {
        name: '${marker.apiJsFileName}-edit',
        data() {
            const that = this;
            return {
                loading: true,
                mainDataForm: {
                    data: {
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
                    info: {
                        processInstanceId: '',
                        taskId: '',
                        result: '',
                        reviewComments: ''
                    }
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
            processInstanceId: String,
            taskId: String,
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
                this.mainDataForm.data = response.data
            },
            resetForm(formName) {
                this.$refs.techTitleEditCascaderId.$refs.panel.clearCheckedNodes()
                this.fetchAuditInfo(this.businessId)
            },
            handleSubmitMainDataForm(result) {
                this.mainDataForm.info.result = result
                this.mainDataForm.info.processInstanceId = this.processInstanceId
                this.mainDataForm.info.taskId = this.taskId

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
                const response = await save${marker.entityName}AndCompleteTask(this.mainDataForm)
                this.$message({
                    message: response.message,
                    type: 'warning'
                })

                this.$emit("hideDialog", true)
            },
            <#list marker.cols as col>
            <#if col.type == 'LAZYCODE'>
                    ${col.prop}EditCascaderChangeEvent(value){
                        const code=value[value.length-1]
                        if(code === undefined){
                            this.mainDataForm.data.${col.prop} = ''
                            return
                        }
                        const name=this.$refs.${col.prop}EditCascaderId.getCheckedNodes()[0].pathLabels.join('/')
                        this.mainDataForm.data.${col.prop} = code+'_'+name
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
                // 通过调用resolve将子节点数据返回，通知组件数据加载完成
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
