<#macro paging pageDetail>
    <div style="text-align: center">
        <div id="laypage-main">

        </div>
        <script>
            layui.use('laypage', function(){
                var laypage = layui.laypage;
                //执行一个laypage实例
                laypage.render({
                    elem: 'laypage-main',
                    count: ${pageDetail.total},
                    curr: ${pageDetail.current},
                    limit: ${pageDetail.size},
                    jump: function(obj,first){
                        if (!first){
                            location.href = "?page="+obj.curr;
                        }
                    }

                });
            });
        </script>
    </div>
</#macro>