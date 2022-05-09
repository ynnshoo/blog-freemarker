<#include "/inc/layout.ftlh"/>

<@layout "首页">

<#include "/inc/common/header-panel.ftlh"/>

<div class="layui-container">
    <div class="layui-row layui-col-space15">
<#--        <#include "/inc/common/left.ftlh"/>-->
        <div class="layui-col-md8">

            <div class="fly-panel" style="margin-bottom: 0;">

                <div class="fly-panel-title fly-filter">
                    <a href="" class="layui-this">综合</a>
                    <span class="fly-mid"></span>
                    <a href="">未结</a>
                    <span class="fly-mid"></span>
                    <a href="">已结</a>
                    <span class="fly-mid"></span>
                    <a href="">精华</a>
                    <span class="fly-filter-right layui-hide-xs">
            <a href="" class="layui-this">按最新</a>
            <span class="fly-mid"></span>
            <a href="">按热议</a>
          </span>
                </div>

                <ul class="fly-list">

                    <#list pageDetail.records as post>
                        <li>
                            <a href="/user/${post.authorId}" class="fly-avatar">
                                <img src="${post.authorAvatar}" alt="${post.authorName}"/>
                            </a>
                            <h2>
                                <a class="layui-badge">${post.categoryName}</a>
                                <a href="jie/detail.html">${post.title}</a>
                            </h2>
                            <div class="fly-list-info">
                                <a href="user/${post.authorId}" link>
                                    <cite>${post.authorName}</cite>
                                </a>
                                <span>${post.created?string("yyyy-MM-dd")}</span>
                                <span class="fly-list-nums"></span>
                                    <i class="iconfont icon-pinglun1" title="回答"></i>${post.commentCount}
                                </span>
                            </div>
                        </li>
                    </#list>
                </ul>

                <@paging pageDetail></@paging>

            </div>
        </div>
        <#include "/inc/common/right.ftlh" />
    </div>
</div>

</@layout>