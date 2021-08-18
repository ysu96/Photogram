// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault(); //폼태그 액션 막기?
	
	let data = $("#profileUpdate").serialize(); //해당 아이디의 폼태그를 찾아서 모든 정보를 시리얼라이즈해줌, 프로필 수정 폼 id
	
	$.ajax({ //js object가 들어가야함
		type:"put",
		url:`/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", //데이터의 타입, 키-벨류 타입임
		dataType: "json" /*반환받을 타입?*/
		
	}).done(res=>{  /*javascript object로 파싱해줌, res는 javascript object가 됨, 위 요청의 응답을 담음*/
	//HttpStatus 상태코드 200번대
		console.log("update 성공", res);
		location.href=`/user/${userId}`; //성공하면 이 페이지로 돌아가라
		
	}).fail(error=>{
		// HttpStatus 상태코드 200번대 아닐 때
		if(error.data == null){
			alert(JSON.stringify(error.responseJSON.message)); //JSON.stringify 함수 사용하면 js 오브젝트를 json 문자열로 변환해줌
		}else{
			alert(JSON.stringify(error.responseJSON.data)); 
		}
		
		console.log("update 실패", error);
	});
	
	
}