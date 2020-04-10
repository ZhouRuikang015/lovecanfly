import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state:{
        //管理页sidebar和header交互
        collapse:false,

        //管理页tags和home交互
        tagsList: [],

        //管理页article与writing交互
        editArticleId: -1,
        refresh:-1,

        user: {
            userId: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).userId,
            username: window.localStorage.getItem('user' || '[]') == null ? 'NotLogin' : JSON.parse(window.localStorage.getItem('user' || '[]')).username,
            nickname: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).nickname,
            gender: window.localStorage.getItem('user' || '[]') == null ? 1 : JSON.parse(window.localStorage.getItem('user' || '[]')).gender,
            roles: window.localStorage.getItem('user' || '[]') == null ? []: JSON.parse(window.localStorage.getItem('user' || '[]')).roles,
            email: window.localStorage.getItem('user' || '[]') == null ? '': JSON.parse(window.localStorage.getItem('user' || '[]')).email,
        },


        //前台搜索条件
        listSearchCondition: {
            condition:'',
            name:''
        },

        keyword:'',
        articleId: window.localStorage.getItem('articleId' || '[]') == null ? '':window.localStorage.getItem('articleId' || '[]'),

    },
    getters: {
        tagsList: state => {
            return state.tagsList.map(tag => tag.name);
        },

        nickname: state =>  {
            let nickname = state.user.nickname;
            return nickname ? nickname : '';
        },

        isAdmin: state => {
            return state.user.roles.find(role => role.roleName === 'ROLE_admin');
        }

    },

    mutations:{
        change(state){
            state.collapse = !state.collapse
        },

        changeTagsList(state,tagsList){
            state.tagsList = tagsList;
        },

        edit(state,editArticleId){
            state.editArticleId = editArticleId;
        },

        refresh(state,refresh){
            state.refresh = refresh;
        },

        login(state,user){
            state.user = user;
            window.localStorage.setItem('user', JSON.stringify(user));
        },

        logout(state){
            window.localStorage.removeItem('user');
            state.user= {};
        },

        getUser(state){
            return state.user;
        },

        changeCondition(state,s){
            state.listSearchCondition.condition = s.condition;
            state.listSearchCondition.name = s.name;
        },

        changeKeyword(state,keyword){
            state.keyword=keyword;
        },

        lookArticle(state,articleId){
            //防止刷新后state内数据清空
            window.localStorage.setItem('articleId', articleId);
            state.articleId = articleId;
        }
    }
});

export default store