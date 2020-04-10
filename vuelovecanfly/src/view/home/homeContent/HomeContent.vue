<template>
  <div>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="16">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </el-col>
      <el-col :xs="24" :sm="8">
        <blog-link></blog-link>
        <category-list></category-list>
        <tag-cloud></tag-cloud>
      </el-col>

    </el-row>

  </div>
</template>

<script>
  import BlogLink from "@/components/home/homeContent/BlogLink";
  import CategoryList from "@/components/home/homeContent/CategoryList";
  import TagCloud from "@/components/home/homeContent/TagCloud";

  export default {
    name: 'HomeContent',
    components: { TagCloud, CategoryList, BlogLink }
  };
</script>

<style scoped>

</style>
