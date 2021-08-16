// (1) 회원정보 수정
function update(userId) {
	let data = $("#profileUpdate").serialize(); //프로필 수정 폼 id
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json" /*반환받을 타입?*/
	}).done(res=>{  /*javascript object로 파싱해줌, res는 javascript object가 됨*/
		console.log("update 성공");
		location.href=`/user/${userId}`; //성공하면 이 페이지로 돌아가라
		
	}).fail(error=>{
		console.log("update 실패");
	});
	
	
}