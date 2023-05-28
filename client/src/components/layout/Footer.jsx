function Footer() {
  return (
    <div className="w-screen bg-white flex items-center justify-center">
      <footer className="max-w-limit w-full flex-col justify-center py-8">
        <div className="flex justify-between">
          <div className="font-extrabold text-2xl text-black1">
            <p>HELLO,</p>
            <p className="pt-3">DEVELOPER!</p>
          </div>
          <div className="w-[31rem] flex justify-between">
            <ul className="font-bold text-black1 text-base">
              About Us
              <li className="font-medium text-xs text-gray6 mt-4">프로젝트소개</li>
              <li className="font-medium text-xs text-gray6 mt-2">팀원소개</li>
            </ul>
            <ul className="font-bold text-black1 text-base">
              My Account
              <li className="font-medium text-xs text-gray6 mt-4">SIGN IN</li>
              <li className="font-medium text-xs text-gray6 mt-2">회원정보수정</li>
            </ul>
            <ul className="font-bold text-black1 text-base">
              Help
              <li className="font-medium text-xs text-gray6 mt-4">공지사항</li>
              <li className="font-medium text-xs text-gray6 mt-2">CONTACT US</li>
            </ul>
          </div>
        </div>
        <div className="font-play max-w-limit w-full mt-6 pt-4 mb-10 border-t border-solid border-gray2 font-normal text-[14px] text-black1">
          © 2023 Coding Team. All rights reserved.
        </div>
      </footer>
    </div>
  );
}

export default Footer;
