import ProfileLine from './UI/ProfileLine';

function ProfileContent() {
  return (
    <div className="w-full mt-main-top pt-[88px] mb-[11.2rem]">
      <div className="flex items-center mb-[6.25rem]">
        {/* img: random background color , nickname center */}
        <img className="rounded-full w-[8rem] h-[8rem] mr-[55px] bg-slate-300" />
        <div>
          <p className="font-bold text-[25px] text-black3">Hello213님</p>
          <p className="mt-[5px] font-normal text-[14px] text-black">#프론트엔드</p>
        </div>
      </div>
      <p className="mt-[6.25rem] text-black3 text-[18px] font-bold">회원정보</p>
      <div className="mt-[18px] w-full border-solid border-y-[1px] border-gray4">
        <ProfileLine title={'이름'} content={'Hello213'} />
        <ProfileLine title={'비밀번호'} content={'******'} />
        <ProfileLine title={'이메일'} content={'Hello213@gmail.com'} />
        <ProfileLine title={'관심분야'} content={'프론트엔드'} />
      </div>
      <button className="w-full mt-[49px] font-bold text-[14px] text-gray11 text-right border-none">
        회원탈퇴하기
      </button>
    </div>
  );
}

export default ProfileContent;
