(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c2c9b3c0"],{1511:function(e,t,a){"use strict";var n=a("b8c5"),l=a.n(n);l.a},"86f5":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"container"},[a("div",{staticClass:"handle-box"},[a("el-button",{staticClass:"handle-del mr10",attrs:{type:"primary",icon:"el-icon-delete"},on:{click:e.deleteAll}},[e._v("批量删除 ")]),a("el-select",{staticClass:"handle-select mr10",attrs:{placeholder:"文章位置"},on:{change:e.changeStatus},model:{value:e.query.status,callback:function(t){e.$set(e.query,"status",t)},expression:"query.status"}},[a("el-option",{key:"-1",attrs:{label:"全部文章",value:-1}}),a("el-option",{key:"1",attrs:{label:"已发布",value:1}}),a("el-option",{key:"0",attrs:{label:"回收站",value:0}}),a("el-option",{key:"2",attrs:{label:"草稿箱",value:2}})],1),a("el-input",{staticClass:"handle-input mr10",attrs:{placeholder:"通过标题搜索"},model:{value:e.query.keyword,callback:function(t){e.$set(e.query,"keyword",t)},expression:"query.keyword"}}),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.handleSearch}},[e._v("搜索")]),a("el-button",{attrs:{type:"info",plain:""},on:{click:e.handleClear}},[e._v("重置")]),this.$store.getters.isAdmin?a("el-switch",{staticStyle:{display:"inline-block","font-size":"12px",float:"right"},attrs:{"active-text":"开启管理员模式","active-color":"#13ce66","inactive-color":"#d5dce6"},on:{change:e.getBriefArticles},model:{value:e.enableAdmin,callback:function(t){e.enableAdmin=t},expression:"enableAdmin"}}):e._e()],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"multipleTable",staticClass:"table",attrs:{data:e.briefArticles,border:"","header-cell-class-name":"table-header"},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),a("el-table-column",{attrs:{prop:"title",label:"标题",align:"center"}}),a("el-table-column",{attrs:{prop:"categoryName",label:"所属分类",align:"center"}}),a("el-table-column",{attrs:{label:"文章状态",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:1===t.row.status?"success":0===t.row.status?"danger":"warning"}},[e._v(" "+e._s(1===t.row.status?"已发布":0===t.row.status?"回收站":"草稿箱")+" ")])]}}])}),e.enableAdmin?a("el-table-column",{attrs:{label:"所属用户",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tooltip",{attrs:{effect:"light",placement:"right-end"}},[a("div",{attrs:{slot:"content"},slot:"content"},[e._v("UID:"+e._s(t.row.userId)+" ")]),a("el-button",{staticStyle:{color:"#6ffcff"},attrs:{type:"text",icon:"el-icon-user",size:"mini"},on:{click:function(a){return e.selectByUserId(t.row.userId)}}},[e._v(e._s(t.row.nickname)+" ")])],1)]}}],null,!1,2371404848)}):e._e(),a("el-table-column",{attrs:{prop:"enableComment",label:"允许评论",width:"60px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:1===t.row.status?"success":0===t.row.status?"danger":"warning"}},[e._v(" "+e._s(1===t.row.enableComment?"是":"否")+" ")])]}}])}),a("el-table-column",{attrs:{prop:"views",label:"浏览量",width:"60px",align:"center"}}),a("el-table-column",{attrs:{prop:"updateTime",label:"最近编辑时间",align:"center"}}),a("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",icon:"el-icon-edit"},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v(e._s(0!=t.row.status?"修改":"重新发布")+" ")]),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){return e.handleDelete(t.$index,t.row)}}},[e._v(e._s(0!=t.row.status?"删除":"彻底删除")+" ")])]}}])})],1),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next","current-page":e.query.curPage,"page-size":e.query.pageSize,total:e.pageTotal},on:{"current-change":e.handlePageChange}})],1)],1)])},l=[],i=a("eeb9"),s={name:"Article",data:function(){return{briefArticles:[],query:{status:-1,keyword:"",curPage:1,pageSize:7},multipleSelection:[],deleteArticleIds:[],pageTotal:0,loading:!1,enableAdmin:!1,adminSelectUserId:-1}},created:function(){this.getBriefArticles()},computed:{refresh:function(){return this.$store.state.refresh}},watch:{refresh:function(){-1!=this.refresh&&(this.query.curPage=1,this.query.status=-1,this.query.keyword="",this.getBriefArticles(),this.$store.commit("refresh",-1))}},methods:{getBriefArticles:function(){var e=this;this.loading=!0,Object(i["b"])(this.enableAdmin?"/admin/all/article/?userId="+this.adminSelectUserId+"&":"/article/all",this.query).then((function(t){200==t.status?(e.loading=!1,e.briefArticles=t.data.list,e.pageTotal=t.data.total):e.loading=!1}),(function(t){e.loading=!1})).catch((function(t){e.loading=!1}))},handlePageChange:function(e){this.$set(this.query,"curPage",e),this.getBriefArticles()},handleSearch:function(){this.getBriefArticles()},selectByUserId:function(e){this.adminSelectUserId=e,this.getBriefArticles()},handleClear:function(){this.query.keyword="",this.query.curPage=1,this.query.status=-1,this.adminSelectUserId=-1,this.getBriefArticles()},changeStatus:function(){this.query.curPage=1,this.getBriefArticles()},handleSelectionChange:function(e){this.multipleSelection=e},deleteAll:function(){var e=this;this.$confirm(0!=this.query.status?"将这些文章放入回收站，是否继续?":"永久删除这些文章, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){var t=e.multipleSelection.length;e.deleteArticleIds=[];for(var a=0;a<t;a++)e.deleteArticleIds.push(e.multipleSelection[a].articleId);e.delSelection(e.query.status),e.deleteArticleIds=[]})).catch((function(){}))},handleDelete:function(e,t){var a=this;this.$confirm(0!=t.status?"将该文章放入回收站，是否继续?":"永久删除该文章, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){a.deleteArticleIds=[],a.deleteArticleIds.push(t.articleId),a.delSelection(t.status),a.deleteArticleIds=[]})).catch((function(){}))},handleEdit:function(e,t){var a=this;this.$confirm(0!=t.status?"修改该文章，是否继续?":"重新发布该文章 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){0!=t.status?(a.$store.commit("edit",t.articleId),a.$router.push("/manage/writing")):Object(i["d"])("/article/regain",{articleId:t.articleId,articleTags:t.articleTags}).then((function(e){if(200==e.status){var t=e.data;"success"==t.status&&a.getBriefArticles()}}))}))},delSelection:function(e){var t=this;0!=e?Object(i["d"])("/article/trash",{articleIds:this.deleteArticleIds}).then((function(e){if(200==e.status){var a=e.data;"success"==a.status&&t.getBriefArticles()}})):Object(i["a"])("/article/",{articleIds:this.deleteArticleIds}).then((function(e){if(200==e.status){var a=e.data;"success"==a.status&&t.getBriefArticles()}}))}}},r=s,c=(a("1511"),a("2877")),o=Object(c["a"])(r,n,l,!1,null,"0dfba9b1",null);t["default"]=o.exports},b8c5:function(e,t,a){}}]);
//# sourceMappingURL=chunk-c2c9b3c0.f4bf6adb.js.map