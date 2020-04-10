<template>
    <div class="wrapper">
        <manage-header></manage-header>
        <manage-sidebar></manage-sidebar>
        <div class="content-box" :class="{'content-collapse':this.$store.state.collapse}">
            <manage-tags></manage-tags>
            <div class="content scrollbar">
                <transition name="move" mode="out-in">
                    <keep-alive :include="this.$store.getters.tagsList">
                        <router-view></router-view>
                    </keep-alive>
                </transition>
                <el-backtop  :right="50" target=".content"></el-backtop>
            </div>
        </div>
    </div>
</template>

<script>

    import ManageHeader from '@/components/manage/Header'
    import ManageSidebar from '@/components/manage/Sidebar'
    import ManageTags from '@/components/manage/Tags'

    export default {
        components: {
            ManageHeader,
            ManageSidebar,
            ManageTags
        }
    };
</script>

<style scoped>
    .wrapper{
        background-image: url(../../assets/img/manage_bg.jpg);
        background-repeat:no-repeat;
        background-size:100% 100%;
        -moz-background-size:100% 100%;
    }
</style>
