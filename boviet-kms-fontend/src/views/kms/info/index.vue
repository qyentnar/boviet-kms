<template>
<div class="app-container">
    <el-row>
        <el-col :span="24">
            <el-page-header @back="goBack" />
        </el-col>
    </el-row>
    <el-row :gutter="15" type="flex" justify="center">
        <el-col :span="6" :xs="24">
            <el-card class="box-card">
                <el-row>
                    <el-col :span="24">
                        <div class="text-center">
                            <documentThumbnail :src="src" />
                            <div class="document-title">{{info.title}}</div>
                            <el-row type="flex" justify="center">
                                <el-col :span="16">
                                    <div class="document-description">
                                        <div class="tag-group">
                                            <span class="tag-group__title">Author: </span>
                                            <el-row>
                                                <el-col :span="18" :offset="6" v-for="item in info.extAuthor" :key="item">
                                                    <el-tag effect="dark" size="mini" type="warning">
                                                        {{ item }}
                                                    </el-tag>
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </div>
                                </el-col>
                            </el-row>

                            <div class="document-info">
                                <el-tag size="mini" type="success"><i class="el-icon-s-flag" /> {{extension}}</el-tag>
                                <el-tag size="mini" type="info"><i class="el-icon-date" /> {{publishDate}}</el-tag>
                            </div>
                            <div class="document-action">
                                <el-button type="primary" size="mini" @click="gotoView" icon="el-icon-view">View</el-button>
                                <el-button type="primary" size="mini" @click="download" icon="el-icon-download">Download</el-button>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="10" :xs="24">
            <el-card class="box-card document-details">
                <el-descriptions border :column="1">
                    <el-descriptions-item label="Document ID">{{info.id}}</el-descriptions-item>
                    <el-descriptions-item label="Document Name">{{info.title}}</el-descriptions-item>
                    <el-descriptions-item label="Document Description">{{info.summary}}</el-descriptions-item>
                    <el-descriptions-item label="Document Version">{{info.version}}</el-descriptions-item>
                    <el-descriptions-item label="Document State">{{info.state}}</el-descriptions-item>
                    <el-descriptions-item label="Department">{{info.deptId}}</el-descriptions-item>
                    <el-descriptions-item label="File Name">{{info.fileName}}</el-descriptions-item>
                    <el-descriptions-item label="Create By">{{info.createBy}}</el-descriptions-item>
                    <el-descriptions-item label="Create Time">{{info.createTime}}</el-descriptions-item>
                    <el-descriptions-item label="Publish Date">{{info.publishDate}}</el-descriptions-item>
                    <el-descriptions-item label="Read Count">{{info.readCount}}</el-descriptions-item>
                    <el-descriptions-item label="Process Id">{{info.processIntanceId}}</el-descriptions-item>
                    <el-descriptions-item label="Update By">{{info.updateBy}}</el-descriptions-item>
                    <el-descriptions-item label="Update Time">{{info.updateTime}}</el-descriptions-item>
                </el-descriptions>
            </el-card>
        </el-col>
    </el-row>
</div>
</template>

<script>
import documentThumbnail from "../components/document-thumbnail";
import {
    listForEs,
    getMain
} from "@/api/kms/main";

export default {
    name: "kms-info",
    components: {
        documentThumbnail,
    },
    data() {
        return {
            src: "/static/img/profile.473f5971.jpg",
            info: [],
            loading: false,
            extension:"",
            publishDate: "",
        };
    },
    created() {
        this.searchList();
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        },
        searchList() {
            this.loading = true;
            const form = {
                searchKey: 'id',
                searchValue: this.$route.query.id,
                pageSize: 10,
                sortBy: 'createTime',
                sortType: 1,
                searchBy: 1
            }
            getMain(this.$route.query.id).then(response => {
                this.info = response.data;
                this.formattedDate(this.info.publishDate);
                this.getExtension();
                this.loading = false;
            });
        },
        formattedDate(inputDate) {
            const date = new Date(inputDate);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            this.publishDate =  `${year}-${month}-${day}`;
        },
        getExtension(){
          const fileName = this.info.fileName;
          this.extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        },
        gotoView(){
          this.$router.push( {
            name: "document-view",
            query: {
              id: this.$route.query.id
            }
          });
        }
    },
};
</script>

<style lang="scss">
.el-row {
    margin-bottom: 20px;

    &:last-child {
        margin-bottom: 0;
    }
}

.app-container {
    height: 95%;

    .el-row {
        &:nth-of-type(2) {
            height: 95%;
        }

    }
}

.el-descriptions-row th {
    &:first-of-type {
        width: 30%;
    }
}

.document-title {
    font-size: 25px;
    font-weight: bold;
    line-height: 25px;
    margin-top: 15px;
}

.document-description {
    font-size: 14px;
    line-height: 1.5;
    margin-top: 5px;
    text-align: left;
}

.document-info {
    margin-top: 10px;
}

.el-tag {
    margin-right: 10px;
}

.el-button {
    margin-top: 20px;
}

.box-card {
    // Các thuộc tính CSS cho box-card đầu tiên
    width: 100%;
    height: 100%;
    background-color: #f5f5f5;
    border-radius: 4px;
    padding: 20px;
    // Thêm các thuộc tính khác nếu cần
}

.tag-group__title {
    font-size: 14px;
    font-weight: bold;
    margin-right: 5px;
}

.tag-group .el-row .el-col {
    margin-bottom: 5px;
}

.document-action {
    margin-top: 100px
}
</style>
