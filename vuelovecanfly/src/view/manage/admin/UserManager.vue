<template>
  <div class="container">
    <div class="user-manager-body" v-loading="loading">
      <el-row style="margin-bottom: 10px">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" >
          <el-radio-group v-model="query.status" @change="getUsers()" style="margin-right: 40px;text-align: left;">
            <el-radio :label="1">已注册</el-radio>
            <el-radio :label="2">待审核</el-radio>
          </el-radio-group>
          <el-input v-model="query.nicknameKeyword" placeholder="通过用户昵称搜索" class="input-body"></el-input>
          <el-button type="primary" style="display: inline-block;" icon="el-icon-search" @click="getUsers()">搜索
          </el-button>
        </el-col>
      </el-row>


      <el-row :gutter="20">
        <el-col v-for="(user,index) in users" :xs="12" :sm="12" :md="12" :lg="6" :xl="6"
                :key="user.username"
                v-loading="singleLoadings[index]" style="margin-bottom:20px">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>{{user.nickname}}</span>
              <el-button @click="handleDelete(user.userId)"
                         class="delete-button"
                         icon="el-icon-delete"
                         type="text">删除
              </el-button>
            </div>
            <div class="form-body">
              <p class="pText">用户名：{{user.username}}</p>
              <p class="pText">性别：{{user.gender == 1? '男':'女'}}</p>
              <p class="pText">邮箱：{{user.email}}</p>
              <p class="pText">注册时间：{{user.registerTime}}</p>
              <div v-if="query.status!=2">
                <div style="margin-bottom: 15px">
                  <p class="pText" style="display: inline">
                    状态：
                  </p>
                  <el-switch
                          v-model="user.status"
                          style="display: inline-block;font-size: 12px"
                          active-text="启用"
                          inactive-text="封禁"
                          :active-value="1"
                          :inactive-value="0"
                          active-color="#13ce66"
                          inactive-color="#ff4949"
                          @change="changeStatus(user.userId,user.status,index,false)"
                  >
                  </el-switch>
                </div>
                <div style="margin-bottom: 15px">
                  <p class="pText" style="display: inline">
                    角色：
                  </p>
                  <el-tag v-for="(item,index) in user.roles"
                          :key="item.roleId"
                          :type="tagType[item.roleId -1]"
                          size="mini"
                          class="fa fa-user"
                          style="margin: 0px 2px"
                  > {{item.roleNameZh}}
                  </el-tag>

                  <el-popover
                          placement="right-end"
                          width="160"
                          @hide="changeRoles(roleIds,user.userId,index)"
                  >
                    <p class="pText">更改角色</p>
                    <el-select v-model="roleIds" multiple placeholder="请选择" size="mini">
                      <el-option
                              v-for="(item,index) in allRoles"
                              :key="item.roleId"
                              :label="item.roleNameZh"
                              :value="item.roleId">
                      </el-option>
                    </el-select>
                    <el-button type="text" size="mini" icon="fa fa-plus-square"
                               style="color: #f07724;" slot="reference" @click="showRoleIds(user.roles)"></el-button>
                  </el-popover>
                </div>
              </div>
              <div v-else>
                <el-button type="warning" icon="fa fa-futbol-o" @click="changeStatus(user.userId,1,index, true)"> 审核通过
                </el-button>
              </div>
            </div>

          </el-card>
        </el-col>


      </el-row>


    </div>
  </div>
</template>

<script>
  import { deleteRequest, getRequest, putRequest } from '@/axios';

  export default {
    name: 'UserManager',

    data() {
      return {
        query: {
          nicknameKeyword: '',
          status: 1,
          curPage: 1,
          pageSize: 12
        },

        loading: false,
        singleLoadings: [],

        users: [],

        tagType: ['danger', 'success', 'warning', 'primary'],

        allRoles: [],
        roleIds: []
      };
    },


    created() {
      this.singleLoadings = Array.apply(null, Array(12)).map(function(item, i) {
        return false;
      });
      this.getUsers();
      this.getAllRoles();
    },

    methods: {
      getUsers() {
        this.loading = true;
        getRequest('/admin/user/all', this.query).then(resp => {
          this.loading = false;
          if (resp.status == 200) {
            this.users = resp.data.list;
          }
        }, err => {
          this.loading = false;
        }).catch(err => {
          this.loading = false;
        });
      },


      getAllRoles() {
        getRequest('/admin/role/all').then(resp => {
          if (resp.status == 200) {
            this.allRoles = resp.data;
          }
        });
      },


      changeStatus(userId, status, index, isNewUser) {
        this.singleLoadings.splice(index, 1, true);
        putRequest('/admin/user/status', {
          userId: userId,
          status: status,
          isNewUser: isNewUser
        }).then(resp => {
          if (resp.status == 200) {
            this.singleLoadings.splice(index, 1, false);
            this.getUsers();
          }
        })
      },

      handleDelete(userId) {
        this.$confirm('删除该用户，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$confirm('该用户下的所有文章、分类、评论均会被删除，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'info'
          }).then(() => {
            deleteRequest('/admin/user/' + userId).then(resp => {
              if (resp.status == 200) {
                this.getUsers();
              }
            })
          })
        })
      },

      showRoleIds(roles) {
        this.roleIds = [];
        roles.forEach((item, index) => {
          this.roleIds.push(item.roleId);
        });
      },

      changeRoles(roleIds, userId, index) {

        if (roleIds.length == 0) {
          this.$message.error('角色不可为空');
          return false;
        }
        putRequest('/admin/user/roles', {
          roleIds: roleIds,
          userId: userId
        }).then(resp => {
          if (resp.status == 200) {
            this.getUserByUserId(userId, index);
          }
        })
      },

      getUserByUserId(userId, index) {
        //splice带刷新功能（体验得..
        this.singleLoadings.splice(index, 1, true);
        getRequest('/admin/user/' + userId).then(resp => {
          if (resp.status == 200) {
            this.singleLoadings.splice(index, 1, false);
            this.users.splice(index, 1, resp.data);
          }
        })
      }

    }
  };
</script>

<style scoped>

  .user-manager-body {
    padding: 0px 20px;
  }

  .input-body {
    width: 30%;
    display: inline-block;
    margin-right: 10px;
  }

  .delete-button {
    float: right;
    padding: 3px 0;
    color: red;
  }


  .form-body {
    padding: 10px;
  }

  .pText {
    margin-bottom: 15px;
    font-size: 14px;
  }
</style>
