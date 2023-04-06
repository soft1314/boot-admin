<!-- 本文件自动生成，再次生成时易被覆盖 -->
<!-- @author 虚领顶劲气沉丹田 -->
<!-- @since 2023-3-30 15:13:02 -->
<template>
    <div class="app-container background-white">
        <!-- 表数据编辑对话框区开始 -->
        <el-form ref="mainEditForm" :model="mainDataForm.editingRecord" size="medium" label-width="150px">
                        <el-form-item label="标题">
                            <span>{{ mainDataForm.editingRecord.title }}</span>
                        </el-form-item>
                        <el-form-item label="请假天数">
                            <span>{{ mainDataForm.editingRecord.days }}</span>
                        </el-form-item>
                        <el-form-item label="请假类型">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.LEAVE_TYPE()),mainDataForm.editingRecord.leaveType) }}</span>
                        </el-form-item>
                        <el-form-item label="情况说明">
                            <span>{{ mainDataForm.editingRecord.note }}</span>
                        </el-form-item>
                        <el-form-item label="请假人">
                            <span>{{ mainDataForm.editingRecord.empName }}</span>
                        </el-form-item>
                        <el-form-item label="请假日期">
                            <span>{{ this.$commonUtils.dateTimeFormat(mainDataForm.editingRecord.bizDate) }}</span>
                        </el-form-item>
                        <el-form-item label="离岗时间">
                            <span>{{ this.$commonUtils.dateTimeFormat(mainDataForm.editingRecord.startTime) }}</span>
                        </el-form-item>
                        <el-form-item label="返岗时间">
                            <span>{{ this.$commonUtils.dateTimeFormat(mainDataForm.editingRecord.finishTime) }}</span>
                        </el-form-item>
                        <el-form-item label="职称">
                            <span>{{ mainDataForm.editingRecord.techTitle }}</span>
                        </el-form-item>
                        <el-form-item label="职务级别">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.EMP_LEVEL()),mainDataForm.editingRecord.empLevel) }}</span>
                        </el-form-item>
                        <el-form-item label="性别">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.SEX()),mainDataForm.editingRecord.sex) }}</span>
                        </el-form-item>
                        <el-form-item label="流转状态">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.FLOW_STATUS()),mainDataForm.editingRecord.flowStatus) }}</span>
                        </el-form-item>
                        <el-form-item label="启用状态">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.ENABLED()),mainDataForm.editingRecord.enabled) }}</span>
                        </el-form-item>
                        <el-form-item label="删除状态">
                            <span>{{ $commonUtils.optoinValue2Lable(optionMap.get($commonDicType.DELETED()),mainDataForm.editingRecord.deleted) }}</span>
                        </el-form-item>
                        <el-form-item label="备注">
                            <span>{{ mainDataForm.editingRecord.remarks }}</span>
                        </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import {
        fetchDemoLeaveInfo
    } from '@/api/generated/demo-leave-scene3'
    import {
        getDictionaryOptionsByItemType
    } from '@/api/dictionary'
    export default {
        name: 'demo-leave-view',
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
                            this.$commonDicType.LEAVE_TYPE(),
                            this.$commonDicType.EMP_LEVEL(),
                            this.$commonDicType.SEX(),
                            this.$commonDicType.FLOW_STATUS(),
                            this.$commonDicType.ENABLED(),
                            this.$commonDicType.DELETED(),
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
                const response = await fetchDemoLeaveInfo(guidContainer)
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
