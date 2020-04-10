import Vue from 'vue';
import Router from 'vue-router';

//个人页面
import Manage from '@/view/manage/Manage';

//广场页面
import Home from "@/view/home/Home";
import HomeContent from "@/view/home/homeContent/HomeContent";
import BriefArticle from "@/view/home/homeContent/home/BriefArticle";
import TimeLine from "@/view/home/homeContent/archive/TimeLine";
import ArticlePage from "@/view/home/articlePage/ArticlePage";


Vue.use(Router);

export default new Router({
    //如无 hidden，原因参考sidebar menu 处源码
    routes: [
        {
            path: '/',
            redirect: '/home/homeContent/briefArticles',
            hidden: true,
            meta: {
                requireAuth: false,
            }
        },

        {
            path: '/home',
            component: Home,
            hidden: true,
            children: [
                {
                    path: '/home/homeContent',
                    name: 'homeContent',
                    component: HomeContent,
                    children: [
                        {
                            path: '/home/homeContent/briefArticles',
                            name: 'briefArticle',
                            component: BriefArticle,
                            meta: {
                                requireAuth: false,
                            },
                        },

                        {
                            path: '/home/homeContent/timeLine',
                            name: 'timeLine',
                            component: TimeLine,
                            meta: {
                                requireAuth: false,
                            },
                        }
                    ],
                    meta: {
                        requireAuth: false,
                    },
                },

                {
                    path: '/home/articlePage',
                    name: 'articlePage',
                    component: ArticlePage,
                    meta: {
                        requireAuth: false,
                    },
                }
            ],
            meta: {
                requireAuth: false,
            },
        },

        {
            path: '/manage',
            redirect: '/manage/dashboard',
            hidden: true,
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin','ROLE_user']
            }
        },


        {
            path: '/manage',
            component: Manage,
            children: [
                {
                    path: '/manage/userManager',
                    name: '用户管理',
                    iconCls: 'el-icon-user',
                    component: () => import('@/view/manage/admin/UserManager'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin']
                    }
                }
            ],
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin']
            }
        },

        {
            path: '/manage',
            component: Manage,
            children: [
                {
                    path: '/manage/dashboard',
                    name: '仪表盘',
                    iconCls: 'el-icon-help',
                    component: () => import('@/view/manage/dashboard/Dashboard'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin','ROLE_user']
                    },
                },
            ],
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin','ROLE_user']
            }
        },

        {
            path: '/manage',
            name: '文章管理',
            iconCls: 'el-icon-edit-outline',
            component: Manage,
            children: [
                {
                    path: '/manage/writing',
                    name: '写文章',
                    component: () => import('@/view/manage/article/Writing'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin','ROLE_user']
                    }
                },
                {
                    path: '/manage/article',
                    name: '所有文章',
                    component: () => import('@/view/manage/article/Article'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin','ROLE_user']
                    }
                }
            ],
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin','ROLE_user']
            },
        },

        {
            path: '/manage',
            component: Manage,
            children: [
                {
                    path: '/manage/category',
                    name: '分类目录',
                    iconCls: 'el-icon-mobile',
                    component: () => import('@/view/manage/category/Category'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin','ROLE_user']
                    },
                },
            ],
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin','ROLE_user']
            }
        },

        {
            path: '/manage',
            component: Manage,
            children: [
                {
                    path: '/manage/comment',
                    name: '评论管理',
                    iconCls: 'el-icon-chat-dot-round',
                    component: () => import('@/view/manage/comment/Comment'),
                    meta: {
                        requireAuth: true,
                        roles: ['ROLE_admin', 'ROLE_user']
                    },
                },
            ],
            meta: {
                requireAuth: true,
                roles: ['ROLE_admin','ROLE_user']
            }
        },

        {
            path: '/login',
            name: 'Login',
            hidden: true,
            component: () => import('@/view/Login'),
            meta: {
                requireAuth: false,
            },
        }

    ]
});
