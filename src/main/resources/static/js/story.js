/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */
// (0) 현재 로그인한 사용자 아이디
let principalId = $("#principalId").val(); // header.jsp에 정의해놓은 principalId

// (1) 스토리 로드하기

let page = 0;

function storyLoad() {
	$.ajax({
		/*type default는 get*/
		url:`/api/image?page=${page}`,
		dataType:"json"
	}).done(res=>{
		
		res.data.content.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		})
	}).fail(error=>{
		
	})
}

storyLoad();

function getStoryItem(image) {
	let item = `<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.username}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;
			
			if(image.likeState){
				item+=`<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
			}
			else{
				item+=`<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
			}
				
				
		item += `		
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.id}">`;

		image.comments.forEach((comment)=>{
			item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
				<p>
					<b>${comment.user.username} :</b> ${comment.content}
				</p>`;
			
			if(principalId == comment.user.id){
				item+=`<button onclick="deleteComment(${comment.id})">
								<i class="fas fa-times"></i>
							</button>`;
			}
				
				
			item +=`
			</div>`;
		})
			
		
		item += `
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;
	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	//문서의 높이가 윈도우 높이와 윈도우 스크롤탑의 합이 되었을 때가 스크롤 가장 마지막부분!
	//console.log("윈도우 st", $(window).scrollTop());
	//console.log("문서 h", $(document).height());
	//console.log("윈도우 h", $(window).height());
	
	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	
	if(checkNum < 1 && checkNum > -1){
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	
	if (likeIcon.hasClass("far")) { //좋아요 안눌린 상태 -> 좋아요 하겠다
		$.ajax({
			type:"post",
			url: `/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); // 해당 아이디에 접근해서 내부 텍스트를 가져와 (좋아요 숫자)
			let likeCount = Number(likeCountStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});
		
		
	} else { //좋아요 눌린 상태 -> 좋아요 취소 하겠다
		$.ajax({
			type:"delete",
			url: `/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); // 해당 아이디에 접근해서 내부 텍스트를 가져와 (좋아요 숫자)
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error);
		});
		
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		content: commentInput.val(),
		imageId: imageId
	}

/*	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}*/

	$.ajax({
		type:"post",
		url:`/api/comment`,
		data:	JSON.stringify(data), //보낼 데이터
		contentType:"application/json; charset=utf-8", //보낼 타입
		dataType:"json" //응답받을 타입
		
	}).done(res=>{
		//console.log("성공", res);
		
		let comment = res.data;
		
		let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b>${comment.user.username} :</b>
		      ${comment.content}
		    </p>
		    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
		  </div>
		`;
		
		commentList.prepend(content); //앞에 넣기
		
	}).fail(error=>{
		console.log("실패", error.responseJSON.data.content);
		alert(error.responseJSON.data.content);
	});
	

	commentInput.val(""); //인풋 필드를 비운다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
		
	}).done(res=>{
		console.log("성공",res);
		
		$(`#storyCommentItem-${commentId}`).remove(); // 화면에서 삭제
	}).fail(error=>{
		console.log("실패", error);
	});
}







