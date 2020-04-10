<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button
                type="primary"
                icon="el-icon-delete"
                class="handle-del mr10"
                @click="deleteAll"
        >批量删除
        </el-button>
        <el-select @change="changeStatus" v-model="query.status" placeholder="文章位置" class="handle-select mr10">
          <el-option key="-1" label="全部文章" :value="-1"></el-option>
          <el-option key="1" label="已发布" :value="1"></el-option>
          <el-option key="0" label="回收站" :value="0"></el-option>
          <el-option key="2" label="草稿箱" :value="2"></el-option>
        </el-select>
        <el-input v-model="query.keyword" placeholder="通过标题搜索" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button type="info" plain @click="handleClear">重置</el-button>
        <el-switch
                v-if="this.$store.getters.isAdmin"
                v-model="enableAdmin"
                style="display: inline-block;font-size: 12px;float: right;"
                active-text="开启管理员模式"
                active-color="#13ce66"
                inactive-color="#d5dce6"
                @change="getBriefArticles"
        >
        </el-switch>
      </div>
      <el-table
              v-loading="loading"
              :data="briefArticles"
              border
              class="table"
              ref="multipleTable"
              header-cell-class-name="table-header"
              @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="title" label="标题" align="center"></el-table-column>
        <el-table-column prop="categoryName" label="所属分类" align="center"></el-table-column>
        <el-table-column label="文章状态" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status=== 1?'success':(scope.row.status=== 0?'danger':'warning')"
            >
              {{scope.row.status=== 1?'已发布':(scope.row.status=== 0?'回收站':'草稿箱')}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="enableAdmin" label="所属用户" align="center">
          <template slot-scope="scope">
            <el-tooltip effect="light" placement="right-end">
              <div slot="content">UID:{{scope.row.userId}}
              </div>
              <el-button
                      type="text"
                      icon="el-icon-user"
                      size="mini"
                      style="color: #6ffcff"
                      @click="selectByUserId(scope.row.userId)"
              >{{scope.row.nickname}}
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="enableComment" label="允许评论" width="60px" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status=== 1?'success':(scope.row.status=== 0?'danger':'warning')"
            >
              {{scope.row.enableComment=== 1?'是':'否'}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="views" label="浏览量" width="60px" align="center"></el-table-column>

        <el-table-column prop="updateTime" label="最近编辑时间" align="center"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click="handleEdit(scope.$index, scope.row)"
            >{{scope.row.status!=0?'修改':'重新发布'}}
            </el-button>
            <el-button
                    type="text"
                    icon="el-icon-delete"
                    class="red"
                    @click="handleDelete(scope.$index, scope.row)"
            >{{scope.row.status!=0?'删除':'彻底删除'}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
                background
                layout="total, prev, pager, next"
                :current-page="query.curPage"
                :page-size="query.pageSize"
                :total="pageTotal"
                @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

  </div>

</template>

<script>
  import { deleteRequest, getRequest, putRequest } from '@/axios';

  export default {
    name: 'Article',
    data() {
      return {
        briefArticles: [],
        query: {
          status: -1,
          keyword: '',
          curPage: 1,
          pageSize: 7
        },

        multipleSelection: [],
        deleteArticleIds: [],
        pageTotal: 0,
        loading: false,


        enableAdmin: false,
        adminSelectUserId: -1

      };
    },

    created() {
      this.getBriefArticles();
    },

    computed: {
      refresh() {
        return this.$store.state.refresh;
      }
    },

    watch: {
      refresh() {
        if (this.refresh != -1) {
          this.query.curPage = 1;
          this.query.status = -1;
          this.query.keyword = '';
          this.getBriefArticles();
          this.$store.commit('refresh', -1);
        }
      }
    },


    methods: {
      getBriefArticles() {
        this.loading = true;
        getRequest(this.enableAdmin ? '/admin/all/article/?userId=' + this.adminSelectUserId + '&' : '/article/all', this.query).then(resp => {
          if (resp.status == 200) {
            this.loading = false;
            this.briefArticles = resp.data.list;
            this.pageTotal = resp.data.total;
          } else {
            this.loading = false;
          }
        }, resp => {
          this.loading = false;
        }).catch(err => {
          this.loading = false;
        });
      },

      handlePageChange(val) {
        this.$set(this.query, 'curPage', val);
        this.getBriefArticles();
      },

      handleSearch() {
        this.getBriefArticles();
      },

      selectByUserId(userId) {
        this.adminSelectUserId = userId;
        this.getBriefArticles();
      },

      handleClear() {
        this.query.keyword = '';
        this.query.curPage = 1;
        this.query.status = -1;
        this.adminSelectUserId = -1;
        this.getBriefArticles();
      },

      changeStatus() {
        this.query.curPage = 1;
        this.getBriefArticles();
      },

      handleSelectionChange(val) {
        this.multipleSelection = val;
      },

      deleteAll() {
        this.$confirm(this.query.status != 0 ? '将这些文章放入回收站，是否继续?' : '永久删除这些文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const length = this.multipleSelection.length;
          this.deleteArticleIds = [];
          for (let i = 0; i < length; i++) {
            this.deleteArticleIds.push(this.multipleSelection[i].articleId);
          }
          this.delSelection(this.query.status);
          this.deleteArticleIds = [];
        }).catch(() => {
        });
      },

      handleDelete(index, row) {
        this.$confirm(row.status != 0 ? '将该文章放入回收站，是否继续?' : '永久删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteArticleIds = [];
          this.deleteArticleIds.push(row.articleId);
          this.delSelection(row.status);
          this.deleteArticleIds = [];
        }).catch(() => {
        });
      },

      handleEdit(index, row) {
        this.$confirm(row.status != 0 ? '修改该文章，是否继续?' : '重新发布该文章 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (row.status != 0) {
            this.$store.commit('edit', row.articleId);
            this.$router.push('/manage/writing');
          } else {
            putRequest('/article/regain', {
              articleId: row.articleId,
              articleTags: row.articleTags
            }).then(resp => {
              if (resp.status == 200) {
                var data = resp.data;
                if (data.status == 'success') {
                  this.getBriefArticles();
                }
              }
            })
          }
        })
      },

      delSelection(status) {
        if (status != 0) {
          putRequest('/article/trash', { articleIds: this.deleteArticleIds }).then(resp => {
            if (resp.status == 200) {
              var data = resp.data;
              if (data.status == 'success') {
                this.getBriefArticles();
              }
            }
          })
        } else {
          deleteRequest('/article/', { articleIds: this.deleteArticleIds }).then(resp => {
            if (resp.status == 200) {
              var data = resp.data;
              if (data.status == 'success') {
                this.getBriefArticles();
              }
            }
          })
        }
      }


    }
  };
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }

  .table {
    width: 100%;
    font-size: 14px;
  }

  .red {
    color: #ff0000;
  }

  .mr10 {
    margin-right: 10px;
  }

  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
</style>
