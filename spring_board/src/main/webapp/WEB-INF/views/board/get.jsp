<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<jsp:include page="../includes/head.jsp" />
 <sec:csrfMetaTags/>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
            <!-- Topbar -->
<jsp:include page="../includes/header.jsp" />
            <!-- End of Topbar -->
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
                        </div>
                        <div class="card-body">
                           <form method="post">
                           <div class="form-group">
                                  <label for="bno" class="text-dark font-weight-bold">Bno</label>
                                  <input class="form-control" id="bno" name="bno" disabled value="${board.bno}">
                               </div>
                              <div class="form-group">
                                  <label for="title" class="text-dark font-weight-bold">Title</label>
                                  <input class="form-control" id="title" name="title" disabled value="${board.title}">
                               </div>
                                 <div class="form-group">
                                    <label for="content" class="text-dark font-weight-bold">Content</label>
                                    <textarea class="form-control" id="content" name="content" disabled>${board.content}</textarea>
                               </div>
                                <div class="form-group">
                                    <label for="content" class="text-dark font-weight-bold">Content</label>
                                    <div>
                                       <c:out value="${board.content}" escapeXml="false"/>
                                    </div>
                               </div>
                                <%--  <div class="form-group row container">
                                    <label for="content" class="text-dark font-weight-bold">Content</label>
                                    <div class="mw-100" id="content" >${board.content}</div>
                               </div> --%>
                               <div class="form-group">
                                  <label for="writer" class="text-dark font-weight-bold">Writer</label>
                                  <input class="form-control" id="writer" name="writer" disabled value="${board.writer}">
                               </div>
                               <sec:authentication property="principal" var="pinfo"/>
                               <sec:authorize access="isAuthenticated()">
                               <c:if test="${pinfo.username==board.writer }">
                               <a class="btn btn-warning" href="modify${cri.params}&bno=${board.bno}">modify</a>   
                               </c:if>
                               </sec:authorize>
                               <a class="btn btn-primary" href="list${cri.params}">List</a>   
                             </form>
                        </div>
                    </div>
                    <!-- file -->
                        <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">File Attach</h6>
                        </div>
                        <div class="card-body">
                            <div class="uploadResult">
                        <ul class="list-group">
                        </ul>
                             </div>
                  </div>
                     </div>
                     <!-- reply -->
                     <div class="card shadow mb-4">
                        <div class="card-header py-3 clearfix">
                            <h6 class="m-0 font-weight-bold text-primary float-left"><i class="fa fa-comments"></i> Reply</h6>
                            <button class="btn btn-primary float-right btn-sm" id="btnRegFrm">New Reply</button>
                        </div>
                        <ul id="replyUL" class="list-group list-group-flush">
                        </ul>
                        <div class="card-footer text-center">
                            <button class="btn btn-primary btn-block" id="btnShowMore">더보기</button>
                        </div>
                    </div>
                    <!-- end of reply -->
                </div>
                <!-- /.container-fluid -->
       
            </div>
            <!-- End of Main Content -->
            <script src="${pageContext.request.contextPath}/resources/js/reply.js"></script>
<script>
   //get방식을 제외한 비동기처리에 필요함
   var csrfHeader=$("meta[name='_csrf_header']").attr("content");
   var csrfToken=$("meta[name='_csrf']").attr("content");
   
   $(document).ajaxSend(function (e,xhr) {
      xhr.setRequestHeader(csrfHeader,csrfToken);
   })


   // ************************************ getList *******************************************
    $(function() {
        console.log(replyService);

        var bno = '${board.bno}';
        var $ul = $("#replyUL");
      
        showList();
        function showList(lastRno,amount){
        
            replyService.getList({bno:bno,lastRno:lastRno,amount:amount},
                function(data) {
                    if(!data){ 
                        return;
                    }
                    if(data.length == 0){ 
                        $("#btnShowMore").text("댓글이 없습니다.").prop("disabled",true);
                        return;
                    }

                    var str="";
                    for(var i in data){
                    str += '<li class="list-group-item" data-rno="'+ +data[i].rno + '">'
                    str += '     <div class="clearfix">'
                    str += '           <div class="float-left text-dark font-weight-bold">'+data[i].replyer+'</div>'
                    str += '           <div class="float-right">'+replyService.displayTime(data[i].replyDate)+'</div>'
                    str += '    </div>'
                    str += '    <div>'+data[i].reply+'</div>'
                    str += '</li>'
                } 
                $("#btnShowMore").text("더보기").prop("disabled",false);
                $ul.append(str);
            }
            )
        }
        // ************************************ reply frm add *******************************************
        $("#btnRegFrm").click(function(){//새댓글쓰기 벝튼 누르면
            /*  <sec:authorize access="isAnonymous()"> */
              if(confirm("로그인이 필요한 페이지 입니다. 로그인 페이지로 이동하겠습니까?")){
                   location.href="/customLogin"
              }
              else{
                 return;
              }
          /*     </sec:authorize> */
            $("#myModal").find("input").val("");//#myModal의 input태그의 값을 가져온다
            $("#replyDate").closest("div").hide();//replyDate의 가장 가까운div찾아서 숨김
            $(".btns button").hide();//.btns밑에 있는 버튼들(Register,modify,remove)을 숨김
            $("#btnReg").show();//Register버튼 보여줌
            $("#myModal").modal("show");//모달 띄움
            
        })

        // ************************************ add *******************************************
        $("#btnReg").click(function() {//#btnReg를 클릭하면
           //reply변수에 들어간 데이터들로 replyVo생성자  호출해서 add()에 넣는다
            var reply = {reply:$("#reply").val(), replyer: $("#replyer").val(), bno:bno};
            replyService.add(reply,
                function(data) {
                    alert(data);//success띄움
                    var count=$ul.find("li").length;//ul태그에 있는 li개수 구함
                      $ul.html("");//??
                    $("#myModal").find("input").val("");//#myModal의 input태그의 값을 가져온다
                    $("#myModal").modal("hide");//그리고 #myModal을 숨겨
                    showList(0,count+1);//그리고 리스트 보여줌
                }

            );
        })
       // ************************************  get   *********************************************   
        $ul.on("click","li",function(){//
            //alert($(this).data("rno"));
            var rno=$(this).data("rno");
            replyService.get(rno,function(data){
                $("#reply").val(data.reply);//reply칸에 가져온reply 넣음
                $("#replyer").val(data.replyer);//replyer칸에 가져온 replyer넣음
                //replyDate에 가져온 replyDtate넣고 읽기전용으로만 만들고  div찾아서 보여좀
                $("#replyDate").val(replyService.displayTime(data.replyDate)).prop("readonly",true).closest("div").show();
                $(".btns button").hide();//버튼 3개 다숨기고
                $("#btnMod, #btnRmv").show();//수정버튼이랑 삭제버튼만 보여줌
                $("#myModal").data("rno",data.rno).modal("show");//모달 보여줌~
            });
        })
        // ************************************ modify  *******************************************   
        $("#btnMod").click(function(){//modify버튼 클릭하면
           //제목,댓글번호,작성자
            var reply = {reply:$("#reply").val(), rno:$("#myModal").data("rno"),replyer:$("#replyer").val()};
            replyService.modify(reply, function(data) {
                    alert(data)//success띄우고
                    $("#myModal").modal("hide");//모달창 숨김
                   // showList();
                    $ul.find("li").each(function(){//ul밑에 있는 li들을 찾아서 
                        if($(this).data("rno")==reply.rno){//만약 li의 rno가 위에 rno면
                          $(this).children().eq(0).find("div").first().text(reply.replyer); //replyer바뀜
                          $(this).children().eq(1).text(reply.reply);//reply바뀜
                    }
                    })
                })
        })
        // ************************************ remove  *******************************************   
        $("#btnRmv").click(function(){
            var rno = $("#myModal").data("rno");
          //remove함수에 rno넣고 
            replyService.remove(rno, function(data) { 
                  //success띄움
                    alert(data)
                     //모달창 숨김
                    $("#myModal").modal("hide");
                    //showList();
                    
                    //ul안에 있는 li찾아서 
                    $ul.find("li").each(function(){ 
                       //li의 rno가 위의 rno랑 같으면
                        if($(this).data("rno")==rno)
                        //li지움
                        $(this).remove();
                    })
                })
        })
        // ************************************ 더보기 버튼  *******************************************   
         $("#btnShowMore").click(function(){//더보기 버튼 클릭하면
             var lastRno = $ul.find("li:last").data("rno"); //lastRno에 ul 안에 있는 마지막li의  rno lastRno에 넣는다.
            // alert(lastRno);
             showList(lastRno);//?
         })

        /*
        더보기 버튼 클릭시 : 마지막 댓글번호 기준으로 GETLIST호출
        댓글 작성 시 : 첫 페이지 본인의 댓글이 표시
        댓글 수정 시 : GETLIST X 동적 처리
        댓글 삭제 시 : GETLIST X 동적 처리
        */
       
       //$("#myModal").modal("show");

         // JS Test

        /**
         * replyService.add
         * 2021.10.06
         */
        // ************************************ add *******************************************
        // replyService.add(
        //     {reply:'브라우저 테스트', replyer:'테스터', bno:69631},
        //      function(data){
        //           alert(data)
        //     }
        // );

         // ************************************ getList  *******************************************   
         // replyService.getList({bno:257, amount:5, lastRno:5}, function(data) {console.log(data)})

         // ************************************ modify  *******************************************   
         //   replyService.modify({rno:1, reply:'브라우저 콘솔 수정', replyer:'테스터'});

         // ************************************ remove  *******************************************   
         // replyService.remove(6)

         // ************************************  get   *********************************************   
         // replyService.get(6)
      
         //첨부파일 불러오기
         $.getJSON("/board/getAttachs/"+bno).done(function(data) {
            console.log(data);
            showUploadedFile(data);
      })
    }); //end of ready
    function showImage(fileCallPath) {
            $("#pictureModal")
            .find("img").attr("src","/display?fileName="+fileCallPath)
            .end().modal("show");
      //   alert(fileCallPath);
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
              str += "<i class='fas fa-paperclip'></i> " + resultArr[i].origin + "</a>";
           }
           str += "</li>";
        }
        $(".uploadResult ul").append(str);
     }

</script>

            <!-- Footer -->
     <jsp:include page="../includes/footer.jsp"/>     
     <!-- End of Footer -->

 </div>
 <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->
<!-- List Modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
 <div class="modal-dialog" role="document">
     <div class="modal-content">
         <div class="modal-header">
             <h5 class="modal-title" id="exampleModalLabel">Reply Modal</h5>
             <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                 <span aria-hidden="true">×</span>
             </button>
         </div>
         <div class="modal-body">
             <div class="form-group">
                 <label for="reply" class="text-dark font-weight-bold">Reply</label>
                 <input class="form-control" id="reply" name="reply" placeholder="New Reply!!!!">
             </div>
             <div class="form-group">
                 <label for="reply" class="text-dark font-weight-bold">Replyer</label>
                 <input class="form-control" id="replyer" name="replyer" placeholder="replyer">
             </div> 
             <div class="form-group">
                 <label for="reply" class="text-dark font-weight-bold">Reply Date</label>
                 <input class="form-control" id="replyDate" name="replyDate" placeholder="">
             </div>       
         </div>
         <div class="modal-footer text-right">
             <div class="btns">
                 <button class="btn btn-primary" id="btnReg">Register</button>
                 <button class="btn btn-warning" id="btnMod">Modify</button>
                 <button class="btn btn-danger" id="btnRmv">Remove</button>
             </div>
             <button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
         </div>
     </div>
 </div>
</div>
<jsp:include page="../includes/foot.jsp"/>     
</body>

</html>