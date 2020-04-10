<template>
  <div>
    <div class="container" v-loading="loading">
      <el-input class="input-d mr" v-model="title" placeholder="请输入标题名称*"></el-input>
      <el-select v-model="categoryId" placeholder="选择分类*" class="selection mr">
        <el-option
                v-for="item in categories"
                :key="item.categoryId" :label="item.categoryName" :value="item.categoryId">
        </el-option>
      </el-select>
      <el-tag
              size="medium"
              :key="tag"
              v-for="tag in articleTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
        {{tag}}
      </el-tag>
      <el-input
              class="input-new-tag "
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
      >
      </el-input>
      <el-button type="primary" v-else class="button-new-tag" size="small" @click="showInput">+ Tag</el-button>
      <mavon-editor v-model="content" ref="md" @imgAdd="$imgAdd" @change="change" style="min-height: 600px"/>
      <el-button round class="editor-btn button-self" @click="changeDrawer()" type="warning">
        提交<i class="el-icon-arrow-right el-icon--right"></i>
      </el-button>
    </div>

    <el-drawer size="34%" title="文章设置" :visible.sync="drawer" direction="rtl">
      <div class="drawer-box">
        <div class="drawer-body">
          <p class="pText">开启评论*：</p>
          <el-radio-group v-model="enableComment">
            <el-radio :label="1">允许</el-radio>
            <el-radio :label="0">禁止</el-radio>
          </el-radio-group>

          <p class="pText">更新时间*：</p>
          <el-date-picker
                  v-model="updateTime"
                  type="datetime"
                  placeholder="请指定时间"
                  value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>

          <el-divider></el-divider>
          <p class="pText">摘要：</p>
          <el-input
                  type="textarea"
                  :rows="5"
                  placeholder="请输入内容"
                  v-model="summary"
                  maxlength="150"
                  show-word-limit
          >
          </el-input>
          <div class="footer">
            <div class="footer-body">
              <el-button size="small" @click="saveArticle(2)">存草稿</el-button>
              <el-button size="small" type="primary" @click="saveArticle(1)">发布</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
  import { mavonEditor } from 'mavon-editor';
  import 'mavon-editor/dist/css/index.css';

  import { getRequest, postRequest, uploadFileRequest } from '@/axios';

  export default {
    name: 'Writing',
    data: function() {
      return {

        articleId: -1,
        title: '',
        content: '',
        html: '',
        categoryId: '',
        articleTags: [],
        summary: '',
        enableComment: 1,
        createTime: '',
        updateTime: '',
        status: -1,
        stat: '',

        categories: [],
        initTime: '',

        inputVisible: false,
        inputValue: '',
        drawer: false,

        loading:false
      };
    },
    components: {
      mavonEditor
    },

    created() {
      this.edit();
      this.getCategories();
    },

    computed: {
      editArticleId() {
        return this.$store.state.editArticleId;
      }
    },
    watch: {
      editArticleId() {
        if (this.editArticleId != -1) {
          this.edit();
        }
      }
    },

    methods: {

      edit() {
        if (this.editArticleId != -1) {
          this.$message.warning('修改加载中...');
          this.loading = true;
          getRequest('/article/' + this.editArticleId).then(resp => {
            if (resp.status == 200) {
              this.loading = false;
              const data = resp.data;
              this.articleId = data.articleId;
              this.title = data.title;
              this.html = data.html;
              this.content = data.content;
              this.categoryId = data.categoryId;
              this.articleTags = data.articleTags.length != 0 ? data.articleTags.split(',') : '';
              this.createTime = data.createTime;
              this.updateTime = data.updateTime;
              this.summary = data.summary;
              this.enableComment = data.enableComment;
              this.status = data.status;
            }
          },err =>{
            this.loading = false;
          }).catch(err => {
            this.loading = false;
          });
          this.$store.commit('edit', -1);
        }
      },


      getCategories() {
        getRequest('/category/all').then(resp => {
          if (resp.status == 200) {
            this.categories = resp.data.list;
          }
        })
      },

      // 将图片上传到服务器，返回地址替换到md中
      $imgAdd(pos, $file) {
        this.$message.warning('上传图片中');
        var formdata = new FormData();
        formdata.append('file', $file);
        uploadFileRequest('/article/uploadImage', formdata).then(resp => {
          if (resp.status == 200 && resp.data.status == 'success') {
            this.$message.success('上传成功');
            this.$refs.md.$img2Url(pos, resp.data.msg);
          }
        })
      },


      change(value, render) {
        // render 为 markdown 解析后的结果
        this.html = render;
      },

      //抽屉设置
      changeDrawer() {
        this.drawer = true;
      },

      handleClose(tag) {
        this.articleTags.splice(this.articleTags.indexOf(tag), 1);
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.articleTags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },


      saveArticle(stat) {
        this.$confirm(stat != 1 ? '保存草稿，是否继续?' : '发布该文章 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (stat == 2 && this.status == 1) {
            this.$message.error('已发布文章不可再存草稿箱哦');
            return;
          }
          this.stat = stat;
          this.$message.warning((stat == 2) ? '保存中...' : '发布中...');
          this.loading =true;
          postRequest('/article/', {
            articleId: this.articleId,
            title: this.title,
            content: this.content,
            html: this.html,
            categoryId: this.categoryId,
            articleTags: this.articleTags.join(),
            enableComment: this.enableComment,
            createTime: this.createTime,
            updateTime: this.updateTime,
            summary: this.summary,
            status: this.status,
            stat: this.stat
          }).then(resp => {
            this.loading = false;
            if (resp.status == 200 && resp.data.status == 'success') {
              this.drawer = false;
              this.$router.push('/manage/article');
              this.$store.commit('refresh', 1);
              this.articleId = -1;
              this.title = '';
              this.content = '';
              this.html = '';
              this.categoryId = '';
              this.articleTags = [];
              this.summary = '';
              this.enableComment = 1;
              this.createTime = '';
              this.updateTime = '';
              this.status = -1;
              this.stat = '';
            }
          },err => {
            this.loading = false;
          }).catch(err=> {
            this.loading = false;
          });
        }).catch(() => {
        });
      }
    }


  };
</script>

<style scoped>
  .editor-btn {
    margin-top: 25px;
    font-size: 14px;
    text-align: center;
  }

  .button-self {
    position: relative;
    left: 89%;
  }

  .input-d {
    width: 300px;
    margin-bottom: 20px;
    display: inline-block;
  }

  .mr {
    margin-right: 10px;
  }

  .selection {
    width: 150px;
  }

  .header {
    width: 100%;
    height: 30px;
    padding: 0 10px;
    border-left: 10px solid #a3be8c;
    font-size: 12px;
    line-height: 30px;
    color: #eff1f5;
    background: #343d46;
    overflow: hidden;
  }

  .drawer-box {
    border-top: 1px solid rgba(208, 208, 208, 0.91);
    margin: 0px;
  }


  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-left: 10px;
  }


  .footer {
    margin-top: 25%;
    width: 100%;
  }

  .footer-body {
    float: right;
  }

  .pText {
    margin: 25px 0px;
    font-size: 14px;
  }

  .drawer-body {
    margin: 0px;
    padding: 0px 20px 0px 20px;
  }
</style>
