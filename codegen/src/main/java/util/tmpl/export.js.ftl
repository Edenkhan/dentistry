	$table.table('addAction', {
	    permission: '${permission}',
	    text: '数据导出',
	    icon: 'file-excel-o',
	    enable: true,
	    exports: [
			<#list cells as cell>
			{name: '${cell.name}', displayName: '${cell.displayName}'},
			</#list>
	    ],
	    action: function ($btn) {
	        $.dialog({
	            cls: 'middle',
	            title: '数据导出',
	            titleIcon: 'file-excel-o',
	            html: '<div></div>',
	            okText: '确定导出',
	            onLoad: function ($context) {
	                $context._export({
	                    url: '${url}',
	                    page: $table.condition.data('conditionform').getParam('page') || 1,
	                    filename: '${filename}',
	                    exports: $btn.data('opts').exports,
	                    onInit: function(){
	                        $context.find(':checkbox').uniform();
	                    }
	                });
	            },
	            onOk: function($context){
	                var params = $table.condition.data('conditionform').getParams();
	                $context._export('export', params);
	            }
	        });
	    }
	});
