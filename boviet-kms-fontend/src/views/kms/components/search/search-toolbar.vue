<template>
  <div>
    <el-card :style="`margin-bottom: 30px`">
        <el-row class="search-input">
            <el-col :md="12" :sm="24" :xs="24">
                <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
                    <el-row>
                        <el-col :span="24">
                            <el-form-item prop="catalogId">
                                <div class="search-toolbar">
                                    <el-dropdown class="dropdown-list" @command="handleCatalogSelected" trigger="click" size="mini">
                                        <el-button style="width: 120px;" size="mini">
                                            所属分类 <i class="el-icon-arrow-down el-icon--right"></i>
                                        </el-button>
                                        <el-dropdown-menu slot="dropdown" >
                                            <el-dropdown-item v-for="catalog in catalogOptions" :key="catalog.id" :command="catalog.id">{{catalog.title}}</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </el-dropdown>
                                    <div v-for="catalog in catalogOptions" :key="catalog.id">
                                        <el-button :type="buttonType" plain size="mini" @click="handleCatalogSelected(catalog.id)" style="margin-right: 20px;">
                                            {{catalog.title}} <i class="el-icon-back el-icon--right" v-if="catalog.id === catalogSelected"/>
                                        </el-button>
                                    </div>
                                </div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24" style="margin-top: -20px;">
                            <div class="search-toolbar">
                                <span class="toolbar-label">
                                    档案编号
                                </span>
                                <el-input v-model="attCode" clearable  placeholder="请输入档案编号" size="mini" style="width: 350px;" @input="handleAttCodeInput">
                                    <el-button v-show="showBtnAttCodeSearch" :slot="showBtnAttCodeSearch ? 'append': ''" icon="el-icon-search" @click="handleAttCodeSearch"/>
                                </el-input>
                            </div>
                        </el-col>
                        <el-col :span="24">
                            <div class="search-toolbar">
                                <span class="toolbar-label">
                                    归档日期
                                </span>
                                <div class="block">
                                    <el-date-picker
                                        v-model="dateRange"
                                        type="daterange"
                                        align="left"
                                        unlink-panels
                                        range-separator="到"
                                        start-placeholder="开始时间"
                                        end-placeholder="结束时间"
                                        :picker-options="pickerOptions" 
                                        @change="handleDatePickerChange"
                                        size="mini"/>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                </el-form>
            </el-col>
            <el-col :md="12" :sm="24" :xs="24">
                <div>
                    <el-tag closable @close="handleCloseTag(1)" v-if="catalogIds.length > 0" type="warning" effect="dark" style="margin-right: 10px;">
                        <span v-for="(catalog,index) in catalogList.filter(item => catalogIds.includes(item.id))" :key="index">
                            <span v-if="index <= 0">
                                所属分类: 
                            </span>
                            <span>
                                {{catalog.title}}
                            </span>
                            <span v-if="index >= 0 && index < catalogList.filter(item => catalogIds.includes(item.id)).length - 1">
                                >
                            </span>
                        </span>
                    </el-tag>
                    <el-tag closable @close="handleCloseTag(2)" v-if="queryParams.attCode.length > 0" type="warning" effect="dark" style="margin-right: 10px;">
                        档案编号: {{queryParams.attCode}}
                    </el-tag>
                    <el-tag closable @close="handleCloseTag(3)" v-if="queryParams.searchTimes != null" type="warning" effect="dark">
                        归档日期: {{queryParams.searchTimes.start}} ~ {{queryParams.searchTimes.end}}
                    </el-tag>
                </div>
            </el-col>
        </el-row>
    </el-card>
  </div>
</template>

<script>
import moment from 'moment';
import { listCatalog } from "@/api/kms/catalog";

export default {
    name: 'BovietKmsSearchToolbar',
    data() {
        return {
            pickerOptions: {
                shortcuts: [{
                    text: '近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, 
                {
                    text: '近一个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                },
                {
                    text: '近三个月',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setMonth(start.getMonth() - 3);
                    picker.$emit('pick', [start, end]);
                    },
                },
                {
                    text: '近半年',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setMonth(start.getMonth() - 6);
                        picker.$emit('pick', [start, end]);
                    },
                },
                {
                    text: '近一年',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setFullYear(start.getFullYear() - 1);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
            showBtnAttCodeSearch: false,
            attCode: '',
            dateRange:[],
            catalogSelected: null,
            buttonType: 'primary',
            catalogOptions: [],
            catalogList: [],
            catalogIds: [],
            queryParams: {
                catalogId: null,
                attCode: '',
                searchTimes: null,
            }
        };
    },

    mounted() {
        this.init()
    },

    methods: {
        init(){
            listCatalog().then(response => {
                if(response.rows.length > 0){
                    this.catalogList = response.rows;
                    this.catalogOptions = this.handleTree(this.catalogList, "id");
                }
            });
        },
        handleCloseTag(index){
            switch(index){
                case 1:
                    this.catalogIds = []
                    this.catalogSelected = null
                    this.catalogOptions = this.handleTree(this.catalogList, "id");
                    break;
                case 2:
                    this.queryParams.attCode = ""
                    break;
                case 3:
                    this.dateRange = null;
                    this.queryParams.searchTimes = null
                    break;
            }
            this.handleQueryParams();
        },
        handleCatalogSelected(catalogId){
            const catalogIds = this.catalogIds
            if(catalogIds.indexOf(catalogId) == -1){
                this.catalogIds.push(catalogId)
            }else {
                this.catalogIds.splice(this.catalogIds.indexOf(catalogId), 1)
            }
            const lastSelected = this.catalogIds.slice(-1)[0]
            this.catalogSelected = lastSelected
            if(lastSelected === null || lastSelected === undefined) {
                this.catalogOptions = this.handleTree(this.catalogList, "id");
            }else{
                const data = this.catalogList
                this.catalogOptions = data.filter(catelog => {
                    return catelog.parentId === lastSelected || catelog.id === lastSelected;
                })
            }
            if(this.catalogIds.length > 0) {
                this.queryParams.catalogId = this.catalogIds[this.catalogIds.length - 1]
            }else{
                this.queryParams.catalogId = null
            }
            this.handleQueryParams();
        },
        handleAttCodeInput(input){
            if(input !== null && input !== ""){
                this.showBtnAttCodeSearch = true;
            }else {
                this.showBtnAttCodeSearch = false;
            }
        },
        handleAttCodeSearch(){
            this.queryParams.attCode = this.attCode
            this.handleQueryParams();
        },
        handleDatePickerChange(){
            this.queryParams.searchTimes = null
            if(this.dateRange != null && this.dateRange != ""){
                const start = moment(this.dateRange[0]).format('YYYY-MM-DD')
                const end = moment(this.dateRange[1]).format('YYYY-MM-DD')
                this.queryParams.searchTimes = {
                    start: start,
                    end: end
                }
            }
            this.queryParams.pageNum = 1
            this.handleQueryParams();
        },
        handleQueryParams() {
            this.$emit("change", this.queryParams)
            this.$emit("search")
        },
    },
};
</script>
<style lang="css" scoped>
    .toolbar-label {
        display: inline-block;
        line-height: 1;
        white-space: nowrap;
        background: #FFF;
        border: 1px solid #DCDFE6;
        color: #606266;
        text-align: center;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        outline: 0;
        margin: 0;
        -webkit-transition: .1s;
        transition: .1s;
        font-weight: 500;
        padding: 9px 15px;
        font-size: 12px;
        border-radius: 3px;
    }

    .search-toolbar {
        display: flex;
        justify-content: start;
        align-items: center;
        margin-bottom: 10px;
        .toolbar-label {
            margin-right: 30px;
            width: 120px;
            height: 30px;
        }
    }

    .dropdown-list {
        margin-right: 30px;
    }
</style>