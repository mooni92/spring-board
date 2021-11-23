<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">
<head>
 <jsp:include page="../includes/head.jsp"/>
  <sec:csrfMetaTags/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/ckeditor/4.10.1/ckeditor.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/ckeditor/4.10.1/config.js"></script>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
       
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
          <!-- Topbar -->
            <jsp:include page="../includes/header.jsp"/>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Board Register</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
                        </div>
                        <div class="card-body">
                        <form method="post">
                           <div class="form-group">
                                 <label for="title" class="text-dark font-weight-bold">Title</label>
                                 <input class="form-control" id="title" name="title" value="에디터제목">
                               </div>
                               <div class="form-group">
                                 <label for="content" class="text-dark font-weight-bold">Content</label>
                                 <textarea rows="10" class="form-control" id="content1" name="content"></textarea>
                               </div>
                               <div class="form-group">
                                 <label for="writer" class="text-dark font-weight-bold">Writer</label>
                                 <input class="form-control" id="writer" name="writer" value="작성자" readonly>
                                 
                               </div>
                               <sec:csrfInput/>
                               <button class="btn btn-primary" id="btnSubmit">Submit</button>
                               <button class="btn btn-danger" type="reset">Reset</button>
                               
                               <!-- <script>  //CK editor적용
                                  CKEDITOR.replace("content1");</script> -->
                          </form>
                        </div>
                    </div>
                    
                      <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">File Attach</h6>
                        </div>
                        <div class="card-body">
                           <div class="form-group uploadDiv">
                                <label for="files" class="btn btn-primary px-4"><i class='far fa-file mr-2'></i> File</label>
                                <input type="file" class="form-control d-none" id="files" name="files" multiple>
                           </div>
                            <div class="uploadResult">
                        <ul class="list-group">
                        </ul>
                             </div>
                  </div>
                     </div>
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- End of Main Content -->
            <!-- Footer -->
     <jsp:include page="../includes/footer.jsp"/>     
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->
    <script>
         //get방식을 제외한 비동기처리에 필요함
      //
   /*       var csrfHeader=$("meta[name='_csrf_header']").attr("content");
		   var csrfToken=$("meta[name='_csrf']").attr("content");
		   
		   $(document).ajaxSend(function (e,xhr) {
		      xhr.setRequestHeader(csrfHeader,csrfToken);
		   }) */
      
         function showImage(fileCallPath) {
            $("#pictureModal")
            .find("img").attr("src","/display?fileName="+fileCallPath)
            .end().modal("show");
      //   alert(fileCallPath);
      }
         
        $(function() {
	       var csrfHeader=$("meta[name='_csrf_header']").attr("content");
	       var csrfToken=$("meta[name='_csrf']").attr("content");
	       
	       $(document).ajaxSend(function (e,xhr) {
	         xhr.setRequestHeader(csrfHeader,csrfToken);
	      })
	      
         var cloneObj=$(".uploadDiv").clone(); //부모태그 가져옴(div)
         
         var regex = /(.*?)\.(exe|sh|zip|alz)$/;
            var maxSize = 1024 * 1024 * 5;

            
            function checkExtension(fileName, fileSize) {
               if(fileSize >= maxSize) {
                  alert("파일 사이즈 초과");
                  return false;
               }
               if(regex.test(fileName)) {
                  alert("해당 종류의 파일은 업로드 할 수 없습니다.");
                  return false;
               }
               return true;
            }

            function showUploadedFile(resultArr) {
                var str = "";
                //이미지 업로드 후 콘솔창 확인
                for(var i in resultArr) {
                   str += "<li class='list-group-item' "
                   str += "data-uuid='" + resultArr[i].uuid + "' ";
                   str += "data-path='" + resultArr[i].path + "' ";
                   str += "data-origin='"+ resultArr[i].origin + "' ";
                   str += "data-size='"+ resultArr[i].size + "' "
                   str += "data-image='"+ resultArr[i].image + "' "
                   str += "data-mime='"+ resultArr[i].mime + "' "
                   str += "data-ext='"+ resultArr[i].ext + "' "
                   str += "data-ext='"+ resultArr[i].ext + "' "
                   str += ">"
                   if(resultArr[i].image) {
                      //알럿창에 이미지 이름 띄우기
                     str += "<a href='javascript:showImage(\""+resultArr[i].fullPath+"\")'>"
                      str += "<img src='/display?fileName=" +  resultArr[i].thumb  + "'>";
                     str += "</a>";
                   } else {
                     str += "<a href='/download?fileName=" + resultArr[i].fullPath + "'>";
                     str += "<i class='fas fa-paperclip'></i> " + resultArr[i].origin + "</a>"
                   }
                   str += "  <small><i data-file='"+ resultArr[i].fullPath +"' data-image='"+resultArr[i].image+"'"
                   str += "class='fas fa-trash-alt text-danger'></i></small></li>";
                }
                $(".uploadResult ul").append(str);
             }

            
            
         $(".uploadDiv").on("change","#files",function() {
            var files = $("#files")[0].files;
            console.log(files);

            var formData = new FormData();
            for(var i in files) {
                     if(!checkExtension(files[i].name, files[i].size)){
                        return false;
                     }
               formData.append("files", files[i]);
            }

            $.ajax("/upload", {
               processData:false,
               contentType:false,
               data:formData,
               dataType:'json',
               type:"POST",
               success:function(result) {
                 // alert(result);
                 console.log(result);
                 $(".uploadDiv").html(cloneObj.html());
                 showUploadedFile(result);
               }
            })
         });
        // $("#pictureModal").modal("show");
        $(".uploadResult").on("click","small i", function(){
           var $li=$(this).closest("li");
           $.ajax("/deleteFile", {
              type : "post",
              data : {fileName:$(this).data("file"),image:$(this).data("image")}, //data("image")->true ,false들어가있음
              success : function(result){
                  $li.remove();
              }
           })
        });
        //글작성 이밴트
        $("#btnSubmit").click(function() {
           event.preventDefault();
           var str="";
           var datas=["uuid","path","origin","ext","mime","size","image"];
           //datas[0]
           $(".uploadResult li").each(function(i) {//(function(i) li반복문의 i
              //여기디스.uploadResult li
              for(var j in datas)//7번돌음
              //for(var j =0;datas.length;j++)
            str+="<input type='hidden' name='attachs["+i+"]." + datas[j] + "' value='"+$(this).data(datas[j])+"'>";
         });
           //여기디스 btnSubmit
           $(this).closest("form").append(str).submit();
           //console.log($(this).closest("form").append(str).html());
         })
        CKEDITOR.replace("content1", {
        	      toolbar: [{
        	          name: 'document',
        	          items: ['Print']
        	        },
        	        {
        	          name: 'clipboard',
        	          items: ['Undo', 'Redo']
        	        },
        	        {
        	          name: 'styles',
        	          items: ['Format', 'Font', 'FontSize']
        	        },
        	        {
        	          name: 'colors',
        	          items: ['TextColor', 'BGColor']
        	        },
        	        {
        	          name: 'align',
        	          items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']
        	        },
        	        '/',
        	        {
        	          name: 'basicstyles',
        	          items: ['Bold', 'Italic', 'Underline', 'Strike', 'RemoveFormat', 'CopyFormatting']
        	        },
        	        {
        	          name: 'links',
        	          items: ['Link', 'Unlink']
        	        },
        	        {
        	          name: 'paragraph',
        	          items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote']
        	        },
        	        {
        	          name: 'insert',
        	          items: ['Image', 'Table']
        	        },
        	        {
        	          name: 'tools',
        	          items: ['Maximize']
        	        },
        	        {
        	          name: 'editing',
        	          items: ['Scayt']
        	        }
        	      ],

        	      extraAllowedContent: 'h3{clear};h2{line-height};h2 h3{margin-left,margin-top}',

        	      // Adding drag and drop image upload.
        	      extraPlugins: 'print,format,font,colorbutton,justify,uploadimage', 
        	      uploadUrl: '/ckupload.json?${_csrf.parameterName}=${_csrf.token}&command=file&type=File&responseType=json',
        	    		  
		          filebrowserImageUploadUrl: '/ckupload.json?${_csrf.parameterName}=${_csrf.token}&command=QuickUpload&type=Images',
		       /*      filebrowserUploadMethod : "form" */

        	      // Configure your file manager integration. This example uses CKFinder 3 for PHP.
        	   /*    filebrowserBrowseUrl: '/apps/ckfinder/3.4.5/ckfinder.html',
        	      filebrowserImageBrowseUrl: '/apps/ckfinder/3.4.5/ckfinder.html?type=Images',
        	      filebrowserUploadUrl: '/apps/ckfinder/3.4.5/core/connector/php/connector.php?command=QuickUpload&type=Files',
        	      filebrowserImageUploadUrl: '/apps/ckfinder/3.4.5/core/connector/php/connector.php?command=QuickUpload&type=Images', */

        	      height: 560,

        	      removeDialogTabs: 'image:advanced;link:advanced',
        	      removeButtons: 'PasteFromWord'
        
        	/* filebrowserUploadUrl : "",
        	filebrowserImageUploadUrl : "", */
     /*    	filebrowserBrowseUrl: '/apps/ckfinder/3.4.5/ckfinder.html',
            filebrowserImageBrowseUrl: '/apps/ckfinder/3.4.5/ckfinder.html?type=Images', */
        });
           
       
      })
      
   </script>
       <div class="modal fade" id="pictureModal">
        <div class="modal-dialog  modal-xl" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Image Detail</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                   <img class="mw-100" src="">
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../includes/foot.jsp"/>     
</body>

</html>