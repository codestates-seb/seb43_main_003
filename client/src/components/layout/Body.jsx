import { useEffect } from 'react';
import axios from '../../utils/axios';

import Footer from './Footer';
import Header from './Header';

function Body({ children, layoutInfo }) {
  const { isMainContentWidthScreen } = layoutInfo;

  // useEffect(() => {
  //   const fetchUserInfo = async () => {
  //     try {
  //       const response = await axios.get('userinfo');

  //       if (response.status >= 200 && response.status < 300) {
  //         console.log(`from server /userinfo 👉🏻`, response.data);
  //       }
  //     } catch (err) {
  //       console.log(err.message);
  //     }
  //   };

  //   fetchUserInfo();
  // }, []);

  return (
    <div className="w-screen flex flex-col items-center bg-gray1 font-noto-kr">
      <Header />
      {isMainContentWidthScreen && children}

      {!isMainContentWidthScreen && (
        <main className="max-w-limit w-full pt-36 flex justify-center">{children}</main>
      )}
      <Footer />
    </div>
  );
}

export default Body;
