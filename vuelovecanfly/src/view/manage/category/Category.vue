<template>
  <div>
    <div>
      <div class="container" style="padding: 15px 30px">
        <div class="handle-box">
          <el-row  style="margin-bottom: 15px;width: 100%">
            <el-switch
                    v-if="this.$store.getters.isAdmin"
                    v-model="enableAdmin"
                    style="display: inline-block;font-size: 12px;float: right"
                    active-text="开启管理员模式"
                    active-color="#13ce66"
                    inactive-color="#d5dce6"
                    @change="getCategories"
            >
            </el-switch>
          </el-row>

          <el-row :gutter="15">
            <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
              <div class="container-self">
                <div class="form-header">
                  <p>添加分类</p>
                </div>
                <div class="form-body">
                  <p class="pText">名称*：</p>
                  <el-input class="input-d mr" v-model="categoryName" placeholder="请输入分类名称"></el-input>


                  <p class="pText">描述：</p>
                  <el-input
                          type="textarea"
                          :rows="5"
                          placeholder="请输入描述信息"
                          v-model="categoryDescribe"
                          maxlength="150"
                          show-word-limit
                  >
                  </el-input>
                  <div style="margin-top: 20px">
                    <div v-if=!edit>
                      <el-button type="primary" @click="handleSave()">保存</el-button>
                    </div>
                    <div v-else>
                      <el-button type="primary" @click="handleSave()">更新</el-button>
                      <el-button type="info" plain @click="handleCancer">取消修改</el-button>
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :xs="16" :sm="16" :md="16" :lg="16" :xl="16">
              <div class="container-self">
                <div class="form-header">
                  <p style="display: inline-block">分类列表
                    <el-select @change="changeStatus" v-model="query.status" placeholder="文章位置" class="select-self">
                      <el-option key="-1" label="全部文章" :value="-1"></el-option>
                      <el-option key="1" label="已发布" :value="1"></el-option>
                      <el-option key="0" label="回收站" :value="0"></el-option>
                      <el-option key="2" label="草稿箱" :value="2"></el-option>
                    </el-select>
                  </p>
                  <el-button type="info" plain @click="handleClear" style="margin-left: 10px" >重置</el-button>
                </div>
                <div class="form-body">
                  <el-table
                          v-loading="loading"
                          :data="categories"
                          border
                          class="table"
                          ref="multipleTable"
                          header-cell-class-name="table-header"
                  >
                    <el-table-column prop="categoryName" label="类名" align="center"></el-table-column>
                    <el-table-column label="文章数" align="center">
                      <template slot-scope="scope">
                        <el-tag type="warning"
                        >
                          {{scope.row.articleNums}}
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
                    <el-table-column prop="categoryDescribe" label="描述" align="center"></el-table-column>
                    <el-table-column label="操作" width="180" align="center">
                      <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"
                        >修改
                        </el-button>
                        <el-button
                                v-if="query.status == -1 && scope.row.articleNums == 0"
                                type="text"
                                icon="el-icon-delete"
                                style="color: red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除
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
            </el-col>
          </el-row>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
  import { deleteRequest, getRequest, postRequest, putRequest } from '@/axios';

  export default {
    name: 'Category',
    data() {
      return {
        categoryId: -1,
        categoryName: '',
        categoryDescribe: '',

        categories: [],

        query: {
          curPage: 1,
          pageSize: 10,
          status: -1
        },

        pageTotal: -1,

        loading: false,
        edit: false,

        enableAdmin: false,
        adminSelectUserId : -1
      };
    },

    created() {
      this.getCategories();
    },

    methods: {
      getCategories() {
        this.loading = true;
        getRequest(this.enableAdmin ? '/admin/all/category/?userId='+ this.adminSelectUserId + '&':'/category/all', this.query).then(resp => {
          if (resp.status == 200) {
            this.loading = false;
            this.categories = resp.data.list;
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

      selectByUserId(userId) {
        this.adminSelectUserId = userId;
        this.getCategories();
      },

      handleClear() {
        this.query.curPage = 1;
        this.query.status = -1;
        this.adminSelectUserId = -1;
        this.getCategories();
      },

      changeStatus() {
        this.query.curPage = 1;
        this.getCategories();
      },

      handlePageChange(val) {
        this.$set(this.query, 'curPage', val);
        this.getCategories();
      },

      handleSave() {
        if (this.categoryId != -1) {
          putRequest('/category/', {
            categoryId: this.categoryId,
            categoryName: this.categoryName,
            categoryDescribe: this.categoryDescribe
          }).then(resp => {
            if (resp.status == 200) {
              this.getCategories();
              this.categoryId = -1;
              this.categoryName = '';
              this.categoryDescribe = '';
              this.edit = false;
            }
          })

        } else {
          if (this.enableAdmin){
            this.$message.error('管理员模式下无法添加分类哦');
            this.categoryName = '';
            this.categoryDescribe = '';
            return false;
          }
          postRequest('/category/', {
            categoryName: this.categoryName,
            categoryDescribe: this.categoryDescribe
          }).then(resp => {
            if (resp.status == 200) {
              this.getCategories();
              this.categoryName = '';
              this.categoryDescribe = '';
            }
          })
        }
      },


      handleEdit(index, row) {
        this.categoryId = row.categoryId;
        this.categoryName = row.categoryName;
        this.categoryDescribe = row.categoryDescribe;
        this.edit = true;
      },

      handleCancer() {
        this.categoryId = -1;
        this.categoryName = '';
        this.categoryDescribe = '';
        this.edit = false;
      },

      handleDelete(index, row) {
        this.$confirm('删除此分类，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRequest('/category/', {
            categoryId: row.categoryId
          }).then(resp => {
            if (resp.status == 200) {
              this.getCategories();
            }
          })
        })
      }
    }

  };
</script>

<style scoped>
  .container-self {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    border-radius: 6px;
  }

  .form-header {
    padding: 12px 30px;
    border-bottom: 1px solid #d0d0d0;
  }

  .form-body {
    padding: 20px 30px 40px 30px;
  }

  .pText {
    margin: 25px 0px;
    font-size: 14px;
  }

  .select-self {
    margin-left: 30px;
    height: 100%;
    width: 150px;
  }
</style>
