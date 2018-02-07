# ReviewPdfOnline
Android打开本地PDF及网络上的PDF（不下载），用pdf.js,亲测有效

index1.html只可以打开英文的pdf
viewer.html都可以打开，中文的英文的，本地的网络的
大家也可以实现下载pdf到本地然后打开



电子签章属于PDF文档的注释或者标记，
                 * 解决电子签章不显示的问题：
                 *在pdf.worker.js文件中找到如下代码段，将this.setFlags(AnnotationFlag.HIDDEN);注释掉就会显示电子签章，反之不显示
                 * 在pdf/generic/build/pdf.worker.js搜索this.setFlags或者AnnotationFlag.HIDDEN，找到
                 *  if (data.fieldType === 'Sig') {
                 this.setFlags(_util.AnnotationFlag.HIDDEN); 注释掉这句就可以了
                 }
