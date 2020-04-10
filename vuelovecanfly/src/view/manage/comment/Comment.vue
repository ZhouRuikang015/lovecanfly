<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.titleKeyword" placeholder="通过文章标题搜索" class="handle-input mr10"></el-input>
        <el-input v-model="query.contentKeyword" placeholder="通过评论搜索" class="handle-input mr10"></el-input>
        <el-select @change="changeSelection" v-model="query.status" placeholder="评论状态" class="handle-select mr10">
          <el-option key="-1" label="全部评论" :value="-1"></el-option>
          <el-option key="1" label="已发布" :value="1"></el-option>
          <el-option key="2" label="待审核" :value="2"></el-option>
          <el-option key="0" label="回收站" :value="0"></el-option>
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button type="info" plain @click="handleClear">重置</el-button>
        <el-switch
                v-if="this.$store.getters.isAdmin"
                v-model="enableAdmin"
                style="display: inline-block;font-size: 12px;float: right;"
                active-text="开启管理员模式"
                active-color="#13ce66"
                inactive-color="#d5dce6"
                @change="getComments"
        >
        </el-switch>
      </div>
      <el-table
              v-loading="loading"
              :data="comments"
              border
              class="table"
              ref="multipleTable"
              header-cell-class-name="table-header"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="commentatorName" label="昵称" width="100" align="left">
          <template slot-scope="scope">
            <el-tooltip  effect="light"  placement="right-end" >
              <div slot="content">name:{{scope.row.commentatorName}}<br/>
                email:{{scope.row.commentatorEmail}}<br/>
                location:{{scope.row.commentatorLocation}}
              </div>
              <el-button  type="text" icon="el-icon-user" >{{scope.row.commentatorName}}</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="commentContent" label="内容" align="center"></el-table-column>
        <el-table-column label="状态" align="center" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status=== 1?'success':(scope.row.status=== 0?'danger':'warning')"
            >
              {{scope.row.status=== 1?'已发布':(scope.row.status=== 0?'回收站':'待审核')}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="评论文章" align="center"></el-table-column>
        <el-table-column v-if="enableAdmin"  label="文章作者" align="center">
          <template slot-scope="scope">
            <el-tooltip  effect="light"  placement="right-end" >
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
        <el-table-column prop="commentCreateTime" label="评论时间" align="center"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click="handleEdit(scope.$index, scope.row)"
            >{{scope.row.status == 2 ? '通过': scope.row.status == 0?'还原':scope.row.replyContent!=''?'查看回复':'回复'}}
            </el-button>
            <el-button
                    type="text"
                    icon="el-icon-delete"
                    style="color: red"
                    @click="handleDelete(scope.$index, scope.row)"
            >{{scope.row.status!=0?'回收站':'删除'}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
                style="display: inline-block; padding: 0px"
                background
                layout="total, prev, pager, next"
                :current-page="query.curPage"
                :page-size="query.pageSize"
                :total="pageTotal"
                @current-change="handlePageChange"
        ></el-pagination>
        <el-select @change="changeSelection" v-model="query.pageSize" placeholder="评论状态" class="handle-select">
          <el-option key="0" label="5条/页" :value="5"></el-option>
          <el-option key="1" label="7条/页" :value="7"></el-option>
          <el-option key="2" label="10条/页" :value="10"></el-option>
          <el-option key="3" label="20条/页" :value="20"></el-option>
          <el-option key="4" label="50条/页" :value="50"></el-option>
        </el-select>
      </div>

      <el-dialog :title="'回复：'+commentatorName" :visible.sync="edit" width="30%">
        <el-input
                type="textarea"
                :rows="8"
                placeholder=""
                v-model=replyContent
                maxlength="150"
                show-word-limit
        >
        </el-input>
        <p v-if="needEdit" style="margin-top: 20px;text-align: right">{{'回复于：'+ replyCreateTime}}</p>
        <el-button style="margin-top: 20px" type="info" plain @click="replyContent=''">重 置</el-button>
        <el-button type="primary" @click="saveEdit">{{needEdit?'修 改':'确 定'}}</el-button>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import { deleteRequest, getRequest, putRequest } from '@/axios';
  import { getFormatTime } from '@/utils/timeFormat';


export default {
    name: 'Comment',
    data() {
      return {
        query: {
          titleKeyword: '',
          contentKeyword: '',
          status: -1,
          curPage: 1,
          pageSize: 7
        },

        comments: [],

        loading: false,
        pageTotal: 0,

        edit: false,
        needEdit: false,
        commentatorName: '',
        replyContent: '',
        replyCreateTime: '',

        enableAdmin: false,
        adminSelectUserId: -1
      }
    },

    created() {
      this.getComments();
    },

    methods: {
      getComments() {
        this.loading = true;
        getRequest(this.enableAdmin?'/admin/all/comment/?userId='+ this.adminSelectUserId + '&':'/comment/all', this.query).then(resp => {
          this.loading = false;
          if (resp.status == 200) {
            this.comments = resp.data.list;
            this.pageTotal = resp.data.total;
          }
        }, err => {
          this.loading = false;
        }).catch(err => {
          this.loading = false;
          this.$message.error('操作失败');
        });
      },

      handleSearch(){
        this.query.curPage = 1;
        this.getComments();
      },

      selectByUserId(userId) {
        this.adminSelectUserId = userId;
        this.getComments();
      },

      handleClear(){
        this.query.titleKeyword = '';
        this.query.contentKeyword = '';
        this.query.status = -1;
        this.query.curPage = 1;
        this.adminSelectUserId = -1;
        this.getComments();
      },

      changeSelection() {
        this.query.curPage = 1;
        this.getComments();
      },

      handlePageChange(val) {
        this.$set(this.query, 'curPage', val);
        this.getComments();
      },

      handleEdit(index, row) {
        if (row.status != 1) {
          this.changeStatus(row.commentId,1);
        }else{
          this.edit = true;
          if (row.replyContent){
            this.needEdit = true;
            this.replyCreateTime = getFormatTime(row.replyCreateTime);
            this.replyContent = row.replyContent;
          }else{
            this.replyContent = '';
            this.needEdit = false;
          }
          this.commentId = row.commentId;
          this.commentatorName = row.commentatorName;
        }
      },

      saveEdit() {
        putRequest('/comment/reply/', {
          commentId: this.commentId,
          replyContent: this.replyContent
        }).then(resp => {
          if (resp.status == 200) {
            if (resp.data.status == 'success') {
              this.edit = false;
              this.replyContent = '';
              this.getComments();
            }
          }
        })
      },

      handleDelete(index, row) {
        if (row.status == 0) {
          deleteRequest('/comment/', {
            commentId: row.commentId
          }).then(resp => {
            if (resp.status == 200) {
              if (resp.data.status == 'success') {
                this.getComments();
              }
            }
          })
        } else {
          this.changeStatus(row.commentId, 0);
        }
      },


      changeStatus(commentId, status) {
        putRequest('/comment/status', {
          commentId:commentId,
          status:status
        }).then(resp => {
          if (resp.status == 200) {
            if (resp.data.status == 'success') {
              this.getComments();
            }
          }
        })
      }

    }

  }

</script>

<style scoped>
  .handle-input {
    width: 250px;
    display: inline-block;
  }

  .mr10 {
    margin-right: 10px;
    margin-bottom: 10px;
  }

  .handle-select {
    width: 100px;
  }

</style>
